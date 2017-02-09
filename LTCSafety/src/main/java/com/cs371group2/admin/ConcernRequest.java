package com.cs371group2.admin;

/**
 * This object represents a concern request containing an offset and a limit,
 * both of which will be to access the concern database.
 *
 * Created on 2017-02-08.
 */
public class ConcernRequest {

    /** The offset in the database to begin loading the concerns from */
    private int offset;

    /** The maximum number of elements to be loaded from the database */
    private int limit;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
