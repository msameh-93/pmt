package com.cegedim.react.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {	//DTO for user log in request (Abstraction)
	@NotBlank(message= "Please provide User Name for logging in")
	private String username;
	@NotBlank(message= "Please enter password")
	private String password;
	
	
	public final String getUsername() {
		return username;
	}
	public final void setUsername(String username) {
		this.username = username;
	}
	public final String getPassword() {
		return password;
	}
	public final void setPassword(String password) {
		this.password = password;
	}
		
}
