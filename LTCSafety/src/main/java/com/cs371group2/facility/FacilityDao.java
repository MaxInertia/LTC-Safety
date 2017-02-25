package com.cs371group2.facility;

import com.cs371group2.Dao;

/**
 * Data access object for the saving, loading, and deletion of facilities.
 *
 * Created by on 2017-02-24.
 */
public class FacilityDao extends Dao<Facility> {

    /**
     * Create a new data access object for loading, saving, and deleting facilities.
     */
    public FacilityDao(){ super(Facility.class); }
}
