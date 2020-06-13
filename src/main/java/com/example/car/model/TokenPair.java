package com.example.car.model;

public class TokenPair {

	private String accessToken;

	private String refreshToken;

	public TokenPair(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

}
