package com.cs371group2.admin;

/**
 * Created on 2017-02-03.
 */
public class FirebaseToken {

    /** The user's unique Firebase ID */
    private String uid;

    /** The email associated with this token */
    private String email;

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    /** Creates a firebase token with the given user ID and email */
    public FirebaseToken(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }
}