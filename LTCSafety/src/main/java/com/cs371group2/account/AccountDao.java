package com.cs371group2.account;

import com.cs371group2.Dao;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class extends the data-access object in our system to load, save, and delete accounts.
 * These accounts
 *
 * Created on 2017-02-06.
 */
public class AccountDao extends Dao<Account> {

    /**
     * Logger definition for this class.
     */
    private static final Logger logger = Logger.getLogger(Dao.class.getName());

    public AccountDao() {
        super(Account.class);
    }

    public Account load(String id) {
        assert (id != null);

        logger.log(Level.FINER, "Concern # " + id + " successfully loaded from the datastore.");
        return ObjectifyService.ofy().load().type(Account.class).id(id).now();
    }

    /**
     * Saves an account synchronously to the datastore. If an account with the same
     * identifier already exists it will be overwritten.
     *
     * @param account The account to be saved to the datastore.
     * @return The key used to load the account from the datastore.
     * @precond account and it's fields are non-null
     * @postcond The account has been saved to the datastore for future loading and / or deleting. If
     * the entity has an identifier of type long it will have been populated with the entity's
     * unique identifier.
     */
    public Key<Account> save(Account account) {
        assert (account != null);
        assert (account.getId() != null);
        assert (account.getPermissions() != null);

        logger.log(Level.FINER,"Successfully saved Entity " + account.toString() + " to the datastore.");
        return super.save(account);
    }
}
