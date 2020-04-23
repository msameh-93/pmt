package com.cegedim.react.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.cegedim.react.exceptions.InvalidLoginResponse;
import com.google.gson.Gson;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	//handles exceptions thrown when authentication is required
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,		
			AuthenticationException authException) throws IOException, ServletException {
		InvalidLoginResponse loginResponse= new InvalidLoginResponse();
		
		String jsonLoginResponse= new Gson().toJson(loginResponse);
		
		response.setContentType("application/json");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().print(jsonLoginResponse);
	}	
	
	
}
