package com.cs371group2.admin;

/**
 * The account type should designate the account's level of access within the system.
 * Admins should have the most control and functionality. Users should be able to submit and
 * retract concerns. Unverified accounts should not be able to do anything until they are verified.
 *
 * Created on 2017-01-30.
 */
public enum AccountType {
    /**
     * If an account is unverified, it should not be able to perform user or administrator functionality
     */
    UNVERIFIED,

    /**
     * User accounts should be able to view and respond to concerns, as well as estimate completion time.
     */
    USER,

    /**
     * Admin accounts should be able to view and respond to concerns, as well as estimate completion time.
     * On top of this, admin accounts should be able to change the AccountType of other user accounts.
     */
    ADMIN
}
