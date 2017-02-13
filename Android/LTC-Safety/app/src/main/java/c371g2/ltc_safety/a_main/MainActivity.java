package c371g2.ltc_safety.a_main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import c371g2.ltc_safety.R;


/**
 * This is the main activity: It contains a button that brings the user to the NewConcernActivity,
 * and a list of all previously submitted concerns that, when pressed, brings the user to the
 * ConcernDetailActivity.
 * Activity: ~ View-Controller
 * @Invariants none
 * @HistoryProperties none
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Fills the ListView in MainActivity with all previously submitted concerns, if any exist.
     * If no previous concerns exist then nothing happens.
     * @preconditions none
     * @modifies The contents of the ListView in MainActivity
     */
    private void populateConcernsList() {}

    /**
     * Switches the activity to the new concern activity. The new concern activity contains fields
     * which when filled specify a concern. The specified concern can then be submitted.
     * @preconditions none
     * @modifies The active activity changes from MainActivity to NewConcernActivity.
     */
    private void openNewConcernActivity() {}

    /**
     * Switches the activity to ConcernDetailActivity. The activity contains fields which are filled
     * with data from a Concern object.
     * @preconditions none
     * @modifies The active activity changes from MainActivity to ConcernDetailActivity.
     */
    private void openViewConcernActivity() {

    }
}
