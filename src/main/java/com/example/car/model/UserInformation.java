package com.example.car.model;

public class UserInformation {

	private String userName;

	private String userEmail;

	public UserInformation(String userName, String userEmail) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

}
