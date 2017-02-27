package c371g2.ltc_safety.a_detail;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.BasicActivity;
import c371g2.ltc_safety.Utilities;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

/**
 * This activity displays all data corresponding to a specified concern. LocalConcern data is passed to
 * this activity as a bundle, that bundle populates the fields of the activity view.
 * Activity: ~ View-Controller
 * @Invariants
 * - The text fields with name, concernType, and faculty data are not empty.
 * - The text fields with phoneNumber and emailAddress data are not both empty.
 * @HistoryProperties
 * For a given instance of this activity, the fields do not change after they are set by the
 * populateFields() method. The method is only called once.
 */
public class ConcernDetailActivity extends BasicActivity {

    ConcernDetailViewModel viewModel = new ConcernDetailViewModel(ConcernDetailActivity.this);

    Button retractButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_concern);

        retractButton = (Button) findViewById(R.id.detailedConcern_retractButton);
        retractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retractConcernButtonAction();
            }
        });

        populateFields(viewModel.getConcern(
                getIntent().getExtras().getInt("concern-index")
        ));
    }

    private void retractConcernButtonAction() {
        progressDialog = Utilities.displayInfoDialogue(
                this,
                null,
                "Please wait",
                null,
                false
        );
        viewModel.retractConcern();
        //TODO: Update the listview of concern statuses
    }

    /**
     * Fills each field in the view with the data contained in the concern of interest. The concern
     * of interest is passed to this activity in a Bundle, stored in savedInstanceState.
     * @preconditions Bundle contains values for each concern field.
     * @modifies Each field in the view is populated with data from viewableConcern.
     * @param concern The concern of interest.
     */
    void populateFields(ConcernWrapper concern) {

        // Contact information

        addViewRow("Name", concern.getReporterName(), R.id.detailedConcern_contactInformation, R.layout.detail_concern_row);
        addViewRow(null, null, R.id.detailedConcern_contactInformation, R.layout.divider);

        String email = concern.getReporterEmail();
        if(!email.equals("")) {
            addViewRow("Email", email, R.id.detailedConcern_contactInformation, R.layout.detail_concern_row);
        }

        String phone = concern.getReporterPhone();
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
        if(!room.equals("")) {
            addViewRow("Room", room, R.id.detailedConcern_concernInformation, R.layout.detail_concern_row);
        }

        String actions = concern.getActionsTaken();
        if(!actions.equals("")) {
            if(!room.equals("")) {
                addViewRow(null, null, R.id.detailedConcern_concernInformation, R.layout.divider);
            }
            addViewRow("Actions Taken", actions, R.id.detailedConcern_concernInformation, R.layout.detail_concern_row);
        }

        setupConcernStatusList(concern.getStatuses());
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
        View row = LayoutInflater.from(this).inflate(rowView, view, false);

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

    /**
     * Add one row to the layout for each concern Status in the list provided
     * @preconditions none.
     * @modifies One view is added to the activity layout for each concern in the list concernStatuses.
     * @param concernStatuses List of concern Statuses
     */
    private void setupConcernStatusList(List concernStatuses) {
        ViewGroup statusLayout = (ViewGroup) findViewById(R.id.detailedConcern_statusLayout);

        if(concernStatuses.size()==0) return;
        for(int i=0; i<concernStatuses.size(); i++) {
            StatusWrapper status = (StatusWrapper) concernStatuses.get(i);
            View statusRow = LayoutInflater.from(this).inflate(R.layout.detail_concern_status, statusLayout, false);

            TextView type = ((TextView) statusRow.findViewById(R.id.detailedConcern_statusType));
            TextView date = ((TextView) statusRow.findViewById(R.id.detailedConcern_statusDate));
            type.setText(status.getType());
            date.setText(status.getFormattedDate());

            statusLayout.addView(statusRow);

            if( (i+1) == concernStatuses.size() ) {
                type.setTextColor(Color.BLACK);
                date.setTextColor(Color.BLACK);

                if(status.getType().equals("RETRACTED")) {
                    retractButton.setEnabled(false);
                }

            } else {
                statusLayout.addView(
                        LayoutInflater.from(this).inflate(R.layout.divider, statusLayout, false)
                );
            }
        }
    }

    private void exitActivity() {}
}
