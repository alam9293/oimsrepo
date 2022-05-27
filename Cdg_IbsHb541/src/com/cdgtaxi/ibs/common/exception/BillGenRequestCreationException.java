package com.cdgtaxi.ibs.common.exception;

public class BillGenRequestCreationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public BillGenRequestCreationException(String errMsg){
		super(errMsg);
	}
}
