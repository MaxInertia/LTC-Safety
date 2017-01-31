package com.cs371group2.admin;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

/**
 * Created by Brandon on 2017-01-30.
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

        //If this is the first time a user is attempting to sign-in
        if(accountDao.load(user.getId()) == null){

            //Create a new, unverified user account
            Account userAccount = new Account(user.getId(), AccountType.UNVERIFIED);

            //Save the user account to the database
            accountDao.save(userAccount);
        }

        //Assert that the user account is in the database
        assert(accountDao.load(user.getId()) != null);
    }

    @ApiMethod(name = "grantAccess", path = "/admin/grantAccess")
    public void grantAccess(String userId, AccountType accessType){

    }
}
