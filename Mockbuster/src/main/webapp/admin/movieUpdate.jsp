<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, persistence.model.*"%>

<%
	Movie movie = (Movie)request.getAttribute("movie");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<title>Modifica <%= movie.getTitle() %></title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/form.css">
	
	<script type = "text/javascript" src = "<%= request.getContextPath() %>/scripts/movieUpdate.js"></script> 
	
</head>
<body>
	<jsp:include page = "/fragments/header.jsp" />
	
	<div class = "page">
		<form id = "movieUpdateForm" action = "<%= request.getContextPath() %>/admin/MovieUpdateServlet" 
			method = "post">
		    <div>
		    	<input type = "hidden" name ="movieid" value = "<%= movie.getId() %>">
		    </div>
		    
		    <div>
		        <label for = "title">Titolo</label>
		        <input type = "text" id = "title" name = "title" 
		        	required pattern = "^[\w\sàèìòù.,']{1,200}$"
		        	value = "<%= movie.getTitle() %>"
		        	onblur = "validateFormField(this, 'titleErrorSpan', textError)">
		        <span id = "titleErrorSpan" class="error"></span>
		    </div>
		
		    <div>
		        <label for = "plot">Trama</label>
		        <textarea id = "plot" name = "plot"
		        	rows = "4" required
		        	onblur = "validateTextArea(this, 'plotErrorSpan', textError)"><%= movie.getPlot() %></textarea>
		        <span id = "plotErrorSpan" class="error"></span>
		    </div>
		
		    <div>
		        <label for = "duration">Durata (minuti)</label>
		        <input type = "number" id = "duration" name = "duration"
		        	min = "1" required
		        	value = "<%= movie.getDuration() %>"
		        	onblur = "validateFormField(this, 'durationErrorSpan', numberError)">
		        <span id = "durationErrorSpan" class="error"></span>
		    </div>
		
		    <div>
		        <label for = "year">Anno del Film</label>
		        <input type = "number" id = "year" name = "year"
		        	min = "1888" required
		        	value = "<%= movie.getYear() %>"
		        	onblur = "validateFormField(this, 'yearErrorSpan', yearError)">
		        <span id = "yearErrorSpan" class="error"></span>
		    </div>
		
		    <div>
		        <label for = "availableLicenses">Licenze Disponibili</label>
		        <input type = "number" id = "availableLicenses" name = "availableLicenses"
		        	min = "0" required
		        	value = "<%= movie.getAvailableLicenses() %>"
		        	onblur = "validateFormField(this, 'availableLicensesErrorSpan', numberError)">
		        <span id = "availableLicensesErrorSpan" class="error"></span>
		    </div>
		
		    <div>
		        <label for = "dailyRentalPrice">Prezzo Noleggio Giornaliero (€)</label>
		        <input type = "number" id = "dailyRentalPrice" name = "dailyRentalPrice"
			        min = "0" step = "0.5" required
			        value = "<%= movie.getDailyRentalPrice() %>"
			        onblur = "validateFormField(this, 'dailyRentalPriceErrorSpan', priceError)">
		        <span id = "dailyRentalPriceErrorSpan" class="error"></span>
		    </div>
		
		    <div>
		        <label for = "purchasePrice">Prezzo di Acquisto (€)</label>
		        <input type = "number" id = "purchasePrice" name = "purchasePrice"
		        	min = "0" step = "0.5" required
		        	value = "<%= movie.getPurchasePrice() %>"
		        	onblur = "validateFormField(this, 'purchasePriceErrorSpan', priceError)">
		        <span id = "purchasePriceErrorSpan" class="error"></span>
		    </div>
		    
		    <div>
		    	<label for = "isVisible" class = "checkboxspan">Film presente in catalogo</label>
		    	<input type="checkbox" id = "isVisible" name="isVisible" checked>
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
		
		    <input type = "submit" value = "Aggiorna" onclick = "return validateForm()">
		</form>
	</div>
</body>
</html>