package com.cdgtaxi.ibs.common.exception;

public class ProcessTripException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProcessTripException(String msg){
		super(msg);
	}
	
	public ProcessTripException(String msg, String code){
		super(msg + " > Code :"+code);
	}
}
