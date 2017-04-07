package com.cs371group2.admin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.cs371group2.DatastoreTest;
import com.cs371group2.TestAccountBuilder;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.client.UpdateConcernStatusResponse;
import com.cs371group2.concern.*;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import org.junit.Test;

/**
 * Created on 2017-02-09.
 */
public class AdminApiTest extends DatastoreTest {

    /**
     * RequestConcernList Tests
     */

    @Test (expected = UnauthorizedException.class)
    public void getConcernListUnauthorizedTest() throws UnauthorizedException, BadRequestException {
        AdminApi api = new AdminApi();
        ConcernListResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, "x5gvMAYGfNcKv74VyHFgr4Ytcge2", false).build());
    }

    /** Tries to load concerns from an empty database and verifies that a list of size zero is returned */
    @Test
    public void getConcernListFromEmptyDatabaseTest() throws UnauthorizedException, BadRequestException {

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        ConcernListResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, builder.build(), false).build());
        assertNotNull(concerns);
        assert(concerns.getConcernList().size() == 0);
        assert(concerns.getStartIndex() == 0);
        assert(concerns.getEndIndex() == 0);
        assert(concerns.getTotalItemsCount() == 0);
    }

    /*** Tests that loading a single concern from the database in a list is functioning properly */
    @Test
    public void getSingleConcernListTest() throws UnauthorizedException, BadRequestException {

        ConcernDao dao = new ConcernDao();
        ConcernTest concernTest = new ConcernTest();

        ConcernData concernData = concernTest.generateConcernData().build();

        Concern firstConcern = new Concern(concernData, true);
        dao.save(firstConcern);

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        ConcernListResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, builder.build(), false).build());

        assertNotNull(concerns);
        assert(concerns.getConcernList().size() == 1);
        assert(concerns.getStartIndex() == 1);
        assert(concerns.getEndIndex() == 1);
        assert(concerns.getTotalItemsCount() == 1);
    }

    /*** Tests that loading multiple concerns from the database is functioning properly */
    @Test
    public void getMultipleConcernListTest() throws UnauthorizedException, BadRequestException {

        ConcernDao dao = new ConcernDao();
        ConcernTest concernTest = new ConcernTest();

        ConcernData concernData;

        concernData = concernTest.generateConcernData().build();
        Concern firstConcern = new Concern(concernData, true);
        dao.save(firstConcern);

        concernData = concernTest.generateConcernData().build();
        Concern lastConcern = new Concern(concernData, true);
        dao.save(lastConcern);

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        ConcernListResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(5, 0, builder.build(), false).build());

        assertNotNull(concerns);
        assert(concerns.getConcernList().size() == 2);
        assert(concerns.getStartIndex() == 1);
        assert(concerns.getEndIndex() == 2);

        //Test accounts are not included in total concern count
        assert(concerns.getTotalItemsCount() == 2);
    }

    /**
     * Tests that loading multiple concerns from the database with a limit reached is functioning properly
     * @throws UnauthorizedException
     * @throws BadRequestException
     */
    @Test
    public void getConcernListWithLimitTest() throws UnauthorizedException, BadRequestException {

        ConcernDao dao = new ConcernDao();
        ConcernTest concernTest = new ConcernTest();

        ConcernData concernData;
        Concern firstConcern;

        for (int x = 0; x < 5; x++) {
            concernData = concernTest.generateConcernData().build();
            firstConcern = new Concern(concernData, true);
            dao.save(firstConcern);
        }

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        ConcernListResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(3, 0, builder.build(), false).build());

        assertNotNull(concerns);
        assert(concerns.getConcernList().size() == 3);
    }

    /*** Tests that loading multiple concerns from the database with a limit reached is functioning properly */
    @Test
    public void getConcernListWithOffsetTest() throws UnauthorizedException, BadRequestException {

        ConcernDao dao = new ConcernDao();
        ConcernTest concernTest = new ConcernTest();

        ConcernData concernData;
        Concern firstConcern;

        for (int x = 0; x < 5; x++) {
            concernData = concernTest.generateConcernData().build();
            firstConcern = new Concern(concernData, true);
            dao.save(firstConcern);
        }

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        ConcernListResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(3, 2, builder.build(), false).build());

        assertNotNull(concerns);
        assert(concerns.getConcernList().size() == 3);
    }

    /**
     * RequestConcern Tests
     */

    /** Tries to load a concern with an invalid token */
    @Test (expected = UnauthorizedException.class)
    public void getConcernUnauthTokenTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        AdminApi api = new AdminApi();
        Concern concern = api.requestConcern(new ConcernRequest.TestHook_MutableConcernRequest(123, "x5gvMAYGfNcKv74VyHFgr4Ytcge2").build());
    }

    /** Tries to load concern from an empty database and verifies that a not found exception is thrown */
    @Test (expected = UnauthorizedException.class)
    public void getConcernUnverifiedDatabaseTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.UNVERIFIED, true);
        Concern concern = api.requestConcern(new ConcernRequest.TestHook_MutableConcernRequest(123, builder.build() ).build());
    }

    /** Tries to toggle the archive status of a concern from an empty database and verifies that a not found exception is thrown */
    @Test (expected = UnauthorizedException.class)
    public void getConcernUnverifiedEmailTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, false);
        Concern concern = new AdminApi().requestConcern(new ConcernRequest.TestHook_MutableConcernRequest(123, builder.build() ).build());
    }

    /** Tries to load concern from an empty database and verifies that a not found exception is thrown */
    @Test (expected = NotFoundException.class)
    public void getConcernFromEmptyDatabaseTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        Concern concern = api.requestConcern(new ConcernRequest.TestHook_MutableConcernRequest(123, builder.build() ).build());
    }

    /*** Tests that loading a single concern from the database is functioning properly */
    @Test
    public void getConcernTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        ConcernTest concernTest = new ConcernTest();
        ConcernData concernData = concernTest.generateConcernData().build();

        Concern firstConcern = new Concern(concernData, true);
        new ConcernDao().save(firstConcern);

        TestAccountBuilder accountBuilder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        String accountToken = accountBuilder.build();

        ConcernRequest request = new ConcernRequest.TestHook_MutableConcernRequest(firstConcern.getId(), accountToken).build();
        Concern concern = new AdminApi().requestConcern(request);

        assertNotNull(concern);
    }

    /**
     * updateConcernStatus Tests
     */

    /** Tries to update a concern status with a null type */
    @Test (expected = BadRequestException.class)
    public void updateConcernStatusNullTypeTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        AdminApi api = new AdminApi();

        Concern toModify = new Concern(new ConcernTest().generateConcernData().build());
        new ConcernDao().save(toModify);

        TestAccountBuilder account = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        UpdateConcernStatusResponse response =
                api.updateConcernStatus(new UpdateConcernStatusRequest.TestHook_MutableUpdateConcernStatusRequest(
                        null,toModify.getId(), account.build()).build());
    }

    /** Tries to update a concern status with a null user token */
    @Test (expected = BadRequestException.class)
    public void updateConcernStatusNullTokenTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        AdminApi api = new AdminApi();

        Concern toModify = new Concern(new ConcernTest().generateConcernData().build());
        new ConcernDao().save(toModify);

        UpdateConcernStatusResponse response =
                api.updateConcernStatus(new UpdateConcernStatusRequest.TestHook_MutableUpdateConcernStatusRequest(
                        ConcernStatusType.SEEN,toModify.getId(), null).build());
    }

    /** Tries to update a concern status without proper access levels*/
    @Test (expected = UnauthorizedException.class)
    public void updateConcernStatusUnauthTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        AdminApi api = new AdminApi();

        Concern toModify = new Concern(new ConcernTest().generateConcernData().build());
        new ConcernDao().save(toModify);

        TestAccountBuilder account = new TestAccountBuilder("id", "email", AccountPermissions.DENIED, true);

        UpdateConcernStatusResponse response =
                api.updateConcernStatus(new UpdateConcernStatusRequest.TestHook_MutableUpdateConcernStatusRequest
                        (ConcernStatusType.SEEN,toModify.getId(), account.build()).build());
    }

    /** Tries to update the concern status of a non-existent concern*/
    @Test (expected = NotFoundException.class)
    public void updateConcernStatusNotFoundTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        AdminApi api = new AdminApi();

        TestAccountBuilder account = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        UpdateConcernStatusResponse response =
                api.updateConcernStatus(new UpdateConcernStatusRequest.TestHook_MutableUpdateConcernStatusRequest
                        (ConcernStatusType.SEEN, 123 , account.build()).build());
    }

    /** Tries to update a concern status validly */
    @Test
    public void updateConcernStatusTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        AdminApi api = new AdminApi();

        Concern toModify = new Concern(new ConcernTest().generateConcernData().build(), true);

        new ConcernDao().save(toModify);

        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        UpdateConcernStatusResponse response =
                api.updateConcernStatus(new UpdateConcernStatusRequest.TestHook_MutableUpdateConcernStatusRequest
                        (ConcernStatusType.RESOLVED,toModify.getId(), account.build()).build());

        assertNotNull(response);
        assert(response.getStatus().getType() == ConcernStatusType.RESOLVED);
        assert(response.getConcernId() == toModify.getId());

        boolean containsStatus = false;

        for (ConcernStatus status : toModify.getStatuses()){
            if(status.getType() == ConcernStatusType.RESOLVED){
                containsStatus = true;
            }
        }

        assert(containsStatus);
    }

    /****************************
     * requestAccountList Tests *
     ****************************/

    /** Tries to request an account list with a null permission */
    @Test (expected = BadRequestException.class)
    public void requestAccountListNullPermissionTest() throws UnauthorizedException, BadRequestException {
        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        AccountListRequest.TestHook_MutableAccountListRequest request =
                new AccountListRequest.TestHook_MutableAccountListRequest(1, 0,
                        account.build(),
                        null);

        new AdminApi().requestAccountList(request.build());
    }

    /** Tries to request an account list with a null token */
    @Test (expected = BadRequestException.class)
    public void requestAccountListNullTokenTest() throws UnauthorizedException, BadRequestException {

        AccountListRequest.TestHook_MutableAccountListRequest request =
                new AccountListRequest.TestHook_MutableAccountListRequest(1, 0,
                        null,
                        AccountPermissions.ADMIN);

        new AdminApi().requestAccountList(request.build());
    }

    /** Tries to request an account list with an empty token */
    @Test (expected = BadRequestException.class)
    public void requestAccountListEmptyTokenTest() throws UnauthorizedException, BadRequestException {

        AccountListRequest.TestHook_MutableAccountListRequest request =
                new AccountListRequest.TestHook_MutableAccountListRequest(1, 0,
                        "",
                        AccountPermissions.ADMIN);

        new AdminApi().requestAccountList(request.build());
    }

    /** Tries to request an account list with an invalid limit */
    @Test (expected = BadRequestException.class)
    public void requestAccountListInvalidLimitTest() throws UnauthorizedException, BadRequestException {

        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        AccountListRequest.TestHook_MutableAccountListRequest request =
                new AccountListRequest.TestHook_MutableAccountListRequest(0, 0,
                        account.build(),
                        AccountPermissions.ADMIN);

        new AdminApi().requestAccountList(request.build());
    }

    /** Tries to request an account list with an invalid token */
    @Test (expected = BadRequestException.class)
    public void requestAccountListInvalidOffsetTest() throws UnauthorizedException, BadRequestException {

        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        AccountListRequest.TestHook_MutableAccountListRequest request =
                new AccountListRequest.TestHook_MutableAccountListRequest(1, -1,
                        account.build(),
                        AccountPermissions.ADMIN);

        new AdminApi().requestAccountList(request.build());
    }


    /** Tries to request an account list validly */
    @Test
    public void requestAccountListTest() throws UnauthorizedException, BadRequestException {
        new AccountDao().save(new Account("Administrator","Administrator", AccountPermissions.ADMIN, true));
        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        AccountListRequest.TestHook_MutableAccountListRequest request =
                new AccountListRequest.TestHook_MutableAccountListRequest(1, 0,
                                                                            account.build(),
                                                                            AccountPermissions.ADMIN);


        AccountListResponse response = new AdminApi().requestAccountList(request.build());
        assertNotNull( response.getItems() );
        assert(response.getTotalItemsCount() == 1);
        assert(response.getStartIndex() == 1);
        assert(response.getEndIndex() == 1);
    }

    /** Tries to request an account list validly containing multiple accounts*/
    @Test
    public void requestAccountListMultipleTest() throws UnauthorizedException, BadRequestException {
        AccountDao dao = new AccountDao();
        dao.save(new Account("Administrator","Administrator", AccountPermissions.ADMIN, true));
        dao.save(new Account("Administrator2","Administrator2", AccountPermissions.ADMIN, true));
        dao.save(new Account("Administrator3","Administrator3", AccountPermissions.ADMIN, true));

        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        AccountListRequest.TestHook_MutableAccountListRequest request =
                new AccountListRequest.TestHook_MutableAccountListRequest(3, 0,
                        account.build(),
                        AccountPermissions.ADMIN);


        AccountListResponse response = new AdminApi().requestAccountList(request.build());
        assertNotNull( response.getItems() );
        assert(response.getTotalItemsCount() == 3);
        assert(response.getStartIndex() == 1);
        assert(response.getEndIndex() == 3);
    }

    /** Tries to request an account list validly from an empty database */
    @Test
    public void requestAccountListEmptyTest() throws UnauthorizedException, BadRequestException {

        TestAccountBuilder account = new TestAccountBuilder("test admin", "email", AccountPermissions.ADMIN, true);

        AccountListRequest.TestHook_MutableAccountListRequest request =
                new AccountListRequest.TestHook_MutableAccountListRequest(1, 0,
                        account.build(),
                        AccountPermissions.ADMIN);


        AccountListResponse response = new AdminApi().requestAccountList(request.build());

        assertNotNull( response.getItems() );
        assert(response.getTotalItemsCount() == 0);
        assert(response.getStartIndex() == 0);
        assert(response.getEndIndex() == 0);
    }

    /****************************
     * toggleArchived Tests *
     ****************************/

    /** Tries to toggle the archive status of a concern with an invalid token */
    @Test (expected = UnauthorizedException.class)
    public void toggleArchivedUnauthTokenTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        new AdminApi().updateArchiveStatus(new ConcernRequest.TestHook_MutableConcernRequest(123, "x5gvMAYGfNcKv74VyHFgr4Ytcge2").build());
    }

    /** Tries to toggle the archive status of a concern from an empty database and verifies that a not found exception is thrown */
    @Test (expected = UnauthorizedException.class)
    public void toggleArchivedUnverifiedTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.UNVERIFIED, true);
        new AdminApi().updateArchiveStatus(new ConcernRequest.TestHook_MutableConcernRequest(123, builder.build() ).build());
    }

    /** Tries to toggle the archive status of a concern from an empty database and verifies that a not found exception is thrown */
    @Test (expected = UnauthorizedException.class)
    public void toggleArchivedUnverifiedEmailTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, false);
        new AdminApi().updateArchiveStatus(new ConcernRequest.TestHook_MutableConcernRequest(123, builder.build() ).build());
    }

    /** Tries to toggle the archive status of a concern from an empty database and verifies that a not found exception is thrown */
    @Test (expected = NotFoundException.class)
    public void toggleArchivedFromEmptyDatabaseTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        new AdminApi().updateArchiveStatus(new ConcernRequest.TestHook_MutableConcernRequest(123, builder.build() ).build());
    }

    /** Tries to toggle the archive status of a concern from the database is functioning properly */
    @Test
    public void toggleArchivedTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        ConcernTest concernTest = new ConcernTest();
        ConcernData concernData = concernTest.generateConcernData().build();

        Concern firstConcern = new Concern(concernData, true);
        new ConcernDao().save(firstConcern);

        TestAccountBuilder accountBuilder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        String accountToken = accountBuilder.build();

        ConcernRequest request = new ConcernRequest.TestHook_MutableConcernRequest(firstConcern.getId(), accountToken).build();
        Concern concern = new AdminApi().requestConcern(request);

        assert(!concern.isArchived());
        new AdminApi().updateArchiveStatus(request);
        assert(concern.isArchived());
        new AdminApi().updateArchiveStatus(request);
        assert(!concern.isArchived());
    }

    /*********************************
     * updateAccountPermission Tests *
     *********************************/

    /** Tests updating the permissions of an account with a null id */
    @Test (expected = BadRequestException.class)
    public void updateAccountPermissionNullIdTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);
        new AccountDao().save(testAccount);

        TestAccountBuilder accountBuilder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        AccountPermissions.DENIED,
                        "",
                        accountBuilder.build()
                ).build();

        new AdminApi().updateAccountPermission(request);
    }

    /** Tests updating the permissions of an account with an empty id */
    @Test (expected = BadRequestException.class)
    public void updateAccountPermissionEmptyIdTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);
        new AccountDao().save(testAccount);

        TestAccountBuilder accountBuilder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        AccountPermissions.DENIED,
                        null,
                        accountBuilder.build()
                ).build();

        new AdminApi().updateAccountPermission(request);
    }

    /** Tests updating the permissions of an account with a null permission */
    @Test (expected = BadRequestException.class)
    public void updateAccountPermissionNullPermissionTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);
        new AccountDao().save(testAccount);

        TestAccountBuilder accountBuilder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        null,
                        testAccount.getId(),
                        accountBuilder.build()
                ).build();

        new AdminApi().updateAccountPermission(request);
    }

    /** Tests updating the permissions of an account with a null access token*/
    @Test (expected = BadRequestException.class)
    public void updateAccountPermissionNullTokenTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);
        new AccountDao().save(testAccount);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        AccountPermissions.DENIED,
                        testAccount.getId(),
                        null
                ).build();

        new AdminApi().updateAccountPermission(request);
    }

    /** Tests updating the permissions of an account with an empty access token*/
    @Test (expected = BadRequestException.class)
    public void updateAccountPermissionEmptyTokenTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);
        new AccountDao().save(testAccount);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        AccountPermissions.DENIED,
                        testAccount.getId(),
                        ""
                ).build();

        new AdminApi().updateAccountPermission(request);
    }

    /** Tests updating the permissions of an account with non-admin rights */
    @Test (expected = UnauthorizedException.class)
    public void updateAccountPermissionInvalidPermissionTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);
        new AccountDao().save(testAccount);

        TestAccountBuilder accountBuilder = new TestAccountBuilder("id", "email", AccountPermissions.UNVERIFIED, true);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        AccountPermissions.DENIED,
                        testAccount.getId(),
                        accountBuilder.build()
                ).build();

        new AdminApi().updateAccountPermission(request);
    }

    /** Tests updating the permissions of an account that does not exist in the database */
    @Test (expected = NotFoundException.class)
    public void updateAccountPermissionNonExistentAccountTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);

        TestAccountBuilder accountBuilder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        AccountPermissions.DENIED,
                        testAccount.getId(),
                        accountBuilder.build()
                ).build();

        new AdminApi().updateAccountPermission(request);
    }

    /** Tests updating the permissions of an account with an invalid access token */
    @Test (expected = UnauthorizedException.class)
    public void updateAccountPermissionInvalidTokenTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        AccountPermissions.DENIED,
                        testAccount.getId(),
                        "this is an invalid token :)"
                ).build();

        new AdminApi().updateAccountPermission(request);
    }

    /** Tests updating the permissions of an account validly */
    @Test
    public void updateAccountPermissionTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        Account testAccount = new Account("TESTING","TESTING",AccountPermissions.UNVERIFIED, true);
        new AccountDao().save(testAccount);

        TestAccountBuilder accountBuilder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        UpdateAccountPermissionRequest request =
                new UpdateAccountPermissionRequest.TestHook_MutableUpdateAccountPermissionRequest(
                        AccountPermissions.DENIED,
                        testAccount.getId(),
                        accountBuilder.build()
                ).build();

        new AdminApi().updateAccountPermission(request);

        testAccount = new AccountDao().load(testAccount.getId());

        assert(testAccount.getPermissions() == AccountPermissions.DENIED);

    }
}