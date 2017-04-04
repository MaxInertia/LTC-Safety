package com.cs371group2.account;

import com.cs371group2.Dao;
import com.cs371group2.admin.AccessToken;
import com.cs371group2.admin.AccountListRequest;
import com.cs371group2.admin.AccountListResponse;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class extends the data-access object in our system to load, save, and delete accounts.
 *
 * History Properties: This class itself is immutable upon creation, as it has no fields that can modify it.
 *
 * Invariance Properties: This class assumes that an Objectify context has already been initialized via
 * InitContextListener and that the account entities have been registered.
 *
 * Created on 2017-02-06.
 */
public class AccountDao extends Dao<Account> {

    /**
     * Logger definition for this class.
     */
    private static final Logger logger = Logger.getLogger(Dao.class.getName());

    public AccountDao() {
        super(Account.class);
    }

    /**
     * Load an account from the datastore using its access token. If the
     * account does not exist then a new one is created with AccountPermissions.UNVERIFIED.
     *
     * @param token The token used to authorize the account.
     * @return The account that the access token was used to authorize.
     * @precond token != null
     * @postcond An account associated with the access token's unique identifier exists in the
     * datastore. If the account didn't exist when loading, new account is created with
     * AccountPermissions.UNVERIFIED.
     */
    public Account load(AccessToken token) {

        assert (token != null);

        Account account = super.load(token.getId());
        if (account == null) {
            account = new Account(token.getId(), token.getEmail(), AccountPermissions.UNVERIFIED);
            this.save(account);

            logger.log(Level.FINER, "Created unverified account with ID " + token.getId() + ".");
        } else {
            logger.log(Level.FINER, "Account " + token.getId() + " loaded from the datastore.");
        }

        System.out.println(token.getEmail() + " == " + account.getEmail());

        assert (token.getEmail().equals(account.getEmail()));

        account.setEmailVerified(token.isEmailVerified());
        return account;
    }

    /**
     * Saves an account synchronously to the datastore. If an account with the same
     * identifier already exists it will be overwritten.
     *
     * @param account The account to be saved to the datastore.
     * @return The key used to load the account from the datastore.
     * @precond account and it's fields are non-null
     * @postcond The account has been saved to the datastore for future loading and / or deleting.
     * If the entity has an identifier of type long it will have been populated with the entity's
     * unique identifier.
     */
    public Key<Account> save(Account account) {

        assert (account != null);
        assert (account.getId() != null);
        assert (account.getPermissions() != null);

        logger.log(Level.FINER,
                "Successfully saved Entity " + account.toString() + " to the datastore.");
        return super.save(account);
    }

    /**
     * Loads a list of accounts from the datastore starting at the given offset and ending by limit.
     * The account is used to determine whether or not it should be loading testing accounts.
     *
     * @param account The account that is requesting the list of concerns.
     * @param request The AccountListRequest containing the requested offset, limit, and account type
     * @return A list of entities in the datastore from the given offset and limit
     * @precond offset != null && offset >= 0
     * @precond limit != null && limit > 0
     * @precond account != null
     */
    public AccountListResponse load(Account account, AccountListRequest request){

        assert account != null;
        assert(request.getOffset() >= 0);
        assert(request.getLimit() > 0);

        Query<Account> query = ObjectifyService.ofy().load().type(Account.class)
                .filter("isTestingAccount = ", account.isTestingAccount())
                .filter("permissions = ", request.getAccountType());

        List<Account> accounts =  query
                .offset(request.getOffset())
                .limit(request.getLimit())
                .list();

        int count = query.count();
        int startIndex = 0;
        int endIndex = 0;

        if(accounts.size() != 0){
            startIndex = request.getOffset() + 1;
            endIndex = request.getOffset() + accounts.size();
        }

        return new AccountListResponse(accounts, startIndex, endIndex, count);
    }

    /**
     * Returns the number of accounts in the database (excluding test accounts)
     *
     * @return The number of accounts entities in the database.
     */
    public int count(Account account){
        return ObjectifyService.ofy().load().type(Account.class)
                .filter("isTestingAccount = ", account.isTestingAccount())
                .count();
    }
}
