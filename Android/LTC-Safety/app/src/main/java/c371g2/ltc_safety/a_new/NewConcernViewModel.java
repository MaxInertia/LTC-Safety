package c371g2.ltc_safety.a_new;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.Reporter;
import com.appspot.ltc_safety.client.model.SubmitConcernResponse;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.NetworkActivity;
import c371g2.ltc_safety.a_main.ViewModelObserver;
import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This class acts as an interface between the app view in the new concern activity and the model.
 * It handles the submission of concerns to the systems backend.
 *
 * Contains a static inner-Class 'Test_Hook' to aid testing.
 *
 * @Invariants
 * - activity is never null after being initialized in the constructor.
 * @HistoryProperties
 * - The value of the signalLatch only decreases.
 */
class NewConcernViewModel {

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
     * Package-private NewConcernViewModel constructor.
     * @param activity The calling activity.
     */
    NewConcernViewModel(@NonNull NetworkActivity activity) {
        this.activity = activity;
        submissionReturnCode = null;
        signalLatch = new CountDownLatch(1);
        instance = this;
    }

    /**
     * Generates and submits a concern with the provided details to the Google Endpoints Backend.
     * @preconditions none
     * @modifies If the input is valid: A new LocalConcern is added to the list of concerns, and  the App
     *  view changes to the ConcernDetailActivity (or MainActivity?). Validity of input is determined
     *  by the classes that extend Verifier.
     * @param concernType The type of concern being submitted.
     * @param actionsTaken The actions taken for the concern.
     * @param facilityName The facility the concern is about.
     * @param reporterName The first and last name of the reporter.
     * @param emailAddress An email address the reporter can be contacted at.
     * @param phoneNumber A phone number that the reporter can be contacted at.
     */
    ReturnCode[] submitConcern(String concernType, String actionsTaken, String facilityName, String roomName,
                             String reporterName, String emailAddress, String phoneNumber) {

        // Use verifier-classes to confirm input is sufficient to submit as concern
        ReturnCode[] returnCodes = new ReturnCode[4];
        int invalidFields = 0;

        if ( !((new NameVerifier()).verify(reporterName)) ) {
            returnCodes[invalidFields] = ReturnCode.INVALID_NAME;
            invalidFields++;
        }
        if ( !((new PhoneNumberVerifier()).verify(phoneNumber)) && !((new EmailAddressVerifier()).verify(emailAddress)) ) {
            returnCodes[invalidFields] = ReturnCode.INVALID_PHONE_AND_EMAIL;
            System.out.println();
            invalidFields++;
        }
        if ( !(concernType.length()>0) ) { // TODO: make concernVerifier class?
            returnCodes[invalidFields] = ReturnCode.NO_CONCERN_TYPE;
            invalidFields++;
        }
        if ( !(facilityName.length()>0) ) { // TODO: make facilityVerifier class?
            returnCodes[invalidFields] = ReturnCode.NO_FACILITY_NAME;
            invalidFields++;
        }
        if(invalidFields>0) {
            return returnCodes;
        }

        // Use 'client' API here to generate and submit concern
        ConcernSubmitter networkTask = new ConcernSubmitter();
        networkTask.data = buildConcernData(
                concernType,
                actionsTaken,
                facilityName,
                roomName,
                reporterName,
                emailAddress,
                phoneNumber
        );
        assert(networkTask.data != null);
        Client.Builder builder = new Client.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                null
        );
        assert(builder != null);
        networkTask.client = builder.build();
        assert(networkTask.client != null);
        networkTask.execute();

