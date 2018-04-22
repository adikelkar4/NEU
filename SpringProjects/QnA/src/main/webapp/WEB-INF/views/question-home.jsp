<%@ include file="header.jsp"%>
<script type="text/javascript">
	jQuery(function($) {
		var pathname = window.location.protocol + "//" + window.location.host
				+ "/QnA";
		$("#submit").on("click", function(e) {
			e.preventDefault();
			if ($.trim($("#questionSpace").val()) != "") {
				$.post("ask/submit",{
				    question: $("#questionSpace").val(),
				    description: $("#description").val(),
				},function(data,status) {
					if(data == true) {
						// Question posted successfully
						window.location.href = pathname+"/home";
					} else {
					}
				});
			};
		});
	})
</script>
<h1>Ask Your Question Here</h1>
	<p>
		<input type="text" size="100" name="questionText" id="questionSpace"
			placeholder="Ask Your Question Here" />
	</p>
	<textarea placeholder="A Brief description of your question (Optional)"
		rows="10" cols="93" id="description" name="questionDescription"></textarea>
	<p>
		<button id="submit" value="Ask Question" >Post Question</button>
	</p>