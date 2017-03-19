package com.cs371group2;

import com.cs371group2.concern.Concern;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;

import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic data access object for loading, saving, and deleting entities from the datastore. This
 * class should be subclassed for every entity that is stored in the database.
 *
 * Created on 2017-01-19.
 */
public abstract class Dao<E> {

    /**
     * Logger definition for this class.
     */
    private static final Logger logger = Logger.getLogger(Dao.class.getName());

    /**
     * The class of the entity that the data access object was created for. This must be stored
     * because class of a generic cannot be accessed without reflection.
     */
    private Class<E> entityClass;

    /**
     * Create a new data access object for loading, saving, and deleting entities of type E.
     *
     * @param entityClass The class of the entity corresponding to e.class.
     * @precond entityClass != null
     */
    protected Dao(Class<E> entityClass) {

        assert entityClass != null;

        logger.log(Level.FINER, "Created Dao for " + entityClass.toString());
        this.entityClass = entityClass;
    }

    /**
     * Loads the entity synchronously from the datastore using its long identifier if it has one.
     *
     * @param id The identifier used to uniquely identify the entity in the database.
     * @return The entity in the datastore that the id references or null if it doesn't exist.
     * @precond id != null
     */
    public E load(Long id) {

        assert id != null;

        logger.log(Level.FINER, "Successfully loaded Entity# " + id + " from the datastore.");
        return ObjectifyService.ofy().load().type(entityClass).id(id).now();
    }


    /**
     * Loads the entity synchronously from the datastore using its string identifier if it has one.
     *
     * @param identifier The identifier used to uniquely identify the entity in the database.
     * @return The entity in the datastore that the identifier references or null if it doesn't
     * exist.
     * @precond identifier != null
     */
    public E load(String identifier) {

        assert identifier != null;

        logger.log(Level.FINER,
                "Successfully loaded Entity# " + identifier + " from the datastore.");
        return ObjectifyService.ofy().load().type(entityClass).id(identifier).now();
    }

    /**
     * Loads the entity synchronously from the datastore directly using its key.
     *
     * @param key The objectify key created directly using the entity's identifier and class.
     * @return The entity in the datastore that the key references or null if it doesn't exist.
     * @precond key != null
     */
    public E load(Key<E> key) {

        assert key != null;

        logger.log(Level.FINER, "Successfully loaded Entity# " + key + " from the datastore.");
        return ObjectifyService.ofy().load().key(key).now();
    }

    /**
     * Loads a collection of entities synchronously from the datastore directly using its keys.
     *
     * @param keysList The objectify keys created directly using the entity's identifier and class.
     * @return The entitys in the datastore that the keys reference.
     * @precond keysList != null
     */
    public Collection<E> load(Collection<Key<E>> keysList) {

        assert keysList != null;

        logger.log(Level.FINER, "Successfully loaded a list of entities from the datastore.");
        return ObjectifyService.ofy().load().keys(keysList).values();
    }

    /**
     * Saves an entity of type E synchronously to the datastore. If an entity with the same
     * identifier already exists it will be overwritten.
     *
     * @param entity The entity to be saved to the datastore.
     * @return The key used to load the entity from the datastore.
     * @precond entity != null
     * @postcond The entity has been saved to the datastore for future loading and / or deleting. If
     * the entity has an identifier of type long it will have been populated with the entity's
     * unique identifier.
     */
    public Key<E> save(E entity) {

        assert entity != null;

        logger.log(Level.FINER,"Successfully saved Entity " + entity.toString() + " to the datastore.");
        return ObjectifyService.ofy().save().entity(entity).now();
    }

    /**
     * Deletes an entity of type E from the datastore.
     *
     * @param entity The entity to be deleted.
     * @precond entity != null
     * @postcond The entity is no longer present in the datastore.
     */
    public Result<Void> delete(E entity) {

        assert entity != null;

        logger.log(Level.FINER, "Deleting Entity " + entity.toString() + " from the datastore.");
        return ObjectifyService.ofy().delete().entity(entity);
    }
}