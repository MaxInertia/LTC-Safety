/**
 * Created by Allan Kerr on 2017-02-12.
 *
 * This spec contains unit tests for the registration controller which
 * is responsible for the creation of new administrator accounts. This
 * involves testing for both valid and invalid registrations.
 *
 */
describe("Registration Controller", function () {

    beforeEach(module('accessApp'));

    var $controller;
    var $scope;
    var firebase;
    var accountStatus;

    /**
     * Setup the mock objects used for all tests.
     */
    beforeEach(inject(function (_$controller_, _firebase_, _accountStatus_) {

        $scope = {
            $apply: function () {
            }
        };
        $controller = _$controller_;
        firebase = _firebase_;
        accountStatus = _accountStatus_;
    }));

    /**
     * Unit tests for the register function
     * to register new administrator accounts.
     */
    describe('Registration tests', function () {

        /**
         * Test that that the account status is changed to new account
         * and the appropriate firebase call is performed upon
         * successful registration.
         */
        it('Successful registration test', function () {

            var newUser = {
                email: 'email@test.com',
                password: 'password',
                confirmPassword: 'password'
            };
            testRegister(newUser, false, null, function () {
                expect(
                    firebase.auth().createUserWithEmailAndPassword).toHaveBeenCalled();
                expect(accountStatus.newAccount).toEqual(true);
            });
        });

        /**
         * Test that an error message is appropriately changed to displayed
         * when registration fails after the call to firebase. This test is limited
         * by Jasmine being unable to access elements meaning the final $apply
         * and element.style = 'block' lines cannot be unit tested without
         * major work arounds.
         */
        it('Failed firebase registration test', function () {

            var newUser = {
                email: 'email@test.com',
                password: 'password',
                confirmPassword: 'password'
            };
            var testCase = function () {
                testRegister(newUser, true, null, function () {
                    expect(accountStatus.newAccount).toEqual(false);
                });
            };
            // This error will be thrown due to the inability to access the access.html page while running unit tests
            expect(testCase).toThrow(
                new Error("Failed to find element with id register-error."));
        });

        /**
         * Test that the appropriate error message is displayed when attempting
         * to register with an empty email.
         */
        it('Failed email registration test', function () {

            var newUser = {
                email: '',
                password: 'password',
                confirmPassword: 'password'
            };
            testRegister(newUser, true, function () {
                spyOn($scope, 'showErrorMessage');
            }, function () {
                expect($scope.showErrorMessage).toHaveBeenCalledWith(
                    'An email must be provided.');
                expect(accountStatus.newAccount).toEqual(false);
            });
        });

        /**
         * Test that the appropriate error message is displayed when attempting
         * to register with an empty password.
         */
        it('Failed password registration test', function () {

            var newUser = {
                email: 'email@test.com',
                password: '',
                confirmPassword: ''
            };
            testRegister(newUser, true, function () {
                spyOn($scope, 'showErrorMessage');
            }, function () {
                expect($scope.showErrorMessage).toHaveBeenCalledWith(
                    'A password must be provided.');
                expect(accountStatus.newAccount).toEqual(false);
            });
        });

        /**
         * Test that the appropriate error message is displayed when attempting
         * to register with two passwords that don't match.
         */
        it('Failed password match registration test', function () {

            var newUser = {
                email: 'email@test.com',
                password: 'passsd',
                confirmPassword: 'psad'
            };
            testRegister(newUser, true, function () {
                spyOn($scope, 'showErrorMessage');
            }, function () {
                expect($scope.showErrorMessage).toHaveBeenCalledWith(
                    'The passwords do not match.');
                expect(accountStatus.newAccount).toEqual(false);
            });
        });

        /**
         * Helper function for testing the register function by providing test conditions,
         * an opportunity to set up spies, and an opportunit to check expectations.
         * @param newUser The details associated with the newly registering user.
         * @param forceFail Whether the test case should simulate successful or failed login.
         * @param setUpSpies A function that spies can be set up in before register is called.
         * @param setUpExpections A function that is called after register to allow for expections
         *                          to be checked.
         */
        var testRegister = function (newUser, forceFail, setUpSpies,
            setUpExpections) {

            // Mock out firebase sign in to simulate success
            var firebaseAuth = {
                createUserWithEmailAndPassword: function (email, password) {
                }
            };
            firebase.auth = function () {
                return firebaseAuth;
            };

            // Mock out firebase and only perform the catch block if an error is to be simulated
            spyOn(firebase.auth(),
                'createUserWithEmailAndPassword').and.returnValue({
                then: function (success, failure) {
                    if (!forceFail) {
                        success({});
                    } else {
                        failure({
                            message: 'error message'
                        });
                    }
                }
            });

            $controller('RegistrationCtrl', {
                $scope: $scope,
                firebase: firebase,
                accountStatus: accountStatus
            });

            $scope.newUser.email = newUser.email;
            $scope.newUser.password = newUser.password;
            $scope.newUser.confirmPassword = newUser.confirmPassword;

            if (setUpSpies != null) {
                setUpSpies();
            }
            $scope.register();

            if (setUpExpections != null) {
                setUpExpections();
            }
        };
    });
});

