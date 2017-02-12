package com.cs371group2.admin;

import org.junit.Test;

/**
 * Created by Brandon on 2017-02-11.
 */
public class ConcernRequestTest {

    /**
    final String TEST_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjU2NmNkN2U3MDJhOGExYmU2ZGVjMjRmZGJmYjIwOTU4ODBlNmFkNWYifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbHRjLXNhZmV0eSIsImF1ZCI6Imx0Yy1zYWZldHkiLCJhdXRoX3RpbWUiOjE0ODY4NDc0ODEsInVzZXJfaWQiOiI1b3ozSFBQblp1VXpWNGhjd1hxdGdHNnRqYzAzIiwic3ViIjoiNW96M0hQUG5adVV6VjRoY3dYcXRnRzZ0amMwMyIsImlhdCI6MTQ4Njg0NzQ4MSwiZXhwIjoxNDg2ODUxMDgxLCJlbWFpbCI6InRlc3RAdGVzdC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsidGVzdEB0ZXN0LmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.jb03QYVYXwfHD6_v4YoS8ZhHe7gI29Zj4pvq5BoHEWKCJGCgjI_3WAxdOhE5L_y7BLyTUTB3j4SCTZAJYU08T8ykRFiWqtUZKynhNmw5S2Ec4QJ_FXZlT44oIj0-ITdphOfw1_uUeIOImWg70Et-hjKMNmZj5yEptHgrJ9THwZMiyzJ7lZvXOKd4oqP5V2AnBO5iAj4rGgp-eqVqsK7fTKOwhr18MPxTR9RbJwFt-bVzEn6L-Oln1Dccx2l9gfrGrYuRaDxaG3ob8o0gYBi3E9Q_zThE-potbhN-TNr8m7LLGz0aEkVmG6HKMTNQ3H-5mI8OI3dDyjYfH5hS7sLscg";

    @Test
    public void ConcernRequestTest(){
        ConcernRequest request = new ConcernRequest(0,1, TEST_TOKEN);
        assert (request != null);
    }

    @Test (expected = AssertionError.class)
    public void NullTokenTest(){
        ConcernRequest request = new ConcernRequest(0,1, null);
        assert (request != null);
    }

    @Test (expected = AssertionError.class)
    public void EmptyTokenTest(){
        ConcernRequest request = new ConcernRequest(0,1, "");
        assert (request != null);
    }

    @Test (expected = AssertionError.class)
    public void InvalidOffsetTest() {
        ConcernRequest request = new ConcernRequest(-1,1, TEST_TOKEN);
    }

    @Test (expected = AssertionError.class)
    public void InvalidLimitTest() {
        ConcernRequest request = new ConcernRequest(0,-1, TEST_TOKEN);
    }

    @Test (expected = AssertionError.class)
    public void ZeroLimitTest() {
        ConcernRequest request = new ConcernRequest(0,0, TEST_TOKEN);
    }
    */
}
