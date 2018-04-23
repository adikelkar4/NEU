<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="false"%>
<%@ include file="header.jsp"%>
<head>
<title>QnA</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.js"></script>
<script type="text/javascript">
	jQuery(function($) {
		var pathname = window.location.protocol + "//" + window.location.host
				+ "/QnA";
		$("#submit").on("click", function() {
			$(this).val("Please Wait...")
			$(".loginerror").html("")
		});

		$("#registerSubmit").on("click", function() {
			$(this).val("Please Wait...")
			$(".registererror").html("")
		});

		$("form[name='registerform']")
				.validate(
						{
							// Specify validation rules
							rules : {
								fname : {
									required : true,
									minlength : 3
								},
								lname : {
									required : true,
									minlength : 3
								},
								email : {
									required : true,
									email : true
								},
								password : {
									required : true,
									minlength : 5
								}
							},
							// Specify validation error messages
							messages : {
								fname : {
									required : "Please enter first name",
									minlength : "Minimum length of first name must be 3"
								},
								lname : {
									required : "Please enter last name",
									minlength : "Minimum length of last name must be 3"
								},
								password : {
									required : "Please provide a password",
									minlength : "Your password must be at least 5 characters long"
								},
								email : "Please enter a valid email address"
							},
							errorPlacement : function(error, element) {
								error.insertAfter(element);
							},
							submitHandler : function(form) {
								$
										.post(
												"register",
												{
													fname : $("#fname").val(),
													lname : $("#lname").val(),
													email : $("#email").val(),
													password : $("#password")
															.val()
												},
												function(data, status) {
													if (!$.trim(data)) {
														console
																.log("User already exists");
														$(".registererror")
																.html(
																		"User with this email already exists.")
														$("#registerSubmit")
																.val("Sign Up");
													} else {
														console.log(data)
														console
																.log("First Name "
																		+ data.fname)
														console
																.log("Last Name "
																		+ data.lname)
														console.log("Email "
																+ data.email)
													}
												});

								$.post("sendEmail", {
									userFname : $("#fname").val(),
									userEmail : $("#email").val()
								}, function(data) {
									console.log(data)
									window.location.href = pathname + "/home";
								});
							}
						});

		$("form[name='loginform']")
				.validate(
						{
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
								$
										.post(
												"login/checklogin",
												{
													email : $("#login_email")
															.val(),
													password : $(
															"#login_password")
															.val()
												},
												function(data, status) {
													if (!$.trim(data)) {
														console
																.log("Invalid username or password");
														$(".loginerror")
																.html(
																		"Invalid username or password. Please try again.")
														$("#submit").val(
																"Login");
													} else {
														console.log(data)
														console
																.log("First Name "
																		+ data.fname)
														console
																.log("Last Name "
																		+ data.lname)
														console.log("Email "
																+ data.email)
														window.location.href = pathname
																+ "/home";
													}
												});
							}
						});
	})
</script>
</head>
<div class="container">
	<h1 style="text-align: center;">Welcome To QnA!</h1>
	<div class="row">
		<div class="col" style="text-align: center;">
			<p style="text-align: center;">Sign Up</p>
			<form name="registerform" action="register" method="POST">
				<p>
					<input type="text" placeholder="First Name" id="fname" name="fname" />
				</p>
				<p>
					<input type="text" placeholder="Last Name" id="lname" name="lname" />
				</p>
				<p>
					<input type="text" placeholder="Email" id="email" name="email" />
				</p>
				<p>
					<input type="password" placeholder="Password" id="password"
						name="password" />
				</p>
				<p>
					<input type="submit" id="registerSubmit" value="Sign Up" />
				</p>
				<div class="registererror"></div>
			</form>
		</div>
		<div class="col">
			<p>Login</p>
			<form name="loginform" action="login" method="post">
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
