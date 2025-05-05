package com.cdgtaxi.ibs.common.exception;

@SuppressWarnings("serial")
public class DataValidationError extends Exception {
	public DataValidationError(){
		
	}
	
	public DataValidationError(String errorMsg){
		super(errorMsg);
	}
}
