package c371g2.ltc_safety.local;

import com.appspot.ltc_safety.client.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that contains all fields in the Concern class, but in common data types. The OwnerToken
 * did not need to be converted into an atomic data type because it only contains String.
 */
public class ConcernWrapper {
    private String concernType;
    private String actionsTaken;
    private Reporter reporter;
    private Location location;

    private List<StatusWrapper> statuses;
    private long dateSubmitted;
    private OwnerToken token;

    /**
     * Constructor for ConcernWrapper. Takes a Concern as input and generates a ConcernWrapper.
     * @param concern
     */
    public ConcernWrapper(Concern concern, OwnerToken token) {
        concernType = concern.getData().getConcernNature();
        if(concernType==null) concernType = "";
        actionsTaken = concern.getData().getActionsTaken();
        if(actionsTaken==null) actionsTaken = "";


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

        statuses = new ArrayList<StatusWrapper>();
        for(ConcernStatus aStatus: concern.getStatuses()) {
            StatusWrapper wrapperStatus = new StatusWrapper(aStatus.getType(), aStatus.getCreationDate().getValue());
            statuses.add(wrapperStatus);
        }
    }

    ConcernWrapper(String rName, String rPhone, String rEmail, String facilityName, String roomName, String concernType, String actionsTaken) {
        reporter = new Reporter(rName, rEmail, rPhone);
        location = new Location(facilityName, roomName);
        this.concernType = concernType;
        this.actionsTaken = actionsTaken;
        dateSubmitted = (new Date()).getTime();
    }

    public String getConcernType() {
        return concernType;
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public String getReporterName() {
        return reporter.getName();
    }

    public String getReporterEmail() {
        return reporter.getEmailAddress();
    }

    public String getReporterPhone() {
        return reporter.getPhoneNumber();
    }

    public String getFacilityName() {
        return location.getFacilityName();
    }

    public String getRoomName() {
        return location.getRoomName();
    }

    public List<StatusWrapper> getStatuses() {
        return statuses;
    }
    public long getSubmissionDate() {
        return dateSubmitted;
    }

    public OwnerToken getOwnerToken() {
        return token;
    }

}


