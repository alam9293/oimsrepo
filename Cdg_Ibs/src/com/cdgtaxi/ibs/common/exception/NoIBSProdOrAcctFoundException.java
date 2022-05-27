package com.cdgtaxi.ibs.common.exception;

public class NoIBSProdOrAcctFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public NoIBSProdOrAcctFoundException(){
		super("No equivalent IBS product or account found exception");
	}
	public NoIBSProdOrAcctFoundException(String errMsg){
		super(errMsg);
	}
}
