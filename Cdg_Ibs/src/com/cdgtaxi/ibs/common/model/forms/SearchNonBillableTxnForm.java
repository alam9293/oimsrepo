package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;

public class SearchNonBillableTxnForm {
	public TmtbNonBillableBatch txnBatch;
	public String paymentType;
	public String txnStatus;
	public String taxiNo;
	public String driverID;
	public Date tripDateFrom;
	public Date tripDateTo;
	public BigDecimal totalAmount;
	public Collection<Long> rejectedTrips;
	public String offline;
	public String pspRefNo;
	public String matchingStatus;
	public List<String> matchingStatuses;
	public boolean isReport = false;
	public String policyNo;
	public List<String> policyStatuses;
	public String policyStatus;
}
