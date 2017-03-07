package c371g2.ltc_safety.a_detail;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.ReturnCode;
import c371g2.ltc_safety.a_main.MainViewModelTests;
import c371g2.ltc_safety.a_main.MainViewModel_TestHook;
import c371g2.ltc_safety.a_new.NewConcernActivity;
import c371g2.ltc_safety.a_new.NewConcernViewModel_TestHook;

import static junit.framework.Assert.assertTrue;

/**
 * This class runs tests on the methods in the ConcernDetailViewModel class. Requires device to run.
 */
@RunWith(AndroidJUnit4.class)
public class ConcernDetailViewModelTests {

    @Rule
    public ActivityTestRule<ConcernDetailActivity> activityRule = new ActivityTestRule<>(ConcernDetailActivity.class, true, false);

    @Rule
    public ActivityTestRule<NewConcernActivity> newConcernRule = new ActivityTestRule<>(NewConcernActivity.class);

    private ConcernDetailActivity activity;
    private ConcernDetailViewModel concernDetailViewModel;

    @Test
    public void test_retractUnsubmittedConcern() throws InterruptedException {
        MainViewModel_TestHook.instance.clearConcernList();
        MainViewModel_TestHook.instance.addConcern(
                MainViewModelTests.generateConcernForTest(
                        "Jeff",
                        "3213884",
                        "PixieGod@email.com",
                        "UofS",
                        "B102",
                        "Near Miss",
                        "None."
                )
        );

        Intent i = new Intent();
        i.putExtra("concern-index",0);
        activity = activityRule.launchActivity(i);
        concernDetailViewModel = new ConcernDetailViewModel(activity);
        concernDetailViewModel.getConcern(0);
        concernDetailViewModel.retractConcern();
        concernDetailViewModel.signalLatch.await(20, TimeUnit.SECONDS);
        assertTrue(concernDetailViewModel.submissionReturnCode.equals(ReturnCode.IOEXCEPTION_THROWN_BY_API));

        activity.finish();
    }

    @Test
    public void test_retractSubmittedConcern() throws InterruptedException {
        MainViewModel_TestHook.instance.clearConcernList();
        NewConcernActivity nca = newConcernRule.getActivity();
        boolean submitSuccessful = NewConcernViewModel_TestHook.instance.submitConcern(
                nca,
                "Near Miss",
                "None.",
                "UofS",
                "B102",
                "Retracting",
                "valid@email.com",
                "3213884"
        );
        assertTrue("The submission via the NewConcernViewModel_TestHook failed! Unable to test retractSubmittedConcern()",submitSuccessful);
        nca.finish();

        Intent i = new Intent();
        i.putExtra("concern-index",0);
        activity = activityRule.launchActivity(i);
        activity.concernDetailViewModel.retractConcern();
        activity.concernDetailViewModel.signalLatch.await(20, TimeUnit.SECONDS);
        assertTrue("The return code for the retraction was null", activity.concernDetailViewModel.submissionReturnCode!=null);
        assertTrue("The return code for the retraction was not SUCCESS",ReturnCode.SUCCESS.equals(activity.concernDetailViewModel.submissionReturnCode));
        activity.finish();
    }
}
