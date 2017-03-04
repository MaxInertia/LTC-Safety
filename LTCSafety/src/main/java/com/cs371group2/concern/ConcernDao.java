package com.cs371group2.concern;

import com.cs371group2.ApiKeys;
import com.cs371group2.Dao;
import com.cs371group2.account.Account;
import com.cs371group2.client.OwnerToken;
import com.cs371group2.facility.Facility;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.googlecode.objectify.ObjectifyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data access object for the saving, loading, and deletion of concerns.
 *
 * Created on 2017-01-19.
 */
public class ConcernDao extends Dao<Concern> {

    private static final Logger logger = Logger.getLogger(ConcernDao.class.getName());

    /**
     * Creates a data access object for loading, saving, and deleting concerns.
     */
    public ConcernDao() {
        super(Concern.class);
    }

    /**
     * Loads a concern from the datastore using an owner token that was previously created by it.
     *
     * @param token The owner token containing the concern ID in the payload.
     * @return The entity in the datastore that the token references or null if it doesn't exist.
     * @precond token != null
     * @precond token.validate().isValid() meaning that the token contains a properly formatted and
     * signed JWS for parsing.
     */
    public Concern load(OwnerToken token) {

        assert token != null;
        assert token.validate().isValid();

        Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                .parseClaimsJws(token.getToken());
        Claims claims = claim.getBody();

        Long id = Long.parseLong(claims.getSubject());
        logger.log(Level.FINER, "Loading concern # " + id + "  from the datastore...");
        return super.load(id);
    }

    /**
     * Loads a list of concerns from the datastore starting at the given offset and ending by limit.
     * The account is used to determine which concerns should be loaded based on the list of facilities
     * they have access to and whether or not it is a testing account.
     *
     * @param account The account that is requesting the list of concerns.
     * @param offset The offset to begin loading from
     * @param limit The maximum amount of concerns to load
     * @return A list of entities in the datastore from the given offset and limit
     * @precond offset != null && offset >= 0
     * @precond limit != null && limit > 0
     * @precond account != null
     */
    public List<Concern> load(Account account, int offset, int limit){

        assert account != null;
        assert(offset >= 0);
        assert(limit > 0);

        Set<Facility> facilities = account.getFacilities();
        return ObjectifyService.ofy().load().type(Concern.class)
                .filter("isTest = ", account.isTestingAccount())
                .filter("facilityRef in ", facilities)
                .order("-submissionDate")
                .offset(offset)
                .limit(limit)
                .list();
    }

    /**
     * Loads a list of concerns from the datastore starting at the given offset and ending by limit.
     * The facilities is the list of facilities to load the concern for, and an exception is thrown
     * if the concern loaded is not among the given locations.
     *
     * @param concernId The unique id of the Concern to load from the datastore
     * @param account The list of facilities to load the concerns from
     * @return A list of entities in the datastore from the given offset and limit
     * @precond offset != null && offset >= 0
     * @precond limit != null && limit > 0
     * @precond account != null
     */
    public Concern load(Account account, long concernId) throws UnauthorizedException, NotFoundException {

        assert account != null;

        Concern concern = super.load(concernId);
        System.out.println("Concern: " + concern);
        if (concern == null) {
            throw new NotFoundException("Failed to find a concern with ID: " + concernId);
        }
        Set<Facility> facilities = account.getFacilities();
        if (facilities.contains(concern.getFacility()) && (account.isTestingAccount() == concern.isTest())){
            return concern;
        } else {
            throw new UnauthorizedException("The account " + account.getId() + "does not have permission to access concern with ID: " + concernId);
        }
    }
}
