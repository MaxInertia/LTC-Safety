package com.cs371group2.admin;

import java.util.logging.Logger;

/**
 * This object represents a specific concern request to access the concern database. It will also include all
 * necessary functionality for authenticating the requester.
 *
 * History property: Instances of this class are immutable from the time they are created.
 *
 * Created on 2017-02-26.
 */
public final class ConcernRequest extends AdminRequest {

    private static final String NULL_TOKEN_ERROR = "Unable to access concern due to non-existent credentials.";

    private static final Logger logger = Logger.getLogger( ConcernListRequest.class.getName() );

    /** The unique id of the concern to load from the database */
    private long concernId;

    public long getConcernId(){ return concernId; }

    /**
     * Checks if the request contains legal information for parse-attempting
     * @return Whether the request contains legal information or not
     */
    public boolean legalRequest(){
        return (accessToken != null && accessToken.isEmpty());
    }

    /**
     * TestHook_MutableConcernRequest is a test hook to make ConcernRequest testable without exposing its
     * members. An instance of TestHook_MutableConcernRequest can be used to construct new concern request
     * instances and set values for testing purposes.
     */
    public static class TestHook_MutableConcernRequest {

        /** An immutable ConcernListRequest for use in testing*/
        private ConcernRequest immutable;

        /**
         * Creates a new mutable concern request
         *
         * @param id The unique id of the concern to load
         * @param token The token of the mutable request
         */
        public TestHook_MutableConcernRequest(long id, String token) {
            immutable = new ConcernRequest();
            immutable.concernId = id;
            immutable.accessToken = token;
        }

        public ConcernRequest build(){
            return immutable;
        }

        public void setMutableLimit(long mutableId) { immutable.concernId = mutableId; }

        public void setMutableToken(String mutableToken) { immutable.accessToken = mutableToken; }
    }
}
