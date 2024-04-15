package com.projeto.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.projeto.models.User;

@Service
public class TokenService {

	private String secret = "secret";

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			String token = JWT.create()
					.withIssuer("proxy-api")
					.withSubject(user.getEmail())
					.withExpiresAt(getExpirationDate())
					.sign(algorithm);
			return token;

		} catch (JWTCreationException exception) {
			throw new RuntimeException("ERROR WHILE GENERATING TOKEN", exception);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.require(algorithm)
					.withIssuer("proxy-api")
					.build()
					.verify(token)
					.getSubject();
		}

		catch (JWTVerificationException exception) {
			return "";
		}
	}

	private Instant getExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
