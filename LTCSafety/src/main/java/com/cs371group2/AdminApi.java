package com.cs371group2;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

/**
 * Created by Brandon on 2017-01-30.
 */
@Api(name = "admin",
        title = "Admin API",
        version = "v1")
public class AdminApi {


    @ApiMethod(name = "requestAccess", path = "/admin/requestAccess")
    public void requestAccess(User user){
        // use user object's id as the key
        // create an AccountDao and account entity using the user id as the key
        // The dao should automatically create a user entity if it doesn't exist
        // The account entity should have a string identifier and a boolean for isVerified
    }

    @ApiMethod(name = "grantAccess", path = "/admin/grantAccess")
    public void grantAccess(){}
}
