package com.example.car.controller;

import javax.validation.Valid;
import java.util.List;

import com.example.car.security.model.RegistrationRequest;
import com.example.car.security.model.SignInRequest;
import com.example.car.security.model.SignUp;
import com.example.car.service.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationService authenticationService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/register")
	public ResponseEntity<Void> requestRegistrationRequest(
			@RequestBody @Valid List<RegistrationRequest> registrationRequest) throws Exception {
		LOGGER.info("Entered registration");
		SignUp signUp = authenticationService.generateRegistrationRequest(registrationRequest);
		return new ResponseEntity(signUp, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/login")
	public ResponseEntity<Void> performLogin(@RequestBody @Valid final SignInRequest signInRequest) throws Exception {
		LOGGER.info("inside do login method of authentication controller for login");
		return new ResponseEntity(authenticationService.generateLoginRequest(signInRequest), HttpStatus.OK);
	}
}
