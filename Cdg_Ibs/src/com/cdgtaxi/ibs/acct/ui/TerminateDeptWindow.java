package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;

public class TerminateDeptWindow extends CommonTerminateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TerminateDeptWindow.class);
	private String custNo, parentCode, code;
	@SuppressWarnings("unchecked")
	public TerminateDeptWindow() throws InterruptedException{
		logger.info("TerminateDeptWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Terminate Department", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = map.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Terminate Department", Messagebox.OK, Messagebox.ERROR);
		}
		// parent code
		parentCode = map.get("parentCode");
		if(parentCode==null || parentCode.trim().length()==0){
			Messagebox.show("No parent code found!", "Terminate Department", Messagebox.OK, Messagebox.ERROR);
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
		((Datebox)this.getFellow("terminateDate")).setValue(new Date());
	}
	public void terminate() throws InterruptedException{
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, parentCode, code)){
			promptMsg = "There is existing future termination. Update terminate?";
		}else{
			promptMsg = "Confirm Termination? This action is irreversible!";
		}
		if(Messagebox.show(promptMsg, "Terminate Department", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.terminate(custNo, parentCode, code);
				Messagebox.show("Department Terminated", "Terminate Department", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to terminate department! Please try again later", "Terminate Department", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}