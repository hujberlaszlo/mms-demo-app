package com.tsystems.mms.demoapp.user.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
