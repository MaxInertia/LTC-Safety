package c371g2.ltc_safety.a_detail;

import android.content.Intent;
import android.support.test.filters.Suppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.a_main.MainViewModelTests;
import c371g2.ltc_safety.a_main.MainViewModel_TestHook;

import static junit.framework.Assert.assertTrue;

/**
 *
 */

@RunWith(AndroidJUnit4.class)
public class ConcernDetailViewModelTests {

    @Rule
    public ActivityTestRule<ConcernDetailActivity> activityRule = new ActivityTestRule<>(ConcernDetailActivity.class, true, false);

    private ConcernDetailActivity activity;
    private ConcernDetailViewModel concernDetailViewModel;

    @Before
    public void beforeEach() {

        MainViewModel_TestHook.instance.setAsOnlyConcern(
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
    }

    @Test
    public void test_retract_concern() throws InterruptedException {
        concernDetailViewModel.getConcern(0);
        concernDetailViewModel.retractConcern();
        concernDetailViewModel.signalLatch.await(20, TimeUnit.SECONDS);

        assertTrue(concernDetailViewModel.submissionReturnCode.equals(ReturnCode.NOERROR));
    }

    @After
    public void tearDown() {
        activity.finish();
    }
}
