package com.bridgelabz.fundoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException {
	public UserException(String exception) {
		super(exception);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
