package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;

public class ReactivateDeptWindow extends CommonReactivateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ReactivateDeptWindow.class);
	private String custNo, parentCode, code;
	@SuppressWarnings("unchecked")
	public ReactivateDeptWindow() throws InterruptedException{
		logger.info("ReactivateDeptWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Reactivate Department", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = map.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Reactivate Department", Messagebox.OK, Messagebox.ERROR);
		}
		// parent code
		parentCode = map.get("parentCode");
		if(parentCode==null || parentCode.trim().length()==0){
			Messagebox.show("No parent code found!", "Reactivate Department", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		if(code==null || code.trim().length()==0){
			this.back();
			return;
		}
		if(parentCode==null || parentCode.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.DEPARTMENT_LEVEL, parentCode, code);
		((Label)this.getFellow("acctName")).setValue((String)details.get("acctName"));
		((Datebox)this.getFellow("reactivateDate")).setValue(new Date());
	}
	public void reactivate() throws InterruptedException{
		validateForm();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate==null || DateUtil.isToday(reactivateDate)){
			reactivateDate = DateUtil.getCurrentTimestamp();
		}
		// additional checking for closed/terminated accounts
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, parentCode, code)){
			List<Date> dates = new ArrayList<Date>();
			dates.add(reactivateDate);
			if(this.businessHelper.getAccountBusiness().hasPastCloseTerminate(custNo, parentCode, code, dates)){
				Messagebox.show("Reactivation date is after Termination date!", "Reactivate Department", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureReactivate(custNo, parentCode, code)){
			promptMsg = "There is existing future reactivation. Update reactivate?";
		}else{
			promptMsg = "Confirm Reactivate?";
		}
		if(Messagebox.show(promptMsg, "Reactivate Department", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.reactivate(custNo, parentCode, code);
				Messagebox.show("Department Reactivated", "Reactivate Department", Messagebox.OK, Messagebox.INFORMATION);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, parentCode, code, null, "R", null);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				LoggerUtil.printStackTrace(logger, e);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, parentCode, code, null, "R", e.getMessage());
				Messagebox.show("Unable to reactivate department! Please try again later", "Reactivate Department", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}