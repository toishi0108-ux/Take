document.addEventListener("DOMContentLoaded", function() {

	const showPassword = document.getElementById("showPassword");
	const password = document.getElementById("personPassword");

	showPassword.addEventListener("change", function() {

		if (this.checked) {
			password.type = "text";
		} else {
			password.type = "password";
		}

	});

});