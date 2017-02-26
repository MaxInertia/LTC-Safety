package c371g2.ltc_safety.a_detail;

import android.content.Intent;
import android.support.test.filters.Suppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 *
 */
@Suppress
@RunWith(AndroidJUnit4.class)
public class ConcernDetailViewModelTests {

    @Rule
    public ActivityTestRule<ConcernDetailActivity> activityRule = new ActivityTestRule<>(ConcernDetailActivity.class);

    private ConcernDetailActivity activity;
    private ConcernDetailViewModel newConcernViewModel;

    @Before
    public void beforeEach() {
        activity = activityRule.launchActivity(new Intent());
        newConcernViewModel = new ConcernDetailViewModel(activity);
    }

    @After
    public void tearDown() {
        activity.finish();
    }
}
