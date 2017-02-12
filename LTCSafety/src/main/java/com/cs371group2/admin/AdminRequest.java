package com.cs371group2.admin;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Represents a request that requires administrative privileges to be successful
 * Created on 2017-02-09.
 */
public abstract class AdminRequest extends AuthenticatedRequest{

    /**
     * Returns a reference to an admin authenticator
     * @return An admin authenticator
     */
    protected Authenticator getAuthenticator(){
        Authenticator authenticator = super.getAuthenticator();

        List<PermissionVerifier> verifiers = new LinkedList<PermissionVerifier>();
        verifiers.add(new AdminPermissionVerifier());
        authenticator.setPermissionVerifiers(verifiers);

        return authenticator;
    }
}
