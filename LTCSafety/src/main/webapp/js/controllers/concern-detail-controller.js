/**
 * The inbox controller is responsible for managing the concerns in the admin inbox.
 * This functions in this controller are responsible for querying for concerns.
 */
safetyApp.controller('ConcernDetailCtrl', function ConcernDetailCtrl($scope, $location, $routeParams, firebase, adminApi) {

    /**
     * The list of concerns that is displayed in the current inbox page.
     * @type {Array}
     */
    $scope.concerns = [];

    /**
     *  The auth value used for accessing the current user.
     *  All functions in this controller require the user to be authenticated.
     */
    $scope.auth = firebase.auth();

    /**
     * The concern request used to populate the current concerns list.
     *
     * accessToken: The users Firebase access token to verify that the request is authenticated
     * limit: The number of concerns that should be returned in the request
     * page: The index of the concern that the request should start at.
     *
     * @type {{accessToken: null, limit: Number, offset: Number}}
     */
    $scope.concernRequest = {
        accessToken : null
    };


    /**
     * The callback when the user auth state changes causing the list of concerns to be updated.
     */
    $scope.auth.onAuthStateChanged(function (firebaseUser) {

        if (firebaseUser) {

            // Update the concern request to have the most recent token
            const promise = firebaseUser.getToken();
            promise.then(function (rawToken) {
                $scope.concernRequest.accessToken = rawToken;

                // Update the list of concerns
                $scope.updateConcernList();
            });
        }
    });


    /**
     * Updates the list of concerns using the existing concern request.
     *
     * @precond $scope.concernRequest.accessToken is a valid Firebase token
     * @precond $scope.concernRequest.accessToken != null
     */
    $scope.updateConcernList = function () {

        if ($scope.concernRequest.accessToken == null) {
            console.log("Attempted to fetch the concerns list with a null access token.");
            return;
        }
        adminApi.requestConcernList($scope.concernRequest).execute(
            function (resp) {
                $scope.concerns = resp.items;
                $scope.identifier = $routeParams.id;
                console.log($scope.identifier);
                $scope.$apply();
            }
        );
    };
});
