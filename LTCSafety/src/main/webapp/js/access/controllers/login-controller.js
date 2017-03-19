
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

        if ($scope.user == null) {
            throw new Error("Attempted to sign in an account with no user credentials.");
        }

        // If the element can't be found no error should be thrown unless sign in fails
        var loginError = document.getElementById('login-error');
        if (loginError != null) {
            loginError.style.display = "none";
        }

        var result = firebase.auth().signInWithEmailAndPassword($scope.user.email, $scope.user.password);
        result.catch(function(error) {

            $scope.errorMessage = error.message;
            $scope.$apply();

            if (loginError == null) {
                throw new Error("Unable to locate login-error element.");
            }
            loginError.style.display = "block";
        });
    };
});
