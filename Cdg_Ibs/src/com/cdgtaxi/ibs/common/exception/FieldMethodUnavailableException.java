package com.cdgtaxi.ibs.common.exception;

public class FieldMethodUnavailableException extends FieldException{
	private static final long serialVersionUID = 1L;
	
	public FieldMethodUnavailableException(String fieldName){
		super("["+fieldName+"] This method is unavailable for this field type");
	}
}
