package com.cs371group2.admin;

import com.cs371group2.account.Account;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Set;

/**
 * This class represents an authenticator object for ensuring that a user has the correct admission levels
 * when accessing functionality that requires authentication.
 *
 * Created on 2017-02-09.
 */
public abstract class Authenticator {

    private Set<PermissionVerifier> permissionVerifiers;

    /**
     * Authenticates the given token and returns the account associated with it
     *
     * @param token The user's token to be authenticated
     * @return The account associated with the given token\
     * @precond token is valid and non-null
     */
    public Account authenticate(String token) throws GeneralSecurityException, IOException { return null; }

    /**
     * Returns a set of permission verifiers to be checked against an account.
     *
     * @return The set of permission verifiers
     */
    protected Set<PermissionVerifier> getPermissionVerifiers(){ return permissionVerifiers; }

    /**
     * Creates an authenticator class that contains the given permission verifiers.
     *
     * @param  verifiers The set of permission verifiers to use when authenticating.
     * @precond permissionVerifiers != null
     */
    public Authenticator(Set<PermissionVerifier> verifiers){
        assert(verifiers != null);
        permissionVerifiers = verifiers;
    }
}
