package c371g2.ltc_safety.a_new;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.NetworkActivity;

/**
 * This activity displays a form with various fields for specifying a safety concern. A concern with
 * sufficient details can be submitted to the database.
 *
 * Activity: ~ View-Controller
 *
 * @Invariants none
 * @HistoryProperties For a given instance of this class, the newConcernViewModel field always
 * contains the same instance.
 */
public class NewConcernActivity extends NetworkActivity {

    /**
     * Reference to the View-Model for this Activity. All functionality in this activity that is
     * not directly related to the UI is encapsulated in the View-Model.
     */
    final NewConcernViewModel newConcernViewModel = new NewConcernViewModel(this);
    /**
     * Reference to the button used to submit a concern.
     */
    Button submitConcernButton;
    /**
     * The input field for the Reporters name.
     */
    EditText nameField;
    /**
     * The input field for the Reporters phone number.
     */
    EditText phoneNumberField;
    /**
     * The input field for the Reporters email address.
     */
    EditText emailAddressField;
    /**
     * The input field for the name of the long term care facility.
     * Possible values located at 'R.array.longtermcare_facilities'
     */
    EditText facilityField;
    /**
     * The input field for a room within the aforementioned LTC facility.
     */
    EditText roomField;
    /**
     * The input field for the nature of the concern.
     * Possible values located at 'R.array.concern_types'
     */
    EditText concernNatureField;
    /**
     * The input field for the actions taken in response to the concern.
     */
    EditText actionsTakenField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_concern);

        nameField = (EditText) findViewById(R.id.nameTextField);
        assert(nameField != null);
        phoneNumberField = (EditText) findViewById(R.id.phoneNumberField);
        assert(phoneNumberField != null);
        emailAddressField = (EditText) findViewById(R.id.emailTextField);
        assert(emailAddressField != null);
        roomField = (EditText) findViewById(R.id.roomNumberField);
        assert(roomField != null);
        actionsTakenField = (EditText) findViewById(R.id.actionsTakenTextField);
        assert(actionsTakenField != null);

        submitConcernButton = (Button) findViewById(R.id.submit_concern_button);
        assert(submitConcernButton != null);
        submitConcernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitConcernButtonAction();
            }
        });

        concernNatureField = (EditText) findViewById(R.id.concernNatureChooser);
        ChooserView.setup(
                concernNatureField,
                "Select LocalConcern Nature",
                getResources().getStringArray(R.array.concern_types)
        );

        facilityField = (EditText) findViewById(R.id.facilityChooser);
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
            displayInfoDialogue(
                    "No internet connection",
                    "Internet required to submit concern",
                    null,
                    true
            );
        }
    }

    /**
     * The operation performed when the submitConcernButton is pressed. If the input fields are valid,
     * this method outputs nothing. If at least one of the required input fields is not valid, a
     * dialog box appears asking the user to fill in the field.
     * @preconditions None.
     * @modifies
     * - See NewConcernViewModel.submitConcern(...)
     * - Initializes progressDialog if user input for concern submission was valid.
     * - Disables the submitConcernButton if user input for concern submission was valid.
     */
    private void submitConcernButtonAction() {
        assert(submitConcernButton != null);
        submitConcernButton.setEnabled(false); // Prevent sending the same concern multiple times
        // Attempt submission of concern containing input fields on the activity
        ReturnCode[] response = newConcernViewModel.submitConcern(
                concernNatureField.getText().toString(),
                actionsTakenField.getText().toString(),
                facilityField.getText().toString(),
                roomField.getText().toString(),
                nameField.getText().toString(),
                emailAddressField.getText().toString(),
                phoneNumberField.getText().toString()
        );
        assert(response != null);
        if(!response[0].equals(ReturnCode.VALID_INPUT)) {
            // Inform user that they are missing required input
            displayInvalidInputDialogue(response);
            submitConcernButton.setEnabled(true);
        } else {
            // Actions to be performed if input is valid
            progressDialog = displayInfoDialogue(
                    null,
                    "Please wait",
                    null,
                    false
            );
            assert(progressDialog!=null && progressDialog.isShowing());
        }
    }

    /**
     * Generates the dialogue popup that appears when the user attempts to submit a concern that is
     * missing one or more of the required input fields.
     * @preconditions none
     * @modifies nothing
     * @param rCodes the return codes generated when a concern submission was attempted
     */
    private void displayInvalidInputDialogue(ReturnCode[] rCodes) {
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
        displayInfoDialogue(
                title,
                message,
                null,
                true
        );
    }
}
