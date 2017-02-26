package com.cs371group2.admin;

import com.cs371group2.account.Account;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.facility.Facility;
import com.google.api.server.spi.config.*;
import com.google.api.server.spi.response.UnauthorizedException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This AdminApi class serves as a backend endpoint to be accessed through the administrative website.
 *
 * Created on 2017-02-08.
 */
@Api(name = "admin",
        title = "Admin API",
        version = "v1")
public class AdminApi {

    /** Logger statement for the class */
    private static final Logger logger = Logger.getLogger( AdminApi.class.getName() );

    /**
     * Requests a list of concerns from the datastore with the request offset and limit. The user submitting
     * the request must have administrative permissions which will be verified for the request.
     *
     * @param request The concern request containing the user's firebase token, along with the requested offset/limit
     * @return A list of concerns loaded from the datastore at the given offset and limit
     * @throws UnauthorizedException
     */
    @ApiMethod(name = "requestConcernList", path = "admin/requestConcernList")
    public List<Concern> requestConcernList(ConcernListRequest request) throws UnauthorizedException {

        logger.log(Level.INFO, "User is requesting a concern list. " + request);

        if(!request.legalRequest()){
            throw new UnauthorizedException("Request was not legal!");
        }

        Account account = request.authenticate();

        ConcernDao dao = new ConcernDao();

        List<Concern> list = dao.load(request.getOffset(), request.getLimit(), account.getLocations() );

        logger.log(Level.INFO, "Concern request was successful.");
        return list;
    }

    /**
     * Requests an individual concern from the database based on the given unique concern id. The user submitting
     * the request must have administrative permissions which will be verified before the request is completed.
     *
     * @param request The concern request containing the user's firebase token, along with the desired concern id
     * @return The concern requested from the database
     * @throws UnauthorizedException If the user is unauthorized or there is an error loading the concern, throw this
     * @precond request != null
     */
    @ApiMethod(name = "requestConcern", path = "admin/requestConcern")
    public Concern requestConcern(ConcernRequest request) throws UnauthorizedException {
        if(request == null){
           throw new UnauthorizedException("The request given was null");
        }

        if(!request.legalRequest()){
            throw new UnauthorizedException("Request was not legal!");
        }

        Account account = request.authenticate();

        Concern loadedConcern = new ConcernDao().load(request.getConcernId());

        if(loadedConcern == null){
            throw new UnauthorizedException("The concern identifier was not found in the database");
        }

        for (Facility location : account.getLocations()){
            if(loadedConcern.getFacility() == location){
                return loadedConcern;
            }
        }

        throw new UnauthorizedException("Account is not linked to the concern's location and cannot be loaded");
    }
}
