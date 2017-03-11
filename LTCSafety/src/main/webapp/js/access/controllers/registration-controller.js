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

        if ($scope.newUser == null) {
            throw new Error("Attempted to register an account with no user credentials.");
        }

        var element = document.getElementById('register-error');
        // Don't throw an error if the error may not need to be displayed
        if (element != null) {
            element.style.display = "none";
        }

        if ($scope.newUser.email == null || $scope.newUser.email.length == 0) {
            $scope.showErrorMessage('An email must be provided.');
        } else if ($scope.newUser.password == null || $scope.newUser.password.length == 0) {
            $scope.showErrorMessage('A password must be provided.');
        } else if ($scope.newUser.password != $scope.newUser.confirmPassword) {
            $scope.showErrorMessage('The passwords do not match.');
        } else {
            firebase.auth().createUserWithEmailAndPassword($scope.newUser.email, $scope.newUser.password).then(function(user) {
                accountStatus.newAccount = true;
            }, function(error) {
                $scope.showErrorMessage(error.message);
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

        if (message == null) {
            throw new Error("Attempted to display an empty registration error message.");
        }

        $scope.errorMessage = message;

        var element = document.getElementById('register-error');
        if (element == null) {
            throw new Error("Failed to find element with id register-error.");
        }
        element.display = "block";
        $scope.$apply();
    }
});
