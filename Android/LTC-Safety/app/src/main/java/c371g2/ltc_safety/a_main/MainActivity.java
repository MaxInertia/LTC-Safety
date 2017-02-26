package c371g2.ltc_safety.a_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_detail.ConcernDetailActivity;
import c371g2.ltc_safety.a_new.NewConcernActivity;

/**
 * This is the main activity: It contains a button that brings the user to the NewConcernActivity,
 * and a list of all previously submitted concerns that, when pressed, brings the user to the
 * ConcernDetailActivity.
 * Activity: ~ View-Controller
 * @Invariants The number of rows in the ListView is equal to LocalConcern.list.size()
 * @HistoryProperties none
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel.initialize(getBaseContext());
        populateConcernsList();

        Button newConcernButton = (Button) findViewById(R.id.new_concern_button);
        setButtonListener(newConcernButton);
    }


    public void setButtonListener(Button newConcernButton) {
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
    private void populateConcernsList() {
        ConcernListAdapter adapter = new ConcernListAdapter(
                this,
                R.layout.concern_list_item,
                MainViewModel.getSortedConcernList()
        );
        ListView concernList = (ListView) findViewById(R.id.concern_listView);
        concernList.setAdapter(adapter);
        concernList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openConcernDetailActivity(position);
                    }
                }
        );
    }

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
     * with data from a LocalConcern object.
     * @preconditions none
     * @modifies The active activity changes from MainActivity to ConcernDetailActivity.
     */
    private void openConcernDetailActivity(int index) {
        Intent intent = new Intent(MainActivity.this, ConcernDetailActivity.class);
        intent.putExtra("concern-index",index);
        MainActivity.this.startActivity(intent);
    }
}
