package com.cs371group2.facility;

import com.cs371group2.DatastoreTest;
import org.junit.Test;

/**
 * Created by Brandon on 2017-02-24.
 */
public class FacilityDaoTest extends DatastoreTest {

    //Test ID for
    private String OTHER_FACILITY = "OTHER_FACILITY";

    /**
     * Tests loading the Other entry from the database, which should always be present
     */
    @Test
    public void loadOtherTest(){
        FacilityDao dao = new FacilityDao();
        Facility other = dao.load(OTHER_FACILITY);
        assert (other.getId() == OTHER_FACILITY);
    }

    /**
     * Tests loading a random id that is not associated with any facilities,
     * in which case the Other class will be returned
     */
    @Test
    public void loadRandomTest(){
        FacilityDao dao = new FacilityDao();
        Facility other = dao.load("asdfRandom");
        assert (other.getId() == OTHER_FACILITY);
    }

    /**
     * Tests saving a new facility to the database then loading it and ensuring it is valid.
     */
    @Test
    public void saveLoadTest(){

        FacilityDao dao = new FacilityDao();

        Facility testFacility = new Facility("Test Facility");
        dao.save( testFacility );

        Facility loadedFacility = dao.load("Test Facility");
        assert (testFacility == loadedFacility);
    }

    /**
     * Tests with a null id for the facility constructor
     * @throws AssertionError
     */
    @Test(expected = AssertionError.class)
    public void NullIdTest() throws Exception{
        Facility facility = new Facility(null);
    }

    /**
     * Tests with an empty id for the facility constructor
     * @throws AssertionError
     */
    @Test(expected = AssertionError.class)
    public void EmptyIdTest() throws Exception{
        Facility facility = new Facility("");
    }

    @Test
    public void FacilityEqualTest(){
        Facility facilityA = new Facility("Facility A");
        Facility facilityB = facilityA;
        assert(facilityA == facilityB);
    }
}
