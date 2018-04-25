package com.finalproject.controller.QnA;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.finalproject.dao.QuestionDao;
import com.finalproject.pojo.Question;
import com.finalproject.pojo.User;

@Controller
public class QuestionController {

	@Autowired
	@Qualifier("questionDao")
	QuestionDao questionDao;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/ask", method = RequestMethod.GET)
	public String questionHome(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("UserSession") != null) {
			return "question-home";
		} else {
			return "index";
		}
	}

	@ResponseBody
	@RequestMapping(value = "ask/submit", method = RequestMethod.POST)
	public Boolean questionAsk(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("question") String question, @RequestParam("description") String questionDescription) {
		try {
			Question quest = new Question();
			HttpSession session = request.getSession(true);
			String questionEncoded = question.replace(' ', '-').replace("?", "");
			quest.setQuestionEncoded(questionEncoded);
			quest.setQuestion(question);
			quest.setqDescription(questionDescription);
			User usr = (User) session.getAttribute("UserSession");
			quest.setUser(usr);
			Question q = questionDao.addQuestion(quest);
			if (q != null) {
				// Question added successfully
				return true;
				// return new ModelAndView("question-view", "question", q);
			} else {
				return false;
				// return new ModelAndView("home");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
			// return new ModelAndView("home");
		}
	}
}
