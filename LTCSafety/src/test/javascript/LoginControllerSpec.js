/**
 * Created by Allan Kerr on 2017-02-12.
 *
 * This spec contains unit tests for the login controller of the access app module.
 * This handles testing for the LTC Safety Admin website's login form prior
 * to accessing the dashboard.
 *
 */
describe("Login Controller", function() {

    beforeEach(module('accessApp'));

    var $controller;
    var $scope;
    var firebase;

    /**
     * Setup the mock objects used for all tests.
     */
    beforeEach(inject(function(_$controller_, _firebase_){

        $scope = {
            $apply : function() {}
        };
        $controller = _$controller_;
        firebase = _firebase_;
    }));

    /**
     * Tests for successful and failed sign ins
     */
    describe('Sign in tests', function() {

        /**
         * Test that sign on successful sign in with password no errors are thrown
         * and the appropriate firebase call is made.
         */
        it('Successful sign in test', function() {

            testSignIn(false, function(){
                expect(firebase.auth().signInWithEmailAndPassword).toHaveBeenCalled();
            });
        });

        /**
         * Test that on failed login, the error is caught and handled appropriately by
         * displaying a login-error element.
         */
        it('Failed sign in test', function() {

            var testCase = function () {
                testSignIn(true, function(){});
            };
            // This error will be thrown due to the inability to access the access.html page while running unit tests
            expect(testCase).toThrow(new Error("Unable to locate login-error element."));
        });

        /**
         * Helper function to test the sign in function
         * @param forceFail True if the test case should simulate a failed sign in
         * @param expections Callback block that expectations can be put in to ensure they hold after running the test.
         */
        var testSignIn = function(forceFail, expections) {

            // Mock out firebase sign in to simulate success
            var firebaseAuth = {
                signInWithEmailAndPassword : function(email, password) {}
            };
            firebase.auth = function() {
                return firebaseAuth;
            };

            // Mock out firebase and only perform the catch block if an error is to be simulated
            spyOn(firebase.auth(), 'signInWithEmailAndPassword').and.returnValue({
                catch : function(callback) {
                    if (forceFail) {
                        callback('error message');
                    }
                }
            });

            $controller('LoginCtrl', { $scope: $scope, firebase : firebase });

            $scope.user.email = 'email@test.com';
            $scope.user.password = 'password';

            $scope.signIn();

            expections();
        };
    });
});
