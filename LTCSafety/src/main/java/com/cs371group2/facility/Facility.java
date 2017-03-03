package com.cs371group2.facility;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Objects;

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
    /**
     * Creates a new facility to be stored in the database with an empty id.
     * NOTE :: THIS IS BAD the client api was complaining about wanting a no
     * param constructor for a facility.
     */
    public Facility(){
        id = "";
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals (Object obj){
        if(obj == null) {
            return false;
        }

        if(!Facility.class.isAssignableFrom(obj.getClass())){
            return false;
        }

        final Facility other = (Facility) obj;

        if(other.getId() == null || this.getId() == null){
            return false;
        }

        return (this.getId() == other.getId());
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
