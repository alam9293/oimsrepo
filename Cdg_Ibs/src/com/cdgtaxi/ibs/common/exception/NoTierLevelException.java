package com.cdgtaxi.ibs.common.exception;

public class NoTierLevelException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NoTierLevelException(){
		super("No tiers found for this master");
	}
	public NoTierLevelException(String errMsg){
		super(errMsg);
	}
}
