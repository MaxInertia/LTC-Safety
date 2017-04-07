package com.cs371group2.concern;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The location class is used to store information pertaining to the location the concern was
 * reported to be at. This includes a room name and facility name.
 *
 * History properties: Instances of this class are immutable from the time they are created.
 *
 * Invariance properties: This class makes no assumptions about the information it is given. However, to be considered
 * "valid" in the system, the facilityName must be non-null.
 *
 * Created on 2017-01-17.
 */
public final class Location implements Validatable {

    private static final Logger logger = Logger.getLogger(Location.class.getName());

    private static final String FACILITY_NAME_ERROR = "A facility name must be provided when submitting a concern.";

    /**
     * The room name within the facility where the concern was reported to be. Room name may be
     * null. This will usually be values similar to Lobby, Room 23A, 223, etc.
     */
    private String roomName;

    /**
     * The facility name is the name of the Saskatoon Health Region location where to concern was
     * reported to be at. This value must never be null.
     */
    private String facilityName;

    /**
     * TestHook_MutableLocation is a test hook to make Location testable without exposing its
     * members. An instance of TestHook_MutableLocation can be used to construct new Locations and
     * set values for testing purposes.
     */
    public static class TestHook_MutableLocation {

        /**
         * The location data that is being built.
         */
        private Location location = new Location();

        /**
         * Constructs a new mutable location.
         *
         * @param facilityName The name of the location's facility.
         * @param roomName The location's room name.
         */
        public TestHook_MutableLocation(String facilityName, String roomName) {
            setFacilityName(facilityName);
            setRoomName(roomName);
        }

        public void setFacilityName(String facilityName) {
            this.location.facilityName = facilityName;
        }

        public void setRoomName(String roomName) {
            this.location.roomName = roomName;
        }

        /**
         * Converts the mutable location to a location instance to be used for testing. Once built
         * the location is immutable regardless of whether the mutable location it was created with
         * is modified.
         *
         * @return The immutable location reference containing the mutable location's data.
         */
        public Location build() {
            Location immutable = new Location();
            immutable.facilityName = this.location.facilityName;
            immutable.roomName = this.location.roomName;
            return immutable;
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    @Override
    public ValidationResult validate() {

        if (facilityName == null) {
            logger.log(Level.WARNING,
                    "Validation of location failed due to facility name being null.");
            return new ValidationResult(FACILITY_NAME_ERROR);
        }

        logger.log(Level.FINER, "Validation of location successful.");
        return new ValidationResult();
    }

    @Override
    public String toString() {
        return "Facility Name: " + this.getFacilityName()
                + "\nRoom Name: " + this.getRoomName() + "\n";
    }
}
