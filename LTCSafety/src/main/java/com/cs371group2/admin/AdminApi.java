package com.cs371group2.admin;

import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.facility.Facility;
import com.cs371group2.facility.FacilityDao;
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
    public List<Concern> requestConcernList(ConcernRequest request) throws UnauthorizedException {

        logger.log(Level.INFO, "User is requesting a concern list. " + request);

        if(!request.legalRequest()){
            throw new UnauthorizedException("Request was not legal!");
        }

        new FacilityDao().save(new Facility("Other"));
        request.authenticate();

        ConcernDao dao = new ConcernDao();

        List<Concern> list = dao.load(request);

        logger.log(Level.INFO, "Concern request was successful.");
        return list;
    }
}
