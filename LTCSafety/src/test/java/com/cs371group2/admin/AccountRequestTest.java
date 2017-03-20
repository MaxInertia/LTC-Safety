package com.cs371group2.admin;

import com.cs371group2.TestAccountBuilder;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.admin.AccountRequest.TestHook_AccountRequest;
import com.google.api.server.spi.response.UnauthorizedException;
import org.junit.Test;

/**
 * Created by allankerr on 2017-03-08.
 */
public class AccountRequestTest {

    /**
     * Test that an account with admin permissions and an verified email is authenticated.
     */
    @Test
    public void testVerifiedEmailAdmin() throws UnauthorizedException {

        TestAccountBuilder builder = new TestAccountBuilder("accountid", "email@gmail.com", AccountPermissions.ADMIN, true);
        String testToken = builder.build();
        sendRequest(testToken);
    }

    /**
     * Test that an account with unverified permissions and an verified email is authenticated.
     */
    @Test
    public void testVerifiedEmailUnverified() throws UnauthorizedException {

        TestAccountBuilder builder = new TestAccountBuilder("accountid", "email@gmail.com", AccountPermissions.UNVERIFIED, true);
        String testToken = builder.build();
        sendRequest(testToken);
    }

    /**
     * Test that an account with unverified permissions and an unverified email is authenticated.
     */
    @Test
    public void testUnverifiedEmailUnverified() throws UnauthorizedException {

        TestAccountBuilder builder = new TestAccountBuilder("accountid", "email@gmail.com", AccountPermissions.UNVERIFIED, false);
        String testToken = builder.build();
        sendRequest(testToken);
    }

    /**
     * Test that an account with admin permissions and an unverified email is authenticated.
     */
    @Test
    public void testAdminEmailUnverified() throws UnauthorizedException {

        TestAccountBuilder builder = new TestAccountBuilder("accountid", "email@gmail.com", AccountPermissions.ADMIN, false);
        String testToken = builder.build();
        sendRequest(testToken);
    }

    /**
     * Authenticate the request with the a test token to ensure that its permissions are accepted.
     * @param testToken The token with the account id and permissions.
     * @throws UnauthorizedException Thrown if the authentication fails
     */
    private void sendRequest(String testToken) throws UnauthorizedException {
        AccountRequest request = new TestHook_AccountRequest(testToken).build();
        new AdminApi().requestAccount(request);
    }
}
