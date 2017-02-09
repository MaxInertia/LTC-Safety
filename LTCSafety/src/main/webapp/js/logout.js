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
    firebase.auth().onAuthStateChanged(function (firebaseUser) {
        if (!firebaseUser) {
            console.log('not log in');
            window.location.replace("index.html");
        }
    });

	//Get Elements
	const btnLogout = document.getElementById('btnLogout');

	//Add logout Event
	btnLogout.addEventListener('click',function(e) {
		firebase.auth().signOut();
	});

	//check login status
	firebase.auth().onAuthStateChanged(function(firebaseUser) {
		if(!firebaseUser){
			console.log('not log in');
			window.location.replace("index.html");
			btnLogout.classList.add('hide');			
		}else{
			btnLogout.classList.remove('hide');
		}
	});
}());