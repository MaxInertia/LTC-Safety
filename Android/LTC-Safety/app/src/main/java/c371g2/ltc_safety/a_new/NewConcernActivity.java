package c371g2.ltc_safety.a_new;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import c371g2.ltc_safety.R;

/**
 * This activity displays a form with various fields for specifying a safety concern. A concern with
 * sufficient details can be submitted to the database.
 * Activity: ~ View-Controller
 * @Invariants The number of rows in the ListView is equal to Concern.list.size()
 * @HistoryProperties none
 */
public class NewConcernActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_concern);
    }

    private void exitActivity() {}
}
