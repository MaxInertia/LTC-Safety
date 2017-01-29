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
	
	//Get elements
	const txtEmail = document.getElementById('txtEmail');
	const txtPassword = document.getElementById('txtPassword');
	const btnLogin = document.getElementById('btnLogin');
	const btnSignUp = document.getElementById('btnSignUp');
	//const btnLogout = document.getElementById('btnLogout');

	//Add login event
	btnLogin.addEventListener('click',e =>{
		//Get email and password
		const email = txtEmail.value;
		const password = txtPassword.value;
		const auth = firebase.auth();

		//Sign in
		const promise = auth.signInWithEmailAndPassword(email,password);
		//Catch error if cannot sign in
		promise.catch(e => console.log(e.message));

	});

	//Add Signup Event
	btnSignUp.addEventListener('click',e =>{
		//Get email and password
		const email = txtEmail.value;
		const password = txtPassword.value;
		const auth = firebase.auth();

		//Sign in
		const promise = auth.createUserWithEmailAndPassword(email,password);
		//Catch error if cannot sign in
		promise.catch(e => console.log(e.message));		
	});

	//Add a realtime listener
	firebase.auth().onAuthStateChanged(firebaseUser =>{
		if(firebaseUser){
			console.log(firebaseUser);
			window.location.replace("home.html");
		}else{
			console.log('not log in');
			
		}
	});

}());
