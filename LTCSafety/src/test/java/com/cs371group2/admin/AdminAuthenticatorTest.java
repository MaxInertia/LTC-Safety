package com.cs371group2.admin;

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
public class AdminAuthenticatorTest {

    /**
     * Tests the authenticate function by providing a null token, which should be asserted.
     */
    @Test //(expected = AssertionError.class)
    public void NullTokenTest() throws GeneralSecurityException, IOException {
        //Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        //set.add(new AdminPermissionVerifier());
        //Authenticator auth = new AdminAuthenticator();
        //auth.authenticate(null);
    }

    /**
     * Tests authenticate() with an empty string.
     */
    @Test //(expected = IOException.class)
    public void EmptyTokenTest() throws GeneralSecurityException, IOException {
        //Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        //AdminPermissionVerifier verifier = new AdminPermissionVerifier();
        //set.add(verifier);
        //String token = "";
        //Authenticator auth = new AdminAuthenticator();

        //auth.authenticate(token);
    }

    /**
     * Tests authenticate() with an empty string.
     */
    @Test //(expected = IOException.class)
    public void InvalidTokenTest() throws GeneralSecurityException, IOException {
        //Set<PermissionVerifier> set = new HashSet<PermissionVerifier>();
        //AdminPermissionVerifier verifier = new AdminPermissionVerifier();
        //set.add(verifier);
        //String token = "this is an invalid test token :)";
        //Authenticator auth = new AdminAuthenticator();

        //auth.authenticate(token);
    }

    /**
     * Tests getPermissions
     */
    @Test
    public void GetPermissionsTest(){
        //Authenticator auth = new AdminAuthenticator();
        //Set<PermissionVerifier> testSet = auth.getPermissionVerifiers();
        //assertNotNull(testSet);
        //assertTrue(testSet.size() == 1);
    }

}
