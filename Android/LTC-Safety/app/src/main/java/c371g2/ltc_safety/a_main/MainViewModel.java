package c371g2.ltc_safety.a_main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.Concern;
import com.appspot.ltc_safety.client.model.ConcernCollection;
import com.appspot.ltc_safety.client.model.ConcernStatus;
import com.appspot.ltc_safety.client.model.OwnerToken;
import com.appspot.ltc_safety.client.model.OwnerTokenListWrapper;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

/**
 * This class acts as an interface between the app view in the main activity and the model. It
 * contains the list of Concerns submitted from this device in concerns. The ConcernRetractionObserver
 * interface is used by the other two View-Model classes to update the concerns with changes
 * resulting from the network operations performed in each.
 *
 * Contains a static inner-class 'Test_Hook' to aid testing.
 *
 * @Invariants none
 * @HistoryProperties
 * - Only one instance of this class exists for the duration of the time the application is
 * running.
 */
class MainViewModel extends AbstractNetworkViewModel implements ConcernRetractionObserver, ConcernSubmissionObserver, Serializable {
    /**
     * The list of all concerns stored on the device.
     * Retracted concerns persist.
     */
    private TreeSet<ConcernWrapper> concerns;
    /**
     * Reference to thread that is used to perform the fetch network operation. Only initialized
     * when fetching is performed, set to null when the thread ends. Used to help prevent memory
     * leaks.
     */
    private transient ConcernUpdater networkTask;
    /**
     * The return code that results from an attempt to submit or retract a concern.
     * This variable is null until a concern submission or retraction is attempted.
     */
    transient FetchReturnCode fetchReturnCode;

    /**
     * Constructor for Testing
     */
    private MainViewModel(){
        concerns = new TreeSet<>();
    }

    /**
     * Package-private MainViewModel constructor.
     * @param activity The main activity
     */
    MainViewModel(@NonNull AbstractNetworkActivity activity) {
        concerns = DeviceStorage.loadConcerns(activity.getBaseContext());
        assert(concerns!=null);
        this.activityWeakReference = new WeakReference<>(activity);
        updateConcerns();
    }

    @Override
    public void concernSubmitted(@NonNull Context context, @NonNull ConcernWrapper newConcern) {
        DeviceStorage.saveConcern(context, newConcern);
        concerns.add(newConcern);
    }

    @Override
    public void concernRetracted(@NonNull Context context, @NonNull ConcernWrapper newConcern) {
        Iterator<ConcernWrapper> iterator = concerns.iterator();
        while(iterator.hasNext()) {
            ConcernWrapper oldConcern = iterator.next();
            if(oldConcern.getOwnerToken().equals(newConcern.getOwnerToken())){
                assert(oldConcern != null);
                assert(oldConcern.getReporterName().equals(newConcern.getReporterName()));
                assert(oldConcern.getReporterEmail().equals(newConcern.getReporterEmail()));
                assert(oldConcern.getReporterPhone().equals(newConcern.getReporterPhone()));
                assert(oldConcern.getConcernType().equals(newConcern.getConcernType()));
                assert(oldConcern.getActionsTaken().equals(newConcern.getActionsTaken()));
                assert(oldConcern.getReporterName().equals(newConcern.getReporterName()));
                assert(oldConcern.getOwnerToken().equals(newConcern.getOwnerToken()));
                break;
            }
        }
        iterator.remove();
        concerns.add(newConcern);
        DeviceStorage.saveConcern(context, newConcern);
    }

    @Override
    protected void stopNetworkThread() {
        if(networkTask != null) {
            networkTask.cancel(true);
            networkTask.concernCollection = null;
            networkTask.viewModel = null;
            networkTask = null;
        }
    }

    /**
     * Sets the current instance of MainActivity that is running. Only called when MainActivity is
     * launched for the nth time since the application started (n!=1).
     * @preconditions
     * - if |activity| is not null, MainActivity has just started.
     * @modifies activity instance stored in this object is changed to |activity|.
     * @param activity MainActivity instance.
     */
    void setActivity(@NonNull AbstractNetworkActivity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    /**
     * Retrieves the list of previously submitted concerns. The list is sorted with the most recent
     * concerns at lower indices.
     * @preconditions none
     * @modifies none.
     * @return The sorted list of previously submitted concerns.
     */
    List<ConcernWrapper> getSortedConcernList(){
        assert(concerns != null);
        return new ArrayList<>(concerns);
    }

    /**
     * Retrieve concern in concerns at index provided
     * @preconditions none
     * @modifies nothing
     * @param index index of concern. index >= 0
     * @return concern at index provided
     */
    ConcernWrapper getConcernAtIndex(int index) {
        return (ConcernWrapper) concerns.toArray()[index];
    }

    /**
     * Uses the inner-class ConcernUpdater to update the list of concern statuses for each concern
     * stored on this device.
     * @preconditions Device has internet access
     * @modifies
     * If any concerns have changed and an internet connection is available:
     *  - The updated version replaces the old concern in the concerns
     *  - The updated version overwrites the old version in device memory
     * If no concerns have changed: Nothing happens.
     * @return true if concern fetching has started, else false
     */
    boolean updateConcerns() {
        if(concerns.size()>0) {
            networkTask = new ConcernUpdater();
            networkTask.viewModel = this;
            networkTask.execute();
            return true;
        } else {
            fetchReturnCode = FetchReturnCode.NO_CONCERNS;
            return false;
        }
    }

    /**
     * This class is responsible for utilizing the network connection to fetch the all concerns that
     * correspond to the owner tokens stored on the device. This is for updating the concern status
     * list of each concern. Each new concern status will be added to it's parent concern.
     * @Invariants none
     * @HistoryProperties none
     */
    static class ConcernUpdater extends AsyncTask<Void,Void,FetchReturnCode> {
        /**
         * Reference to MainViewModel instance
         */
        MainViewModel viewModel;
        /**
         * Stores the backends response to the fetchConcerns request.
         */
        ConcernCollection concernCollection;

        @Override
        protected FetchReturnCode doInBackground(Void... params) {
            Client.Builder builder = new Client.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),
                    null
            );
            Client client = builder.build();

            OwnerTokenListWrapper listWrapper = new OwnerTokenListWrapper();
            listWrapper.setTokens(getStoredOwnerTokens());
            FetchReturnCode rCode = null;

            try {
                concernCollection = client.fetchConcerns(listWrapper).execute();
                rCode = FetchReturnCode.SUCCESS;
            } catch(IOException ioException) {
                concernCollection = null;
                rCode = FetchReturnCode.IOEXCEPTION_THROWN_BY_API;
            }

            return rCode;
        }

