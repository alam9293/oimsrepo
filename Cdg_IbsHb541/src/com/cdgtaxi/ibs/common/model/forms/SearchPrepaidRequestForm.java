package com.cdgtaxi.ibs.common.model.forms;

import java.util.Date;

public class SearchPrepaidRequestForm {
	
	private Integer reqNo;
    private Integer accountNo;
	private String requestor;
    private Date requestDateFrom;
    private Date requestDateTo;
    private String status;
    private String requestType;
    private Long invoiceNoFrom;
    private Long invoiceNoTo;
    private String invoiceStatus;
    private String cardNo;
    
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getReqNo() {
		return reqNo;
	}
	public Integer getAccountNo() {
		return accountNo;
	}
	public String getRequestor() {
		return requestor;
	}
	public Date getRequestDateFrom() {
		return requestDateFrom;
	}
	public Date getRequestDateTo() {
		return requestDateTo;
	}
	public String getStatus() {
		return status;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setReqNo(Integer reqNo) {
		this.reqNo = reqNo;
	}
	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public void setRequestDateFrom(Date requestDateFrom) {
		this.requestDateFrom = requestDateFrom;
	}
	public void setRequestDateTo(Date requestDateTo) {
		this.requestDateTo = requestDateTo;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	
	
	
}
