package c371g2.ltc_safety.a_new;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import c371g2.ltc_safety.R;

import static junit.framework.Assert.assertTrue;

/**
 * This class is used to test the NewConcernActivity class. This test class requires a device (real or
 * emulated) to run.
 */
@RunWith(AndroidJUnit4.class)
public class NewConcernActivityTests {

    @ClassRule
    public static ActivityTestRule<NewConcernActivity> mActivity = new ActivityTestRule<>(NewConcernActivity.class);
    private Activity newConcernActivity;

    @BeforeClass
    public static void setup() {
        mActivity.launchActivity(new Intent());
    }

    @Before
    public void beforeEach() {
        newConcernActivity = mActivity.getActivity();
    }

    /**
     * Checks if the Submit LocalConcern button has an on-Click-Listener
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Test
    public void test_onCreate_buttonHasClickListener() {
        Button newConcernButton = (Button) newConcernActivity.findViewById(R.id.submit_concern_button);

        assertTrue("The 'Submit LocalConcern'-button does not have an onClickListener", newConcernButton.hasOnClickListeners());
    }

}
