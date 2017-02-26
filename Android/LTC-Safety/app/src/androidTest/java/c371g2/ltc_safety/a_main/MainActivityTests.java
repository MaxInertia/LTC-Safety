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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_new.NewConcernActivity;

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
    private Activity mainActivity;

    @BeforeClass
    public static void setup() {
        mActivity.launchActivity(new Intent());
    }

    @Before
    public void beforeEach() {
        mainActivity = mActivity.getActivity();
    }

    /**
     * Checks if the New LocalConcern button has an on-Click-Listener
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Test
    public void test_onCreate_buttonHasClickListener() {
        Button newConcernButton = (Button) mainActivity.findViewById(R.id.new_concern_button);

        assertTrue("The 'New LocalConcern'-button does not have an onClickListener", newConcernButton.hasOnClickListeners());
    }

    /**
     * This test checks if openNewConcernActivity() changes the activity
     */
    @Test
    public void test_newConcernButtonChangesActivity() {
        Instrumentation.ActivityMonitor activityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(NewConcernActivity.class.getName(),null,false);

        final Button newConcernButton = (Button) mainActivity.findViewById(R.id.new_concern_button);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newConcernButton.performClick();
            }
        });

        NewConcernActivity newConcernActivity = (NewConcernActivity) InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);
        assertNotNull(newConcernActivity);
        newConcernActivity.finish();
    }

    @Test
    public void test_onCreate_ListView_hasOnClickListener() {
        Activity mainActivity = mActivity.getActivity();
        ListView concernList = (ListView) mainActivity.findViewById(R.id.concern_listView);

        assertTrue("The concern ListView does not have an onClickListener", concernList.getOnItemClickListener()!=null);
    }

}
