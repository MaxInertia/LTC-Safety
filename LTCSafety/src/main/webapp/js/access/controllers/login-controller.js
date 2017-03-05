
app.controller('LoginCtrl', function ($scope, firebase) {

    /**
     * The email and password pair to be used when the user attempts to sign in.
     * These map to to the email and password textfields.
     *
     * @type {{email: null, password: null}}
     */
    $scope.user = {
        email: null,
        password: null
    };

    /**
     * Attempts to sign in the user using the current credentials in $scope.user
     * @precond $scope.user != null
     * @postcond The user is redirected to home.html if the provided credentials are valid.
     *           If the credentials are invalid then an error message is displayed.
     */
    $scope.signIn = function () {

        console.assert($scope.user != null, "Attempted to sign in an account with no user credentials.");

        document.getElementById('login-error').style.display = "none";

        firebase.auth().signInWithEmailAndPassword($scope.user.email, $scope.user.password).catch(function(error) {

            $scope.errorMessage = error.message;
            $scope.$apply();

            document.getElementById('login-error').style.display = "block";
        });
    };
});
