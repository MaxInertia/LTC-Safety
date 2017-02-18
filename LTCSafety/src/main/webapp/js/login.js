/**
 * The login module used for administrator authentication to access the administrator dashboard.
 */
var app = angular.module('loginApp', []);
app.controller('loginCtrl', function ($scope, $location) {

    var config = {
        apiKey: "AIzaSyBAIqht-YgwA268IUBxzNRijrM4Kj5rNhs",
        authDomain: "ltc-safety.firebaseapp.com",
        databaseURL: "https://ltc-safety.firebaseio.com",
        storageBucket: "ltc-safety.appspot.com",
        messagingSenderId: "918019074217"
    };
    firebase.initializeApp(config);

    /**
     *  The auth value used for authenticating user credentials and receiving authenitication status changes.
     */
    $scope.auth = firebase.auth();

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
     * @postcond The user is redirected to home.html if the provided credentials are valid.
     *           If the credentials are invalid then an error message is displayed.
     */
    $scope.signIn = function () {

        const promise = $scope.auth.signInWithEmailAndPassword($scope.user.email, $scope.user.password);
        //Catch error if cannot sign in
        promise.catch(function (e) {
            document.getElementById('invalid-credentials').style.display = "block";
        });
    };

    /**
     * The callback when the Firebase auth changes to authenticated to redirect the administrator to the dashboard.
     */
    $scope.auth.onAuthStateChanged(function (firebaseUser) {
        if (firebaseUser) {
            window.location.replace("home.html");
        }
    });
});