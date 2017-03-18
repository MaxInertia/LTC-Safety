package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.appspot.ltc_safety.client.model.OwnerToken;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.a_new.NewConcernActivity;
import c371g2.ltc_safety.local.ConcernWrapper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * This class runs tests on the methods in the MainViewModel class. Requires device to run.
 */
@RunWith(AndroidJUnit4.class)
public class MainViewModelTests {

    @ClassRule
    public static ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class, true, false);

    @Rule
    public ActivityTestRule<NewConcernActivity> newConcernRule = new ActivityTestRule<>(NewConcernActivity.class);

    private MainActivity activity;
    private MainViewModel mainViewModel;

    @Before
    public void setup() {
        // This confirms that the only Concerns on the device for a given test in this class are the
        // concerns added in that test.
        SharedPreferences memory = newConcernRule.getActivity().getSharedPreferences(
                DeviceStorage.CONCERN_SHARED_PREF_KEY, Context.MODE_PRIVATE
        );
        SharedPreferences.Editor memoryEditor = memory.edit();
        memoryEditor.clear().commit();

        Intent i = new Intent();
        i.putExtra("testing",true);
        activity = mActivity.launchActivity(i);
        mainViewModel = MainActivity.Test_Hook.getMainViewModel(activity);
    }

    @After
    public void cleanup() {
        activity.finish();

    }

    @Test
    public void test_updateConcerns_withNoStoredConcerns() throws InterruptedException {
        MainViewModel.Test_Hook.updateConcerns(mainViewModel);

        assertTrue(
                "The return code was not NO_CONCERNS, it was "+mainViewModel.fetchReturnCode,
                FetchReturnCode.NO_CONCERNS.equals(mainViewModel.fetchReturnCode)
        );
    }

    @Test
    public void test_updateConcerns_withSubmittedConcerns() throws InterruptedException {
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
        boolean submitSuccessful = NewConcernActivity.Test_Hook.submitConcern(
                activity,
                mainViewModel,
                testConcern
        );
        assertTrue("The submission via the NewConcernViewModel_TestHook failed! Unable to test updateSubmittedConcern()", submitSuccessful);

        MainViewModel.Test_Hook.updateConcerns(mainViewModel);
        mainViewModel.signalLatch.await(20, TimeUnit.SECONDS);
        assertTrue("The return code for the retraction was null", mainViewModel.fetchReturnCode != null);
        assertTrue(
                "The return code for the retraction was not SUCCESS, it was " + mainViewModel.fetchReturnCode,
                mainViewModel.fetchReturnCode.equals(FetchReturnCode.SUCCESS)
        );
    }

    @Test
    public void test_ConcernUpdaterClass() {
        MainViewModel.ConcernUpdater updater = mainViewModel.new ConcernUpdater();
        ArrayList<OwnerToken> tokens = updater.getStoredOwnerTokens();

        System.out.println("[MainViewModelTests]\tToken count: " + tokens.size());
        if(tokens.size()==0) return;
        for(OwnerToken t: tokens) {
            try {
                assertTrue(t.toPrettyString()!="");
                System.out.println("[MainViewModelTests]\t"+t.toPrettyString());
            } catch(IOException ioException) {
                ioException.printStackTrace();
                fail();
            }
        }
    }

    @Test
    public void test_getSortedListOrder() throws InterruptedException {
        ConcernWrapper concern_0 = ConcernWrapper.Test_Hook.getEmptyConcern();
        MainViewModel.Test_Hook.addConcern(mainViewModel,concern_0);
        Thread.sleep(20);
        ConcernWrapper concern_1 = ConcernWrapper.Test_Hook.getEmptyConcern();
        MainViewModel.Test_Hook.addConcern(mainViewModel,concern_1);
        Thread.sleep(20);
        ConcernWrapper concern_2 = ConcernWrapper.Test_Hook.getEmptyConcern();
        MainViewModel.Test_Hook.addConcern(mainViewModel,concern_2);

        List<ConcernWrapper> concerns = mainViewModel.getSortedConcernList();

        // Concerns added at later times should be at lower indices in the list.
        assertEquals(concern_0.getSubmissionDate(), concerns.get(2).getSubmissionDate());
        assertEquals(concern_1.getSubmissionDate(), concerns.get(1).getSubmissionDate());
        assertEquals(concern_2.getSubmissionDate(), concerns.get(0).getSubmissionDate());
    }

}
