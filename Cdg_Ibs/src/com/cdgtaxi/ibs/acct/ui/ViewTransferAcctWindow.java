package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.util.DateUtil;

public class ViewTransferAcctWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewTransferAcctWindow.class);
	private Integer requestNo;
	@SuppressWarnings("unchecked")
	public ViewTransferAcctWindow() throws InterruptedException{
		logger.info("ViewTransferAcctWindow()");
		// getting the param
		Map<String, Integer> params = Executions.getCurrent().getArg();
		// getting request number
		requestNo = params.get("requestNo");
		// checking request number. If null, return error and go back
		if(requestNo==null){
			Messagebox.show("No Request Number!", "View Transfer Request", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException{
		logger.info("init()");
		if(requestNo == null){
			this.back();
			return;
		}
		Map<String, Object> request = this.businessHelper.getAccountBusiness().getAcctTransferReq(requestNo);
		((Label)this.getFellow("fromSalesperson")).setValue((String)request.get("fromSalesperson"));
		((Label)this.getFellow("toSalesperson")).setValue((String)request.get("toSalesperson"));
		((Label)this.getFellow("requester")).setValue((String)request.get("requester"));
		((Label)this.getFellow("reqDate")).setValue((String)request.get("reqDate"));
		((Label)this.getFellow("reqTime")).setValue((String)request.get("reqTime"));
		Date effectiveDate = (Date)request.get("effectiveDate");
		((Label)this.getFellow("effectiveDate")).setValue(DateUtil.convertDateToStr(effectiveDate, DateUtil.GLOBAL_DATE_FORMAT));
		if(effectiveDate.before(DateUtil.getCurrentDate())){
			((Button)this.getFellow("cancelBtn")).setDisabled(true);
		}
		Map<String, String> accounts = (Map<String, String>)request.get("accounts");
		Rows rows = (Rows)this.getFellow("accounts");
		for(String custNo : accounts.keySet()){
			Row row = new Row();
			row.appendChild(new Label(custNo));
			row.appendChild(new Label(accounts.get(custNo)));
			rows.appendChild(row);
		}
	}
	public void cancelRequest() throws InterruptedException{
		logger.info("cancelRequest()");
		if(Messagebox.show("Cancel Request? Action is irreversible!", "View Transfer Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			this.businessHelper.getAccountBusiness().deleteAcctTransferReq(requestNo);
			Messagebox.show("Request deleted", "View Transfer Request", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}