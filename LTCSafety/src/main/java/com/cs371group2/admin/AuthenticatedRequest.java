package com.cs371group2.admin;

import com.cs371group2.Validatable;
import com.cs371group2.account.Account;
import com.google.api.server.spi.response.UnauthorizedException;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by Brandon on 2017-02-09.
 */
abstract class AuthenticatedRequest{

    /**
     * The access token of the user to be checked for authenticity
     */
    protected String accessToken;

    /**
     * Authenticates the requests token and returns the account associated with it if successful. If not,
     * throws an exception
     *
     * @return The account of the authenticated user
     * @throws UnauthorizedException If the accessToken does not have the required permission, throw this
     */
    public Account authenticate() throws UnauthorizedException {
        if(accessToken == null) {
            throw new UnauthorizedException("Access token is null!");
        }

        Account account = getAuthenticator().authenticate(accessToken);
        return account;
    }

    /**
     * Returns a reference to a FirebaseAuthenticator
     * @return The firebase authenticator to use
     */
    protected Authenticator getAuthenticator(){
        return new FirebaseAuthenticator();
    };


    public String getAccessToken() {
        return accessToken;
    }

}
