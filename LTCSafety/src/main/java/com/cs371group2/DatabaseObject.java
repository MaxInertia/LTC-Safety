package com.cs371group2;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by allankerr on 2017-01-15.
 */

// Objects saved to the database must have the @Entity annotation
@Entity
public class DatabaseObject {

    // The ID is used to uniquely identifiy the object

    /**
     * The ID is used to uniquely identify the object. If the ID is a String, the unique identifier must manually be set.
     *  If the ID is a Long then it is automatically generated to be unqiue when saved.
     */

    @Id
    private String key;

    // This would be automatically generated.
    //@Id
    //private Long key;



    // Indexing contents allows for objects to be queried based on their contents.
    //
    // ObjectifyService.ofy().load().type(DatabaseObject.class).filter("contents =", 5).now();


    @Index
    private List<Integer> contents = new LinkedList<>();

    // All entities need a no-argument constructor which is used when being loaded from the database
    private DatabaseObject() {}

    public DatabaseObject(String key, List<Integer> contents) {
        this.key = key;
        this.contents.addAll(contents);
    }
}
