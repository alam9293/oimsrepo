package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;

public class GstInfo {

	private BigDecimal amountWithoutGst;
	private BigDecimal gst;
	private BigDecimal amountWithGst;
	
	public BigDecimal getAmountWithoutGst() {
		return amountWithoutGst;
	}
	public void setAmountWithoutGst(BigDecimal amountWithoutGst) {
		this.amountWithoutGst = amountWithoutGst;
	}
	public BigDecimal getGst() {
		return gst;
	}
	public void setGst(BigDecimal gst) {
		this.gst = gst;
	}
	public BigDecimal getAmountWithGst() {
		return amountWithGst;
	}
	public void setAmountWithGst(BigDecimal amountWithGst) {
		this.amountWithGst = amountWithGst;
	}
	
	
	
	
}
