package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Collections;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

/**
 * This class acts as an interface between the app view in the main activity and the model. It
 * contains the list of Concerns submitted from this device in concernList. The ConcernRetractionObserver
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
class MainViewModel extends AbstractNetworkViewModel implements ConcernRetractionObserver, ConcernSubmissionObserver, Serializable {

    /**
     * The return code that results from an attempt to submit or retract a concern.
     * This variable is null until a concern submission or retraction is attempted.
     */
    FetchReturnCode fetchReturnCode;

    /**
     * The list of all concerns stored on the device.
     * Retracted concerns persist.
     */
    private ArrayList<ConcernWrapper> concernList;

    /**
     * Constructor for Testing
     */
    private MainViewModel(){
        concernList = new ArrayList<>();
    }

    /**
     * Package-private MainViewModel constructor.
     * @param activity The main activity
     */
    MainViewModel(@NonNull AbstractNetworkActivity activity) {
        concernList = DeviceStorage.loadConcerns(activity.getBaseContext());
        assert(concernList!=null);
        this.activity = activity;
        Toast.makeText(activity,"MainViewModel Constructed",Toast.LENGTH_SHORT).show();
        updateConcerns();
    }

    /**
     * Retrieves the list of previously submitted concerns. The list is sorted with the most recent
     * concerns at lower indices.
     * @preconditions initialize() was called prior to this method.
     * @modifies concernList is sorted; more recent concerns at lower indices.
     * @return The sorted list of previously submitted concerns
     */
    ArrayList<ConcernWrapper> getSortedConcernList(){
        assert(concernList != null);
        Collections.sort(concernList);
        return concernList;
    }

    /**
     * Uses the inner-class ConcernUpdater to update the list of concern statuses for each concern
     * stored on this device.
     * @preconditions Device has internet access
     * @modifies
     * If any concerns have changed and an internet connection is available:
     *  - The updated version replaces the old concern in the concernList
     *  - The updated version overwrites the old version in device memory
     * If no concerns have changed: Nothing happens.
     * @return true if concern fetching has started, else false
     */
    boolean updateConcerns() {
        if(concernList.size()>0) {
            ConcernUpdater networkTask = new ConcernUpdater();
            networkTask.execute();
            return true;
        } else {
            fetchReturnCode = FetchReturnCode.NO_CONCERNS;
            return false;
        }
    }

    /**
     * Retrieve concern in concernList at index provided
     * @preconditions none
     * @modifies nothing
     * @param index index of concern. index >= 0
     * @return concern at index provided
     */
    ConcernWrapper getConcernAtIndex(int index) {
        return concernList.get(index);
    }

    @Override
    public void concernSubmitted(@NonNull Context context, @NonNull ConcernWrapper newConcern) {
        DeviceStorage.saveConcern(context, newConcern);
        Toast.makeText(context,"Size before: "+concernList.size(),Toast.LENGTH_SHORT).show();
        concernList.add(newConcern);
        Toast.makeText(context,"Size after: "+concernList.size(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void concernRetracted(@NonNull Context context, @NonNull ConcernWrapper concern) {
        int index = 0;
        ConcernWrapper oldConcern = null;
        for(index = 0; index<concernList.size(); index++) {
            oldConcern = concernList.get(index);
            if(oldConcern.getOwnerToken().equals(concern.getOwnerToken())){
                break;
            }
        }

        assert(oldConcern != null);

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
        public void clearConcernList(MainViewModel mainViewModel) {
            mainViewModel.concernList = new ArrayList<>();
        }

        @Override
        public MainViewModel getMainViewModelInstance() {
            return new MainViewModel();
        }

        @Override
        public ConcernWrapper getConcern(ConcernRetractionObserver mainViewModel, int index) {
            return ((MainViewModel)mainViewModel).concernList.get(index);
        }

        @Override
        public void initializeConcernList(MainViewModel mainViewModel) {
            mainViewModel.concernList = new ArrayList<>();
        }

        static void addConcern(MainViewModel viewModel, ConcernWrapper concern) throws NullPointerException {
            viewModel.concernList.add(concern);
        }

        static void updateConcerns(MainViewModel viewModel) {
            viewModel.updateConcerns();
        }

    }
}