        @Override
        protected void onPostExecute(FetchReturnCode returnCode) {
            viewModel.networkTask = null;
            final AbstractNetworkActivity activity = viewModel.activityWeakReference.get();

            String message;
            if(FetchReturnCode.SUCCESS.equals(returnCode)) {
                assert(concernCollection != null);
                returnCode = addNewConcernStatuses(activity.getBaseContext(), concernCollection);
                message = "Concern statuses were updated";
            } else {
                returnCode = FetchReturnCode.IOEXCEPTION_THROWN_BY_API;
                message = "Concern status updates failed";
            }

            //progressDialog is not initialized for tests
            if(!activity.isFinishing() && activity.progressDialog!=null){
                assert(activity.progressDialog.isShowing());
                activity.progressDialog.cancel();
                AlertDialog.Builder b = new AlertDialog.Builder(activity);
                b.setMessage(message);
                activity.progressDialog = b.create();
                //activity.progressDialog.setTitle(message);
                activity.progressDialog.setCancelable(true);
                activity.progressDialog.show();
            }

            viewModel.fetchReturnCode = returnCode;
            viewModel.networkTask = null;
            concernCollection = null;
            viewModel.signalLatch.countDown();
            assert(viewModel.signalLatch.getCount() == 0);
            ((ListView) activity.findViewById(R.id.main_concernListView)).invalidateViews();
            viewModel = null;
        }

        /**
         * Retrieve the OwnerToken from each Concern in concerns.
         * @return List of OwnerTokens from each concern stored on the Device.
         */
        public @NonNull ArrayList<OwnerToken> getStoredOwnerTokens() {
            ArrayList<OwnerToken> tokens = new ArrayList<>();
            for(ConcernWrapper concern: viewModel.concerns) {
                tokens.add(concern.getOwnerToken());
            }
            return tokens;
        }

        /**
         * Updates the ConcernStatus list of each concern in concerns that has changed.
         * This involves converting each new ConcernStatus into the local StatusWrapper class,
         * inserting that StatusWrapper into the relevant concern, and overwriting the previous
         * version of the concern in device memory.
         */
        private FetchReturnCode addNewConcernStatuses(Context context, ConcernCollection concernCollection) {
            Iterator<ConcernWrapper> iterator = viewModel.concerns.iterator();
            for(Concern backendConcern: concernCollection.getItems()) {
                ConcernWrapper localConcern = iterator.next();
                int newStatusCount = backendConcern.getStatuses().size() - localConcern.getStatuses().size();
                assert(newStatusCount >= 0);

                if(newStatusCount > 0) {
                    ArrayList<ConcernStatus> backendConcernStatusList = (ArrayList<ConcernStatus>) backendConcern.getStatuses();
                    ArrayList<StatusWrapper> localConcernStatusList = (ArrayList<StatusWrapper>) localConcern.getStatuses();

                    while(localConcern.getStatuses().size() != backendConcernStatusList.size()) {
                        StatusWrapper newStatus = new StatusWrapper(
                                backendConcernStatusList.get(localConcernStatusList.size()).getType(),
                                backendConcernStatusList.get(localConcernStatusList.size()).getCreationDate().getValue()
                        );
                        localConcernStatusList.add(newStatus);
                    }
                    DeviceStorage.saveConcern(context, localConcern);
                }
            }
            return FetchReturnCode.SUCCESS;
        }

    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Any methods or fields can be added to this static inner-Class to aid testing. To use this
     * class...
     *
     * 1) Implement required getters/modifiers here
     * 2) Call those methods in the test class
     *
     * @Invariants none
     * @HistoryProperties none
     */
    static class Test_Hook implements MainViewModel_TestHook {

        @Override
        public MainViewModel getMainViewModelInstance() {
            return new MainViewModel();
        }

        @Override
        public ConcernWrapper getConcern(ConcernRetractionObserver mainViewModel, int index) {
            return (ConcernWrapper) ((MainViewModel)mainViewModel).concerns.toArray()[index];
        }

        public static void clearConcernList(MainViewModel mainViewModel) {
            mainViewModel.concerns = new TreeSet<>();
        }

        static void addConcern(MainViewModel viewModel, ConcernWrapper concern) throws NullPointerException {
            viewModel.concerns.add(concern);
        }

        static void updateConcerns(MainViewModel viewModel) {
            viewModel.updateConcerns();
        }

    }
}
