<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*, java.io.File, model.*"%>

<%
	ArrayList<Movie> movieList = (ArrayList<Movie>)request.getAttribute("movieList");
	if(movieList == null) {
		request.getRequestDispatcher("/common/MovieRetrieveServlet?page=index").forward(request, response);
		return;	
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset = "UTF-8">
	
	<title>Mockbuster</title>
	
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/container.css">
	<script type="text/javascript" src = "<%= request.getContextPath() %>/scripts/search.js"></script>
</head>
<body>
	<jsp:include page = "/fragments/header.jsp" />
	<div class = "page">
		
		<input type="text" id = "searchbar" class = "searchbar"
			onkeyup = "retrieveMovies('<%=request.getContextPath()%>')">
		
		<div id = "containers">
			<% for(Movie movie : movieList) {
			   	if(movie.isVisible()) { %>
					<div class = "container">
						<a href = "<%= request.getContextPath() %>/browse/MoviePageServlet?id=<%= movie.getId() %>">
							<img src = "<%= request.getContextPath() %>/images/posters/<%= movie.getPosterPath() %>"
								onerror="this.src='<%= request.getContextPath() %>/images/posters/error.jpg'">
						</a>
					</div>
				<%
				}
			}
			%>
		</div>
	</div>
	
	<jsp:include page = "/fragments/footer.jsp"/>
	
	<%
	if(request.getParameter("search") != null) { %>
		<script type="text/javascript">
			toggleSearchbarVisibility('<%= request.getContextPath() %>');
		</script>
	<%
	}
	%>
</body>
</html>