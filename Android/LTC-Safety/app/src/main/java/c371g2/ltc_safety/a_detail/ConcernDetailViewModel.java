package c371g2.ltc_safety.a_detail;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.UpdateConcernStatusResponse;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import c371g2.ltc_safety.NetworkActivity;
import c371g2.ltc_safety.a_main.ViewModelObserver;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

import static c371g2.ltc_safety.a_detail.ReturnCode.IOEXCEPTION;
import static c371g2.ltc_safety.a_detail.ReturnCode.NOERROR;
import static c371g2.ltc_safety.a_detail.ReturnCode.SUCCESS;

/**
 * This class acts as an interface between the app view in the concern detail activity and the model.
 * It handles the concern retraction network task.
 *
 * @Invariants
 * - index is in the range [0, MainViewModel.concernList.size()-1]
 * - activity is never null after being initialized in the constructor.
 * @HistoryProperties
 * - The value of the signalLatch only decreases.
 */
class ConcernDetailViewModel {

    /**
     * Reference to the Activity class which initialized this class.
     */
    final private NetworkActivity activity;
    /**
     * A class used to inform a test class that a network operation has been completed.
     * This variable is null until a concern submission is attempted with valid inputs.
     */
    final CountDownLatch signalLatch;
    /**
     * The return code that results from an attempt to submit a concern.
     * This variable is null until a concern submission is attempted.
     */
    ReturnCode submissionReturnCode;
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
    ConcernDetailViewModel(@NonNull NetworkActivity activity) {
        this.activity = activity;
        signalLatch = new CountDownLatch(1);
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
                returnCode = NOERROR;
            } catch (IOException ioException) {
                returnCode = IOEXCEPTION;
            }

            return returnCode;
        }

        @Override
        protected void onPostExecute(ReturnCode returnCode) {
            if(!activity.isFinishing() && activity.progressDialog!=null) {
                activity.progressDialog.cancel(); //progressDialog is not initialized for tests
            }

            if (returnCode.equals(NOERROR)) {
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
                returnCode = SUCCESS;

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
}
