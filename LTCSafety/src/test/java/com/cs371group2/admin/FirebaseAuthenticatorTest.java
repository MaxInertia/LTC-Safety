package com.cs371group2.admin;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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
    @Test (expected = AssertionError.class)
    public void NullTokenTest(){
        Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        set.add(new AdminPermissionVerifier());
        Authenticator auth = new FirebaseAuthenticator(set);
        auth.authenticate(null);
    }

    /**
     * Tests authenticate() with an empty string.
     */
    @Test (expected = AssertionError.class)
    public void EmptyTokenTest(){
        Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        AdminPermissionVerifier verifier = new AdminPermissionVerifier();
        set.add(verifier);
        String token = "";
        Authenticator auth = new FirebaseAuthenticator(set);
        auth.authenticate(token);
    }

    @Test (expected = AssertionError.class)
    public void NullSetTest(){
        Authenticator auth = new FirebaseAuthenticator(null);
    }

    @Test (expected = AssertionError.class)
    public void EmptySetTest(){
        Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        Authenticator auth = new FirebaseAuthenticator(set);
    }

    @Test
    public void GetPermissionsTest(){
        Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        AdminPermissionVerifier verifier = new AdminPermissionVerifier();
        set.add(verifier);
        Authenticator auth = new FirebaseAuthenticator(set);
        set = auth.getPermissionVerifiers();

        assertTrue(set.contains(verifier));
    }
}
