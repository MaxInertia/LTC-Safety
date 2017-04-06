package c371g2.ltc_safety.a_detail;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.Suppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.AbstractNetworkViewModel;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_main.ConcernRetractionObserver;
import c371g2.ltc_safety.a_main.MainActivity;
import c371g2.ltc_safety.a_main.MainViewModel_TestHook;
import c371g2.ltc_safety.a_new.NewConcernActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static c371g2.ltc_safety.R.id.newConcern_submitConcernButton;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsAnything.anything;

/**
 * This class runs Espresso tests on MainActivity.
 */
@Suppress
@RunWith(AndroidJUnit4.class)
public class ConcernDetailEspressoTests {

    @Rule
    public ActivityTestRule<NewConcernActivity> nActivityRule = new ActivityTestRule<>(NewConcernActivity.class, true, false);

    private NewConcernActivity activity;

    @Before
    public void setup() {
        ConcernRetractionObserver observer = MainActivity.Test_Hook.getNewMainViewModel();
        Intent i = new Intent();
        i.putExtra("observer",observer);
        activity = nActivityRule.launchActivity(i);
    }

    /**
     * Confirm that each row in the concern list view is clickable.
     */
    @Test
    public void testRetractConcernListItem() throws InterruptedException {

        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Dorian Gray"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField))
                .perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());

        ((AbstractNetworkViewModel)NewConcernActivity.Test_Hook.getViewModel(activity))
                .signalLatch.await(20, TimeUnit.SECONDS);

        Espresso.pressBack();

        Instrumentation.ActivityMonitor activityMonitor = InstrumentationRegistry
                .getInstrumentation().addMonitor(ConcernDetailActivity.class.getName(),null,false);

        onData(anything()).inAdapterView(withId(R.id.main_concernListView))
                .atPosition(0).perform(click());

        ConcernDetailActivity concernDetailActivity = (ConcernDetailActivity) InstrumentationRegistry
                .getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);

        onView(withId(R.id.detailedConcern_retractButton)).perform(scrollTo(), click() );

        ConcernDetailActivity.Test_Hook.getConcernDetailViewModel(concernDetailActivity)
                .signalLatch.await(20, TimeUnit.SECONDS);

        onView(withText("Retraction successful")).check(matches(isDisplayed()));

        Espresso.pressBack();
        Espresso.pressBack();

        onData(anything()).inAdapterView(withId(R.id.main_concernListView))
                .atPosition(0).perform(click());

        onView(withId(R.id.detailedConcern_retractButton)).check(matches(not(isDisplayed())));

    }

    @Test
    public void testBackNav() {
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()));
    }

    //TODO: Write tests that can verify ListView item clicks behave as expected
}
