<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="false"%>
<%@ include file="header.jsp"%>
<head>
<title>QnA</title>
<link href="${pageContext.request.contextPath}/resources/css/index.css"	rel="stylesheet" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.js"></script>
<script type="text/javascript">
jQuery(function($) {
	var pathname = window.location.protocol + "//" + window.location.host
			+ "/QnA";
	$("form[name='loginform']").validate({
		// Specify validation rules
		rules : {
			login_email : {
				required : true,
				email : true
			},
			login_password : {
				required : true,
				minlength : 5
			}
		},
		// Specify validation error messages
		messages : {
			login_password : {
				required : "Please provide a password",
				minlength : "Your password must be at least 5 characters long"
			},
			login_email : "Please enter a valid email address"
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element);
		}
	});
})
</script>
</head>
<div class="main">
	<div class="root_logo"></div>
	<h2 style="text-align: center; color: black;">Login with your admin credentials</h2>
	<div class="inner_container">
		<div class="user_action loginform">
			<h5>Login</h5>
			<form name="loginform" action="admin/login" method="post">
				<p>
					<input placeholder="Email" type="email" id="login_email"
						name="login_email" />
				</p>
				<p>
					<input placeholder="Password" type="password" id="login_password"
						name="login_password" />
				</p>
				<p>
					<input id="submit" type="submit" value="Login" name="submit" />
				</p>
				<div class="loginerror"></div>
			</form>
		</div>
	</div>
</div>
