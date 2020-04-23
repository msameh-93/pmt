package com.cegedim.react.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Thrown from try/catch block
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException {
	
	public ProjectIdException(String message) {
		super(message);
	}
}
