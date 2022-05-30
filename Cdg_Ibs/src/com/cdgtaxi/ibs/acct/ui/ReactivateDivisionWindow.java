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

public class ReactivateDivisionWindow extends CommonReactivateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ReactivateDivisionWindow.class);
	private String custNo, code;
	@SuppressWarnings("unchecked")
	public ReactivateDivisionWindow() throws InterruptedException{
		logger.info("ReactivateDivisionWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Reactivate Division", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = map.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Reactivate Division", Messagebox.OK, Messagebox.ERROR);
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
		((Datebox)this.getFellow("reactivateDate")).setValue(new Date());
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
	public void reactivate() throws InterruptedException{
		validateForm();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate==null || DateUtil.isToday(reactivateDate)){
			reactivateDate = DateUtil.getCurrentTimestamp();
		}
		// additional checking for closed/terminated accounts
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, null, code)){
			List<Date> dates = new ArrayList<Date>();
			dates.add(reactivateDate);
			if(this.businessHelper.getAccountBusiness().hasPastCloseTerminate(custNo, null, code, dates)){
				Messagebox.show("Reactivation date is after Termination date!", "Reactivate Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureReactivate(custNo, null, code)){
			promptMsg = "There is existing future reactivation. Update reactivate?";
		}else{
			promptMsg = "Confirm Reactivate?";
		}
		if(Messagebox.show(promptMsg, "Reactivate Division", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.reactivate(custNo, null, code);
				Messagebox.show("Division Reactivated", "Reactivate Division", Messagebox.OK, Messagebox.INFORMATION);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, code, null , "R", null);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				LoggerUtil.printStackTrace(logger, e);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, code, null , "R", e.getMessage());
				Messagebox.show("Unable to reactivate division! Please try again later", "Reactivate Division", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}