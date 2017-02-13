/**
 * Created by allankerr on 2017-02-13.
 *
 * Unit tests for the inbox controller to ensure that concerns are properly fetched
 * from the Admin API and that paging functions behave as expected.
 */
describe("Inbox Controller", function() {

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
            requestConcernList: function(request) {
                return {
                    execute: function(callback) {
                        var response = {
                            items : ['test1', 'test2']
                        };
                        callback(response);
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
     * Unit tests for the inbox controllers request concerns function
     * for fetching concerns from the backend using the Admin API.
     */
    describe('Inbox concern request tests', function() {

        /**
         * Test to ensure that the list of concerns is updated when update concern list is called.
         */
        it('Request concerns test', function() {

            var controller = $controller('InboxCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concernRequest.accessToken = "fakeAccessToken";
            $scope.updateConcernList();

            // Expect that the concerns list is non-empty
            expect($scope.concerns).toEqual(['test1', 'test2']);

        });

        /**
         * Test to ensure that the list of concerns is not updated when no access token is provided
         */
        it('Request concerns without token test', function() {

            var controller = $controller('InboxCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.updateConcernList();

            // Expect that the concerns list is not updated
            expect($scope.concerns).toEqual([]);

        });

        /**
         * Test to ensure that the list of concerns is not updated when an invalid page offset is provided
         */
        it('Request concerns without negative page offset test', function() {

            var controller = $controller('InboxCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concernRequest.accessToken = "fakeAccessToken";
            $scope.concernRequest.offset = -1;
            $scope.updateConcernList();

            // Expect that the concerns list is not updated
            expect($scope.concerns).toEqual([]);

        });

        /**
         * Test to ensure that the list of concerns is not updated when an invalid page offset is provided
         */
        it('Request concerns with non-positive page limit test', function() {

            var controller = $controller('InboxCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concernRequest.accessToken = "fakeAccessToken";
            $scope.concernRequest.limit = 0;
            $scope.updateConcernList();

            // Expect that the concerns list is not updated
            expect($scope.concerns).toEqual([]);

        });
    });

    /**
     * Unit tests for the inbox controllers nextPage, previousPage, and refresh
     * functions for fetching concerns from the backend using the Admin API.
     */
    describe('Inbox paging tests', function() {

            /**
         * Test to check that the list of concerns is repopulated from the AdminApi when refresh is called.
         */
        it('Refresh concerns test', function() {

            var controller = $controller('InboxCtrl', { $scope: $scope, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concernRequest.accessToken = "fakeAccessToken";
            $scope.concerns = [];
            $scope.refresh();

            // Expect that the concerns list was repopulated
            expect($scope.concerns).toEqual(['test1', 'test2']);
        });

        /**
         * Test to check that a URL change occurs with a new offset occurs when nextPage() is called.
         */
        it('Next page test', function() {

            var limit = 25;
            var offset = 0;

            // Mock out location to catch the page redirect
            var $location = {
                url: function(path) {
                    expect(path).toEqual('/inbox/' + limit + '/' + limit)
                }
            };

            var controller = $controller('InboxCtrl', { $scope: $scope, $location: $location, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concernRequest.accessToken = "fakeAccessToken";
            $scope.concernRequest.offset = offset;
            $scope.concernRequest.limit = limit;

            $scope.nextPage();
        });

        /**
         * Test to check that a URL change occurs with a new offset occurs when previousPage() is called.
         */
        it('Previous page test', function() {

            var limit = 25;
            var offset = 25;

            // Mock out location to catch the page redirect
            var $location = {
                url: function(path) {
                    expect(path).toEqual('/inbox/' + (offset - limit) + '/' + limit)
                }
            };

            var controller = $controller('InboxCtrl', { $scope: $scope, $location: $location, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concernRequest.accessToken = "fakeAccessToken";
            $scope.concernRequest.offset = offset;
            $scope.concernRequest.limit = limit;

            $scope.previousPage();
        });

        /**
         * Test to check that the previousPage request is ignored if it would result in a negative page offset.
         */
        it('Invalid previous page test', function() {

            // Mock out location to catch the page redirect
            var $location = {
                url: function(path) {}
            };
            spyOn($location, 'url');

            var controller = $controller('InboxCtrl', { $scope: $scope, $location: $location, firebase: firebaseMock, adminApi: adminApiMock });

            $scope.concernRequest.accessToken = "fakeAccessToken";
            $scope.concernRequest.offset = 0;
            $scope.concernRequest.limit = 25;

            $scope.previousPage();

            expect($location.url).not.toHaveBeenCalled();
        });
    });
});