package com.cs371group2.admin;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;
import com.cs371group2.account.AccountPermissions;

import java.util.logging.Logger;

/**
 * This object represents an account request containing an offset and a limit,
 * both of which will be to access the account database. It will also include all
 * necessary functionality for authenticating the requester. Finally, there will be
 * an AccountPermissions field for loading only accounts of a certain access level.
 *
 * History property: Instances of this class are immutable from the time they are created.
 *
 * Created on 2017-02-08.
 */
public final class AccountListRequest extends PagedRequest implements Validatable {

    private static final String NULL_PERMISSION_ERROR = "Unable to request list due to null permission level requested.";

    /** This is the type of the accounts to load from the database. */
    private AccountPermissions accountType;

    /**
     * Validates the PagedRequest to ensure that the fields are legal and non-null.
     *
     * @return The result of the validation, including a reason in the case of failure
     */
    @Override
    public ValidationResult validate() {

        if(null == accountType){
            return new ValidationResult(NULL_PERMISSION_ERROR);
        }

        return super.validate();
    }

    /** @return the account permission of the request */
    public AccountPermissions getAccountType() {return accountType;}

    /**
     * TestHook_MutableAccountListRequest is a test hook to make AccountListRequest testable without exposing its
     * members. An instance of TestHook_MutableAccountListRequest can be used to construct new concern request
     * instances and set values for testing purposes.
     */
    public static class TestHook_MutableAccountListRequest {

        /** An immutable AccountListRequest for use in testing*/
        private AccountListRequest immutable;

        /**
         * Creates a new mutable account request
         *
         * @param limit The account limit of the mutable request
         * @param offset The account offset of the mutable request
         * @param token The token of the mutable request
         */
        public TestHook_MutableAccountListRequest(int limit, int offset, String token, AccountPermissions type) {
            immutable = new AccountListRequest();
            immutable.limit = limit;
            immutable.offset = offset;
            immutable.accessToken = token;
            immutable.accountType = type;
        }

        public AccountListRequest build(){
            AccountListRequest request = new AccountListRequest();
            request.limit = immutable.limit;
            request.offset = immutable.offset;
            request.accessToken = immutable.accessToken;
            request.accountType = immutable.accountType;
            return request;
        }

        public void setMutableLimit(int mutableLimit) { immutable.limit = mutableLimit; }

        public void setMutableOffset(int mutableOffset) { immutable.offset = mutableOffset; }

        public void setMutableToken(String mutableToken) { immutable.accessToken = mutableToken; }
    }
}
