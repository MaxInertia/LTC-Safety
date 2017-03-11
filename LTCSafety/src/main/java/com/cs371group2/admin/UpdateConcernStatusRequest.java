package com.cs371group2.admin;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernStatus;
import com.cs371group2.concern.ConcernStatusType;

import javax.validation.Valid;
import java.util.List;

/**
 * This object represents an update concern request containing a concern id
 * and the concern status to apply to the concern. It will also include all necessary
 * functionality for authenticating the requester.
 *
 * History property: Instances of this class are immutable from the time they are created.
 *
 * Created on 2017-02-08.
 */
public class UpdateConcernStatusRequest extends AdminRequest implements Validatable{

    /** The ConcernStatus to be applied to concern associated with the given ID. */
    private ConcernStatus concernStatus;

    /** The id of the concern to update the status of */
    private long concernId;

    private static final String NULL_TOKEN_ERROR = "Unable to update a concern's status due to non-existent credentials.";

    private static final String EMPTY_TOKEN_ERROR = "Unable to update a concern's status due to receiving an empty access token.";

    private static final String NULL_TYPE_ERROR = "Unable to update a concern's status with a null status type.";

    /**
     * Creates an UpdateConcernStatusRequest for use with the AdminAPI to update the status of a concern.
     *
     * @param statusType The type of concern status to apply to the concern
     * @param id The unique id of the concern to be updated
     * @precond statusType != null
     */
    public UpdateConcernStatusRequest(ConcernStatusType statusType, long id, String userToken){
        assert(statusType != null);
        assert(userToken != null);

        concernStatus = new ConcernStatus(statusType);
        concernId = id;
        accessToken = userToken;
    }

    /**
     * Validates the ConcernListRequest to ensure that the fields are legal and non-null.
     *
     * @return The result of the validation, including a reason in the case of failure
     */
    @Override
    public ValidationResult validate() {
        if(null == accessToken){
            return new ValidationResult(NULL_TOKEN_ERROR);
        } else if (accessToken.isEmpty()) {
            return new ValidationResult(EMPTY_TOKEN_ERROR);
        } else if(concernStatus == null){
            return new ValidationResult(NULL_TYPE_ERROR);
        }

        return new ValidationResult();
    }

    /**
     * TestHook_MutableUpdateConcernStatusRequest is a test hook to make UpdateConcernStatusRequest testable without
     * exposing its members. An instance of TestHook_MutableUpdateConcernStatusRequest can be used to construct
     * new UpdateConcernStatusRequest instances and set values for testing purposes.
     */
    public static class TestHook_MutableUpdateConcernStatusRequest {

        /** An immutable ConcernListRequest for use in testing*/
        private UpdateConcernStatusRequest immutable;

        /**
         * Creates a new mutable UpdateConcernStatusRequest for testing purposes.
         *
         * @param statusType The type of concern status to apply to the concern.
         * @param id The unique id of the concern to be updated.
         */
        public TestHook_MutableUpdateConcernStatusRequest(ConcernStatusType statusType, long id, String token) {
            immutable = new UpdateConcernStatusRequest(statusType, id, token);
        }

        public UpdateConcernStatusRequest build(){
            return immutable;
        }

        public void setMutableConcernType(ConcernStatusType statusType) {
            immutable.concernStatus = new ConcernStatus(statusType);
        }

        public void setMutableConcernId(long mutableId) { immutable.concernId = mutableId; }
    }
}
