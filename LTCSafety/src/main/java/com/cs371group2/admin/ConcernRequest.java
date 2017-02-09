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

    /**
     * Creates a concern request for the given offset and limit
     *
     * @param requestOffset The offset to begin loading at the datastore
     * @param requestLimit The maximum amount of concerns to load
     * @precond requestOffset >= 0
     * @precond requestLimit > 0
     */
    public ConcernRequest(int requestOffset, int requestLimit){
        assert(requestOffset >= 0);
        assert(requestLimit > 0);

        offset = requestOffset;
        limit = requestLimit;
    }

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
