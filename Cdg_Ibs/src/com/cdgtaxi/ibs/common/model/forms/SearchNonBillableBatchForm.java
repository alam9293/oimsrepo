package com.cdgtaxi.ibs.common.model.forms;

import java.util.Date;
import java.util.List;

import com.cdgtaxi.ibs.master.model.MstbAcquirer;

public class SearchNonBillableBatchForm {
	public String batchNo;
	public String mid;
	public String tid;
	public List<String> matchingStatus;
	public String completeStatus;
	public Date uploadDateFrom;
	public Date uploadDateTo;
	public Date creditDateFrom;
	public Date creditDateTo;
	public MstbAcquirer acquirer;
	public String paymentType;
	public String batchStatus;
	public boolean isAtLeastOneCriteriaSelected = false;
	public String jobNo;
	public String interfaceMappingValue;
}
