package com.cs371group2.admin;

import com.cs371group2.account.Account;
import com.cs371group2.account.AccountDao;

import java.util.List;

/**
 * Created by Brandon on 2017-03-15.
 */

public final class AccountListResponse extends PagedResponse {

    /** The list of elements received from the response */
    private List<Account> responseList;

    /**
     * Creates a new PagedResponse containing a list of entities and the paging information associated with it.
     *
     * @param responseList The list of elements represented by the response
     * @param pageStartIndex The index of the first element (starting at 1)
     * @precond responseList != null
     */
    public AccountListResponse(List<Account> responseList, int pageStartIndex) {
        super(pageStartIndex, pageStartIndex + responseList.size(), new AccountDao().count());
    }

    public List<Account> getItems() { return responseList; }
}
