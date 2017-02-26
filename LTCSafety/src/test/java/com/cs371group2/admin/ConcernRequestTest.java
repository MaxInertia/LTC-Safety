package com.cs371group2.admin;

import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernTest;
import com.google.api.server.spi.response.UnauthorizedException;
import org.junit.Test;

/**
 * Created by Brandon on 2017-02-26.
 */
public class ConcernRequestTest {
    final String TEST_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjU2NmNkN2U3MDJhOGExYmU2ZGVjMjRmZGJmYjIwOTU4ODBlNmFkNWYifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbHRjLXNhZmV0eSIsImF1ZCI6Imx0Yy1zYWZldHkiLCJhdXRoX3RpbWUiOjE0ODY4NDc0ODEsInVzZXJfaWQiOiI1b3ozSFBQblp1VXpWNGhjd1hxdGdHNnRqYzAzIiwic3ViIjoiNW96M0hQUG5adVV6VjRoY3dYcXRnRzZ0amMwMyIsImlhdCI6MTQ4Njg0NzQ4MSwiZXhwIjoxNDg2ODUxMDgxLCJlbWFpbCI6InRlc3RAdGVzdC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsidGVzdEB0ZXN0LmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.jb03QYVYXwfHD6_v4YoS8ZhHe7gI29Zj4pvq5BoHEWKCJGCgjI_3WAxdOhE5L_y7BLyTUTB3j4SCTZAJYU08T8ykRFiWqtUZKynhNmw5S2Ec4QJ_FXZlT44oIj0-ITdphOfw1_uUeIOImWg70Et-hjKMNmZj5yEptHgrJ9THwZMiyzJ7lZvXOKd4oqP5V2AnBO5iAj4rGgp-eqVqsK7fTKOwhr18MPxTR9RbJwFt-bVzEn6L-Oln1Dccx2l9gfrGrYuRaDxaG3ob8o0gYBi3E9Q_zThE-potbhN-TNr8m7LLGz0aEkVmG6HKMTNQ3H-5mI8OI3dDyjYfH5hS7sLscg";

    @Test
    public void ConcernRequestTest() throws UnauthorizedException {
        //Concern toLoad = new Concern(new ConcernTest().generateConcernData().build());
        //new ConcernDao().save(toLoad);
        //ConcernRequest.TestHook_MutableConcernRequest testRequest =
        //        new ConcernRequest.TestHook_MutableConcernRequest(toLoad.getId(), TEST_TOKEN);
        //ConcernRequest request = testRequest.build();
//
        //assert(toLoad == new AdminApi().requestConcern(request));
    }

    @Test (expected = UnauthorizedException.class)
    public void NullTokenTest() throws UnauthorizedException {
        ConcernRequest.TestHook_MutableConcernRequest testRequest =
                new ConcernRequest.TestHook_MutableConcernRequest(1, null);
        ConcernRequest request = testRequest.build();
        new AdminApi().requestConcern(request);
    }

    @Test (expected = UnauthorizedException.class)
    public void EmptyTokenTest() throws UnauthorizedException {
        ConcernRequest.TestHook_MutableConcernRequest testRequest =
                new ConcernRequest.TestHook_MutableConcernRequest(1, "");
        ConcernRequest request = testRequest.build();
        new AdminApi().requestConcern(request);
    }

    @Test (expected = UnauthorizedException.class)
    public void InvalidIdTest() throws UnauthorizedException {
        ConcernRequest.TestHook_MutableConcernRequest testRequest =
                new ConcernRequest.TestHook_MutableConcernRequest(0, null);
        ConcernRequest request = testRequest.build();
        new AdminApi().requestConcern(request);
    }
}
