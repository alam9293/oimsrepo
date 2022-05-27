package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.List;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;

public abstract class CommonTerminateWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private List<Listitem> terminateReasons = ComponentUtil.convertToListitems(ConfigurableConstants.getAccountTerminateReasons(), true);
	public List<Listitem> getTerminateReasons(){
		return this.terminateReasons;
	}
	protected void terminate(String custNo, String subAcctNo) throws Exception{
		displayProcessing();
		Date terminateDate = ((Datebox)this.getFellow("terminateDate")).getValue();
		// converting the date to current time if it is today
		if(terminateDate==null || DateUtil.isToday(terminateDate)){
			terminateDate = DateUtil.getCurrentTimestamp();
		}
		String terminateCode = (String)((Listbox)this.getFellow("terminateReasonList")).getSelectedItem().getValue();
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		String userId = this.getUserLoginIdAndDomain();
		Checkbox grace = (Checkbox)this.getFellowIfAny("grace");
		if(grace!=null){
			if(grace.isChecked()){
				this.businessHelper.getAccountBusiness().terminateAcct(custNo, subAcctNo, terminateDate, terminateCode, remarks, userId);
				terminateDate = DateUtil.addMonthsToDate(ConfigurableConstants.getAccountTerminateGracePeriod(), terminateDate);
			}
		}
		this.businessHelper.getAccountBusiness().closeAcct(custNo, subAcctNo, terminateDate, terminateCode, remarks, userId);
	}
	protected void terminate(String custNo, String parentCode, String code) throws Exception{
		displayProcessing();
		Date terminateDate = ((Datebox)this.getFellow("terminateDate")).getValue();
		// converting the date to current time if it is today
		if(terminateDate==null || DateUtil.isToday(terminateDate)){
			terminateDate = DateUtil.getCurrentTimestamp();
		}
		String terminateCode = (String)((Listbox)this.getFellow("terminateReasonList")).getSelectedItem().getValue();
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		String userId = this.getUserLoginIdAndDomain();
		Checkbox grace = (Checkbox)this.getFellowIfAny("grace");
		if(grace!=null){
			if(grace.isChecked()){
				this.businessHelper.getAccountBusiness().terminateAcct(custNo, parentCode, code, terminateDate, terminateCode, remarks, userId);
				terminateDate = DateUtil.addMonthsToDate(ConfigurableConstants.getAccountTerminateGracePeriod(), terminateDate);
			}
		}
		this.businessHelper.getAccountBusiness().closeAcct(custNo, parentCode, code, terminateDate, terminateCode, remarks, userId);
	}
}