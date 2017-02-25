package com.cs371group2.facility;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * This class represents a care home facility in the system. It will be stored in the database and will be used
 * in keeping track of the administrators and concerns assigned to their respective facilities.
 *
 * It is immutable from the moment it is created, and is only created through the administrative API.
 *
 * Created by on 2017-02-24.
 */
@Entity
public class Facility {



    /** This string identifier represents the unique ID of the facility. */
    @Id
    private String id;

    /**
     * Creates a new facility to be stored in the database with the given id as its
     * @param facilityId The id for the newly created facility
     * @precond facilityId must non-null and non-empty
     */
    public Facility(String facilityId){
        assert( facilityId != null );
        assert( !facilityId.isEmpty() );
        id = facilityId;
    }

    public String getId() {
        return id;
    }
}
