package com.cs371group2.admin;

import com.cs371group2.client.ClientApi;
import com.cs371group2.concern.Concern;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * This AdminApi class serves as a backend website for the administrator website
 *
 * Created on 2017-02-08.
 */
@Api(name = "client",
        title = "User API",
        version = "v1")
public class AdminApi {

    /** Logger statement for the class */
    private static final Logger logger = Logger.getLogger( ClientApi.class.getName() );

    /**
     * Returns a verified admin the list of concerns as specified by the given ConcernRequest.
     *
     * @return A list of concerns containing the elements specified by the ConcernRequest
     */
    @ApiMethod(name = "getConcerns", path = "/admin/getConcerns")
    public LinkedList<Concern> getConcerns(ConcernRequest request){
        return null;
    }
}
