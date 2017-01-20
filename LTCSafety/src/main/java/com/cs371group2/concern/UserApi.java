package com.cs371group2.concern;

import com.cs371group2.ValidationResult;
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
        ValidationResult result = data.validate();
        if (!result.isValid()) {
            throw new BadRequestException(result.getErrorMessage());
        }
        Concern concern = new Concern(data);
        new ConcernDao().save(concern);
        return concern.generateOwnerToken();
    }

    @ApiMethod(name = "retractConcern", path = "/concern/retract")
    public void retractConcern(OwnerToken token) throws UnauthorizedException, NotFoundException {

    }

    /**
     * The validatable interface is used to validate objects passed to API endpoints from the user or
     * administrator APIs. This is used to check if all required values are present and return an
     * appropriate error message if data is missing.
     *
     * Created on 2017-01-19.
     */
    public static interface Validatable {

        /**
         * Validate object by running its a block of code to determine if all required values are
         * present.
         *
         * @return The result of the validation with result.isValid() being true if the validation
         * passed and result.isValid() being false if it failed. If the validation failed then
         * result.getErrorMessage() will contain a message specifying what went wrong.
         */
        public ValidationResult validate();
    }
}
