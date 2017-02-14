package c371g2.ltc_safety.local;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Tests the methods in the Location class.
 */
public class LocationTests {

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

}
