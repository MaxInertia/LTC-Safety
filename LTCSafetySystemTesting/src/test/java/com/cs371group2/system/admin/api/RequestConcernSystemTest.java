package com.cs371group2.system.admin.api;

import com.appspot.ltc_safety.admin.Admin;
import com.appspot.ltc_safety.admin.model.Concern;
import com.appspot.ltc_safety.admin.model.ConcernRequest;
import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.Reporter;
import com.appspot.ltc_safety.client.model.SubmitConcernResponse;
import com.cs371group2.system.AccountPermissions;
import com.cs371group2.system.AdminAPISystemTests;
import com.cs371group2.system.TestAccountBuilder;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RequestConcernSystemTest {

    private static Admin admin;

    @BeforeClass
    public static void setUp() throws Exception {
        admin = new Admin.Builder(new NetHttpTransport(), new GsonFactory(), null).build();
    }

    public static SubmitConcernResponse submitConcern(String facility, boolean test) throws IOException {
        Client client = new Client.Builder(new NetHttpTransport(), new GsonFactory(), null).setApplicationName(RequestConcernSystemTest.class.getName()).build();
        ConcernData data = new ConcernData();
        data.setConcernNature("Water main break");
        data.setActionsTaken("None");
        Reporter fake = new Reporter();
        fake.setName("Fake");
        fake.setEmail("Fake@fake.com");
        fake.setPhoneNumber("" + Math.floor(Math.random() * 10000000));
        data.setReporter(fake);
        Location location = new Location();
        location.setFacilityName(facility);
        int randomID = (int) Math.floor(Math.random() * 1000000);
        location.setRoomName("Fake" + randomID);
        System.out.println(randomID);
        data.setLocation(location);
        return client.submitTestConcern(test, data).execute();
    }

    /**
     * Test submitting an null token, expect to receive http 400 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNoToken() throws IOException {

        try {
            admin.requestConcern(null).execute();
        } catch (GoogleJsonResponseException e) {
            System.out.println(e.getDetails().getMessage());
            assertEquals(e.getDetails().getMessage(), "Unable to access concern due to non-existent credentials.");
        }
    }

    /**
     * Test submitting an expired token, expect to receive http 401 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testExpiredToken() throws IOException {
        ConcernRequest test1 = new ConcernRequest();
        test1.setAccessToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjI2NDE1ZTE2ODIzNDNiMDBmZmUwYjE1ODBiMThhMDI0MzNlMjJhMDUifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbHRjLXNhZmV0eSIsImF1ZCI6Imx0Yy1zYWZldHkiLCJhdXRoX3RpbWUiOjE0ODg1MjkxMzYsInVzZXJfaWQiOiJ4NWd2TUFZR2ZOY0t2NzRWeUhGZ3I0WXRjZ2UyIiwic3ViIjoieDVndk1BWUdmTmNLdjc0VnlIRmdyNFl0Y2dlMiIsImlhdCI6MTQ4ODU3NjQ5MSwiZXhwIjoxNDg4NTgwMDkxLCJlbWFpbCI6InBlcnNvbmFsLmFsbGFua2VyckBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsicGVyc29uYWwuYWxsYW5rZXJyQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.OveHPLDqIgX8Moau2dk6_g5k_FL4TSQ8z9_41N6co1Rj6iJvGs_lT9q2J-hgaR2cMyVnaqLcgSiquk2V6RcRdbwtHa1KryeQvdyG3tPCs-r3JrHf19afEyHyC289k4xb0f6bNb7c0p5yqnSR7JTdzWeyPoQ3LqiPWzXtgtwoN7LL6t-T1ME_OB3VW-2dncbBOTMFwMzGc2ejK-PomdxsgCaNBm7oeNNBxFGnZQ-iXSq2umMvpb2CboqHyXWU57-e8TCiI84AwxHG77jsH5ztnj0ZixJ3k3dDzUusU7w5PJ5CB7oR0kJwMugBYrQnJo397sYBoaKsXymwp5GBhtYzTg\n" +
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
        try {
            admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a malformed token, expect to receive http 400 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testMalformedToken() throws IOException {
        ConcernRequest test1 = new ConcernRequest();
        test1.setAccessToken("Malformed Token");
        try {
            admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting an invalid token, expect to receive http 400 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testInvalidToken() throws IOException {
        ConcernRequest test1 = new ConcernRequest();
        test1.setAccessToken("changedvaluessothatthetokenisntauthenticatedOiJIUzI1NiIsnR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
        try {
            admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting request with an valid token, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testValidToken() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        ConcernRequest test1 = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", true);
        test1.setConcernId(newConcern.getConcern().getId());
        test1.setAccessToken(token);
        Concern response = admin.requestConcern(test1).execute();
        assertEquals(newConcern.getConcern().getData(), response.getData());
    }

    /**
     * Test submitting a request with a nonexistant id, expect to receive http 404 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNonexistentID() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        ConcernRequest test1 = new ConcernRequest();
        test1.setAccessToken(token);
        test1.setConcernId((long) 4595);
        try {
            admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "Failed to find a concern with ID: 4595");
        }
    }

    /**
     * Test submitting a request with a null concern ID, expect to receive http 400 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNullId() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        ConcernRequest test1 = new ConcernRequest();
        test1.setAccessToken(token);
        try {
            admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "java.lang.IllegalArgumentException: id cannot be zero");
        }
    }

    /**
     * Test submitting a request for a concern with a different facility, expect error 404 in response
     * TODO: Does not work because Accounts only have one facility
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testDifferentFacility() throws IOException {
        ConcernRequest test1 = new ConcernRequest();
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("Preston Extendicare");
        String token = builder.build();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", true);
        test1.setAccessToken(token);
        try {
            admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "java.lang.IllegalArgumentException: id cannot be zero");
        }
    }

    /**
     * Test submitting a request with a id for a regular concern using a test account, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNonTestConcern() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        ConcernRequest test1 = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", false);
        test1.setAccessToken(token);
        test1.setConcernId(newConcern.getConcern().getId());
        try{
        Concern response = admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a request for a concern that has the same (non other) facility as the account, expect to succeed
     * TODO
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNonOtherTester() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("Preston Extendicare");
        String token = builder.build();
        ConcernRequest test1 = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("Preston Extendicare", false);
        test1.setAccessToken(token);
        test1.setConcernId(newConcern.getConcern().getId());
        try{
            Concern response = admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting a request for a concern that has a different (non other) facility as the account, expect to succeed
     * TODO
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNonOtherTesterConcern() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("Preston Extendicare");
        String token = builder.build();
        ConcernRequest test1 = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("shksv", false);
        test1.setAccessToken(token);
        test1.setConcernId(newConcern.getConcern().getId());
        try{
            Concern response = admin.requestConcern(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

}