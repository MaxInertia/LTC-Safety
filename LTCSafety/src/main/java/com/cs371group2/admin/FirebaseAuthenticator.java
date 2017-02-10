package com.cs371group2.admin;

import com.cs371group2.account.Account;

import java.util.Set;

/**
 * This class represents a firebase authenticator object for ensuring that a user has the correct admission levels
 * when accessing functionality that requires firebase authentication.
 *
 * Created on 2017-02-09.
 */
public class FirebaseAuthenticator extends Authenticator {

    /**
     * Authenticates the given firebase token and returns the account associated with it
     *
     * @param token The user's token to be authenticated
     * @return The account associated with the given token\
     * @precond token is valid and non-null
     */
    @Override
    public Account authenticate(String token){ return null; }

    /**
     * Creates an authenticator class that contains the given permission verifiers
     */

    public FirebaseAuthenticator(Set<PermissionVerifier> verifiers){
        super(verifiers);
    }
}
