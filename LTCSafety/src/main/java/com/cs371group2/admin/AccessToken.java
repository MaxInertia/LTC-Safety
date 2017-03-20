package com.cs371group2.admin;

/**
 * Represents an access token to be used for checking permissions of a user's account.
 *
 * History property: Instances of this class are immutable from the time they are created.
 *
 * Invariance Properties: This class assumes that the given email, id, and name are validly formatted. It also assumes
 * that the given email verified boolean is in-fact correct.
 *
 * Created on 2017-02-09.
 */
public final class AccessToken {

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
    private boolean isEmailVerified;

    /**
     * Creates an access token representing the given information.
     *
     * @param email The email for the access token
     * @param id The unique user id for the access token
     * @param name The name of the user
     * @param isEmailVerified Is the user verified or not?
     * @precond tokenId != null, tokenEmail or tokenName != null
     */
    public AccessToken(String email, String id, String name, boolean isEmailVerified) throws IllegalArgumentException {

        if((email.isEmpty() && name.isEmpty()) || id.isEmpty()){
            throw new IllegalArgumentException("Access token was not given enough information to be created");
        }

        this.email = email;
        this.id = id;
        this.name = name;
        this.isEmailVerified = isEmailVerified;
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
        return isEmailVerified;
    }
}
