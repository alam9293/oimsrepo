package com.cdgtaxi.ibs.acl.exception;

public class PasswordUsedException extends Exception{
	private static final long serialVersionUID = 1L;

	public PasswordUsedException(String exceptionMessage){
		super(exceptionMessage);
	}
}
