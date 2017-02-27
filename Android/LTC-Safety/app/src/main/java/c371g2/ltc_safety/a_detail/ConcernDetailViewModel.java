package c371g2.ltc_safety.a_detail;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.UpdateConcernStatusResponse;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import c371g2.ltc_safety.Utilities;
import c371g2.ltc_safety.a_main.ViewModelObserver;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

import static c371g2.ltc_safety.a_detail.ReturnCode.IOEXCEPTION;
import static c371g2.ltc_safety.a_detail.ReturnCode.NOERROR;
import static c371g2.ltc_safety.a_detail.ReturnCode.SUCCESS;

/**
 * This class acts as an interface between the app view in the concern detail activity and the model.
 * @Invariants none
 * @HistoryProperties none
 */
class ConcernDetailViewModel {

    private ConcernDetailActivity activity;
    final CountDownLatch signalLatch;
    ReturnCode submissionReturnCode;

    private ConcernWrapper concern;
    private int index;

    ConcernDetailViewModel(ConcernDetailActivity activity) {
        signalLatch = new CountDownLatch(1);
        this.activity = activity;
    }

    /**
     * Retrieve concern from MainViewModel
     *
     * @param index index of requested concern
     * @return concern at specified index
     */
    ConcernWrapper getConcern(int index) {
        this.index = index;
        this.concern = ViewModelObserver.instance.getConcernAtIndex(index);
        return concern;
    }

    /**
     * Retract the concern currently being viewed on the ConcernDetailActivity
     */
    protected void retractConcern() {
        (new ConcernRetractor()).execute();
    }

    /**
     * This class is responsible for utilizing the network connection to send a concern retraction
     * request to the database via the Client-API. The network operation is performed on a separate
     * thread.
     */
    private class ConcernRetractor extends AsyncTask<Void, Void, ReturnCode> {

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
                    Utilities.displayInfoDialogue(
                            activity,
                            "Retract successful",
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
                    Utilities.displayInfoDialogue(
                            activity,
                            "Error",
                            "Failed retracting concern",
                            null,
                            true);
                }
            }

            submissionReturnCode = returnCode;
            signalLatch.countDown();
        }

    }

}
