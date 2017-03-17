package c371g2.ltc_safety.a_main;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.ListView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import c371g2.ltc_safety.a_detail.ConcernDetailActivity;
import c371g2.ltc_safety.a_main.MainActivity.Test_Hook;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_new.NewConcernActivity;
import c371g2.ltc_safety.local.ConcernWrapper;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

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
        mainActivity.finish();
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

        MainActivity.Test_Hook // method call
                .call_openConcernDetailActivity(((MainActivity)mainActivity), testConcern);

        ConcernDetailActivity concernDetailActivity = (ConcernDetailActivity) InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);
        assertNotNull(concernDetailActivity);
        concernDetailActivity.finish();
    }

}
