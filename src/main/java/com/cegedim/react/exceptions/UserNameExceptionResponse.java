package com.cegedim.react.exceptions;

//Used as a DTO to hold error response data
public class UserNameExceptionResponse {
	private String username;
	
	public UserNameExceptionResponse(String username) {
		this.username= username;
	}
	
	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}
	
	
}
