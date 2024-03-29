package com.cs371group2.admin;

import com.cs371group2.ValidationResult;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;
import com.cs371group2.client.UpdateConcernStatusResponse;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernStatus;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
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
     * @throws UnauthorizedException If the admin is unauthorized or there is an error loading the concern list
     * @throws BadRequestException If the request contained invalid paging information.
     */
    @ApiMethod(name = "requestConcernList", path = "admin/requestConcernList")
    public ConcernListResponse requestConcernList(ConcernListRequest request) throws UnauthorizedException, BadRequestException {

        ValidationResult result = request.validate();
        if (!result.isValid()){
            logger.log(Level.WARNING, "Admin tried requesting a concern list with invalid data.");
            throw new BadRequestException(result.getErrorMessage());
        }

        Account account = request.authenticate();
        assert account != null;
        logger.log(Level.INFO,account + " is requesting a concern list " + request);

        return new ConcernDao().load(account, request.getOffset(), request.getLimit(), request.isArchived());
    }

    /**
     * Requests an individual concern from the database based on the given unique concern id. The user submitting
     * the request must have administrative permissions which will be verified before the request is completed.
     *
     * @param request The concern request containing the user's firebase token, along with the desired concern id
     * @return The concern requested from the database
     * @throws UnauthorizedException If the admin is unauthorized or there is an error loading the concern
     * @throws BadRequestException If the request or the admin's account contained invalid information
     * @throws NotFoundException Thrown if the requested concern does not exist.
     * @precond request != null
     */
    @ApiMethod(name = "requestConcern", path = "admin/requestConcern")
    public Concern requestConcern(ConcernRequest request)
            throws UnauthorizedException, BadRequestException, NotFoundException {

        ValidationResult result = request.validate();
        if (!result.isValid()){
            logger.log(Level.WARNING, "Admin tried requesting a concern with invalid data.");
            throw new BadRequestException(result.getErrorMessage());
        }

        Account account = request.authenticate();
        assert account != null;
        logger.log(Level.INFO,account + " is requesting a concern " + request);

        Concern loadedConcern = new ConcernDao().load(account, request.getConcernId());
        logger.log(Level.INFO, "Concern " + loadedConcern + " was successfully loaded!");
        return loadedConcern;
    }

    /**
     * Updates the concern status of the concern specified in the request, as long as the user submitting the
     * request has administrative permissions.
     *
     * @param request The updateConcernStatus request containing the concern update, concern type, and firebase token
     * @return The response containing the updated concern
     * @throws UnauthorizedException If the admin is unauthorized or there is an error loading the concern
     * @throws BadRequestException If the request or the admin's account contained invalid information
     * @throws NotFoundException Thrown if the requested concern does not exist.
     * @precond request != null
     * @postcond The concern status has been updated
     */
    @ApiMethod(name = "updateConcernStatus", path = "admin/updateConcernStatus")
    public UpdateConcernStatusResponse updateConcernStatus(UpdateConcernStatusRequest request)
            throws UnauthorizedException, BadRequestException, NotFoundException {

        Concern loadedConcern = requestConcern(request);

        ConcernStatus status = new ConcernStatus(request.getConcernStatus());
        loadedConcern.getStatuses().add(status);
        new ConcernDao().save(loadedConcern);

        logger.log(Level.INFO, "Concern " + loadedConcern + " had its status successfully updated!");

        return new UpdateConcernStatusResponse(loadedConcern.getId(), status);
    }

    @ApiMethod(name = "requestAccount", path = "admin/requestAccount")
    public Account requestAccount(AccountRequest request) throws UnauthorizedException {
        return request.authenticate();
    }

    /**
     * Requests a list of accounts from the datastore with the request offset and limit. The user submitting
     * the request must have administrative permissions which will be verified for the request.
     *
     * @param request The account request containing the user's firebase token, requested offset/limit, and account type
     * @return A list of accounts loaded from the datastore at the given offset and limit
     * @throws UnauthorizedException If the admin is unauthorized or there is an error loading the account list
     * @throws BadRequestException If the request contained invalid paging information or a null permissions.
     */
    @ApiMethod(name = "requestAccountList", path = "admin/requestAccountList")
    public AccountListResponse requestAccountList(AccountListRequest request) throws UnauthorizedException, BadRequestException {
        ValidationResult result = request.validate();
        if (!result.isValid()){
            logger.log(Level.WARNING, "Admin tried requesting an account list with invalid data.");
            throw new BadRequestException(result.getErrorMessage());
        }

        Account account = request.authenticate();
        assert account != null;
        logger.log(Level.INFO,account + " is requesting an account list " + request);

        return new AccountDao().load(account, request);
    }

    /**
     * Updates the archival status of the requested concern. Unarchived concerns become archived and vice-versa.
     * This takes the concern's id, as well as the user's firebase token for verification of credentials.
     *
     * @param request The concern request containing the user's firebase token, along with the desired concern id
     * @return The concern requested from the database
     * @throws UnauthorizedException If the admin is unauthorized or there is an error loading the concern
     * @throws BadRequestException If the request or the admin's account contained invalid information
     * @throws NotFoundException Thrown if the requested concern does not exist.
     * @precond request != null
     */
    @ApiMethod(name = "updateArchiveStatus", path = "admin/updateArchiveStatus")
    public void updateArchiveStatus(ConcernRequest request)
            throws UnauthorizedException, BadRequestException, NotFoundException {

        Concern loadedConcern = requestConcern(request);
        loadedConcern.toggleArchived();
        new ConcernDao().save(loadedConcern);
        logger.log(Level.INFO, "Concern " + loadedConcern + " archive status was successfully updated!");
    }


    /**
     * Updates the permission level of the account associated with the given id. The user submitting the request
     * must hold administrative privileges to update the permissions of other accounts.
     *
     * @param request The update request containing the user's firebase token, along with the desired account id and
     *                the permission level to grant it.
     * @throws UnauthorizedException If the admin is unauthorized to modify the permissions of other accounts.
     * @throws BadRequestException If the request or the admin's account contained invalid information
     * @throws NotFoundException If the given account does not exist in the database.
     */
    @ApiMethod(name = "updateAccountPermission", path = "admin/updateAccountPermission")
    public void updateAccountPermission(UpdateAccountPermissionRequest request)
            throws UnauthorizedException, BadRequestException, NotFoundException {

        ValidationResult result = request.validate();
        if (!result.isValid()){
            logger.log(Level.WARNING, "Admin tried updating an account permission with invalid data.");
            throw new BadRequestException(result.getErrorMessage());
        }

        Account account = request.authenticate();
        assert account != null;
        logger.log(Level.INFO,account + " is updating an account's permissions " + request);

        AccountDao dao = new AccountDao();
        Account toUpdate = dao.load(request.getAccountId());

        if(toUpdate == null)
            throw new NotFoundException("Requested account does not exist within the account database");

        toUpdate.setPermissions(request.getPermissions());
        dao.save(toUpdate);
    }
}
