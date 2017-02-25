package com.cs371group2.concern;

import static org.junit.Assert.assertNotNull;

import com.cs371group2.DatastoreTest;
import com.cs371group2.client.ClientApi;
import com.cs371group2.concern.Location.TestHook_MutableLocation;
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
    public ConcernData.TestHook_MutableConcernData generateConcernData() {

        Location.TestHook_MutableLocation location = new TestHook_MutableLocation(
                "Test Facility",
                "Room Name");

        Reporter.TestHook_MutableReporter reporter = new Reporter.TestHook_MutableReporter(
                "First and Last",
                "email@gmail.com",
                "306 700 7600");

        ConcernData.TestHook_MutableConcernData data = new ConcernData.TestHook_MutableConcernData(
                "A type of concern",
                "These are some actions taken",
                reporter,
                location);

        return data;
    }

    /**
     * Ensures that submission fails when the nature of a safety concern is null
     */
    @Test(expected = BadRequestException.class)
    public void nullNatureOfSafetyConcern() throws Exception {

        ConcernData.TestHook_MutableConcernData data = generateConcernData();
        data.setConcernNature(null);
        new ClientApi().submitConcern(data.build());
    }

    /**
     * Ensures that submission fails when the reporter's name is null
     */
    @Test(expected = BadRequestException.class)
    public void nullReporterName() throws Exception {

        ConcernData.TestHook_MutableConcernData data = generateConcernData();
        data.getMutableReporter().setName(null);
        new ClientApi().submitConcern(data.build());
    }

    /**
     * Ensures that submission fails when email and phone number are both null
     */
    @Test(expected = BadRequestException.class)
    public void nullPhoneNumberAndEmail() throws Exception {

        ConcernData.TestHook_MutableConcernData data = generateConcernData();
        data.getMutableReporter().setPhoneNumber(null);
        data.getMutableReporter().setEmail(null);
        new ClientApi().submitConcern(data.build());
    }

    /**
     * Ensures that submission is still valid when only the email is null
     */
    @Test
    public void nullEmail() throws Exception {

        ConcernData.TestHook_MutableConcernData data = generateConcernData();
        data.getMutableReporter().setEmail(null);
        assertNotNull(new ClientApi().submitConcern(data.build()));
    }

    /**
     * Ensures that submission is still valid when only the phone number is null
     */
    @Test
    public void nullPhoneNumber() throws Exception {

        ConcernData.TestHook_MutableConcernData data = generateConcernData();
        data.getMutableReporter().setPhoneNumber(null);
        assertNotNull(new ClientApi().submitConcern(data.build()));
    }

    /**
     * Tests that the facility name in a concern cannot be null when
     * submitting a concern.
     */
    @Test(expected = BadRequestException.class)
    public void nullFacility() throws Exception {

        ConcernData.TestHook_MutableConcernData data = generateConcernData();
        data.getMutableLocation().setFacilityName(null);
        new ClientApi().submitConcern(data.build());
    }

    /**
     * Tests that the facility name in a concern that is unknown to the system is properly assigned the
     * Other facility reference when submitting a concern.
     */
    @Test
    public void unknownFacility() throws Exception {
        ConcernData.TestHook_MutableConcernData data = generateConcernData();
        data.getMutableLocation().setFacilityName("Unknown facility name");
        new ClientApi().submitConcern(data.build());
    }
}