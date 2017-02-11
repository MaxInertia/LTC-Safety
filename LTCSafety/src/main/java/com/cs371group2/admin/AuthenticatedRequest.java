package com.cs371group2.admin;

import com.cs371group2.account.Account;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by Brandon on 2017-02-09.
 */
public abstract class AuthenticatedRequest {

    /**
     * The access token of the user to be checked for authenticity
     */
    protected String accessToken;

    /**
     * Authenticates the requests token and returns the account associated with it if successful. If not,
     * throws an exception
     *
     * @return The account of the authenticated user
     * @throws GeneralSecurityException If the accessToken does not have the required permission, throw this
     * @throws IOException If the accessToken is not in the correct format or unreadable, throw this
     */
    public Account authenticate() throws GeneralSecurityException, IOException{
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

}
