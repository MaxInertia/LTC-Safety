/**
 * Created by allankerr on 2017-02-23.
 */

app.controller('RegistrationCtrl', function ($scope, firebase, accountStatus) {

    /**
     * The email and password used to create the new administrator account.
     * These map to to the email and password. A third paramter is provided
     * for the confirmation password to ensure that the user didn't mistype
     * their password.
     *
     * @type {{email: null, password: null, confirmPassword: null}}
     */
    $scope.newUser = {
        email: null,
        password: null,
        confirmPassword: null
    };

    /**
     * The error message that is displayed if registration fails.
     * @type {null}
     */
    $scope.errorMessage = null;

    /**
     * Attempts to register a new account with the specified email and password.
     * @precond scope.newUser != null
     * @postcond If a valid email and password were provided a new account is created,
     *           the user is redirected to home.html, and an email is sent to the account
     *           to verify that the creator is the owner of that email.
     *           if the email is associated with an existing account an error message is displayed.
     */
    $scope.register = function () {

        console.assert($scope.newUser != null, "Attempted to register an account with no user credentials.");

        document.getElementById('register-error').style.display = "none";

        if ($scope.newUser.email == null || $scope.newUser.email.isEmpty) {
            $scope.showErrorMessage('An email must be provided.');
        } else if ($scope.newUser.password == null || $scope.newUser.password.isEmpty) {
            $scope.showErrorMessage('A password must be provided.');
        } else if ($scope.newUser.password != $scope.newUser.confirmPassword) {
            $scope.showErrorMessage('The passwords do not match.');
        } else {
            firebase.auth().createUserWithEmailAndPassword($scope.newUser.email, $scope.newUser.password).then(function(user) {
                accountStatus.newAccount = true;
            }, function(error) {
                $scope.showErrorMessage(error.message);
                $scope.$apply();
            });
        }
    };

    /**
     * Display an error message to the user.
     * @precond message != null
     * @postcond The message has been displayed to the user.
     * @param message The message to be displayed.
     */
    $scope.showErrorMessage = function(message) {

        console.assert(message != null, "Attempted to display an empty registration error message.");

        $scope.errorMessage = message;
        document.getElementById('register-error').style.display = "block";
    }
});
