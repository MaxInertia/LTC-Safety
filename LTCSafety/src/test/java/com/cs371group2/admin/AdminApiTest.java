package com.cs371group2.admin;

import com.cs371group2.account.Account;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernData;
import com.cs371group2.concern.ConcernTest;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.UnauthorizedException;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created on 2017-02-09.
 */
public class AdminApiTest {

    @Test (expected = UnauthorizedException.class)
    public void getConcernsUnauthorizedTest() throws UnauthorizedException, BadRequestException {
        AdminApi api = new AdminApi();
        List<Concern> concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, "x5gvMAYGfNcKv74VyHFgr4Ytcge2").build());
    }

    @Test
    public void getConcernsAuthorizedTest() throws UnauthorizedException, BadRequestException {
/*


        ConcernListRequest request = Mockito.mock(ConcernListRequest.class);
        request.accessToken = "ff";
        Mockito.doReturn(new Account("asdf", AccountPermissions.ADMIN)).when(request).getAuthenticator().authenticate(request.accessToken);

        AdminApi api = new AdminApi();
        List<Concern> concerns = api.requestConcernList(request);
        assertNotNull(concerns);
*/
    }
}
