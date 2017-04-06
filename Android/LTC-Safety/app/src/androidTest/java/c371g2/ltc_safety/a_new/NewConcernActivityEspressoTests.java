package c371g2.ltc_safety.a_new;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.Suppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_detail.ConcernDetailActivity;
import c371g2.ltc_safety.a_main.ConcernSubmissionObserver;
import c371g2.ltc_safety.a_main.MainActivity;
import c371g2.ltc_safety.a_main.MainViewModelTests;
import c371g2.ltc_safety.a_main.MainViewModel_TestHook;
import c371g2.ltc_safety.a_new.NewConcernActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static c371g2.ltc_safety.R.id.newConcern_submitConcernButton;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsAnything.anything;

/**
 * This class runs Espresso tests on NewConcernActivity.
 *
 * The tests here assume that after a successful concern submission the user is returned to MainActivity.
 */
@Suppress
@RunWith(AndroidJUnit4.class)
public class NewConcernActivityEspressoTests {

    @Rule
    public ActivityTestRule<NewConcernActivity> ncActivityRule = new ActivityTestRule<>(NewConcernActivity.class);

    NewConcernActivity activity;

    @Before
    public void setup() {
        ConcernSubmissionObserver observer = MainActivity.Test_Hook.getNewMainViewModel();
        Intent i = new Intent();
        i.putExtra("observer",observer);
        activity = ncActivityRule.launchActivity(i);
    }

