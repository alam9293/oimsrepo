package com.cdgtaxi.ibs.common.exception;

public class IncorrectLengthException extends FieldException{
	private static final long serialVersionUID = 1L;
	
	public IncorrectLengthException(String valueLength, String expectedLength){
		super("Raw String Data is having a length("+valueLength+") " +
					"not the same as the expected length("+expectedLength+")!");
	}
	
	public IncorrectLengthException(String fieldName, String valueLength, String expectedLength){
		super("["+fieldName+"] Expected Value argument is having a length("+valueLength+") " +
				"not the same as the expected length("+expectedLength+") of this field!");
	}
	public IncorrectLengthException(String value){
		super(value);
	}
	
}
