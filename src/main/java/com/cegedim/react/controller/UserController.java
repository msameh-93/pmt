package com.cegedim.react.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegedim.react.domain.User;
import com.cegedim.react.payload.JwtGeneratedResponse;
import com.cegedim.react.payload.LoginRequest;
import com.cegedim.react.security.JwtTokenProvider;
import com.cegedim.react.security.SecurityConstants;
import com.cegedim.react.services.MapValidationError;
import com.cegedim.react.services.UserService;
import com.cegedim.react.services.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private MapValidationError validator;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authManager;		//Autowired from Security config
	
	//ENSURE RIGHT USER, THEN PROVIDE TOKEN
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody@Valid LoginRequest loginReq, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return validator.validate(bindingResult);
		}
		Authentication auth= authManager.authenticate(	//spring method
										new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(auth);	//sets the 'authenticator'
		String jwt= SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(auth);
		
		return new ResponseEntity<JwtGeneratedResponse>(new JwtGeneratedResponse(true, jwt), HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody@Valid User newUser, BindingResult bindingResult) {
		userValidator.validate(newUser, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return validator.validate(bindingResult);
		}
		newUser.setConfirmPassword("");
		User user= userService.saveUser(newUser);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
}
