
var app = angular.module('myApp', []);
app.controller('formCtrl', function ($scope, $location) {

    var config = {
        apiKey: "AIzaSyBAIqht-YgwA268IUBxzNRijrM4Kj5rNhs",
        authDomain: "ltc-safety.firebaseapp.com",
        databaseURL: "https://ltc-safety.firebaseio.com",
        storageBucket: "ltc-safety.appspot.com",
        messagingSenderId: "918019074217"
    };
    firebase.initializeApp(config);

    $scope.auth = firebase.auth();

    $scope.user = {
        email: null,
        password: null
    };

    $scope.reset = function () {

        const promise = $scope.auth.signInWithEmailAndPassword($scope.user.email, $scope.user.password);
        //Catch error if cannot sign in
        promise.catch(function (e) {
            document.getElementById('invalid-credentials').style.display = "block";
        });
    };

    $scope.auth.onAuthStateChanged(function (firebaseUser) {
        if (firebaseUser) {
            window.location.replace("home.html");
        }
    });
});