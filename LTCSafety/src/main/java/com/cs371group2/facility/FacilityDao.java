package com.cs371group2.facility;

import com.cs371group2.Dao;
import com.cs371group2.concern.ConcernDao;

import java.util.logging.Logger;

/**
 * Data access object for the saving, loading, and deletion of facilities.
 *
 * Created by on 2017-02-24.
 */
public class FacilityDao extends Dao<Facility> {

    private static final Logger logger = Logger.getLogger(ConcernDao.class.getName());

    /**
     * Create a new data access object for loading, saving, and deleting facilities.
     */
    public FacilityDao(){ super(Facility.class); }

    /**
     * Loads the facility with the given identifier from the datastore. If the
     * facility does not exist in the database, it return the Other facility.
     *
     * @param identifier The identifier used to uniquely identify the facility in the database.
     * @return The requested facility if available, the Other facility otherwise
     */
    public Facility load(String identifier){
        assert(identifier != null);

        Facility toLoad = super.load(identifier);

        if( toLoad == null ){
            toLoad = super.load("Other");
        }

        return toLoad;
    }
}
