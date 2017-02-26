package c371g2.ltc_safety.local;

import android.support.test.filters.SmallTest;
import android.support.test.filters.Suppress;
import android.support.test.runner.AndroidJUnit4;

import com.appspot.ltc_safety.client.model.OwnerToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Tests the methods inside the 'local' package that must be run on a device (real or emulated).
 */
@Suppress
@RunWith(AndroidJUnit4.class)
@SmallTest
public class Local_InstrumentedTests {
    public Local_InstrumentedTests(){}

    @Test
    public void test_toBundle() {
        String rName = "Jeff Redbeard";
        String rPhone = "1231231234";
        String rEmail = "valid.address@hotmail.ca";
        String facility = "Luthercorp";
        String room = "g-006";
        String concernType = "Biohazard";
        String actionsTaken = "None";
        ConcernWrapper concern = new ConcernWrapper(new com.appspot.ltc_safety.client.model.Concern(), new OwnerToken());
        /*Bundle bundle = concern.toBundle();

        assertTrue(rName.equals(bundle.getString("reporterName")));
        assertTrue(rPhone.equals(bundle.getString("reporterPhone")));
        assertTrue(rEmail.equals(bundle.getString("reporterEmail")));
        assertTrue(facility.equals(bundle.getString("facilityName")));
        assertTrue(room.equals(bundle.getString("roomName")));
        assertTrue(concernType.equals(bundle.getString("concernType")));
        assertTrue(actionsTaken.equals(bundle.getString("actionsTaken")));
        assertTrue(concern.getConcernObject().getSubmissionDate().toString().equals(bundle.getString("dateSubmitted")));*/
    }

}
