package com.cs371group2.admin;

import com.cs371group2.DatastoreTest;
import com.cs371group2.TestAccountBuilder;
import com.cs371group2.account.AccountPermissions;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.googlecode.objectify.util.DatastoreIntrospector;
import org.junit.Test;

/**
 * Created by Brandon on 2017-02-11.
 */
public class ConcernListRequestTest extends DatastoreTest {

    @Test
    public void ConcernRequestTest() throws UnauthorizedException, BadRequestException {
        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        ConcernListRequest.TestHook_MutableConcernListRequest testRequest =
                new ConcernListRequest.TestHook_MutableConcernListRequest(1,0, account.build());
        ConcernListRequest request = testRequest.build();
        new AdminApi().requestConcernList(request);
    }

    @Test (expected = BadRequestException.class)
    public void NullTokenTest() throws UnauthorizedException, BadRequestException {
        ConcernListRequest.TestHook_MutableConcernListRequest testRequest =
                new ConcernListRequest.TestHook_MutableConcernListRequest(1,0, null);
        ConcernListRequest request = testRequest.build();
        new AdminApi().requestConcernList(request);
    }

    @Test (expected = BadRequestException.class)
    public void EmptyTokenTest() throws UnauthorizedException, BadRequestException {
        ConcernListRequest.TestHook_MutableConcernListRequest testRequest =
                new ConcernListRequest.TestHook_MutableConcernListRequest(1,0, "");
        ConcernListRequest request = testRequest.build();
        new AdminApi().requestConcernList(request);
    }

    @Test (expected = BadRequestException.class)
    public void InvalidOffsetTest() throws UnauthorizedException, BadRequestException {
        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        ConcernListRequest.TestHook_MutableConcernListRequest testRequest =
                new ConcernListRequest.TestHook_MutableConcernListRequest(-1,0, account.build());
        ConcernListRequest request = testRequest.build();
        new AdminApi().requestConcernList(request);
    }

    @Test (expected = BadRequestException.class)
    public void InvalidLimitTest() throws UnauthorizedException, BadRequestException {
        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        ConcernListRequest.TestHook_MutableConcernListRequest testRequest =
                new ConcernListRequest.TestHook_MutableConcernListRequest(1,-1, account.build());
        ConcernListRequest request = testRequest.build();
        new AdminApi().requestConcernList(request);
    }

    @Test (expected = BadRequestException.class)
    public void ZeroLimitTest() throws UnauthorizedException, BadRequestException {
        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);


        ConcernListRequest.TestHook_MutableConcernListRequest testRequest =
                new ConcernListRequest.TestHook_MutableConcernListRequest(0,0, account.build());
        ConcernListRequest request = testRequest.build();
        new AdminApi().requestConcernList(request);
    }
}
