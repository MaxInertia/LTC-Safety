/**
 * The main module that managers routing and injection for all controllers in the LTC-Safety administrator console.
 */
var safetyApp = angular.module('safetyApp', ['ngRoute']).config(
    ['$locationProvider', '$routeProvider',
        function config($locationProvider, $routeProvider) {

            // The prefix used for partial page urls within the Safety App module
            $locationProvider.hashPrefix('!');

            /**
             * Converts an object into an unsigned it and returns a promise that evaluates to the result.
             * This function should be used for safely routing unsigned integers to controllers.
             * @precond defer != null
             * @param defer The defer that the returned promise is created from.
             * @param value The value that is attempted to be converted into an unsigned integer.
             * @returns A promise that can be used to extract the result.
             *      If parsing fails then the promise evaluates to an error message.
             *      If the parsing succeeded then the parsing evaluates to an unsigned integer.
             */
            var parseUnsignedInt = function (defer, value) {

                var intValue = parseInt(value, 10);
                if (!isNaN(intValue) && intValue >= 0) {
                    defer.resolve(intValue);
                } else {
                    defer.reject('Expected unsigned int. Found: ' + value);
                }
                return defer.promise;
            };

            /**
             * The route provider configuration mapping partial page url's to their appropriate HTML pages and controllers.
             */
            $routeProvider.when('/inbox/:page/:limit', {
                templateUrl: '/inbox.html',
                resolve: {
                    page: function ($q, $route) {
                        return parseUnsignedInt($q.defer(),
                            $route.current.params.page);
                    },
                    limit: function ($q, $route) {
                        return parseUnsignedInt($q.defer(),
                            $route.current.params.limit);
                    }
                }
            }).when('/concern-detail/:id', {
                templateUrl: '/concern-detail.html'
            }).otherwise({
                redirectTo: '/'
            });
        }]);

/**
 * The Firebase factory allowing for the Firebase Service to be injected into the safetyApp's controllers.
 */
safetyApp.factory('firebase', function () {

    if (firebase.apps.length == 0) {
        var config = {
            apiKey: "AIzaSyBAIqht-YgwA268IUBxzNRijrM4Kj5rNhs",
            authDomain: "ltc-safety.firebaseapp.com",
            databaseURL: "https://ltc-safety.firebaseio.com",
            storageBucket: "ltc-safety.appspot.com",
            messagingSenderId: "918019074217"
        };
        firebase.initializeApp(config);
    }
    return firebase;
});

/**
 * The authentication service used to determine whether the user is currently signed in
 * and, if an account is signed in, perform account related functionality like refreshing
 * the account, signing out, and sending verification emails.
 */
safetyApp.service('auth', ['firebase', function (firebase) {

    var self = this;

    /**
     * The authStateChanged callback that is called when the firebase auth state changes.
     * This causes the account information to be fetched from the LTC Safety Datastore and
     * all onAuthStateChanged callbacks to be called with the newly fetched account data.
     */
    firebase.auth().onAuthStateChanged(function (firebaseUser) {
        if (firebaseUser) {

            firebaseUser.getToken().then(function (rawToken) {

                var request = {
                    accessToken: rawToken
                };
                self.requestAccount(request, function (account) {
                        self.account = account;

                        callbacks.forEach(function (callback) {
                            callback(account);
                        });
                    }
                );
            });
        } else {
            self.account = null;
            callbacks.forEach(function (callback) {
                callback(null);
            });
        }
    });

    this.requestAccount = function(request, callback) {
        gapi.client.admin.requestAccount(request).execute(callback);
    };

    /**
     * The array of callbacks to call when the account auth state changes.
     * @type {Array}
     */
    var callbacks = [];

    /**
     * Add an auth state changed callback that is notified when the account's auth state changes.
     * @param callback The callback to perform when the auth state changes.
     * @pre callback != null
     * @post The callback has been added to the array of callbacks.
     */
    this.onAuthStateChanged = function (callback) {

        if (callback == null) {
            throw new Error("Attempted to add a null onAuthStateChanged callback.");
        }
        var index = callbacks.indexOf(callback);
        if (index < 0) {
            callbacks.push(callback);
        }
    };

    /**
     * The currently signed in account or null if no account is signed.
     * @type {null}
     */
    this.account = null;

    /**
     * Refresh the current signed in account to refresh its account permissions status and its email verification status.
     * @pre this.account != null
     * @post All onAuthStateChanged callbacks are called upon successful refresh with the updated account object.
     */
    this.refresh = function () {
        if (this.account == null) {
            throw new Error("Attempted to refresh an account that was not signed in.");
        }
        var firebaseUser = firebase.auth().currentUser;
        firebaseUser.reload().then(function () {
            firebaseUser.getToken(true);
        });
    };

    /**
     * Send a verification email to the currently signed in account's email address.
     * @pre this.account != null

     * @returns A promise that completes when the email has been sent.
     */
    this.sendVerificationEmail = function () {
        if (this.account == null) {
            throw new Error("Attempted to send verification email to an account that was not signed in.");
        }
        return firebase.auth().currentUser.sendEmailVerification();
    };

    /**
     * Sign out of the currently signed in account.
     * @pre this.account != null
     * @post All onAuthStateChanged callbacks are called upon successful sign out with a null account.
     */
    this.signOut = function () {
        if (this.account == null) {
            throw new Error("Attempted to sign out of an account that was not signed in.");
        }
        firebase.auth().signOut();
    };
}]);

/**
 * The Admin API factory allowing for the Admin API Service to be injected into the safetyApp's controllers.
 */
safetyApp.factory('adminApi', function () {
    return gapi.client.admin;
});
