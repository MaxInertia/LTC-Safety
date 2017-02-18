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
            }).otherwise({
                redirectTo: '/inbox/0/25'
            });
        }]);

/**
 * The Firebase factory allowing for the Firebase Service to be injected into the safetyApp's controllers.
 */
safetyApp.factory('firebase', function () {
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
 * The Admin API factory allowing for the Admin API Service to be injected into the safetyApp's controllers.
 */
safetyApp.factory('adminApi', function () {
    return gapi.client.admin;
});
