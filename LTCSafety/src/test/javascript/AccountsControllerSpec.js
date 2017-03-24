/**
 * Created by allankerr on 2017-02-13.
 *
 * Unit tests for the accounts controller to ensure that the
 * filtering based on account type, account paging, and refreshing
 * accounts is working as intended.
 */
describe("Accounts Controller", function() {

    beforeEach(module('safetyApp'));

    var $scope;
    var adminApiMock;
    var firebaseMock;
    var $controller;

    beforeEach(inject(function(_$controller_){

        $controller = _$controller_;

        $scope = {
            $apply : function(callback) {
                callback();
            }
        };

        // AdminApi mock to mock out server api calls
        adminApiMock = {
            updateConcernStatus : function(request) {},
            requestAccountList : function(request) {},
            updateAccountPermission : function(request) {}
        };

        // Firebase mock to mock out authentication server calls
        firebaseMock = {
            auth: function() {
                return {
                    onAuthStateChanged: function(callback) {}
                };
            }
        };
    }));

    /**
     * Tests for updating the account type filter used to determine the account types
     * that are displayed in the list.
     */
    describe('Filter account types tests', function() {

        /**
         * Test that an error is throwing when attempting to filter the accounts based on an unsupported type.
         */
        it('Filter invalid type', function() {

            $controller('AccountsCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            spyOn($scope, 'refreshPage');

            // Expect that an error was thrown due supplying an invalid account type
            expect(function(){
                $scope.filterType('INVALID_TYPE');
            }).toThrow(new Error("Attempted to filter on invalid type: INVALID_TYPE"));

            expect($scope.refreshPage).not.toHaveBeenCalled();
        });

        /**
         * Test that the page is refreshed with the desired account type starting at offset 0
         * when the account filter type is updated.
         */
        it('Filter valid type', function() {

            $controller('AccountsCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            spyOn($scope, 'refreshPage');

            $scope.filterType('ADMIN');

            expect($scope.accountsRequest.accountType).toEqual('ADMIN');
            expect($scope.accountsRequest.offset).toEqual(0);
            expect($scope.refreshPage).toHaveBeenCalled();
        });
    });

    /**
     * Tests for refreshing the current page, returning to the previous page,
     * and going to the next page of accounts.
     */
    describe('Account paging tests', function() {

        /**
         * Test that the page is not refreshed and the current page
         * offset remains unchanged when attempting to go to an out of bounds page.
         */
        it('Next page out of bounds test', function () {

            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock
            });

            $scope.accounts.totalItemsCount = 100;
            $scope.accountsRequest.offset = 95;
            $scope.accountsRequest.limit = 5;

            spyOn($scope, 'refreshPage');

            $scope.nextPage();

            expect($scope.accountsRequest.offset).toEqual(95);
            expect($scope.refreshPage).not.toHaveBeenCalled();
        });

        /**
         * Test that the page is refreshed and the page
         * offset is increased by the limit to allow the next page to be displayed
         */
        it('Next page in bounds test', function () {

            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock
            });

            $scope.accounts.totalItemsCount = 100;
            $scope.accountsRequest.offset = 0;
            $scope.accountsRequest.limit = 25;

            spyOn($scope, 'refreshPage');

            $scope.nextPage();

            expect($scope.accountsRequest.offset).toEqual(25);
            expect($scope.accountsRequest.limit).toEqual(25);
            expect($scope.refreshPage).toHaveBeenCalled();
        });

        /**
         * Test that the page is not refreshed and the current page
         * offset remains unchanged when attempting to go to an out of bounds page.
         */
        it('Previous page out of bounds test', function () {

            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock
            });

            $scope.accountsRequest.offset = 5;
            $scope.accountsRequest.limit = 25;

            spyOn($scope, 'refreshPage');

            $scope.previousPage();

            expect($scope.accountsRequest.offset).toEqual(5);
            expect($scope.accountsRequest.limit).toEqual(25);
            expect($scope.refreshPage).not.toHaveBeenCalled();
        });

        /**
         * Test that the page is refreshed and the page offset is decreased
         * by the page limit to allow the previous page to be displayed
         */
        it('Previous page in bounds test', function () {

            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock
            });

            $scope.accounts.totalItemsCount = 100;
            $scope.accountsRequest.offset = 50;
            $scope.accountsRequest.limit = 25;

            spyOn($scope, 'refreshPage');

            $scope.previousPage();

            expect($scope.accountsRequest.offset).toEqual(25);
            expect($scope.accountsRequest.limit).toEqual(25);
            expect($scope.refreshPage).toHaveBeenCalled();
        });

        /**
         * Test that the a url request with the expected parameters
         * is made when refreshing the page with valid data.
         */
        it('Valid page refresh test', function () {

            var $location = {
                url: function (path) {
                }
            };
            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock,
                $location: $location
            });

            testRefresh(50, 25, 'ADMIN', function () {
                spyOn($location, 'url');
            }, function () {
                expect($location.url).toHaveBeenCalledWith('/manage-accounts/ADMIN/50/25');
            });
        });

        /**
         * Test that an error is thrown when attempting to refresh with a negative page offset
         */
        it('Negative page offset refresh test', function () {

            var $location = {
                url: function (path) {
                }
            };
            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock,
                $location: $location
            });

            expect(function() {
                testRefresh(-1, 25, 'ADMIN', function () {
                    spyOn($location, 'url');
                }, function () {
                    expect($location.url).not.toHaveBeenCalled();
                });
            }).toThrow(
                new Error("Attempted to fetch a page with a negative start index."));


        });

        /**
         * Test that an error is thrown when attempting to refresh with a negative page offset
         */
        it('Non-positive page limit refresh test', function () {

            var $location = {
                url: function (path) {
                }
            };
            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock,
                $location: $location
            });

            expect(function() {
                testRefresh(0, 0, 'ADMIN', function () {
                    spyOn($location, 'url');
                }, function () {
                    expect($location.url).not.toHaveBeenCalled();
                });
            }).toThrow(
                new Error("Attempted to fetch an empty page."));
        });

        /**
         * Test that an error is thrown when attempting to refresh with an unsupported account type
         */
        it('Invalid account type refresh test', function () {

            var $location = {
                url: function (path) {
                }
            };
            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock,
                $location: $location
            });

            expect(function(){
                testRefresh(0, 25, 'INVALID_TYPE', function () {
                    spyOn($location, 'url');
                }, function () {
                    expect($location.url).not.toHaveBeenCalled();
                });
            }).toThrow(
                new Error("Attempted to fetch a page with an invalid account filter type: INVALID_TYPE"));
        });


        /**
         * Helper function for testing refresh with success and error conditions.
         * @param offset The offset used for the refresh request.
         * @param limit The limit used for the refresh request.
         * @param accountType The account type used for the refresh request.
         * @param setupSpies A callback called prior to calling refresh to setup spies if needed.
         * @param checkExpectations A callback after calling refreshPage to check expectionations hold.
         */
        var testRefresh = function (offset, limit, accountType, setupSpies,
            checkExpectations) {

            $scope.accountsRequest.offset = offset;
            $scope.accountsRequest.limit = limit;
            $scope.accountsRequest.accountType = accountType;

            setupSpies();

            $scope.refreshPage();

            checkExpectations();
        };
    });

    /**
     * Tests for refreshing the list of accounts for the currently loaded page without redirect.
     */
    describe('Refresh accounts tests', function () {

        /**
         * Test that an error is thrown when attempting to refresh accounts with an unsupported account type
         */
        it('Invalid account type refresh test', function () {

            expect(function(){
                testRefreshAccounts(false, "test_token", 0, 25, 'INVALID_TYPE', function () {
                    spyOn(adminApiMock, 'requestAccountList');
                }, function () {
                    expect(adminApiMock.requestAccountList).not.toHaveBeenCalled();
                    expect($scope.accounts.items).toEqual([]);
                });
            }).toThrow(new Error("Attempted to refresh accounts with an invalid account filter type: INVALID_TYPE"));
        });

        /**
         * Test that an error is thrown when attempting to refresh accounts with a negative page offset
         */
        it('Invalid page offset refresh test', function () {

            expect(function(){
                testRefreshAccounts(false, "test_token", -1, 25, 'ADMIN', function () {
                    spyOn(adminApiMock, 'requestAccountList');
                }, function () {
                    expect(adminApiMock.requestAccountList).not.toHaveBeenCalled();
                    expect($scope.accounts.items).toEqual([]);
                });
            }).toThrow(new Error("Attempted to fetch a page with a negative start index."));
        });

        /**
         * Test that an error is thrown when attempting to refresh accounts with a null or empty token
         */
        it('Empty token refresh test', function () {

            expect(function(){
                testRefreshAccounts(false, "", 0, 25, 'ADMIN', function () {
                    spyOn(adminApiMock, 'requestAccountList');
                }, function () {
                    expect(adminApiMock.requestAccountList).not.toHaveBeenCalled();
                    expect($scope.accounts.items).toEqual([]);
                });
            }).toThrow(new Error("Attempted to refresh the accounts list with a null access token."));
        });

        /**
         * Test that an error is thrown when attempting to refresh accounts with a non-positive page size
         */
        it('Invalid page size refresh test', function () {

            expect(function(){
                testRefreshAccounts(false, "test_token", 0, 0, 'ADMIN', function () {
                    spyOn(adminApiMock, 'requestAccountList');
                }, function () {
                    expect(adminApiMock.requestAccountList).not.toHaveBeenCalled();
                    expect($scope.accounts.items).toEqual([]);
                });
            }).toThrow(new Error("Attempted to fetch an empty page."));
        });

        /**
         * Test that the error modal is displayed when input is valid but the api call fails
         */
        it('Server side failure refresh test', function () {

            testRefreshAccounts(true, "test_token", 0, 25, 'ADMIN', function () {
                $scope.showModalError = function(message) {};
                spyOn($scope, 'showModalError');
            }, function () {
                expect($scope.showModalError).toHaveBeenCalledWith('Failed to refresh the accounts list.');
                expect($scope.accounts.items).toEqual([]);
            });
        });

        /**
         * Test that accounts is updated when providing valid input and the api call succeeds
         */
        it('Successful refresh test', function () {

            testRefreshAccounts(false, "test_token", 0, 25, 'ADMIN', function () {}, function () {
                expect($scope.accounts).toEqual([1,2,3,4,5]);
            });
        });

        /**
         * Helper function for testing refresh accounts
         * @param simulateError true if the api call should simulate an error or false otherwise
         * @param token The access token to be used for the test case
         * @param offset The page offset to be used for the test case
         * @param limit The page size to be used for the test case
         * @param accountType The account type to be used for the test case
         * @param setupSpies A function called before refreshAccounts() to setup any spies
         * @param checkExpectations A function called after refreshAccounts() to ensure expectations hold
         */
        var testRefreshAccounts = function(simulateError, token, offset, limit, accountType, setupSpies, checkExpectations) {

            adminApiMock.requestAccountList  = function(request) {
                return {
                    execute : function(callback) {
                        if (simulateError) {
                            callback({ error : "an error occurred" });
                        } else {
                            callback([1,2,3,4,5]);
                        }
                    }
                }
            };

            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock
            });
            $scope.accessToken = token;
            $scope.accountsRequest.offset = offset;
            $scope.accountsRequest.limit = limit;
            $scope.accountsRequest.accountType = accountType;

            setupSpies();

            $scope.refreshAccounts();

            checkExpectations();
        };
    });

    describe('Update account permissions tests', function () {

        /**
         * Test that an error is thrown when attempting to update permissions
         * without providing an access token.
         */
        it('Update account permissions with invalid token test', function () {

            expect(function(){
                testUpdatePermissions(false, "", "12345", "email@test.com", 'ADMIN', function () {
                    spyOn(adminApiMock, 'updateAccountPermission');
                    spyOn($scope, 'refreshAccounts');
                }, function () {
                    expect(adminApiMock.updateAccountPermission).not.toHaveBeenCalled();
                    expect($scope.refreshAccounts).not.toHaveBeenCalled();
                });
            }).toThrow(new Error("Attempted to update the permissions of an account with no access token."));
        });

        /**
         * Test that an error is thrown when attempting to update permissions for
         * an account that doesn't have an identifier.
         */
        it('Update account permissions with invalid account id test', function () {

            expect(function(){
                testUpdatePermissions(false, "test_token", "", "email@test.com", 'ADMIN', function () {
                    spyOn(adminApiMock, 'updateAccountPermission');
                    spyOn($scope, 'refreshAccounts');
                }, function () {
                    expect(adminApiMock.updateAccountPermission).not.toHaveBeenCalled();
                    expect($scope.refreshAccounts).not.toHaveBeenCalled();
                });
            }).toThrow(new Error("Attempted to update the permissions of an account with no id."));
        });

        /**
         * Test that an error is thrown when attempting to update permissions with an invalid
         * permissions type.
         */
        it('Update account permissions with invalid permissions type test', function () {

            expect(function(){
                testUpdatePermissions(false, "test_token", "12345", "email@test.com", 'INVALID_TYPE', function () {
                    spyOn(adminApiMock, 'updateAccountPermission');
                    spyOn($scope, 'refreshAccounts');
                }, function () {
                    expect(adminApiMock.updateAccountPermission).not.toHaveBeenCalled();
                    expect($scope.refreshAccounts).not.toHaveBeenCalled();
                });
            }).toThrow(new Error("Attempted to update the permissions of an account to an invalid account type: INVALID_TYPE"));
        });

        /**
         * Test that an error is displayed to the user when the server side api call fails.
         */
        it('Update account permissions with api failure test', function () {

            testUpdatePermissions(true, "test_token", "12345", "email@test.com", 'ADMIN', function () {
                $scope.showModalError = function(message) {};
                spyOn($scope, 'showModalError');
                spyOn($scope, 'refreshAccounts');
            }, function () {
                expect($scope.showModalError).toHaveBeenCalledWith('Failed to update permissions for email@test.com');
                expect($scope.refreshAccounts).not.toHaveBeenCalled();
            });
        });

        /**
         * Test that the accounts list is refreshed when the permissions of an account are successfully updated.
         */
        it('Successful update account permissions test', function () {

            testUpdatePermissions(false, "test_token", "12345", "email@test.com", 'ADMIN', function () {
                $scope.showModalError = function(message) {};
                spyOn($scope, 'refreshAccounts');
            }, function () {
                expect($scope.refreshAccounts).toHaveBeenCalled();
            });
        });

        /**
         * Helper function for testing updatePermissions with both success and error conditions.
         * @param simulateError true if the api endpoint should simulate an error occurring
         * @param token The access token to be used for the test case
         * @param accountId The account id of the account who's permissions are to be updated
         * @param accountEmail The account email of the account who's permissions are to be updated
         * @param permissionsType The permissions the account is to be updated to
         * @param setupSpies A function called before refreshAccounts() to setup any spies
         * @param checkExpectations A function called after refreshAccounts() to ensure expectations hold
         */
        var testUpdatePermissions = function(simulateError, token, accountId, accountEmail, permissionsType, setupSpies, checkExpectations) {

            adminApiMock.updateAccountPermission  = function(request) {
                return {
                    execute : function(callback) {
                        if (simulateError) {
                            callback({ error : "an error occurred" });
                        } else {
                            callback({});
                        }
                    }
                }
            };
            $controller('AccountsCtrl', {
                $scope: $scope,
                firebase: firebaseMock,
                adminApi: adminApiMock
            });

            var account = {
                id : accountId,
                email : accountEmail
            };
            $scope.accessToken = token;

            setupSpies();

            $scope.updatePermissions(account, permissionsType);

            checkExpectations();
        };
    });


    /**
     * Tests that mapping account permissions types to human readable names functions properly.
     */
    describe('Account permissions name tests', function() {

        /**
         * Tests that the permissionsNames function properly maps from permission types to human readable names.
         */
        it('Account permissions type to name test', function() {

            $controller('AccountsCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            var name = $scope.permissionsNames('ADMIN');
            expect(name).toEqual('Admin');

            name = $scope.permissionsNames('UNVERIFIED');
            expect(name).toEqual('Unverified');

            name = $scope.permissionsNames('DENIED');
            expect(name).toEqual('Denied');

            name = $scope.permissionsNames('DENIED');
            expect(name).toEqual('Denied');

            expect(function(){
                $scope.permissionsNames('INVALID_TYPE');
            }).toThrow(new Error("Attempted to get the name for an invalid permissions type: INVALID_TYPE"));
        });
    });

});