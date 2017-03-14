package com.cs371group2.admin;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;

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
public final class ConcernListRequest extends PagedRequest implements Validatable {

    /**
     * TestHook_MutableConcernListRequest is a test hook to make ConcernListRequest testable without exposing its
     * members. An instance of TestHook_MutableConcernListRequest can be used to construct new concern request
     * instances and set values for testing purposes.
     */
    public static class TestHook_MutableConcernListRequest {

        /** An immutable ConcernListRequest for use in testing*/
        private ConcernListRequest immutable;

        /**
         * Creates a new mutable concern request
         *
         * @param limit The concern limit of the mutable request
         * @param offset The concern offset of the mutable request
         * @param token The token of the mutable request
         */
        public TestHook_MutableConcernListRequest(int limit, int offset, String token) {
            immutable = new ConcernListRequest();
            immutable.limit = limit;
            immutable.offset = offset;
            immutable.accessToken = token;
        }

        public ConcernListRequest build(){
            ConcernListRequest request = new ConcernListRequest();
            request.limit = immutable.limit;
            request.offset = immutable.offset;
            request.accessToken = immutable.accessToken;
            return request;
        }

        public void setMutableLimit(int mutableLimit) { immutable.limit = mutableLimit; }

        public void setMutableOffset(int mutableOffset) { immutable.offset = mutableOffset; }

        public void setMutableToken(String mutableToken) { immutable.accessToken = mutableToken; }
    }
}
