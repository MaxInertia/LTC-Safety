package com.cs371group2.admin;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;
import com.cs371group2.client.UpdateConcernStatusResponse;
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
 * Invariance properties: This class assumes that administrative permissions are required for the request to be
 * fulfilled. It also assumes that the requester has access to the unique long id of the concern they desire. Finally,
 * it assumes that the user will be authenticating themselves via firebase token. Finally, it also assumes that
 * the given concernStatus will be applied to the requested concern in the method it is used.
 *
 * Created on 2017-02-08.
 */
public class UpdateConcernStatusRequest extends ConcernRequest implements Validatable{

    /** The ConcernStatus to be applied to concern associated with the given ID. */
    private ConcernStatusType concernStatus;

    private static final String NULL_TYPE_ERROR = "Unable to update a concern's status with a null status type.";

    /**
     * Validates the ConcernListRequest to ensure that the fields are legal and non-null.
     *
     * @return The result of the validation, including a reason in the case of failure
     */
    @Override
    public ValidationResult validate() {
        if(concernStatus == null){
            return new ValidationResult(NULL_TYPE_ERROR);
        }

        return super.validate();
    }

    public ConcernStatusType getConcernStatus() {
        return concernStatus;
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
            immutable = new UpdateConcernStatusRequest();
            immutable.concernStatus = statusType;
            immutable.concernId = id;
            immutable.accessToken = token;
        }

        public UpdateConcernStatusRequest build(){
            UpdateConcernStatusRequest request = new UpdateConcernStatusRequest();
            request.concernStatus = immutable.concernStatus;
            request.concernId = immutable.concernId;
            request.accessToken = immutable.accessToken;

            return request;
        }

        public void setMutableConcernType(ConcernStatusType statusType) {
            immutable.concernStatus = statusType;
        }

        public void setMutableConcernId(long mutableId) { immutable.concernId = mutableId; }
    }
}
