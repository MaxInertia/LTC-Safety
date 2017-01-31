package com.cs371group2.admin;

import com.cs371group2.Dao;

/**
 * Created on 2017-01-30.
 */
public class AccountDao extends Dao<Account> {

    /**
     * Creates a data access object for requesting and granting access to user accounts
     */
    public AccountDao() {
        super(Account.class);
    }


}
