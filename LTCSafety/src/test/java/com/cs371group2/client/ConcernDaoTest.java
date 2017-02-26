package com.cs371group2.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.cs371group2.DatastoreTest;
import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernData;
import com.cs371group2.concern.ConcernTest;
import com.cs371group2.facility.Facility;
import com.cs371group2.facility.FacilityDao;
import com.googlecode.objectify.Key;

import java.util.HashSet;
import java.util.List;
import org.junit.Test;

/**
 * This class is used to test the Dao class and the ConcernDao class. This class tests the base data
 * access object class due to it being abstract making it difficult to test directly. As a result,
 * load, save, and delete are tested despite not being overridden by the concern data access
 * object.
 *
 * Created on 2017-01-22.
 */
public class ConcernDaoTest extends DatastoreTest {

    /**
     * Assert that the two concerns are equal based on the test parameters for the data access
     * object testing.
     *
     * @param concern The originally created concern that the test concern should be compared to.
     * @param loadedConcern The test concern to check for correctness.
     */
    private void assertConcerns(Concern concern, Concern loadedConcern) {

        ConcernData data = concern.getData();
        ConcernData loadedData = loadedConcern.getData();
        assertEquals(loadedData.getActionsTaken(), data.getActionsTaken());
        assertEquals(loadedData.getConcernNature(), data.getConcernNature());

        assertEquals(loadedData.getReporter().getName(), data.getReporter().getName());
        assertEquals(loadedData.getReporter().getPhoneNumber(),
                data.getReporter().getPhoneNumber());
        assertEquals(loadedData.getReporter().getEmail(), data.getReporter().getEmail());

        assertEquals(loadedData.getLocation().getFacilityName(),
                data.getLocation().getFacilityName());
        assertEquals(loadedData.getLocation().getRoomName(), data.getLocation().getRoomName());

        assertNotNull(loadedConcern.getSubmissionDate());
    }

    /**
     * Test for testing the saving, loading, and deleting of entities. This creates an entity, saves
     * it, loads it using multiple methods, then deletes it.
     */
    @Test
    public void saveLoadDeleteTest() throws Exception {

        ConcernDao dao = new ConcernDao();

        // Save the object
        ConcernData data = new ConcernTest().generateConcernData().build();
        Concern concern = new Concern(data);
        Key<Concern> key = dao.save(concern);

        // Load it using its key
        Concern keyLoadedConcern = dao.load(key);
        assertConcerns(concern, keyLoadedConcern);

        // Load it using its id
        Concern idLoadedConcern = dao.load(key.getId());
        assertConcerns(concern, idLoadedConcern);

        // Load it using its owner token
        Concern tokenLoadedConcern = dao.load(concern.generateOwnerToken());
        assertConcerns(concern, tokenLoadedConcern);

        // Delete it synchronously
        dao.delete(concern).now();

        // Assert its been deleted
        Concern deletedConcern = dao.load(concern.generateOwnerToken());
        assertNull(deletedConcern);
    }

    /**
     * Test for testing the loading a list of concerns using an offset and limits, ensuring that they are indeed
     * being loaded based on date
     * @throws Exception
     */
    @Test
    public void LoadListTest() throws Exception {
        ConcernDao dao = new ConcernDao();
        ConcernTest concernTest = new ConcernTest();

        ConcernData concernData = concernTest.generateConcernData().build();

        Concern firstConcern = new Concern(concernData);
        dao.save(firstConcern);

        List<Concern> concernList = dao.load(0, 1, null);

        assertEquals(firstConcern, concernList.get(0));

        concernData = concernTest.generateConcernData().build();
        Concern secondConcern = new Concern(concernData);
        dao.save(secondConcern);

        concernList = dao.load(1, 1, null);

        assertEquals(secondConcern, concernList.get(0));
    }

    @Test (expected = AssertionError.class)
    public void LoadInvalidOffsetTest() throws Exception{
        ConcernDao dao = new ConcernDao();
        List<Concern> concernList;
        concernList = dao.load(-1, 5, null);
    }

    @Test (expected = AssertionError.class)
    public void LoadInvalidLimitTest() throws Exception{
        ConcernDao dao = new ConcernDao();
        List<Concern> concernList;
        concernList = dao.load(0, -1, null);
    }

    /**
     * This test saves and loads a concern from the "Other" facility in the form of a list
     */
    @Test
    public void LoadConcernsFromOther(){

        ConcernDao dao = new ConcernDao();
        ConcernTest concernTest = new ConcernTest();
        ConcernData concernData = concernTest.generateConcernData().build();

        Concern firstConcern = new Concern(concernData);
        dao.save(firstConcern);

        HashSet<Facility> testSet = new HashSet<Facility>();
        testSet.add(new FacilityDao().load("Other"));

        List<Concern> concerns = dao.load(0, 1, testSet);

        assertNotNull(concerns);
        assert(concerns.size() > 0);
    }
}