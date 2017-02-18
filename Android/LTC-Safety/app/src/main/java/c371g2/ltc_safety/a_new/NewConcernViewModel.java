package c371g2.ltc_safety.a_new;

import android.os.AsyncTask;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.Reporter;
import com.appspot.ltc_safety.client.model.SubmitConcernResponse;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import c371g2.ltc_safety.a_main.ViewModelObserver;
import c371g2.ltc_safety.local.Concern;

/**
 * This class acts as an interface between the app view in the new concern activity and the model.
 * @Invariants none
 * @HistoryProperties none
 */
class NewConcernViewModel {

    private NewConcernActivity activity;
    final CountDownLatch signalLatch;
    ReturnCode submissionReturnCode;

    NewConcernViewModel(NewConcernActivity activity) {
        signalLatch = new CountDownLatch(1);
        submissionReturnCode = null;
        this.activity = activity;
    }

    /**
     * Generates and submits a concern with the provided details to the Google Endpoints Backend.
     * @preconditions none
     * @modifies If the input is valid: A new Concern is added to the list of concerns, and  the App
     *  view changes to the ConcernDetailActivity (or MainActivity?). Validity of input is determined
     *  by the classes that extend Verifier.
     * @param concernType The type of concern being submitted.
     * @param actionsTaken The actions taken for the concern.
     * @param facilityName The facility the concern is about.
     * @param reporterName The first and last name of the reporter.
     * @param emailAddress An email address the reporter can be contacted at.
     * @param phoneNumber A phone number that the reporter can be contacted at.
     */
    ReturnCode submitConcern(String concernType, String actionsTaken, String facilityName,
                             String reporterName, String emailAddress, String phoneNumber) {

        // TODO: Use verifier-classes to confirm input is sufficient to submit as concern
        if ( !((new NameVerifier()).verify(reporterName)) ) return ReturnCode.INVALID_NAME;
        if ( !(concernType.length()>0) ) return ReturnCode.NO_CONCERN_TYPE; // TODO: make concernVerifier class
        if ( !((new PhoneNumberVerifier()).verify(phoneNumber)) && !((new EmailAddressVerifier()).verify(emailAddress)) ) {
            return ReturnCode.INVALID_PHONE_AND_EMAIL;
        }

        // TODO: Use 'client' API here to generate and submit concern
        ConcernSubmitter networkTask = new ConcernSubmitter();
        networkTask.data = buildConcernData(
                concernType,
                actionsTaken,
                facilityName,
                reporterName,
                emailAddress,
                phoneNumber
        );
        Client.Builder builder = new Client.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                null
        );
        networkTask.client = builder.build();
        networkTask.execute();

        return ReturnCode.VALID_INPUT;
    }

    private ConcernData buildConcernData(String concernType, String actionsTaken, String facilityName,
                                         String reporterName, String emailAddress, String phoneNumber) {
        ConcernData concernData = new ConcernData();
        concernData.setConcernNature(concernType);
        concernData.setActionsTaken(actionsTaken);

        Location facility = new Location();
        facility.setFacilityName(facilityName);
        concernData.setLocation(facility);

        Reporter reporter = new Reporter();
        reporter.setName(reporterName);
        reporter.setPhoneNumber(phoneNumber);
        reporter.setEmail(emailAddress);
        concernData.setReporter(reporter);

        return concernData;
    }


    /**
     * This class is responsible for utilizing the network connection to send the concern to the
     * database via the Client-API. The network operation is performed on a separate thread.
     */
    private class ConcernSubmitter extends AsyncTask<Void,Void,ReturnCode> {

        private SubmitConcernResponse response;
        private Client client;
        private ConcernData data;

        @Override
        protected ReturnCode doInBackground(Void... params) {
            response = null;
            ReturnCode returnCode = ReturnCode.SUCCESS;
            try{
                response = client.submitConcern(data).execute();
            } catch(IOException ioException) {
                returnCode = ReturnCode.IOEXCEPTION_THROWN_BY_API;
            } finally {
                data = null;
                client = null;
            }
            return returnCode;
        }

        @Override
        protected void onPostExecute(ReturnCode returnCode) {
            if(returnCode!=ReturnCode.IOEXCEPTION_THROWN_BY_API && response!=null) {
                // Store concern and token on device
                Concern concern = new Concern(response.getConcern(), response.getOwnerToken());
                ViewModelObserver.instance.newConcernSubmitted(concern);

                // Inform user that the concern was successfully submitted
                if(!activity.isFinishing()) activity.displayInfoDialogue(null,"Concern Submitted");

            } else if(returnCode==ReturnCode.IOEXCEPTION_THROWN_BY_API && response!=null) {
                if(!activity.isFinishing()) activity.displayInfoDialogue("IOException Thrown","response was initialized");

            } else if(returnCode==ReturnCode.IOEXCEPTION_THROWN_BY_API){
                if(!activity.isFinishing()) activity.displayInfoDialogue("IOException Thrown","response is NULL");

            } else if(returnCode==null) {
                if(!activity.isFinishing()) activity.displayInfoDialogue("Response was NULL","No IOException thrown.");
            }
            submissionReturnCode = returnCode;
            signalLatch.countDown();
        }
    }
}
