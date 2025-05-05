package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;

public class SearchGiftItemForm {
	Integer categoryNo;
	String itemCode;
	String itemName;
	Integer pointsFrom;
	Integer pointsTo;
	BigDecimal priceFrom;
	BigDecimal priceTo;

	public Integer getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(Integer categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getPointsFrom() {
		return pointsFrom;
	}
	public void setPointsFrom(Integer pointsFrom) {
		this.pointsFrom = pointsFrom;
	}
	public Integer getPointsTo() {
		return pointsTo;
	}
	public void setPointsTo(Integer pointsTo) {
		this.pointsTo = pointsTo;
	}
	public BigDecimal getPriceFrom() {
		return priceFrom;
	}
	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}
	public BigDecimal getPriceTo() {
		return priceTo;
	}
	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}
}
