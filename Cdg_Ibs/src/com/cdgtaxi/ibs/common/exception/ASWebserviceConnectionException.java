package com.cdgtaxi.ibs.common.exception;

public class ASWebserviceConnectionException extends Exception{
	private static final long serialVersionUID = 1L;
	public ASWebserviceConnectionException(){
		super("Unable to establish connection with AS Webservice!");
	}
}
