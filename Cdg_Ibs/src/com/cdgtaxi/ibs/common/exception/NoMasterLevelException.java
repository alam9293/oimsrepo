package com.cdgtaxi.ibs.common.exception;

public class NoMasterLevelException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public NoMasterLevelException(){
		super("No master found for this master");
	}
	public NoMasterLevelException(String errMsg){
		super(errMsg);
	}
}
