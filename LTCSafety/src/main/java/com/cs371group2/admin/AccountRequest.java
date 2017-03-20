package com.cs371group2.admin;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is used for authenticated requests that don't require any permissions. AccountRequest
 * can be used for requests that fetch account information because a valid token is sufficient
 * regardless of permissions.
 *
 * History property: Instances of this class are immutable from the time they are created.
 *
 * Invariance Properties: This class assumes that no administrative privileges are required for the request
 * to be fulfilled.
 *
 * Created on 2017-03-07.
 */
public class AccountRequest extends AuthenticatedRequest {

    /**
     * Returns a reference to an authenticator
     *
     * @return An authenticator
     */
    protected Authenticator getAuthenticator() {
        Authenticator authenticator = super.getAuthenticator();

        List<PermissionVerifier> verifiers = new LinkedList<PermissionVerifier>();
        verifiers.add(new PermissionVerifier());
        authenticator.setPermissionVerifiers(verifiers);

        return authenticator;
    }

    /**
     * A test hook providing a constructor for the account request class to avoid exposing private
     * fields.
     */
    public static class TestHook_AccountRequest {

        /**
         * An immutable AccountRequest for use in testing
         */
        private AccountRequest immutable = new AccountRequest();


        public TestHook_AccountRequest(String accessToken) {
            immutable.accessToken = accessToken;
        }

        /**
         * Build the request that can be used for testing.
         *
         * @return An immutable request that can be used for testing purposes.
         */
        public AccountRequest build() {
            return immutable;
        }
    }
}
