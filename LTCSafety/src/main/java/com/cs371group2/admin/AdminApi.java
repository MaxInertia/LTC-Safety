package com.cs371group2.admin;

import com.cs371group2.Dao;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.client.ClientApi;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.google.api.server.spi.config.*;
import com.google.api.server.spi.response.UnauthorizedException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This AdminApi class serves as a backend website for the administrator website
 *
 * Created on 2017-02-08.
 */
@Api(name = "admin",
        title = "Admin API",
        version = "v1",
        issuers = {
        @ApiIssuer(
                name = "firebase",
                issuer = "https://securetoken.google.com/ltc-safety",
                jwksUri = "https://www.googleapis.com/service_accounts/v1/metadata/x509/securetoken@system.gserviceaccount.com")
        },
        issuerAudiences = {
                @ApiIssuerAudience(name = "firebase", audiences = "ltc-safety")
        })
public class AdminApi {

    /** Logger statement for the class */
    private static final Logger logger = Logger.getLogger( AdminApi.class.getName() );

    /** This method is only used for testing access the API through gapi */
    @ApiMethod(name = "requestConcernList", path = "admin/requestConcernList")
    public List<Concern> requestConcernList(ConcernRequest request) throws UnauthorizedException {

        logger.log(Level.INFO, "API has been accessed! " + request);

        request.authenticate();

        ConcernDao dao = new ConcernDao();

        List<Concern> list = dao.load(request.getOffset(), request.getLimit());
        return list;
    }
}
