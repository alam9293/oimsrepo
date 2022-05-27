package com.cdgtaxi.ibs.common.exception;


@SuppressWarnings("serial")
public class InventorySerialNoUnavailableException extends Exception {
	@Override
	public String getMessage() {
		return "The Serial No.(s) specified are unavailable";
	}
}
