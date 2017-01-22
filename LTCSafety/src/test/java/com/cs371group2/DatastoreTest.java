package com.cs371group2;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;

/**
 * This class is used as the base class for any classes that unit tests that may interact with the
 * datastore. This automatically sets up a local datastore for each test and tears it down
 * afterwards.
 *
 * Created on 2017-01-22.
 */
public abstract class DatastoreTest {

    /**
     * The local test helper for setting up and tearing down the local database.
     */
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    private Closeable session;

    /**
     * Setup the environment for local testing of the datastore by creating an empty datastore to
     * perform the tests with.
     */
    @Before
    public void setUp() throws Exception {
        this.session = ObjectifyService.begin();
        this.helper.setUp();
        new InitContextListener().contextInitialized(null);
    }

    /**
     * Destroy the local test datastore cleanup for the next test.
     */
    @After
    public void tearDown() throws Exception {
        AsyncCacheFilter.complete();
        this.session.close();
        this.helper.tearDown();
    }
}
