package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

public class TerminateSubPersWindow extends CommonTerminateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TerminateSubPersWindow.class);
	private String custNo, acctNo;
	@SuppressWarnings("unchecked")
	public TerminateSubPersWindow() throws InterruptedException{
		logger.info("TerminateSubPersWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Terminate Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// account number
		acctNo = map.get("acctNo");
		if(acctNo==null || acctNo.trim().length()==0){
			Messagebox.show("No account no found!", "Terminate Sub Applicant", Messagebox.OK, Messagebox.ERROR);
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
		((Datebox)this.getFellow("terminateDate")).setValue(new Date());
	}
	public void terminate() throws InterruptedException{
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, acctNo)){
			promptMsg = "There is existing future termination. Update terminate?";
		}else{
			promptMsg = "Confirm Termination? This action is irreversible!";
		}
		if(Messagebox.show(promptMsg, "Terminate Sub Applicant", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.terminate(custNo, acctNo);
				Messagebox.show("Sub Applicant Terminated", "Terminate Sub Applicant", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to terminate sub applicant! Please try again later", "Terminate Sub Applicant", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}