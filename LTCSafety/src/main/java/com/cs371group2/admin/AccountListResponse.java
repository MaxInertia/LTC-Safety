package com.cs371group2.admin;

import com.cs371group2.account.Account;

import java.util.List;

/**
 * This object represents an account request response for the AdminApi methods.
 * It contains a list of accounts received from the query, as well as
 *
 * History property: Instances of this class are mutable from the time they are created,
 * due to referential nature of the list of elements.
 *
 * Invariance Properties: This class assumes that an administrator has requested a list of accounts
 * and has been validly authenticated. It also assumed the list it is given is not null, the start index
 * is smaller or equal to the end index, and the total size is non-negative.
 *
 * Created on 2017-03-15.
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
     *
     * @precond list != null
     * @precond startIndex <= endIndex
     * @precond totalSize >= 0
     */
    public AccountListResponse(List<Account> accountList, int startIndex, int endIndex, int totalSize) {
        super(startIndex, endIndex, totalSize);
        this.responseList = accountList;
    }

    /**
     * @return the list of accounts received in response to an administrator request.
     */
    public List<Account> getItems() { return responseList; }
}
