package com.cs371group2.admin;

/**
 * Represents an access token to be used for checking permissions of a user's account.
 *
 * Created on 2017-02-09.
 */
public class AccessToken {

    /**
     * The email of the user requesting access.
     */
    private String email;

    /**
     * The id of the user requesting access.
     */
    private String id;

    /**
     * The name of the user requesting access.
     */
    private String name;

    /**
     * Is the user verified or not?
     */
    private boolean isVerified;

    /**
     * Creates an access token representing the given information.
     *
     * @param tokenEmail The email for the access token
     * @param tokenId The unique user id for the access token
     * @param tokenName The name of the user
     * @param tokenIsVerified Is the user verified or not?
     * @precond None of the string parameters are null
     */
    public AccessToken(String tokenEmail, String tokenId, String tokenName, boolean tokenIsVerified){
        assert(tokenEmail != null);
        assert(tokenId != null);
        assert(tokenName != null);

        email = tokenEmail;
        id = tokenId;
        name = tokenName;
        isVerified = tokenIsVerified;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isVerified() {
        return isVerified;
    }
}
