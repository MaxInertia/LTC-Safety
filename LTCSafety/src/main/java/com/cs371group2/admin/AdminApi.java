package com.cs371group2.admin;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

/**
 * Administrative API class used for requesting and granting account authorization
 *
 * Created on 2017-01-30.
 */
@Api(name = "admin",
        title = "Admin API",
        version = "v1")
public class AdminApi {

    /**
     * Requests user access for users that are signing in for their first time
     *
     * @param user The user to request access for
     */
    @ApiMethod(name = "requestAccess", path = "/admin/requestAccess")
    public void requestAccess(User user){

        //Create a new AccountDao
        AccountDao accountDao = new AccountDao();

        //If this is the first time a user is attempting to sign-in, create a new account and save it
        if(accountDao.load(user.getId()) == null){
            Account userAccount = new Account(user.getId());
            accountDao.save(userAccount);
        }

        //Assert that the user account is in the database
        //assert(accountDao.load(user.getId()) != null);
    }

    /**
     * Used by administrators to modify the account type of a user
     *
     * @param userId The userID of the account to modify
     * @param accountType The user's new account type
     */
    @ApiMethod(name = "grantAccess", path = "/admin/grantAccess")
    public void grantAccess(String userId, AccountType accountType){

        //Assign the user account the given access type and updates its database entry
        AccountDao accountDao = new AccountDao();
        Account userAccount = accountDao.load(userId);
        userAccount.setAccessType(accountType);
        accountDao.save(userAccount);
    }
}
