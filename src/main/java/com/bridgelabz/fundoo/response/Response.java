package com.bridgelabz.fundoo.response;

public class Response {
	private String message;
	private Object result;
	private int status;

	public String getMessage() {
		return message;
	}

	public Object getResult() {
		return result;
	}

	public int getStatus() {
		return status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Response(String message, Object result, int status) {
		super();
		this.message = message;
		this.result = result;
		this.status = status;
	}

}
