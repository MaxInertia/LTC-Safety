package com.cs371group2.admin;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test cases for the base PermissionVerifier to test that it accepts all accounts. The base
 * permission verifier must accept all accounts regardless of permissions to allow for fetching of
 * account information.
 *
 * Created on 2017-03-08.
 */
public class PermissionVerifierTest {

    /**
     * Test that the base permission verifier class verifies any account
     * regardless of the access token.
     */
    @Test
    public void nullPermissions() throws Exception {
        assertTrue(new PermissionVerifier().hasPermission(null, null));
    }
}
