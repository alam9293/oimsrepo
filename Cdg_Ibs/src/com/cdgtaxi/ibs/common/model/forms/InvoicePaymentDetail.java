package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;

public class InvoicePaymentDetail {
	private BmtbInvoiceHeader invoiceHeader;
	private BigDecimal minAppliedAmount;
	private BigDecimal totalAppliedAmount;
	private boolean isFullPayment;
	private Map<Long, BigDecimal> invoiceDetailAppliedAmount;
	private Map<Long, BmtbInvoiceDetail> invoiceDetailApplied;
	
	public InvoicePaymentDetail(BmtbInvoiceHeader invoiceHeader){
		if(invoiceHeader == null) throw new IllegalArgumentException("Invoice Header cannot be null");
		this.invoiceHeader = invoiceHeader;
		minAppliedAmount = new BigDecimal(0.00);
		totalAppliedAmount = new BigDecimal(0.00);
		invoiceDetailAppliedAmount = new HashMap<Long, BigDecimal>();
		invoiceDetailApplied = new HashMap<Long, BmtbInvoiceDetail>();
		isFullPayment = false;
	}
	
	public BmtbInvoiceHeader getInvoiceHeader() {
		return invoiceHeader;
	}
	public void setInvoiceHeader(BmtbInvoiceHeader invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}
	public BigDecimal getMinAppliedAmount() {
		return minAppliedAmount;
	}
	public void setMinAppliedAmount(BigDecimal minAppliedAmount) {
		this.minAppliedAmount = minAppliedAmount;
	}
	public BigDecimal getTotalAppliedAmount() {
		return totalAppliedAmount;
	}
	public void setTotalAppliedAmount(BigDecimal totalAppliedAmount) {
		this.totalAppliedAmount = totalAppliedAmount;
	}
	public Map<Long, BigDecimal> getInvoiceDetailAppliedAmount() {
		return invoiceDetailAppliedAmount;
	}
	public void setInvoiceDetailAppliedAmount(
			Map<Long, BigDecimal> invoiceDetailAppliedAmount) {
		this.invoiceDetailAppliedAmount = invoiceDetailAppliedAmount;
	}
	public boolean isFullPayment() {
		return isFullPayment;
	}
	public void setFullPayment(boolean isFullPayment) {
		this.isFullPayment = isFullPayment;
	}
	public Map<Long, BmtbInvoiceDetail> getInvoiceDetailApplied() {
		return invoiceDetailApplied;
	}
	public void setInvoiceDetailApplied(
			Map<Long, BmtbInvoiceDetail> invoiceDetailApplied) {
		this.invoiceDetailApplied = invoiceDetailApplied;
	}
}
