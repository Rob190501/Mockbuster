<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "persistence.model.* , java.util.*, java.time.format.DateTimeFormatter"%>
    
<%
	Customer customer = (Customer)request.getSession().getAttribute("user");
	List<Order> orders = (List<Order>)request.getAttribute("orders");
	
	if(orders == null) {
		request.getRequestDispatcher("/browse/GetOrdersServlet").forward(request, response);
		return;	
	}
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Ordini</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/table.css">
</head>
<body>
	<jsp:include page = "/fragments/header.jsp"/>
	<div class = "page">
		<h1>I miei ordini</h1>
		
		<div class = "tablecontainer">
			<table>
				<tr>
					<th>Ordine</th>
					<th>Data</th>
					<th>Totale</th>
					<th></th>
				</tr>
				<%
				for(Order order : orders) {
					%>
					<tr>
						<td><%= order.getId() %></td>
						<td><%= order.getDate().format(formatter) %></td>
						<td><%= order.getTotal() %>€</td>
						<td><a href ="<%= request.getContextPath() %>/browse/GetOrdersServlet?userid=<%=customer.getId()%>&orderid=<%=order.getId()%>">Dettagli</a></td>
					</tr>
					<%
				}
				%>
			</table>
		</div>
	</div>
</body>
</html>