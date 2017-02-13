
var safetyApp = angular.module('safetyApp', ['ngRoute']).
    config(['$locationProvider', '$routeProvider', function config($locationProvider, $routeProvider) {

        $locationProvider.hashPrefix('!');

        var parseUnsignedInt = function (defer, value) {

            var intValue = parseInt(value, 10);
            if (!isNaN(intValue) && intValue >= 0) {
                defer.resolve(intValue);
            } else {
                defer.reject('Expected unsigned int. Found: ' + value);
            }
            return defer.promise;
        };

        $routeProvider.when('/inbox/:page/:limit', {
            templateUrl: '/inbox.html',
            resolve: {
                page: function ($q, $route){
                    return parseUnsignedInt($q.defer(), $route.current.params.page);
                },
                limit: function ($q, $route){
                    return parseUnsignedInt($q.defer(), $route.current.params.limit);
                }
            }
        }).otherwise({
            redirectTo: '/inbox/0/25'
        });
    }]);

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

safetyApp.factory('adminApi', function() {
    return gapi.client.admin;
});
