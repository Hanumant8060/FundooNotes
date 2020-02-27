package com.bridgelabz.fundoo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus
public class NotesException extends Exception {
	public NotesException(String exception) {
		super(exception);
	}
	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
