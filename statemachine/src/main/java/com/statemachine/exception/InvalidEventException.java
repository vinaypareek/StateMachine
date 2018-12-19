package com.statemachine.exception;

public class InvalidEventException extends Exception {

	private static final long serialVersionUID = 2896118288175029488L;

	public InvalidEventException(String message) {
		super(message);
	}

}
