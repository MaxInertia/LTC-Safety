package com.cs371group2.admin;

import com.google.api.server.spi.response.UnauthorizedException;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the FirebaseAuthenticator class by providing invalid inputs to its functions, as well as
 * valid inputs to ensure proper functionality.
 *
 * Created on 2017-02-10.
 */
public class FirebaseAuthenticatorTest {

    /**
     * Tests the authenticate function by providing a null token, which should be asserted.
     */
    @Test (expected = UnauthorizedException.class)
    public void NullTokenTest() throws UnauthorizedException {
        Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        set.add(new AdminPermissionVerifier());
        Authenticator auth = new FirebaseAuthenticator();
        auth.authenticate(null);
    }

    /**
     * Tests authenticate() with an empty string.
     */
    @Test (expected = UnauthorizedException.class)
    public void EmptyTokenTest() throws UnauthorizedException {
        Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        AdminPermissionVerifier verifier = new AdminPermissionVerifier();
        set.add(verifier);
        String token = "";
        Authenticator auth = new FirebaseAuthenticator();

        auth.authenticate(token);
    }

    /**
     * Tests authenticate() with an empty string.
     */
    @Test (expected = UnauthorizedException.class)
    public void InvalidTokenTest() throws UnauthorizedException {
        Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        AdminPermissionVerifier verifier = new AdminPermissionVerifier();
        set.add(verifier);
        String token = "this is an invalid test token :)";
        Authenticator auth = new FirebaseAuthenticator();

        auth.authenticate(token);
    }

}
