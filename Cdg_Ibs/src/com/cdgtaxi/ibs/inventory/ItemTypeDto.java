package com.cdgtaxi.ibs.inventory;

import java.math.BigDecimal;

public class ItemTypeDto {
	private Integer itemTypeNo;
	private String typeName;
	private BigDecimal price;
	private Integer totalQty;
	private Integer issuedQty;
	private Integer stockQty;
	private Integer redeemedQty;
	private Integer reservedQty;
	public Integer getItemTypeNo() {
		return itemTypeNo;
	}
	public void setItemTypeNo(Integer itemTypeNo) {
		this.itemTypeNo = itemTypeNo;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}
	public Integer getIssuedQty() {
		return issuedQty;
	}
	public void setIssuedQty(Integer issuedQty) {
		this.issuedQty = issuedQty;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}
	public Integer getRedeemedQty() {
		return redeemedQty;
	}
	public void setRedeemedQty(Integer redeemedQty) {
		this.redeemedQty = redeemedQty;
	}
	public void setReservedQty(Integer reservedQty) {
		this.reservedQty = reservedQty;
	}
	public Integer getReservedQty() {
		return reservedQty;
	}
}
