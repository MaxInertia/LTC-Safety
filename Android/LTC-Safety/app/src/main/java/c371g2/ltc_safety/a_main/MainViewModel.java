package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.appspot.ltc_safety.client.model.ConcernCollection;
import com.appspot.ltc_safety.client.model.OwnerToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.ReturnCode;
import c371g2.ltc_safety.local.ConcernWrapper;

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
    static MainViewModel observerInstance;

    /**
     * The list of all concerns stored on the device.
     * Retracted concerns persist.
     */
    private static ArrayList<ConcernWrapper> concernList;

    MainViewModel(@NonNull AbstractNetworkActivity activity) {
        this.activity = activity;
        observerInstance = this;
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
        // TODO: Initialize instance of ConcernUpdater, execute task
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
    private class ConcernUpdater extends AsyncTask<Void,Void,ReturnCode> {
        /**
         * Stores the backends response to the fetchConcerns request.
         */
        ConcernCollection concernCollection;

        @Override
        protected ReturnCode doInBackground(Void... params) {
            // TODO: Prepare fetch concerns request
            ArrayList<OwnerToken> tokens = getStoredOwnerTokens();

            //try {
                // TODO: Add fetch-API call here
                //return ReturnCode.SUCCESS;
            //} catch(IOException ioException) {
                // TODO: Display error message
                //return ReturnCode.IOEXCEPTION_THROWN_BY_API;
            //}
            return null;
        }

        @Override
        protected void onPostExecute(ReturnCode returnCode) {
            if(ReturnCode.SUCCESS.equals(returnCode)) {
                returnCode = addNewConcernStatuses(concernCollection);
            } else {
                //TODO: Display error message
            }
            submissionReturnCode = returnCode;
            signalLatch.countDown();
            assert(signalLatch.getCount() == 0);
        }

        /**
         * Retrieve the OwnerToken from each Concern in concernList.
         * @return List of OwnerTokens from each concern stored on the Device.
         */
        private @NonNull ArrayList<OwnerToken> getStoredOwnerTokens() {
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
        private ReturnCode addNewConcernStatuses(ConcernCollection concernCollection) {
            // TODO: Convert new ConcernStatus' into StatusWrapper instances
            // TODO: Add those wrappers to the end of the status list of the proper Concern
            // TODO: Overwrite previous version of concern in Device memory
            return null;
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
