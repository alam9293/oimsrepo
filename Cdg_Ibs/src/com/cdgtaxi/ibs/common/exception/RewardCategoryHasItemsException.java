package com.cdgtaxi.ibs.common.exception;

import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;

@SuppressWarnings("serial")
public class RewardCategoryHasItemsException extends Exception {
	LrtbGiftCategory category;

	public RewardCategoryHasItemsException(LrtbGiftCategory category) {
		this.category = category;
	}

	@Override
	public String getMessage() {
		return category.getCategoryName() + " still has items associated with it. " +
		"Delete or move the items to another category then try again.";
	}
}
