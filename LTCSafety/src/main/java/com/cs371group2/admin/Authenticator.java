package com.cs371group2.admin;

import android.util.Pair;
import com.cs371group2.account.Account;
import com.google.api.server.spi.response.UnauthorizedException;

import java.util.Collection;

/**
 * This class represents an authenticator object for ensuring that a user has the correct admission levels
 * when accessing functionality that requires authentication.
 *
 * Created on 2017-02-09.
 */
abstract class Authenticator{

    /** The list of permission verifiers that a user must pass to be considered authenticated */
    private Collection<PermissionVerifier> permissionVerifiers;

    /**
     * Authenticates the given token and returns the account associated with it
     *
     * @param token The user's token to be authenticated
     * @return The account associated with the given token\
     * @precond token is valid and non-null
     */
    public final Account authenticate(String token) throws UnauthorizedException {
        Pair<Account, AccessToken> infoPair = authenticateAccount(token);

        boolean isVerified = false;
        for (PermissionVerifier verifier : permissionVerifiers){
            if(verifier.hasPermission(infoPair.first, infoPair.second)){
                isVerified = true;
            }
        }

        if(!isVerified){
            throw new UnauthorizedException("User attempted access without proper permissions.");
        }

        return infoPair.first;
    }

    /**
     * Authenticates the given token and returns the account associated with it. This should be implemented to
     * handle the authenticator's specific token needs.
     *
     * @param token The user's token to be authenticated
     * @return The account associated with the given token\
     * @precond token is valid and non-null
     */
    protected abstract Pair<Account, AccessToken> authenticateAccount(String token) throws UnauthorizedException;

    /**
     * Sets the authenticator permission verifiers to the given collection of verifiers.
     * @param permissionVerifiers The collection of verifiers to be checked when authenticating a user.
     * @precond permissionVerifiers != null
     * @postcond PermissionVerifiers != null
     */
    public void setPermissionVerifiers(Collection<PermissionVerifier> permissionVerifiers) {
        assert(permissionVerifiers != null);
        this.permissionVerifiers = permissionVerifiers;
        assert(this.permissionVerifiers != null);
    }
}
