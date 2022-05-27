package com.cdgtaxi.ibs.common.exception;


@SuppressWarnings("serial")
public class InventorySerialNoExistsException extends Exception {
	@Override
	public String getMessage() {
		return "The Serial No.(s) specified are already used";
	}
}
