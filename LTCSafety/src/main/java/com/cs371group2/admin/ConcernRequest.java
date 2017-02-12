package com.cs371group2.admin;

import com.cs371group2.ApiKeys;
import com.cs371group2.ValidationResult;
import com.cs371group2.client.OwnerToken;
import io.jsonwebtoken.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This object represents a concern request containing an offset and a limit,
 * both of which will be to access the concern database. It will also include all
 * necessary functionality for authenticating the requester.
 *
 * Created on 2017-02-08.
 */
public class ConcernRequest extends AdminRequest {

    private static final String NULL_TOKEN_ERROR = "Unable to access concern due to non-existent credentials.";

    private static final Logger logger = Logger.getLogger( OwnerToken.class.getName() );

    /** The offset in the database to begin loading the concerns from */
    private int offset;

    /** The maximum number of elements to be loaded from the database */
    private int limit;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

/**
    @Override
    public ValidationResult validate() {

        if (accessToken == null) {
            logger.log(Level.FINE, "Tried to validate a null owner token.");
            return new ValidationResult(NULL_TOKEN_ERROR);
        }
        try {
            Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_SIGNING_KEY)
                    .parseClaimsJws(accessToken);
            Claims claims = claim.getBody();

            // Ensuring that the parsing is correct despite the result not being used
            Long.parseLong(claims.getSubject());

        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | NumberFormatException e) {

            logger.log(Level.FINE, "Owner token validation failed due to " + e.getMessage());
            return new ValidationResult(e.getMessage());
        }

        logger.log(Level.FINER, "Owner token validation successful.");
        return new ValidationResult();
    } */
}
