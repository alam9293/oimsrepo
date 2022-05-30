package com.cdgtaxi.ibs.common.exception;

import java.util.Collection;

public class InvalidValueException extends FieldException{
	private static final long serialVersionUID = 1L;
	
	public InvalidValueException(String fieldName, String value, Collection<String> expectedValue){
		super("["+fieldName+"] Data is having a value("+value+") " +
				"not the same as the expected value("+expectedValue+") of this field!");
	}
}
