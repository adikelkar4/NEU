package com.finalproject.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.finalproject.pojo.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fname", "error.invalid.fname", "Please enter first name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lname", "error.invalid.lname", "Please enter last name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.actor", "Please enter email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.actress", "Please enter password");
	}

}
