/**
 * The concern detail controller is responsible for the displaying the details of a single concern. This
 * includes additional information not shown in the inbox list view. Clicking on a concern in the inbox list
 * will cause the detail view to be displayed.
 */
safetyApp.controller('ConcernDetailCtrl', function ConcernDetailCtrl($scope, $location, $routeParams, firebase, adminApi) {

    /**
     * The concern data that is received from the concern request
     * and displayed to the administrator.
     *
     * @type {null}
     */
    $scope.concern = null;


    /**
     *  The auth value used for accessing the current user.
     *  All functions in this controller require the user to be authenticated.
     */
    $scope.auth = firebase.auth();

    /**
     * The request used to load the concern from the admin API.
     * accessToken: The token used to authenticate the request.
     * concernId: The unique identifier of the concern that should be requested.
     *
     * @type {{accessToken: null, concernId}}
     */
    $scope.concernRequest = {
        accessToken : null,
        concernId : $routeParams.id
    };

    /**
     * The status names function maps the enum status values to human readable strings.
     * @param key The enum value of a status.
     * @precond key != null && !key.isEmpty
     * @returns The string representation of the enum value.
     */
    $scope.statusNames = function(key) {

        console.assert(key != null && !key.isEmpty, "Attempted to get the name of an invalid status enum value.");

        // TODO Provide the string values.

        return key;
    };

    /**
     * The callback when the user auth state changes causing the data for the concern to be fetched.
     */
    $scope.auth.onAuthStateChanged(function (firebaseUser) {

        if (firebaseUser) {

            // Update the concern request to have the most recent token
            firebaseUser.getToken().then(function (rawToken) {
                $scope.concernRequest.accessToken = rawToken;

                // Update the list of concerns
                $scope.fetchConcern();
            });
        }
    });

    /**
     * Fetches the concern data from the admin API referenced in the concern request.
     * @precond !$scope.concernRequest.token.isEmpty
     * @precond $scope.concernRequest.concernId != null
     * @postcond The concern detail view has been updated to display the received concern data.
     */
    $scope.fetchConcern = function() {

        if ($scope.concernRequest.accessToken == null || $scope.concernRequest.accessToken.isEmpty) {
            throw new Error("Attempted to fetch a concern without providing an access token.");
        }
        if ($scope.concernRequest.concernId == null) {
            throw new Error("Attempted to fetch a concern with no id.");
        }

        adminApi.requestConcern($scope.concernRequest).execute(
            function (resp) {

                $scope.concern = resp;
                $scope.$apply();
            }
        );
    };
});
