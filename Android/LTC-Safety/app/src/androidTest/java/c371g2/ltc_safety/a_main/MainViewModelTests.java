package c371g2.ltc_safety.a_main;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.appspot.ltc_safety.client.model.Concern;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.ConcernStatus;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.OwnerToken;
import com.appspot.ltc_safety.client.model.Reporter;
import com.google.api.client.util.DateTime;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import c371g2.ltc_safety.local.ConcernWrapper;

import static junit.framework.Assert.fail;

/**
 * This class runs tests on the methods in the MainViewModel class. Requires device to run.
 */
@RunWith(AndroidJUnit4.class)
public class MainViewModelTests {

    @ClassRule
    public static ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        mActivity.launchActivity(new Intent());
    }

    @Test
    public void test_writeAndReadDeviceData() {
        String reporterName = "Reporter";
        String phoneNumber = "Phone";
        String emailAddress = "Email";
        String facilityName = "Facility";
        String roomName = "B102";
        String concernType = "Type";
        String actionsTaken = "Actions";

        // The following values do not need to be valid. The data that is used to create concerns
        // in the NewConcernViewModel is
        ConcernWrapper concern = generateConcernForTest(
                reporterName,
                phoneNumber,
                emailAddress,
                facilityName,
                roomName,
                concernType,
                actionsTaken
        );

        Activity activity = mActivity.getActivity();
        MainViewModel.saveConcern(activity.getBaseContext(), concern);

        // If the list is null or has no elements, save failed.
        ArrayList<ConcernWrapper> concerns = MainViewModel.loadConcerns(activity.getBaseContext());
        if(concerns==null || concerns.size()==0){
            fail();
        }

        // If the list does not contain the concern we attempted to save then the save failed.
        for(ConcernWrapper c: concerns) {
            if(reporterName.equals(c.getReporterName()) &&
                    phoneNumber.equals(c.getReporterPhone()) &&
                    emailAddress.equals(c.getReporterEmail()) &&
                    facilityName.equals(c.getFacilityName()) &&
                    roomName.equals(c.getRoomName()) &&
                    concernType.equals(c.getConcernType()) &&
                    actionsTaken.equals(c.getActionsTaken())) {
                return;
            }
        }
        fail();
    }

    public static ConcernWrapper generateConcernForTest(String reporterName, String phoneNumber, String emailAddress,
                                                        String facilityName, String roomName, String concernType,
                                                        String actionsTaken) {
        ConcernData concernData = new ConcernData();
        concernData.setConcernNature(concernType);
        concernData.setActionsTaken(actionsTaken);

        Location facility = new Location();
        facility.setFacilityName(facilityName);
        facility.setRoomName(roomName);
        concernData.setLocation(facility);

        Reporter reporter = new Reporter();
        reporter.setName(reporterName);
        reporter.setPhoneNumber(phoneNumber);
        reporter.setEmail(emailAddress);
        concernData.setReporter(reporter);

        ArrayList<ConcernStatus> statuses = new ArrayList<>();
        ConcernStatus status = new ConcernStatus();
        status.setType("PENDING");
        status.setCreationDate(new DateTime(1));
        statuses.add(status);

        Concern clientConcern = new Concern();
        clientConcern.setData(concernData);
        clientConcern.setSubmissionDate(new DateTime(1));
        clientConcern.setStatuses(statuses);

        return new ConcernWrapper(clientConcern,new OwnerToken());
    }
}
