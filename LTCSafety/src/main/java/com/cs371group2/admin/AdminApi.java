package com.cs371group2.admin;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiIssuer;
import com.google.api.server.spi.config.ApiIssuerAudience;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.auth.EspAuthenticator;
import com.google.api.server.spi.response.UnauthorizedException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Administrative API class used for requesting and granting account authorization
 *
 * Created on 2017-01-30.
 */
@Api(name = "admin",
        title = "Admin API",
        version = "v1",
        authenticators = {EspAuthenticator.class},
        issuers = {
                @ApiIssuer(
                        name = "firebase",
                        issuer = "https://securetoken.google.com/ltc-safety",
                        jwksUri = "https://www.googleapis.com/service_accounts/v1/metadata/x509/securetoken@system.gserviceaccount.com")
        },
        issuerAudiences = {
                @ApiIssuerAudience(name = "firebase", audiences = "ltc-safety")
        })
public class AdminApi {

    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( AdminApi.class.getName() );

    /**
     * Requests user access for users that are signing in for their first time
     *
     * @param user The user to request access for
     *
     * @precond User is non-null
     * @postcond An account has been created for the user with an access type of "UNVERIFIED"
     */
    @ApiMethod(name = "requestAccess", path = "/admin/requestAccess", httpMethod = ApiMethod.HttpMethod.GET)
    public void requestAccess(User user){

        assert(user != null);

        //Create a new AccountDao
        AccountDao accountDao = new AccountDao();

        //If this is the first time a user is attempting to sign-in, create a new account and save it
        if(accountDao.load(user.getId()) == null){
            Account userAccount = new Account(user.getId());
            accountDao.save(userAccount);
        }

        LOGGER.log(Level.INFO, "User " + user.getId() + " has requested account verification");

        //Assert that the user account is in the database
        assert(accountDao.load(user.getId()) != null);
    }

    /**
     * Used by administrators to modify the account type of a user
     *
     * @param admin The admin account that is granting access
     * @param userId The userID of the account to modify
     * @param accountType The user's new account type
     *
     * @precond admin != null, userId != null, accountType != null
     * @postcond Desired user account's access level has been modified
     */
    @ApiMethod(name = "grantAccess", path = "/admin/grantAccess", httpMethod = ApiMethod.HttpMethod.GET)
    public void grantAccess(User admin, String userId, AccountType accountType) throws UnauthorizedException{

        assert(admin != null);
        assert(userId != null);
        assert(accountType != null);

        AccountDao accountDao = new AccountDao();
        Account adminAccount = accountDao.load(admin.getId());

        if(adminAccount.getAccessType() != AccountType.ADMIN) {
            LOGGER.log(Level.FINE, "Non-admin attempted to modify a user's access level");
            throw new UnauthorizedException("Non-admin attempted to modify a user's access level");
        }

        //Assign the user account the given access type and updates its database entry
        Account userAccount = accountDao.load(userId);
        userAccount.setAccessType(accountType);
        accountDao.save(userAccount);

        LOGGER.log(Level.INFO, "Account " + userId +
                " has had it's access level changed to " + accountType.toString());
    }
}
