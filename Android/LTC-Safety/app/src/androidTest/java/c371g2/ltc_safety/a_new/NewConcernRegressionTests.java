package c371g2.ltc_safety.a_new;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import c371g2.ltc_safety.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;

/**
 * Tests that cover the bugs discovered in the NewConcernActivity.
 */
@RunWith(AndroidJUnit4.class)
public class NewConcernRegressionTests {

    @Rule
    public ActivityTestRule<NewConcernActivity> mActivityRule = new ActivityTestRule<>(NewConcernActivity.class);

    @Test
    public void scrollBug1() {
        mActivityRule.launchActivity(new Intent());
        // Increase view height and enable scrolling
        onView(withId(R.id.newConcern_actionsTakenField)).perform(typeText("\n\n\n\n\n\n\n\n\n\n"));
        // Scroll back to concernNatureChooser
        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo());
        // Scroll down with initial screen touch being on the concernNatureChooser
        onView(withId(R.id.newConcern_concernNatureChooser)).perform(swipeDown());

        try {
            onView(withId(R.id.activity_new_concern)).check(matches(isDisplayed()));
        } catch(NoMatchingViewException noMatchingViewException) {
            fail("The NewConcernAcitivity view is not visible after scrolling via initial touch on concernNatureChooser.");
        }
    }

    @Test
    public void scrollBug2() {
        mActivityRule.launchActivity(new Intent());
        // Increase view height and enable scrolling
        onView(withId(R.id.newConcern_actionsTakenField)).perform(typeText("\n\n\n\n\n\n\n\n\n\n"));
        // Scroll back to facilityChooser
        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo());
        // Scroll down with initial screen touch being on the facilityChooser
        onView(withId(R.id.newConcern_facilityChooser)).perform(swipeDown());

        try {
            onView(withId(R.id.activity_new_concern)).check(matches(isDisplayed()));
        } catch(NoMatchingViewException noMatchingViewException) {
            fail("The NewConcernAcitivity view is not visible after scrolling via initial touch on facilityChooser.");
        }
    }
}
