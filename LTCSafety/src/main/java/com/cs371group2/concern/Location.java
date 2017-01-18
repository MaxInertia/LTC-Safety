package com.cs371group2.concern;

/**
 * The location class is used to store information pertaining to the location the concern was
 * reported to be at. This includes a room name and facility name.
 *
 * Created on 2017-01-17.
 */
public final class Location {

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

    public String getRoomName() {
        return roomName;
    }

    public String getFacilityName() {
        return facilityName;
    }
}
