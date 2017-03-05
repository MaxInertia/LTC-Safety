package com.cs371group2.client;

import com.cs371group2.ValidationResult;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernData;
import com.cs371group2.concern.ConcernStatus;
import com.cs371group2.concern.OwnerTokenListWrapper;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Api(name = "client",
        title = "User API",
        version = "v1")
public final class ClientApi {

    private static final Logger logger = Logger.getLogger( ClientApi.class.getName() );

    private static final String CONCERN_NOT_FOUND_ERROR = "Attempted to retract a concern that could not be found.";

    @ApiMethod(name = "submitTestConcern", path = "/test/concern/submit")
    public SubmitConcernResponse submitTestConcern(ConcernData data, @Named("isTest") Boolean isTest) throws BadRequestException {

        if (isTest == null) {
            isTest = false;
        }
        ValidationResult result = data.validate();
        if (!result.isValid()) {
            logger.log(Level.WARNING, "Client tried submitting a concern with invalid data.");
            throw new BadRequestException(result.getErrorMessage());
        }

        Concern concern = new Concern(data, isTest);
        new ConcernDao().save(concern);

        logger.log(Level.INFO, "Client successfully submitted concern:\n" + concern.toString());
        return new SubmitConcernResponse(concern);
    }


    @ApiMethod(name = "submitConcern", path = "/concern/submit")
    public SubmitConcernResponse submitConcern(ConcernData data) throws BadRequestException {
        return this.submitTestConcern(data, false);
    }

    @ApiMethod(name = "fetchConcerns", path = "/concern/fetchConcerns")
    public LinkedList<Concern> fetchConcerns(OwnerTokenListWrapper tokens) throws  BadRequestException, NotFoundException, ConflictException {

        LinkedList<Concern> returnList = new LinkedList<Concern>();

        for(OwnerToken curToken : tokens.getTokens()){

            ValidationResult result = curToken.validate();

            if (!result.isValid()) {
                logger.log(Level.WARNING, "Client tried retrieving a concern with invalid token.");
                throw new BadRequestException(result.getErrorMessage());
            }
            ConcernDao dao = new ConcernDao();
            Concern concern = dao.load(curToken);
            if (concern == null) {
                logger.log(Level.WARNING, "Client tried retrieving a concern but concern was not found.");
                throw new NotFoundException(CONCERN_NOT_FOUND_ERROR);
            }
            returnList.add(concern);
        }
        logger.log(Level.INFO, "Client successfully fetched a list of concerns:\n" + returnList.toString());
        return returnList;
    }

    @ApiMethod(name = "retractConcern", path = "/concern/retract")
    public UpdateConcernStatusResponse retractConcern(OwnerToken token) throws BadRequestException, NotFoundException, ConflictException {

        ValidationResult result = token.validate();
        if (!result.isValid()) {
            logger.log(Level.WARNING, "Client tried retracting a concern with invalid token.");
            throw new BadRequestException(result.getErrorMessage());
        }

        ConcernDao dao = new ConcernDao();
        Concern concern = dao.load(token);
        if (concern == null) {
            logger.log(Level.WARNING, "Client tried retracting a concern but concern was not found.");
            throw new NotFoundException(CONCERN_NOT_FOUND_ERROR);
        }

        if (!concern.isRetracted()) {
            ConcernStatus status = concern.retract();
            dao.save(concern);

            logger.log(Level.INFO, "Client successfully retracted a concern.");
            return new UpdateConcernStatusResponse(concern.getId(), status);

        } else {
            logger.log(Level.WARNING, "Attempted to retract a concern that has already been retracted.");
            throw new ConflictException("Attempted to retract a concern that has already been retracted.");
        }
    }
}
