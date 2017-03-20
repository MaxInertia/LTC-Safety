package c371g2.ltc_safety.a_main;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_detail.ConcernDetailActivity;
import c371g2.ltc_safety.a_new.NewConcernActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static c371g2.ltc_safety.R.id.newConcern_submitConcernButton;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsAnything.anything;

/**
 * This class runs Espresso tests on MainActivity.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity activity;

    /**
     * Confirms that the new concern button is clickable.
     */
    @Test
    public void testNewConcernButton_isClickable() {
        //mActivityRule.launchActivity(null);
        onView(withId(R.id.main_newConcernButton)).check(matches(isClickable()));
    }

    /**
     * Press the new concern button. The activity should change, loading the activity_new_concern view.
     * The test passes if this view appears and fails if it does not.
     */
    @Test
    public void testNewConcernButton_changesActivity() {
        //mActivityRule.launchActivity(null);
        onView(withId(R.id.main_newConcernButton)).perform(click());
        onView(withId(R.id.activity_new_concern)).check(matches(isDisplayed()));
    }

    /**
     * Attempt refreshing concerns with no concerns on device
     */
    @Test
    public void testConcernUpdateButton_WithNoConcerns() {
        mActivityRule.getActivity().finish();
        MainViewModel observer = MainActivity.Test_Hook.getNewMainViewModel();
        Intent i = new Intent();
        i.putExtra("observer", observer);
        activity = mActivityRule.launchActivity(i);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("concerns", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        onView(withId(R.id.action_reload_concerns)).perform(click());

        assertTrue(MainActivity.Test_Hook.getMainViewModel(activity).fetchReturnCode.equals(FetchReturnCode.NO_CONCERNS));
    }

    /**
     * Attempt refreshing concerns with concerns on device
     */
    @Test
    public void testConcernUpdateButton_WithConcerns() throws InterruptedException {
        activity = mActivityRule.getActivity();

        Instrumentation.ActivityMonitor activityMonitor = InstrumentationRegistry
                .getInstrumentation().addMonitor(NewConcernActivity.class.getName(),null,false);
        onView(withId(R.id.main_newConcernButton)).perform(click());
        NewConcernActivity ncActivity = (NewConcernActivity) InstrumentationRegistry
                .getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);


        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Dorian Gray"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));
        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());
        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());
        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));
        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));
        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        activityMonitor = InstrumentationRegistry
                .getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());
        ((AbstractNetworkViewModel)NewConcernActivity.Test_Hook.getViewModel(ncActivity))
                .signalLatch.await(20, TimeUnit.SECONDS);
        Espresso.pressBack();
        activity = (MainActivity) InstrumentationRegistry
                .getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);

        onView(withId(R.id.action_reload_concerns)).perform(click());
        MainActivity.Test_Hook.getMainViewModel(activity).signalLatch
                .await(20, TimeUnit.SECONDS);

        onView(withText("Concern statuses were updated")).check(matches(isDisplayed()));
    }

    //TODO: Write tests that can verify ListView item clicks behave as expected
}