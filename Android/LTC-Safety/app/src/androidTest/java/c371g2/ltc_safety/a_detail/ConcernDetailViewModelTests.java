package c371g2.ltc_safety.a_detail;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.a_main.ConcernRetractionObserver;
import c371g2.ltc_safety.a_main.ConcernSubmissionObserver;
import c371g2.ltc_safety.a_main.DeviceStorageTests;
import c371g2.ltc_safety.a_main.MainActivity;
import c371g2.ltc_safety.a_main.MainViewModel_TestHook;
import c371g2.ltc_safety.a_new.NewConcernActivity;
import c371g2.ltc_safety.local.ConcernWrapper;

import static junit.framework.Assert.assertTrue;

/**
 * This class runs tests on the methods in the ConcernDetailViewModel class. Requires device to run.
 */
@RunWith(AndroidJUnit4.class)
public class ConcernDetailViewModelTests {
    @Rule
    public ActivityTestRule<ConcernDetailActivity> concernDetailRule = new ActivityTestRule<>(ConcernDetailActivity.class, true, false);
    @Rule
    public ActivityTestRule<NewConcernActivity> newConcernRule = new ActivityTestRule<>(NewConcernActivity.class, true, false);
    @Rule
    public ActivityTestRule<MainActivity> mainRule = new ActivityTestRule<>(MainActivity.class, true, false);

    private ConcernDetailActivity activity;
    private static ConcernWrapper testConcern;
    private ConcernRetractionObserver mainViewModel;

    @BeforeClass
    public static void setup() {
        testConcern = DeviceStorageTests.generateConcernForTest(
                "Jeff",
                "3213884",
                "PixieGod@email.com",
                "UofS",
                "B102",
                "Near Miss",
                "None.",
                "Description"
        );
    }

    @Before
    public void beforeEach() {
        // Initialize MainViewModel
        //mainViewModel = MainViewModel_TestHook.instance.getMainViewModelInstance();
        mainViewModel = MainActivity.Test_Hook.getNewMainViewModel();
        assert(mainViewModel != null);
    }
    
    @After
    public void cleanUp() {
        //MainViewModel_TestHook.instance.clearConcernList(mainViewModel);
    }

    @Test
    public void test_retractUnsubmittedConcern() throws InterruptedException {
        // Retract concern
        Intent i = new Intent();
        i.putExtra("concern",testConcern);
        i.putExtra("observer",((Serializable) mainViewModel));
        activity = concernDetailRule.launchActivity(i);
        ConcernDetailViewModel concernDetailViewModel = ConcernDetailActivity.Test_Hook.getConcernDetailViewModel(activity);
        concernDetailViewModel.retractConcern();
        concernDetailViewModel.signalLatch.await(20, TimeUnit.SECONDS);
        assertTrue(concernDetailViewModel.retractionReturnCode.equals(RetractionReturnCode.IOEXCEPTION_THROWN_BY_API));
        activity.finish();
    }

    @Test
    public void test_retractSubmittedConcern() throws InterruptedException {
        // Submit concern
        Intent i = new Intent();
        i.putExtra("observer",((Serializable)mainViewModel));
        NewConcernActivity ncActivity = newConcernRule.launchActivity(i);
        boolean submitSuccessful = NewConcernActivity.Test_Hook.submitConcern(
                ncActivity,
                ((ConcernSubmissionObserver) mainViewModel),
                testConcern
        );
        assertTrue("The submission via the NewConcernViewModel_TestHook failed! Unable to test retractSubmittedConcern()",submitSuccessful);
        ncActivity.finish();

        // Retract concern
        i = new Intent();
        i.putExtra("observer",mainViewModel);
        i.putExtra("concern",MainViewModel_TestHook.instance.getConcern(mainViewModel,0));
        activity = concernDetailRule.launchActivity(i);
        ConcernDetailViewModel concernDetailViewModel = ConcernDetailActivity.Test_Hook.getConcernDetailViewModel(activity);
        concernDetailViewModel.retractConcern();
        concernDetailViewModel.signalLatch.await(20, TimeUnit.SECONDS);
        assertTrue("The return code for the retraction was null", concernDetailViewModel.retractionReturnCode!=null);
        assertTrue("The return code for the retraction was not SUCCESS", RetractionReturnCode.SUCCESS.equals(concernDetailViewModel.retractionReturnCode));
        activity.finish();
    }
}
