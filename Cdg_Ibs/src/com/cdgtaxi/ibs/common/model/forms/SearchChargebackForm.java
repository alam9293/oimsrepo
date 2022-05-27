package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;
import java.util.Date;

import com.cdgtaxi.ibs.master.model.MstbAcquirer;

public class SearchChargebackForm {
	public MstbAcquirer acquirer;
	public String paymentType;
	public String taxiNo;
	public String driverID;
	public String jobNo;
	public Date tripDateFrom;
	public Date tripDateTo;
	public BigDecimal totalAmount;
	public String offline;
	public String txnStatus;
}
