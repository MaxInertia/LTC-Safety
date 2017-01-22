package com.cs371group2.concern;

import static org.junit.Assert.assertNotNull;

import com.cs371group2.DatastoreTest;
import com.cs371group2.client.ClientApi;
import com.google.api.server.spi.response.BadRequestException;
import org.junit.Test;

/**
 * These tests are related to the concern class to ensure that concern data is properly validated
 * when submitted. This involves checking that the correct responses are returned when unexpected or
 * invalid data is provided during concern submission.
 *
 * NOTE - These tests were originally part of the client API tests. They were moved to take
 * advantage of the package private variables for testing.
 *
 * Created on 2017-01-22.
 */
public class ConcernTest extends DatastoreTest {

    /**
     * Generates a new concern with all instance variables initialized to be valid strings. This is
     * used as a helper method to construct new concern data.
     *
     * @return A new concern data object with all instance variables initialized.
     */
    public ConcernData generateConcernData() {
        ConcernData data = new ConcernData();
        data.actionsTaken = "These are some actions taken";
        data.concernNature = "A type of concern";

        Location location = new Location();
        location.facilityName = "Test Facility";
        location.roomName = "Room Name";
        data.location = location;

        Reporter reporter = new Reporter();
        reporter.email = "email@gmail.com";
        reporter.phoneNumber = "306 700 7600";
        reporter.name = "First Andlast";
        data.reporter = reporter;
        return data;
    }

    /**
     * Ensures that submission fails when the nature of a safety concern is null
     */
    @Test(expected = BadRequestException.class)
    public void nullNatureOfSafetyConcern() throws Exception {

        ConcernData data = generateConcernData();
        data.concernNature = null;
        new ClientApi().submitConcern(data);
    }

    /**
     * Ensures that submission fails when the reporter's name is null
     */
    @Test(expected = BadRequestException.class)
    public void nullReporterName() throws Exception {

        ConcernData data = generateConcernData();
        data.reporter.name = null;
        new ClientApi().submitConcern(data);
    }

    /**
     * Ensures that submission fails when email and phone number are both null
     */
    @Test(expected = BadRequestException.class)
    public void nullPhoneNumberAndEmail() throws Exception {

        ConcernData data = generateConcernData();
        data.reporter.phoneNumber = null;
        data.reporter.email = null;
        new ClientApi().submitConcern(data);
    }

    /**
     * Ensures that submission is still valid when only the email is null
     */
    @Test
    public void nullEmail() throws Exception {

        ConcernData data = generateConcernData();
        data.reporter.email = null;
        assertNotNull(new ClientApi().submitConcern(data));
    }

    /**
     * Ensures that submission is still valid when only the phone number is null
     */
    @Test
    public void nullPhoneNumber() throws Exception {

        ConcernData data = generateConcernData();
        data.reporter.phoneNumber = null;
        assertNotNull(new ClientApi().submitConcern(data));
    }

    /**
     * Tests that the facility name in a concern cannot be null when
     * submitting a concern.
     */
    @Test(expected = BadRequestException.class)
    public void nullFacility() throws Exception {

        ConcernData data = generateConcernData();
        data.location.facilityName = null;
        new ClientApi().submitConcern(data);
    }
}