package com.cdgtaxi.ibs.common.exception;

public class ProcessUploadTripBatchException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProcessUploadTripBatchException(String msg){
		super(msg);
	}
	
	public ProcessUploadTripBatchException(String msg, String code){
		super(msg + " > Code :"+code);
	}
}
