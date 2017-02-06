package com.cs371group2.admin;

import com.cs371group2.Dao;
import com.googlecode.objectify.ObjectifyService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data access object for requesting and granting access to user accounts.
 *
 * Created on 2017-01-30.
 */
public class AccountDao extends Dao<Account> {

    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Dao.class.getName() );

    /**
     * Creates a data access object for requesting and granting access to user accounts.
     */
    public AccountDao() {
        super(Account.class);
    }


}
