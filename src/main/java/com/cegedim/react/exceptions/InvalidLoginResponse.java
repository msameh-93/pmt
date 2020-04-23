package com.cegedim.react.exceptions;

public class InvalidLoginResponse {	//Response object returned as an exception message
	private String username;
	private String password;
	
	public InvalidLoginResponse() {
		this.username= "Invalid User Name / or bearer token missing";
		this.password= "Invalid Password";
	}

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
