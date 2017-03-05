/**
 * The login module used for administrator authentication to access the administrator dashboard.
 */
var app = angular.module('accessApp', []).controller('RootCtrl', function ($scope, firebase, accountStatus) {

    /**
     * The callback when the Firebase auth changes to authenticated to redirect the administrator to the dashboard.
     * @postcond The user has been redirected to the dashboard if the user is signed in.
     *           If the user isn't signed in, nothing happens.
     */
    firebase.auth().onAuthStateChanged(function (user) {
        if (user) {
            if (accountStatus.newAccount) {

                // Send verification email for new accounts
                user.sendEmailVerification().then(function(user) {
                    $scope.redirectToDashboard();
                });
            } else {
                $scope.redirectToDashboard();
            }
        }
    });

    /**
     * Redirects the user to the Administrator Dashboard.
     * @postcond The user has been redirected to the dashboard.
     */
    $scope.redirectToDashboard = function() {
        window.location.replace("home.html");
    }
});


/**
 * The Firebase factory allowing for the Firebase Service to be injected into the accessApp's controllers.
 */
app.factory('firebase', function () {
    var config = {
        apiKey: "AIzaSyBAIqht-YgwA268IUBxzNRijrM4Kj5rNhs",
        authDomain: "ltc-safety.firebaseapp.com",
        databaseURL: "https://ltc-safety.firebaseio.com",
        storageBucket: "ltc-safety.appspot.com",
        messagingSenderId: "918019074217"
    };
    firebase.initializeApp(config);
    return firebase;
});

/**
 * The account status factory allowing for onAuthStateChanged(...) to differentiate between user
 * sign up and user login. If a new account is created then a verification needs to be sent.
 * The account status always defaults to not being a new account.
 */
app.factory('accountStatus', function(){
    var status = {
        newAccount: false
    };
    return status;
});
