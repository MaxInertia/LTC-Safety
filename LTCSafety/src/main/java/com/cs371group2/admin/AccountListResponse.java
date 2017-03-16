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
     * Creates a new AccountListResponse containing a list of accounts and the paging information associated with it.
     *
     * @param accountList The list of accounts to be represented in the response.
     * @param startIndex The paging index of the first account (starting at 1)
     * @param endIndex The paging index of the last account (starting at 1)
     * @param totalSize The total amount accounts stored in the database
     * @precond list != null
     * @precond startIndex <= endIndex
     * @precond totalSize >= 0
     */
    public AccountListResponse(List<Account> accountList, int startIndex, int endIndex, int totalSize) {
        super(startIndex, endIndex, totalSize);
        this.responseList = accountList;
    }

    public List<Account> getItems() { return responseList; }
}
