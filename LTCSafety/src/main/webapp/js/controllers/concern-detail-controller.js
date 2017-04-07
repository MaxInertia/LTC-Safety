/**
 * The concern detail controller is responsible for the displaying the details of a single concern. This
 * includes additional information not shown in the inbox list view. Clicking on a concern in the inbox list
 * will cause the detail view to be displayed.
 */
safetyApp.controller('ConcernDetailCtrl', function ConcernDetailCtrl($scope, $location, $routeParams, firebase, adminApi) {

    Webflow.ready();

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
     * Update the status of the currently viewed concern to a new status.
     * @param status The new status that is being added to the concern.
     * @pre $scope.concern != null, there must be a concern to update its status
     * @pre status != null and status is not empty
     * @post If the request succeeded the concern has been updated locally and on the server to include the new status.
     *       Otherwise, the modal error view has been displayed with a message stating that the status failed to update.
     *
     */
    $scope.updateStatus = function(status) {

        $("nav").removeClass("w--open");
        $("div").removeClass("w--open");

        if (status == null || status.length <= 0) {
            throw new Error("Attempted to update a concern to a null or empty status.");
        }
        if ($scope.concern == null) {
            throw new Error("Attempted to update the status for a null concern.");
        }
        var currentStatus = $scope.concern.statuses[$scope.concern.statuses.length-1].type;
        if (status == currentStatus) {
            return;
        }

        var request = {
            concernStatus : status,
            concernId : $scope.concern.id,
            accessToken : $scope.concernRequest.accessToken
        };

        adminApi.updateConcernStatus(request).execute(
            function (resp) {
                if (resp.error) {
                    $scope.showModalError('Failed to update concern status.');
                } else if (resp.concernId != $scope.concern.id) {
                    $scope.showModalError('Update Failed: Received a response with a mismatched concern identifier.');
                } else {
                    $scope.$apply(function () {
                        $scope.concern.statuses.push(resp.status);
                    });
                }
            }
        );
    };

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

                console.log(resp);
                $scope.concern = resp;
                $scope.$apply();
            }
        );
    };


    /** Toggle the archive status of concern through the admin API
     * @precond $scope.concernRequest.accessToken != null
     * @precond $scope.concernRequest.accessToken is not empty
     * @precond $scope.concernRequest.concernId != null
     * @post If the request succeeded the concern has been updated locally and on the server to be archived if it
     *       was previously unarchived or unarchived if it was archived.
     *       Otherwise, the modal error view has been displayed with a message stating that toggle the status failed.
     */
    $scope.toggleArchived = function() {

        if ($scope.concernRequest.accessToken == null || $scope.concernRequest.accessToken.isEmpty) {
            throw new Error("Attempted to update the archive status of a concern without providing an access token.");
        }
        if ($scope.concernRequest.concernId == null) {
            throw new Error("Attempted to update the archive status of a concern with no id.");
        }
        adminApi.updateArchiveStatus($scope.concernRequest).execute(
            function (resp) {
                if (resp.error) {
                    $scope.showModalError('Failed to toggle the concern archive status.');
                } else {
                    $scope.concern.archived = !$scope.concern.archived;
                    $scope.$apply();
                }
            }
        );
    };
});
