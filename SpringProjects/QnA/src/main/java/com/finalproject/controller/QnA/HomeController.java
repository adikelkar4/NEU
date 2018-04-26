package com.finalproject.controller.QnA;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.finalproject.dao.QuestionDao;
import com.finalproject.dao.RoleDao;
import com.finalproject.dao.UserDao;
import com.finalproject.pojo.Question;
import com.finalproject.pojo.Role;
import com.finalproject.pojo.User;

import sun.misc.BASE64Decoder;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	@Qualifier("roleDao")
	RoleDao roleDao;

	@Autowired
	@Qualifier("questionDao")
	QuestionDao questionDao;

	PasswordEncrypt pEnc;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean emailValidate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView getAdminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new ModelAndView("admin-login");
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public ModelAndView getPostAdminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorization = request.getHeader("Authorization");
		if (authorization == null) {
			askForPassword(response);
		} else {
			String userInfo = authorization.substring(6).trim();
			logger.info("userinfo");
			logger.info(authorization);
			BASE64Decoder decoder = new BASE64Decoder();
			String nameAndPassword = new String(decoder.decodeBuffer(userInfo));
			// Decoded part looks like "username:password".
			int index = nameAndPassword.indexOf(":");
			String user = nameAndPassword.substring(0, index);
			String password = nameAndPassword.substring(index + 1);
			logger.info(user);
			logger.info(password);
			request.setAttribute("Authorization", null);
			// High security: username must be reverse of password.
			if (areEqualReversed(user, password)) {
				// showStock(request, response);
				return new ModelAndView("admin-home");
				// return new ModelAndView("index");
			} else {
				// askForPassword(response);
				return new ModelAndView("admin-home");
				//askForPassword(response);
			}

		}
		return new ModelAndView("admin-home");
	}

	private void askForPassword(HttpServletResponse response) {
		response.setStatus(response.SC_UNAUTHORIZED); // I.e., 401
		response.setHeader("WWW-Authenticate", "BASIC realm=\"Insider-Trading\"");
	}

	private boolean areEqualReversed(String s1, String s2) {
		logger.info(s1);
		logger.info(s2);
		s2 = (new StringBuffer(s2)).reverse().toString();
		return ((s1.length() > 0) && s1.equals(s2));
	}

	@RequestMapping(value = "/view/{question_code}", method = RequestMethod.GET)
	public ModelAndView getQuestionPage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("question_code") String param) {
		HttpSession session = request.getSession();
		if (session.getAttribute("UserSession") != null) {
			try {
				response.sendRedirect("home");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (param != "") {
				Question q = questionDao.getQuestionByName(param);
				return new ModelAndView("question-view", "question", q);
			} else {
				return null;
			}
		}
		return null;

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("UserSession") != null) {
			try {
				response.sendRedirect("home");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return "index";
		}
		return null;
	}

	@RequestMapping(value = "home", method = RequestMethod.GET)
	public ModelAndView gotoHome(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("UserSession") != null) {
			List<Question> allQuestions = questionDao.getAllQuestions();
			logger.info(allQuestions.toString());
			return new ModelAndView("home", "allQuestions", allQuestions);
		} else {
			return new ModelAndView("index");
		}
	}

	@ModelAttribute("addUser")
	public User createUserObj() {
		return new User();
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "index";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, String> registerUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("fname") String fname, @RequestParam("lname") String lname,
			@RequestParam("email") String email, @RequestParam("password") String password) throws Exception {
		HashMap<String, String> hMap = new HashMap<String, String>();
		fname = Jsoup.parse(fname).text();
		lname = Jsoup.parse(lname).text();
		email = Jsoup.parse(email).text();
		password = Jsoup.parse(password).text();
		password = PasswordEncrypt.PasswordEncrypt(password);

		if (!emailValidate(email)) {
			return null;
		} else {
			if (!userDao.userExists(email)) {
				User usr = new User();
				String token = UUID.randomUUID().toString();
				usr.setFname(fname);
				usr.setLname(lname);
				usr.setEmail(email);
				usr.setPassword(password);
				usr.setIsActive(false);
				usr.setUniqueToken(token);
				Role role = roleDao.getRoleByName("user");
				usr.setRole(role);
				userDao.adduser(usr);
				hMap.put("Success", "Sign up Successful! You will be redirected now");
				hMap.put("fname", fname);
				hMap.put("lname", lname);
				hMap.put("email", email);
				hMap.put("uid", (String.valueOf(usr.getUserID())));
				hMap.put("token", usr.getUniqueToken());

				HttpSession session = request.getSession(true);
				session.setAttribute("UserSession", usr);
				return hMap;
			} else {
				return null;
			}
		}
	}

	@RequestMapping("user/validate/{id}/{token}")
	public String activateUser(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id,
			@PathVariable("token") String token) {
		User usr = userDao.validateUser(id, token);
		if (usr != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("UserSession", usr);
			return "active";
		}
		return null;
	}

	@RequestMapping(value = "login/checklogin", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, String> logInUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("email") String email, @RequestParam("password") String password) {
		// Remove any tags if present
		HashMap<String, String> hMap = new HashMap<String, String>();
		try {
			email = Jsoup.parse(email).text();
			password = Jsoup.parse(password).text();
			password = PasswordEncrypt.PasswordEncrypt(password);

			if (!emailValidate(email)) {
				return null;
			} else {
				User u = userDao.getUser(email, password);
				hMap.put("Success", "Login Successful! You will be redirected now");
				hMap.put("uid", String.valueOf(u.getUserID()));
				hMap.put("fname", u.getFname());
				hMap.put("lname", u.getLname());
				hMap.put("email", u.getEmail());
				HttpSession session = request.getSession(true);
				session.setAttribute("UserSession", u);
				return hMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
