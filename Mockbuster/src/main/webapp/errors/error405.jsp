<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Metodo non consentito</title>
	<meta name = "viewport" content = "width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href = "<%= request.getContextPath() %>/styles/common.css">
</head>
<body>
	<div class = "page">
		<h1>Errore 405</h1>
		<p>Metodo non consentito</p>
	</div>
</body>
</html>