package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class SuspendDivisionWindow extends CommonSuspendWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SuspendDivisionWindow.class);
	private String custNo, code;
	@SuppressWarnings("unchecked")
	public SuspendDivisionWindow() throws InterruptedException{
		logger.info("SuspendDivWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Suspend Division", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = map.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Suspend Division", Messagebox.OK, Messagebox.ERROR);
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
		((Datebox)this.getFellow("suspendDate")).setValue(new Date());
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
	public void suspend() throws InterruptedException{
		Date suspendDate = ((Datebox)this.getFellow("suspendDate")).getValue();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate!=null && (suspendDate.after(reactivateDate))){
			Messagebox.show("Suspension date must be before reactivation date!", "Suspend Division", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(suspendDate==null || DateUtil.isToday(suspendDate)){
			suspendDate = DateUtil.getCurrentTimestamp();
		}
		// additional checking for closed/terminated accounts
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, null, code)){
			List<Date> dates = new ArrayList<Date>();
			dates.add(suspendDate);
			if(reactivateDate!=null){
				dates.add(reactivateDate);
			}
			if(this.businessHelper.getAccountBusiness().hasPastCloseTerminate(custNo, null, code, dates)){
				Messagebox.show("Suspension/Reactivation date is after Termination date!", "Suspend Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureSuspend(custNo, null, code)){
			promptMsg = "There is existing future suspension. Update suspend?";
		}else{
			promptMsg = "Confirm Suspend?";
		}
		if(Messagebox.show(promptMsg, "Suspend Division", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.suspend(custNo, null, code);
				Messagebox.show("Division Suspended", "Suspend Division", Messagebox.OK, Messagebox.INFORMATION);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, code, null , "S", null);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				LoggerUtil.printStackTrace(logger, e);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, code, null , "S", e.getMessage());
				Messagebox.show("Unable to suspend division! Please try again later", "Suspend Division", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}