package c371g2.ltc_safety.a_detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    }

    /**
     * Fills each field in the view with the data contained in the concern of interest. The concern
     * of interest is passed to this activity in a Bundle, stored in savedInstanceState.
     * @preconditions Bundle contains values for each concern field.
     * @modifies Each field in the view is populated with data from viewableConcern.
     * @param concern The concern of interest.
     */
    private void populateFields(ConcernWrapper concern) {}

    private void exitActivity() {}
}
