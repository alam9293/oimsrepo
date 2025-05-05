package com.cdgtaxi.ibs.common.model.forms;

import java.sql.Date;

public class SearchTransactionCodeForm {
	public Integer entityNo;
	public String taxType;
	public String txnCode;
	public String txnType;
	public Date effDateFromDate;
	public Date effDateToDate;

	public boolean isAtLeastOneCriteriaSelected = false;
}
