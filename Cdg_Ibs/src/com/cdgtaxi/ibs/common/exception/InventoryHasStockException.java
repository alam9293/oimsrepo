package com.cdgtaxi.ibs.common.exception;

@SuppressWarnings("serial")
public class InventoryHasStockException extends Exception {
	public InventoryHasStockException() {
	}

	@Override
	public String getMessage() {
		return "Item Type still has stock and cannot be deleted.";
	}
}
