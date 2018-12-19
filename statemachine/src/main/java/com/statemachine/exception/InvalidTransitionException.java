package com.statemachine.exception;

public class InvalidTransitionException extends Exception {

	private static final long serialVersionUID = 4070789368564360500L;

	public InvalidTransitionException(String message) {
		super(message);
	}

}
