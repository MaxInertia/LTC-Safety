package c371g2.ltc_safety.a_new;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_main.ConcernSubmissionObserver;
import c371g2.ltc_safety.a_main.MainViewModel_TestHook;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * This class is used to test the NewConcernActivity class. This test class requires a device (real or
 * emulated) to run.
 */
@RunWith(AndroidJUnit4.class)
public class NewConcernActivityTests {
    @Rule
    public ActivityTestRule<NewConcernActivity> ncActivity = new ActivityTestRule<>(NewConcernActivity.class, true, false);
    private NewConcernActivity newConcernActivity;

    @Before
    public void setup() {
        Intent i = new Intent();
        ConcernSubmissionObserver observer = Mockito.mock(ConcernSubmissionObserver.class);
        i.putExtra("observer", observer);
        newConcernActivity = ncActivity.launchActivity(i);
    }

    @After
    public void cleanUp() {
        if(newConcernActivity != null) {
            newConcernActivity.finish();
        }
    }

    /**
     * Checks if the Submit LocalConcern button has an on-Click-Listener
     */
    @Test
    public void test_onCreate_buttonHasClickListener() {
        Button newConcernButton = (Button) newConcernActivity.findViewById(R.id.newConcern_submitConcernButton);
        assertTrue("The 'Submit LocalConcern'-button does not have an onClickListener", newConcernButton.hasOnClickListeners());
    }

    @Test
    public void test_submitConcernButtonAction() {
        newConcernActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NewConcernActivity.Test_Hook.testhook_call_submitConcernButtonAction(newConcernActivity);
                // Invalid input (null) therefore progress dialog should be null
                assertTrue(
                        "(progressDialog) Concern submission was attempted when it should not have been",
                        newConcernActivity.progressDialog==null
                );
                assertTrue(
                        "(submissionReturnCode) Concern submission was attempted when it should not have been",
                        NewConcernActivity.Test_Hook.getViewModel(newConcernActivity)
                                .submissionReturnCode==null
                );
            }
        });
    }

    @Test
    public void test_displayInvalidInputDialogue() {
        newConcernActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SubmissionReturnCode[] codes = new SubmissionReturnCode[4];
                codes[0] = SubmissionReturnCode.INVALID_NAME;
                codes[1] = SubmissionReturnCode.INVALID_PHONE_AND_EMAIL;
                codes[2] = SubmissionReturnCode.NO_CONCERN_TYPE;
                codes[3] = SubmissionReturnCode.NO_FACILITY_NAME;

                AlertDialog dialog = NewConcernActivity.Test_Hook
                        .testhook_call_createInvalidInputDialogue(newConcernActivity, codes);

                // Same case as test_submitConcernButtonAction(), both progressDialog and
                // submissionReturnCode should not be initialized.
                assertTrue(
                        "(progressDialog) Concern submission was attempted when it should not have been",
                        newConcernActivity.progressDialog==null
                );
                assertTrue(
                        "(submissionReturnCode) Concern submission was attempted when it should not have been",
                        NewConcernActivity.Test_Hook.getViewModel(newConcernActivity)
                                .submissionReturnCode==null
                );

                // Verify dialog created
                assertTrue("Dialog is null when it should have been initialized!", dialog!=null);
                assertTrue(
                        "Dialog is not connected to the application context!",
                        dialog.getContext().getApplicationContext()
                                .equals(newConcernActivity.getApplicationContext())
                );
            }
        });
    }


}
