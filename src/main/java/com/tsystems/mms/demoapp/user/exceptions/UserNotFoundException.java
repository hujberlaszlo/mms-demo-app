package com.tsystems.mms.demoapp.user.exceptions;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
