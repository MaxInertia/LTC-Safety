package c371g2.ltc_safety.a_detail;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.R;
import c371g2.ltc_safety.a_main.DeviceStorageTests;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * This class is used to test the ConcernDetailActivity class. This test class requires a device (real
 * or emulated) to run.
 */
@RunWith(AndroidJUnit4.class)
public class ConcernDetailActivityTests {

    @Rule
    public ActivityTestRule<ConcernDetailActivity> mActivity = new ActivityTestRule<>(ConcernDetailActivity.class,true,false);

    private ConcernWrapper concern;
    private AbstractNetworkActivity concernDetailActivity;

    @Before
    public void setup() {
         concern = DeviceStorageTests.generateConcernForTest(
                "Jeff",
                "3213884",
                "PixieGod@email.com",
                "UofS",
                "B102",
                "Near Miss",
                "None.",
                 "Description here!"
        );

        Intent i = new Intent();
        i.putExtra("concern",concern);
        mActivity.launchActivity(i);

        concernDetailActivity = mActivity.getActivity();
    }

    // Contact information tests

    @Test
    public void test_field_name() {
         TextView tView = ((TextView) ((RelativeLayout)((LinearLayout) concernDetailActivity.findViewById(
                R.id.detailedConcern_contactInformation
        )).getChildAt(0)).getChildAt(1));
        assertNotNull(tView);

        String nameFieldString = tView.getText().toString();
        assertEquals(nameFieldString,concern.getReporterName());
    }

    @Test
    public void test_field_phoneNumber() {
        TextView tView = ((TextView) ((RelativeLayout)((LinearLayout) concernDetailActivity.findViewById(
                R.id.detailedConcern_contactInformation
        )).getChildAt(4)).getChildAt(1));
        assertNotNull(tView);

        String phoneNumberString = tView.getText().toString();
        assertEquals(phoneNumberString, concern.getReporterPhone());
    }

    @Test
    public void test_field_email() {
        TextView tView = ((TextView) ((RelativeLayout)((LinearLayout) concernDetailActivity.findViewById(
                R.id.detailedConcern_contactInformation
        )).getChildAt(2)).getChildAt(1));
        assertNotNull(tView);

        String emailFieldString = tView.getText().toString();
        assertEquals(emailFieldString, concern.getReporterEmail());
    }

    // Concern information tests

    @Test
    public void test_field_concernNature() {
        TextView tView = ((TextView) ((RelativeLayout)((LinearLayout) concernDetailActivity.findViewById(
                R.id.detailedConcern_concernInformation
        )).getChildAt(0)).getChildAt(1));

        assertNotNull(tView);

        String concernNatureString = tView.getText().toString();
        assertEquals(concernNatureString, concern.getConcernType());
    }

    @Test
    public void test_field_facilityName() {
        TextView tView = ((TextView) ((RelativeLayout)((LinearLayout)  concernDetailActivity.findViewById(
                R.id.detailedConcern_concernInformation
        )).getChildAt(2)).getChildAt(1));
        assertNotNull(tView);

        String facilityNameString = tView.getText().toString();
        assertEquals(facilityNameString, concern.getFacilityName());
    }

    @Test
    public void test_field_roomName() {
        TextView tView = ((TextView) ((RelativeLayout)((LinearLayout)  concernDetailActivity.findViewById(
                R.id.detailedConcern_concernInformation
        )).getChildAt(4)).getChildAt(1));
        assertNotNull(tView);

        String roomFieldString = tView.getText().toString();
        assertEquals(roomFieldString, concern.getRoomName());
    }

    @Test
    public void test_field_description() {
        TextView tView = ((TextView) ((RelativeLayout)((LinearLayout)  concernDetailActivity.findViewById(
                R.id.detailedConcern_concernInformation
        )).getChildAt(6)).getChildAt(1));
        assertNotNull(tView);

        String descriptionString = tView.getText().toString();
        assertEquals(descriptionString, concern.getDescription());
    }

    @Test
    public void test_field_actionsTaken() {
         TextView tView = ((TextView) ((RelativeLayout)((LinearLayout)  concernDetailActivity.findViewById(
                R.id.detailedConcern_concernInformation
        )).getChildAt(8)).getChildAt(1));
        assertNotNull(tView);

        String actionsTakenString = tView.getText().toString();
        assertEquals(actionsTakenString, concern.getActionsTaken());
    }

    // Status test

    @Test
    public void test_statusList() {
        List statuses = concern.getStatuses();

        for(int i=0; i<concern.getStatuses().size(); i=i+2) {
            String statusType = ((TextView)((RelativeLayout)((LinearLayout) concernDetailActivity.findViewById(
                    R.id.detailedConcern_statusLayout
            )).getChildAt(i)).getChildAt(0)).getText().toString();

            assertEquals(statusType, ((StatusWrapper)statuses.get(i)).getType());
        }
    }
}
