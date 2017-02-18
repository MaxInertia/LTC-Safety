package c371g2.ltc_safety.a_new;

import android.os.AsyncTask;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.Reporter;
import com.appspot.ltc_safety.client.model.SubmitConcernResponse;

import java.util.concurrent.CountDownLatch;


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
     * @param facultyName The facility the concern is about.
     * @param reporterName The first and last name of the reporter.
     * @param emailAddress An email address the reporter can be contacted at.
     * @param phoneNumber A phone number that the reporter can be contacted at.
     * @return The ReturnCode for the submit concern action.
     */
    ReturnCode submitConcern(String concernType, String actionsTaken, String facultyName,
                              String reporterName, String emailAddress, String phoneNumber) {
        // TODO: Use verifier-classes to confirm input is sufficient to submit as concern
        // TODO: Use 'client' API here to generate and submit concern
        // TODO: Use ViewModelObserver interface to update concern list
        return null;
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
            return null;
        }

        @Override
        protected void onPostExecute(ReturnCode returnCode) {

        }
    }
}
