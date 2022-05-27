package com.cdgtaxi.ibs.common.exception;

@SuppressWarnings("serial")
public class DuplicateCodeError extends DataValidationError {
	
	private String message;
	
	public DuplicateCodeError() {
		super();
		
		//default message
		message = "An existing record with the same code already exists. Please specify another code.";
	}
	
	public DuplicateCodeError(String message) {
		super();
		
		//default message
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
