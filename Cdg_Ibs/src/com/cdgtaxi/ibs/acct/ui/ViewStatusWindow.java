package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;

public class ViewStatusWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewStatusWindow.class);
	private String custNo, parentCode, code, acctNo;
	@SuppressWarnings("unchecked")
	public ViewStatusWindow() throws InterruptedException{
		logger.info("ViewStatusWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		// account code
		code = map.get("code");
		// parent code
		parentCode = map.get("parentCode");
		// account number
		acctNo = map.get("acctNo");
		this.setWidth("75%");
		this.setMinwidth(100);
		this.setSizable(false);
		this.setClosable(true);
	}
	public void init(){
		logger.info("init()");
		Rows rows = (Rows)this.getFellow("status");
		List<Map<String, String>> statuses = this.businessHelper.getAccountBusiness().getStatuses(custNo, acctNo, parentCode, code);
		for(Map<String, String> status : statuses){
			Row row = new Row();
			row.appendChild(new Label(status.get("reason")));
			row.appendChild(new Label(status.get("status")));
			row.appendChild(new Label(status.get("effectiveDate")));
			row.appendChild(new Label(status.get("user")));
			row.appendChild(new Label(status.get("remarks")));
			// for deletion of status
			Date effectiveDate = DateUtil.convertStrToDate(status.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
			if(!DateUtil.isToday(effectiveDate) && effectiveDate.after(DateUtil.getCurrentDate())){
				if(!status.get("status").equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED)) 
						&& !status.get("status").equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED))){
					Menupopup deletePopup = new Menupopup();
					Menuitem deleteItem = new Menuitem("Delete Status");
					deletePopup.appendChild(deleteItem);
//					ZScript showInfo = ZScript.parseContent("viewStatusWindow.deleteStatus("+status.get("statusNo")+")");
//					showInfo.setLanguage("java");
//					EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
//					// adding script to label
//					deleteItem.addEventHandler("onClick", pdEvent);
					row.setContext("deletePopup");
				}
			}
			rows.appendChild(row);
		}
	}
	public void deleteStatus(Integer statusNo) throws InterruptedException{
		logger.info("deleteStatus(Integer statusNo)");
		if(Messagebox.show("Delete status?", "View Status History", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			this.businessHelper.getAccountBusiness().deleteStatus(statusNo);
			Messagebox.show("Status Deleted", "View Status History", Messagebox.OK, Messagebox.QUESTION);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		init();
	}
}
