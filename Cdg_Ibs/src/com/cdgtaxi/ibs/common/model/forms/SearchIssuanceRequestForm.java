package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;

import com.cdgtaxi.ibs.common.model.AmtbAccount;

public class SearchIssuanceRequestForm {
	public String customerNo;
	public String accountName;
	public AmtbAccount account;
	public AmtbAccount division;
	public AmtbAccount department;
	public Integer requestNo;
	public String requestStatus;
	public java.sql.Date requestDateFrom;
	public java.sql.Date requestDateTo;
	public String requestor;
	public boolean isAtLeastOneCriteriaSelected;
	
	public Integer itemTypeNo;
	public BigDecimal serialNoStart;
	public BigDecimal serialNoEnd;
	public Integer maxResult = null;
	
	public SearchIssuanceRequestForm(){
		isAtLeastOneCriteriaSelected = false;
	}
}
