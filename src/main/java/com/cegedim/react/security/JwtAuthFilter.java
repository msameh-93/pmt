package com.cegedim.react.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cegedim.react.domain.User;
import com.cegedim.react.services.CustomUserDetailService;

public class JwtAuthFilter extends OncePerRequestFilter {	//Filter that runs once for every request
	//Filter used to validate token against custom UserDetail service
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private CustomUserDetailService userDetail;
	
	
	//USER REQUESTS DATA, ENSURE TOKEN IS VALID
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt= null;
		try {
			String bearerToken= request.getHeader(SecurityConstants.HEADER_STRING);	//Will be supplied from client
			if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
				jwt= bearerToken.substring(7, bearerToken.length());	//Get JWT from Authorization header
			}
			if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userId= tokenProvider.getUserIdFromToken(jwt);
				User user= userDetail.loadUserById(userId);
				
				//New Authenticator
				UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(
											user.getUsername(), null, Collections.emptyList());	//List of roles
				//configure authenticator details
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//assign authenticator to context
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
			
		} catch(Exception e) {
			logger.error("Could not set user authentication in security context", e);
		}
		
		//If all goes well, call next in filter
		filterChain.doFilter(request, response);
		
	}

	
	
}
