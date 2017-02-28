package c371g2.ltc_safety.local;

/**
 * This class is used to store data on the location of concern.
 * NOT TO BE CONFUSED WITH CLIENT-API Location
 * @Invariants No fields are null.
 * @HistoryProperties All fields are final.
 */
public class Location {
    private final String facilityName;
    private final String roomName;

    public Location(String facilityName, String roomName) {
        this.facilityName = facilityName;
        this.roomName = roomName;
    }

    /**
     * Retrieves the name of the Facility to which the concern applies.
     * @preconditions none.
     * @modifies nothing.
     * @return Long term care facility name.
     */
    public String getFacilityName() {
        return this.facilityName;
    }

    /**
     * Retrieves the room the concern applies to, if provided.
     * @preconditions none.
     * @modifies nothing.
     * @return Room name or empty string.
     */
    public String getRoomName() {
        return this.roomName;
    }
}
