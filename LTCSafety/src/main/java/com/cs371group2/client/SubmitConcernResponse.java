package com.cs371group2.client;

import com.cs371group2.concern.Concern;

/**
 * The response object sent back to the client when a concern is submitted. This contains all data
 * related to the concern and the token giving ownernship. Its only purpose is to be sent back to
 * the client.
 *
 * History properties: Instances of this class are immutable from the time they are created.
 *
 * Invariance properties: This class assumes that a valid concern submission has taken place, and the concern
 * given upon instantiation is the submitted concern.
 *
 * Created on 2017-02-01.
 */
final class SubmitConcernResponse {

    /**
     * The JWT giving the client that holds it the right to retract or modify the token.
     */
    private OwnerToken ownerToken;

    /**
     * All information related to the submitted concern includes its unique identifier.
     */
    private Concern concern;

    public OwnerToken getOwnerToken() {
        return ownerToken;
    }

    public Concern getConcern() {
        return concern;
    }

    /**
     * Creates a new submit concern response for passing a concern and owner token back to the client.
     * @precond concern != null
     * @param concern The that was created from the client's submitted data.
     */
    SubmitConcernResponse(Concern concern) {

        assert concern != null;

        this.ownerToken = concern.generateOwnerToken();
        this.concern = concern;
    }
}
