package com.cdgtaxi.ibs.common.exception;

public class BillGenRequestExistanceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public BillGenRequestExistanceException(){
		super("There is a request of same setup existed on the given bill gen request date");
	}
	public BillGenRequestExistanceException(String errMsg){
		super(errMsg);
	}
}
