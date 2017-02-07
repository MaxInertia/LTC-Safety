package account;

import com.cs371group2.Dao;

/**
 * Created by Brandon on 2017-02-06.
 */
public class AccountDao extends Dao<Account> {

    public AccountDao() {
        super(Account.class);
    }

    public Account load(String id) {
        return null;
    }
}
