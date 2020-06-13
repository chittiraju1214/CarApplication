package com.example.car.security.model;

import javax.validation.constraints.NotNull;

public class SignInRequest {

	@NotNull
	private String userEmail;

	@NotNull
	private String password;

	public String getUserEmail() {
		return userEmail;
	}

	public String getPassword() {
		return password;
	}

}
