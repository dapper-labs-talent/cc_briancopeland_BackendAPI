package com.example.restservice;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.restservice.model.GetUsersResponse;
import com.example.restservice.model.Signup;
import com.example.restservice.model.User;
import com.example.restservice.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class Controllers {

	private static final String TOKEN = "token";
	private static final String MISSING_REQUIRED_PARAMETER_MSG = "Missing required parameter %s";
	private static final String EMAIL_ALREADY_EXISTS_MSG = "User with email %s already exists";
	private static final String INVALID_PASSWORD_MSG = "Invalid password for email %s";

	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String USERS = "users";
	private static final String X_AUTHENTICATION_TOKEN = "x-authentication-token";

	@Autowired
	UserRepository repository;

	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody Signup request) {
		try {
			if (request.getFirstName() == null) {
				return new ResponseEntity<String>(String.format(MISSING_REQUIRED_PARAMETER_MSG, FIRST_NAME), 
													HttpStatus.BAD_REQUEST);
			} else if (request.getLastName() == null) {
				return new ResponseEntity<String>(String.format(MISSING_REQUIRED_PARAMETER_MSG, LAST_NAME), 
													HttpStatus.BAD_REQUEST);
			} else if (request.getEmail() == null) {
				return new ResponseEntity<String>(String.format(MISSING_REQUIRED_PARAMETER_MSG, EMAIL), 
													HttpStatus.BAD_REQUEST);
			} else if (request.getPassword() == null){
				return new ResponseEntity<String>(String.format(MISSING_REQUIRED_PARAMETER_MSG, PASSWORD), 
													HttpStatus.BAD_REQUEST);
			}

			User user = new User(request.getEmail(), request.getHashedPassword(), request.getFirstName(), request.getLastName());
			User existingUser = repository.findByEmail(user.getEmail());
			if (existingUser != null){
				return new ResponseEntity<String>(String.format(EMAIL_ALREADY_EXISTS_MSG, user.getEmail()), 
													HttpStatus.BAD_REQUEST);
			}
			repository.save(user);
			HashMap<String, String> jwtMap = new HashMap<>();

			String jwtToken = JWTWrapper.getToken(request.getEmail());
			jwtMap.put(TOKEN, jwtToken);
			return new ResponseEntity<Object>(jwtMap, HttpStatus.OK);
		} catch (JWTCreationException exception){
			return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody Signup request) {
		if (request.getEmail() == null) {
			return new ResponseEntity<String>(String.format(MISSING_REQUIRED_PARAMETER_MSG, EMAIL), 
												HttpStatus.BAD_REQUEST);
		} else if (request.getPassword() == null){
			return new ResponseEntity<String>(String.format(MISSING_REQUIRED_PARAMETER_MSG, PASSWORD), 
												HttpStatus.BAD_REQUEST);
		}

		String email = request.getEmail();
		String hashedPassword = request.getHashedPassword();
		User user = repository.findByEmail(email);
		if (!hashedPassword.equals(user.getPassword())){
			return new ResponseEntity<String>(String.format(INVALID_PASSWORD_MSG, email), HttpStatus.BAD_REQUEST);
		}
		
		HashMap<String, String> jwtMap = new HashMap<>();
		try {
			String jwtToken = JWTWrapper.getToken(request.getEmail());
			jwtMap.put(TOKEN, jwtToken);
			return new ResponseEntity<Object>(jwtMap, HttpStatus.OK);
		} catch (JWTCreationException exception){
			return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/users")
	public ResponseEntity getUsers(@RequestHeader(X_AUTHENTICATION_TOKEN) String authHeader) {
		Map<String, List<GetUsersResponse>> jsonMap = new HashMap<>();
		try {
			JWTWrapper.verifyToken(authHeader);

			List<User> users = repository.findAll();
			List<GetUsersResponse> userResponses = users.stream().map(u -> new GetUsersResponse(u)).collect(Collectors.toList());
			jsonMap.put(USERS, userResponses);

			return new ResponseEntity<Object>(jsonMap, HttpStatus.OK);
		} catch (JWTVerificationException exception) {
			return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/users")
	public ResponseEntity putUser(@RequestHeader(X_AUTHENTICATION_TOKEN) String authHeader, @RequestBody Signup request) {
		try {
			if (request.getFirstName() == null) {
				return new ResponseEntity<String>(String.format(MISSING_REQUIRED_PARAMETER_MSG, FIRST_NAME), 
													HttpStatus.BAD_REQUEST);
			} else if (request.getLastName() == null) {
				return new ResponseEntity<String>(String.format(MISSING_REQUIRED_PARAMETER_MSG, LAST_NAME), 
													HttpStatus.BAD_REQUEST);
			}

			String customerEmail = JWTWrapper.verifyToken(authHeader);

			User user = repository.findByEmail(customerEmail);
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			repository.save(user);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (JWTVerificationException exception) {
			return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}