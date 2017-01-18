package com.cs371group2.concern;

/**
 * The owner token class is used to store the JWS token that is created upon concern submission.
 * This JWS contains the identifier used to identify the concern within the data store. Possessing
 * an owner token gives a client the ability to retract the concern that they have submitted.
 *
 * Created on 2017-01-17.
 */
class OwnerToken {

    /**
     * The raw JWS token string used to verify that the owner token is authentic. The identifier used
     * to locate the concern within the database is stored in the payload section of this token as
     * the subject.
     */
    private String token;

    public String getToken() {
        return token;
    }
}
