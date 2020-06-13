package com.example.car.exception;

import java.io.Serializable;

public class CarServiceException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -8831435040472055457L;

	public CarServiceException(final String message) {
		super(message);
	}
}
