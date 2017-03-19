package c371g2.ltc_safety.a_detail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.InfoDialog;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_main.ConcernRetractionObserver;
import c371g2.ltc_safety.a_main.MainActivity;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

/**
 * This activity displays all data corresponding to a given concern. The concern is passed to this
 * activity along with the ConcernRetractionObserver instance through the Activity Intent.
 *
 * Activity: ~ View-Controller
 *
 * Contains a static inner-class 'Test_Hook' to aid testing.
 *
 * @Invariants
 * - The text fields with name, concernType, and faculty data are not empty.
 * - The text field with the phoneNumber and the text field with the emailAddress are not both empty.
 * @HistoryProperties
 * - For a given instance of this activity, the fields do not change (same instances throughout
 * lifetime) after they are set by the populateFields() method. The method is only called once.
 */
public class ConcernDetailActivity extends AbstractNetworkActivity {
    /**
     * Reference to the View-Model for this Activity. All functionality in this activity that is
     * not directly related to the UI is encapsulated in the View-Model.
     */
    private ConcernDetailViewModel concernDetailViewModel;
    /**
     * Reference to the button used to retract a concern.
     */
    private Button retractConcernButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_concern);

        ConcernWrapper concern = ((ConcernWrapper) getIntent().getSerializableExtra("concern"));
        concernDetailViewModel = new ConcernDetailViewModel(
                this,
                concern,
                (ConcernRetractionObserver) getIntent().getSerializableExtra("observer")
        );

        setupRetractConcernButton();
        populateFields(concern) ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("observer",concernDetailViewModel.getObserver());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(getParent()!=null) {
            super.onBackPressed();
        } else {
            // Prevents loading an out of date ListView
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("observer", concernDetailViewModel.getObserver());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(i);
        }
    }

    @Override
    protected void onDestroy() {
        concernDetailViewModel.stopNetworkThread();
        super.onDestroy();
    }

    /**
     * Sets up the button used to retract the concern that is currently being viewed.
     * @preconditions none
     * @modifies
     * - Initializes retractConcernButton with reference to button on view.
     * - Assigns an OnClickListener to retraceConcernButton.
     */
    private void setupRetractConcernButton() {
        retractConcernButton = (Button) findViewById(R.id.detailedConcern_retractButton);
        final ConcernDetailActivity me = this;
        assert(retractConcernButton != null);
        retractConcernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(me);
                progressDialog.setTitle("Loading");
                ((ProgressDialog)progressDialog).setMessage("Please wait...");
                progressDialog.show();
                if(getParent()!=null) {
                    // Prevents loading an out of date ListView if back nav is pressed
                    getParent().finish();
                }
                assert(progressDialog != null && progressDialog.isShowing());
                concernDetailViewModel.retractConcern();
            }
        });
    }

    /**
     * Fills each field in the view with the data contained in the concern of interest.
     * @preconditions concern is not null
     * @modifies Each field in the view is populated with data from concern.
     * @param concern The concern of interest / The concern whose data is being loaded into the
     *                activities layout.
     */
    void populateFields(@NonNull ConcernWrapper concern) {
        // Contact information
        addViewRow("Name", concern.getReporterName(), R.id.detailedConcern_contactInformation, R.layout.detail_concern_row);
        addViewRow(null, null, R.id.detailedConcern_contactInformation, R.layout.divider);

        String email = concern.getReporterEmail();
        assert(email != null);
        if(!email.equals("")) {
            addViewRow("Email", email, R.id.detailedConcern_contactInformation, R.layout.detail_concern_row);
        }

        String phone = concern.getReporterPhone();
        assert(phone != null);
        if(!phone.equals("")) {
            if(!email.equals("")) {
                addViewRow(null, null, R.id.detailedConcern_contactInformation, R.layout.divider);
            }
            addViewRow("Phone Number", phone, R.id.detailedConcern_contactInformation, R.layout.detail_concern_row);
        }

        // Concern information
        addViewRow("Concern Nature", concern.getConcernType(), R.id.detailedConcern_concernInformation, R.layout.detail_concern_row);
        addViewRow(null, null, R.id.detailedConcern_concernInformation, R.layout.divider);
        addViewRow("Facility Name", concern.getFacilityName(), R.id.detailedConcern_concernInformation, R.layout.detail_concern_row);
        addViewRow(null, null, R.id.detailedConcern_concernInformation, R.layout.divider);

        String room = concern.getRoomName();
        assert(room != null);
        if(!room.equals("")) {
            addViewRow("Room", room, R.id.detailedConcern_concernInformation, R.layout.detail_concern_row);
        }

        String description = concern.getDescription();
        assert(description != null);
        if(!description.equals("")) {
            if(!room.equals("")) {
                addViewRow(null, null, R.id.detailedConcern_concernInformation, R.layout.divider);
            }
            addViewRow("Description", description, R.id.detailedConcern_concernInformation, R.layout.detail_concern_row);
        }

        String actions = concern.getActionsTaken();
        assert(actions != null);
        if(!actions.equals("")) {
            if(!description.equals("")) {
                addViewRow(null, null, R.id.detailedConcern_concernInformation, R.layout.divider);
            }
            addViewRow("Actions Taken", actions, R.id.detailedConcern_concernInformation, R.layout.detail_concern_row);
        }

        // Concern Statuses
        setupConcernStatusList(concern.getStatuses());
    }

    /**
     * Add one row to the layout for each concern Status in the concernStatuses list.
     * @preconditions
     * - concernStatuses is not null.
     * @modifies
     * - One view is added to the activity layout for each concern in the list concernStatuses.
     * @param concernStatuses List of concern Statuses corresponding to the concern of interest.
     */
    void setupConcernStatusList(@NonNull List concernStatuses) {
        ViewGroup statusLayout = (ViewGroup) findViewById(R.id.detailedConcern_statusLayout);
        statusLayout.removeAllViews();
        assert(statusLayout != null);

        for(int i=0; i<concernStatuses.size(); i++) {
            // Get layout, fill layout fields with concern status fields, add layout to the statusLayout.
            View statusRow = LayoutInflater.from(this).inflate(R.layout.detail_concern_status, statusLayout, false);
            TextView type = ((TextView) statusRow.findViewById(R.id.detailedConcern_statusType));
            assert(type != null);
            TextView date = ((TextView) statusRow.findViewById(R.id.detailedConcern_statusDate));
            assert(date != null);
            StatusWrapper status = (StatusWrapper) concernStatuses.get(i);
            date.setText(status.getFormattedDate());
            assert(date.getText()!=null && !date.getText().equals(""));
            switch(status.getType()) {
                case "PENDING":
                    type.setText(R.string.PENDING_text);
                    break;
                case "SEEN":
                    type.setText(R.string.SEEN_text);
                    break;
                case "RESPONDING24":
                    type.setText(R.string.RESPONDING24_text);
                    break;
                case "RESPONDING48":
                    type.setText(R.string.RESPONDING48_text);
                    break;
                case "RESPONDING72":
                    type.setText(R.string.RESPONDING72_text);
                    break;
                case "RESOLVED":
                    type.setText(R.string.RESOLVED_text);
                    break;
                case "RETRACTED":
                    type.setText(R.string.RETRACTED_text);
                    break;
            }
            assert(type.getText()!=null && !type.getText().equals(""));
            statusLayout.addView(statusRow);

            if( (i+1) == concernStatuses.size() ) {
                // Distinguish the most recent Status by setting the text color to black.
                type.setTextColor(Color.BLACK);
                date.setTextColor(Color.BLACK);
                if(status.getType().equals("RETRACTED") || status.getType().equals("RESOLVED")) {
                    retractConcernButton.setEnabled(false);
                    retractConcernButton.setVisibility(View.GONE);
                    ((LinearLayout)findViewById(R.id.detailedConcern_retractButtonContainer)).setVisibility(View.GONE);
                }
            } else {
                // Add a divider layout between the status at index i and i+1
                statusLayout.addView(
                        LayoutInflater.from(this).inflate(R.layout.divider, statusLayout, false)
                );
            }
        }
    }

    /**
     * Adds a view (rowView) to the specified parent view.
     * @preconditions parentView and rowView are integers that correspond to id-resources: R.id.___
     * @modifies rowView is added to the list of children of parentView.
     * @param key The value for the first TextView
     * @param value The value for the second TextView
     * @param parentView The view which will be the parent of rowView.
     * @param rowView The view being added to parentView.
     */
    private void addViewRow(String key, String value, int parentView, int rowView) {
        ViewGroup view = (ViewGroup) findViewById(parentView);
        assert(view != null);
        View row = LayoutInflater.from(this).inflate(rowView, view, false);
        assert(row != null);

        if(key!=null) {
            ((TextView) row.findViewById(R.id.detailedConcern_key))
                    .setText(key);
        }
        if(value != null) {
            ((TextView) row.findViewById(R.id.detailedConcern_value))
                    .setText(value);
        }
        view.addView(row);
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
        static ConcernDetailViewModel getConcernDetailViewModel(ConcernDetailActivity activity) {
            return activity.concernDetailViewModel;
        }
    }
}
