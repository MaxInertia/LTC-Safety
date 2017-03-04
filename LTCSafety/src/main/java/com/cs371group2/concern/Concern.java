package com.cs371group2.concern;

import com.cs371group2.client.OwnerToken;
import com.cs371group2.facility.Facility;
import com.cs371group2.facility.FacilityDao;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The concern class is used to model and store all information relating to a concern within the
 * data store. This includes the submitted concern data and information for administrators to modify
 * as they tend to concerns.
 */
@Entity
public final class Concern {

    /**
     * Logger definition for this class.
     */
    private static final Logger logger = Logger.getLogger(Concern.class.getName());

    /**
     * Used to uniquely identify concerns within the database. This value will be automatically
     * created by the data store.
     */
    @Id
    private Long id;

    /**
     * Used to identifiy whether a concern is accessible by test accounts.
     * This is an investment in testability to allow testing of authenticated parts of the system.
     */
    @Index
    private boolean isTest = false;

    /**
     * The statuses that the concern has been through in its life cycle order by date with the
     * current concern being the last.
     */
    @Index
    private SortedSet<ConcernStatus> statuses = new TreeSet<ConcernStatus>();

    /**
     * Used to identify whether a concern is being actively tracked within the system. Once a
     * concern has been resolved or retracted it can be archived. Duplicate concerns may also be
     * archived by the administrators.
     */
    @Index
    private boolean isArchived = false;

    /**
     * A reference to the care home facility this concern is linked to. If the location of the
     * care home has not yet been added to the database or does not exist, this will refer to a
     * the placeholder care home facility, Other.
     */
    @Index
    private Ref<Facility> facilityRef;

    /**
     * The exact date and time the concern was submitted.
     */
    @Index
    private Date submissionDate = new Date();

    /**
     * The user submitted data relating to the concern such as nature, location, and reporter.
     */
    private ConcernData data;

    public Long getId() {
        return id;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public SortedSet<ConcernStatus> getStatuses() {
        return statuses;
    }

    public boolean isArchived() {
        return isArchived;
    }

    /**
     * Determines whether the concerns most recent status update was to retracted.
     * @precond The concern has at least one status.
     * @return True if the concern is currently retracted.
     */
    public boolean isRetracted() {

        assert !statuses.isEmpty();

        return statuses.last().getType().equals(ConcernStatusType.RETRACTED);
    }

    public ConcernData getData() {
        return data;
    }

    public boolean isTest() {
        return isTest;
    }

    private Concern() {
    }

    /**
     * Create a new concern
     *
     * @param data The data for the concern that was submitted from the Android or iOS client.
     * @precond data != null data is valid based on its validate method
     */
    public Concern(ConcernData data) {
        this(data, false);
    }

    /**
     * Create a new concern
     *
     * @param isTest Whether or not concern is accessible by test accounts.
     * @param data The data for the concern that was submitted from the Android or iOS client.
     * @precond data != null data is valid based on its validate method
     */
    public Concern(ConcernData data, boolean isTest) {

        assert data != null;
        assert data.validate().isValid();

        this.statuses.add(new ConcernStatus(ConcernStatusType.PENDING));
        this.isTest = isTest;
        this.data = data;

        facilityRef = Ref.create(new FacilityDao().load(data.getLocation().getFacilityName()));
        
        if (isTest) {
            logger.log(Level.FINER, "Test concern created: \n" + this.toString());
        } else {
            logger.log(Level.FINER, "Concern created: \n" + this.toString());
        }
    }

    /**
     * Generates an owner token containing the concern id for this concern giving the holder
     * authorization to retract, access, or update the concern.
     *
     * @return The owner token for this concern giving the holder authorized access to it.
     * @precond The id of the concern must be populated. This means that the concern must be stored
     * in the datastore using the ConcernDao prior to generating the owner token.
     */
    public OwnerToken generateOwnerToken() {

        assert id != null;

        logger.log(Level.FINER, "Owner Token being created: ID# " + this.id);
        return new OwnerToken(id);
    }

    /**
     * Updates the concern entity to reflect that the concern has been retracted.
     *
     * @postcond The status has been changed to RETRACTED and isArchived is now true.
     * @return The retracted status that has been set as the concern's current status.
     */
    public ConcernStatus retract() {
        ConcernStatus status = new ConcernStatus(ConcernStatusType.RETRACTED);
        statuses.add(status);
        isArchived = true;

        logger.log(Level.FINER, "Concern Retracted: ID# " + this.id);

        return status;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Facility getFacility(){
        return facilityRef.get();
    }

    @Override
    public String toString() {
        return "Concern:\nID# " + this.id + this.getData().toString();
    }
}
