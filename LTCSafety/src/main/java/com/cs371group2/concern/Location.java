package com.cs371group2.concern;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;

/**
 * The location class is used to store information pertaining to the location the concern was
 * reported to be at. This includes a room name and facility name.
 *
 * Created on 2017-01-17.
 */
public final class Location implements Validatable {

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
            return new ValidationResult(FACILITY_NAME_ERROR);
        }
        return new ValidationResult();
    }
}
