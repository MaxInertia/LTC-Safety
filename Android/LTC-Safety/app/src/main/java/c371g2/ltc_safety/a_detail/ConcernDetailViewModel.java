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
import c371g2.ltc_safety.InfoDialog;
import c371g2.ltc_safety.a_main.ConcernRetractionObserver;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

/**
 * This class acts as an interface between the app view in the concern detail activity and the model.
 * It handles the concern retraction network task.
 *
 * Contains a static inner-class 'Test_Hook' to aid testing.
 *
 * @Invariants
 * - activity is never null and cannot be reassigned (final)
 * - concern is never null and cannot be reassigned (final)
 * @HistoryProperties
 * - The value of the signalLatch only decreases.
 */
class ConcernDetailViewModel extends AbstractNetworkViewModel{
    /**
     * Observer that is notified when a concern has been retracted.
     */
    final private ConcernRetractionObserver concernRetractionObserver;
    /**
     * The concern whose data is loaded into the layout.
     */
    final private ConcernWrapper concern;

    /**
     * The return code that results from an attempt to submit or retract a concern.
     * This variable is null until a concern submission or retraction is attempted.
     */
    RetractionReturnCode retractionReturnCode;

    /**
     * Package-private ConcernDetailViewModel constructor.
     * @param activity The calling activity.
     */
    ConcernDetailViewModel(@NonNull AbstractNetworkActivity activity, ConcernWrapper concern,
                           ConcernRetractionObserver observer) {
        this.activity = activity;
        this.concern = concern;
        this.concernRetractionObserver = observer;
    }

    /**
     * Retrieve reference of observer
     * @preconditions none
     * @modifies none
     * @return ConcernRetractionObserver
     */
    ConcernRetractionObserver getObserver() {
        return concernRetractionObserver;
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
    private class ConcernRetractor extends AsyncTask<Void, Void, RetractionReturnCode> {

        /**
         * The response received from the backend after retracting a concern.
         * Contains the retracted status.
         */
        UpdateConcernStatusResponse statusResponse;

        @Override
        protected RetractionReturnCode doInBackground(Void... params) {
            RetractionReturnCode returnCode;
            Client.Builder builder = new Client.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),
                    null
            );
            Client client = builder.build();

            try {
                statusResponse = client.retractConcern(concern.getOwnerToken()).execute();
                assert(statusResponse != null);
                returnCode = RetractionReturnCode.SUCCESS;
            } catch (IOException ioException) {
                returnCode = RetractionReturnCode.IOEXCEPTION_THROWN_BY_API;
            }

            return returnCode;
        }

        @Override
        protected void onPostExecute(RetractionReturnCode returnCode) {
            if(!activity.isFinishing() && activity.progressDialog!=null) {
                activity.progressDialog.cancel(); //progressDialog is not initialized for tests
            }

            if (returnCode.equals(RetractionReturnCode.SUCCESS)) {
                if (!activity.isFinishing() && !activity.isDestroyed()) {
                    InfoDialog.createInfoDialogue(
                            activity,
                            "Retraction successful",
                            "Your concern has been retracted",
                            null,
                            true
                    ).show();
                }
                concern.getStatuses().add( new StatusWrapper(
                        statusResponse.getStatus().getType(),
                        statusResponse.getStatus().getCreationDate().getValue()
                ));

                concernRetractionObserver.concernRetracted(
                        activity.getBaseContext(),
                        concern
                );

                ((ConcernDetailActivity)activity).setupConcernStatusList(concern.getStatuses());
            } else {
                if (!activity.isFinishing()) {
                    InfoDialog.createInfoDialogue(
                            activity,
                            "Error",
                            "Failed retracting your concern",
                            null,
                            true).show();
                }
            }

            retractionReturnCode = returnCode;
            signalLatch.countDown();
            assert(signalLatch.getCount() == 0);
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
    static class Test_Hook {
        public static ConcernWrapper getConcern(ConcernDetailViewModel viewModel) {
            return viewModel.concern;
        }
    }
}
