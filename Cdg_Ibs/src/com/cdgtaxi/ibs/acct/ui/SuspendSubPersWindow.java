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

import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;

public class SuspendSubPersWindow extends CommonSuspendWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SuspendSubPersWindow.class);
	private String custNo, acctNo;
	@SuppressWarnings("unchecked")
	public SuspendSubPersWindow() throws InterruptedException{
		logger.info("SuspendSubPersWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Suspend Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// account number
		acctNo = map.get("acctNo");
		if(acctNo==null || acctNo.trim().length()==0){
			Messagebox.show("No account no found!", "Suspend Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		// checking
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}else if(acctNo==null || acctNo.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> subDetails = this.businessHelper.getAccountBusiness().getSubPersAccount(acctNo);
		((Label)this.getFellow("acctName")).setValue((String)subDetails.get("acctName"));
		((Datebox)this.getFellow("suspendDate")).setValue(new Date());
	}
	public void suspend() throws InterruptedException{
		Date suspendDate = ((Datebox)this.getFellow("suspendDate")).getValue();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate!=null && (suspendDate.after(reactivateDate))){
			Messagebox.show("Suspension date must be before reactivation date!", "Suspend Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(suspendDate==null || DateUtil.isToday(suspendDate)){
			suspendDate = DateUtil.getCurrentTimestamp();
		}
		// additional checking for closed/terminated accounts
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, acctNo)){
			List<Date> dates = new ArrayList<Date>();
			dates.add(suspendDate);
			if(reactivateDate!=null){
				dates.add(reactivateDate);
			}
			if(this.businessHelper.getAccountBusiness().hasPastCloseTerminate(custNo, acctNo, dates)){
				Messagebox.show("Suspension/Reactivation date is after Termination date!", "Suspend Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureSuspend(custNo, acctNo)){
			promptMsg = "There is existing future suspension. Update suspend?";
		}else{
			promptMsg = "Confirm Suspend?";
		}
		if(Messagebox.show(promptMsg, "Suspend Sub Applicant", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.suspend(custNo, acctNo);
				Messagebox.show("Sub Applicant Suspended", "Suspend Sub Applicant", Messagebox.OK, Messagebox.INFORMATION);
				
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, null, acctNo , "S", null);
				
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				LoggerUtil.printStackTrace(logger, e);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, null, acctNo , "S", e.getMessage());
				Messagebox.show("Unable to suspend sub applicant! Please try again later", "Suspend Sub Applicant", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}