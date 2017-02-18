package com.cs371group2.concern;

import com.cs371group2.ApiKeys;
import com.cs371group2.Dao;
import com.cs371group2.client.OwnerToken;
import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.ObjectifyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
     * Loads a list of concerns from the datastore starting at the given offset and ending by limit.
     *
     * @param offset The offset to begin loading from
     * @param limit The maximum amount of concerns to load
     * @return A list of entities in the datastore from the given offset and limit
     * @precond offset != null && offset >= 0
     * @precond limit != null && limit > 0
     */
    public List<Concern> load(int offset, int limit){
        assert(offset >= 0);
        assert(limit > 0);

        List<Concern> filteredList = ObjectifyService.ofy().load().type(Concern.class).order("submissionDate")
                                                                                    .offset(offset).limit(limit).list();

        return filteredList;
    }
}
