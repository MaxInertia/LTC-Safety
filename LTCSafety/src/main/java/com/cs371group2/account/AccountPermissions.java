package com.cs371group2.account;

/**
 * This enum represents the permission level of an account, which is used to dictate which operations
 * they can take while using the system.
 *
 * Created on 2017-02-06.
 */
public enum AccountPermissions {

    /**
     * Accounts may hold admin priviledges which allows them to view and respond to concerns, as well as assign
     * other accounts admin rights
     */
    ADMIN,

    /**
     * An account that has signed up but hasn't been given access to the system yet.
     */
    UNVERIFIED,

    /**
     * An account who's access request has been rejected by an existing administrator.
     */
    DENIED
}
