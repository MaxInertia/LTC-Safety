package c371g2.ltc_safety.a_main;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.a_detail.ConcernDetailActivity;
import c371g2.ltc_safety.a_main.MainActivity.Test_Hook;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_new.NewConcernActivity;
import c371g2.ltc_safety.local.ConcernWrapper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.anything;

/**
 * This class is used to test the MainActivity class. This test class requires a device (real or
 * emulated) to run.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTests {

    @ClassRule
    public static ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity;

    @BeforeClass
    public static void setup() {
        //mActivity.launchActivity(new Intent());
    }

    @Before
    public void beforeEach() {
        mainActivity = mActivity.launchActivity(null);
    }

    @After
    public void cleanUp() {
        if(mainActivity != null) {
            mainActivity.finish();
        }
    }

    /**
     * Checks if the New LocalConcern button has an on-Click-Listener
     */
    //@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Test
    public void test_onCreate_buttonHasClickListener() {
        Button newConcernButton = (Button) mainActivity.findViewById(R.id.main_newConcernButton);

        assertTrue("The 'New LocalConcern'-button does not have an onClickListener", newConcernButton.hasOnClickListeners());
    }

    /**
     * This test checks if openNewConcernActivity() changes the activity
     */
    @Test
    public void test_newConcernButton_ClickListener_ChangesActivity() {
        Instrumentation.ActivityMonitor activityMonitor = InstrumentationRegistry
                .getInstrumentation().addMonitor(NewConcernActivity.class.getName(),null,false);

        MainActivity.Test_Hook.testhook_call_openNewConcernActivity(mainActivity); // Method call

        NewConcernActivity newConcernActivity = (NewConcernActivity) InstrumentationRegistry
                .getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);
        assertNotNull(newConcernActivity);
        newConcernActivity.finish();
    }

    @Test
    public void test_onCreate_ListView_hasOnClickListener() {
        Activity mainActivity = mActivity.getActivity();
        ListView concernList = (ListView) mainActivity.findViewById(R.id.main_concernListView);

        assertTrue("The concern ListView does not have an onClickListener", concernList.getOnItemClickListener()!=null);
    }

    @Test
    public void test_concernListViewRow_ClickListener_ChangesActivity() {
        Instrumentation.ActivityMonitor activityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(ConcernDetailActivity.class.getName(),null,false);
        ConcernWrapper testConcern = DeviceStorageTests.generateConcernForTest(
                "Jeff",
                "3213884",
                "PixieGod@email.com",
                "UofS",
                "B102",
                "Near Miss",
                "None.",
                "Description"
        );

        MainActivity.Test_Hook
                .call_openConcernDetailActivity(((MainActivity)mainActivity), testConcern);

        ConcernDetailActivity concernDetailActivity = (ConcernDetailActivity) InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);
        assertNotNull(concernDetailActivity);
        concernDetailActivity.finish();
    }


    private void concernListItemStatusTestsSetup(String status) throws InterruptedException {
        final ConcernWrapper testConcern = DeviceStorageTests.generateConcernForTest(
                "Jeff",
                "3213884",
                "PixieGod@email.com",
                "UofS",
                "B102",
                "Near Miss",
                "None.",
                "Description here!"
        );
        ConcernWrapper.Test_Hook.addStatus(testConcern, status);
        final MainViewModel viewModel = MainActivity.Test_Hook.getMainViewModel(mainActivity);

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ConcernWrapper> concerns = new ArrayList<>();
                concerns.add(testConcern);
                ConcernListAdapter newAdapter = new ConcernListAdapter(
                        mainActivity.getBaseContext(),
                        R.layout.concern_list_item,
                        concerns
                );
                ListView list = ((ListView) mainActivity.findViewById(R.id.main_concernListView));
                list.setAdapter(newAdapter);
                list.invalidateViews();
                viewModel.signalLatch.countDown();
            }
        });

        viewModel.signalLatch.await(4, TimeUnit.SECONDS);
    }

    /**
     * Checks if the last status update (PENDING) for a concern appears in the ListView
     * @throws InterruptedException UI Thread interrupted (Should not occur)
     */
    @Test
    public void test_conernListItemStatus_PENDING() throws InterruptedException {
        concernListItemStatusTestsSetup("PENDING");

        onData(anything()).inAdapterView(withId(R.id.main_concernListView)).atPosition(0).
                onChildView(withId(R.id.concernListItem_status)).
                check(matches(withText(
                        mainActivity.getResources().getString(R.string.PENDING_text)
                )));
    }

    /**
     * Checks if the last status update (SEEN) for a concern appears in the ListView
     * @throws InterruptedException UI Thread interrupted (Should not occur)
     */
    @Test
    public void test_conernListItemStatus_SEEN() throws InterruptedException {
        concernListItemStatusTestsSetup("SEEN");

        onData(anything()).inAdapterView(withId(R.id.main_concernListView)).atPosition(0).
                onChildView(withId(R.id.concernListItem_status)).
                check(matches(withText(
                        mainActivity.getResources().getString(R.string.SEEN_text)
                )));
    }

    /**
     * Checks if the last status update (RESPONDING24) for a concern appears in the ListView
     * @throws InterruptedException UI Thread interrupted (Should not occur)
     */
    @Test
    public void test_conernListItemStatus_RESPONDING24() throws InterruptedException {
        concernListItemStatusTestsSetup("RESPONDING24");

        onData(anything()).inAdapterView(withId(R.id.main_concernListView)).atPosition(0).
                onChildView(withId(R.id.concernListItem_status)).
                check(matches(withText(
                        mainActivity.getResources().getString(R.string.RESPONDING24_text)
                )));
    }

    /**
     * Checks if the last status update (RESPONDING48) for a concern appears in the ListView
     * @throws InterruptedException UI Thread interrupted (Should not occur)
     */
    @Test
    public void test_conernListItemStatus_RESPONDING48() throws InterruptedException {
        concernListItemStatusTestsSetup("RESPONDING48");

        onData(anything()).inAdapterView(withId(R.id.main_concernListView)).atPosition(0).
                onChildView(withId(R.id.concernListItem_status)).
                check(matches(withText(
                        mainActivity.getResources().getString(R.string.RESPONDING48_text)
                )));
    }

    /**
     * Checks if the last status update (RESPONDING72) for a concern appears in the ListView
     * @throws InterruptedException UI Thread interrupted (Should not occur)
     */
    @Test
    public void test_conernListItemStatus_RESPONDING72() throws InterruptedException {
        concernListItemStatusTestsSetup("RESPONDING72");

        onData(anything()).inAdapterView(withId(R.id.main_concernListView)).atPosition(0).
                onChildView(withId(R.id.concernListItem_status)).
                check(matches(withText(
                        mainActivity.getResources().getString(R.string.RESPONDING72_text)
                )));
    }

    /**
     * Checks if the last status update (RESOLVED) for a concern appears in the ListView
     * @throws InterruptedException UI Thread interrupted (Should not occur)
     */
    @Test
    public void test_conernListItemStatus_RESOLVED() throws InterruptedException {
        concernListItemStatusTestsSetup("RESOLVED");

        onData(anything()).inAdapterView(withId(R.id.main_concernListView)).atPosition(0).
                onChildView(withId(R.id.concernListItem_status)).
                check(matches(withText(
                        mainActivity.getResources().getString(R.string.RESOLVED_text)
                )));
    }

    /**
     * Checks if the last status update (RETRACTED) for a concern appears in the ListView
     * @throws InterruptedException UI Thread interrupted (Should not occur)
     */
    @Test
    public void test_conernListItemStatus_RETRACTED() throws InterruptedException {
        concernListItemStatusTestsSetup("RETRACTED");

        onData(anything()).inAdapterView(withId(R.id.main_concernListView)).atPosition(0).
                onChildView(withId(R.id.concernListItem_status)).
                check(matches(withText(
                        mainActivity.getResources().getString(R.string.RETRACTED_text)
                )));
    }
}
