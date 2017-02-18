package c371g2.ltc_safety.a_main;

import android.support.test.runner.AndroidJUnit4;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.OwnerToken;
import com.appspot.ltc_safety.client.model.Reporter;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import c371g2.ltc_safety.local.Concern;

import static junit.framework.Assert.fail;

/**
 * This class runs tests on the methods in the MainViewModel class.
 */
@RunWith(AndroidJUnit4.class)
public class MainViewModelTests {
    @Test
    public void test_writeAndReadDeviceData() {
        // The following values do not need to be valid. The data that is used to create concerns
        // in the NewConcernViewModel is
        String reporterName = "Reporter";
        String phoneNumber = "Phone";
        String emailAddress = "Email";
        String facilityName = "Facility";
        String roomName = "B102";
        String concernType = "Type";
        String actionsTaken = "Actions";

        ConcernData concernData = new ConcernData();
        concernData.setConcernNature(concernType);
        concernData.setActionsTaken(actionsTaken);
        Location facility = new Location();
        facility.setFacilityName(facilityName);
        concernData.setLocation(facility);
        Reporter reporter = new Reporter();
        reporter.setName(reporterName);
        reporter.setPhoneNumber(phoneNumber);
        reporter.setEmail(emailAddress);
        concernData.setReporter(reporter);

        com.appspot.ltc_safety.client.model.Concern clientConcern = new com.appspot.ltc_safety.client.model.Concern();
        clientConcern.setData(concernData);

        Concern concern = new Concern(clientConcern,new OwnerToken());
        MainViewModel.saveNewConcern(concern);

        // If the list is null or has no elements, save failed.
        ArrayList<Concern> concerns = MainViewModel.loadConcerns();
        if(concerns==null || concerns.size()==0){
            fail();
        }

        // If the list does not contain the concern we attempted to save then the save failed.
        for(Concern c: concerns) {
            if(reporterName.equals(c.getConcernObject().getData().getReporter().getName()) && phoneNumber.equals(c.getConcernObject().getData().getReporter().getPhoneNumber())
                    && emailAddress.equals(c.getConcernObject().getData().getReporter().getEmail()) && facilityName.equals(c.getConcernObject().getData().getLocation().getFacilityName())
                    && roomName.equals(c.getConcernObject().getData().getLocation().getRoomName()) && concernType.equals(c.getConcernObject().getData().getConcernNature())
                    && actionsTaken.equals(c.getConcernObject().getData().getActionsTaken())) {
                return;
            }
        }
        fail();
    }
}
