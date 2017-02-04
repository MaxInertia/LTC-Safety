package com.cs371group2.admin;

import com.google.api.server.spi.auth.EspAuthenticator;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;

/**
 * Authenticator for use with firebase functionality
 *
 * Created on 2017-02-04.
 */
public class FirebaseAuthenticator implements Authenticator {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(FirebaseTokenVerifier.class.getName());

    /**
     * Takes an http servlet request, authenticates it and ensures that the user's access level is sufficient
     *
     * @param request The HttpServletRequest to authenticate
     * @return The user account if user is authorized, null otherwise
     */
    @Override
    public User authenticate(HttpServletRequest request) {

        if(request == null){
            return null;
        }

        EspAuthenticator authenticator = new EspAuthenticator();
        User user = authenticator.authenticate(request);

        if(user != null){
            AccountDao dao = new AccountDao();
            Account userAccount = dao.load(user.getId());

            if(userAccount.getAccessType() == AccountType.ADMIN || userAccount.getAccessType() == AccountType.USER)
                return user;
        }

        return null;
    }
}
