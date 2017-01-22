package com.cs371group2.concern;

import com.cs371group2.ValidationResult;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;

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
    public void retractConcern(OwnerToken token) throws BadRequestException, NotFoundException {
        ValidationResult result = token.validate();
        if (!result.isValid()) {
            throw new BadRequestException(result.getErrorMessage());
        }
        ConcernDao dao = new ConcernDao();
        Concern concern = dao.load(token);
        if (concern == null) {
            throw new NotFoundException("");
        }
        concern.retract();
        dao.save(concern);
    }
}
