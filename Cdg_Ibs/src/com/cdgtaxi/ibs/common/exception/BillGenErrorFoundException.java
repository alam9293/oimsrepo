package com.cdgtaxi.ibs.common.exception;

public class BillGenErrorFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public BillGenErrorFoundException(){
		super("The corporate is facing error in bill gen that has not yet been resolved. " +
				"Please resolve the error first before proceeding to create a new bill gen request.");
	}
	public BillGenErrorFoundException(String errMsg){
		super(errMsg);
	}
}
