package com.cs371group2.concern;

import com.cs371group2.ApiKeys;
import com.cs371group2.Dao;
import com.cs371group2.client.OwnerToken;
import com.cs371group2.facility.Facility;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.LoadResult;
import com.googlecode.objectify.ObjectifyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.*;
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

        logger.log(Level.FINER, "Concern # " + id + " successfully loaded from the datastore.");
        return load(id);
    }

    /**
     * Loads a collection of concerns from the datastore using a list of owner tokens that were previously created by it.
     *
     * @param tokens The owner tokens containing the concern ID in the payload.
     * @return The entitys in the datastore that the tokens reference or an empty collection if there were no tokens passed.
     * @precond tokens != null
     * @precond all the tokens in the tokens list contain a properly formatted and signed JWS for parsing.
     */
    public Collection<Concern> load(LinkedList<OwnerToken> tokens) {

        assert tokens != null;

        List<Key<Concern>> keysList = new ArrayList<>();

        for(OwnerToken curToken : tokens){
            assert curToken.validate().isValid();
            keysList.add(Key.create(Concern.class, curToken.getToken()));
        }
        System.out.println("\n\n\n\nKeysList item count: " + keysList.size());

        Collection<Concern> concerns = load(keysList);

        System.out.println("ReturnList item count: " + concerns.size() + "\n\n\n\n\n");

        return concerns;
    }


    /**
     * Loads a list of concerns from the datastore starting at the given offset and ending by limit.
     * The facilities is the list of facilities to load concerns for (ignoring all concerns that are
     * not associated with any of the given facilities)
     *
     * @param offset The offset to begin loading from
     * @param limit The maximum amount of concerns to load
     * @param facilities The list of facilities to load the concerns from
     * @return A list of entities in the datastore from the given offset and limit
     * @precond offset != null && offset >= 0
     * @precond limit != null && limit > 0
     */
    public List<Concern> load(int offset, int limit, HashSet<Facility> facilities){
        assert(offset >= 0);
        assert(limit > 0);

        List<Concern> filteredList = new LinkedList<Concern>();

        if(facilities != null) {
            filteredList = ObjectifyService.ofy().load().type(Concern.class)
                    .filter("facilityRef in ", facilities)
                    .order("submissionDate")
                    .offset(offset)
                    .limit(limit)
                    .list();
        }

        return filteredList;
    }

    /**
     * Loads a list of concerns from the datastore starting at the given offset and ending by limit.
     * The facilities is the list of facilities to load the concern for, and an exception is thrown
     * if the concern loaded is not among the given locations.
     *
     * @param concernId The unique id of the Concern to load from the datastore
     * @param facilities The list of facilities to load the concerns from
     * @return A list of entities in the datastore from the given offset and limit
     * @precond offset != null && offset >= 0
     * @precond limit != null && limit > 0
     */
    public Concern load(long concernId, HashSet<Facility> facilities) throws UnauthorizedException, BadRequestException {

        Concern loaded = super.load(concernId);

        if(facilities != null){
            if(facilities.contains(loaded.getFacility())){
                return loaded;
            } else {
                throw new UnauthorizedException("Requested concern is not associated with any of the given facilities");
            }
        } else {
            throw new UnauthorizedException("Set of facilities given for loading the concern was null!");
        }
    }
}
