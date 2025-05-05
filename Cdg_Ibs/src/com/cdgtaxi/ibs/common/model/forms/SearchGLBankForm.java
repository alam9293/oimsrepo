package com.cdgtaxi.ibs.common.model.forms;

import java.sql.Date;

public class SearchGLBankForm {
	public Integer entityNo;
	public String bankCode;
	public String branchCode;
	public Date effDateFromDate;
	public Date effDateToDate;

	public boolean isAtLeastOneCriteriaSelected = false;
}
