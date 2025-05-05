package com.cdgtaxi.ibs.common.exception;


@SuppressWarnings("serial")
public class InsufficientInventoryStockException extends Exception {
	@Override
	public String getMessage() {
		return "There is insufficient stock to be issued";
	}
}
