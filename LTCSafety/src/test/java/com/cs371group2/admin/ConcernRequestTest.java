package com.cs371group2.admin;

import com.cs371group2.DatastoreTest;
import com.cs371group2.TestAccountBuilder;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernTest;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import org.junit.Test;

/**
 * Created by Brandon on 2017-02-26.
 */
public class ConcernRequestTest extends DatastoreTest {

    @Test(expected = UnauthorizedException.class)
    public void ConcernRequestTest()
            throws UnauthorizedException, BadRequestException, NotFoundException {

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        String token = builder.build();

        Concern toLoad = new Concern(new ConcernTest().generateConcernData().build());
        new ConcernDao().save(toLoad);

        ConcernRequest.TestHook_MutableConcernRequest testRequest = new ConcernRequest.TestHook_MutableConcernRequest(toLoad.getId(), token);
        ConcernRequest request = testRequest.build();

        new AdminApi().requestConcern(request);
    }

    @Test (expected = BadRequestException.class)
    public void NullTokenTest() throws UnauthorizedException, NotFoundException, BadRequestException {
        ConcernRequest.TestHook_MutableConcernRequest testRequest =
                new ConcernRequest.TestHook_MutableConcernRequest(1, null);
        ConcernRequest request = testRequest.build();
        new AdminApi().requestConcern(request);
    }

    @Test (expected = BadRequestException.class)
    public void EmptyTokenTest()
            throws UnauthorizedException, BadRequestException, NotFoundException {
        ConcernRequest.TestHook_MutableConcernRequest testRequest =
                new ConcernRequest.TestHook_MutableConcernRequest(1, "");
        ConcernRequest request = testRequest.build();
        new AdminApi().requestConcern(request);
    }
}
