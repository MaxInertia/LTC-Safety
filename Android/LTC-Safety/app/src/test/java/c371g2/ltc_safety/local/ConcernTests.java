package c371g2.ltc_safety.local;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertTrue;

/**
 * Tests the methods in the ConcernWrapper class.
 */
public class ConcernTests {
    
    @Test
    public void test_getReporterDetailsThroughConcern() {
        String testName = "Al Gore";
        String testEmail = "Valid.Email.Address@Form.com";
        String testNumber = "1231231234";
        ConcernWrapper concern = new ConcernWrapper(testName,testNumber,testEmail,null,null,null,null,null);

        assertTrue(concern.getReporterName().equals(testName));
        assertTrue(concern.getReporterEmail().equals(testEmail));
        assertTrue(concern.getReporterPhone().equals(testNumber));
    }

    @Test
    public void test_getLocationDetailsThroughConcern() {
        String facilityName = "Luther Care Home";
        String roomName = "G-007";
        ConcernWrapper concern = new ConcernWrapper(null,null,null,facilityName,roomName,null,null,null);

        assertTrue(concern.getFacilityName().equals(facilityName));
        assertTrue(concern.getRoomName().equals(roomName));
    }

    @Test
    public void test_getConcernType() {
        String concernType = "Biohazard";
        ConcernWrapper concern = new ConcernWrapper(null,null,null,null,null,concernType,null,null);

        assertTrue(concern.getConcernType().equals(concernType));
    }

    @Test
    public void test_getActionsTaken() {
        String actionsTaken = "Nada!";
        ConcernWrapper concern = new ConcernWrapper(null,null,null,null,null,null,actionsTaken,null);

        assertTrue(concern.getActionsTaken().equals(actionsTaken));
    }

    @Test
    public void test_getDescription() {
        String description = "Bad things";
        ConcernWrapper concern = new ConcernWrapper(null,null,null,null,null,null,description,null);

        assertTrue(concern.getActionsTaken().equals(description));
    }

    @Test
    public void test_getDateSubmitted() {
        ConcernWrapper concern = new ConcernWrapper(null,null,null,null,null,null,null,null);

        long date = (new Date()).getTime();
        long delta = 1000*60; // Minute accuracy

        assertTrue( (concern.getSubmissionDate() > date-delta) && (concern.getSubmissionDate() < date+delta) );
    }

    @Test
    public void test_CompareTo() throws InterruptedException {
        ConcernWrapper firstConcern = new ConcernWrapper(null,null,null,null,null,null,null,null);
        Thread.sleep(20);
        ConcernWrapper secondConcern = new ConcernWrapper(null,null,null,null,null,null,null,null);

        assertTrue(firstConcern.compareTo(secondConcern) == 1);
        assertTrue(secondConcern.compareTo(firstConcern) == -1);
    }
}
