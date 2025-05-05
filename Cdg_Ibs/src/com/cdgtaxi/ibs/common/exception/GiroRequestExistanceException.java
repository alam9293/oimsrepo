package com.cdgtaxi.ibs.common.exception;

public class GiroRequestExistanceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public GiroRequestExistanceException(){
		super("There is a request of same setup existed on the given request date");
	}
	public GiroRequestExistanceException(String errMsg){
		super(errMsg);
	}
}
