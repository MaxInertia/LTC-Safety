package com.cs371group2.concern;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.googlecode.objectify.NotFoundException;

@Api(name = "userApi",
        canonicalName = "User API",
        title = "User API",
        version = "v1")
public final class UserApi {

    @ApiMethod(name = "submitConcern", path = "/concern/submit")
    public OwnerToken submitConcern(ConcernData data) throws BadRequestException {
        return null;
    }

    @ApiMethod(name = "retractConcern", path = "/concern/retract")
    public void retractConcern(OwnerToken token) throws UnauthorizedException, NotFoundException {

    }
}
