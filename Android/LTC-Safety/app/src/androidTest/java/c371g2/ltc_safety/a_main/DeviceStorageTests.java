package c371g2.ltc_safety.a_main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.appspot.ltc_safety.client.model.Concern;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.ConcernStatus;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.OwnerToken;
import com.appspot.ltc_safety.client.model.Reporter;
import com.google.api.client.util.DateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.TreeSet;

import c371g2.ltc_safety.local.ConcernWrapper;

import static junit.framework.Assert.fail;

/**
 * This class tests saving and loading Concerns to and from device memory.
 */
@RunWith(AndroidJUnit4.class)
public class DeviceStorageTests {

    @ClassRule
    public static ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);
    MainActivity activity;

    @Before
    public void setup() {
        activity = mActivity.launchActivity(new Intent());
    }

    @After
    public void cleanUp() {
        activity.finish();
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
        String description = "Description";

        // The following values do not need to be valid. The data that is used to create concerns
        // in the NewConcernViewModel is
        ConcernWrapper concern = generateConcernForTest(
                reporterName,
                phoneNumber,
                emailAddress,
                facilityName,
                roomName,
                concernType,
                actionsTaken,
                description
        );

        activity = mActivity.getActivity();
        DeviceStorage.saveConcern(activity.getBaseContext(), concern);

        // If the list is null or has no elements, save failed.
        TreeSet<ConcernWrapper> concerns = DeviceStorage.loadConcerns(activity.getBaseContext());
        if(concerns==null || concerns.size()==0){
            fail();
        }

        boolean successfullyLoaded = false;
        // If the list does not contain the concern we attempted to save then the save failed.
        for(ConcernWrapper c: concerns) {
            if(reporterName.equals(c.getReporterName()) &&
                    phoneNumber.equals(c.getReporterPhone()) &&
                    emailAddress.equals(c.getReporterEmail()) &&
                    facilityName.equals(c.getFacilityName()) &&
                    roomName.equals(c.getRoomName()) &&
                    concernType.equals(c.getConcernType()) &&
                    description.equals(c.getDescription()) &&
                    actionsTaken.equals(c.getActionsTaken())) {
                successfullyLoaded = true;
            }
        }

        SharedPreferences memory = activity.getSharedPreferences(DeviceStorage.CONCERN_SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor memoryEditor = memory.edit();
        memoryEditor.clear().commit();

        if(!successfullyLoaded) {
            fail();
        }
    }

    public static ConcernWrapper generateConcernForTest(String reporterName, String phoneNumber, String emailAddress,
                                                        String facilityName, String roomName, String concernType,
                                                        String actionsTaken, String description) {
        ConcernData concernData = new ConcernData();
        concernData.setConcernNature(concernType);
        concernData.setActionsTaken(actionsTaken);
        concernData.setDescription(description);

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
