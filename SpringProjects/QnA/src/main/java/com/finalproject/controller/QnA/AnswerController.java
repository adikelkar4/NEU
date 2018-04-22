package com.finalproject.controller.QnA;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.finalproject.dao.AnswerDao;
import com.finalproject.dao.QuestionDao;
import com.finalproject.pojo.Answer;
import com.finalproject.pojo.Question;
import com.finalproject.pojo.User;

@Controller
public class AnswerController {
	@Autowired
	@Qualifier("answerDao")
	AnswerDao answerDao;

	@Autowired
	@Qualifier("questionDao")
	QuestionDao questionDao;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@ResponseBody
	@RequestMapping(value = "/answer/submit", method = RequestMethod.POST)
	private Boolean postAnswer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("questionId") int questionId, @RequestParam("answer") String answer) {
		Question q = questionDao.getQuestionById(questionId);
		User u = (User) request.getSession().getAttribute("UserSession");
		Answer a = answerDao.saveAnswer(answer, q, u);
		if (a != null) {
			return true;
		} else {
			return false;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/answer/getall", method = RequestMethod.POST)
	private List<Answer> getAllAnswers(@RequestParam("questionId") int question) {
		Question q = questionDao.getQuestionById(question);		
		List<Answer> ans = answerDao.getAnswersByQuestion(q);
		logger.info("ANSWER CONTROLLER FRO AJAX last one");
		if (ans != null) {
			return ans;
		} else {
			return null;
		}

	}
}
