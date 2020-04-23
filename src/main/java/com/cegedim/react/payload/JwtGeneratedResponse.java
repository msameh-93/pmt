package com.cegedim.react.payload;


public class JwtGeneratedResponse {	//DTO response for JWT created for logged in user
	
	private boolean succes;
	private String token;
	
	
	public JwtGeneratedResponse(boolean succes, String token) {
		super();
		this.succes = succes;
		this.token = token;
	}
	
	public final boolean isSucces() {
		return succes;
	}
	public final void setSucces(boolean succes) {
		this.succes = succes;
	}
	public final String getToken() {
		return token;
	}
	public final void setToken(String token) {
		this.token = token;
	}
	
	

}
