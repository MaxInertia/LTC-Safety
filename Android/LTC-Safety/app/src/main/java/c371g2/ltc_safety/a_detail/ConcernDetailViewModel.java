package c371g2.ltc_safety.a_detail;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.UpdateConcernStatusResponse;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.ReturnCode;
import c371g2.ltc_safety.a_main.ViewModelObserver;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

/**
 * This class acts as an interface between the app view in the concern detail activity and the model.
 * It handles the concern retraction network task.
 *
 * Contains a static inner-class 'Test_Hook' to aid testing.
 *
 * @Invariants
 * - index is in the range [0, MainViewModel.concernList.size()-1]
 * - activity is never null after being initialized in the constructor.
 * @HistoryProperties
 * - The value of the signalLatch only decreases.
 */
class ConcernDetailViewModel extends AbstractNetworkViewModel{
    /**
     * The concern whose data is loaded into the layout.
     */
    private ConcernWrapper concern;
    /**
     * The index the loaded concern is stored at inside MainViewModel.concernList.
     */
    private int index;

    /**
     * Package-private ConcernDetailViewModel constructor.
     * @param activity The calling activity.
     */
    ConcernDetailViewModel(@NonNull AbstractNetworkActivity activity) {
        this.activity = activity;
        instance = this;
    }

    /**
     * Retrieve concern at the provided index of concernList from MainViewModel.
     * @preconditions index is between zero and MainViewModel.concernList.size()
     * @modifies
     * - 'this.concern' and 'this.index' are initialized.
     * - The index of 'this.concern' in MainViewModel.concernList is 'this.index'
     * @param index index of requested concern.
     * @return concern at the provided index
     */
    ConcernWrapper getConcern(int index) {
        this.index = index;
        assert(index > -1);
        this.concern = ViewModelObserver.instance.getConcernAtIndex(index);
        assert(concern != null);
        return concern;
    }

    /**
     * Retract the concern currently being viewed on the ConcernDetailActivity
     * @preconditions
     * - getConcern was called for this instance of the class before this method.
     * @modifies
     * - If the retraction was successful: A "RETRACTED" state is added to 'this.concern'
     */
    void retractConcern() {
        (new ConcernRetractor()).execute();
    }

    /**
     * This class is responsible for utilizing the network connection to send a concern retraction
     * request to the database via the Client-API. The network operation is performed on a separate
     * thread.
     * @Invariants none
     * @HistoryProperties none
     */
    private class ConcernRetractor extends AsyncTask<Void, Void, ReturnCode> {

        /**
         * The response received from the backend after retracting a concern.
         * Contains the retracted status.
         */
        UpdateConcernStatusResponse statusResponse;

        @Override
        protected ReturnCode doInBackground(Void... params) {
            ReturnCode returnCode;
            Client.Builder builder = new Client.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),
                    null
            );
            Client client = builder.build();

            try {
                statusResponse = client.retractConcern(concern.getOwnerToken()).execute();
                assert(statusResponse != null);
                returnCode = ReturnCode.SUCCESS;
            } catch (IOException ioException) {
                returnCode = ReturnCode.IOEXCEPTION_THROWN_BY_API;
            }

            return returnCode;
        }

        @Override
        protected void onPostExecute(ReturnCode returnCode) {
            if(!activity.isFinishing() && activity.progressDialog!=null) {
                activity.progressDialog.cancel(); //progressDialog is not initialized for tests
            }

            if (returnCode.equals(ReturnCode.SUCCESS)) {
                if (!activity.isFinishing() && !activity.isDestroyed()) {
                    activity.displayInfoDialogue(
                            "Retraction successful",
                            "Your concern has been retracted",
                            null,
                            true
                    );
                }
                concern.getStatuses().add( new StatusWrapper(
                        statusResponse.getStatus().getType(),
                        statusResponse.getStatus().getCreationDate().getValue()
                ));

                ViewModelObserver.instance.concernRetracted(
                        activity.getBaseContext(),
                        concern,
                        index
                );

            } else {
                if (!activity.isFinishing()) {
                    activity.displayInfoDialogue(
                            "Error",
                            "Failed retracting your concern",
                            null,
                            true);
                }
            }

            submissionReturnCode = returnCode;
            signalLatch.countDown();
            assert(signalLatch.getCount() == 0);
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * A reference to the current instance of ConcernDetailViewModel for use with the Test_Hook class.
     * This will be null unless ConcernDetailViewActivity was launched.
     */
    private static ConcernDetailViewModel instance;

    /**
     * Used to set the instance to null when the activity is being destroyed.
     * This is not to be called anywhere except ConcernDetailActivity.onDestroy()
     * @preconditions none
     * @modifies ConcernDetailViewModel is set to null.
     */
    void nullifyInstance() {
        instance = null;
    }

    /**
     * Any methods or fields can be added to this static inner-Class to aid testing. Feel free to use
     * the field 'instance' here. To use this class do the following:
     * 1) Add method headers to the interface 'ConcernDetailViewModel_TestHook'.
     * 2) Implement those methods in this class (Test_Hook).
     * 3) Call on those methods from a text class via the Interface ConcernDetailViewModel_TestHook.
     *
     * Examples:
     * ConcernDetailViewModel_TestHook.instance.someMethod();
     * ConcernDetailViewModel_TestHook.instance.otherMethod();
     */
    static class Test_Hook implements ConcernDetailViewModel_TestHook {}
}
