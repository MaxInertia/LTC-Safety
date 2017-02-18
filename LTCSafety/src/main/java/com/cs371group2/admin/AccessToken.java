package com.cs371group2.admin;

/**
 * Represents an access token to be used for checking permissions of a user's account.
 *
 * Created on 2017-02-09.
 */
final class AccessToken {

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
     * @precond tokenId != null, tokenEmail or tokenName != null
     */
    public AccessToken(String tokenEmail, String tokenId, String tokenName, boolean tokenIsVerified) throws IllegalArgumentException{
        if((tokenEmail.isEmpty() && tokenName.isEmpty()) || tokenId.isEmpty()){
            throw new IllegalArgumentException("Access token was not given enough information to be created");
        }

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

    public boolean isEmailVerified() {
        return isVerified;
    }
}
