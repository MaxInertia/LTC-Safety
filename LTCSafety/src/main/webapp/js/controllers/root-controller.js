/**
 * The root controller is used as an app-wide controller for the Angular JS safetyApp module.
 * The root controller is applied to the body that contains all partials.
 * All control functionality that needs to exist outside of the partial views should be defined here.
 */
safetyApp.controller('RootCtrl', function RootCtrl($scope, $window, firebase) {

    /**
     * The firebase auth variable used for managing authorization state.
     *
     */
    $scope.auth = firebase.auth();

    /**
     * The sign out function that is triggered with an ng-click from the Sign Out button.
     * @precond firebase.auth().currentUser != null
     * @postcond The user has been redirected to the login page.
     */
    $scope.signOut = function () {

        console.assert(firebase.auth().currentUser != null, "Attempted to sign out when not signed in.");

        $scope.auth.signOut();
    };

    /**
     * The callback when the Firebase auth state changes used for the client side redirect to the login page.
     * @postcond The user has been redirected to the login page if they aren't signed in.
     *           If they are signed in, nothing is done.
     */
    $scope.auth.onAuthStateChanged(function (firebaseUser) {
        if (!firebaseUser) {
            $window.location.replace("/");
        }
    });
});
