const emptyError = "Campo vuoto";
const emailError = "username@example.com";
const passwordError = "Tra 4 e 10 caratteri alfanumerici"
const nameError = "Consentite solo lettere";
const addressError = "Via Bologna 12 40100 Bologna";

function validateFormField(field, errorSpanId, errorMessage) {
	let errorSpan = document.getElementById(errorSpanId);
	 
	if(field.checkValidity()) {
		field.classList.remove("error");
		field.classList.add("valid");
		errorSpan.innerHTML = "";
		return true;
	}
	
	field.classList.remove("valid");
	field.classList.add("error");
	errorSpan.innerHTML = field.validity.valueMissing ? emptyError: errorMessage;
	return false;	
}

function validateSignup() {
	let isValid = true;
	let form = document.getElementById("signupForm");
	
	if(!validateFormField(form.email, "emailErrorSpan", emailError)) {
		isValid = false;
	}
	
	if(!validateFormField(form.password, "passwordErrorSpan", passwordError)) {
		isValid = false;
	}
	
	if(!validateFormField(form.firstName, "firstNameErrorSpan", nameError)) {
		isValid = false;
	}

	if(!validateFormField(form.lastName, "lastNameErrorSpan", nameError)) {
		isValid = false;
	}

	if(!validateFormField(form.billingAddress, "billingAddressErrorSpan", addressError)) {
		isValid = false;
	}
	
	return isValid;
}

function validateLogin() {
	let isValid = true;
	let form = document.getElementById("loginForm");
	
	if(!validateFormField(form.email, "emailErrorSpan", emailError)) {
		isValid = false;
	}
	
	if(!validateFormField(form.password, "passwordErrorSpan", passwordError)) {
		isValid = false;
	}
	
	return isValid;
}