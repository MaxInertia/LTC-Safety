package com.cs371group2.admin;

import com.cs371group2.DatastoreTest;
import com.cs371group2.TestAccountBuilder;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;
import com.cs371group2.account.AccountPermissions;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernData;
import com.cs371group2.concern.ConcernTest;
import com.cs371group2.facility.Facility;
import com.cs371group2.facility.FacilityDao;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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
        List<Concern> concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, "x5gvMAYGfNcKv74VyHFgr4Ytcge2").build());
    }

    /** Tries to load concerns from an empty database and verifies that a list of size zero is returned */
    @Test
    public void getConcernListFromEmptyDatabaseTest() throws UnauthorizedException, BadRequestException {

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);
        builder.addFacility("OTHER_FACILITY");
        List<Concern> concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, builder.build()).build());
        assert(concerns.size() == 0);
        assertNotNull(concerns);
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
        List<Concern> concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.size() == 1);
    }

    /*** Tests that loading multiple concerns from the database is functioning properly */
    @Test
    public void getMultipleConcernListTest() throws UnauthorizedException, BadRequestException {

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

        List<Concern> concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(5, 0, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.size() == 5);
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

        List<Concern> concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(3, 0, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.size() == 3);
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

        List<Concern> concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(3, 2, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.size() == 3);
    }

    /*** Tests that loading concerns when the account has no facilities returns nothing */
    @Test
    public void getConcernListWithNoFacilityTest() throws UnauthorizedException, BadRequestException {

        ConcernDao dao = new ConcernDao();
        ConcernTest concernTest = new ConcernTest();

        ConcernData concernData;
        Concern firstConcern;

        AdminApi api = new AdminApi();

        TestAccountBuilder builder = new TestAccountBuilder("id", "email", AccountPermissions.ADMIN, true);

        List<Concern> concerns = api.requestConcernList(new ConcernListRequest.TestHook_MutableConcernListRequest(1, 0, builder.build()).build());

        assertNotNull(concerns);
        assert(concerns.size() == 0);
    }

}
