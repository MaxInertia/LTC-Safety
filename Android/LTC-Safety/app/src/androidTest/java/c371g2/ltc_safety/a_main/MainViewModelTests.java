package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.appspot.ltc_safety.client.model.OwnerToken;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.a_new.NewConcernActivity;
import c371g2.ltc_safety.a_new.NewConcernViewModel_TestHook;

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

    @Before
    public void setup() {
        // This confirms that the only Concerns on the device for a given test in this class are the
        // concerns added in that test.
        MainViewModel_TestHook.instance.clearConcernList();
        SharedPreferences memory = newConcernRule.getActivity().getSharedPreferences(DeviceStorage.CONCERN_SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor memoryEditor = memory.edit();
        memoryEditor.clear().commit();
    }

    @Test
    public void test_updateConcerns_withNoStoredConcerns() throws InterruptedException {
        Intent i = new Intent();
        i.putExtra("testing",true);
        mActivity.launchActivity(i);
        MainActivity activity = mActivity.getActivity();
        activity.viewModel.updateConcerns(activity.getBaseContext());

        activity.viewModel.signalLatch.await(20, TimeUnit.SECONDS);
        assertTrue("The return code was not NULL_POINTER, it was "+activity.viewModel.fetchReturnCode, FetchReturnCode.NULL_POINTER.equals(activity.viewModel.fetchReturnCode));
        activity.finish();
    }

    @Test
    public void test_updateConcerns_withSubmittedConcerns() throws InterruptedException {
        newConcernRule.launchActivity(new Intent());
        NewConcernActivity nca = newConcernRule.getActivity();
        boolean submitSuccessful = NewConcernViewModel_TestHook.instance.submitConcern(
                nca,
                "Near Miss",
                "None.",
                "",
                "UofS",
                "B101",
                "Drake Benet",
                "Test@valid.ca",
                "1231231234"
        );
        assertTrue("The submission via the NewConcernViewModel_TestHook failed! Unable to test updateSubmittedConcern()",submitSuccessful);
        nca.finish();

        Intent i = new Intent();
        i.putExtra("testing",true);
        mActivity.launchActivity(i);
        MainActivity activity = mActivity.getActivity();
        activity.viewModel.updateConcerns(activity.getBaseContext());
        activity.viewModel.signalLatch.await(20, TimeUnit.SECONDS);
        assertTrue("The return code for the retraction was null", activity.viewModel.fetchReturnCode!=null);
        assertTrue("The return code for the retraction was not SUCCESS, it was "+activity.viewModel.fetchReturnCode,activity.viewModel.fetchReturnCode.equals(FetchReturnCode.SUCCESS));
        activity.finish();
    }

    @Test
    public void test_ConcernUpdaterClass() {
        Intent i = new Intent();
        i.putExtra("testing",true);
        mActivity.launchActivity(i);
        MainActivity activity = mActivity.getActivity();

        MainViewModel.ConcernUpdater updater = activity.viewModel.new ConcernUpdater();
        ArrayList<OwnerToken> tokens = updater.getStoredOwnerTokens();

        System.out.println("[MainViewModelTests]\tToken count: " + tokens.size());
        for(OwnerToken t: tokens) {
            try {
                System.out.println("[MainViewModelTests]\t"+t.toPrettyString());
            } catch(IOException ioException) {
                ioException.printStackTrace();
                activity.finish();
                fail();
            }
        }
    }
}
