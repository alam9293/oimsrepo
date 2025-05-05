package com.cdgtaxi.ibs.common.model.forms;

import java.sql.Date;

import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;

public class SearchGiroRequestForm {
	public Long	requestNo;
	public FmtbEntityMaster entity;
	public Date requestDate;
	public Integer requestTime;
	public Date valueDate;
	public Date cutoffDate;
	public String status;
}
