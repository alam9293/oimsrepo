package com.cdgtaxi.ibs.common.exception;

public class InvalidFieldTypeValueException extends FieldException{
	private static final long serialVersionUID = 1L;
	
	public InvalidFieldTypeValueException(String fieldName){
		super("["+fieldName+"] The data type of the value is not the expected data type!");
	}
}
