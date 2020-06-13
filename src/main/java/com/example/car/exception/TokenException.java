package com.example.car.exception;

public class TokenException extends RuntimeException {
	private static final long serialVersionUID = -1977220980184789024L;

	public TokenException(final String message) {

		super(message);
	}

	public TokenException(final Throwable cause) {

		super(cause);
	}

	public TokenException(final String message, final Throwable cause) {

		super(message, cause);
	}

}
