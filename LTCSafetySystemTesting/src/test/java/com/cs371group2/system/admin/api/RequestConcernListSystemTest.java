package com.cs371group2.system.admin.api;

import com.appspot.ltc_safety.admin.Admin;
import com.appspot.ltc_safety.admin.model.ConcernCollection;
import com.appspot.ltc_safety.admin.model.ConcernListRequest;
import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.Reporter;
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
import static org.junit.Assert.assertNull;

public class RequestConcernListSystemTest {

    private static Admin admin;

    @BeforeClass
    public static void setUp() throws Exception {
        admin = new Admin.Builder(new NetHttpTransport(), new GsonFactory(), null).build();
    }

    public static void submitConcern(String facility) throws IOException {
        Client client = new Client.Builder(new NetHttpTransport(), new GsonFactory(), null).setApplicationName(RequestConcernListSystemTest.class.getName()).build();
        ConcernData data = new ConcernData();
        data.setConcernNature("Water main break");
        data.setActionsTaken("None");
        Reporter fake = new Reporter();
        fake.setName("Fake");
        fake.setEmail("Fake@fake.com");
        fake.setPhoneNumber("" + Math.floor(Math.random() * 10000000));
        data.setReporter(fake);
        Location test = new Location();
        test.setFacilityName(facility);
        int randomID = (int) Math.floor(Math.random() * 1000000);
        test.setRoomName("Fake" + randomID);
        System.out.println(randomID);
        data.setLocation(test);
        client.submitTestConcern(true, data).execute();
    }

    /**
     * Test submitting an null token, expect to receive http 400 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNoToken() throws IOException {

        try {
            admin.requestConcernList(null).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 400);
        }
    }

    /**
     * Test submitting an expired token, expect to receive http 401 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testExpiredToken() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjI2NDE1ZTE2ODIzNDNiMDBmZmUwYjE1ODBiMThhMDI0MzNlMjJhMDUifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbHRjLXNhZmV0eSIsImF1ZCI6Imx0Yy1zYWZldHkiLCJhdXRoX3RpbWUiOjE0ODg1MjkxMzYsInVzZXJfaWQiOiJ4NWd2TUFZR2ZOY0t2NzRWeUhGZ3I0WXRjZ2UyIiwic3ViIjoieDVndk1BWUdmTmNLdjc0VnlIRmdyNFl0Y2dlMiIsImlhdCI6MTQ4ODU3NjQ5MSwiZXhwIjoxNDg4NTgwMDkxLCJlbWFpbCI6InBlcnNvbmFsLmFsbGFua2VyckBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsicGVyc29uYWwuYWxsYW5rZXJyQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.OveHPLDqIgX8Moau2dk6_g5k_FL4TSQ8z9_41N6co1Rj6iJvGs_lT9q2J-hgaR2cMyVnaqLcgSiquk2V6RcRdbwtHa1KryeQvdyG3tPCs-r3JrHf19afEyHyC289k4xb0f6bNb7c0p5yqnSR7JTdzWeyPoQ3LqiPWzXtgtwoN7LL6t-T1ME_OB3VW-2dncbBOTMFwMzGc2ejK-PomdxsgCaNBm7oeNNBxFGnZQ-iXSq2umMvpb2CboqHyXWU57-e8TCiI84AwxHG77jsH5ztnj0ZixJ3k3dDzUusU7w5PJ5CB7oR0kJwMugBYrQnJo397sYBoaKsXymwp5GBhtYzTg\n" +
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
        test1.setLimit(5);
        test1.setOffset(5);
        try {
            admin.requestConcernList(test1).execute();
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
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("Malformed Token");
        test1.setLimit(5);
        test1.setOffset(5);
        try {
            admin.requestConcernList(test1).execute();
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
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken("changedvaluessothatthetokenisntauthenticatedOiJIUzI1NiIsnR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
        test1.setLimit(5);
        test1.setOffset(5);
        try {
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getStatusCode(), 401);
        }
    }

    /**
     * Test submitting an valid token, expect to suceed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testValidToken() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("random");
        String token = builder.build();
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken(token);
        test1.setLimit(5);
        test1.setOffset(5);
        ConcernCollection response = admin.requestConcernList(test1).execute();
        //TODO
    }

    /**
     * Test submitting a request with a negative limit, expect to receive http 400 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNegativeLimit() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("random");
        String token = builder.build();
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken(token);
        test1.setLimit(-5);
        test1.setOffset(5);
        try {
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {

            assertEquals(e.getDetails().getMessage(), "Unable to request concern list due to an invalid requested limit.");
        }
    }

    /**
     * Test submitting a request with a negative offset, expect to receive http 400 in response
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNegativeOffset() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("random");
        String token = builder.build();
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken(token);
        test1.setLimit(5);
        test1.setOffset(-5);
        try {
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "Unable to request concern list due to an invalid requested offset.");
        }
    }

    /**
     * Test submitting a request with a limit equal to zero, expect give error 400
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testZeroLimit() throws IOException {
        ConcernListRequest test1 = new ConcernListRequest();
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("random");
        String token = builder.build();
        test1.setAccessToken(token);
        test1.setLimit(0);
        test1.setOffset(5);
        try {
            admin.requestConcernList(test1).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "Unable to request concern list due to an invalid requested limit.");
        }
    }

    /**
     * Test submitting a request with a massive limit, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testMassiveLimit() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        for (int i = 0; i < 5; i++) {
            submitConcern("OTHER_FACILITY");
        }
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken(token);
        test1.setLimit(50000000);
        test1.setOffset(2);
        ConcernCollection result = admin.requestConcernList(test1).execute();
        test1.setLimit(2);
        test1.setOffset(3);
        ConcernCollection resultB = admin.requestConcernList(test1).execute();
        assertEquals(result.getItems().get(1).getId(), resultB.getItems().get(0).getId());
        assertEquals(result.getItems().get(2).getId(), resultB.getItems().get(1).getId());
    }

    /**
     * Test submitting a request with a massive offset, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testMassiveOffset() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("Other");
        String token = builder.build();
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken(token);
        test1.setLimit(5);
        test1.setOffset(50000000);
        ConcernCollection result = admin.requestConcernList(test1).execute();
        assertNull(result.getItems());
    }

    /**
     * Test submitting a request with a normal offset and limit, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNormalPaging() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        for (int i = 0; i < 5; i++) {
            submitConcern("OTHER_FACILITY");
        }
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken(token);
        test1.setLimit(5);
        test1.setOffset(0);
        ConcernCollection result = admin.requestConcernList(test1).execute();
        test1.setLimit(2);
        test1.setOffset(3);
        ConcernCollection resultB = admin.requestConcernList(test1).execute();
        assertEquals(result.getItems().get(3).getId(), resultB.getItems().get(0).getId());
        assertEquals(result.getItems().get(4).getId(), resultB.getItems().get(1).getId());
    }


    /**
     * Test requesting a concern list while in a non other facility.
     * MANUAL TEST
     * TODO: Cannot finish tests until Acount supports multiple facilities
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testFacilityFiltering() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("Preston Extendicare");
        String token = builder.build();
        for (int i = 0; i < 5; i++) {
            submitConcern("Preston Extendicare");
        }
        ConcernListRequest test1 = new ConcernListRequest();
        test1.setAccessToken(token);
        test1.setLimit(5);
        test1.setOffset(0);
        ConcernCollection result = admin.requestConcernList(test1).execute();
        //System.out.println(result.getItems().get(0).getData().getLocation().getFacilityName() + " RoomID: " + result.getItems().get(0).getData().getLocation().getRoomName());
    }
}