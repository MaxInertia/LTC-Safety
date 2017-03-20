/**
 * Tests for the auth service that it properly interfaces
 * with the internal firebase API and properly notifies observers
 * on auth state changes.
 *
 * Created by Allan Kerr on 2017-03-09.
 */
describe("Auth Service", function() {

    beforeEach(module('safetyApp'));

    var auth;
    var firebase;
    var authStateCallback;
    var currentUser;

    /**
     * Setup the mock objects used for all tests.
     */
    beforeEach(inject(function(_auth_, _firebase_,$injector){

        auth = _auth_;
        firebase = _firebase_;

        auth.account = {};

        // Mock firebase user to avoid cross-network calls
        currentUser = {
            sendEmailVerification : function() {},
            reload : function() {
                return {
                    then : function(callback) {
                        callback();
                    }
                }
            },
            getToken : function(refresh) {
                return {
                    then : function(callback) {
                        callback();
                    }
                }
            }
        };

        // Mock the auth object to allow for testing onAuthStateChanged
        var firebaseAuth = {
            onAuthStateChanged: function(callback) {
                authStateCallback = callback;
            },
            signOut:function() {},
            currentUser : currentUser
        };

        firebase.auth = function() {
            return firebaseAuth;
        };
    }));

    /**
     * Unit tests for the root controllers authentication redirects and sign out functionality.
     */
    describe('Authenticated tests', function() {

        /**
         * Test the the firebase account sends a verification email when the auth account is told to.
         */
        it('Send verification email test', function() {

            spyOn(firebase.auth().currentUser, 'sendEmailVerification');

            auth.sendVerificationEmail();

            expect(firebase.auth().currentUser.sendEmailVerification).toHaveBeenCalled();
        });

        /**
         * Test that the firebase account is signed out when signing out the auth account.
         */
        it('Sign out test', function() {

            spyOn(firebase.auth(), 'signOut');

            auth.signOut();

            expect(firebase.auth().signOut).toHaveBeenCalled();
        });

        /**
         * Test that the firebase account's token is refreshed when refreshing the auth account.
         */
        it('Refresh test', function() {

            spyOn(firebase.auth().currentUser, 'getToken');

            auth.refresh();

            expect(firebase.auth().currentUser.getToken).toHaveBeenCalledWith(true);
        });

        /**
         * Tests for the auth state changed callbacks on the auth service.
         */
        describe('Auth state changed tests', function() {

            /**
             * Test that all observers are notified with null
             * when the authentication state changes to unauthenticed.
             */
            it('Unauthenticated state test', function() {

                var observer = {
                    callback : function (account) {}
                };
                spyOn(observer, 'callback');

                auth.onAuthStateChanged(observer.callback);

                authStateCallback(null);

                expect(observer.callback).toHaveBeenCalledWith(null);

            });

            /**
             * Test that all observers are notified with the proper account
             * object when the authentication state changes to being authenticated.
             */
            it('Authenticated state test', function() {

                // Add the callback for authentication state changed
                var observer = {
                    callback1 : function (account) {},
                    callback2 : function (account) {}
                };
                spyOn(observer, 'callback1');
                spyOn(observer, 'callback2');

                // Mock out the gapi request account call
                var mockAccount = {
                    email : "test@email.com"
                };
                auth.requestAccount = function(request, callback) {
                    callback(mockAccount);
                };


                auth.onAuthStateChanged(observer.callback1);
                auth.onAuthStateChanged(observer.callback2);

                // Perform the call and check that the observer callback was notified.
                authStateCallback(currentUser);
                expect(observer.callback1).toHaveBeenCalledWith(mockAccount);
                expect(observer.callback2).toHaveBeenCalledWith(mockAccount);
            });
        });
    });
});