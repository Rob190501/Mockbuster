<%@ page language = "java" contentType = "text/html; charset = UTF-8"
    pageEncoding = "UTF-8" import = "java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset = "UTF-8">
	
	<title>Registrati</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/form.css">
	
	<script type = "text/javascript" src = "<%= request.getContextPath() %>/scripts/loginSignup.js"></script>
</head>
<body>
	<jsp:include page = "/fragments/header.jsp" />
	
	<div class = "page">
		<form id = "signupForm" action = "<%= request.getContextPath() %>/common/SignupServlet"
		 	method = "post">
			<div>
				<label for = "email">Email</label>
				<input type = "email" id = "email" name = "email"
					required onblur = "validateFormField(this, 'emailErrorSpan', emailError)">
				<span id = "emailErrorSpan" class = "error"></span>
			</div>
			
			<div>
				<label for = "password">Password</label>
				<input type = "password" id = "password" name = "password"
					required pattern = "^(\w+){4,10}$"
					onblur = "validateFormField(this, 'passwordErrorSpan', passwordError)">
				<span id = "passwordErrorSpan" class = "error"></span>
			</div>
			
			<div>
				<label for = "firstName">Nome</label>
				<input type = "text" id = "firstName" name = "firstName"
					required pattern = "^[A-Za-z]+$"
					onblur = "validateFormField(this, 'firstNameErrorSpan', nameError)">
				<span id = "firstNameErrorSpan" class = "error"></span>
			</div>
			
			<div>
				<label for = "lastName">Cognome</label>
				<input type = "text" id = "lastName" name = "lastName"
					required pattern = "^[A-Za-z]+$"
					onblur = "validateFormField(this, 'lastNameErrorSpan', nameError)">
				<span id = "lastNameErrorSpan" class = "error"></span>
			</div>
			
			<div>
				<label for = "billingAddress">Indirizzo fatturazione</label>
				<input type = "text" id = "billingAddress" name = "billingAddress"
					required pattern = "^([A-Za-z]+\s)+\d+\s\d{5}\s[A-Za-z]+$"
					onblur = "validateFormField(this, 'billingAddressErrorSpan', addressError)">
				<span id = "billingAddressErrorSpan" class = "error"></span>
			</div>
			
			<div>
			<%
			ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
			if(errors != null) {
				for(String e: errors) { %>
					<p class = "error"><%= e %></p>	
				<%}
			}
			%>
			</div>
			
			<input type = "submit" value = "Registrati" onclick="return validateSignup()">
		</form>
	</div>
</body>
</html>