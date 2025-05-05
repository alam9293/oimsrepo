package com.cdgtaxi.ibs.common.exception;

public class BillGenRequestNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public BillGenRequestNotFoundException(Integer billGenRequestNo){
		super("Bill gen request "+billGenRequestNo+" not found!");
	}
	public BillGenRequestNotFoundException(String errMsg){
		super(errMsg);
	}
}
