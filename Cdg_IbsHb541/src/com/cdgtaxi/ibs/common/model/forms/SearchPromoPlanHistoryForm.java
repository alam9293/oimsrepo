package com.cdgtaxi.ibs.common.model.forms;

import java.util.Date;

import com.cdgtaxi.ibs.common.model.PmtbProductType;

public class SearchPromoPlanHistoryForm {
	public String name;
	public String type;
	public PmtbProductType productType;
	public String promoType;
	public Date effDateFrom;
	public Date effDateTo;
	public String jobType;
	public String vehicleModel;
	public boolean isAtLeastOneCriteriaSelected = false;
	public Date effCutOffDate;
	public String reqToStatus;
}
