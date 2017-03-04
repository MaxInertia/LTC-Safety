package com.cs371group2.admin;

import android.util.Pair;
import com.cs371group2.ApiKeys;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.facility.Facility;
import com.google.api.server.spi.response.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class represents an authenticator object for ensuring that a user has the correct admission levels
 * when accessing functionality that requires authentication.
 *
 * Created on 2017-02-09.
 */
abstract class Authenticator {

    /** The list of permission verifiers that a user must pass to be considered authenticated */
    private Collection<PermissionVerifier> permissionVerifiers;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FirebaseAuthenticator.class.getName());

    /**
     * Authenticates the given token and returns the account associated with it
     *
     * @param token The user's token to be authenticated
     * @return The account associated with the given token\
     * @precond token is valid and non-null
     */
    public Account authenticate(String token) throws UnauthorizedException {

        if (token == null || token.isEmpty()) {
            throw new UnauthorizedException("Token is invalid and cannot be authenticated");
        }

        Pair<Account, AccessToken> infoPair;
        try {
            // Test hook to try and authenticate the token as a test account
            // This allows for authenticated methods to be tested with automated unit tests.
            infoPair = authenticateTestAccount(token);

        } catch (UnauthorizedException ex) {
            logger.info("Attempting to authenticate access token.");
            infoPair = authenticateAccount(token);
        }

        logger.info("Authentication succeeded: " + infoPair.first);

        boolean isVerified = false;
        Iterator<PermissionVerifier> iter = permissionVerifiers.iterator();
        while (!isVerified && iter.hasNext()) {
            PermissionVerifier verifier = iter.next();
            isVerified = verifier.hasPermission(infoPair.first, infoPair.second);
        }

        if (!isVerified){
            logger.warning("Account attempted access without proper permissionssions: " + infoPair.first);
            throw new UnauthorizedException("User attempted access without proper permissions.");
        }
        return infoPair.first;
    }

    /**
     * Sets the authenticator permission verifiers to the given collection of verifiers.
     * @param permissionVerifiers The collection of verifiers to be checked when authenticating a user.
     * @precond permissionVerifiers != null
     * @postcond PermissionVerifiers != null
     */
    public void setPermissionVerifiers(Collection<PermissionVerifier> permissionVerifiers) {
        assert(permissionVerifiers != null);
        this.permissionVerifiers = permissionVerifiers;
        assert(this.permissionVerifiers != null);
    }

    /**
     * Authenticates the given token and returns the account associated with it. This should be implemented to
     * handle the authenticator's specific token needs.
     *
     * @param token The user's token to be authenticated
     * @return The account associated with the given token\
     * @precond token is valid and non-null
     */
    protected abstract Pair<Account, AccessToken> authenticateAccount(String token) throws UnauthorizedException;

    /**
     * Authenticates the given token and, if it is a valid token, generates a test account with the requested
     * permissions and facilities. This account is flagged as a test account meaning it only has access to
     * concerns that are flagged as test concerns.
     * @param token The user's token to be authenticated
     * @return A new account with the access permissions specified in the token.
     * @throws UnauthorizedException Thrown if the token is not a valid test account token.
     */
    private Pair<Account, AccessToken> authenticateTestAccount(String token) throws UnauthorizedException {

        logger.info("Attempting to authenticate test account.");
        try {
            Jws<Claims> claim = Jwts.parser().setSigningKey(ApiKeys.JWS_TEST_SIGNING_KEY)
                    .parseClaimsJws(token);
            Claims claims = claim.getBody();

            String id = claims.getSubject();
            AccountPermissions permissions = AccountPermissions.valueOf((String)claims.get("permissions"));
            Account account = new Account(id, permissions, true);

            Collection<String> facilities = (Collection<String>)claims.get("facilities");
            for (String name : facilities) {
                account.addFacility(new Facility(name));
            }

            String email = (String)claims.get("email");
            Boolean isEmailVerified = (Boolean)claims.get("emailVerified");
            AccessToken accessToken = new AccessToken(email, id, id, isEmailVerified);

            logger.info("Authenticated test account " + account);

            return new Pair<Account, AccessToken>(account, accessToken);

        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException e) {

            logger.info("Token does not belong to a test account.");
            throw new UnauthorizedException("Token does not belong to a test account.", e);

        } catch (ClassCastException | IllegalArgumentException e) {

            logger.info("Malformed test account token.");
            throw new UnauthorizedException("Malformed test account token.", e);
        }
    }
}
