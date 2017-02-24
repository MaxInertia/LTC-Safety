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
     * Attempts to register a
     */
    $scope.register = function () {

        document.getElementById('register-error').style.display = "none";

        if ($scope.newUser.password != $scope.newUser.confirmPassword) {
            $scope.showErrorMessage('The passwords do not match.');
        } else {
            firebase.auth().createUserWithEmailAndPassword($scope.newUser.email, $scope.newUser.password).then(function(user) {

                console.log("CREATED ACCOUNT NEW ACCOUNT");
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
     * @param message The message to be displayed.
     */
    $scope.showErrorMessage = function(message) {
        if (message == null) {
            console.log("Attempted to display an empty registration error message.");
            return;
        }
        $scope.errorMessage = message;
        document.getElementById('register-error').style.display = "block";
    }
});
