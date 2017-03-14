package com.cs371group2.admin;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;

import java.util.logging.Logger;

/**
 * This object represents a paged request containing an offset and a limit,
 * both of which will be to access the database. It will also include all
 * necessary functionality for authenticating the requester.
 *
 * History property: Instances of this class are immutable from the time they are created.
 *
 * Created on 2017-02-08.
 */
public class PagedRequest extends AdminRequest implements Validatable {

    private static final String NULL_TOKEN_ERROR = "Unable to access requested list due to non-existent credentials.";

    private static final String EMPTY_TOKEN_ERROR = "Unable to access requested list due to receiving an empty access token.";

    private static final String INVALID_OFFSET_ERROR = "Unable to request requested list due to an invalid requested offset.";

    private static final String INVALID_LIMIT_ERROR = "Unable to request requested list due to an invalid requested limit.";

    /** The offset in the database to begin loading from */
    int offset;

    /** The maximum number of elements to be loaded from the database */
    int limit;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * Validates the PagedRequest to ensure that the fields are legal and non-null.
     *
     * @return The result of the validation, including a reason in the case of failure
     */
    @Override
    public ValidationResult validate() {
        if(null == accessToken){
            return new ValidationResult(NULL_TOKEN_ERROR);
        } else if (accessToken.isEmpty()){
            return new ValidationResult(EMPTY_TOKEN_ERROR);
        } else if (1 > limit){
            return new ValidationResult(INVALID_LIMIT_ERROR);
        } else if (0 > offset){
            return new ValidationResult(INVALID_OFFSET_ERROR);
        }

        return new ValidationResult();
    }

    /**
     * TestHook_MutablePagedRequest is a test hook to make PagedRequest testable without exposing its
     * members. An instance of TestHook_MutableCPagedRequest can be used to construct new paged request
     * instances and set values for testing purposes.
     */
    public static class TestHook_MutablePagedRequest {

        /** An immutable PagedRequest for use in testing*/
        private PagedRequest immutable;

        /**
         * Creates a new mutable concern request
         *
         * @param limit The limit of the mutable request
         * @param offset The offset of the mutable request
         * @param token The token for the mutable request
         */
        public TestHook_MutablePagedRequest(int limit, int offset, String token) {
            immutable = new PagedRequest();
            immutable.limit = limit;
            immutable.offset = offset;
            immutable.accessToken = token;
        }

        public PagedRequest build(){
            return immutable;
        }

        public void setMutableLimit(int mutableLimit) { immutable.limit = mutableLimit; }

        public void setMutableOffset(int mutableOffset) { immutable.offset = mutableOffset; }

        public void setMutableToken(String mutableToken) { immutable.accessToken = mutableToken; }
    }
}
