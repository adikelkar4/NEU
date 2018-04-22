<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<html>
<head>
<title>QnA | View</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	jQuery(function($) {
		var pathname = window.location.protocol + "//" + window.location.host
				+ "/QnA";
		$.post("/QnA/answer/getall", {questionId:'${question.questionID}'}, function(data, status){
			console.log(data)
		})
		$("#postAnswer").on("click", function(e) {
			e.preventDefault();
			if ($.trim($("#answerSpace").val()) != "") {
				$.post("/QnA/answer/submit", {
					questionId: '${question.questionID}',
					answer : $("#answerSpace").val()
				}, function(data, status) {
					if (data == true) {
						// Answer posted successfully
						console.log("answer added suvveskjljhd")
						window.location.href = pathname + "/home";
					} else {
					}
				});
			}
		})
	})
</script>
</head>
<body>
	<h1>Question View</h1>
	<p>${question.question}</p>
	<p>${question.qDescription}</p>
	<div id="writeAnswer">Write an answer</div>
	<p>
		<textarea id="answerSpace" name="answer" rows="10" cols="70"></textarea>
	</p>
	<p>
		<button id="postAnswer">Post Answer</button>
	</p>
	<div id="answerList"></div>
</body>
</html>
