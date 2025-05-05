package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;
import java.util.Date;

import com.cdgtaxi.ibs.master.model.MstbAcquirer;

public class SearchBankPaymentAdviseForm {
	public BigDecimal collectionAmount;
	public String txnRefNo;
	public Date creditDateFrom;
	public Date creditDateTo;
	public MstbAcquirer acquirer;
	public Long paymentNo;
}
