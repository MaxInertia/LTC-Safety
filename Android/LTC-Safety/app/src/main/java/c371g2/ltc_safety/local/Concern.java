package c371g2.ltc_safety.local;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is used to store the details of submitted Concerns on the reporters device along with
 * the owner token associated with that concern. It provides the data required to:
 * 1. Populate the concern list on the main activity,
 * 2. Fill in the fields on the view concern activity,
 * 3. Retract the concern.
 * NOT TO BE CONFUSED WITH CLIENT-API Concern
 * @Invariants none
 * @HistoryProperties All fields except token are final.
 */
public class Concern {

    private final Reporter reporter;
    private final Location location;

    private final String concernType;
    private final String actionsTaken;

    private final Date dateSubmitted;
    private String token;

    public Concern(String reporterName, String reporterPhone,
                   String reporterEmail, String facilityName,
                   String roomName, String concernType,
                   String actionsTaken) {
        this.reporter = new Reporter(reporterName, reporterEmail, reporterPhone);
        this.location = new Location(facilityName,roomName);
        this.concernType = concernType;
        this.actionsTaken = actionsTaken;
        this.dateSubmitted = new Date(); // TODO: Check if this can return incorrect values when the device date is set wrong
    }

    /**
     * Create a bundle containing this concern. Used for transferring concerns between activities.
     * @preconditions This concern object contains all fields required to be submitted: Reporter name,
     * one contact method (email or phone), concern type, and facility. This concern must also have
     * a valid owner token.
     * @modifies Nothing, this concern is unchanged.
     * @return a bundle containing all of this concerns data.
     */
    public Bundle toBundle() {
        return new Bundle();
    }

    public String getReporterName() {
        return reporter.getName();
    }

    public String getReporterEmailAddress() {
        return reporter.getEmailAddress();
    }

    public String getReporterPhoneNumber() {
        return reporter.getPhoneNumber();
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public String getConcernType() {
        return concernType;
    }

    public String getFacilityName() {
        return location.getFacilityName();
    }

    public String getRoomName() {
        return location.getRoomName();
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
