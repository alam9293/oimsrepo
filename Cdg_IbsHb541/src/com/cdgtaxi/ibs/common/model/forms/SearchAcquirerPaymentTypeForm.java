package com.cdgtaxi.ibs.common.model.forms;

import java.util.Date;

public class SearchAcquirerPaymentTypeForm {
	public String name;
	public Integer acquirerNo;
	public String value;
	public Date effDateFromDate;
	public Date effDateToDate;
	public boolean isAtLeastOneCriteriaSelected = false;
}
