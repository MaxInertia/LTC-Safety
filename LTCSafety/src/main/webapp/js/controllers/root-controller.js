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

        /**
         * The callback when the account authorization state changes. This callback is used
         * to determine whether to hide the splash screen and reveal the administrator dashboard
         * or display the appropriate tab denying the account access.
         */
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

        /**
         * Check the verification status of the currently signed in account. This may be done to check
         * whether the account's email verification status or permissions have changed since the
         * initial verification.
         * @pre auth.account != null meaning an account is currently signed in.
         * @post The loading indicator tab is displayed.
         * @post The asynchronous account refresh process has been started which will trigger onAuthStateChanged.
         */
        $scope.verifyAccount = function () {

            if (auth.account == null) {
                throw new Error("Attempted to verify an account that is not signed in.");
            }
            $scope.showLoadingIndicator();
            auth.refresh();
        };

        /**
         * Shows the verification splash screen's loading indicator tab
         * to indicate that the sign in is being handled.
         * @pre none
         * @post The loading tab has been displayed in the verification splash screen and all other tabs have been hidden.
         */
        $scope.showLoadingIndicator = function () {
            $scope.showTab('Splash-Loading-Tab');
        };

        /**
         * Shows a tab within the verification splash screen based on its id. Different tabs are shown
         * based on the accounts verification status. Existing tabs include a tab with a loading indicator,
         * a tab shown when an account isn't email verified, and a tab when the account hasn't been verified yet.
         * @pre tabId != null and tabId is not empty
         * @pre tabId corresponds to one of the existing HTML tab identifiers in the splash screen tab group.
         * @param tabId The HTML identifier for the tab element.
         * @post The tab with tabId has been shown and the previously active tab has been hidden.
         */
        $scope.showTab = function (tabId) {

            if (tabId == null && tabId.length >= 0) {
                throw new Error('Attempted to show a tab with no id.');
            }
            var tab = document.getElementById(tabId);
            if (tab == null) {
                throw new Error('Attempted to show a non-existent tab.');
            }
            tab.click();
        };

        /**
         * Hides the verifications splash screen to make the administrator dashboard visible.
         * @pre none
         * @post The splash screen will have display: none and 0% opacity after 200 ms.
         */
        $scope.hideSplashScreen = function () {

            var splashScreen = document.getElementById('Splash-Screen');
            if (splashScreen == null) {
                throw new Error("Attempted to hide non-existent splash screen.");
            }
            $scope.hide(splashScreen);
        };

        /**
         * The string bound to the error message text block in the modal error popup.
         * This value is set whenever a new modal error is displayed and cleared
         * when the popup is closed.
         */
        $scope.modalError = null;

        /**
         * Show the modal error used for displaying pop-up errors to users.
         * @pre message != null andd message is not empty
         * @post The modal error will have display: block and 100% opacity after 200 ms.
         * @post $scope.modalError == message
         */
        $scope.showModalError = function(message) {

            if (message == null || message.length <= 0) {
                throw new Error("Attempted to show a modal error with a null or empty error message.");
            }

            $scope.$apply(function() {
                $scope.modalError = message;
            });

            var modal = document.getElementById('Modal-Error');
            if (modal == null) {
                throw new Error("Attempted to show non-existent error modal.");
            }
            $scope.show(modal);

            if ($scope.modalError != message) {
                throw new Error("The modal error did not match the specified error aftering being shown.");
            }
        };

        /**
         * Hide the modal error used for displaying pop-up errors to users.
         * @pre none
         * @post The modal error will have display: none and 0% opacity after 200 ms.
         * @post $scope.modalError == null
         */
        $scope.hideModalError = function() {

            var modal = document.getElementById('Modal-Error');
            if (modal == null) {
                throw new Error("Attempted to hide non-existent error modal.");
            }
            $scope.hide(modal);
            $scope.modalError = null;

            if ($scope.modalError != null) {
                throw new Error("Expected the modal error message to be null after hiding.");
            }
        };

        /**
         * Hide an element with a fade in animation causing it to transition to display: none
         * @pre element != null
         * @param element The element to be hidden.
         * @post The element will be at 0% opacity with display hidden after 200 ms
         */
        $scope.hide = function(element) {

            if (element == null) {
                throw new Error("Attempted to hide a null element.");
            }

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
            ix.run(hide, element);
        };

        /**
         * Show an element with a fade in animation causing it to transition to display: block
         * @pre element != null
         * @param element The element to be shown.
         * @post The element will be at 100% opacity with display block in 200 ms
         */
        $scope.show = function(element) {

            if (element == null) {
                throw new Error("Attempted to show a null element.");
            }

            var ix = Webflow.require('ix');
            var show = {
                "stepsA":[{
                    "display":"block",
                    "opacity":1,
                    "transition":"opacity 200 ease 0"
                }],"stepsB":[]
            };
            ix.run(show, element);
        };

        /**
         * The status names function maps the enum status values to human readable strings.
         * @param key The enum value of a status.
         * @precond key != null && !key.isEmpty
         * @returns The string representation of the enum value.
         */
        $scope.statusNames = function(key) {

            if (key == null || key.length <= 0) {
                throw new Error("Attempted to get the name of an invalid status enum value.");
            }
            if (key == 'PENDING') {
                return 'Unread'
            } else if (key == 'SEEN') {
                return 'Seen'
            } else if (key == 'RESPONDING24') {
                return 'Responding in 24 Hours'
            } else if (key == 'RESPONDING48') {
                return 'Responding in 48 Hours'
            } else if (key == 'RESPONDING72') {
                return 'Responding in 72 Hours'
            } else if (key == 'RESOLVED') {
                return 'Resolved'
            } else if (key == 'RETRACTED') {
                return 'Retracted'
            } else {
                throw new Error("Received unrecognized status key " + key);
            }
            return key;
        };
    });
