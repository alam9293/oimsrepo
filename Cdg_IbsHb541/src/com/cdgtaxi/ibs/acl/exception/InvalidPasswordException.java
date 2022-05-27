package com.cdgtaxi.ibs.acl.exception;

public class InvalidPasswordException extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidPasswordException(String exceptionMessage){
		super(exceptionMessage);
	}
}
