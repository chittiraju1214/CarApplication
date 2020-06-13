package com.example.car.security.model;

import com.example.car.model.TokenPair;

public class SignIn {

	private long userId;

	private String userName;

	private String userEmail;

	private TokenPair tokenPair;

	public SignIn(long userId, String userName, String userEmail, TokenPair tokenPair) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.tokenPair = tokenPair;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public TokenPair getTokenPair() {
		return tokenPair;
	}

	public void setTokenPair(TokenPair tokenPair) {
		this.tokenPair = tokenPair;
	}

}
