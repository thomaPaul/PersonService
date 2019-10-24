package com.pthoma.personService;

import java.util.List;

public class ResponseMsg {

	private String errorMessage;
	private List<Object> responseBody;

	public ResponseMsg(List<Object> responseBody, String errorMessage) {
		super();
		this.errorMessage = errorMessage;
		this.responseBody = responseBody;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<Object> getResponseBody() {
		return responseBody;
	}

}