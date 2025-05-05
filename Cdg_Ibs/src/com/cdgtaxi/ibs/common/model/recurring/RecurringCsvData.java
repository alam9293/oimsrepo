package com.cdgtaxi.ibs.common.model.recurring;

import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;

public class RecurringCsvData implements Comparable<RecurringCsvData> {

//	public static final String[] headerTitle = new String[] {"Reference ID","Customer Token","Amount","Currency","Merchant","Account","Invoice Date","Invoice Number","Customer Account No","Cab Charge No"};
//	public static final String[] headersVariable = new String[] { "referenceId", "customerToken", "amount", "currency",
//			"merchant", "account","invoiceDate","invoiceNo", "customerAccountNo" , "cabChargeNo"};
	public static final String[] headerTitle = new String[] {"Reference ID","Customer Token","Amount","Currency","Invoice Date","Invoice Number","Customer Account No","Cab Charge No"};
	public static final String[] headersVariable = new String[] { "referenceId", "customerToken", "amount", "currency","invoiceDate","invoiceNo", "customerAccountNo" , "cabChargeNo"};
	
	private String referenceId;
	private String customerToken;
	private String amount;
	private String currency;
//	private String merchant;
//	private String account;
	private String invoiceDate;
	private String invoiceNo;
	private String customerAccountNo;
	private String cabChargeNo;
	
	public RecurringCsvData()
	{
		
	}
			
//	public RecurringCsvData(String referenceId, String customerToken, String amount, String currency, String merchant, String account, String invoiceDate, String invoiceNo, String customerAccountNo, String cabChargeNo) {
	public RecurringCsvData(String referenceId, String customerToken, String amount, String currency, String invoiceDate, String invoiceNo, String customerAccountNo, String cabChargeNo) {
		this.referenceId = referenceId;
		this.customerToken = customerToken;
		this.amount = amount;
		this.currency = currency;
//		this.merchant = merchant;
//		this.account = account;
		this.invoiceDate = invoiceDate;
		this.invoiceNo = invoiceNo;
		this.customerAccountNo = customerAccountNo;
		this.cabChargeNo = cabChargeNo;
	}


	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getCustomerToken() {
		return customerToken;
	}

	public void setCustomerToken(String customerToken) {
		this.customerToken = customerToken;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

//	public String getMerchant() {
//		return merchant;
//	}
//
//	public void setMerchant(String merchant) {
//		this.merchant = merchant;
//	}
//
//	public String getAccount() {
//		return account;
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCustomerAccountNo() {
		return customerAccountNo;
	}

	public void setCustomerAccountNo(String customerAccountNo) {
		this.customerAccountNo = customerAccountNo;
	}

	public String getCabChargeNo() {
		return cabChargeNo;
	}

	public void setCabChargeNo(String cabChargeNo) {
		this.cabChargeNo = cabChargeNo;
	}

	private BmtbBillGenReq billGenReq;

	public BmtbBillGenReq getBillGenReq() {
		return billGenReq;
	}

	public void setBillGenReq(BmtbBillGenReq billGenReq) {
		this.billGenReq = billGenReq;
	}
	
	public String[] getRecordData() {
//		return new String[] { getReferenceId(), getCustomerToken(),getAmount(),getCurrency(),getMerchant(),getAccount(),getInvoiceDate(),getInvoiceNo(),getCustomerAccountNo(),getCabChargeNo()};
		return new String[] { getReferenceId(), getCustomerToken(),getAmount(),getCurrency(),getInvoiceDate(),getInvoiceNo(),getCustomerAccountNo(),getCabChargeNo()};
	}

	public int compareTo(RecurringCsvData o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static String[] getHeadertitle() {
		return headerTitle;
	}
	
	public static String[] getHeadersvariable() {
		return headersVariable;
	}

}
