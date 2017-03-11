package com.cs371group2.admin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.cs371group2.DatastoreTest;
import com.cs371group2.TestAccountBuilder;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.client.UpdateConcernStatusResponse;
import com.cs371group2.concern.*;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import java.util.List;
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
        ConcernListRequestResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, "x5gvMAYGfNcKv74VyHFgr4Ytcge2").build());
    }

    /** Tries to load concerns from an empty database and verifies that a list of size zero is returned */
    @Test
    public void getConcernListFromEmptyDatabaseTest() throws UnauthorizedException, BadRequestException {

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        ConcernListRequestResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, builder.build()).build());
        assertNotNull(concerns);
        assert(concerns.getConcernCount() == 0);
        assertNull(concerns.getFirstConcern());
        assertNull(concerns.getLastConcern());
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
        builder.addFacility("OTHER_FACILITY");

        ConcernListRequestResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.getConcernCount() == 1);
        assert(concerns.getFirstConcern() == firstConcern);
        assert(concerns.getLastConcern() == firstConcern);
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
        builder.addFacility("OTHER_FACILITY");

        ConcernListRequestResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(5, 0, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.getConcernCount() == 2);

        //Since these are loaded by date, the ordering will be backwards in relation to this test
        assert(concerns.getFirstConcern() == lastConcern);
        assert(concerns.getLastConcern() == firstConcern);
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
        builder.addFacility("OTHER_FACILITY");

        ConcernListRequestResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(3, 0, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.getConcernCount() == 3);
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
        builder.addFacility("OTHER_FACILITY");

        ConcernListRequestResponse concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(3, 2, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.getConcernCount() == 3);
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
    @Test (expected = NotFoundException.class)
    public void getConcernFromEmptyDatabaseTest() throws UnauthorizedException, BadRequestException, NotFoundException {

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
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
    @Test (expected = AssertionError.class)
    public void updateConcernStatusNullTypeTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        AdminApi api = new AdminApi();

        Concern toModify = new Concern(new ConcernTest().generateConcernData().build());
        new ConcernDao().save(toModify);

        TestAccountBuilder account = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        UpdateConcernStatusResponse response =
                api.updateConcernStatus(new UpdateConcernStatusRequest
                        (null,toModify.getId(), account.build()));
    }

    /** Tries to update a concern status with a null user token */
    @Test (expected = BadRequestException.class)
    public void updateConcernStatusNullTokenTest() throws UnauthorizedException, BadRequestException, NotFoundException {
        AdminApi api = new AdminApi();

        Concern toModify = new Concern(new ConcernTest().generateConcernData().build());
        new ConcernDao().save(toModify);

        UpdateConcernStatusResponse response =
                api.updateConcernStatus(new UpdateConcernStatusRequest
                        (ConcernStatusType.SEEN,toModify.getId(), null));
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
}
