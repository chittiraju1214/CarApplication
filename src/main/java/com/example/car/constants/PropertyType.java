package com.example.car.constants;

public enum PropertyType {

	AUTHORIZATION_TOKEN(0),

	REFRESH_TOKEN(1);

	private int tokenTypeId;

	public int getTokenTypeId() {
		return tokenTypeId;
	}

	public void setTokenTypeId(final int tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}

	PropertyType(final int tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}
}
