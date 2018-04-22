<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>QnA</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/global.css" rel="stylesheet" />
</head>
<body>
	<div class="site_header">
		<div class="navbar">
			<!--<ul>
				<div><a href="/QnA"> <img alt="QnA_Logo" height="15%" width="10%"
						src="${pageContext.request.contextPath}/resources/images/logo.PNG"></a>
				</div>
				<div><a href="news.asp">Notifications</a></div>
				<div>Hey ${sessionScope.UserSession.fname}!</div>
				<div><a href="logout">Logout</a></div>
			</ul>-->
		</div>
	</div>