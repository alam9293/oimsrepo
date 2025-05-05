package com.cdgtaxi.ibs.common.exception;

@SuppressWarnings("serial")
public class CDGEInventoryInterfaceException extends Exception {
	private final String message;

	public CDGEInventoryInterfaceException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
