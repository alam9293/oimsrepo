package com.cdgtaxi.ibs.common.exception;

public class NoDetailLevelException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public NoDetailLevelException(){
		super("No details found for this master");
	}
	public NoDetailLevelException(String errMsg){
		super(errMsg);
	}
}
