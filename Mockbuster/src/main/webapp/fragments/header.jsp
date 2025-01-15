<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import = "persistence.model.*, security.UserRole.Role"%>
<%
    Role role = (Role) request.getSession().getAttribute("role");
    Customer user = null;
    if (role == Role.CUSTOMER)
        user = (Customer) request.getSession().getAttribute("user");
%>

<link rel = "stylesheet" href = "<%= request.getContextPath()%>/styles/header.css">

<script type="text/javascript" src = "<%= request.getContextPath()%>/scripts/header.js"></script>

<header>
    <a href = "<%= request.getContextPath()%>/common/index.jsp" class = "logo">
        MOCKBUSTER
    </a>

    <div class = "nav">
        <div id = "menu" class = "menu">
        <% if (role == Role.CATALOG_MANAGER) { %>
            <a href = "<%= request.getContextPath()%>/admin/notVisiblePage.jsp">Non in catalogo</a>
            <a href = "<%= request.getContextPath()%>/admin/movieUpload.jsp">Movie upload</a>
        <% } %>
        
        <% if (role == Role.ORDER_MANAGER) { %>
            <a href = "<%= request.getContextPath()%>/admin/allOrdersPage.jsp">Tutti gli ordini</a>
        <% } %>
        
        <% if (role == Role.CUSTOMER) { %>
            <a href = "<%= request.getContextPath()%>/browse/ordersPage.jsp">I miei ordini</a>
            <a href = "<%= request.getContextPath()%>/browse/myAccountPage.jsp">Il mio account</a>
        <% } %>
        
            <a href = "<%= request.getContextPath()%>/common/LogoutServlet">Esci</a>
        </div>
        
        <% if (role == null) {%>
            <a href = "<%= request.getContextPath()%>/common/login.jsp">Accedi</a>	
        <% } else {%>
            <img src ="https://localhost:8181/icons/menu.png"
                 id = "hamburger" class = "hamburger"
                 onclick = "toggleMenuVisibility()">
            
            <% if(role != Role.ORDER_MANAGER) { %>
            <img src ="https://localhost:8181/icons/search.png"
                 id = "lens" class = "lens"
                 <% if (request.getRequestURI().equals(request.getContextPath() + "/common/index.jsp")) {%>
                 onclick = "toggleSearchbarVisibility()"
                 <% } else {%>
                 onclick = "window.location.href = '<%= request.getContextPath()%>/common/index.jsp?search='"
                 <% }%>
            >
            <% } %>

            <% if(role == Role.CUSTOMER) { %>
                <a href = "<%= request.getContextPath()%>/browse/cartPage.jsp">
                    <img src = "https://localhost:8181/icons/cart.png">
                </a>
            <% } %>

            <div class = "greetings">
                <% if (role == Role.CUSTOMER) {%>
                    <span>Ciao <%= user.getFirstName()%>!</span>
                    <span>Saldo: <%= user.getCredit()%>€</span>
                <% } %>
                <% if (role == Role.ORDER_MANAGER) {%>
                    <span>Gestore</span>
                    <span>ordini</span>
                <% } %>
                <% if (role == Role.CATALOG_MANAGER) {%>
                    <span>Gestore</span>
                    <span>catalogo</span>
                <% } %>
            </div>
        <% }%>
    </div>
</header>