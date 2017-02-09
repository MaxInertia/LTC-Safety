package com.cs371group2.admin;

import com.cs371group2.account.Account;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.DatastoreTest;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;

/**
 * These tests are created for the Account class to ensure that account data is properly validated when
 * submitted into the system. This involves checking that the correct responses are returned when unexpected or
 * invalid data is provided during concern submission.
 *
 * Created on 2017-02-06.
 */
public class AccountTest extends DatastoreTest {

    //Test ID for
    private String testId = "5oz3HPPnZuUzV4hcwXqtgG6tjc03";

    /**
     * Generates a new account with all instance variables initialized to be valid strings. This is
     * used as a helper method to construct new concern data.
     *
     * @return A new account data object with all instance variables initialized.
     */
    public Account generateAccount() {
        return new Account(testId, AccountPermissions.ADMIN);
    }

    @Test(expected = AssertionError.class)
    private void NullIdTest() throws Exception{
        Account account = new Account(null, AccountPermissions.ADMIN);
        assertNull(account);
    }

    @Test(expected = AssertionError.class)
    private void NullPermissionTest() throws Exception{
        Account account = new Account(testId, null);
        assertNull(account);
    }
}
