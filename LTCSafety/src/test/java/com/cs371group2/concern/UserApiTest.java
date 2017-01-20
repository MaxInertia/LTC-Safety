package com.cs371group2.concern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.cs371group2.ApiKeys;
import com.cs371group2.InitContextListener;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for testing the endpoints used by the Android and iOS mobile clients.
 */
public class UserApiTest {

    /**
     * The local test helper for setting up and tearing down the local database.
     */
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    protected Closeable session;

    /**
     * Setup the environment for local testing of the datastore by creating an empty datastore to
     * perform the tests with.
     */
    @Before
    public void setUp() throws Exception {
        this.session = ObjectifyService.begin();
        this.helper.setUp();
        new InitContextListener().contextInitialized(null);
    }

    /**
     * Destroy the local test datastore cleanup for the next test.
     */
    @After
    public void tearDown() throws Exception {
        AsyncCacheFilter.complete();
        this.session.close();
        this.helper.tearDown();
    }

    /**
     * Tests submit concern and retract concern with expected inputs. A single test method is used
     * in order to ensure that submit concern passes its tests before retract concern is called
     * because the retract concern unit test calls the submitConcern API endpoint.
     */
    @Test
    public void concernTest() throws Exception {
        submitConcern();
        retractConcern();
    }

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
     * Tests the submitConcern endpoint with valid data. This should result in the concern being
     * stored in the database and a valid owner token being returned that can be used to access the
     * concern.
     */
    public void submitConcern() throws Exception {

        ConcernData data = generateConcernData();

        OwnerToken token = new UserApi().submitConcern(data);
        Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                .parseClaimsJws(token.getToken());
        Claims claims = claim.getBody();

        Long id = Long.parseLong(claims.getSubject());
        Key<Concern> key = Key.create(Concern.class, id);
        Concern concern = ObjectifyService.ofy().load().key(key).now();

        ConcernData loadedData = concern.getData();
        assertEquals(loadedData.getActionsTaken(), data.getActionsTaken());
        assertEquals(loadedData.getConcernNature(), data.getConcernNature());

        assertEquals(loadedData.getReporter().getName(), data.getReporter().getName());
        assertEquals(loadedData.getReporter().getPhoneNumber(),
                data.getReporter().getPhoneNumber());
        assertEquals(loadedData.getReporter().getEmail(), data.getReporter().getEmail());

        assertEquals(loadedData.getLocation().getFacilityName(),
                data.getLocation().getFacilityName());
        assertEquals(loadedData.getLocation().getRoomName(), data.getLocation().getRoomName());

        assertEquals(concern.getStatus(), ConcernStatus.PENDING);
        assertNotNull(concern.getSubmissionDate());
    }

    /**
     * Ensures that submission fails when the nature of a safety concern is null
     */
    @Test(expected = BadRequestException.class)
    public void nullNatureOfSafetyConcern() throws Exception {

        ConcernData data = generateConcernData();
        data.concernNature = null;
        new UserApi().submitConcern(data);
    }

    /**
     * Ensures that submission fails when the reporter's name is null
     */
    @Test(expected = BadRequestException.class)
    public void nullReporterName() throws Exception {

        ConcernData data = generateConcernData();
        data.reporter.name = null;
        new UserApi().submitConcern(data);
    }

    /**
     * Ensures that submission fails when email and phone number are both null
     */
    @Test(expected = BadRequestException.class)
    public void nullPhoneNumberAndEmail() throws Exception {

        ConcernData data = generateConcernData();
        data.reporter.phoneNumber = null;
        data.reporter.email = null;
        new UserApi().submitConcern(data);
    }

    /**
     * Ensures that submission is still valid when only the email is null
     */
    @Test
    public void nullEmail() throws Exception {

        ConcernData data = generateConcernData();
        data.reporter.email = null;
        assertNotNull(new UserApi().submitConcern(data));
    }

    /**
     * Ensures that submission is still valid when only the phone number is null
     */
    @Test
    public void nullPhoneNumber() throws Exception {

        ConcernData data = generateConcernData();
        data.reporter.phoneNumber = null;
        assertNotNull(new UserApi().submitConcern(data));
    }

    /**
     * Tests that the facility name in a concern cannot be null when
     * submitting a concern.
     */
    @Test(expected = BadRequestException.class)
    public void nullFacility() throws Exception {

        ConcernData data = generateConcernData();
        data.location.facilityName = null;
        new UserApi().submitConcern(data);
    }

    /**
     * Tests that a concern can be properly retracted from the database.
     * Specifically tests that the concern has been moved to the archive
     * and that the concern's status has been changed to "RETRACTED".
     */
    public void retractConcern() throws Exception {

        ConcernData data = generateConcernData();
        OwnerToken token = new UserApi().submitConcern(data);
        new UserApi().retractConcern(token);

        Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                .parseClaimsJws(token.getToken());
        Claims claims = claim.getBody();

        Long id = Long.parseLong(claims.getSubject());
        Key<Concern> key = Key.create(Concern.class, id);
        Concern concern = ObjectifyService.ofy().load().key(key).now();

        assertTrue(concern.isArchived());
        assertEquals(concern.getStatus(), ConcernStatus.RETRACTED);
    }

    /**
     * Tries to access data in the database with a random key and subject
     * testing that accessing that data will fail.
     * retractConcern should throw UnauthorizedException if this is the case.
     */
    @Test(expected = UnauthorizedException.class)
    public void retractUnauthorized() throws Exception {

        OwnerToken token = new OwnerToken();
        token.token = Jwts.builder()
                .setSubject("9329032492384590435")
                .signWith(SignatureAlgorithm.HS256, "A random key that will fail.")
                .compact();

        new UserApi().retractConcern(token);
    }

    /**
     * Tries to access data in the database with a random subject but correct key
     * testing that accessing that data will fail.
     * retractConcern should throw NotFoundException if this is the case.
     */
    @Test(expected = NotFoundException.class)
    public void retractNotFound() throws Exception {
        OwnerToken token = new OwnerToken(93290324923845L);
        new UserApi().retractConcern(token);
    }
}