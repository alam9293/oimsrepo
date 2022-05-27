package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.List;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;

public abstract class CommonReactivateWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private List<Listitem> reactivateReasons = ComponentUtil.convertToListitems(ConfigurableConstants.getAccountReactivateReasons(), true);
	public List<Listitem> getReactivateReasons(){
		return this.reactivateReasons;
	}
	protected void reactivate(String custNo, String parentCode, String code) throws Exception{
		displayProcessing();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		// converting the date to current time if it is today
		if(reactivateDate==null || DateUtil.isToday(reactivateDate)){
			reactivateDate = DateUtil.getCurrentTimestamp();
		}
		String reactivateCode = (String)((Listbox)this.getFellow("reactivateReasonList")).getSelectedItem().getValue();
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().reactivateAcct(custNo, parentCode, code, reactivateDate, reactivateCode, remarks, userId);
	}
	protected void validateForm(){
		((Textbox)this.getFellow("remarks")).getValue();
	}
	
	
	protected void reactivate(String custNo, String subAcctNo) throws Exception{
		displayProcessing();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		// converting the date to current time if it is today
		if(reactivateDate==null || DateUtil.isToday(reactivateDate)){
			reactivateDate = DateUtil.getCurrentTimestamp();
		}
		String reactivateCode = (String)((Listbox)this.getFellow("reactivateReasonList")).getSelectedItem().getValue();
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().reactivateAcct(custNo, subAcctNo, reactivateDate, reactivateCode, remarks, userId);
	}
}
