package com.cdgtaxi.ibs.report.exception;

public class FormatNotSupportedException extends InterruptedException {
	private static final long serialVersionUID = 1L;
	public FormatNotSupportedException(){
		super("Format Not Supported");
	}
	public FormatNotSupportedException(String errMsg){
		super(errMsg);
	}
}
