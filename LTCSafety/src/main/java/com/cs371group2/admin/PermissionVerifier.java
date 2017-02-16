package com.cs371group2.admin;

import com.cs371group2.account.Account;

/**
 * This object serves as a verifier class for accounts with functionality for checking
 * permissions, and should be extended for more specific verification requirements.
 *
 * Created on 2017-02-09.
 */
public abstract class PermissionVerifier {

    /**
     * Checks if the given account is verified.
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

        return token.isEmailVerified();
    }
}
