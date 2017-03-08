package com.cs371group2.admin;

import com.cs371group2.DatastoreTest;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountPermissions;
import org.junit.Test;

/**
 * These tests are created for the Account class to ensure that account data is properly validated when
 * submitted into the system. This involves checking that the correct responses are returned when unexpected or
 * invalid data is provided during concern submission.
 *
 * Created on 2017-02-06.
 */
public class AccountTest extends DatastoreTest {

    //Test ID and email for test cases
    private String testId = "5oz3HPPnZuUzV4hcwXqtgG6tjc03";
    private String testEmail = "test@email.com";

    /**
     * Generates a new account with all instance variables initialized to be valid strings. This is
     * used as a helper method to construct new concern data.
     *
     * @return A new account data object with all instance variables initialized.
     */
    public Account generateAccount() {
        return new Account(testId, testEmail, AccountPermissions.ADMIN);
    }

    /**
     * Tests with a null id for the account constructor
     * @throws AssertionError
     */
    @Test (expected = AssertionError.class)
    public void nullIdTest() throws Exception{
        new Account(null, testEmail, AccountPermissions.ADMIN);
    }

    /**
     * Tests with a null permission for the account constructor
     * @throws AssertionError
     */
    @Test(expected = AssertionError.class)
    public void nullPermissionTest() throws Exception{
        new Account(testId, testEmail, null);
    }
}
