package c371g2.ltc_safety.a_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_new.NewConcernActivity;


/**
 * This is the main activity: It contains a button that brings the user to the NewConcernActivity,
 * and a list of all previously submitted concerns that, when pressed, brings the user to the
 * ConcernDetailActivity.
 * Activity: ~ View-Controller
 * @Invariants The number of rows in the ListView is equal to Concern.list.size()
 * @HistoryProperties none
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newConcernButton = (Button) findViewById(R.id.new_concern_button);
        newConcernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewConcernActivity();
            }
        });
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
    private void openNewConcernActivity() {
        Intent newConcernIntent = new Intent(MainActivity.this, NewConcernActivity.class);
        MainActivity.this.startActivity(newConcernIntent);
    }

    /**
     * Switches the activity to ConcernDetailActivity. The activity contains fields which are filled
     * with data from a Concern object.
     * @preconditions none
     * @modifies The active activity changes from MainActivity to ConcernDetailActivity.
     */
    private void openViewConcernActivity() {

    }
}
