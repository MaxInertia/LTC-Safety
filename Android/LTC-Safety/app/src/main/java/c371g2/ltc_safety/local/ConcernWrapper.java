package c371g2.ltc_safety.local;

import android.support.annotation.NonNull;

import com.appspot.ltc_safety.client.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that contains all fields in the Concern class, but in common data types. The OwnerToken
 * did not need to be converted into an atomic data type because it only contains String.
 * @Invariants
 * - All fields are non-null.
 * - concernType.length() > 0
 * @HistoryProperties All fields are final except for the OwnerToken. For a given instance of
 * ConcernWrapper, each of those fields is only initialized via the constructor.
 */
public class ConcernWrapper implements Comparable<ConcernWrapper>{
    /**
     * The Concern Nature.
     */
    final private String concernType;
    /**
     * The actions taken in light of this concern.
     */
    final private String actionsTaken;
    /**
     *
     */
    final private String description;
    /**
     * The Reporter of this concern. Name as well as Phone number and/or Email address
     */
    final private Reporter reporter;
    /**
     * Location of the concern. LTC-Facility and possibly a Room.
     */
    final private Location location;
    /**
     * The list of Statuses tied to this concern.
     */
    final private List<StatusWrapper> statuses;
    /**
     * The date this concern was submitted.
     */
    final private long dateSubmitted;
    /**
     * The token that proves ownership of this concern.
     */
    private OwnerToken token;

    /**
     * Constructor for ConcernWrapper. Takes a Concern as input and generates a ConcernWrapper.
     * @preconditions concern and token are non-null.
     * @modifies Initializes all fields in this class. Fields corresponding to the inputs not
     * provided by the reporter are initialized to an empty string.
     * @param concern The concern received from the backend via the Client API
     * @param token The token that proves this concern was submitted on this device.
     */
    public ConcernWrapper(@NonNull Concern concern,@NonNull OwnerToken token) {
        if(concern.getData().getConcernNature() == null) {
            concernType = "";
        } else {
            concernType = concern.getData().getConcernNature();
        }

        if(concern.getData().getActionsTaken() == null) {
            actionsTaken = "";
        } else {
            actionsTaken = concern.getData().getActionsTaken();
        }

        if(concern.getData().getDescription() == null) {
            description = "";
        } else {
            description = concern.getData().getDescription();
        }

        String reporterName = concern.getData().getReporter().getName();
        if(reporterName==null) reporterName = "";
        String reporterEmail = concern.getData().getReporter().getEmail();
        if(reporterEmail==null) reporterEmail = "";
        String reporterPhone = concern.getData().getReporter().getPhoneNumber();
        if(reporterPhone==null) reporterPhone = "";
        reporter = new Reporter(reporterName, reporterEmail, reporterPhone);

        String facilityName = concern.getData().getLocation().getFacilityName();
        if(facilityName==null) facilityName = "";
        String roomName = concern.getData().getLocation().getRoomName();
        if(roomName==null) roomName = "";
        location = new Location(facilityName, roomName);

        dateSubmitted = concern.getSubmissionDate().getValue();
        this.token = token;

        statuses = new ArrayList<>();
        for(ConcernStatus aStatus: concern.getStatuses()) {
            StatusWrapper wrapperStatus = new StatusWrapper(aStatus.getType(), aStatus.getCreationDate().getValue());
            statuses.add(wrapperStatus);
        }
    }

    /**
     * Creates a ConcernWrapper class
     * @param reporterName The first and last name of the reporter of this concern.
     * @param reporterPhone The reporters phone number.
     * @param reporterEmail The reporters email.
     * @param facilityName The name of the LTC-Facility at which the concern applies.
     * @param roomName The room relevant to the concern.
     * @param concernType The concern nature.
     * @param actionsTaken Actions taken in light of the concern.
     */
    ConcernWrapper(String reporterName, String reporterPhone, String reporterEmail, String facilityName, String roomName, String concernType, String actionsTaken, String description) {
        reporter = new Reporter(reporterName, reporterEmail, reporterPhone);
        location = new Location(facilityName, roomName);
        this.concernType = concernType;
        this.actionsTaken = actionsTaken;
        this.description = description;
        dateSubmitted = (new Date()).getTime();
        statuses = new ArrayList<>();
    }

    /**
     * Retrieves the Concern Nature.
     * @preconditions none.
     * @modifies nothing.
     * @return concern nature.
     */
    public String getConcernType() {
        return concernType;
    }

    /**
     * Retrieves the Actions Taken.
     * @preconditions none.
     * @modifies nothing.
     * @return Actions taken or empty string.
     */
    public String getActionsTaken() {
        return actionsTaken;
    }

    /**
     * Retrieves the concern description.
     * @preconditions none.
     * @modifies nothing.
     * @return Concern description or empty string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the name of the reporters.
     * @preconditions none.
     * @modifies nothing.
     * @return Reporter name.
     */
    public String getReporterName() {
        return reporter.getName();
    }

    /**
     * Retrieves the reporters email address, if provided.
     * @preconditions none.
     * @modifies nothing.
     * @return Reporter email address or empty string.
     */
    public String getReporterEmail() {
        return reporter.getEmailAddress();
    }

    /**
     * Retrieves the reporters phone number, if provided.
     * @preconditions none.
     * @modifies nothing.
     * @return Reporter phone number or empty string.
     */
    public String getReporterPhone() {
        return reporter.getPhoneNumber();
    }

    /**
     * Retrieves the name of the Facility to which the concern applies.
     * @preconditions none.
     * @modifies nothing.
     * @return Long term care facility name.
     */
    public String getFacilityName() {
        return location.getFacilityName();
    }

    /**
     * Retrieves the room the concern applies to, if provided.
     * @preconditions none.
     * @modifies nothing.
     * @return Room name or empty string.
     */
    public String getRoomName() {
        return location.getRoomName();
    }

    /**
     * Retrieves the list of statuses.
     * @preconditions none.
     * @modifies nothing.
     * @return list of statuses.
     */
    public List<StatusWrapper> getStatuses() {
        return statuses;
    }

    /**
     * Retrieves the date this concern was submitted.
     * @preconditions none.
     * @modifies nothing.
     * @return Date the concern was submitted as a long.
     */
    public long getSubmissionDate() {
        return dateSubmitted;
    }

    /**
     * Retrieves the Owner token.
     * @preconditions none.
     * @modifies nothing.
     * @return owner token.
     */
    public OwnerToken getOwnerToken() {
        return token;
    }

    @Override
    public int compareTo(ConcernWrapper otherConcern) {
        if(dateSubmitted>otherConcern.dateSubmitted) {
            return -1;
        } else {
            return 1;
        }
    }
}


