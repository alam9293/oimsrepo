package com.cdgtaxi.ibs.common.exception;

import com.cdgtaxi.ibs.common.model.LrtbGiftItem;

@SuppressWarnings("serial")
public class RewardItemIsUsedException extends Exception {
	LrtbGiftItem item;

	public RewardItemIsUsedException(LrtbGiftItem item) {
		this.item = item;
	}

	@Override
	public String getMessage() {
		return item.getItemName() + " cannot be deleted because there is stock movement.";
	}
}
