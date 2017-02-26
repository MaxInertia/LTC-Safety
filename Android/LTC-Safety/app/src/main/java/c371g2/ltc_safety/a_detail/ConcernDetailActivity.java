package c371g2.ltc_safety.a_detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.local.ConcernWrapper;

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
public class ConcernDetailActivity extends AppCompatActivity {

    ConcernDetailViewModel viewModel = new ConcernDetailViewModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_concern);

        populateFields(viewModel.getConcern(
                getIntent().getExtras().getInt("concern-index")
        ));

        Button retractButton = (Button) findViewById(R.id.detailedConcern_retractButton);
        retractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.retractConcern();
                //TODO: Update the listview of concern statuses
            }
        });
    }

    /**
     * Fills each field in the view with the data contained in the concern of interest. The concern
     * of interest is passed to this activity in a Bundle, stored in savedInstanceState.
     * @preconditions Bundle contains values for each concern field.
     * @modifies Each field in the view is populated with data from viewableConcern.
     * @param concern The concern of interest.
     */
    private void populateFields(ConcernWrapper concern) {
        ((TextView) findViewById(R.id.detailedConcern_nameField))
                .setText(concern.getReporterName());
        ((TextView) findViewById(R.id.detailedConcern_emailField))
                .setText(concern.getReporterEmail());
        ((TextView) findViewById(R.id.detailedConcern_phoneField))
                .setText(concern.getReporterPhone());
        ((TextView) findViewById(R.id.detailedConcern_concernNatureField))
                .setText(concern.getConcernType());
        ((TextView) findViewById(R.id.detailedConcern_facilityNameField))
                .setText(concern.getFacilityName());
        ((TextView) findViewById(R.id.detailedConcern_roomField))
                .setText(concern.getRoomName());
        ((TextView) findViewById(R.id.detailedConcern_actionsTakenField))
                .setText(concern.getActionsTaken());
    }

    private void exitActivity() {}
}
