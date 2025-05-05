package com.cdgtaxi.ibs.acct.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;

public abstract class CommonSuspendWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private List<Listitem> suspendReasons = ComponentUtil.convertToListitems(ConfigurableConstants.getAccountSuspendReasons(), true);
	public List<Listitem> getSuspendReasons(){
		return this.suspendReasons;
	}
	protected void suspend(String custNo, String parentCode, String code) throws Exception{
		displayProcessing();
		Date suspendDate = ((Datebox)this.getFellow("suspendDate")).getValue();
		// converting the date to current time if it is today
		if(suspendDate==null || DateUtil.isToday(suspendDate)){
			suspendDate = DateUtil.getCurrentTimestamp();
		}
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate!=null){
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.setTime(reactivateDate);
			reactivateDate = DateUtil.convertTo2359Hours(tempCalendar);
		}
		String suspendCode = (String)((Listbox)this.getFellow("suspendReasonList")).getSelectedItem().getValue();
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().suspendAcct(custNo, parentCode, code, suspendDate, reactivateDate, suspendCode, remarks, userId, false);
	}
	protected void suspend(String custNo, String subAcctNo) throws Exception{
		displayProcessing();
		Date suspendDate = ((Datebox)this.getFellow("suspendDate")).getValue();
		// converting the date to current time if it is today
		if(suspendDate==null || DateUtil.isToday(suspendDate)){
			suspendDate = DateUtil.getCurrentTimestamp();
		}
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate!=null){
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.setTime(reactivateDate);
			reactivateDate = DateUtil.convertTo2359Hours(tempCalendar);
		}
		String suspendCode = (String)((Listbox)this.getFellow("suspendReasonList")).getSelectedItem().getValue();
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().suspendAcct(custNo, subAcctNo, suspendDate, reactivateDate, suspendCode, remarks, userId, false);
	}
}