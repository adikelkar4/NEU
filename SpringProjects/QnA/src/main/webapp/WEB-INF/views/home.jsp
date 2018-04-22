<%@ include file="header.jsp"%>
<script type="text/javascript">
	jQuery(function() {
		jQuery(".questionAnchor").hover(function() {
			console.log(${question.question})
			var newlink = "view/"+$(this).data("question").replace(/\s+/g, '-');
			this.href = newlink;
		});
	})
</script>
<p>
	<a href="ask"><button>Ask Question</button></a>
</p>
<h3>Question Stream</h3>
<p>
	<c:forEach items="${allQuestions}" var="question">
		<a data-question="${question.question}" class="questionAnchor"
			target="_BLANK" href=""><p>${question.question}</p></a>
	</c:forEach>
</p>
<%@ include file="footer.jsp"%>