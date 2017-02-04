package com.cs371group2.admin;

/**
 * Object representing a firebase token containing a unique user ID and an associated email
 *
 * Created on 2017-02-03.
 */
public class FirebaseToken {

    /** The user's unique Firebase ID */
    private String uid;

    /** The email associated with this token */
    private String email;

    /** Whether the token is email verified or not */
    private boolean emailVerified;

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public boolean getEmailVerified() {
        return emailVerified;
    }

    /**
     * Creates a firebase token with the given user ID and email
     *
     * @param uid The unique ID of the user
     * @param email The email of the user
     * @param emailVerification Whether the email has been verified or not
     */
    public FirebaseToken(String uid, String email, boolean emailVerification) {
        this.uid = uid;
        this.email = email;
        this.emailVerified = emailVerification;
    }
}