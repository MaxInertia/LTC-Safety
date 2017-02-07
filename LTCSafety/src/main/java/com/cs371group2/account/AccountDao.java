package com.cs371group2.account;

import com.cs371group2.Dao;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Brandon on 2017-02-06.
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
        return null;
    }

    /**
     * Saves an account synchronously to the datastore. If an account with the same
     * identifier already exists it will be overwritten.
     *
     * @param entity The entity to be saved to the datastore.
     * @return The key used to load the entity from the datastore.
     * @precond entity != null
     * @postcond The entity has been saved to the datastore for future loading and / or deleting. If
     * the entity has an identifier of type long it will have been populated with the entity's
     * unique identifier.
     */
    public Key<Account> save(Account entity) {

        assert (entity.getId() != null);
        assert (entity.getPermissions() != null);

        logger.log(Level.FINER,"Successfully saved Entity " + entity.toString() + " to the datastore.");
        return ObjectifyService.ofy().save().entity(entity).now();
    }
}
