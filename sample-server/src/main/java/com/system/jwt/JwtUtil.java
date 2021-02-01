package com.system.jwt;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	public static Claims parseJWT(String jwsString, String base64EncodedSecretString) {
		Claims claims = null;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(Base64.getDecoder().decode(base64EncodedSecretString))
					.build()
					.parseClaimsJws(jwsString)
					.getBody();
		} catch (JwtException e) {
			e.printStackTrace();
		}
		
		return claims;
	}
	
	public static String createJWT(String id, String issuer, String subject, long ttlMillis,
			String base64EncodedSecretString) {
		
		long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64EncodedSecretString));
	    
	    JwtBuilder builder = Jwts.builder()
	    		.setIssuer(issuer)
	    	    .setSubject(subject)
	    	    .setNotBefore(now)
	    	    .setId(id)
	    	    .signWith(secretKey);
	    
	    if (ttlMillis >= 0) {
	    	long expMillis = nowMillis + ttlMillis;
	    	Date expDate = new Date(expMillis);
	    	builder.setExpiration(expDate);
	    }
	    
	    return builder.compact();
	}
}
