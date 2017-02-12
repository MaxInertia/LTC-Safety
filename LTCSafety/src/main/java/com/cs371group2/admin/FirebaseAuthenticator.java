package com.cs371group2.admin;

import android.util.Pair;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.AccessControlContext;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

import java.util.Set;

/**
 * This class represents a firebase authenticator object for ensuring that a user has the correct admission levels
 * when accessing functionality that requires firebase authentication.
 *
 * Created on 2017-02-09.
 */
public class FirebaseAuthenticator extends Authenticator{

    /**
     * Authenticates the given firebase token and returns the account associated with it
     *
     * @param token The user's token to be authenticated
     * @return The account associated with the given token\
     * @precond token is valid and non-null
     */
    @Override
    protected Pair<Account, AccessToken> authenticateAccount(String token) throws UnauthorizedException {

        assert(token != null);

        try {
            AccessToken accessToken = verifyToken(token);

            LOGGER.log(Level.WARNING, "Access token id was: " + accessToken.getId());

            AccountDao dao = new AccountDao();
            Account account = dao.load(accessToken.getId());

            return new Pair<Account, AccessToken>(account, accessToken);

        } catch (GeneralSecurityException | IOException e) {
            throw new UnauthorizedException(e);
        }
    }

    /**
     *
     *
     * PRIVATE AUTHENTICATOR HELPERS
     *
     *
     */
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(FirebaseAuthenticator.class.getName());

    private static final String PUBLIC_KEYS_URI = "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";

    private static final String EMAIL_CLAIM = "email";

    private static final String NAME_CLAIM = "user_id";

    private static final String EMAIL_VERIFIED_CLAIM = "email_verified";

    /**
     * Verifies a firebase token in the form of a string and returns a FirebaseToken
     * object representing the token's information
     *
     * @param token The unverified token
     *
     * @return A FirebaseToken object representing the verified token
     *
     * @throws GeneralSecurityException If the token was null, invalid, or had issues verified throw this
     * @throws IOException If there was an issue loading the key as a JSON object
     */
    private AccessToken verifyToken(String token) throws GeneralSecurityException, IOException {
        if (token == null || token.isEmpty()) {
            LOGGER.log(Level.WARNING, "Token receieved was null or empty");
            throw new IOException("Token was null or empty");
        }
        // get public keys
        JsonObject publicKeys = getPublicKeysJson();

        // get json object as map
        // loop map of keys finding one that verifies
        for (Map.Entry<String, JsonElement> entry : publicKeys.entrySet()) {
            try {
                // get public key
                LOGGER.info(entry.getKey());
                PublicKey publicKey = getPublicKey(entry);

                // validate claim set
                Jws<Claims> jws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);

                for ( String key : jws.getBody().keySet()){
                    System.out.println("KEY: " + key + " VALUE: " + jws.getBody().get(key));
                }

                String uuid = jws.getBody().getSubject();
                String email = (String)jws.getBody().get(EMAIL_CLAIM);
                String name = (String)jws.getBody().get(NAME_CLAIM);
                boolean verifiedEmail = (boolean)jws.getBody().get(EMAIL_VERIFIED_CLAIM);
                return new AccessToken(email, uuid, name, verifiedEmail);
            } catch (SignatureException e) {
                // If the key doesn't match the next key should be tried
            } catch (MalformedJwtException e){
                throw new IOException();
            }
        }

        LOGGER.log(Level.WARNING, "Token was not found in map of keys");
        throw new IOException("Did not find token in map of keys");
    }

    /**
     * Returns a PublicKey based on the given map entry
     *
     * @param entry The map entry to find the public key for
     *
     * @return The public key of the entry
     * @throws GeneralSecurityException
     */
    private PublicKey getPublicKey(Map.Entry<String, JsonElement> entry) throws GeneralSecurityException, IOException {
        String publicKeyPem = entry.getValue().getAsString()
                .replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)----", "")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "")
                .trim();

        LOGGER.info(publicKeyPem);

        // generate x509 cert
        InputStream inputStream = new ByteArrayInputStream(entry.getValue().getAsString().getBytes("UTF-8"));
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)cf.generateCertificate(inputStream);

        return cert.getPublicKey();
    }

    /**
     * Gets all the public keys as a Json object
     *
     * @return The public keys as a Json object
     * @throws IOException If parsing fails, throws this exception
     */
    private JsonObject getPublicKeysJson() throws IOException {
        // get public keys
        URI uri = URI.create(PUBLIC_KEYS_URI);
        GenericUrl url = new GenericUrl(uri);
        HttpTransport http = new NetHttpTransport();
        HttpResponse response = http.createRequestFactory().buildGetRequest(url).execute();

        // store json from request
        String json = response.parseAsString();
        // disconnect
        response.disconnect();

        // parse json to object
        return new JsonParser().parse(json).getAsJsonObject();
    }
}
