package com.cegedim.react.exceptions;

//Used in sending proper response entity exception error
public class ProjectIdExceptionResponse {
	
	private String identifier;	//Will store error message

	public ProjectIdExceptionResponse(String identifier) {
		this.identifier = identifier;
	}

	public final String getIdentifier() {	//Uses getter name to set key name
		return identifier;
	}

	public final void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
