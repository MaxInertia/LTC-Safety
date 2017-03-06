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
            $apply : function() {}
        };

        // AdminApi mock to mock out server concern calls
        adminApiMock = {
            requestConcern: function(request) {
                return {
                    execute: function(callback) {
                        callback('Test Response');
                    }
                }
            }
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
            }

            var controller = $controller('ConcernDetailCtrl', { $scope: $scope, $routeParams: routeParams, firebase: firebaseMock, adminApi: adminApiMock });

            spyOn($scope, '$apply');

            $scope.concernRequest.accessToken = 'fake token';
            $scope.fetchConcern();

            // Expect that the concern was updated
            expect($scope.concern).toEqual('Test Response');
            expect($scope.$apply).toHaveBeenCalled();
        });
    });

    /**
     * Unit tests for the concern detail function that maps
     * status type enum values to human readable strings.
     */
    describe('Concern detail status mapping tests', function() {

        /**
         * Test that the the concern key is mapped using the identitiy function.
         * TODO Update when names for statuses are decided upon.
         */
        it('Convert status test', function() {

            var controller = $controller('ConcernDetailCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            var input = "Status Key";
            var result = $scope.statusNames(input);
            expect(result).toEqual(input);
        });
    });
});