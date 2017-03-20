package c371g2.ltc_safety.a_new;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.InfoDialog;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_main.ConcernSubmissionObserver;
import c371g2.ltc_safety.a_main.MainActivity;
import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This activity displays a form with various fields for specifying a safety concern. A concern with
 * sufficient details can be submitted to the database from this activity.
 *
 * Activity: ~ View-Controller
 *
 * @Invariants none
 * @HistoryProperties
 * - For a given instance of this class after onCreate has been called, each field will contain a
 * unchanging and non-null reference, effectively final.
 */
public class NewConcernActivity extends AbstractNetworkActivity {
    /**
     * Reference to the View-Model for this Activity. All functionality in this activity that is
     * not directly related to the UI is encapsulated in the View-Model.
     */
    private NewConcernViewModel newConcernViewModel;
    /**
     * Reference to the button used to submit a concern.
     */
    private Button submitConcernButton;
    /**
     * The input field for the Reporters name.
     */
    private EditText nameField;
    /**
     * The input field for the Reporters phone number.
     */
    private EditText phoneNumberField;
    /**
     * The input field for the Reporters email address.
     */
    private EditText emailAddressField;
    /**
     * The input field for the name of the long term care facility.
     * Possible values located at 'R.array.longtermcare_facilities'
     */
    private TextView facilityField;
    /**
     * The input field for a room within the aforementioned LTC facility.
     */
    private EditText roomField;
    /**
     * The input field for the nature of the concern.
     * Possible values located at 'R.array.concern_types'
     */
    private TextView concernNatureField;
    /**
     * The input field for the actions taken in response to the concern.
     */
    private EditText actionsTakenField;
    /**
     * A description of the concern.
     */
    private EditText concernDescriptionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_concern);
        newConcernViewModel = new NewConcernViewModel(
                this,
                (ConcernSubmissionObserver) getIntent().getSerializableExtra("observer")
        );

        nameField = (EditText) findViewById(R.id.newConcern_nameTextField);
        assert(nameField != null);
        phoneNumberField = (EditText) findViewById(R.id.newConcern_phoneNumberField);
        assert(phoneNumberField != null);
        emailAddressField = (EditText) findViewById(R.id.newConcern_emailTextField);
        assert(emailAddressField != null);
        roomField = (EditText) findViewById(R.id.newConcern_roomNumberField);
        assert(roomField != null);
        actionsTakenField = (EditText) findViewById(R.id.newConcern_actionsTakenField);
        assert(actionsTakenField != null);
        concernDescriptionField = (EditText) findViewById(R.id.newConcern_descriptionField);
        assert(concernDescriptionField != null);
        submitConcernButton = (Button) findViewById(R.id.newConcern_submitConcernButton);
        assert(submitConcernButton != null);
        submitConcernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitConcernButtonAction();
            }
        });

        concernNatureField = (TextView) findViewById(R.id.newConcern_concernNatureChooser);
        ChooserView.setup(
                concernNatureField,
                "Select LocalConcern Nature",
                getResources().getStringArray(R.array.concern_types)
        );

        facilityField = (TextView) findViewById(R.id.newConcern_facilityChooser);
        ChooserView.setup(
                facilityField,
                "Select Facility",
                getResources().getStringArray(R.array.longtermcare_facilities)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If the device has no network connection, inform user that one is required to submit a concern.
        if( !hasNetworkAccess() ) {
            InfoDialog.createInfoDialogue(
                    NewConcernActivity.this,
                    "No internet connection",
                    "Internet required to submit concern",
                    null,
                    true
            ).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("observer",newConcernViewModel.getObserver());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        newConcernViewModel.stopNetworkThread();
        super.onDestroy();
    }

    /**
     * The operation performed when the submitConcernButton is pressed. If the input fields are valid,
     * this method outputs nothing. If at least one of the required input fields is not valid, a
     * dialog box appears asking the user to fill in the field.
     * @preconditions None.
     * @modifies
     * - Initializes progressDialog if user input for concern submission was valid.
     * - Disables the submitConcernButton if user input for concern submission was valid.
     * - See NewConcernViewModel.submitConcern(...) for more.
     */
    private void submitConcernButtonAction() {
        assert(submitConcernButton != null);
        submitConcernButton.setEnabled(false); // Prevent sending the same concern multiple times
        // Attempt submission of concern containing input fields on the activity
        SubmissionReturnCode[] response = newConcernViewModel.submitConcern(
                concernNatureField.getText().toString(),
                actionsTakenField.getText().toString(),
                concernDescriptionField.getText().toString(),
                facilityField.getText().toString(),
                roomField.getText().toString(),
                nameField.getText().toString(),
                emailAddressField.getText().toString(),
                phoneNumberField.getText().toString()
        );
        assert(response != null);
        if(!response[0].equals(SubmissionReturnCode.VALID_INPUT)) {
            // Inform user that they are missing required input
            createInvalidInputDialogue(response).show();
            submitConcernButton.setEnabled(true);
        } else {
            // Actions to be performed if input is valid
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Loading");
            ((ProgressDialog)progressDialog).setMessage("Please wait...");
            progressDialog.show();
            assert(progressDialog!=null && progressDialog.isShowing());
        }
    }

    /**
     * Generates the dialogue popup that appears when the user attempts to submit a concern that is
     * missing one or more of the required input fields.
     * @preconditions none
     * @modifies nothing
     * @param rCodes the return codes generated when a concern submission was attempted.
     */
    private AlertDialog createInvalidInputDialogue(SubmissionReturnCode[] rCodes) {
        String message = "";
        String title = "Missing required field";
        for(int r=0; r<4; r++) {
            // Check if all codes have been read
            if(rCodes[r] == null) {
                assert(r > 0);
                if(r>1) title+="s";
                break;
            }
            // Determine if an input is missing or is invalid
            if(r>0) message += '\n';
            switch (rCodes[r].id) {
                case 1: // No LocalConcern type
                    message+= " -  Concern Nature";
                    break;
                case 2: // No Facility
                    message += " -  Facility";
                    break;
                case 3: // No name
                    message+= " -  Name";
                    break;
                case 4: // No contact info
                    message += " -  Phone or Email";
                    break;
            }
        }
        // Display missing fields in popup
        return InfoDialog.createInfoDialogue(
                NewConcernActivity.this,
                title,
                message,
                null,
                true
        );
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
        public static NewConcernViewModel getViewModel(NewConcernActivity activity) {
            return activity.newConcernViewModel;
        }

        static void testhook_call_submitConcernButtonAction(NewConcernActivity activity) {
            activity.submitConcernButtonAction();
        }

        static AlertDialog testhook_call_createInvalidInputDialogue(NewConcernActivity activity,
                                                              SubmissionReturnCode[] codes) {
            return activity.createInvalidInputDialogue(codes);
        }

        public static boolean submitConcern(
                AbstractNetworkActivity activity,
                ConcernSubmissionObserver mainViewModel,
                ConcernWrapper concern) throws InterruptedException {

            NewConcernViewModel newConcernViewModel = new NewConcernViewModel(
                    activity,
                    mainViewModel
            );
            return NewConcernViewModel.Test_Hook.submitConcernOutsideWithConcern(
                    newConcernViewModel,
                    concern
            );
        }
    }

}
