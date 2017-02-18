package c371g2.ltc_safety.a_new;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.Utilities;

/**
 * This activity displays a form with various fields for specifying a safety concern. A concern with
 * sufficient details can be submitted to the database.
 * Activity: ~ View-Controller
 * @Invariants none
 * @HistoryProperties none
 */
public class NewConcernActivity extends AppCompatActivity {

    NewConcernViewModel newConcernViewModel;

    Button submitConcernButton;

    EditText nameField;
    EditText phoneNumberField;
    EditText emailAddressField;
    EditText actionsTakenField;

    Spinner concernNatureSpinner;
    Spinner facilitySpinner;

    AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_concern);
        //getActionBar().setHomeAsUpIndicator(R.drawable.ic); // TODO: Change up-nav to "cancel"

        nameField = (EditText) findViewById(R.id.nameTextField);
        assert(nameField != null);
        phoneNumberField = (EditText) findViewById(R.id.phoneNumberField);
        assert(phoneNumberField != null);
        emailAddressField = (EditText) findViewById(R.id.emailTextField);
        assert(emailAddressField != null);
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

        concernNatureSpinner = (Spinner) findViewById(R.id.concernTypeSpinner);
        assert(concernNatureSpinner != null);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.concern_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        concernNatureSpinner.setAdapter(adapter);
        assert(concernNatureSpinner.getAdapter() != null);

        facilitySpinner = (Spinner) findViewById(R.id.facilitySpinner);
        assert(facilitySpinner != null);
        adapter = ArrayAdapter.createFromResource(this, R.array.longtermcare_facilities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facilitySpinner.setAdapter(adapter);
        assert(facilitySpinner.getAdapter() != null);

        newConcernViewModel = new NewConcernViewModel(NewConcernActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( !Utilities.hasNetworkAccess(NewConcernActivity.this) ) {
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
     */
    private void submitConcernButtonAction() {
        submitConcernButton.setEnabled(false); // Prevent sending the same concern multiple times

        // Attempt submission of concern containing input fields on the activity
        ReturnCode[] response = newConcernViewModel.submitConcern(
                concernNatureSpinner.getSelectedItem().toString(),
                actionsTakenField.getText().toString(),
                facilitySpinner.getSelectedItem().toString(),
                nameField.getText().toString(),
                emailAddressField.getText().toString(),
                phoneNumberField.getText().toString()
        );
        assert(response != null);

        if(response[0]!=ReturnCode.VALID_INPUT) {
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
        }
    }

    /**
     * Generates the dialogue popup that appears when the user attempts to submit a concern that is
     * missing one or more of the required input fields.
     * @param rCodes the return codes generated when a concern submission was attempted
     */
    private void displayInvalidInputDialogue(ReturnCode[] rCodes) {

        String message = "";
        for(int r=0; r<3; r++) {
            // Check if all codes have been read, then display popup
            if(rCodes[r] == null) {
                assert(r > 0);
                if(r==1) {
                    displayInfoDialogue(
                            "Missing required field",
                            message,
                            null,
                            true
                    );
                } else if(r>1) {
                    displayInfoDialogue(
                            "Missing required fields",
                            message,
                            null,
                            true
                    );
                }
                break;
            }

            if(r>0) message += '\n';
            switch (rCodes[r].id) {
                case 1: // No Concern type
                    message+="- Concern Type";
                    break;
                case 2: // No Facility
                    message += "- Facility";
                    break;
                case 3: // No name
                    message+= "- Name";
                    break;
                case 4: // No contact info
                    message += "- Contact information";
                    break;
            }
        }
    }

    /**
     * Display a popup. This can contain a title and/or a message. A listener can be linked to the
     * popup; the operation in the listener will be performed when the popup is dismissed.
     * @param title Title of the popup
     * @param message Text in the popup
     * @param listener The listener for the dismissal of the popup
     * @param isCancellable Boolean; can the user dismiss the popup
     */
    AlertDialog displayInfoDialogue(String title, String message, DialogInterface.OnDismissListener listener, boolean isCancellable) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NewConcernActivity.this);
        alertBuilder.setCancelable(isCancellable);
        if(title != null) {
            alertBuilder.setTitle(title);
        }
        if(message != null) {
            alertBuilder.setMessage(message);
        }
        if(listener != null) {
            alertBuilder.setOnDismissListener(listener);
        }
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
        return dialog;
    }

    private void exitActivity() {}
}
