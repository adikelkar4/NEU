package com.finalproject.controller.QnA;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmailController {
	static String emailToRecipient, emailSubject, emailMessage;
	static final String emailFromRecipient = "adi.sandbox";
	static ModelAndView modelViewObj;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private JavaMailSender mailSenderObj;

	// This Method Is Used To Prepare The Email Message And Send It To The Client
	@ResponseBody
	@RequestMapping(value = "sendEmail", method = RequestMethod.POST)
	public String sendEmailToClient(HttpServletRequest request, @RequestParam String userFname,
			@RequestParam String userEmail, @RequestParam String userId, @RequestParam String token) {
		String baseUrl = String.format("%s://%s:%d/QnA/", request.getScheme(), request.getServerName(),
				request.getServerPort());
		String retVal = "/user/validate/"+userId+"/"+token;
		// Reading Email Form Input Parameters
		emailSubject = "Let the QnA Begin!!";
		emailMessage = "";
		emailMessage += "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<body>\r\n"
				+ "<div class=\"main\" style=\"font-family: 'Trebuchet MS', 'Bookman Old Style', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;width: 50%;border: 1px solid #e2e2e2;border-radius: 1%;margin-top: 4%;margin-left: 4%;background-color: whitesmoke;\">\r\n"
				+ "<div class=\"inner_container\">\r\n"
				+ "<h2 style=\"text-align: center; color: black;\">Let knowledge flow!</h2>\r\n"
				+ "<div class=\"registerform\" style=\"padding: 5%;\">";
		emailMessage += "<p>Hey " + userFname + ",</p>";
		emailMessage += "<p>Welcome to the QnA squad! You can post any kind of question and our community of awesome users will be there to answer them. Lets get started then!</p>\r\n";
		emailMessage += "<p>Just click on the link below to activate your account</p>";
		emailMessage += "<p>" + baseUrl + "user/validate/"+userId+"/"+token + "</p>";
		emailMessage += "<p></p><p>Remember these golden words:<strong> Be Nice, Be Respectful</strong></p>";
		emailMessage += "<p>Best,</p>\r\n" + "<p></p>\r\n" + "<p>TEAM QnA</p>\r\n" + "</div>\r\n" + "</div>\r\n"
				+ "</div>\r\n" + "</body>\r\n" + "</html>";
		emailToRecipient = userEmail;

		// Logging The Email Form Parameters For Debugging Purpose
		System.out.println("\nReceipient?= " + emailToRecipient + ", Subject?= " + emailSubject + ", Message?= "
				+ emailMessage + "\n");

		mailSenderObj.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setFrom(emailFromRecipient);
				mimeMsgHelperObj.setText(emailMessage, true);
				mimeMsgHelperObj.setSubject(emailSubject);
			}
		});
		System.out.println("\nMessage Sent Successfully....!\n");

		return "";
	}
}