    /**
     * Press the submit concern button. The activity should not change.
     * The test passes if the view does not change and fails if it does.
     *
     * This test is to ensure that future modifications to the codebase do not allow concerns to be
     * submitted with no data. It succeeds prior to anything being implemented because concern
     * submission itself has yet to be implemented.
     */
    @Test
    public void testSubmitConcernButton_withEmptyFields() {
        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());
        onView(withText("Missing required field")).check(matches(isDisplayed()));
        onView(withText(" -  Name\n -  Phone or Email\n -  Concern Nature\n -  Facility")).check(matches( isDisplayed()));
    }

    /**
     * This test confirms that a concern can not be submitted when no phone number or email is
     * provided. The test passes if the view does not change and fails if it does.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptContactMethods() {
        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Brandon Blue"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));
        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());
        onView(withText("Missing required field")).check(matches(isDisplayed()));
        onView(withText(" -  Phone or Email")).check(matches( isDisplayed()));
    }

    /**
     * This test confirms that a concern can not be submitted when no name is provided. The test
     * passes if the view does not change and fails if it does.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptName() {
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());
        onView(withText("Missing required field")).check(matches(isDisplayed()));
        onView(withText(" -  Name")).check(matches( isDisplayed()));
    }

    /**
     * Press the submit concern button. The activity should change, loading the activity_new_concern
     * view. The test passes if the view changes to the view-concern activity and fails if it does
     * not.
     */
    @Test
    public void testSubmitConcernButton_withCompletedFields() throws InterruptedException {

        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Frank Stein"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());

        NewConcernActivity.Test_Hook.getViewModel(activity).signalLatch.await(20, TimeUnit.SECONDS);

        onView(withText("Your concern has been submitted")).check(matches(isDisplayed()));
    }

    /**
     * This test confirms that a concern can be submitted when an email is provided, but no phone
     * number. The test passes if the view changes to the view-concern activity and fails if it does
     * not.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptPhone() throws InterruptedException {

        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Jenny"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));


        onView(withId(newConcern_submitConcernButton)).perform(closeSoftKeyboard(), scrollTo(), click());

        NewConcernActivity.Test_Hook.getViewModel(activity).signalLatch.await(20, TimeUnit.SECONDS);

        onView(withText("Your concern has been submitted")).check(matches(isDisplayed()));
    }

    /**
     * This test confirms that a concern can be submitted when a phone number is provided, but no
     * email. The test passes if the view changes to the view-concern activity and fails if it does
     * not.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptEmail() throws InterruptedException {

        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Christian Bale"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(closeSoftKeyboard(), scrollTo(), click());

        NewConcernActivity.Test_Hook.getViewModel(activity).signalLatch.await(20, TimeUnit.SECONDS);

        onView(withText("Your concern has been submitted")).check(matches(isDisplayed()));
    }

    /**
     * This test confirms that a concern can not be submitted when everything except the concern is provided. The test
     * passes if the view does not change and fails if it does.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptConcern() {
        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Butler"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());
        onView(withText("Missing required field")).check(matches(isDisplayed()));
        onView(withText(" -  Concern Nature")).check(matches( isDisplayed()));
    }

    /**
     * This test confirms that a concern can not be submitted when no facility is provided. The test
     * passes if the view does not change and fails if it does.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptFacility() {
        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Artemis Fowl"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());
        onView(withText("Missing required field")).check(matches(isDisplayed()));
        onView(withText(" -  Facility")).check(matches( isDisplayed()));
    }

    /**
     * Press the submit concern button. The activity should change, loading the activity_new_concern
     * view. The test passes if the view changes to the view-concern activity and fails if it does
     * not.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptRoom() throws InterruptedException {

        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Blondy"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());


        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());

        NewConcernActivity.Test_Hook.getViewModel(activity).signalLatch.await(20, TimeUnit.SECONDS);

        onView(withText("Your concern has been submitted")).check(matches(isDisplayed()));
    }

    /**
     * Press the submit concern button. The activity should change, loading the activity_new_concern
     * view. The test passes if the view changes to the view-concern activity and fails if it does
     * not.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptDescription() throws InterruptedException {

        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("The short one"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());

        NewConcernActivity.Test_Hook.getViewModel(activity).signalLatch.await(20, TimeUnit.SECONDS);

        onView(withText("Your concern has been submitted")).check(matches(isDisplayed()));
    }

    /**
     * Press the submit concern button. The activity should change, loading the activity_new_concern
     * view. The test passes if the view changes to the view-concern activity and fails if it does
     * not.
     */
    @Test
    public void testSubmitConcernButton_withAllFieldsExceptActions() throws InterruptedException {

        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Serious Black"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());

        NewConcernActivity.Test_Hook.getViewModel(activity).signalLatch.await(20, TimeUnit.SECONDS);

        onView(withText("Your concern has been submitted")).check(matches(isDisplayed()));
    }


    /**
     * Press the submit concern button. The activity should change, loading the activity_new_concern
     * view. The test passes if the view changes to the view-concern activity and fails if it does
     * not.
     */
    @Test
    public void testSubmitConcernButton_withExtendedASCIIActionTakenField() throws InterruptedException {

        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Albus Dumbledore"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Infection control"))).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));

        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));
        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), replaceText("见 見 败 敗"));
        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());

        NewConcernActivity.Test_Hook.getViewModel(activity).signalLatch.await(20, TimeUnit.SECONDS);

        onView(withText("Your concern has been submitted")).check(matches(isDisplayed()));
    }

    @Test
    public void testSubmitConcernButton_withOtherConcern() throws InterruptedException {
        onView(withId(R.id.newConcern_nameTextField)).perform(typeText("Hermione Granger"));
        onView(withId(R.id.newConcern_phoneNumberField)).perform(typeText("1231231234"));
        onView(withId(R.id.newConcern_emailTextField)).perform(typeText("Valid_Format@Address.com"));

        onView(withId(R.id.newConcern_concernNatureChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Other"))).perform(click());

        ViewInteraction editText = onView(allOf(withClassName(is("android.widget.EditText")),isDisplayed()));
        editText.perform(typeText("Zombies"));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.newConcern_facilityChooser)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("Oliver Lodge"))).perform(click());

        onView(withId(R.id.newConcern_roomNumberField)).perform(scrollTo(), typeText("40"));
        onView(withId(R.id.newConcern_descriptionField)).perform(scrollTo(), typeText("Nada!"));
        onView(withId(R.id.newConcern_actionsTakenField)).perform(scrollTo(), typeText("Nada!"));

        onView(withId(newConcern_submitConcernButton)).perform(scrollTo(), click());
        NewConcernActivity.Test_Hook.getViewModel(activity).signalLatch.await(20, TimeUnit.SECONDS);
        onView(withText("Your concern has been submitted")).check(matches(isDisplayed()));
    }

    @Test
    public void testBackNav() {
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()));
    }

}
