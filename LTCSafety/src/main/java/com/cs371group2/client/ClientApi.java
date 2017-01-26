package com.cs371group2.client;

import com.cs371group2.ValidationResult;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernData;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Api(name = "client",
        title = "User API",
        version = "v1")
public final class ClientApi {
    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ClientApi.class.getName() );

    private static final String CONCERN_NOT_FOUND_ERROR = "Attempted to retract a concern that could not be found.";

    @ApiMethod(name = "submitConcern", path = "/concern/submit")
    public OwnerToken submitConcern(ConcernData data) throws BadRequestException {
        ValidationResult result = data.validate();
        if (!result.isValid()) {
            LOGGER.log(Level.FINE, "Client tried submitting a concern with invalid data.");
            throw new BadRequestException(result.getErrorMessage());
        }
        Concern concern = new Concern(data);
        new ConcernDao().save(concern);
        LOGGER.log(Level.INFO, "Client successfully submitted concern:\n" + concern.toString());
        return concern.generateOwnerToken();
    }

    @ApiMethod(name = "retractConcern", path = "/concern/retract")
    public void retractConcern(OwnerToken token) throws BadRequestException, NotFoundException {
        ValidationResult result = token.validate();
        if (!result.isValid()) {
            LOGGER.log(Level.FINE, "Client tried retracting a concern with invalid token.");
            throw new BadRequestException(result.getErrorMessage());
        }
        ConcernDao dao = new ConcernDao();
        Concern concern = dao.load(token);
        if (concern == null) {
            LOGGER.log(Level.FINE, "Client tried retracting a concern but concern was not found.");
            throw new NotFoundException(CONCERN_NOT_FOUND_ERROR);
        }
        concern.retract();
        dao.save(concern);
        LOGGER.log(Level.INFO, "Client successfully retracted a concern.");
    }
}
