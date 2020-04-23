package com.cegedim.react.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNameException extends RuntimeException{
	public UserNameException(String message) {
		//Throw by try/catch block
		super(message);
	}
}
