


var displayApp = displayApp || {};


displayApp.controllers = angular.module('displayControllers', []);

displayApp.controllers.controller('logOutCtrl', function ($scope) {
    var config = {
        apiKey: "AIzaSyBAIqht-YgwA268IUBxzNRijrM4Kj5rNhs",
        authDomain: "ltc-safety.firebaseapp.com",
        databaseURL: "https://ltc-safety.firebaseio.com",
        storageBucket: "ltc-safety.appspot.com",
        messagingSenderId: "918019074217"
    };
    firebase.initializeApp(config);

    $scope.auth = firebase.auth();

    //Get Elements
    const btnLogout = document.getElementById('btnLogout');

    //Add logout Event
    btnLogout.addEventListener('click',function(e) {
        $scope.auth.signOut();
    });

    $scope.requestConcern = function(){
        gapi.client.admin.requestConcernList().execute(
            function(resp){
                $scope.$apply(function(){
                  if(resp.error){
                      var errorMessage = resp.error.message || '';
                      $scope.messages = 'Failed to get concern : ' + errorMessage;
                  }
                  else{
                      $scope.concernlist = resp.result;
                  }
                });
                console.log(resp);
                $scope.concernlist = resp.result;

            });
    };

    //Get Elements
    const btnShowList = document.getElementById('btnShowList');

    //Add logout Event
    btnShowList.addEventListener('click',function(e) {
        $scope.requestConcern();
    });

    $scope.auth.onAuthStateChanged(function (firebaseUser) {
        if (firebaseUser) {
            btnLogout.classList.remove('hide');
        }
        else{
            window.location.replace("index.html");
            btnLogout.classList.add('hide');
        }
    });

});