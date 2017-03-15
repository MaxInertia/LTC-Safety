package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.Concern;
import com.appspot.ltc_safety.client.model.ConcernCollection;
import com.appspot.ltc_safety.client.model.ConcernStatus;
import com.appspot.ltc_safety.client.model.OwnerToken;
import com.appspot.ltc_safety.client.model.OwnerTokenListWrapper;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

/**
 * This class acts as an interface between the app view in the main activity and the model. It
 * contains the list of Concerns submitted from this device in concernList. The ViewModelObserver
 * interface is used by the other two View-Model classes to update the concernList with changes
 * resulting from the network operations performed in each.
 *
 * Contains a static inner-class 'Test_Hook' to aid testing.
 *
 * @Invariants none
 * @HistoryProperties
 * - The concernList is the same ArrayList instance for the duration of the time the application is
 * running. However, this is not the case when performing tests. The Test_Hook contains a method
 * which can reinitialize it.
 */
class MainViewModel extends AbstractNetworkViewModel implements ViewModelObserver {
    /**
     * Instance of MainViewModel used by ViewModelObserver interface to access non-static methods
     */
    static MainViewModel mainViewModel;
    /**
     * The return code that results from an attempt to submit or retract a concern.
     * This variable is null until a concern submission or retraction is attempted.
     */
    public FetchReturnCode fetchReturnCode;

    /**
     * The list of all concerns stored on the device.
     * Retracted concerns persist.
     */
    private static ArrayList<ConcernWrapper> concernList;

    /**
     * Constructor for ViewModelObserver
     */
    MainViewModel(){}

    /**
     * Constructor for MainActivity
     * @param activity The main activity
     */
    MainViewModel(@NonNull AbstractNetworkActivity activity) {
        this.activity = activity;
        mainViewModel = this;
    }

    /**
     * Initialize model; loads the concerns from device memory and stores them in concernList.
     * @preconditions context is not null.
     * @modifies concernList; adds all concerns stored in device memory to the list.
     * @param context The base context of the activity that called this method (typically MainActivity)
     */
    static void initialize(@NonNull Context context) {
        concernList = DeviceStorage.loadConcerns(context);
        assert(concernList!=null);
    }

    /**
     * Retrieves the list of previously submitted concerns. The list is sorted with the most recent
     * concerns at lower indices.
     * @preconditions initialize() was called prior to this method.
     * @modifies concernList is sorted; more recent concerns at lower indices.
     * @return The sorted list of previously submitted concerns
     */
    static ArrayList<ConcernWrapper> getSortedConcernList(){
        assert(concernList != null);
        Collections.sort(concernList);
        return concernList;
    }

    /**
     * Uses the inner-class ConcernUpdater to update the list of concern statuses for each concern
     * stored on this device.
     * @preconditions Must be called after loadConcerns()
     * @modifies
     * If any concerns have changed and an internet connection is available:
     * - The updated version replaces the old concern in the concernList
     * - The updated version overwrites the old version in device memory
     * If no concerns have changed: Nothing happens.
     * @param context The context of the activity the method is being called from. MainActivity in
     *                normal use, but may be others for tests.
     */
    void updateConcerns(@NonNull Context context) {
            ConcernUpdater networkTask = new ConcernUpdater();
            networkTask.execute();
    }

    @Override
    public void newConcernSubmitted(@NonNull Context context,@NonNull ConcernWrapper newConcern) {
        DeviceStorage.saveConcern(context, newConcern);
        concernList.add(newConcern);
    }

    @Override
    public ConcernWrapper getConcernAtIndex(int index) {
        return concernList.get(index);
    }

    @Override
    public void concernRetracted(Context context, ConcernWrapper concern, int index) {
        ConcernWrapper oldConcern = concernList.get(index);
        assert(oldConcern.getReporterName().equals(concern.getReporterName()));
        assert(oldConcern.getReporterEmail().equals(concern.getReporterEmail()));
        assert(oldConcern.getReporterPhone().equals(concern.getReporterPhone()));

        assert(oldConcern.getConcernType().equals(concern.getConcernType()));
        assert(oldConcern.getActionsTaken().equals(concern.getActionsTaken()));
        assert(oldConcern.getReporterName().equals(concern.getReporterName()));

        assert(oldConcern.getOwnerToken().equals(concern.getOwnerToken()));
        concernList.set(index,concern);
        DeviceStorage.saveConcern(context, concern);
    }

