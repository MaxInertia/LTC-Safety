package com.cs371group2.admin;

import com.cs371group2.DatastoreTest;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;
import com.google.api.server.spi.response.BadRequestException;
import com.googlecode.objectify.Key;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        Account keyLoadedConcern = dao.load(key);
        assertAccounts(account, keyLoadedConcern);

        // Load it using its id
        Account idLoadedConcern = dao.load(account.getId());
        assertAccounts(account, idLoadedConcern);

        // Delete it synchronously
        dao.delete(account).now();

        // Assert its been deleted
        Account deletedConcern = dao.load(account.getId());
        assertNull(deletedConcern);
    }


    /**
     * Ensures that submission fails when the account's id is null
     */
    @Test(expected = AssertionError.class)
    public void nullId() throws Exception {
        Account testAccount = new AccountTest().generateAccount();
        testAccount.setId(null);
        AccountDao dao = new AccountDao();
        dao.save(testAccount);
    }

    /**
     * Ensures that submission fails when the account permissions are null
     */
    @Test(expected = AssertionError.class)
    public void nullPermissions() throws Exception {
        Account testAccount = new AccountTest().generateAccount();
        testAccount.setPermissions(null);
        AccountDao dao = new AccountDao();
        dao.save(testAccount);
    }


}
