package c371g2.ltc_safety.a_detail;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_main.MainViewModelTests;
import c371g2.ltc_safety.local.ConcernWrapper;

import static junit.framework.Assert.assertEquals;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class ConcernDetailActivityTests {

    @ClassRule
    public static ActivityTestRule<ConcernDetailActivity> mActivity = new ActivityTestRule<>(ConcernDetailActivity.class);
    public static ConcernWrapper concern;
    private Activity concernDetailActivity;

    @BeforeClass
    public static void setup() {
        String reporterName = "Jeff";
        String phoneNumber = "3213884";
        String emailAddress = "PixieGod@email.com";
        String facilityName = "UofS";
        String roomName = "B102";
        String concernType = "Near Miss";
        String actionsTaken = "None.";

        MainViewModelTests.generateConcernForTest(
                reporterName,
                phoneNumber,
                emailAddress,
                facilityName,
                roomName,
                concernType,
                actionsTaken
        );
        Intent i = new Intent();
        i.putExtra("concern-index",0);
        mActivity.launchActivity(i);

        int index = mActivity.getActivity().getIntent().getExtras().getInt("concern-index");
        concern = mActivity.getActivity().viewModel.getConcern(index);
}

    @Before
    public void beforeEach() {
        concernDetailActivity = mActivity.getActivity();
    }

    @Test
    public void test_field_name() {
        String nameFieldString = ((TextView) concernDetailActivity.findViewById(
                R.id.detailedConcern_nameField
        )).getText().toString();
        assertEquals(nameFieldString,concern.getReporterName());
    }

    @Test
    public void test_field_phoneNumber() {
        String phoneNumberString = ((TextView) concernDetailActivity.findViewById(
                R.id.detailedConcern_phoneField
        )).getText().toString();
        assertEquals(phoneNumberString, concern.getReporterPhone());
    }

    @Test
    public void test_field_email() {
        String emailFieldString = ((TextView) concernDetailActivity.findViewById(
                R.id.detailedConcern_emailField
        )).getText().toString();
        assertEquals(emailFieldString, concern.getReporterEmail());
    }

    @Test
    public void test_field_concernNature() {
        String concernNatureString = ((TextView) concernDetailActivity.findViewById(
                R.id.detailedConcern_concernNatureField
        )).getText().toString();
        assertEquals(concernNatureString, concern.getConcernType());
    }

    @Test
    public void test_field_facilityName() {
        String facilityNameString = ((TextView) concernDetailActivity.findViewById(
                R.id.detailedConcern_facilityNameField
        )).getText().toString();
        assertEquals(facilityNameString, concern.getFacilityName());
    }

    @Test
    public void test_field_roomName() {
        String roomFieldString = ((TextView) concernDetailActivity.findViewById(
                R.id.detailedConcern_roomField
        )).getText().toString();
        assertEquals(roomFieldString, concern.getRoomName());
    }

    @Test
    public void test_field_actionsTaken() {
        String actionsTakenString = ((TextView) concernDetailActivity.findViewById(
                R.id.detailedConcern_actionsTakenField
        )).getText().toString();
        assertEquals(actionsTakenString, concern.getReporterPhone());
    }

    @Test
    public void test_statusListSize() {
        ListView listView = (ListView) concernDetailActivity.findViewById(
                R.id.detailedConcern_statusList
        );
        assertEquals(listView.getAdapter().getCount(),concern.getStatuses().size());
    }
}
