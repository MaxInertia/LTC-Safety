package com.cs371group2.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.cs371group2.ApiKeys;
import com.cs371group2.DatastoreTest;
import com.cs371group2.concern.*;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Tests for testing the endpoints used by the Android and iOS mobile clients.
 */
public class ClientApiTest extends DatastoreTest {

    /**
     * Tests submit concern and retract concern with expected inputs. A single test method is used
     * in order to ensure that submit concern passes its tests before retract concern is called
     * because the retract concern unit test calls the submitConcern API endpoint.
     */
    @Test
    public void concernTest() throws Exception {
        submitConcern();
        fetchConcerns();
        retractConcern();
    }

    /**
     * Tests the submitConcern endpoint with valid data. This should result in the concern being
     * stored in the database and a valid owner token being returned that can be used to access the
     * concern.
     */
    private void submitConcern() throws Exception {

        ConcernData data = new ConcernTest().generateConcernData().build();

        OwnerToken token = new ClientApi().submitConcern(data).getOwnerToken();
        Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                .parseClaimsJws(token.getToken());
        Claims claims = claim.getBody();

        Long id = Long.parseLong(claims.getSubject());
        Key<Concern> key = Key.create(Concern.class, id);
        Concern concern = ObjectifyService.ofy().load().key(key).now();

        ConcernData loadedData = concern.getData();
        assertEquals(loadedData.getActionsTaken(), data.getActionsTaken());
        assertEquals(loadedData.getConcernNature(), data.getConcernNature());
        assertEquals(loadedData.getDescription(), data.getDescription());

        assertEquals(loadedData.getReporter().getName(), data.getReporter().getName());
        assertEquals(loadedData.getReporter().getPhoneNumber(),
                data.getReporter().getPhoneNumber());
        assertEquals(loadedData.getReporter().getEmail(), data.getReporter().getEmail());

        assertEquals(loadedData.getLocation().getFacilityName(),
                data.getLocation().getFacilityName());
        assertEquals(loadedData.getLocation().getRoomName(), data.getLocation().getRoomName());

        assertEquals(concern.getStatuses().first().getType(), ConcernStatusType.PENDING);
        assertNotNull(concern.getSubmissionDate());
    }


    /**
     * Tests that a list of concerns can be properly accessed from the database.
     * Specifically tests this by creating concern data, submitting it to the database,
     * accessing that data, and making sure it is the same as what was inserted.
     */
    private void fetchConcerns() throws Exception {
        ConcernData data1 = new ConcernTest().generateConcernData().build();
        ConcernData data2 = new ConcernTest().generateConcernData().build();
        OwnerToken token1 = new ClientApi().submitConcern(data1).getOwnerToken();
        OwnerToken token2 = new ClientApi().submitConcern(data2).getOwnerToken();

        LinkedList<OwnerToken> tokenSet = new LinkedList<OwnerToken>();
        tokenSet.add(token1);
        tokenSet.add(token2);
        LinkedList<Concern> returnedConcerns = new ClientApi().fetchConcerns(new OwnerTokenListWrapper(tokenSet));

        assertEquals(returnedConcerns.getFirst().getData(), data1);
        assertEquals(returnedConcerns.getLast().getData(), data2);
        assertTrue(returnedConcerns.getFirst().getData() != returnedConcerns.getLast().getData());
    }



    /**
     * Tries to access data in the database with a random key and subject testing that accessing
     * that data will fail. fetchConcern should throw BadRequestException if this is the case.
     */
    @Test(expected = BadRequestException.class)
    public void fetchUnauthorized() throws Exception {

        OwnerToken token = new OwnerToken();
        token.token = Jwts.builder()
                .setSubject("9329032492384590435")
                .signWith(SignatureAlgorithm.HS256, "A random key that will fail.")
                .compact();

        LinkedList<OwnerToken> tokenList = new LinkedList<>();
        tokenList.add(token);
        OwnerTokenListWrapper tokenWrapperList = new OwnerTokenListWrapper(tokenList);
        new ClientApi().fetchConcerns(tokenWrapperList);
    }

    /**
     * Attempts to fetch a concern with an entity for an entity ID that doesn't exist. The test
     * passes if the NotFoundException is thrown because the entity shouldn't exist.
     */
    @Test(expected = NotFoundException.class)
    public void fetchNotFound() throws Exception {
        OwnerToken token = new OwnerToken(93290324923845L);
        LinkedList<OwnerToken> tokenList = new LinkedList<>();
        tokenList.add(token);
        OwnerTokenListWrapper tokenWrapperList = new OwnerTokenListWrapper(tokenList);
        new ClientApi().fetchConcerns(tokenWrapperList);
    }