        returnCodes[0] = ReturnCode.VALID_INPUT;
        return returnCodes;
    }

    private ConcernData buildConcernData(String concernType, String actionsTaken, String facilityName, String roomName,
                                         String reporterName, String emailAddress, String phoneNumber) {
        ConcernData concernData = new ConcernData();
        concernData.setConcernNature(concernType);
        concernData.setActionsTaken(actionsTaken);

        Location facility = new Location();
        facility.setFacilityName(facilityName);
        facility.setRoomName(roomName);
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
     * @Invariants none
     * @HistoryProperties none
     */
    private class ConcernSubmitter extends AsyncTask<Void,Void,ReturnCode> {
        /**
         * The response received from the backend after submitting a concern.
         * Contains the concern and an owner token.
         */
        private SubmitConcernResponse response;
        /**
         * The Client instance, used to send a concern to the backend.
         */
        private Client client;
        /**
         * The object containing all concern details input by the user.
         */
        private ConcernData data;

        @Override
        protected ReturnCode doInBackground(Void... params) {
            response = null;
            ReturnCode returnCode = ReturnCode.SUCCESS;
            try{
                response = client.submitConcern(data).execute();
                assert(response != null);
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
            if(!activity.isFinishing() && activity.progressDialog!=null) {
                activity.progressDialog.cancel(); //progressDialog is not initialized for tests
            }
            if(returnCode!=ReturnCode.IOEXCEPTION_THROWN_BY_API) {
                assert(response != null);
                // Store concern and token on device
                ConcernWrapper concern = new ConcernWrapper(response.getConcern(), response.getOwnerToken());
                ViewModelObserver.instance.newConcernSubmitted(activity.getBaseContext(), concern);

                // Inform user that the concern was successfully submitted
                if(!activity.isFinishing() && !activity.isDestroyed()) {
                    activity.displayInfoDialogue(
                            "Submission successful",
                            "Your concern has been submitted",
                            null,
                            true
                    );
                }

            } else {
                // LocalConcern submission failed, possible cause: No internet access on device
                if(!activity.isFinishing()) {
                    activity.displayInfoDialogue(
                            "Error",
                            "Failed submitting your concern",
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
     * A reference to the current instance of NewConcernViewModel for use with the Test_Hook class.
     * This will be null unless NewConcernViewActivity was launched.
     */
    private static NewConcernViewModel instance;

    /**
     * Used to set the instance to null when the activity is being destroyed.
     * This is not to be called anywhere except NewConcernActivity.onDestroy()
     * @preconditions none
     * @modifies NewConcernViewModel is set to null.
     */
    void nullifyInstance() {
        instance = null;
    }

    /**
     * Any methods or fields can be added to this static inner-Class to aid testing. Feel free to use
     * the field 'instance' here. To use this class do the following:
     * 1) Add method headers to the interface 'NewConcernViewModel_TestHook'.
     * 2) Implement those methods in this class (Test_Hook).
     * 3) Call on those methods from a text class via the Interface NewConcernViewModel_TestHook.
     *
     * Examples:
     * NewConcernViewModel_TestHook.instance.submitConcern();
     * NewConcernViewModel_TestHook.instance.otherMethodName();
     */
    static class Test_Hook implements NewConcernViewModel_TestHook {

        @Override
        public boolean submitConcern(@NonNull NetworkActivity testActivity, String concernType, String actionsTaken, String facilityName,
                                  String roomName, String reporterName, String emailAddress, String phoneNumber) throws InterruptedException {

            NewConcernViewModel ncvm = new NewConcernViewModel(testActivity);
            ReturnCode[] returnCode = ncvm.submitConcern(
                    concernType,
                    actionsTaken,
                    facilityName,
                    roomName,
                    reporterName,
                    emailAddress,
                    phoneNumber
            );

            // Inputs were accepted by the Verifier classes
            if(returnCode[0]==ReturnCode.VALID_INPUT) {
                // Wait for network thread to finish
                ncvm.signalLatch.await(20, TimeUnit.SECONDS);
                // Submission successful, return true.
                if(ncvm.submissionReturnCode==ReturnCode.SUCCESS) {
                    return true;
                }
            }

            // Submission failed.
            return false;
        }
    }
}
