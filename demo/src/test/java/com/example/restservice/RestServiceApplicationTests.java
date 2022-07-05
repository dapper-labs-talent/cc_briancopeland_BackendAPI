package com.example.restservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RestServiceApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  void verifyJWT() {
    String email = "myemail@abc.com";
    String jwtToken = JWTWrapper.getToken(email);
    assertTrue(email.equals(JWTWrapper.verifyToken(jwtToken)));
  }

  @Test
  void verifyPasswordHash() {
    String password = "foobar";
    String hashedPassword = "w6uP8Tcg6K2QR905Rms8iXTlksL6OD1KOWBxTK7wxPI=";
    assertTrue(hashedPassword.equals(PasswordUtils.hashPassword(password)));
  }

}