// JavaScript File fore firebase setup
(function(){
	// Initialize Firebase
	var config = {
    apiKey: "AIzaSyBAIqht-YgwA268IUBxzNRijrM4Kj5rNhs",
    authDomain: "ltc-safety.firebaseapp.com",
    databaseURL: "https://ltc-safety.firebaseio.com",
    storageBucket: "ltc-safety.appspot.com",
    messagingSenderId: "918019074217"
    };
	firebase.initializeApp(config);
	//check login status
	firebase.auth().onAuthStateChanged(firebaseUser =>{
		if(!firebaseUser){
			console.log('not log in');
			window.location.replace("index.html");		
		}
	});
}());