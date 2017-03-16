package com.cs371group2.system.admin.api;

import com.appspot.ltc_safety.admin.Admin;
import com.appspot.ltc_safety.admin.model.ConcernCollection;
import com.appspot.ltc_safety.admin.model.ConcernListRequest;
import com.cs371group2.system.SystemTests;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AuthenticationSystemTest {

    private static Admin admin;

    @BeforeClass
    public static void setUp() throws Exception {
        admin = new Admin.Builder(new NetHttpTransport(), new GsonFactory(), null).build();
    }



    /**
     * Test submitting an null token, expect to receive http 401 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testNoToken() throws IOException {

        try{
        admin.requestConcernList(null).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting an expired token, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testExpiredToken() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjI2NDE1ZTE2ODIzNDNiMDBmZmUwYjE1ODBiMThhMDI0MzNlMjJhMDUifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbHRjLXNhZmV0eSIsImF1ZCI6Imx0Yy1zYWZldHkiLCJhdXRoX3RpbWUiOjE0ODg1MjkxMzYsInVzZXJfaWQiOiJ4NWd2TUFZR2ZOY0t2NzRWeUhGZ3I0WXRjZ2UyIiwic3ViIjoieDVndk1BWUdmTmNLdjc0VnlIRmdyNFl0Y2dlMiIsImlhdCI6MTQ4ODU3NjQ5MSwiZXhwIjoxNDg4NTgwMDkxLCJlbWFpbCI6InBlcnNvbmFsLmFsbGFua2VyckBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsicGVyc29uYWwuYWxsYW5rZXJyQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.OveHPLDqIgX8Moau2dk6_g5k_FL4TSQ8z9_41N6co1Rj6iJvGs_lT9q2J-hgaR2cMyVnaqLcgSiquk2V6RcRdbwtHa1KryeQvdyG3tPCs-r3JrHf19afEyHyC289k4xb0f6bNb7c0p5yqnSR7JTdzWeyPoQ3LqiPWzXtgtwoN7LL6t-T1ME_OB3VW-2dncbBOTMFwMzGc2ejK-PomdxsgCaNBm7oeNNBxFGnZQ-iXSq2umMvpb2CboqHyXWU57-e8TCiI84AwxHG77jsH5ztnj0ZixJ3k3dDzUusU7w5PJ5CB7oR0kJwMugBYrQnJo397sYBoaKsXymwp5GBhtYzTg\n" +
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
        test1.setLimit(5);
        test1.setOffset(5);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a malformed token, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testMalformedToken() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("Malformed Token");
        test1.setLimit(5);
        test1.setOffset(5);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting an invalid token, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testInvalidToken() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("changedvaluessothatthetokenisntauthenticatedOiJIUzI1NiIsnR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
        test1.setLimit(5);
        test1.setOffset(5);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting an valid token, expect to suceed
     */
    @Test
    @Category(SystemTests.class)
    public void testValidToken() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("changedvaluessothatthetokenisntauthenticatedOiJIUzI1NiIsnR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
        test1.setLimit(5);
        test1.setOffset(5);
        ConcernCollection response = admin.requestConcernList(test1).execute();
    }

    /**
     * Test submitting a request with a negative limit, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testNegativeLimit() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("XXXXXXXXXXXX");
        test1.setLimit(-5);
        test1.setOffset(5);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a request with a negative offset, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testNegativeOffset() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("XXXXXXXXXXXX");
        test1.setLimit(5);
        test1.setOffset(-5);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a request with a limit equal to zero, expect to succeed
     */
    @Test
    @Category(SystemTests.class)
    public void testZeroLimit() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("XXXXXXXXXXXX");
        test1.setLimit(0);
        test1.setOffset(5);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a request with a massive limit, expect to succeed
     */
    @Test
    @Category(SystemTests.class)
    public void testMassiveLimit() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("XXXXXXXXXXXX");
        test1.setLimit(50000000);
        test1.setOffset(5);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a request with a massive offset, expect to succeed
     */
    @Test
    @Category(SystemTests.class)
    public void testMassiveOffset() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("XXXXXXXXXXXX");
        test1.setLimit(5);
        test1.setOffset(50000000);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a request with a normal offset and limit, expect to succeed
     */
    @Test
    @Category(SystemTests.class)
    public void testNormalPaging() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("XXXXXXXXXXXX");
        test1.setLimit(5);
        test1.setOffset(0);
        try{
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }
}