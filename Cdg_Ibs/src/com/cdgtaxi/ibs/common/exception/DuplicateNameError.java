package com.cdgtaxi.ibs.common.exception;

@SuppressWarnings("serial")
public class DuplicateNameError extends DataValidationError {
	public DuplicateNameError() {
		super();
	}

	@Override
	public String getMessage() {
		return "An existing record with the same name already exists. Please specify another name.";
	}

}
