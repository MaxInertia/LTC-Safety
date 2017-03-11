/**
 * Created by allankerr on 2017-02-12.
 *
 * The unit tests for the root controller. This consists of tests for functionality used across
 * all partials in the safetyApp AngularJS module such as authentication.
 */
describe("Root Controller", function() {

    beforeEach(module('safetyApp'));

    var $controller;
    var $scope;
    var $window;
    var auth;

    /**
     * Setup the mock objects used for all tests.
     */
    beforeEach(inject(function(_$controller_){

        $controller = _$controller_;

        $scope = {
            $apply : function() {}
        };

        $window = {
            location : {
                replace : function(path) {}
            }
        };

        auth = {
            onAuthStateChanged : function(callback) {}
        };
    }));

    /**
     * Unit tests for the root controllers authentication redirects and sign out functionality.
     */
    describe('Authenticated tests', function() {

        beforeEach(inject(function(){
            auth.account = {};
        }));

        /**
         * Test that the auth service is sent a verification email when the root controller is told to send a verification email.
         */
        it('Send verification email test', function() {

            auth.sendVerificationEmail = function() {};
            spyOn(auth, 'sendVerificationEmail');

            var controller = $controller('RootCtrl', { $scope: $scope, auth : auth});
            $scope.sendVerificationEmail();

            expect(auth.sendVerificationEmail).toHaveBeenCalled();
        });

        /**
         * Test that auth service is signed out when the root controller is told to sign out the currently signed in account.
         */
        it('Sign out test', function() {

            auth.signOut = function() {};
            spyOn(auth, 'signOut');

            var controller = $controller('RootCtrl', { $scope: $scope, auth : auth});
            $scope.signOut();

            expect(auth.signOut).toHaveBeenCalled();
        });

        /**
         * Test that the email verification tab is displayed when the
         * account has email verified as false.
         */
        it('Auth state changed test without verified email', function() {

            $scope.showTab = function (tabName) {};
            spyOn($scope, 'showTab');
            spyOn($scope, '$apply');

            var account = {
                emailVerified : false,
                permissions : 'ADMIN'
            };
            testAuthStateChanged(account, function(){
                expect($scope.showTab).toHaveBeenCalledWith('Splash-Email-Verification-Tab');
                expect($scope.$apply).toHaveBeenCalled();
            });
        });

        /**
         * Test that the unverified permissions tab is displayed when the
         * account doesn't have admin privileges.
         */
        it('Auth state changed test without admin', function() {

            $scope.showTab = function (tabName) {};
            spyOn($scope, 'showTab');
            spyOn($scope, '$apply');

            var account = {
                emailVerified : true,
                permissions : 'UNVERIFIED'
            };
            testAuthStateChanged(account, function(){
                expect($scope.showTab).toHaveBeenCalledWith('Splash-Status-Verification-Tab');
                expect($scope.$apply).toHaveBeenCalled();
            });
        });

        /**
         * Test that the splash screen is hidden to reveal the dashboard when a verified account signs in.
         */
        it('Auth state changed test without admin', function() {

            $scope.hideSplashScreen = function () {};
            spyOn($scope, 'hideSplashScreen');

            var account = {
                emailVerified : true,
                permissions : 'ADMIN'
            };
            testAuthStateChanged(account, function(){
                expect($scope.hideSplashScreen).toHaveBeenCalled();
            });
        });

        /**
         * Verify that the account is refreshed when verify account is called.
         */
        it('Verify test', function() {

            auth.refresh = function() {};
            spyOn(auth, 'refresh');

            var controller = $controller('RootCtrl', { $scope: $scope, auth : auth});

            spyOn($scope, 'showLoadingIndicator');

            $scope.verifyAccount();

            expect(auth.refresh).toHaveBeenCalled();
            expect($scope.showLoadingIndicator).toHaveBeenCalled();
        });
    });

    /**
     * Unit tests for the root controller's functions while in an unauthenticated state.
     */
    describe('Unauthenticated tests', function() {

        /**
         * Test that an error is thrown when attempting to send a verification email to an account that is not signed in.
         */
        it('Send verification email test', function() {

            auth.sendVerificationEmail = function() {};
            spyOn(auth, 'sendVerificationEmail');

            var controller = $controller('RootCtrl', { $scope: $scope, auth : auth});

            expect($scope.sendVerificationEmail).toThrow(new Error("Attempted to send verification email to an account that is not signed in."));
            expect(auth.sendVerificationEmail).not.toHaveBeenCalled();
        });

        /**
         * Test that an error is thrown when attempting to sign out of an account that is not signed in.
         */
        it('Sign out test', function() {

            auth.signOut = function() {};
            spyOn(auth, 'signOut');

            var controller = $controller('RootCtrl', { $scope: $scope, auth : auth});

            expect($scope.signOut).toThrow(new Error("Attempted to sign out of an account that is not signed in."));
            expect(auth.signOut).not.toHaveBeenCalled();
        });

        /**
         * Verify that an error is thrown when attempted to check the verification status of an account that isn't signed in.
         */
        it('Verify test', function() {

            auth.refresh = function() {};
            spyOn(auth, 'refresh');

            var controller = $controller('RootCtrl', { $scope: $scope, auth : auth});

            expect($scope.verifyAccount).toThrow(new Error("Attempted to verify an account that is not signed in."));
            expect(auth.refresh).not.toHaveBeenCalled();
        });

        /**
         * Test that the window is redirected to the access page when the auth state changes to no account.
         */
        it('Auth state changed test with out account', function() {

            spyOn($window.location, 'replace');

            testAuthStateChanged(null, function(){
                expect($window.location.replace).toHaveBeenCalledWith('/');
            });
        });
    });

    /**
     * Unit tests for changing interface states in the root controller.
     * This involves transitioning between tabs and hiding the splash screen.
     */
    describe('Interface state tests', function() {

        /**
         * Test that the loading indicator tab is shown when calling
         * show loading indicator.
         */
        it('Show loading indicator test', function () {

            $controller('RootCtrl', { $scope: $scope, auth : auth});
            spyOn($scope, 'showTab');
            $scope.showLoadingIndicator();

            expect($scope.showTab).toHaveBeenCalledWith('Splash-Loading-Tab');
        });

        /**
         * Test that an error is thrown when a non-existent tab is shown.
         * A successful test cannot be done because Jasmine cannot get elements
         * defined in home.html.
         */
        it('Show tab test', function () {

            $controller('RootCtrl', { $scope: $scope, auth : auth});
            var showTabCall = function() {
                $scope.showTab('Splash-Loading-Tab');
            };
            expect(showTabCall).toThrow(new Error('Attempted to show a non-existent tab.'));
        });

        /**
         * Test that an error is thrown when hiding the splash screen. This is because
         * the Jasmine tests do not have access to home.html meaning getElementById
         * fails.
         */
        it('Hide splash screen test', function () {

            $controller('RootCtrl', { $scope: $scope, auth : auth});
            expect($scope.hideSplashScreen).toThrow(new Error("Attempted to hide non-existent splash screen."));
        });
    });

    /**
     * Helper function to test auth state changed with an account and defined expectations.
     * @param account The account object used for the auth state changed callback.
     * @param expectationsCallback A function containing expectations that are verified after the callback completes.
     */
    var testAuthStateChanged = function(account, expectationsCallback) {

        auth.onAuthStateChanged = function(callback) {
            callback(account);
            expectationsCallback();
        };
        $controller('RootCtrl', { $scope: $scope, $window : $window, auth : auth});
    };
});
