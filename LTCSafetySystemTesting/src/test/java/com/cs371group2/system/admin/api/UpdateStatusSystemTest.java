package com.cs371group2.system.admin.api;

import com.appspot.ltc_safety.admin.Admin;
import com.appspot.ltc_safety.admin.model.Concern;
import com.appspot.ltc_safety.admin.model.ConcernRequest;
import com.appspot.ltc_safety.admin.model.UpdateConcernStatusRequest;
import com.appspot.ltc_safety.admin.model.UpdateConcernStatusResponse;
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

public class UpdateStatusSystemTest {

    private static Admin admin;

    @BeforeClass
    public static void setUp() throws Exception {
        admin = new Admin.Builder(new NetHttpTransport(), new GsonFactory(), null).build();
    }

    /**
     * A helper method to create a generic concern to be requested
     *
     * @param facility The facility the concern was submitted for
     * @param test     If the concern was a test concern or not
     * @return The response, that will be used to get the concern ID
     * @throws IOException
     */
    public static SubmitConcernResponse submitConcern(String facility, boolean test) throws IOException {
        Client client = new Client.Builder(new NetHttpTransport(), new GsonFactory(), null).setApplicationName(UpdateStatusSystemTest.class.getName()).build();
        ConcernData data = new ConcernData();
        data.setConcernNature("Test Update Status");
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
     * Test submitting request to change the status, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testExistingStatus() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        ConcernRequest testRequest = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", true);
        testRequest.setConcernId(newConcern.getConcern().getId());
        testRequest.setAccessToken(token);
        Concern response = admin.requestConcern(testRequest).execute();
        UpdateConcernStatusRequest testUpdateStatus = new UpdateConcernStatusRequest();
        testUpdateStatus.setConcernId(response.getId());
        testUpdateStatus.setAccessToken(token);

        testUpdateStatus.setConcernStatus("SEEN");
        UpdateConcernStatusResponse result = admin.updateConcernStatus(testUpdateStatus).execute();
        assertEquals("SEEN", result.getStatus().getType());
    }

    /**
     * Test submitting request nonexistant status, expect to give error 400
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testNonexistingStatus() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        ConcernRequest testRequest = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", true);
        testRequest.setConcernId(newConcern.getConcern().getId());
        testRequest.setAccessToken(token);
        Concern response = admin.requestConcern(testRequest).execute();
        UpdateConcernStatusRequest testUpdateStatus = new UpdateConcernStatusRequest();
        testUpdateStatus.setConcernId(response.getId());
        testUpdateStatus.setAccessToken(token);

        testUpdateStatus.setConcernStatus("NOT");
        try {
            UpdateConcernStatusResponse result = admin.updateConcernStatus(testUpdateStatus).execute();
        } catch (GoogleJsonResponseException e) {;
            assertEquals(e.getStatusCode(), 400);
        }
    }

    /**
     * Test submitting a request to change the status to one the object previous held, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testRevertToPriorStatus() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        ConcernRequest testRequest = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", true);
        testRequest.setConcernId(newConcern.getConcern().getId());
        testRequest.setAccessToken(token);
        Concern response = admin.requestConcern(testRequest).execute();
        UpdateConcernStatusRequest testUpdateStatus = new UpdateConcernStatusRequest();
        testUpdateStatus.setConcernId(response.getId());
        testUpdateStatus.setAccessToken(token);

        testUpdateStatus.setConcernStatus("SEEN");
        UpdateConcernStatusResponse result = admin.updateConcernStatus(testUpdateStatus).execute();
        assertEquals("SEEN", result.getStatus().getType());
        testUpdateStatus.setConcernStatus("PENDING");
        result = admin.updateConcernStatus(testUpdateStatus).execute();
        assertEquals("PENDING", result.getStatus().getType());
    }

    /**
     * Test submitting a request to change the status to the one the object currently holds, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testSetToSameStatus() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        System.out.println(token);
        ConcernRequest testRequest = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", true);
        testRequest.setConcernId(newConcern.getConcern().getId());
        testRequest.setAccessToken(token);
        Concern response = admin.requestConcern(testRequest).execute();
        UpdateConcernStatusRequest testUpdateStatus = new UpdateConcernStatusRequest();
        testUpdateStatus.setConcernId(response.getId());
        testUpdateStatus.setAccessToken(token);

        testUpdateStatus.setConcernStatus("SEEN");
        UpdateConcernStatusResponse result = admin.updateConcernStatus(testUpdateStatus).execute();
        assertEquals("SEEN", result.getStatus().getType());
        testUpdateStatus.setConcernStatus("SEEN");
        result = admin.updateConcernStatus(testUpdateStatus).execute();
        assertEquals("SEEN", result.getStatus().getType());
    }

    /**
     * Test submitting a request to change the status to the one the object currently holds, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testArchive() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        System.out.println(token);
        ConcernRequest testRequest = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", true);
        testRequest.setConcernId(newConcern.getConcern().getId());
        testRequest.setAccessToken(token);
        Concern response = admin.requestConcern(testRequest).execute();
        newConcern.getConcern().setArchived(true);
        admin.updateArchiveStatus(testRequest).execute();
        testRequest.setConcernId(newConcern.getConcern().getId());
        testRequest.setAccessToken(token);
        response = admin.requestConcern(testRequest).execute();

        assertEquals(true, response.getArchived());
    }

    /**
     * Test that a concerns previous statuses are recorded, expect to succeed
     */
    @Test
    @Category(AdminAPISystemTests.class)
    public void testStatusList() throws IOException {
        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        String token = builder.build();
        ConcernRequest testRequest = new ConcernRequest();
        SubmitConcernResponse newConcern = submitConcern("OTHER_FACILITY", true);
        testRequest.setConcernId(newConcern.getConcern().getId());
        testRequest.setAccessToken(token);
        Concern response = admin.requestConcern(testRequest).execute();
        UpdateConcernStatusRequest testUpdateStatus = new UpdateConcernStatusRequest();
        testUpdateStatus.setConcernId(response.getId());
        testUpdateStatus.setAccessToken(token);

        testUpdateStatus.setConcernStatus("SEEN");
        UpdateConcernStatusResponse result = admin.updateConcernStatus(testUpdateStatus).execute();
        testUpdateStatus.setConcernStatus("RESPONDING24");
        result = admin.updateConcernStatus(testUpdateStatus).execute();
        testRequest.setConcernId(newConcern.getConcern().getId());
        testRequest.setAccessToken(token);
        response = admin.requestConcern(testRequest).execute();
        assertEquals("PENDING", response.getStatuses().get(0).getType());
        assertEquals("SEEN", response.getStatuses().get(1).getType());
        assertEquals("RESPONDING24", response.getStatuses().get(2).getType());

    }
}