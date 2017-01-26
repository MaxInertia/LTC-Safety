package com.cs371group2.concern;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The location class is used to store information pertaining to the location the concern was
 * reported to be at. This includes a room name and facility name.
 *
 * Created on 2017-01-17.
 */
public final class Location implements Validatable {

    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Location.class.getName() );


    private static final String FACILITY_NAME_ERROR = "A facility name must be provided when submitting a concern.";

    /**
     * The room name within the facility where the concern was reported to be. Room name may be
     * null. This will usually be values similar to Lobby, Room 23A, 223, etc.
     */
    String roomName;

    /**
     * The facility name is the name of the Saskatoon Health Region location where to concern was
     * reported to be at. This value must never be null.
     */
    String facilityName;

    public String getRoomName() {
        return roomName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    @Override
    public ValidationResult validate() {

        if (facilityName == null) {
            LOGGER.log(Level.FINE, "Validation of location failed due to facility name being null.");
            return new ValidationResult(FACILITY_NAME_ERROR);
        }
        LOGGER.log(Level.FINER, "Validation of location successful.");
        return new ValidationResult();
    }

    @Override
    public String toString(){
        return "Facility Name: " + this.getFacilityName()
                + "\nRoom Name: " + this.getRoomName() + "\n";
    }
}
