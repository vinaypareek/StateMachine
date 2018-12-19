package com.statemachine.exception;

public class InvalidStateException extends Exception {

	private static final long serialVersionUID = 7782722393881225119L;

	public InvalidStateException(String message) {
		super(message);
	}

}
