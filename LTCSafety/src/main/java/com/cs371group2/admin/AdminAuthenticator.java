package com.cs371group2.admin;


import java.util.Set;

/**
 * Authenticator class that takes a firebase token and verifies that the token owner is an admin. This
 * is used to ensure that no unauthorized access is granted.
 *
 * Created on 2017-02-11.
 */
public class AdminAuthenticator extends FirebaseAuthenticator {

    /**
     * Returns the set of permission verifiers associated with this authenticator
     * @return
     */
    public Set<PermissionVerifier> getPermissionVerifiers(){
        return permissionVerifiers;
    }

    /**
     * Creates a new admin authenticator for checking that a user has admin permissions.
     * @postcond permissionVerifiers set has admin permission verifier in it.
     */
    public AdminAuthenticator(){
        super();
        permissionVerifiers.add(new AdminPermissionVerifier());
    }
}
