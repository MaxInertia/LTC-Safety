/**
 * The accounts controller is responsible for displaying accounts that have requested access, been denied access,
 * or have access to the LTC Safety dashboard. This involves handling requests for accounts based on page
 * and account permissions and updating the permissions of existing accounts.
 */
safetyApp.controller('AccountsCtrl', function AccountsCtrl($scope, $location, $routeParams, firebase, adminApi) {

    // Call when the controller is first loaded to initialize the filter dropdown
    Webflow.ready();

    /**
     * Initialization function called whenever the account list is refreshed
     * used to initialize the update permissions drop downs in the list.
     */
    $scope.accountsDidLoad = function() {
        Webflow.ready();
    };

    /**
     * The access token used for admin api requests.
     * @type {null}
     */
    $scope.accessToken = null;

    /**
     * The accounts data currently being displayed in the list.
     * pageStartIndex: the index of the first account in the current accounts page relative to the total number of accounts in the datastore.
     * pageEndIndex: the index of the last account in the current accounts page relative to the total number of accounts in the datastore.
     * totalItemsCount: the total number of accounts in the datastore.
     * items: the list of accounts currently being displayed in the accounts list
     * @type {{pageStartIndex: number, pageEndIndex: number, totalItemsCount: number, items: Array}}
     */
    $scope.accounts = {
        startIndex : 0,
        endIndex : 0,
        totalItemsCount : 0,
        items : []
    };

    /**
     * The request that was used to load the current page of accounts.
     * accountType: the permission type that the request is filtered upon
     * limit: the number of accounts to request
     * offset: the start index of the request relative to the total number of accounts of the filtered type.
     * @type {{accountType, limit: Number, offset: Number}}
     */
    $scope.accountsRequest = {
        accountType : $routeParams.accountType,
        limit : parseInt($routeParams.limit),
        offset : parseInt($routeParams.offset)
    };

    /**
     * The auth state changed callback that is called when the user is authorized.
     * This forces the list of accounts to be refreshed upon first authorization.
     */
    firebase.auth().onAuthStateChanged(function (firebaseUser) {

        if (firebaseUser) {

            firebaseUser.getToken().then(function (rawToken) {
                $scope.accessToken = rawToken;
                $scope.refreshAccounts();
            });
        }
    });

    /**
     * Determine whether an account type is one of the accepted account type values.
     * These are ADMIN, UNVERIFIED, and DENIED.
     * @param accountType The account type to validate.
     * @pre none
     * @post none
     * @returns true if the account type is valid or false if it isn't.
     */
    var isValidType = function(accountType) {
        return accountType == 'ADMIN' || accountType == 'UNVERIFIED' || accountType == 'DENIED';
    };

    /**
     * Change the account type that the accounts list is being filtered with.
     * @pre accountType is one of the accepted account type values which are ADMIN, UNVERIFIED, and DENIED.
     * @param accountType The new account type to filter with.
     * @post the accountsRequest has been updated with the new account filter type restarting at offset 0
     * @post the page has been refreshed with the new accountRequest data
     * @post all open drop downs have been closed
     */
    $scope.filterType = function(accountType) {

        closeDropdowns();

        if (!isValidType(accountType)) {
            throw new Error("Attempted to filter on invalid type: " + accountType);
        }
        $scope.accountsRequest.accountType = accountType;
        $scope.accountsRequest.offset = 0;

        $scope.refreshPage();
    };

    /**
     * Request the next page of accounts by increasing the account request offset by the current page size.
     * @pre none
     * @post If there are any accounts in the next page then the page is refreshed with the increased account request offset,
     *       otherwise, the request is ignored if the page would be empty.
     */
    $scope.nextPage = function() {

        var nextOffset = (+$scope.accountsRequest.offset) + (+$scope.accountsRequest.limit);
        if (nextOffset < $scope.accounts.totalItemsCount) {
            $scope.accountsRequest.offset = nextOffset;
            $scope.refreshPage();
        } else {
            console.log("Attempted to fetch a page with an offset greater than the total number of items.");
        }
    };

    /**
     * Request the previous page of accounts by decreasing the account request offset by the current page size.
     * @pre none
     * @post If the new page offset is non-negative then the page is refreshed with the decreased account request offset,
     *       otherwise, the request is ignored due to an invalid offset.
     */
    $scope.previousPage = function() {

        var nextOffset = $scope.accountsRequest.offset - $scope.accountsRequest.limit;
        if (nextOffset >= 0) {
            $scope.accountsRequest.offset = nextOffset;
            $scope.refreshPage();
        } else {
            console.log("Attempted to fetch a page with a negative offset.");
        }
    };

    /**
     * Refresh the current page to load it with new accountRequest data. This may be
     * due to changing the account filter type, the request offset, or the request limit.
     * @pre $scope.accountsRequest.accountType is one of the valid account types: ADMIN, UNVERIFIED, DENIED
     * @pre $scope.accountsRequest.offset < 0
     * @pre $scope.accountsRequest.limit <= 0
     * @post A redirect the the manage-accounts page with the new accounts request has been performed.
     */
    $scope.refreshPage = function() {

        if (!isValidType($scope.accountsRequest.accountType)) {
            throw new Error("Attempted to fetch a page with an invalid account filter type: " + $scope.accountsRequest.accountType);
        }
        if ($scope.accountsRequest.offset < 0) {
            throw new Error("Attempted to fetch a page with a negative start index.");
        }
        if ($scope.accountsRequest.limit <= 0) {
            throw new Error("Attempted to fetch an empty page.");
        }
        $location.url('/manage-accounts/' + $scope.accountsRequest.accountType + '/' + $scope.accountsRequest.offset + '/' + $scope.accountsRequest.limit);
    };

    /**
     * Refresh the current list of accounts using the existing request information without reloading the page.
     * @pre $scope.accountsRequest.accountType is one of the valid account types: ADMIN, UNVERIFIED, DENIED
     * @pre $scope.accountsRequest.offset < 0
     * @pre $scope.accountsRequest.limit <= 0
     * @pre $scope.accessToken != null and is not empty
     * @post If refreshing the accounts succeeded then $scope.accounts is updated with the new accounts data,
     *       otherwise, a modal error is displayed notifying the user an error occurred.
     */
    $scope.refreshAccounts = function() {

        if (!isValidType($scope.accountsRequest.accountType)) {
            throw new Error("Attempted to refresh accounts with an invalid account filter type: " + $scope.accountsRequest.accountType);
        }
        if ($scope.accessToken == null || $scope.accessToken.length <= 0) {
            throw new Error("Attempted to refresh the accounts list with a null access token.");
        }
        if ($scope.accountsRequest.offset < 0) {
            throw new Error("Attempted to fetch a page with a negative start index.");
        }
        if ($scope.accountsRequest.limit <= 0) {
            throw new Error("Attempted to fetch an empty page.");
        }

        var request = {
            accessToken : $scope.accessToken,
            accountType : $scope.accountsRequest.accountType,
            limit : $scope.accountsRequest.limit,
            offset : $scope.accountsRequest.offset
        };
        adminApi.requestAccountList(request).execute(
            function (resp) {
                if (resp.error) {
                    $scope.showModalError('Failed to refresh the accounts list.');
                } else {
                    $scope.$apply(function(){
                        $scope.accounts = resp;
                    });
                }
            }
        );
    };

    /**
     * Update the permissions of an existing account.
     * @pre $scope.accessToken != null and is not empty
     * @pre account.id != null and is not empty
     * @pre permissionsType is one of the accepted types: ADMIN, UNVERIFIED, or DENIED
     * @param account The account who's permissions should be updated.
     * @param permissionsType The permissions that the account should be updated to
     *                        this can be one of ADMIN, UNVERIFIED, DENIED
     * @post all open drop downs have been closed
     * @post the current page of accounts is refreshed if the permissions update succeeded,
     *       otherwise, a modal error is displayed notifying the user that the update failed.
     */
    $scope.updatePermissions = function(account, permissionsType) {

        closeDropdowns();

        if ($scope.accessToken == null || $scope.accessToken.length <= 0) {
            throw new Error("Attempted to update the permissions of an account with no access token.");
        }
        if (!isValidType(permissionsType)) {
            throw new Error("Attempted to update the permissions of an account to an invalid account type: " + permissionsType);
        }
        if (account.id == null || account.id.length <= 0) {
            throw new Error("Attempted to update the permissions of an account with no id.");
        }

        var request = {
            accessToken : $scope.accessToken,
            accountId : account.id,
            permissions : permissionsType
        };
        adminApi.updateAccountPermission(request).execute(
            function (resp) {
                if (resp.error) {
                    $scope.showModalError('Failed to update permissions for ' + account.email);
                } else {
                    $scope.refreshAccounts();
                }
            }
        );
    };

    /**
     * Helper function for programmatically closing drop down menus. This is required
     * because drop down menus do not automatically close when an option is selected.
     */
    var closeDropdowns = function() {
        $("nav").removeClass("w--open");
        $("div").removeClass("w--open");
    };
});
