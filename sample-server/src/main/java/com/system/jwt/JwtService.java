package com.system.jwt;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String base64EncodedSecretKey;
	
	@Value("${jwt.ttl}")
	private long timeToLiveMinute;
	
	@Value("${jwt.issuer}")
	private String issuer;
	
	public String createUserJWT(String userName) {
		String id = UUID.randomUUID().toString();
		long ttlMillis = timeToLiveMinute * 60 * 1000;
		return JwtUtil.createJWT(id, issuer, userName, ttlMillis, base64EncodedSecretKey);
	}
	
	public Boolean validateUserJWT(String userJwt) {
		if (JwtUtil.parseJWT(userJwt, base64EncodedSecretKey) != null) {
			return true;
		} else {
			return false;
		}
	}
}
