/**
 * The root controller is used as an app-wide controller for the Angular JS safetyApp module.
 * The root controller is applied to the body that contains all partials.
 * All control functionality that needs to exist outside of the partial views should be defined here.
 */
safetyApp.controller('RootCtrl',
    function RootCtrl($scope, $location, $window, auth) {

        /**
         * Sign out the currently signed in account.
         * @pre auth.account != null
         * @post The current account has been signed out triggering onAuthStateChanged when it finishes.
         */
        $scope.signOut = function () {

            if (auth.account == null) {
                throw new Error("Attempted to sign out of an account that is not signed in.");
            }
            auth.signOut();
        };

        /**
         * Send a verification email to the currently signed in account's email address.
         * @pre auth.account != null
         */
        $scope.sendVerificationEmail = function () {

            if (auth.account == null) {
                throw new Error("Attempted to send verification email to an account that is not signed in.");
            }
            auth.sendVerificationEmail();
        };

        auth.onAuthStateChanged(function (account) {
            if (!account) {
                $window.location.replace("/");
            } else {
                if (!account.emailVerified) {
                    $scope.showTab('Splash-Email-Verification-Tab');
                } else if (account.permissions != 'ADMIN') {
                    $scope.showTab('Splash-Status-Verification-Tab');
                } else {
                    $scope.hideSplashScreen();
                    $location.path('/inbox/0/25');
                }
                $scope.$apply();
            }
        });

        $scope.verifyAccount = function () {

            if (auth.account == null) {
                throw new Error("Attempted to verify an account that is not signed in.");
            }
            $scope.showLoadingIndicator();
            auth.refresh();
        };

        $scope.showLoadingIndicator = function () {
            $scope.showTab('Splash-Loading-Tab');
        };

        $scope.showTab = function (tabId) {

            if (tabId == null) {
                throw new Error('Attempted to show a tab with no id.');
            }
            var tab = document.getElementById(tabId);
            if (tab == null) {
                throw new Error('Attempted to show a non-existent tab.');
            }
            tab.click();
        };

        $scope.hideSplashScreen = function () {

            var ix = Webflow.require('ix');
            var hide = {
                "stepsA": [{
                    "display": "block",
                    "opacity": 0,
                    "transition": "transform 200 ease 0, opacity 200 ease 0"
                }, {
                    "display": "none"
                }], "stepsB": []
            };

            var splashScreen = document.getElementById('Splash-Screen');
            if (splashScreen == null) {
                throw new Error("Attempted to hide non-existent splash screen.");
            }
            ix.run(hide, splashScreen);
        };
    });
