const emptyError = "Campo vuoto";
const nameError = "Consentite solo lettere";
const addressError = "Via Bologna 12 40100 Bologna";
const creditError = "Solo numeri, saldo minimo = saldo attuale"

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

function validateUserForm() {
	let isValid = true;
	let form = document.getElementById("userForm");
	
	if(!validateFormField(form.firstName, "firstNameErrorSpan", nameError)) {
		isValid = false;
	}

	if(!validateFormField(form.lastName, "lastNameErrorSpan", nameError)) {
		isValid = false;
	}

	if(!validateFormField(form.billingAddress, "billingAddressErrorSpan", addressError)) {
		isValid = false;
	}
	
	if(!validateFormField(form.credit, "creditErrorSpan", creditError)) {
		isValid = false;
	}
	
	return isValid;
}