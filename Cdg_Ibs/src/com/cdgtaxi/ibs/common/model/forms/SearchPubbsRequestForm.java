package com.cdgtaxi.ibs.common.model.forms;

import java.sql.Date;

public class SearchPubbsRequestForm {
	public Integer requestNo;
	public String status;
	public Integer billGenRequestNo;
	public Date invoiceDate;
	public Long invoiceNoFrom;
	public Long invoiceNoTo;
	public Integer accountNo;
	public Date requestDateFrom;
	public Date requestDateTo;
}
