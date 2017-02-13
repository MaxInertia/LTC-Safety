/**
 * @fileoverview
 * Provides methods for the Hello Endpoints sample UI and interaction with the admin api
 */

/** google global namespace for Google projects. */
var google = google || {};

/** appengine namespace for Google Developer Relations projects. */
google.appen = google.appen || {};

/** hello namespace for this sample. */
google.appen.admini = google.appen.admini || {};



google.appen.admini.CLIENT_ID = '918019074217-4pqjkjgf5fnn152eqlrr8b5pqoj39k1p.apps.googleusercontent.com';
google.appen.admini.SCOPES = 'https://www.googleapis.com/auth/userinfo.email';

google.appen.admini.Say = function(){
    console.log('Say something');
};


google.appen.admini.requestConcern = function(){
    gapi.client.admin.requestConcernList().execute(
        function(resp){
            console.log(resp);
        });
};

/**
 * Initializes the application.
 * @param {string} apiRoot Root of the API's path.
 */
google.appen.admini.init = function(apiRoot) {
  // Loads the OAuth and admin APIs asynchronously, and triggers login
  // when they have completed.
    var callback = function(){
        google.appen.admini.requestConcern();
    }
    gapi.client.load('admin', 'v1', callback, apiRoot);
};




