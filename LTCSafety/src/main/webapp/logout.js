(function(){
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