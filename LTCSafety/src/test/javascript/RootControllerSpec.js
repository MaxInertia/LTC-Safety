/**
 * Created by allankerr on 2017-02-12.
 *
 * The unit tests for the root controller. This consists of tests for functionality used across
 * all partials in the safetyApp AngularJS module such as authentication.
 */
describe("Root Controller", function() {

    beforeEach(module('safetyApp'));

    var $controller;

    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;
    }));

    /**
     * Unit tests for the root controllers authentication redirects and sign out functionality.
     */
    describe('Authentication tests', function() {

        /**
         * This test ensures that Firebase's signOut function is called when the root controller calls signOut.
         */
        it('Sign out test', function() {

            var $scope = {};
            var $window = {
                location: {
                    replace: function(path) {}
                }
            };

            // Firebase mock to mock out authentication server calls
            var firebaseMock = {
                auth: function() {
                    return {
                        currentUser : {},
                        signOut : function() {},
                        onAuthStateChanged: function(callback) {}
                    };
                }
            };

            var controller = $controller('RootCtrl', { $scope: $scope, $window : $window, firebase : firebaseMock });

            // Call signout and expect the auth domain to sign out.
            spyOn($scope.auth, 'signOut');
            $scope.signOut();
            expect($scope.auth.signOut).toHaveBeenCalled();

        });
    });
});
