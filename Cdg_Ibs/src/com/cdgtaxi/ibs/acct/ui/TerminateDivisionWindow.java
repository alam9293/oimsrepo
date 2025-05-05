package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.StringUtil;

public class TerminateDivisionWindow extends CommonTerminateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TerminateDivisionWindow.class);
	private String custNo, code;
	@SuppressWarnings("unchecked")
	public TerminateDivisionWindow() throws InterruptedException{
		logger.info("TerminateDivisionWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Terminate Division", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = map.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Terminate Division", Messagebox.OK, Messagebox.ERROR);
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
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.DIVISION_LEVEL, null, code);
		((Label)this.getFellow("acctName")).setValue((String)details.get("acctName"));
		((Datebox)this.getFellow("terminateDate")).setValue(new Date());
		Rows childrenAccts = (Rows)this.getFellow("childrenAccts");
		// getting all depts
		Map<Integer, Map<String, Object>> depts = this.businessHelper.getAccountBusiness().getAccounts(custNo, code, 2);
		for(Integer deptId : depts.keySet()){
			Map<String, Object> dept = depts.get(deptId);
			Row deptRow = new Row();
			String deptLabel = dept.get("acctName") + "(" + dept.get("acctCode") + ")";
			deptRow.appendChild(new Label(deptLabel));
			deptRow.appendChild(new Label(NonConfigurableConstants.ACCOUNT_CATEGORY.get(dept.get("acctCategory"))));
			deptRow.appendChild(new Label(NonConfigurableConstants.ACCOUNT_STATUS.get(dept.get("acctStatus"))));
			String creditBalance = StringUtil.bigDecimalToString((BigDecimal)dept.get("creditBalance"), StringUtil.GLOBAL_DECIMAL_FORMAT);
			deptRow.appendChild(new Label(creditBalance));
			String creditLimit = StringUtil.bigDecimalToString((BigDecimal)dept.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT);
			deptRow.appendChild(new Label(creditLimit));
			childrenAccts.appendChild(deptRow);
		}
	}
	public void terminate() throws InterruptedException{
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, null, code)){
			promptMsg = "There is existing future termination. Update terminate?";
		}else{
			promptMsg = "Confirm Terminate? This action is irreversible!";
		}
		if(Messagebox.show(promptMsg, "Terminate Division", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.terminate(custNo, null, code);
				Messagebox.show("Division Terminated", "Terminate Division", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to terminate division! Please try again later", "Terminate Division", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}