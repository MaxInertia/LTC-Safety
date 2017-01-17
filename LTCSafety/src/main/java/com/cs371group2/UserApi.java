package com.cs371group2;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;

/**
 * Created by allankerr on 2017-01-17.
 */
@Api(name = "userApi", title = "User API", version = "v1")
public class UserApi {

    @ApiMethod(name = "submitConcern", path = "/concern/submit")
    public OwnerToken submitConcern(ConcernInformation information) {
        return null;
    }

    @ApiMethod(name = "retractConcern", path = "/concern/retract")
    public void retractConcern(OwnerToken token) throws UnauthorizedException {

    }
}
