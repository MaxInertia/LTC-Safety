package com.cs371group2.client;

import com.cs371group2.concern.ConcernStatus;

/**
 * The update concern status response class is used for sending the data associated with updating a
 * concern's status back to the client. This involves sending back the newly added concern and the
 * identifier for the concern that was updated.
 *
 * History Properties:
 * Concern ID and Concern status will remain constant starting at the point they are passed to the constructor.
 *
 * Created on 2017-02-13.
 */
public final class UpdateConcernStatusResponse {

    /**
     * The unique identifier for the concern who's status was updated.
     */
    private Long concernId;

    /**
     * The status that the concern was changed to.
     */
    private ConcernStatus status;

    public Long getConcernId() {
        return concernId;
    }

    public ConcernStatus getStatus() {
        return status;
    }

    /**
     * Constructs a new concert status response object to send concern status information back to the client.
     * @param concernId The unique identifier for the concern who's status was updated.
     * @param status The status the concern was updated to.
     */
    public UpdateConcernStatusResponse(Long concernId, ConcernStatus status) {

        assert concernId != null;
        assert status != null;

        this.concernId = concernId;
        this.status = status;
    }
}
