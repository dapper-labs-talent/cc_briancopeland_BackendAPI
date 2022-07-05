package com.example.restservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.UUID;

public class JWTWrapper {

	private static String EMAIL = "email";
	private static String defaultIssuer = "auth0";
	//TODO Change to pub-sub method to enable key rotation, and multiple instances running
	//this service
    public static Algorithm algorithm = Algorithm.HMAC256(UUID.randomUUID().toString());


    public static String getToken(String customerEmail) throws JWTCreationException {
		String token = JWT.create()
			.withClaim(EMAIL, customerEmail)
			.withIssuer(defaultIssuer)
			.sign(algorithm);
		return token;
    }

	public static String verifyToken(String authToken) throws JWTVerificationException {
		JWTVerifier verifier = JWT.require(algorithm)
			.withIssuer(defaultIssuer)
			.withClaimPresence(EMAIL)
			.build();
		DecodedJWT jwt = verifier.verify(authToken);
		return jwt.getClaim(EMAIL).asString();
	}
}
