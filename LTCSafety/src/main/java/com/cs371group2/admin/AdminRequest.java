package com.cs371group2.admin;

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
        return new AdminAuthenticator();
    }
}
