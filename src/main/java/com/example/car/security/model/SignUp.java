package com.example.car.security.model;

import java.util.List;

import com.example.car.model.UserInformation;

public class SignUp {

	List<UserInformation> successfullyRegistered;

	List<UserInformation> alreadyExisting;

	public SignUp(List<UserInformation> successfullyRegistered, List<UserInformation> alreadyExisting) {
		super();
		this.successfullyRegistered = successfullyRegistered;
		this.alreadyExisting = alreadyExisting;
	}

}
