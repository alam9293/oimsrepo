package com.cdgtaxi.ibs.common.model.forms;

import java.sql.Date;

import com.cdgtaxi.ibs.common.model.AmtbAccount;

public class SearchAdjustmentRequestForm {
	public String customerNo;
	public String accountName;
	public AmtbAccount account;
	public Date requestDateFrom;
	public Date requestDateTo;
	public String requestStatus;
	public String requester;
	public boolean isAtLeastOneCriteriaSelected;
	
	public SearchAdjustmentRequestForm(){
		isAtLeastOneCriteriaSelected = false;
	}
}
