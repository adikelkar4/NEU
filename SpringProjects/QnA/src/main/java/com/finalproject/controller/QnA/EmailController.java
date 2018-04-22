package com.finalproject.controller.QnA;

import java.io.IOException;
import java.io.InputStream;

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
			@RequestParam String userEmail) {

		// Reading Email Form Input Parameters
		emailSubject = "Let the QnA Begin!!";
		emailMessage = "Welcome " + userFname
				+ ", This email confirms your sign up to QnA. QnA is a Web Development Tools and Methods final project created by the one and only debugger Aditya!";
		emailToRecipient = userEmail;
		logger.info(emailFromRecipient);
		logger.info(emailMessage);
		logger.info(userFname);
		

		// Logging The Email Form Parameters For Debugging Purpose
		System.out.println("\nReceipient?= " + emailToRecipient + ", Subject?= " + emailSubject + ", Message?= "
				+ emailMessage + "\n");

		mailSenderObj.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setFrom(emailFromRecipient);
				mimeMsgHelperObj.setText(emailMessage);
				mimeMsgHelperObj.setSubject(emailSubject);
			}
		});
		System.out.println("\nMessage Sent Successfully....!\n");

		return "Email sending is blah";
	}
}
