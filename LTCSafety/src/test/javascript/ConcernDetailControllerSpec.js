/**
 * Created by allankerr on 2017-02-13.
 *
 * Unit tests for the concern controller to ensure that the concern
 * is properly loaded from the admin API and displayed.
 */
describe("Detail Controller", function() {

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

        // AdminApi mock to mock out server concern calls
        adminApiMock = {
            requestConcern: function(request) {
                return {
                    execute: function(callback) {
                        callback('Test Response');
                    }
                }
            },
            updateConcernStatus : function(request) {}
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
     * Unit tests for the concern detail controller's fetch concern function
     * used to fetch and display a concern from the backend using the Admin API.
     */
    describe('Concern detail request tests', function() {

        /**
         * Test that an error is thrown when fetching with no access token.
         */
        it('Fetch concern without token test', function() {

            var controller = $controller('ConcernDetailCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            spyOn($scope, '$apply');

            $scope.concernRequest.concernId = 12345;

            // Expect that an error was thrown due to no access token
            expect($scope.fetchConcern).toThrow(new Error("Attempted to fetch a concern without providing an access token."));
            expect($scope.concern).toEqual(null);
            expect($scope.$apply).not.toHaveBeenCalled();
        });

        /**
         * Test that an error is thrown when fetching with no concern id.
         */
        it('Fetch concern without concern id', function() {

            var controller = $controller('ConcernDetailCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            spyOn($scope, '$apply');

            $scope.concernRequest.accessToken = 'fake token';

            // Expect that an error was thrown due to no id being provided
            expect($scope.fetchConcern).toThrow(new Error("Attempted to fetch a concern with no id."));
            expect($scope.concern).toEqual(null);
            expect($scope.$apply).not.toHaveBeenCalled();
        });

        /**
         * Test that the concern is updated when a valid request is provided.
         */
        it('Fetch valid concern test', function() {

            var routeParams = {
                id: 12345
            };

            var controller = $controller('ConcernDetailCtrl', { $scope: $scope, $routeParams: routeParams, firebase: firebaseMock, adminApi: adminApiMock });

            spyOn($scope, '$apply');

            $scope.concernRequest.accessToken = 'fake token';
            $scope.fetchConcern();

            // Expect that the concern was updated
            expect($scope.concern).toEqual('Test Response');
            expect($scope.$apply).toHaveBeenCalled();
        });
    });

    describe('Concern detail request tests', function() {

        /**
         * Test that an error is thrown when attempting to
         * update the status when no concern is set.
         */
        it('Update status for null concern test', function() {

            $controller('ConcernDetailCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            expect(function(){
                $scope.updateStatus("RESOLVED");
            }).toThrow(new Error("Attempted to update the status for a null concern."));
        });

        /**
         * Test that the admin api call is never performed when attempting to update the status
         * to the same status that it currently has.
         */
        it('Update to same status test', function() {

            spyOn(adminApiMock, 'updateConcernStatus');

            $controller('ConcernDetailCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concern = {
                statuses : [
                    {type : "RESOLVED"},
                    {type : "PENDING"}
                ]
            };
            $scope.updateStatus("PENDING");
            expect(adminApiMock.updateConcernStatus).not.toHaveBeenCalled();
        });


        /**
         * Test that the error modal div is displayed when the update concern
         * status api call fails despite valid input.
         */
        it('Update with server side failure test', function() {

            adminApiMock.updateConcernStatus = function(request) {
                return {
                    execute: function (callback) {
                        callback({
                            error : "An error occurred"
                        });
                    }
                };
            };

            $scope.showModalError = function(error) {};
            spyOn($scope, 'showModalError');

            $controller('ConcernDetailCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concern = {
                statuses : [
                    {type : "RESOLVED"},
                    {type : "PENDING"}
                ]
            };
            $scope.updateStatus("RESPONDING24");
            expect($scope.showModalError).toHaveBeenCalledWith('Failed to update concern status.');
        });

        /**
         * Test that the error modal div is displayed when the update concern
         * status api call outputs
         */
        it('Successful status update test', function() {

            // Mock out the api call to return the expected result
            adminApiMock.updateConcernStatus = function(request) {
                return {
                    execute: function (callback) {
                        callback({
                            status : {
                                type : request.concernStatus
                            }
                        });
                    }
                };
            };

            $controller('ConcernDetailCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concern = {
                statuses : [
                    {type : "RESOLVED"},
                    {type : "PENDING"}
                ]
            };
            var newStatus = "RETRACTED";
            $scope.updateStatus(newStatus);

            // Expect the concern status to be updated
            expect($scope.concern.statuses[2].type).toEqual(newStatus);
        });
    });

});