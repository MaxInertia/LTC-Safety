/**
 * Created by allankerr on 2017-02-23.
 */

app.controller('ForgotPasswordCtrl', function ($scope, firebase) {

    /**
     * The email and password pair to be used when the user attempts to sign in.
     * These map to to the email and password textfields.
     *
     * @type {{email: null, password: null, confirmPassword: null}}
     */
    $scope.email = null;

    $scope.errorMessage = null;

    /**
     * Attempts to sign in the user using the current credentials in $scope.user
     * @postcond The user is redirected to home.html if the provided credentials are valid
     *           and a password reset email is sent to the specified email address.
     *           If the credentials are invalid then an error message is displayed.
     */
    $scope.forgotPassword = function () {

        // If the element can't be found no error should be thrown unless sending the email fails
        var forgotPasswordError = document.getElementById('forgot-password-error');
        if (forgotPasswordError != null) {
            forgotPasswordError.style.display = "none";
        }

        firebase.auth().sendPasswordResetEmail($scope.email).catch(function(error) {

            $scope.errorMessage = error.message;
            $scope.$apply();

            if (forgotPasswordError == null) {
                throw new Error("Unable to locate forgot-password-error element.");
            }
            forgotPasswordError.style.display = "block";
        });
    };
});
