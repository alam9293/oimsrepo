package com.cdgtaxi.ibs.interfaces.vitalmaster;

public class VitalMasterLoginRegInterfaceException extends Exception {
	private static final long serialVersionUID = 1L;
	public VitalMasterLoginRegInterfaceException(){
		super("Vital Master Login Registration Exception");
	}
	public VitalMasterLoginRegInterfaceException(String message) {
		super(message);
	}
}