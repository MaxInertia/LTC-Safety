package c371g2.ltc_safety.a_main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.InfoDialog;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_detail.ConcernDetailActivity;
import c371g2.ltc_safety.a_new.NewConcernActivity;
import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This is the main activity which is created when launching the application. It contains...
 * - A button that brings the user to the NewConcernActivity.
 * - A list of all previously submitted concerns that, when pressed, brings the user to the
 * ConcernDetailActivity.
 *
 * Activity: ~ View-Controller
 * Contains a static inner-Class 'Test_Hook' to aid testing.
 *
 * @Invariants
 * - The number of rows in the ListView is equal to MainViewModel.concernList.size()
 * - This activity is only initialized once for any standard use case of the app (non-testing).
 * @HistoryProperties none
 */
public class MainActivity extends AbstractNetworkActivity {
    /**
     * Reference to the View-Model for this Activity. All functionality in this activity that is
     * not directly related to the UI is encapsulated in the View-Model.
     */
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getSerializableExtra("observer") != null) {
            mainViewModel = ((MainViewModel) getIntent().getSerializableExtra("observer"));
            mainViewModel.setActivity(this);
        } else {
            mainViewModel = new MainViewModel(this);
        }

        assert(mainViewModel.getSortedConcernList() != null);
        populateConcernsList();
        setupNewConcernButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_reload_concerns) {
            if(!hasNetworkAccess()) {
                Toast.makeText(
                        getBaseContext(),
                        "Internet connection required",
                        Toast.LENGTH_LONG
                ).show();
            } else {
                if(mainViewModel.updateConcerns()) { // TODO: removed getContext() from updateConcern
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Loading");
                    ((ProgressDialog)progressDialog).setMessage("Please wait...");
                } else {
                    progressDialog = InfoDialog.createInfoDialogue(
                            MainActivity.this,
                            null,
                            "No concerns to update!",
                            null,
                            true
                    );
                }
                progressDialog.show();
                assert (progressDialog != null && progressDialog.isShowing());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(mainViewModel != null) mainViewModel.stopNetworkThread();
        super.onDestroy();
    }

    /**
     * Sets up the onClickListener for the button that brings the user to the NewConcernActivity
     * for submitting concerns.
     * @preconditions none
     * @modifies
     * - An onClickListener is assigned to newConcernButton.
     */
    private void setupNewConcernButton() {
        Button newConcernButton = (Button) findViewById(R.id.main_newConcernButton);
        assert(newConcernButton != null);
        newConcernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewConcernActivity();
            }
        });
        assert(newConcernButton.hasOnClickListeners());
    }

    /**
     * Fills the ListView in MainActivity with all previously submitted concerns, if any exist.
     * If no previous concerns exist then nothing happens.
     * @preconditions MainViewModel.concernList was initialized
     * @modifies The contents of the ListView in MainActivity are populated by the elements in
     * MainViewModel.concernList
     */
    private void populateConcernsList() {
        ConcernListAdapter adapter = new ConcernListAdapter(
                this,
                R.layout.concern_list_item,
                mainViewModel.getSortedConcernList()
        );
        ListView concernList = (ListView) findViewById(R.id.main_concernListView);
        assert(concernList != null);
        concernList.setAdapter(adapter);
        concernList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openConcernDetailActivity(mainViewModel.getConcernAtIndex(position));
                    }
                }
        );
        assert(concernList.hasOnClickListeners());
    }

    /**
     * Switches the activity to the new concern activity. The new concern activity contains fields
     * which when filled specify a concern. The specified concern can then be submitted.
     * @preconditions mainViewModel has been initialized
     * @modifies The active activity changes from MainActivity to NewConcernActivity.
     */
    private void openNewConcernActivity() {
        Intent newConcernIntent = new Intent(MainActivity.this, NewConcernActivity.class);
        assert(newConcernIntent != null);
        newConcernIntent.putExtra("observer",((ConcernSubmissionObserver)mainViewModel));
        assert(newConcernIntent.getExtras().getSerializable("observer").equals(mainViewModel));

        newConcernIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        MainActivity.this.startActivity(newConcernIntent);
    }

    /**
     * Switches the activity to ConcernDetailActivity. The activity contains fields which are filled
     * with data from a Concern object.
     * @preconditions mainViewModel has been initialized
     * @modifies The active activity changes from MainActivity to ConcernDetailActivity.
     * @param concern The concern that corresponds to the row in the ListView of concerns that was
     *                pressed.
     */
    private void openConcernDetailActivity(@NonNull ConcernWrapper concern) {
        Intent concernDetailIntent = new Intent(MainActivity.this, ConcernDetailActivity.class);
        assert(concernDetailIntent != null);
        concernDetailIntent.putExtra("observer",((ConcernRetractionObserver)mainViewModel));
        concernDetailIntent.getExtras().getSerializable("observer").equals(mainViewModel);
        assert(concernDetailIntent.getExtras().getSerializable("observer").equals(mainViewModel));
        concernDetailIntent.putExtra("concern",concern);
        assert(concernDetailIntent.getExtras().getSerializable("concern").equals(concern));
        //mainViewModel.setActivity(null);
        //mainViewModel = null;

        concernDetailIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        MainActivity.this.startActivity(concernDetailIntent);
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
    public static class Test_Hook {
        public static MainViewModel getMainViewModel(MainActivity mainActivity) {
            return mainActivity.mainViewModel;
        }

        public static void call_openConcernDetailActivity(MainActivity activity,
                                                          ConcernWrapper concern) {
            activity.openConcernDetailActivity(concern);
        }

        public static void testhook_call_openNewConcernActivity(MainActivity activity) {
            activity.openNewConcernActivity();
        }

        static void clearConcerns(MainActivity activity) {
            MainViewModel.Test_Hook.clearConcernList(activity.mainViewModel);
        }

        public static void addConcern(final MainActivity activity, ConcernWrapper concern) {
            MainViewModel.Test_Hook.addConcern(activity.mainViewModel, concern);
        }

        public static MainViewModel getNewMainViewModel() {
            return MainViewModel.Test_Hook.instance.getMainViewModelInstance();
        }
    }
}
