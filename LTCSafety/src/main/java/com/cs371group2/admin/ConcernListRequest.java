package com.cs371group2.admin;

import com.cs371group2.Validatable;

/**
 * This object represents a concern request containing an offset and a limit,
 * both of which will be to access the concern database. It will also include all
 * necessary functionality for authenticating the requester.
 *
 * History properties: Instances of this class are immutable from the time they are created.
 *
 * Invariance properties: This class assumes that paging information is required to go along with the
 * concerns requested, and that archived and non-archived concerns are not to be loaded within the same request.
 *
 * Created on 2017-02-08.
 */
public final class ConcernListRequest extends PagedRequest implements Validatable {

    /** Should the request load archived or non-archived concerns? */
    private boolean archived;

    /** @return Whether the request should load archived or non-archived concerns */
    public boolean isArchived() {
        return archived;
    }

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
        public TestHook_MutableConcernListRequest(int limit, int offset, String token, boolean archived) {
            immutable = new ConcernListRequest();
            immutable.limit = limit;
            immutable.offset = offset;
            immutable.accessToken = token;
            immutable.archived = archived;
        }

        public ConcernListRequest build(){
            ConcernListRequest request = new ConcernListRequest();
            request.limit = immutable.limit;
            request.offset = immutable.offset;
            request.accessToken = immutable.accessToken;
            request.archived = immutable.archived;
            return request;
        }

        public void setMutableLimit(int mutableLimit) { immutable.limit = mutableLimit; }

        public void setMutableOffset(int mutableOffset) { immutable.offset = mutableOffset; }

        public void setMutableToken(String mutableToken) { immutable.accessToken = mutableToken; }

        public void setMutableArchived(boolean mutableArchived) { immutable.archived = mutableArchived; }
    }
}
