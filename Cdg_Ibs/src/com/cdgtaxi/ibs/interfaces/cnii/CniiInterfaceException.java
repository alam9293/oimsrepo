package com.cdgtaxi.ibs.interfaces.cnii;

public class CniiInterfaceException extends Exception {
	private static final long serialVersionUID = 1L;
	public CniiInterfaceException(){
		super("CNII Exception");
	}
	public CniiInterfaceException(String message) {
		super(message);
	}
}