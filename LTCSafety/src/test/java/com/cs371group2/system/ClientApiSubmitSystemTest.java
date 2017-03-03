package com.cs371group2.system;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.Location;
import com.appspot.ltc_safety.client.model.Reporter;
import com.appspot.ltc_safety.client.model.SubmitConcernResponse;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * These tests are related to the concern class to ensure that concern data is properly validated
 * when submitted. This involves checking that the correct responses are returned when unexpected or
 * invalid data is provided during concern submission.
 * <p>
 * NOTE - These tests were originally part of the client API tests. They were moved to take
 * advantage of the package private variables for testing.
 * <p>
 * Created on 2017-01-22.
 */
public class ClientApiSubmitSystemTest {

    private static Client client;

    @BeforeClass
    public static void setUp() throws Exception {
        client = new Client.Builder(new NetHttpTransport(), new GsonFactory(), null).build();
    }

    /**
     * Creates a default set of concern data to be used in the tests
     *
     * @return The created concern data
     */
    private ConcernData generateConcernData() {
        ConcernData data = new ConcernData();
        data.setConcernNature("Water main break");
        data.setActionsTaken("None");
        Reporter fake = new Reporter();
        fake.setName("Fake");
        fake.setEmail("Fake@fake.com");
        fake.setPhoneNumber("123456789");
        data.setReporter(fake);
        Location test = new Location();
        test.setFacilityName("Test Facility");
        test.setRoomName("Fake");
        data.setLocation(test);
        return data;
    }

    /**
     * Asserts that when the provided concern data is submitted then the specified error code is returned.
     *
     * @param data      The provided concern data
     * @param errorCode The expected error code
     */
    private void testSubmitConcernError(ConcernData data, int errorCode) throws IOException {
        try {
            SubmitConcernResponse response = client.submitConcern(data).execute();

        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getCode(), errorCode);

        }
    }

    /**
     * Test submitting an empty concern, expect to receive http 400 in response
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoInput() throws IOException {

        ConcernData data = new ConcernData();
        testSubmitConcernError(data, 400);

    }

    /**
     * Test submitting a concern without a concern nature, expect to receive http 400 in response
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoConcern() throws IOException {

        ConcernData data = generateConcernData();

        data.setConcernNature(null);
        testSubmitConcernError(data, 400);

    }

    /**
     * Test submitting a concern without a reporter, expect to receive http 400 in response
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoReporter() throws IOException {

        ConcernData data = generateConcernData();

        data.setReporter(null);
        testSubmitConcernError(data, 400);

    }

    /**
     * Test submitting a concern without a location, expect to receive http 400 in response
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoLocation() throws IOException {

        ConcernData data = generateConcernData();

        data.setLocation(null);
        testSubmitConcernError(data, 400);

    }

    /**
     * Test submitting a concern without a reporter name, expect to receive http 400 in response
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoReporterName() throws IOException {

        ConcernData data = generateConcernData();

        data.getReporter().setName(null);
        testSubmitConcernError(data, 400);
    }

    /**
     * Test submitting a concern where the reporter has no email or phone number, expect to receive http 400 in response
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoReporterContact() throws IOException {

        ConcernData data = generateConcernData();

        data.getReporter().setEmail(null);
        data.getReporter().setPhoneNumber(null);
        testSubmitConcernError(data, 400);
    }

    /**
     * Test submitting a concern where the reporter has no email, expect to submission to succeed
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoReporterEmail() throws IOException {

        ConcernData data = generateConcernData();

        data.getReporter().setEmail(null);

        SubmitConcernResponse response = client.submitConcern(data).execute();

        assertEquals(response.getConcern().getData(), data);


    }

    /**
     * Test submitting a concern where the reporter has no phone, expect to submission to succeed
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoReporterPhone() throws IOException {

        ConcernData data = generateConcernData();

        data.getReporter().setPhoneNumber(null);
        SubmitConcernResponse response = client.submitConcern(data).execute();

        assertEquals(response.getConcern().getData(), data);

    }

    /**
     * Test submitting a concern without a facility, expect to receive http 400 in response
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoFacility() throws IOException {

        ConcernData data = generateConcernData();

        data.getLocation().setFacilityName(null);
        testSubmitConcernError(data, 400);
    }

    /**
     * Test submitting a concern without a room name, expect to submission to succeed
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoRoom() throws IOException {

        ConcernData data = generateConcernData();

        data.getLocation().setRoomName(null);
        SubmitConcernResponse response = client.submitConcern(data).execute();

        assertEquals(response.getConcern().getData(), data);

    }

    /**
     * Test submitting a concern where no actions have been taken, expect to submission to succeed
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testNoActions() throws IOException {

        ConcernData data = generateConcernData();
        data.setActionsTaken(null);

        SubmitConcernResponse response = client.submitConcern(data).execute();

        assertEquals(response.getConcern().getData(), data);

    }

    /**
     * Test submitting a concern where there is a larger than standard actions taken entry, expect to submission to succeed
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testLargeAction() throws IOException {

        ConcernData data = generateConcernData();

        data.setActionsTaken("Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Waldo Waldo! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Carmen Sandeigo! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Waldo Waldo! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!" +
                "Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum! Lorem Ipsum!");
        SubmitConcernResponse response = client.submitConcern(data).execute();

        assertEquals(response.getConcern().getData(), data);

    }

    /**
     * Test submitting a concern where everything is entered, expect to submission to succeed
     */
    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void testSuccess() throws IOException {

        ConcernData data = generateConcernData();
        SubmitConcernResponse response = client.submitConcern(data).execute();

        assertEquals(response.getConcern().getData(), data);
        /* Due to variances between webserver and local clocks, time is required to be within 15 seconds. */
        assertTrue(Math.abs(response.getConcern().getSubmissionDate().getValue() - System.currentTimeMillis()) < 15000);

    }

}