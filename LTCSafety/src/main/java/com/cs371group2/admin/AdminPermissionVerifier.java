package com.cs371group2.admin;

import com.cs371group2.account.Account;
import com.cs371group2.account.AccountPermissions;

/**
 * Specialized verifier object for checking if the given account and access token is verified and has
 * administrative permission levels.
 *
 * History property: Instances of this class are immutable from the time they are created.
 *
 * Invariance properties: This class assumes that the given account and access token are non-null and that
 * the account is a valid account within the database. As well, it assumes that the access token contains the
 * information corresponding to the given account and only the given account.
 *
 * Created on 2017-02-09.
 */
class AdminPermissionVerifier extends PermissionVerifier{

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
        assert(account != null);
        assert(token != null);

        return( token.isEmailVerified() && account.getPermissions() == AccountPermissions.ADMIN );
    }
}
