package com.cegedim.react.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice	//Advice (AOP) for controller
public class CustomResponseEntityException extends ResponseEntityExceptionHandler {
	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectIdException(ProjectIdException exc, WebRequest request) {
		//Object can be list of errors or one error string
		ProjectIdExceptionResponse exceptionResponse= new ProjectIdExceptionResponse(exc.getMessage());
		
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectIdException(UserNameException exc, WebRequest request) {
		//Object can be list of errors or one error string
		UserNameExceptionResponse exceptionResponse= new UserNameExceptionResponse(exc.getMessage());

		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
