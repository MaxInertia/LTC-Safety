
/**
 * The inbox controller is responsible for managing the concerns in the admin inbox.
 * This functions in this controller are responsible for querying for concerns
 * and switching between concern pages.
 */
safetyApp.controller('InboxCtrl', function InboxCtrl($scope, $location, $routeParams, firebase, adminApi) {

    Webflow.ready();

    /**
     * The list of concerns that is displayed in the current inbox page.
     * @type {Array}
     */
    //$scope.concerns = [];
    $scope.concerns = {
        startIndex : 0,
        endIndex : 0,
        totalItemsCount : 0,
        concernList : []
    };

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
     * offset: The index of the concern that the request should start at.
     * archived: The boolean value of the concern indicate whether it is archived
     * @type {{accessToken: null, limit: Number, offset: Number}}
     */
    $scope.concernRequest = {
        accessToken : null,
        limit : parseInt($routeParams.limit),
        offset : parseInt($routeParams.offset),
        archived: $routeParams.archived
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
     * Updates the existing concern list by querying the Admin API for the most recent set of concerns.
     *
     * @precond $scope.concernRequest.accessToken is a valid Firebase token
     * @precond $scope.concernRequest.accessToken != null
     * @postcond The concern list view has been updated to reload items with the same offset and limit.
     */
    $scope.refresh = function () {

        if ($scope.concernRequest.accessToken == null) {
            throw  new Error("Attempted to refresh the concerns list with a null access token.");
        }

        $scope.updateConcernList();
    };

    /**
     * Fetches the concerns in the next page by querying the Admin API starting at the end of the current page.
     *
     * @precond $scope.concernRequest.accessToken is a valid Firebase token
     * @precond $scope.concernRequest.accessToken != null
     * @precond $scope.concernRequest.archived != null
     * @postcond The concern list view has been updated to show the next page of older concerns.
     */
    $scope.nextPage = function () {

        if ($scope.concernRequest.accessToken == null) {
            throw new Error("Attempted to fetch the next page with a null access token.");
        }

        if ($scope.concernRequest.archived == null){
            throw new Error("Attempted to fetch the previous page with a null archived value")
        }

        var nextOffset = +$scope.concernRequest.offset + +$scope.concernRequest.limit;
        if (nextOffset < $scope.concerns.totalItemsCount) {
            $location.url('/inbox/' + nextOffset + '/' + $scope.concernRequest.limit + '/' + $scope.concernRequest.archived);
        } else {
            console.log("Attempted to fetch a page with an offset greater than the total number of items.");
        }
    };

    /**
     * Fetches the concerns in the previous page by querying the Admin API starting before the start of the current page.
     *
     * @precond $scope.concernRequest.accessToken is a valid Firebase token
     * @precond $scope.concernRequest.accessToken != null
     * @precond $scope.concernRequest.offset >= $scope.concernRequest.limit
     * @precond $scope.concernRequest.archived != null`
     * @postcond The concern list view has been updated to show the previous page of newer concerns.
     */
    $scope.previousPage = function () {

        if ($scope.concernRequest.accessToken == null) {
            throw new Error("Attempted to fetch the previous page with a null access token.");
        }
        if ($scope.concernRequest.archived == null){
            throw new Error("Attempted to fetch the previous page with a null archived value")
        }

        var nextOffset = $scope.concernRequest.offset - $scope.concernRequest.limit;
        if (nextOffset < 0) {
            console.log("Attempted to fetch a page with a negative start index.");
            return;
        }
        $location.url('/inbox/' + nextOffset + '/' + $scope.concernRequest.limit + '/' + $scope.concernRequest.archived);
    };

    /**
     * Select a concern to display all of its details.
     *
     * @precond concern.id != null
     * @postcond The user has been redirected to the concern detail page.
     */
    $scope.concernSelected = function (concern) {

        if (concern == null) {
            throw new Error("Attempted to select a null concern.");
        }

        var identifier = concern.id;
        console.log("Selected " + identifier);
        $location.url('/concern-detail/' + identifier);
    };

    /**
     * Updates the list of concerns using the existing concern request.
     *
     * @precond $scope.concernRequest.accessToken is a valid Firebase token
     * @precond $scope.concernRequest.accessToken != null
     * @precond $scope.concernRequest.offset >= 0
     * @precond $scope.concernRequest.limit > 0
     * @postcond The concern list view has been updated with the loaded list of concerns.
     */
    $scope.updateConcernList = function () {

        if ($scope.concernRequest.accessToken == null) {
            throw new Error("Attempted to refresh the concerns list with a null access token.");
        }
        if ($scope.concernRequest.offset < 0) {
            throw new Error("Attempted to fetch a page with a negative start index.");
        }
        if ($scope.concernRequest.limit <= 0) {
            throw new Error("Attempted to fetch an empty page.");
        }

        adminApi.requestConcernList($scope.concernRequest).execute(
            function (resp) {
                $scope.concerns = resp;
                $scope.$apply();
            }
        );
    };

    /**
     * Gets the name of the current status for a concern.
     * @param concern The concern to get the most recent status for.
     * @pre concern != null
     * @pre concern.statuses.length > 0
     * @returns The name of the concern's most recent status update.
     */
    $scope.currentStatus = function(concern) {

        if (concern == null) {
            throw new Error("Attempted to get the status of a null concern.");
        }
        if (concern.statuses.length <= 0) {
            throw new Error("Attempted to get the status of a concern with no statuses.");
        }
        var key = concern.statuses[concern.statuses.length-1].type;
        return $scope.statusNames(key);
    };
});