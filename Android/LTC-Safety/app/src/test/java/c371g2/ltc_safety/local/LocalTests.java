package c371g2.ltc_safety.local;

import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A very basic test class for confirming that all setters and getters are working for the classes
 * in the 'local' package: Concern, Location, and Reporter.
 */
public class LocalTests {

    @Test
    public void test_getName() {
        String testName = "Jessica Jones";
        Reporter reporter = new Reporter(testName,null,null);

        assertTrue(reporter.getName().equals(testName));
    }

    @Test
    public void test_getPhoneNumber() {
        String testNumber = "1231231234";
        Reporter reporter = new Reporter(null,null,testNumber);

        assertTrue(reporter.getPhoneNumber().equals(testNumber));
    }

    @Test
    public void test_getEmailAddress() {
        String email = "Valid.Email.Address@Form.com";
        Reporter reporter = new Reporter(null,email,null);

        assertTrue(reporter.getEmailAddress().equals(email));
    }

    @Test
    public void test_getReporterDetailsThroughConcern() {
        String testName = "Al Gore";
        String testEmail = "Valid.Email.Address@Form.com";
        String testNumber = "1231231234";
        Concern concern = new Concern(testName,testNumber,testEmail,null,null,null,null);

        assertTrue(concern.getReporterName().equals(testName));
        assertTrue(concern.getReporterEmailAddress().equals(testEmail));
        assertTrue(concern.getReporterPhoneNumber().equals(testNumber));
    }

    @Test
    public void test_getFacility() {
        String facilityName = "Luther Care Home";
        Location location = new Location(facilityName,null);

        assertTrue(location.getFacilityName().equals(facilityName));
    }

    @Test
    public void test_getRoom() {
        String roomName = "G-007";
        Location location = new Location(null,roomName);

        assertTrue(location.getRoomName().equals(roomName));
    }

    @Test
    public void test_getLocationDetailsThroughConcern() {
        String facilityName = "Luther Care Home";
        String roomName = "G-007";
        Concern concern = new Concern(null,null,null,facilityName,roomName,null,null);

        assertTrue(concern.getFacilityName().equals(facilityName));
        assertTrue(concern.getRoomName().equals(roomName));
    }

    @Test
    public void test_getConcernType() {
        String concernType = "Biohazard";
        Concern concern = new Concern(null,null,null,null,null,concernType,null);

        assertTrue(concern.getConcernType().equals(concernType));
    }

    @Test
    public void test_getActionsTaken() {
        String actionsTaken = "Nada!";
        Concern concern = new Concern(null,null,null,null,null,null,actionsTaken);

        assertTrue(concern.getActionsTaken().equals(actionsTaken));
    }

    @Test
    public void test_getDateSubmitted() {
        Concern concern = new Concern(null,null,null,null,null,null,null);

        assertTrue(concern.getDateSubmitted()!=null);
    }

    @Test
    public void test_getToken_and_setToken() {
        String token = "akbsi12s";
        Concern concern = new Concern(null,null,null,null,null,null,null);
        concern.setToken(token);
        assertTrue(concern.getToken().equals(token));
    }
}
