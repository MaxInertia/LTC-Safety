package c371g2.ltc_safety.local;

/**
 * This class is used to store data on the location of concern.
 * NOT TO BE CONFUSED WITH CLIENT-API Location
 * @Invariants
 * @HistoryProperties All fields are final.
 */
public class Location {
    private final String facilityName;
    private final String roomName;

    public Location(String facilityName, String roomName) {
        this.facilityName = facilityName;
        this.roomName = roomName;
    }

    public String getFacilityName() {
        return this.facilityName;
    }

    public String getRoomName() {
        return this.roomName;
    }
}
