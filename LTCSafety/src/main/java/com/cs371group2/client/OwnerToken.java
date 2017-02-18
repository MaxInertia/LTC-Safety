package com.cs371group2.client;

import com.cs371group2.ApiKeys;
import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The owner token class is used to store the JWS token that is created upon concern submission.
 * This JWS contains the identifier used to identify the concern within the data store. Possessing
 * an owner token gives a client the ability to retract the concern that they have submitted.
 *
 * Created on 2017-01-17.
 */
public class OwnerToken implements Validatable {

    private static final String NULL_TOKEN_ERROR = "Unable to access concern due to non-existent credentials.";

    private static final Logger logger = Logger.getLogger( OwnerToken.class.getName() );

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
    public OwnerToken(Long id) {

        assert id != null;

        logger.log(Level.FINER, "Owner token generated for Concern# " + id + ".");

        token = Jwts.builder()
                .setSubject(id.toString())
                .signWith(SignatureAlgorithm.HS256, ApiKeys.JWS_SIGNING_KEY)
                .compact();
    }

    /**
     * A constructor to make the owner token class more testable. This allows for tokens
     * to be created with invalid or null JWS's. This should only be used for testing.
     */
    OwnerToken() {

    }

    @Override
    public ValidationResult validate() {

        if (token == null) {
            logger.log(Level.FINE, "Tried to validate a null owner token.");
            return new ValidationResult(NULL_TOKEN_ERROR);
        }
        try {
            Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                    .parseClaimsJws(token);
            Claims claims = claim.getBody();

            // Ensuring that the parsing is correct despite the result not being used
            Long.parseLong(claims.getSubject());

        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | NumberFormatException e) {

            logger.log(Level.FINE, "Owner token validation failed due to " + e.getMessage());
            return new ValidationResult(e.getMessage());
        }

        logger.log(Level.FINER, "Owner token validation successful.");
        return new ValidationResult();
    }
}
