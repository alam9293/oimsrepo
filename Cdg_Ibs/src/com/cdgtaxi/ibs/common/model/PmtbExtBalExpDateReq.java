package com.cdgtaxi.ibs.common.model;

import java.util.Date;






@SuppressWarnings("serial")
public class PmtbExtBalExpDateReq extends PmtbPrepaidReq implements java.io.Serializable {

	private String newBalExpDateDurType;
	private Integer newBalExpDateDurLen;
	private Date oldBalExpDate;
	private Date newBalExpDate;
	private PmtbProduct pmtbProduct;
	public String getNewBalExpDateDurType() {
		return newBalExpDateDurType;
	}
	public Integer getNewBalExpDateDurLen() {
		return newBalExpDateDurLen;
	}
	public Date getNewBalExpDate() {
		return newBalExpDate;
	}
	public PmtbProduct getPmtbProduct() {
		return pmtbProduct;
	}
	public void setNewBalExpDateDurType(String newBalExpDateDurType) {
		this.newBalExpDateDurType = newBalExpDateDurType;
	}
	public void setNewBalExpDateDurLen(Integer newBalExpDateDurLen) {
		this.newBalExpDateDurLen = newBalExpDateDurLen;
	}
	public void setNewBalExpDate(Date newBalExpDate) {
		this.newBalExpDate = newBalExpDate;
	}
	public void setPmtbProduct(PmtbProduct pmtbProduct) {
		this.pmtbProduct = pmtbProduct;
	}
	public Date getOldBalExpDate() {
		return oldBalExpDate;
	}
	public void setOldBalExpDate(Date oldBalExpDate) {
		this.oldBalExpDate = oldBalExpDate;
	}
	
	
	

	
	
	
}
