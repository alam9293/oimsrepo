package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;
import java.util.Date;

import com.cdgtaxi.ibs.master.model.MstbAcquirer;

public class SearchAcquirerMdrForm {
	public MstbAcquirer acquirer;
	public BigDecimal rate;
	public Date effDateFromDate;
	public Date effDateToDate;
	public boolean isAtLeastOneCriteriaSelected = false;
}
