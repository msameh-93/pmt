package com.cegedim.react.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cegedim.react.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	public String generateToken(Authentication authentication) {	//paramter from auth created in user controller (signin method)
		//Generate Token
		User user=(User) authentication.getPrincipal();	//Get user being authenticated (configured in security config)
		String userId= Long.toString(user.getId());
		Date now= new Date(System.currentTimeMillis());
		Date expiryTime= new Date(now.getTime()+SecurityConstants.TOKEN_EXPIRY);
		
		Map<String, Object> claims= new HashMap<>();
		claims.put("id", userId);
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullName());
		//Role (IF IMPLEMENTED)
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiryTime)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.compact();
	}
	//Verify Token
	public boolean validateToken(String token) {
		try {
			//Parse token
			Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
			//if no errors throw== valid
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//Get user ID from token
	public Long getUserIdFromToken(String token) {
		//Extract claims
		Claims claims= Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
		//return ID
		return Long.parseLong((String)claims.get("id"));		
	}
}
