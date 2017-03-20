/**
 * Created by Allan Kerr on 2017-02-12.
 *
 * This spec contains unit tests for the forgot password controller.
 * It contains the tests for successful and failed sending of password
 * reset emails to account email addresses.
 */
describe("Forgot Password Controller", function() {

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
     * Tests for successful and failed password reset email requests
     */
    describe('Forgot password tests', function() {

        /**
         * Test that on successful password reset request
         * no errors are thrown and the appropriate firebase
         * function is called.
         */
        it('Successful password reset email test', function() {

            testForgotPassword(false, function(){
                expect(firebase.auth().sendPasswordResetEmail).toHaveBeenCalled();
            });
        });

        /**
         * Test that on failed password reset request, an error message
         * is displayed to the user.
         */
        it('Failed password reset email test', function() {

            var testCase = function () {
                testForgotPassword(true, function(){});
            };
            // This error will be thrown due to the inability to access the access.html page while running unit tests
            expect(testCase).toThrow(new Error("Unable to locate forgot-password-error element."));
        });

        /**
         * Helper function to test the forgot password function.
         * @param forceFail True if the test case should simulate a failed sign in
         * @param expections Callback block that expectations can be put in to ensure they hold after running the test.
         */
        var testForgotPassword = function(forceFail, expections) {

            // Mock out firebase sign in to simulate success
            var firebaseAuth = {
                sendPasswordResetEmail : function(email) {}
            };
            firebase.auth = function() {
                return firebaseAuth;
            };

            // Mock out firebase and only perform the catch block if an error is to be simulated
            spyOn(firebase.auth(), 'sendPasswordResetEmail').and.returnValue({
                catch : function(callback) {
                    if (forceFail) {
                        callback('error message');
                    }
                }
            });

            $controller('ForgotPasswordCtrl', { $scope: $scope, firebase : firebase });

            $scope.email = 'email@test.com';
            if (forceFail) {
                $scope.errorMessage = 'An error message';
            }
            $scope.forgotPassword();

            expections();
        };
    });
});
