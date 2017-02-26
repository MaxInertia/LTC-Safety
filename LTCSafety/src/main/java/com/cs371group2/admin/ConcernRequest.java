package com.cs371group2.admin;

import com.cs371group2.client.OwnerToken;

import java.util.logging.Logger;

/**
 * This object represents a concern request containing an offset and a limit,
 * both of which will be to access the concern database. It will also include all
 * necessary functionality for authenticating the requester.
 *
 * History property: Instances of this class are immutable from the time they are created.
 *
 * Created on 2017-02-08.
 */
public final class ConcernRequest extends AdminRequest {

    private static final String NULL_TOKEN_ERROR = "Unable to access concern due to non-existent credentials.";

    private static final Logger logger = Logger.getLogger( ConcernRequest.class.getName() );

    /** The offset in the database to begin loading the concerns from */
    private int offset;

    /** The maximum number of elements to be loaded from the database */
    private int limit;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * Checks if the request contains legal information for parse-attempting
     * @return Whether the request contains legal information or not
     */
    public boolean legalRequest(){
        if(limit < 1 || offset < 0 || accessToken == null || accessToken == "") {
            return false;
        }

        return true;
    }
    /**
     * TestHook_MutableConcernData is a test hook to make ConcernRequest testable without exposing its
     * members. An instance of TestHook_MutableConcernRequest can be used to construct new concern request
     * instances and set values for testing purposes.
     */
    public static class TestHook_MutableConcernRequest {

        /** An immutable ConcernRequest for use in testing*/
        private ConcernRequest immutable;

        /**
         * Creates a new mutable concern request
         *
         * @param limit The concern limit of the mutable request
         * @param offset The concern offset of the mutable request
         * @param token The token of the mutable request
         */
        public TestHook_MutableConcernRequest(int limit, int offset, String token) {
            immutable = new ConcernRequest();
            immutable.limit = limit;
            immutable.offset = offset;
            immutable.accessToken = token;
        }

        public ConcernRequest build(){
            return immutable;
        }

        public void setMutableLimit(int mutableLimit) { immutable.limit = mutableLimit; }

        public void setMutableOffset(int mutableOffset) { immutable.offset = mutableOffset; }

        public void setMutableToken(String mutableToken) { immutable.accessToken = mutableToken; }
    }
}
