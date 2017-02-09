package com.cs371group2.admin;

import com.cs371group2.account.Account;
import com.google.api.server.spi.config.Authenticator;

/**
 * Created by Brandon on 2017-02-09.
 */
public class AuthenticatedRequest {

    /**
     * The firebase token of the user to be checked for authenticity
     */
    private String accessToken;

    public Account authenticate(){ return null; }

    protected Authenticator getAuthenticator(){ return null; };

}
