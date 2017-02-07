package com.cs371group2.admin;

import account.Account;
import account.AccountPermissions;
import com.cs371group2.DatastoreTest;

/**
 * These tests are created for the Account class to ensure that account data is properly validated when
 * submitted into the system. This involves checking that the correct responses are returned when unexpected or
 * invalid data is provided during concern submission.
 *
 * Created on 2017-02-06.
 */
public class AccountTest extends DatastoreTest {

    private String testId = "";

    /**
     * Generates a new account with all instance variables initialized to be valid strings. This is
     * used as a helper method to construct new concern data.
     *
     * @return A new account data object with all instance variables initialized.
     */
    public Account generateAccount() {
        return new Account(testId, AccountPermissions.ADMIN);
    }
}
