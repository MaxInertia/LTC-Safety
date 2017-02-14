package c371g2.ltc_safety.a_main;

import android.support.test.runner.AndroidJUnit4;

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
        Concern concern = new Concern(reporterName,phoneNumber,emailAddress,facilityName,roomName,concernType,actionsTaken);

        MainViewModel.saveNewConcern(concern);

        // If the list is null or has no elements, save failed.
        ArrayList<Concern> concerns = MainViewModel.loadConcerns();
        if(concerns==null || concerns.size()==0){
            fail();
        }

        // If the list does not contain the concern we attempted to save then the save failed.
        for(Concern c: concerns) {
            if(reporterName.equals(c.getReporterName()) && phoneNumber.equals(c.getReporterPhoneNumber())
                    && emailAddress.equals(c.getReporterEmailAddress()) && facilityName.equals(c.getFacilityName())
                    && roomName.equals(c.getRoomName()) && concernType.equals(c.getConcernType())
                    && actionsTaken.equals(c.getActionsTaken())) {
                return;
            }
        }
        fail();
    }
}
