package com.cs371group2.concern;

import com.cs371group2.ApiKeys;
import com.cs371group2.Dao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/**
 * Data access object for the saving, loading, and deletion of concerns.
 *
 * Created on 2017-01-19.
 */
public class ConcernDao extends Dao<Concern> {

    /**
     * Creates a data access object for loading, saving, and deleting concerns.
     */
    public ConcernDao() {
        super(Concern.class);
    }

    public Concern load(OwnerToken token) {

        assert token != null;
        assert token.validate().isValid();

        Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                .parseClaimsJws(token.getToken());
        Claims claims = claim.getBody();

        Long id = Long.parseLong(claims.getSubject());
        return load(id);
    }
}
