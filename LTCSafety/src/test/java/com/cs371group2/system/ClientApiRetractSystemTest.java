package com.cs371group2.system;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.*;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

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
public class ClientApiRetractSystemTest {

    private static Client client;
    private static OwnerToken token1;

    @BeforeClass
    public static void setUp() throws Exception {
        client = new Client.Builder(new NetHttpTransport(), new GsonFactory(), null).build();
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
        SubmitConcernResponse response = client.submitConcern(data).execute();
        SubmitConcernResponse response2 = client.submitConcern(data).execute();
        token1 = response.getOwnerToken();
    }


    /**
     * Test submitting an null token, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testNoToken() throws IOException {

        try {
            UpdateConcernStatusResponse result = client.retractConcern(null).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getCode(), 400);
        }

    }

    /**
     * Test submitting a token with a null string, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testNoStringToken() throws IOException {

        OwnerToken nullToken = new OwnerToken();
        nullToken.setToken(null);
        try {
            UpdateConcernStatusResponse result = client.retractConcern(nullToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getCode(), 400);
        }

    }

    /**
     * Test submitting a malformed token, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testMalformedToken() throws IOException {

        OwnerToken malformedToken = new OwnerToken();
        malformedToken.setToken("ASDGGFSDVSRCDSVDSZ");
        try {
            UpdateConcernStatusResponse result = client.retractConcern(malformedToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getCode(), 400);
        }

    }

    /**
     * Test submitting an illegal token, expect to receive http 400 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testIllegalToken() throws IOException {
        OwnerToken illegalToken = new OwnerToken();
        illegalToken.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MDk0NTg1MjQ4MkUxOTA0In0.WK_n-6kTpFabvjG_SQTruBImIiqrEqXb3YR4sG0zwH");
        try {
            UpdateConcernStatusResponse result = client.retractConcern(illegalToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getCode(), 400);
        }

    }

    /**
     * Test submitting a valid token that has no concern associated with it, expect to receive http 404 in response
     */
    @Test
    @Category(SystemTests.class)
    public void testNoConcernFound() throws IOException {
        OwnerToken formerlyLegalToken = new OwnerToken();
        formerlyLegalToken.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NzQ2NjY0ODk5ODcwNzIwIn0.a-xMYgzdF7EER3cyoVlkyc7ycsCETPcfpRhuP8-XKFw");
        try {
            UpdateConcernStatusResponse result = client.retractConcern(formerlyLegalToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getCode(), 404);
        }

    }

    /**
     * Test retracting a concern that has already been retracted, expect to receive http 409 in response
     * @throws IOException
     */
    @Test
    @Category(SystemTests.class)
    public void testRetractRetractedConcern() throws IOException {
        OwnerToken legalToken = new OwnerToken();
        legalToken.setToken(token1.getToken());
        try {
            UpdateConcernStatusResponse result = client.retractConcern(legalToken).execute();
            result = client.retractConcern(legalToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getCode(), 409);
        }

    }

    /**
     * Test a successful retraction
     */
    @Test
    @Category(SystemTests.class)
    public void testRetractSuccess() throws IOException {
        OwnerToken legalToken = new OwnerToken();
        legalToken.setToken(token1.getToken());

        UpdateConcernStatusResponse result = client.retractConcern(legalToken).execute();

        assertEquals(result.getStatus().getType(), "RETRACTED");
    }
}