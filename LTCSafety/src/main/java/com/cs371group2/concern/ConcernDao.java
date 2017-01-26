package com.cs371group2.concern;

import com.cs371group2.ApiKeys;
import com.cs371group2.Dao;
import com.cs371group2.client.OwnerToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data access object for the saving, loading, and deletion of concerns.
 *
 * Created on 2017-01-19.
 */
public class ConcernDao extends Dao<Concern> {

    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ConcernDao.class.getName() );



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
        if(token == null){ LOGGER.log(Level.WARNING, "Concern tried to be loaded from datastore with null token."); }
        assert token != null;
        if(!token.validate().isValid()){ LOGGER.log(Level.WARNING, "Concern tried to be loaded from datastore with invalid token."); }
        assert token.validate().isValid();

        Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                .parseClaimsJws(token.getToken());
        Claims claims = claim.getBody();

        Long id = Long.parseLong(claims.getSubject());

        LOGGER.log(Level.FINER, "Concern # " + id + " successfully loaded from the datastore.");
        return load(id);
    }
}
