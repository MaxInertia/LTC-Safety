package com.cs371group2.admin;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;
import com.cs371group2.account.Account;
import com.cs371group2.account.AccountPermissions;

/**
 * This class represents a request involving the updating of an account with the given permission levels. This request
 * requires the requester to hold administrative priviledges, and takes an account id and a permission level to apply
 * to the account.
 *
 * History Property: Instances of this class are immutable from the time they are created.
 *
 * Invariance Properties: This class assumes that the given accountId is validly formatted, and that
 * the given AccountPermission is non-null.
 *
 * Created by Brandon on 2017-03-20.
 */
public class UpdateAccountPermissionRequest extends AdminRequest implements Validatable{

    private static final String NULL_TOKEN_ERROR = "Unable to access requested list due to non-existent credentials.";

    private static final String EMPTY_TOKEN_ERROR = "Unable to access requested list due to receiving an empty access token.";

    private static final String NULL_PERMISSION_ERROR = "Unable to update an account's permission with a null permission.";

    private static final String NULL_ID_ERROR = "Unable to update an account's permissions with a null id.";

    private static final String EMPTY_ID_ERROR = "Unable to update an account's permissions with an empty id.";

    /**
     * The id of the account to update the permissions of.
     */
    private String accountId;

    /**
     * The account permissions to grant the given account.
     */
    private AccountPermissions permissions;

    /**
     * Validates the request, ensuring that the accountId and accessToken are non-null and non-empty. Also ensures that
     * the given accountPermission is non-null.
     *
     * @return Whether the request is valid or not.
     */
    @Override
    public ValidationResult validate() {
        if(accessToken == null){
            return new ValidationResult(NULL_TOKEN_ERROR);
        } else if (accessToken.isEmpty()){
            return new ValidationResult(NULL_TOKEN_ERROR);
        } else if(accountId == null){
            return new ValidationResult(NULL_ID_ERROR);
        } else if (accountId.isEmpty()){
            return new ValidationResult(EMPTY_ID_ERROR);
        } else if (permissions == null){
            return new ValidationResult(NULL_PERMISSION_ERROR);
        }
        return new ValidationResult();
    }

    /**
     * TestHook_MutableUpdateAccountPermissionRequest is a test hook to make UpdateAccountPermissionRequest testable
     * without exposing its members. An instance of TestHook_MutableUpdateAccountPermissionRequest can be used to
     * construct new UpdateAccountPermissionRequest instances and set values for testing purposes.
     */
    public static class TestHook_MutableUpdateAccountPermissionRequest {

        /** An immutable ConcernListRequest for use in testing*/
        private UpdateAccountPermissionRequest immutable = new UpdateAccountPermissionRequest();

        /**
         * Creates a new, mutable UpdateAccountPermissionRequest for testing purposes
         *
         * @param permission The account permissions to apply to the given account
         * @param accountId The id of the account to update the permissions of
         * @param token The firebase token of the account requesting the permission update
         */
        public TestHook_MutableUpdateAccountPermissionRequest(AccountPermissions permission, String accountId, String token) {
            immutable.permissions = permission;
            immutable.accountId = accountId;
            immutable.accessToken = token;
        }

        public UpdateAccountPermissionRequest build(){
            return immutable;
        }

        public void setMutablePermissions(AccountPermissions permissions) {
            immutable.permissions = permissions;
        }

        public void setMutableAccountId(String mutableId) { immutable.accountId = mutableId; }
    }
}
