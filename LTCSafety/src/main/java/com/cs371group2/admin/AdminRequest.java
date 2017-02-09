package com.cs371group2.admin;

import com.google.api.server.spi.config.Authenticator;

/**
 * Created by Brandon on 2017-02-09.
 */
public class AdminRequest extends AuthenticatedRequest{

    protected Authenticator getAuthenticator(){ return null; }
}
