package com.cegedim.react.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cegedim.react.domain.User;

//Validates binding result data
//Password length and match validation
@Service
public class UserValidator implements Validator {	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);		//validate correct object
	}

	@Override
	public void validate(Object target, Errors errors) {
		//errors: bindingResult object passed through spring 
		User user=(User) target;
		
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Password must be greater than 6 characters long");	//reject value in bindingResult
		}
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Match", "Passwords do not match");
		}
	}
	
}
