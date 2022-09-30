function onSuccess(googleUser) {
	console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
}
function onFailure(error) {
	console.log(error);
}
function renderButton() {
	gapi.signin2.render('my-signin2', {
		'scope': 'profile email',
		'width': 'fill',
		'longtitle': true,
		'theme': 'light',
		'text': "구글로 로그인",
		'onsuccess': onSuccess,
		'onfailure': onFailure
	});
}