package com.cs371group2.concern;

import java.util.Date;

/**
 * The status of a concern including the type of the concern and when this status was created.
 * Throughout its life cycle a concern's status will change multiple times.
 *
 * Created on 2017-02-01.
 */
public class ConcernStatus implements Comparable<ConcernStatus> {

    /**
     * The date the concern was created. This should correspond to the date that the concern's
     * status changed.
     */
    private Date creationDate = new Date();

    private ConcernStatusType type;

    public Date getCreationDate() {
        return creationDate;
    }

    public ConcernStatusType getType() {
        return type;
    }

    /**
     * No-arg constructor required for loading from the datastore.
     */
    private ConcernStatus() {}

    /**
     * Creates a new concern status with the specified time. The creation date for the concern is
     * set to be the current system time at the time the first instance is created.
     *
     * @param type The type of the new concern status.
     * @precond type != null
     */
    ConcernStatus(ConcernStatusType type) {
        assert type != null;
        this.type = type;
    }

    @Override
    public int compareTo(ConcernStatus o) {
        return creationDate.compareTo(o.creationDate);
    }
}
