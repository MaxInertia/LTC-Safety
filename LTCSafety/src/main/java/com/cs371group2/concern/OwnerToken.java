package com.cs371group2.concern;

import com.cs371group2.ApiKeys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The owner token class is used to store the JWS token that is created upon concern submission.
 * This JWS contains the identifier used to identify the concern within the data store. Possessing
 * an owner token gives a client the ability to retract the concern that they have submitted.
 *
 * Created on 2017-01-17.
 */
class OwnerToken {

    /**
     * The raw JWS token string used to verify that the owner token is authentic. The identifier
     * used to locate the concern within the database is stored in the payload section of this token
     * as the subject.
     */
    String token;

    public String getToken() {
        return token;
    }

    /**
     * Creates a new owner token with the specified identifier. This identifier must correspond to a
     * concern within the datastore. This id is then signed using the private server key to create
     * the raw token.
     *
     * @param id The identifier of a concern within the datastore.
     * @pre-cond id != null
     */
    OwnerToken(Long id) {

        assert id != null;

        token = Jwts.builder()
                .setSubject(id.toString())
                .signWith(SignatureAlgorithm.HS256, ApiKeys.JWS_SIGNING_KEY)
                .compact();
    }

    OwnerToken() {

    }
}
