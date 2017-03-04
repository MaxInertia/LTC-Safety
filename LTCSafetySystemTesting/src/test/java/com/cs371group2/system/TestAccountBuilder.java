package com.cs371group2.system;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class is used to build test accounts for testing authenticated endpoints without the need for a Firebase token.
 * It generates a token that can be used to access a restricted subset of concerns flagged as "isTest" being true.
 */
public final class TestAccountBuilder {

    /**
     * The ID the test account is given.
     */
    private final String id;

    /**
     * The email the test account is given.
     */
    private final String email;

    /**
     * The list of facilities that the test account has access to.
     */
    private final List<String> facilities = new LinkedList<String>();

    /**
     * The account's permission level.
     */
    private final AccountPermissions permissions;

    /**
     * Whether or not the account is email verified.
     */
    private final boolean isEmailVerified;

    /**
     * Constructs a new test account builder for building testing tokens.
     * @param id The id the account is given.
     * @param email The email the account is given.
     * @param permissions The account's permission level.
     * @param isEmailVerified Whether the account's email should start as verified or not.
     * @precond id != null and id is not empty
     * @precond email != null and email is not empty
     * @precond permissions != null
     */
    public TestAccountBuilder(String id, String email, AccountPermissions permissions, boolean isEmailVerified) {

        assert id != null;
        assert !id.isEmpty();

        assert email != null;
        assert !email.isEmpty();

        assert permissions != null;

        this.id = id;
        this.email = email;
        this.permissions = permissions;
        this.isEmailVerified = isEmailVerified;
    }

    /**
     * Add a facility to the accounts permissions list. This allows the account to access
     * test concerns assigned to this facility.
     * @param facility
     */
    public void addFacility(String facility) {

        assert facility != null;
        assert !facility.isEmpty();

        this.facilities.add(facility);
    }

    /**
     * Build an access token based on the specified test account data.
     * This token can be used anywhere a Firebase token can be used but has
     * restricted access to concerns flagged with "isTest" as true.
     * @return The access token.
     */
    public String build() {

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("emailVerified", isEmailVerified);
        payload.put("permissions", permissions);
        payload.put("facilities", facilities);
        payload.put("email", email);

        Claims claims = new DefaultClaims(payload);
        claims.setSubject(id.toString());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, ApiKeys.JWS_TEST_SIGNING_KEY)
                .compact();
    }
}
