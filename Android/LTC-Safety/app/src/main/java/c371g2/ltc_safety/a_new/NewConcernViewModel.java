package c371g2.ltc_safety.a_new;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.a_main.ConcernSubmissionObserver;
import c371g2.ltc_safety.a_main.MainActivity;
import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This class acts as an interface between the app view in the new concern activity and the model.
 * It handles the submission of concerns to the systems backend.
 *
 * Contains a static inner-Class 'Test_Hook' to aid testing.
 *
 * @Invariants
 * - Neither activity (from superclass) nor concernSubmissionObserver are ever null after being
 * initialized in the constructor.
 * - concernSubmissionObserver always contains the same instance (final).
 * @HistoryProperties
 * - The value of the signalLatch only decreases.
 */
class NewConcernViewModel extends AbstractNetworkViewModel {
    /**
     * Observer that is notified when a concern has been submitted.
     */
    final private ConcernSubmissionObserver concernSubmissionObserver;
    /**
     * Reference to thread that is used to perform the submit network operation. Only initialized
     * when a concern is submitted, set to null when the thread ends. Used to help prevent memory
     * leaks.
     */
    private ConcernSubmitter networkTask;
    /**
     * The return code that results from an attempt to submit or retract a concern.
     * This variable is null until a concern submission or retraction is attempted.
     */
    SubmissionReturnCode submissionReturnCode;

    /**
     * Package-private NewConcernViewModel constructor.
     * @param activity The calling activity.
     */
    NewConcernViewModel(@NonNull AbstractNetworkActivity activity,
                        @NonNull ConcernSubmissionObserver observer) {
        this.activityWeakReference = new WeakReference<>(activity);
        this.concernSubmissionObserver = observer;
        submissionReturnCode = null;
    }

    @Override
    protected void stopNetworkThread() {
        if(networkTask != null) {
            networkTask.cancel(true);
            networkTask.data = null;
            networkTask.client = null;
            networkTask.response = null;
            networkTask.viewModel = null;
            networkTask = null;
        }
    }

    /**
     * Retrieve reference of observer
     * @preconditions none
     * @modifies none
     * @return ConcernSubmissionObserver
     */
    ConcernSubmissionObserver getObserver() {
        return concernSubmissionObserver;
    }