    /**
     * Tests that when a concern is fetched with a token that contains a non-numerical concern
     * identifier a BadRequestException is thrown. Concerns can only be accessed with identifiers of
     * type long.
     */
    @Test(expected = BadRequestException.class)
    public void fetchInvalidIdentifier() throws Exception {
        OwnerToken token = new OwnerToken();
        token.token = Jwts.builder()
                .setSubject("Not a string")
                .signWith(SignatureAlgorithm.HS256, ApiKeys.JWS_SIGNING_KEY)
                .compact();
        LinkedList<OwnerToken> tokenList = new LinkedList<>();
        tokenList.add(token);
        OwnerTokenListWrapper tokenWrapperList = new OwnerTokenListWrapper(tokenList);
        new ClientApi().fetchConcerns(tokenWrapperList);
    }

    /**
     * Tests that when an owner token with a malformed JWS is used to fetch a concern that it
     * fails gracefully by throwing a bad request exception.
     */
    @Test(expected = BadRequestException.class)
    public void fetchMalformed() throws Exception {
        OwnerToken token = new OwnerToken();
        token.token = "Invalid JWS";
        LinkedList<OwnerToken> tokenList = new LinkedList<>();
        tokenList.add(token);
        OwnerTokenListWrapper tokenWrapperList = new OwnerTokenListWrapper(tokenList);
        new ClientApi().fetchConcerns(tokenWrapperList);
    }

    /**
     * Tests that when an owner token with no raw token is provided that the request to fetch the
     * concern fails gracefully by throwing a bad request exception.
     */
    @Test(expected = BadRequestException.class)
    public void fetchNull() throws Exception {
        OwnerToken token = new OwnerToken();
        token.token = null;
        LinkedList<OwnerToken> tokenList = new LinkedList<>();
        tokenList.add(token);
        OwnerTokenListWrapper tokenWrapperList = new OwnerTokenListWrapper(tokenList);
        new ClientApi().fetchConcerns(tokenWrapperList);
    }

    /**
     * Tests that a concern can be properly retracted from the database.
     * Specifically tests that the concern has been moved to the archive
     * and that the concern's status has been changed to "RETRACTED" and
     * that the retract concern method has returned the now retracted
     * concern status.
     */
    private void retractConcern() throws Exception {
        ConcernData data = new ConcernTest().generateConcernData().build();
        OwnerToken token = new ClientApi().submitConcern(data).getOwnerToken();
        UpdateConcernStatusResponse response = new ClientApi().retractConcern(token);

        Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                .parseClaimsJws(token.getToken());
        Claims claims = claim.getBody();

        Long id = Long.parseLong(claims.getSubject());
        Key<Concern> key = Key.create(Concern.class, id);
        Concern concern = ObjectifyService.ofy().load().key(key).now();

        assertTrue(concern.isArchived());
        assertEquals(concern.getStatuses().last().getType(), ConcernStatusType.RETRACTED);
        assertEquals(concern.getStatuses().last(), response.getStatus());
    }



    /**
     * Tries to access data in the database with a random key and subject testing that accessing
     * that data will fail. retractConcern should throw BadRequestException if this is the case.
     */
    @Test(expected = BadRequestException.class)
    public void retractUnauthorized() throws Exception {

        OwnerToken token = new OwnerToken();
        token.token = Jwts.builder()
                .setSubject("9329032492384590435")
                .signWith(SignatureAlgorithm.HS256, "A random key that will fail.")
                .compact();

        new ClientApi().retractConcern(token);

    }

    /**
     * Attempts to retract a concern with an entity for an entity ID that doesn't exist. The test
     * passes if the NotFoundException is thrown because the entity shouldn't exist.
     */
    @Test(expected = NotFoundException.class)
    public void retractNotFound() throws Exception {
        OwnerToken token = new OwnerToken(93290324923845L);
        new ClientApi().retractConcern(token);
    }

    /**
     * Tests that when a concern is retracted with a token that contains a non-numerical concern
     * identifier a BadRequestException is thrown. Concerns can only be accessed with identifiers of
     * type long.
     */
    @Test(expected = BadRequestException.class)
    public void retractInvalidIdentifier() throws Exception {
        OwnerToken token = new OwnerToken();
        token.token = Jwts.builder()
                .setSubject("Not a string")
                .signWith(SignatureAlgorithm.HS256, ApiKeys.JWS_SIGNING_KEY)
                .compact();

        new ClientApi().retractConcern(token);
    }

    /**
     * Tests that when an owner token with a malformed JWS is used to retract a concern that it
     * fails gracefully by throwing a bad request exception.
     */
    @Test(expected = BadRequestException.class)
    public void retractMalformed() throws Exception {
        OwnerToken token = new OwnerToken();
        token.token = "Invalid JWS";
        new ClientApi().retractConcern(token);
    }

    /**
     * Tests that when an owner token with no raw token is provided that the request to retract the
     * concern fails gracefully by throwing a bad request exception.
     */
    @Test(expected = BadRequestException.class)
    public void retractNull() throws Exception {
        OwnerToken token = new OwnerToken();
        token.token = null;
        new ClientApi().retractConcern(token);
    }


}