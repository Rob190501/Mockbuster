<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "persistence.model.* , java.util.*"%>
    
<%
	Customer loggedUser = (Customer)request.getSession().getAttribute("user");

	ArrayList<Customer> users = (ArrayList<Customer>)request.getAttribute("users");
	
	if(users == null) {
		request.getRequestDispatcher("/admin/GetAllUsersServlet").forward(request, response);
		return;	
	}
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Utenti</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/table.css">
</head>
<body>
	<jsp:include page = "/fragments/header.jsp"/>
	<div class = "page">
		<h1>Tutti gli utenti</h1>
		
		<div class = "tablecontainer">
			<table>
				<tr>
					<th>ID</th>
					<th>Email</th>
					<th>Nome</th>
					<th>Cognome</th>
					<th>Credito</th>
					<th></th>
				</tr>
				<% for(Customer user : users) { %>
					<tr>
						<td><%= user.getId() %></td>
						<td><%= user.getEmail() %></td>
						<td><%= user.getFirstName() %></td>
						<td><%= user.getLastName() %></td>
						<td><%= user.getCredit() %>€</td>
						<td>
						<% if(!user.getId().equals(loggedUser.getId())) { %>
							<% if(user.isAdmin()) { %>
								<a href ="<%= request.getContextPath() %>/admin/SetAdminServlet?action=remove&id=<%= user.getId() %>">
									Rimuovi admin
								</a>
							<% } else { %>
								<a href ="<%= request.getContextPath() %>/admin/SetAdminServlet?action=set&id=<%= user.getId() %>">
									Nomina admin
								</a>
							<% } 
							}
						%>
						</td>
					</tr>
				<% } %>
			</table>
		</div>
	</div>
</body>
</html>