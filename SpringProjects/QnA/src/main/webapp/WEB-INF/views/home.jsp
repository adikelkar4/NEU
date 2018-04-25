<%@ include file="header.jsp"%>
<script type="text/javascript">
	jQuery(function() {
		jQuery(".questionAnchor").hover(function() {
			var newlink = "view/" + $(this).data("question").replace(/\s+/g, '-').replace(/\?/g, "");
			this.href = newlink;
		});
	});
</script>
<div class="container">
	<p>
	<c:if test="${sessionScope.UserSession.isActive == false}">
		<button disabled>Ask Question</button>
		<div class="error">Activate your account to post a question</div>
	</c:if>
	<c:if test="${sessionScope.UserSession.isActive == true}">
		<a href="ask"><button>Ask Question</button></a>
	</c:if>
	</p>
	<h3>Question Stream</h3>
	<p>
		<c:forEach items="${allQuestions}" var="question">
			<a data-question="${question.question}" class="questionAnchor"
				target="_BLANK" href=""><p>${question.question}</p></a>
			<div class="user_name">${question.user.fname} ${question.user.lname} </div>
			<hr />
		</c:forEach>
	</p>
</div>
<%@ include file="footer.jsp"%>