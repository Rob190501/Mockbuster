<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*"%>

<%
	User user = (User)request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Il mio account</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/form.css">
	
	<script type = "text/javascript" src = "<%= request.getContextPath() %>/scripts/myAccount.js"></script>
</head>
<body>
	<jsp:include page = "/fragments/header.jsp" />
	
	<div class = "page">
		<form id = "userForm" action = "<%= request.getContextPath() %>/browse/UpdateUserServlet"
		 	method = "post">
			<div>
				<p>Email: <%= user.getEmail() %></p>
			</div>
			
			<div>
				<label for = "firstName">Nome</label>
				<input type = "text" id = "firstName" name = "firstName"
					required pattern = "^[A-Za-z]+$"
					value = "<%= user.getFirstName() %>"
					onblur = "validateFormField(this, 'firstNameErrorSpan', nameError)">
				<span id = "firstNameErrorSpan" class = "error"></span>
			</div>
			
			<div>
				<label for = "lastName">Cognome</label>
				<input type = "text" id = "lastName" name = "lastName"
					required pattern = "^[A-Za-z]+$"
					value = "<%= user.getLastName() %>"
					onblur = "validateFormField(this, 'lastNameErrorSpan', nameError)">
				<span id = "lastNameErrorSpan" class = "error"></span>
			</div>
			
			<div>
				<label for = "billingAddress">Indirizzo fatturazione</label>
				<input type = "text" id = "billingAddress" name = "billingAddress"
					required pattern = "^([A-Za-z]+\s)+\d+\s\d{5}\s[A-Za-z]+$"
					value = "<%= user.getBillingAddress() %>"
					onblur = "validateFormField(this, 'billingAddressErrorSpan', addressError)">
				<span id = "billingAddressErrorSpan" class = "error"></span>
			</div>
			
			<div>
				<label for = "credit">Saldo</label>
				<input type = "number" id = "credit" name = "credit"
					required min = "<%= user.getCredit() %>"
					value = "<%= user.getCredit() %>" step = "0.5"
					onblur = "validateFormField(this, 'creditErrorSpan', creditError)">
				<span id = "creditErrorSpan" class = "error"></span>
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
			
			<input type = "submit" value = "Aggiorna" onclick="return validateUserForm()">
		</form>
	</div>
	
</body>
</html>