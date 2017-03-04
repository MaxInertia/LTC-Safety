package com.cs371group2.system.client.api;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.*;
import com.cs371group2.system.ClientAPISystemTests;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RetractSystemTest {

    private static Client client;

    @BeforeClass
    public static void setUp() throws Exception {
        client = new Client.Builder(new NetHttpTransport(), new GsonFactory(), null).build();
    }

    private OwnerToken getValidOwnerToken() throws IOException {

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
        return response.getOwnerToken();
    }


    /**
     * Test submitting an null token, expect to receive http 400 in response
     */
    @Test
    @Category(ClientAPISystemTests.class)
    public void testNoToken() throws IOException {

        try {
            UpdateConcernStatusResponse result = client.retractConcern(null).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "Unable to access concern due to non-existent credentials.");
        }

    }

    /**
     * Test submitting a token with a null string, expect to receive http 400 in response
     */
    @Test
    @Category(ClientAPISystemTests.class)
    public void testNoStringToken() throws IOException {

        OwnerToken nullToken = new OwnerToken();
        nullToken.setToken(null);
        try {
            UpdateConcernStatusResponse result = client.retractConcern(nullToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "Unable to access concern due to non-existent credentials.");
        }

    }

    /**
     * Test submitting a malformed token, expect to receive http 400 in response
     */
    @Test
    @Category(ClientAPISystemTests.class)
    public void testMalformedToken() throws IOException {

        OwnerToken malformedToken = new OwnerToken();
        malformedToken.setToken("ASDGGFSDVSRCDSVDSZ");
        try {
            UpdateConcernStatusResponse result = client.retractConcern(malformedToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "JWT strings must contain exactly 2 period characters. Found: 0");
        }

    }

    /**
     * Test submitting an illegal token, expect to receive http 400 in response
     */
    @Test
    @Category(ClientAPISystemTests.class)
    public void testIllegalToken() throws IOException {
        OwnerToken illegalToken = new OwnerToken();
        illegalToken.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MDk0NTg1MjQ4MkUxOTA0In0.WK_n-6kTpFabvjG_SQTruBImIiqrEqXb3YR4sG0zwH");
        try {
            UpdateConcernStatusResponse result = client.retractConcern(illegalToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.");
        }

    }

    /**
     * Test submitting a valid token that has no concern associated with it, expect to receive http 404 in response
     */
    @Test
    @Category(ClientAPISystemTests.class)
    public void testNoConcernFound() throws IOException {
        OwnerToken formerlyLegalToken = new OwnerToken();
        formerlyLegalToken.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NzQ2NjY0ODk5ODcwNzIwIn0.a-xMYgzdF7EER3cyoVlkyc7ycsCETPcfpRhuP8-XKFw");
        try {
            UpdateConcernStatusResponse result = client.retractConcern(formerlyLegalToken).execute();
        } catch (GoogleJsonResponseException e) {

            assertEquals(e.getDetails().getMessage(), "Attempted to retract a concern that could not be found.");
        }

    }

    /**
     * Test retracting a concern that has already been retracted, expect to receive http 409 in response
     * @throws IOException
     */
    @Test
    @Category(ClientAPISystemTests.class)
    public void testRetractRetractedConcern() throws IOException {

        OwnerToken legalToken = getValidOwnerToken();
        try {
            UpdateConcernStatusResponse result = client.retractConcern(legalToken).execute();
            result = client.retractConcern(legalToken).execute();
        } catch (GoogleJsonResponseException e) {
            assertEquals(e.getDetails().getMessage(), "Attempted to retract a concern that has already been retracted.");
        }

    }

    /**
     * Test a successful retraction
     */
    @Test
    @Category(ClientAPISystemTests.class)
    public void testRetractSuccess() throws IOException {

        OwnerToken legalToken = getValidOwnerToken();
        UpdateConcernStatusResponse result = client.retractConcern(legalToken).execute();
        assertEquals(result.getStatus().getType(), "RETRACTED");
    }
}