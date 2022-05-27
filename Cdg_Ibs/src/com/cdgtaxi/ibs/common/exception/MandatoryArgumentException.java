package com.cdgtaxi.ibs.common.exception;

public class MandatoryArgumentException extends FieldException{
	private static final long serialVersionUID = 1L;
	
	public MandatoryArgumentException(String argumentName){
		super(argumentName+" is mandatory!");
	}
	
	public MandatoryArgumentException(String fieldName, String argumentName){
		super("["+fieldName+"] "+argumentName+" is mandatory!");
	}
}
