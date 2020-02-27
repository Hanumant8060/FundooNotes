package com.bridgelabz.fundoo.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoo.response.ErrorResponse;
import com.bridgelabz.fundoo.response.Response;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(UserException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserException exception) {
		Response response = new Response(exception.getMessage(), "null", 400);
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ErrorResponse error = new ErrorResponse("Validation Failed", details);
		return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(NotesException.class)
	public final ResponseEntity<Object> handleNotesException(NotesException exception) {
		Response response = new Response(exception.getMessage(), "null", 400);
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LabelException.class)
	public final ResponseEntity<Object> handleLabelException(LabelException exception) {
		Response response = new Response(exception.getMessage(), "null", 400);
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

	}

}
