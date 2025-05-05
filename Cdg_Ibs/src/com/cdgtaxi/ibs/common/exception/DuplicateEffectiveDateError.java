package com.cdgtaxi.ibs.common.exception;

@SuppressWarnings("serial")
public class DuplicateEffectiveDateError extends DataValidationError {
	public DuplicateEffectiveDateError() {
		super();
	}

	@Override
	public String getMessage() {
		return "An existing record with the same effective date already exists. Please specify another effective date.";
	}

}
