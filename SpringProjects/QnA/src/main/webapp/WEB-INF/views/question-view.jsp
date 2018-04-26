<%@ include file="header.jsp"%>
<html>
<head>
<title>QnA | View</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	jQuery(function($) {
		var pathname = window.location.protocol + "//" + window.location.host
				+ "/QnA";
		var userExists = "";
		$.post("/QnA/answer/getall", {
			questionId : '${question.questionID}'
		}, function(data, status) {
			var size = Object.keys(data).length;
			$("#totalAnswers").append(size + " Answers");
			console.log(data)
			//console.log(size)
			for (var i = 1; i <= size; i++) {
				for (var j = 0; j < 3; j++) {
					var userName = "${sessionScope.UserSession.fname} ${sessionScope.UserSession.lname}";
					if(userExists == "" && data[i][j] == userName) {
						userExists = "true";
					}
					var answerText = "";
					var postedDate = "";
					var authorInfo = "";
					if (j == 0) {
						// Author Name
						authorInfo += "<p>" + data[i][j] + "</p>";
					} else if (j == 1) {
						// Posted date
						var d = new Date(data[i][j]);
						console.log("DATE POSTED ROM DB ")
						console.log(d)
						postedDate += "<p>" + data[i][j] + "</p>";
					} else {
						// Answer content
						answerText += "<p>" + data[i][j] + "</p>";
					}
					if(authorInfo == "<p>"+userName+"</p>") {
						$(".answerList").append("<h4 id='myAnswer' class='author_info'>" + authorInfo + "</h4>");
					} else {
						$(".answerList").append("<h4 class='author_info'>" + authorInfo + "</h4>");
					}
					$(".answerList").append("<div class='posted_date'>" + postedDate + "</div>");
					$(".answerList").append("<div class='answer_text'>" + answerText + "</div>");
					if(authorInfo == "<p>"+userName+"</p>") {
						$(".answerList").append("<a id='answerOps' data-toggle='modal' href='#deleteAnswer'><strong>Delete</strong></a>");
					}
				}
				$(".answerList").append("<hr/>");
			}
			/*if(userExists != "" && userExists == "true") {
				$("#writeAnswer").hide();
				$("#updateAnswer").append("<a href='#myAnswer' class='updateMyAnswer'>Update My Answer</a>");
			}*/
		})
		$("#postAnswer").on("click", function(e) {
			e.preventDefault();
			if ($.trim($("#answerSpace").val()) != "") {
				$.post("/QnA/answer/submit", {
					questionId : '${question.questionID}',
					answer : $("#answerSpace").val()
				}, function(data, status) {
					if (data != null) {
						// Answer posted successfully
						location.reload();
					} else {
						console.log("error occurred ")
					}
				});
			}
		});

		$("#deleteConfirm").on("click", function(e) {
			e.preventDefault();
			$.post("/QnA/answer/delete", {
				questionId : '${question.questionID}',
				user : '${sessionScope.UserSession.userID}'
			}, function(data, status) {
				if (data != null) {
					// Answer deleted successfully
					location.reload();
				} else {
					console.log("error occurred ")
				}
			});
		});
	})
</script>
</head>
<body>
	<div class="container">
		<h2>${question.question}</h2>
		<p>${question.qDescription}</p>
		<a id="writeAnswer" data-toggle="modal" href="#myModal"><button class="btn btn-default">Write Answer</button></a>
		<div id="updateAnswer"></div>

		<!-- Modal -->
		<div class="modal fade bd-example-modal-lg" id="myModal" role="dialog">
			<div class="modal-dialog modal-lg">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">${sessionScope.UserSession.fname}
							${sessionScope.UserSession.lname} is writing...</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<p>
							<textarea style="min-width: 100%; min-height: 50%;" placeholder="Write your answer" id="answerSpace"
								name="answer"></textarea>
						</p>
					</div>
					<div class="modal-footer">
					<button class="btn btn-default" id="postAnswer">Post Answer</button>
					</div>
				</div>

			</div>
		</div>
		
		<div class="modal fade bd-example-modal-lg" id="deleteAnswer" role="dialog">
			<div class="modal-dialog modal-lg">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Remove Answer</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<p>
							Are you sure you want to delete your answer?
						</p>
					</div>
					<div class="modal-footer">
					<button type="button" id="deleteConfirm" class="btn btn-primary">Yes</button>
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>

		<div id="answerPopup" role="dialog" class="answerSpace modal fade">
			<div class="modal-body modal-dialog">

				
			</div>
		</div>
		<div id="answerList">
			<div id="totalAnswers"></div>
			<hr />
			<div class="answerList"></div>
		</div>
	</div>