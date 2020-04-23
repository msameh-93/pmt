package com.cegedim.react.security;

public class SecurityConstants {
	public static final String SIGN_UP_URLS= "/api/users/**";
	public static final String H2URL="h2-console/**";
	//JWT
	public static final String SECRET= "MySuperUltraSecretKeyForJwt";
	public static final String TOKEN_PREFIX= "Bearer ";	//Bearer tokens
	public static final String HEADER_STRING= "Authorization";
	public static final long TOKEN_EXPIRY= 600_000; 	//30 seconds
}
