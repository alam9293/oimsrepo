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

public class SuspendDeptWindow extends CommonSuspendWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SuspendDeptWindow.class);
	private String custNo, parentCode, code;
	@SuppressWarnings("unchecked")
	public SuspendDeptWindow() throws InterruptedException{
		logger.info("SuspendDeptWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Suspend Department", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = map.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Suspend Department", Messagebox.OK, Messagebox.ERROR);
		}
		// parent code
		parentCode = map.get("parentCode");
		if(parentCode==null || parentCode.trim().length()==0){
			Messagebox.show("No parent code found!", "Suspend Department", Messagebox.OK, Messagebox.ERROR);
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
		((Datebox)this.getFellow("suspendDate")).setValue(new Date());
	}
	public void suspend() throws InterruptedException{
		Date suspendDate = ((Datebox)this.getFellow("suspendDate")).getValue();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate!=null && (suspendDate.after(reactivateDate))){
			Messagebox.show("Suspension date must be before reactivation date!", "Suspend Department", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(suspendDate==null || DateUtil.isToday(suspendDate)){
			suspendDate = DateUtil.getCurrentTimestamp();
		}
		// additional checking for closed/terminated accounts
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, parentCode, code)){
			List<Date> dates = new ArrayList<Date>();
			dates.add(suspendDate);
			if(reactivateDate!=null){
				dates.add(reactivateDate);
			}
			if(this.businessHelper.getAccountBusiness().hasPastCloseTerminate(custNo, parentCode, code, dates)){
				Messagebox.show("Suspension/Reactivation date is after Termination date!", "Suspend Department", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureSuspend(custNo, parentCode, code)){
			promptMsg = "There is existing future suspension. Update suspend?";
		}else{
			promptMsg = "Confirm Suspend?";
		}
		if(Messagebox.show(promptMsg, "Suspend Department", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.suspend(custNo, parentCode, code);
				Messagebox.show("Department Suspended", "Suspend Department", Messagebox.OK, Messagebox.INFORMATION);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, parentCode, code, null , "S", null);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				LoggerUtil.printStackTrace(logger, e);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, parentCode, code, null , "S", e.getMessage());
				Messagebox.show("Unable to suspend department! Please try again later", "Suspend Department", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}