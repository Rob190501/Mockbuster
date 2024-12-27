const emptyError = "Campo vuoto";
const numberError = "Consentiti solo numeri interi >= 0";
const priceError = "Consentiti solo numeri >= 0";
const yearError = "Anno min. 1888";
const imageError = "Consentite solo immagini JPEG e PNG max 10MB";
const textError = "Consentite solo max 200 lettere, numeri e spazi";

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

function validateTextArea(textArea, errorSpanId, errorMessage) {
	let errorSpan = document.getElementById(errorSpanId);
	let pattern = /^[\w\sàèìòù.,']{1,200}$/;
	
	if(pattern.test(textArea.value)) {
		textArea.classList.remove("error");
		textArea.classList.add("valid");
		errorSpan.innerHTML = "";
		return true;
	}
	
	textArea.classList.remove("valid");
	textArea.classList.add("error");
	errorSpan.innerHTML = textArea.validity.valueMissing ? emptyError: errorMessage;
	return false;	
}

function validateSubmittedFile(field, errorSpanId, errorMessage) {
	let errorSpan = document.getElementById(errorSpanId);
	let submittedFiles = field.files.length;
	
	if (submittedFiles > 0) {
		let file = field.files[0];
		const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
		
		if (file.size < MAX_FILE_SIZE) {
			let validImageTypes = ["image/jpeg", "image/png"];
			
			if(validImageTypes.includes(file.type)) {
				field.classList.remove("error");
				field.classList.add("valid");
				errorSpan.innerHTML = "";
				return true;
			}
		}
	}
	
	field.classList.remove("valid");
	field.classList.add("error");
	errorSpan.innerHTML = submittedFiles == 0 ? emptyError: errorMessage;
	return false;	
}

function validateForm() {
	let isValid = true;
	let form = document.getElementById("movieUploadForm");
	
	if(!validateFormField(form.title, "titleErrorSpan", textError)) {
		isValid = false;
	}
	
	if(!validateTextArea(form.plot, "plotErrorSpan", textError)) {
		isValid = false;
	}
	
	if(!validateFormField(form.duration, "durationErrorSpan", numberError)) {
		isValid = false;
	}

	if(!validateFormField(form.year, "yearErrorSpan", yearError)) {
		isValid = false;
	}

	if(!validateFormField(form.availableLicenses, "availableLicensesErrorSpan", numberError)) {
		isValid = false;
	}
	
	if(!validateFormField(form.dailyRentalPrice, "dailyRentalPriceErrorSpan", priceError)) {
		isValid = false;
	}
	
	if(!validateFormField(form.purchasePrice, "purchasePriceErrorSpan", priceError)) {
		isValid = false;
	}
	
	if(!validateSubmittedFile(form.poster, "posterErrorSpan", imageError)) {
		isValid = false;
	}
	
	return isValid;
}