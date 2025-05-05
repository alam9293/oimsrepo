package com.cdgtaxi.ibs.common.exception;

import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class InsufficientGiftStockException extends Exception {
	Integer qtyNeeded, qtyAvailable;

	public InsufficientGiftStockException(Integer qtyNeeded, Integer qtyAvailable) {
		this.qtyNeeded = qtyNeeded;
		this.qtyAvailable = qtyAvailable;
	}

	@Override
	public String getMessage() {
		return "Insufficient Stock. " + StringUtil.numberToString(qtyNeeded, StringUtil.GLOBAL_INTEGER_FORMAT) +
		" item(s) required but only " + StringUtil.numberToString(qtyAvailable, StringUtil.GLOBAL_INTEGER_FORMAT) +
		" item(s) are available in stock.";
	}
}
