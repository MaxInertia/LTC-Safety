package com.cs371group2.admin;

import com.cs371group2.client.OwnerToken;

import java.util.logging.Logger;

/**
 * This object represents a concern request containing an offset and a limit,
 * both of which will be to access the concern database. It will also include all
 * necessary functionality for authenticating the requester.
 *
 * Created on 2017-02-08.
 */
public final class ConcernRequest extends AdminRequest {

    private static final String NULL_TOKEN_ERROR = "Unable to access concern due to non-existent credentials.";

    private static final Logger logger = Logger.getLogger( OwnerToken.class.getName() );

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
        int mutableLimit;
        int mutableOffset;
        String mutableToken;

        /**
         * Creates a new mutable concern request
         *
         * @param limit The concern limit of the mutable request
         * @param offset The concern offset of the mutable request
         * @param token The token of the mutable request
         */
        public TestHook_MutableConcernRequest(int limit, int offset, String token) {
            this.mutableLimit = limit;
            this.mutableOffset = offset;
            this.mutableToken = token;
        }

        public ConcernRequest build(){
            ConcernRequest immutable = new ConcernRequest();
            immutable.limit = this.mutableLimit;
            immutable.offset = this.mutableOffset;
            immutable.accessToken = this.mutableToken;
            return immutable;
        }

        public void setMutableLimit(int mutableLimit) { this.mutableLimit = mutableLimit; }

        public void setMutableOffset(int mutableOffset) { this.mutableOffset = mutableOffset; }

        public void setMutableToken(String mutableToken) { this.mutableToken = mutableToken; }
    }
}
