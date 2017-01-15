package com.cs371group2;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.util.ArrayList;
import java.util.List;

@Api(name = "demoApi", version = "v1",
        title = "Demo API",
        description = "This is a demo API to familiarize the development team with app engine.")
public class DemoApi {


    // @Named(...) is needed for all primative types like String, Integer, Double, etc.
    // It is generally recommended to use a class like ClassInput to receive input rather than named primatives.
    @ApiMethod(name = "demoEndPoint", path = "demo/endpoint")
    public List<String> demoEndPoint(@Named("PrimativeInput") String primativeInput, ClassInput classInput) {




        // All classes must be registered before being stored in the database
        // Once per class is sufficient
        ObjectifyService.register(DatabaseObject.class);





        // Create an object.
        DatabaseObject testObject = new DatabaseObject(classInput.getVariableOne(), classInput.getVariableTwo());

        // Save it synchronously to the database.
        ObjectifyService.ofy().save().entity(testObject).now();

        // Other examples for saving objects into the database
        //
        // Save multiple entities at once.
        //ObjectifyService.ofy().save().entities(testObject, anotherEntityWhichMayBeADifferentClass).now();




        // Create the key to access the object that was just saved and load the object from the database synchronously.
        Key<DatabaseObject> key = Key.create(DatabaseObject.class, classInput.getVariableOne());
        DatabaseObject loaded = ObjectifyService.ofy().load().key(key).now();



        // Other examples for loading objects from the database
        //
        // Load all of a specific class
        // ObjectifyService.ofy().load().type(DatabaseObject.class);
        //
        // Load all DatabaseObjects who's contents array contains 5
        // ObjectifyService.ofy().load().type(DatabaseObject.class).filter("contents =", 5).list();



        // Create the Response
        List<String> output = new ArrayList<String>();
        output.add(primativeInput);

        output.add(classInput.getVariableOne());
        for (Integer i : classInput.getVariableTwo()) {
            output.add(i.toString());
        }
        return output;
    }
}