    /**
     * Generates and submits a concern with the provided details to the Google Endpoints Backend.
     * @preconditions none
     * @modifies If the input is valid: A new LocalConcern is added to the list of concerns, and  the App
     *  view changes to the ConcernDetailActivity (or MainActivity?). Validity of input is determined
     *  by the classes that extend Verifier.
     * @param concernType The type of concern being submitted.
     * @param actionsTaken The actions taken for the concern.
     * @param description A description of the concern.
     * @param facilityName The facility the concern is about.
     * @param roomName The room the event of concern took place.
     * @param reporterName The first and last name of the reporter.
     * @param emailAddress An email address the reporter can be contacted at.
     * @param phoneNumber A phone number that the reporter can be contacted at.
     */
    SubmissionReturnCode[] submitConcern(String concernType,
                                         String actionsTaken,
                                         String description,
                                         String facilityName,
                                         String roomName,
                                         String reporterName,
                                         String emailAddress,
                                         String phoneNumber) {

        // Use verifier-classes to confirm input is sufficient to submit as concern
        SubmissionReturnCode[] returnCodes = new SubmissionReturnCode[4];
        int invalidFields = 0;

        if ( !((new NameVerifier()).verify(reporterName)) ) {
            returnCodes[invalidFields] = SubmissionReturnCode.INVALID_NAME;
            invalidFields++;
        }
        if ( !((new PhoneNumberVerifier()).verify(phoneNumber)) && !((new EmailAddressVerifier()).verify(emailAddress)) ) {
            returnCodes[invalidFields] = SubmissionReturnCode.INVALID_PHONE_AND_EMAIL;
            System.out.println();
            invalidFields++;
        }
        if ( !(concernType.length()>0) ) { // TODO: make concernVerifier class?
            returnCodes[invalidFields] = SubmissionReturnCode.NO_CONCERN_TYPE;
            invalidFields++;
        }
        if ( !(facilityName.length()>0) ) { // TODO: make facilityVerifier class?
            returnCodes[invalidFields] = SubmissionReturnCode.NO_FACILITY_NAME;
            invalidFields++;
        }
        if(invalidFields>0) {
            return returnCodes;
        }

        // Use 'client' API here to generate and submit concern
        networkTask = new ConcernSubmitter();
        networkTask.viewModel = this;
        networkTask.data = buildConcernData(
                concernType,
                actionsTaken,
                description,
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

        returnCodes[0] = SubmissionReturnCode.VALID_INPUT;
        return returnCodes;
    }

    /**
     * Convenience method for creating a ConcernData instance.
     * @preconditions none
     * @modifies nothing
     * @param concernType The type of concern being submitted.
     * @param actionsTaken The actions taken for the concern.
     * @param description A description of the concern.
     * @param facilityName The facility the concern is about.
     * @param roomName The room the event of concern took place.
     * @param reporterName The first and last name of the reporter.
     * @param emailAddress An email address the reporter can be contacted at.
     * @param phoneNumber A phone number that the reporter can be contacted at.
     * @return Instance of ConcernData class containing the values provided.
     */
    private ConcernData buildConcernData(String concernType, String actionsTaken, String description, String facilityName,
                                         String roomName, String reporterName, String emailAddress, String phoneNumber) {

        ConcernData concernData = new ConcernData();
        concernData.setConcernNature(concernType);
        concernData.setActionsTaken(actionsTaken);
        concernData.setDescription(description);

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
    private static class ConcernSubmitter extends AsyncTask<Void,Void,SubmissionReturnCode> {
        /**
         * Reference to the NewConcernViewModel tied to the activity.
         */
        NewConcernViewModel viewModel;
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
        protected SubmissionReturnCode doInBackground(Void... params) {
            response = null;
            SubmissionReturnCode returnCode = SubmissionReturnCode.SUCCESS;
            try{
                response = client.submitConcern(data).execute();
                assert(response != null);
            } catch(IOException ioException) {
                returnCode = SubmissionReturnCode.IOEXCEPTION_THROWN_BY_API;
            } finally {
                data = null;
                client = null;
            }
            return returnCode;
        }

        @Override
        protected void onPostExecute(SubmissionReturnCode returnCode) {
            viewModel.networkTask = null;
            final AbstractNetworkActivity activity = viewModel.activityWeakReference.get();

            if(returnCode!=SubmissionReturnCode.IOEXCEPTION_THROWN_BY_API) {
                assert(response != null);
                // Store concern and token on device
                ConcernWrapper concern = new ConcernWrapper(response.getConcern(), response.getOwnerToken());
                viewModel.concernSubmissionObserver.concernSubmitted(activity.getBaseContext(), concern);

                // Inform user that the concern was successfully submitted
                if(!activity.isFinishing() && activity.progressDialog!=null) {
                    activity.progressDialog.cancel();
                    AlertDialog.Builder b = new AlertDialog.Builder(activity);
                    b.setMessage("Your concern has been submitted");
                    activity.progressDialog = b.create();
                    activity.progressDialog.setCancelable(true);
                    activity.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Intent i = new Intent(activity, MainActivity.class);
                            i.putExtra("observer",viewModel.concernSubmissionObserver);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(i);
                            activity.finish();
                        }
                    });
                    activity.progressDialog.show();
                }

            } else if(!activity.isFinishing() && activity.progressDialog!=null) {
                // LocalConcern submission failed, possible cause: No internet access on device
                activity.progressDialog.cancel();
                AlertDialog.Builder b = new AlertDialog.Builder(activity);
                b.setMessage("Failed submitting your concern");
                activity.progressDialog = b.create();
                activity.progressDialog.setCancelable(true);
                activity.progressDialog.show();
            }

            viewModel.submissionReturnCode = returnCode;
            response = null;
            client = null;
            data = null;
            viewModel.signalLatch.countDown();
            assert(viewModel.signalLatch.getCount() == 0);
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
    public static class Test_Hook {

        public static boolean submitConcernOutsideWithConcern(NewConcernViewModel newConcernViewModel, @NonNull ConcernWrapper concern) throws InterruptedException {
            SubmissionReturnCode[] returnCode = newConcernViewModel.submitConcern(
                    concern.getConcernType(),
                    concern.getActionsTaken(),
                    concern.getDescription(),
                    concern.getFacilityName(),
                    concern.getRoomName(),
                    concern.getReporterName(),
                    concern.getReporterEmail(),
                    concern.getReporterPhone()
            );

            // Inputs were accepted by the Verifier classes
            if(returnCode[0]==SubmissionReturnCode.VALID_INPUT) {
                // Wait for network thread to finish
                newConcernViewModel.signalLatch.await(20, TimeUnit.SECONDS);
                // Submission successful, return true.
                if(newConcernViewModel.submissionReturnCode==SubmissionReturnCode.SUCCESS) {
                    return true;
                }
            }

            // Submission failed.
            return false;
        }

    }
}
