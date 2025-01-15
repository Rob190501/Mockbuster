<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import = "persistence.model.* , java.util.*, java.time.format.DateTimeFormatter"%>

<%
    //Customer user = (Customer)request.getSession().getAttribute("user");
    ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
    ArrayList<Customer> customers = (ArrayList<Customer>) request.getAttribute("users");

    if (orders == null || customers == null) {
        request.getRequestDispatcher("/admin/GetAllOrdersServlet").forward(request, response);
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
        <link rel = "stylesheet" href = "<%= request.getContextPath()%>/styles/common.css">
        <link rel = "stylesheet" href = "<%= request.getContextPath()%>/styles/table.css">
    </head>
    <body>
        <jsp:include page = "/fragments/header.jsp"/>
        <div class = "page">
            <h1>Tutti gli ordini</h1>

            <form method = "get" action = "<%= request.getContextPath()%>/admin/GetAllOrdersServlet">
                <label for = "from">Dal </label>
                <input type = "date" id = "from" name = "from" required>

                <label for = "to">al </label>
                <input type= "date" id = "to" name = "to" required>

                <label for = "userid">Utente: </label>
                <select id = "userid" name = "userid">
                    <option value ="" selected>Tutti gli utenti</option>
                    <%
                        for (Customer c : customers) {
                    %>
                    <option value="<%= c.getId()%>"><%= c.getEmail()%></option>
                    <%
                        }
                    %>
                </select>

                <input type = "submit" value = "Ricerca">
            </form>

            <div class = "tablecontainer">
                <table>
                    <tr>
                        <th>Ordine</th>
                        <th>Data</th>
                        <th>Totale</th>
                        <th>Email</th>
                        <th></th>
                    </tr>
                    <%
                        for (Order order : orders) {
                    %>
                    <tr>
                        <td><%= order.getId()%></td>
                        <td><%= order.getDate().format(formatter)%></td>
                        <td><%= order.getTotal()%>€</td>
                        <td><%= order.getUser().getEmail()%></td>
                        <td><a href ="<%= request.getContextPath()%>/browse/GetOrdersServlet?userid=<%=order.getUser().getId()%>&orderid=<%=order.getId()%>">Dettagli</a></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
        </div>
    </body>
</html>