    /**
     * This class is responsible for utilizing the network connection to fetch the all concerns that
     * correspond to the owner tokens stored on the device. This is for updating the concern status
     * list. Each new concern status will be added to it's parent concern.
     * @Invariants none
     * @HistoryProperties none
     */
    class ConcernUpdater extends AsyncTask<Void,Void,FetchReturnCode> {
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
            String message = "Concern status updates failed";
            if(FetchReturnCode.SUCCESS.equals(returnCode)) {
                assert(concernCollection != null);
                returnCode = addNewConcernStatuses(concernCollection);
                message = "Concern statuses were updated";
            }

            if(!activity.isFinishing() && activity.progressDialog!=null) { //progressDialog is not initialized for tests
                assert(activity.progressDialog.isShowing());
                activity.progressDialog.setMessage(message);
                activity.progressDialog.setCancelable(true);
            }

            fetchReturnCode = returnCode;
            signalLatch.countDown();
            assert(signalLatch.getCount() == 0);
        }

        /**
         * Retrieve the OwnerToken from each Concern in concernList.
         * @return List of OwnerTokens from each concern stored on the Device.
         */
        public @NonNull ArrayList<OwnerToken> getStoredOwnerTokens() {
            ArrayList<OwnerToken> tokens = new ArrayList<>();
            for(ConcernWrapper concern: concernList) {
                tokens.add(concern.getOwnerToken());
            }
            return tokens;
        }

        /**
         * Updates the ConcernStatus list of each concern in concernList that has changed.
         * This involves converting each new ConcernStatus into the local StatusWrapper class,
         * inserting that StatusWrapper into the relevant concern, and overwriting the previous
         * version of the concern in device memory.
         */
        private FetchReturnCode addNewConcernStatuses(ConcernCollection concernCollection) {
            if(concernCollection==null || concernCollection.getItems()==null) {
                return FetchReturnCode.NULL_POINTER;
                // TODO: Display error message
            }
            int index = 0;
            for(Concern backendConcern: concernCollection.getItems()) {
                int newStatusCount = backendConcern.getStatuses().size() - concernList.get(index).getStatuses().size();
                assert(newStatusCount>=0);

                if(newStatusCount > 0) {
                    ArrayList<ConcernStatus> backendConcernStatusList = (ArrayList<ConcernStatus>) backendConcern.getStatuses();
                    ArrayList<StatusWrapper> localConcernStatusList = (ArrayList<StatusWrapper>) concernList.get(index).getStatuses();

                    while( backendConcern.getStatuses().size() != concernList.get(index).getStatuses().size()) {
                        StatusWrapper newStatus = new StatusWrapper(
                                backendConcernStatusList.get(localConcernStatusList.size()).getType(),
                                backendConcernStatusList.get(localConcernStatusList.size()).getCreationDate().getValue()
                        );
                        localConcernStatusList.add(newStatus);
                    }
                    DeviceStorage.saveConcern(activity.getBaseContext(), concernList.get(index));
                }
                index++;
            }
            return FetchReturnCode.SUCCESS;
        }

    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Any methods or fields can be added to this static inner-Class to aid testing. To use it:
     * 1) Add method headers to the interface 'MainViewModel_TestHook'.
     * 2) Implement those methods in this class (Test_Hook).
     * 3) Call on those methods from a text class via the Interface MainViewModel_TestHook.
     *
     * Examples:
     * MainViewModel_TestHook.instance.setAsOnlyConcern();
     * MainViewModel_TestHook.instance.otherMethodName();
     */
    static class Test_Hook implements MainViewModel_TestHook {
        @Override
        public void clearConcernList() {
            concernList = new ArrayList<>();
        }

        @Override
        public void addConcern(ConcernWrapper concern) throws NullPointerException {
            concernList.add(concern);
        }

    }
}
