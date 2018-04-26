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
		},
		submitHandler : function(form) {
			$.post("admin",{
				email : $("#login_email").val(),
				password : $("#login_password").val()
			}, function(data, status) {
				if (!$.trim(data)) {
					console.log("Invalid username or password");
					$(".loginerror").html("Invalid username or password. Please try again.")
					$("#submit").val("Login");
				} else {
					console.log(data)
					console.log("First Name "+ data.fname)
					console.log("Last Name "+ data.lname)
					console.log("Email "+ data.email)
					window.location.href = pathname+ "/home";
				}
			});
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
			<h5>Welcome admin</h5>
			
		</div>
	</div>
</div>
