<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*"%>
<%
	Order order = (Order)request.getAttribute("order");
%>

<!DOCTYPE html>
<html>
<head>
	
	<meta charset="UTF-8">
	<title>Ordine n.<%= order.getId() %></title>
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	
</head>
<body>
	<jsp:include page = "/fragments/header.jsp"/>
	
	<div class = "page">
		
		<h1>Ordine n.<%= order.getId() %></h1>
		<h3>Data ordine <%= order.getDate() %></h3>
		<h3>Totale: <%= order.getTotal() %>€</h3>
		
		<%
			if(!order.getPurchasedMovies().isEmpty()) {
				%>
					<br>
					<h3>Acquisti</h3>
				<%
				for(PurchasedMovie movie: order.getPurchasedMovies()) {
					%>
					<p>
						<%= movie.getTitle() %> -
						<%= movie.getPrice() %>€
					<p>
					<%
				}
			}
		%>
		
		
		
		<%
			if(!order.getRentedMovies().isEmpty()) {
				%>
					<br>
					<h3>Noleggi</h3>
				<%
				
				for(RentedMovie movie: order.getRentedMovies()) {
					%>
					<p>
						<%= movie.getTitle() %> -
						<%= movie.getDays() %> giorni x 
						<%= movie.getDailyPrice() %>€ = 
						<%= movie.getDays() * movie.getDailyPrice() %>€
					</p>
					<%
				}
			}
		%>
	</div>
</body>
</html>