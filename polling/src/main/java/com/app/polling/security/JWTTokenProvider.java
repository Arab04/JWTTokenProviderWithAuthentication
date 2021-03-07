package com.app.polling.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenProvider {

	
	private static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);
	
	@Value("${app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication) {
		UserPrincipal userprincipal = (UserPrincipal) authentication.getPrincipal();
		
		Date now = new Date();
		Date expiry = new Date(now.getTime()+jwtExpirationInMs);
		
		return Jwts.builder()
				.setSubject(Long.toString(userprincipal.getId()))
				.setIssuedAt(now)
				.setExpiration(expiry)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
}
