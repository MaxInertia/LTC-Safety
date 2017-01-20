package com.cs371group2.concern;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.util.Date;

/**
 * The concern class is used to model and store all information relating to a concern within the
 * data store. This includes the submitted concern data and information for administrators to modify
 * as they tend to concerns.
 */
@Entity
public final class Concern {

    /**
     * Used to uniquely identify concerns within the database. This value will be automatically
     * created by the data store.
     */
    @Id
    private Long id;

    /**
     * The status of the concern within the system detailing action that has been taken. The status
     * changes as administrators acknowledge, respond to, and resolve concerns.
     */
    @Index
    private ConcernStatus status = ConcernStatus.PENDING;

    /**
     * Used to identify whether a concern is being actively tracked within the system. Once a
     * concern has been resolved or retracted it can be archived. Duplicate concerns may also be
     * archived by the administrators.
     */
    @Index
    private boolean isArchived = false;

    /**
     * The exact date and time the concern was submitted.
     */
    @Index
    private Date submissionDate = new Date();

    /**
     * The user submitted data relating to the concern such as nature, location, and reporter.
     */
    private ConcernData data;

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public ConcernStatus getStatus() {
        return status;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public ConcernData getData() {
        return data;
    }
}
