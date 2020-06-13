package com.example.car.constants;

public enum AppProperties {

	JWT_TOKEN_SECRET_KEY("jwt.secret"),

	ENCRYPTION_SECRET_KEY("encryption.secret"),

	TOKEN_EXPIRY_IN_SECS_KEY("token.expiry.seconds"),

	ACCESS_TOKEN_EXPIRY_IN_SECS_KEY("jwt.access.token.expiration.seconds"),

	REFRESH_TOKEN_EXPIRY_IN_SECS_KEY("jwt.refresh.token.expiration.seconds");
	private String propName;

	AppProperties(String propName) {
		this.propName = propName;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}
}
