package com.cdgtaxi.ibs.common.model.forms;

import java.util.Date;

public class SearchInvoiceForm extends SearchByAccountForm {
	Long invoiceNoFrom;
	Long invoiceNoTo;
	Date invoiceDateFrom;
	Date invoiceDateTo;
	String invoiceStatus;

	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public Long getInvoiceNoFrom() {
		return invoiceNoFrom;
	}
	public void setInvoiceNoFrom(Long invoiceNoFrom) {
		this.invoiceNoFrom = invoiceNoFrom;
	}
	public Long getInvoiceNoTo() {
		return invoiceNoTo;
	}
	public void setInvoiceNoTo(Long invoiceNoTo) {
		this.invoiceNoTo = invoiceNoTo;
	}
	public Date getInvoiceDateFrom() {
		return invoiceDateFrom;
	}
	public void setInvoiceDateFrom(Date invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
	}
	public Date getInvoiceDateTo() {
		return invoiceDateTo;
	}
	public void setInvoiceDateTo(Date invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
	}
	@Override
	public boolean isAtLeastOneCriteriaSelected() {
		return super.isAtLeastOneCriteriaSelected()
		|| invoiceNoFrom != null
		|| invoiceNoTo != null
		|| invoiceDateFrom != null
		|| invoiceDateTo != null
		|| (invoiceStatus != null && !invoiceStatus.trim().equals(""));
	}
}
