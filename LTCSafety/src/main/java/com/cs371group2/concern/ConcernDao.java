package com.cs371group2.concern;

import com.cs371group2.Dao;
import com.cs371group2.account.Account;
import com.cs371group2.admin.ConcernListResponse;
import com.cs371group2.client.OwnerToken;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data access object for the saving, loading, and deletion of concerns.
 *
 * History Properties: This class itself is immutable upon creation, as it has no fields that can modify it.
 *
 * Invariance Properties: This class assumes that an Objectify context has already been initialized via
 * InitContextListener and that the concern entity have been registered.
 *
 * Created on 2017-01-19.
 */
public class ConcernDao extends Dao<Concern> {

    private static final Logger logger = Logger.getLogger(ConcernDao.class.getName());

    /**
     * Creates a data access object for loading, saving, and deleting concerns.
     */
    public ConcernDao() {
        super(Concern.class);
    }

    /**
     * Loads a concern from the datastore using an owner token that was previously created by it.
     *
     * @param token The owner token containing the concern ID in the payload.
     * @return The entity in the datastore that the token references or null if it doesn't exist.
     * @precond token != null
     * @precond token.validate().isValid() meaning that the token contains a properly formatted and
     * signed JWS for parsing.
     */
    public Concern load(OwnerToken token) {

        assert token != null;
        assert token.validate().isValid();

        Long id = token.parseToken();
        logger.log(Level.FINER, "Loading concern # " + id + "  from the datastore...");
        return super.load(id);
    }

    /**
     * Loads a collection of concerns from the datastore using a list of owner tokens that were previously created by it.
     *
     * @param tokens The owner tokens containing the concern ID in the payload.
     * @return The entitys in the datastore that the tokens reference or an empty collection if there were no tokens passed.
     * @precond tokens != null
     * @precond all the tokens in the tokens list contain a properly formatted and signed JWS for parsing.
     */
    public Collection<Concern> load(LinkedList<OwnerToken> tokens) {

        assert tokens != null;

        List<Key<Concern>> keysList = new LinkedList<>();

        for(OwnerToken curToken : tokens){
            assert curToken.validate().isValid();
            keysList.add(Key.create(Concern.class, curToken.parseToken()));
            System.out.println("\n\n\n\n" + curToken.parseToken());
        }

        Collection<Concern> concerns = load(keysList);

        return concerns;
    }


    /**
     * Loads a list of concerns from the datastore starting at the given offset and ending by limit.
     * The account is used to determine which concerns should be loaded based on the list of facilities
     * they have access to and whether or not it is a testing account.
     *
     * @param account The account that is requesting the list of concerns.
     * @param offset The offset to begin loading from
     * @param limit The maximum amount of concerns to load
     * @return A list of entities in the datastore from the given offset and limit
     * @precond offset != null && offset >= 0
     * @precond limit != null && limit > 0
     * @precond account != null
     */
    public ConcernListResponse load(Account account, int offset, int limit, boolean archived){

        assert account != null;
        assert(offset >= 0);
        assert(limit > 0);


        Query<Concern> query = ObjectifyService.ofy().load().type(Concern.class)
                .filter("isTest = ", account.isTestingAccount())
                .filter("isArchived = ", archived);


        List<Concern> loadedConcerns = query
                .order("-submissionDate")
                .offset(offset)
                .limit(limit)
                .list();

        int count = query.count();

        int startIndex = 0;
        int endIndex = 0;

        if(loadedConcerns.size() != 0){
            startIndex = offset + 1;
            endIndex = offset + loadedConcerns.size();
        }

        return new ConcernListResponse(loadedConcerns, startIndex, endIndex, count);
    }

    /**
     * Loads a concern from the database based on the given id. The account is used to ensure that
     * only a test/non-test account is loaded depending on the type of account requesting it
     *
     * @param concernId The unique id of the Concern to load from the database
     * @param account The account requesting the concern
     * @return A list of entities in the datastore from the given offset and limit
     * @precond offset != null && offset >= 0
     * @precond limit != null && limit > 0
     * @precond account != null
     */
    public Concern load(Account account, long concernId) throws UnauthorizedException, NotFoundException {

        assert account != null;

        Concern concern = super.load(concernId);
        System.out.println("Concern: " + concern);
        if (concern == null) {
            throw new NotFoundException("Failed to find a concern with ID: " + concernId);
        }
        if (account.isTestingAccount() == concern.isTest()) {
            return concern;
        } else {
            throw new UnauthorizedException("The account " + account.getId() + "does not have permission to access concern with ID: " + concernId);
        }
    }
}
