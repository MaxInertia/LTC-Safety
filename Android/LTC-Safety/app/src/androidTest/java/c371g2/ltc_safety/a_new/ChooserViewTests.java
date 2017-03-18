package c371g2.ltc_safety.a_new;

import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * The class tests the ChooserView class which sets up popup ComboBoxes from AlertDialogs.
 */
public class ChooserViewTests {

    @Rule
    public ActivityTestRule<NewConcernActivity> newConcernRule = new ActivityTestRule<>(NewConcernActivity.class, true, false);

    @Test
    public void test_setup() {
        //TextView textView = Mockito.mock(TextView.class);
        NewConcernActivity newConcernActivity = newConcernRule.launchActivity(new Intent());
        TextView textView = new TextView(newConcernActivity);
        String[] items = {"A","B","C"};
        ChooserView.setup(
                textView,
                "Title",
                items
        );
        assertTrue("View was not assigned an onClickListener.", textView.hasOnClickListeners());
        //ArgumentCaptor<View.OnClickListener> argument = ArgumentCaptor.forClass(View.OnClickListener.class);
        //verify(textView).setOnClickListener(argument.capture());
        //assertTrue(argument.getValue()!=null);
        newConcernActivity.finish();
    }

    /*@Test
    public void test_chooserAction() {

        Context context = Mockito.mock(Context.class);
        TextView textView = new TextView(context);
        //TextView textView = new TextView(context);
        String[] items = {"A","B","C"};
        ChooserView.setup(
                textView,
                "Title",
                items
        );

        textView.performClick();

        //ArgumentCaptor<View.OnClickListener> argument = ArgumentCaptor.forClass(View.OnClickListener.class);
        //verify(textView).setOnClickListener(argument.capture());
    }*/

}
