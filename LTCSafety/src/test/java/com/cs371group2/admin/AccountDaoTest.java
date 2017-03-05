package com.cs371group2.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.cs371group2.DatastoreTest;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import org.junit.Test;

/**
 *  * This class is used to test the Dao class and the AccountDao class. This class tests the base data
 * access object class due to it being abstract making it difficult to test directly. As a result,
 * load, save, and delete are tested despite not being overridden by the concern data access
 * object.
 *
 * Created on 2017-02-06.
 */
public class AccountDaoTest extends DatastoreTest {

    /**
     * Assert that the two accounts are equal based on the test parameters for the data access
     * object testing.
     *
     * @param account The originally created concern that the test concern should be compared to.
     * @param loadedAccount The test concern to check for correctness.
     */
    private void assertAccounts(Account account, Account loadedAccount) {

        assertEquals(loadedAccount.getId(), account.getId());
        assertEquals(loadedAccount.getPermissions(), account.getPermissions());

    }

    /**
     * Test for testing the saving, loading, and deleting of accounts. This creates an account, saves
     * it, loads it using multiple methods, then deletes it.
     */
    @Test
    public void saveLoadDeleteTest() throws Exception {
        AccountDao dao = new AccountDao();

        // Save the object
        Account account = new AccountTest().generateAccount();

        Key<Account> key = dao.save(account);

        // Load it using its key
        Account keyLoadedAccount = dao.load(key);
        assertAccounts(account, keyLoadedAccount);

        // Load it using its id
        Account idLoadedAccount = dao.load(account.getId());
        assertAccounts(account, idLoadedAccount);

        // Delete it synchronously
        dao.delete(account).now();

        // Assert its been deleted
        Account deletedConcern = ObjectifyService.ofy().load().key(key).now();
        assertNull(deletedConcern);
    }

    /**
     * Ensures that saving fails when the account permissions are null
     */
    @Test(expected = AssertionError.class)
    public void nullPermissions() throws Exception {

        Account testAccount = new AccountTest().generateAccount();
        testAccount.setPermissions(null);
        new AccountDao().save(testAccount);
    }
}
