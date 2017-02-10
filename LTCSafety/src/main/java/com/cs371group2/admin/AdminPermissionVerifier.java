package com.cs371group2.admin;

import com.cs371group2.account.Account;

/**
 * Specialized verifier object for checking if the given account and access token is verified and has
 * administrative permission levels.
 *
 * Created on 2017-02-09.
 */
public class AdminPermissionVerifier extends PermissionVerifier {

    /**
     * Checks if the given account is verified and has administrative permissions.
     *
     * @param account The account to check for permissions.
     * @param token The AccessToken representing the user's information.
     * @return Whether the account has been verified or not.
     * @precond account != null and its fields are non-null.
     * @precond token != null and its fields are non-null.
     */
    public boolean hasPermission(Account account, AccessToken token){
        return false;
    }
}
