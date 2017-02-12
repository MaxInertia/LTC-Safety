package com.cs371group2.admin;

import android.util.Pair;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;
import com.google.api.server.spi.response.UnauthorizedException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents an authenticator object for ensuring that a user has the correct admission levels
 * when accessing functionality that requires authentication.
 *
 * Created on 2017-02-09.
 */
public abstract class Authenticator{


    private Collection<PermissionVerifier> permissionVerifiers;

    /**
     * Authenticates the given token and returns the account associated with it
     *
     * @param token The user's token to be authenticated
     * @return The account associated with the given token\
     * @precond token is valid and non-null
     */
    public Account authenticate(String token) throws UnauthorizedException {
        Pair<Account, AccessToken> infoPair = authenticateAccount(token);

        for (PermissionVerifier verifier : permissionVerifiers){
            if(verifier.hasPermission(infoPair.first, infoPair.second) == false){
                throw new UnauthorizedException("User attempted access without proper permissions.");
            }
        }

        return infoPair.first;
    }

    protected abstract Pair<Account, AccessToken> authenticateAccount(String token) throws UnauthorizedException;

    public void setPermissionVerifiers(Collection<PermissionVerifier> permissionVerifiers) {
        this.permissionVerifiers = permissionVerifiers;
    }
}
