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

public class ReactivateCorpWindow extends CommonReactivateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ReactivateCorpWindow.class);
	private String custNo;
	@SuppressWarnings("unchecked")
	public ReactivateCorpWindow() throws InterruptedException{
		logger.info("ReactivateCorpWindow()");
		// getting the param
		Map<String, String> params = Executions.getCurrent().getArg();
		// getting account number
		custNo = params.get("custNo");
		// checking account number. If null, return error and go back
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No Account Number!", "Reactivate Corporate", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.CORPORATE_LEVEL, null, null);
		((Label)this.getFellow("acctName")).setValue((String)details.get("acctName"));
		((Datebox)this.getFellow("reactivateDate")).setValue(new Date());
		Rows childrenAccts = (Rows)this.getFellow("childrenAccts");
		// getting all division
		Map<Integer, Map<String, Object>> divisions = this.businessHelper.getAccountBusiness().getAccounts(custNo, null, 1);
		if(!divisions.isEmpty()){
			for(Integer divId : divisions.keySet()){
				Map<String, Object> division = divisions.get(divId);
				Row divisionRow = new Row();
				String divLabel = division.get("acctName") + "(" + division.get("acctCode") + ")";
				divisionRow.appendChild(new Label(divLabel));
				divisionRow.appendChild(new Label(NonConfigurableConstants.ACCOUNT_CATEGORY.get(division.get("acctCategory"))));
				divisionRow.appendChild(new Label(NonConfigurableConstants.ACCOUNT_STATUS.get(division.get("acctStatus"))));
				String creditBalance = StringUtil.bigDecimalToString((BigDecimal)division.get("creditBalance"), StringUtil.GLOBAL_DECIMAL_FORMAT);
				divisionRow.appendChild(new Label(creditBalance));
				String creditLimit = StringUtil.bigDecimalToString((BigDecimal)division.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT);
				divisionRow.appendChild(new Label(creditLimit));
				childrenAccts.appendChild(divisionRow);
				Map<Integer, Map<String, Object>> depts = this.businessHelper.getAccountBusiness().getAccounts(custNo, (String)division.get("acctCode"), 2);
				for(Integer deptId : depts.keySet()){
					Map<String, Object> dept = depts.get(deptId);
					Row deptRow = new Row();
					String deptLabel = dept.get("acctName") + "(" + dept.get("acctCode") + ")";
					deptRow.appendChild(new Label(deptLabel));
					deptRow.appendChild(new Label(NonConfigurableConstants.ACCOUNT_CATEGORY.get(dept.get("acctCategory"))));
					deptRow.appendChild(new Label(NonConfigurableConstants.ACCOUNT_STATUS.get(dept.get("acctStatus"))));
					creditBalance = StringUtil.bigDecimalToString((BigDecimal)dept.get("creditBalance"), StringUtil.GLOBAL_DECIMAL_FORMAT);
					deptRow.appendChild(new Label(creditBalance));
					creditLimit = StringUtil.bigDecimalToString((BigDecimal)dept.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT);
					deptRow.appendChild(new Label(creditLimit));
					childrenAccts.appendChild(deptRow);
				}
			}
		}
	}
	public void reactivate() throws InterruptedException{
		validateForm();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate==null || DateUtil.isToday(reactivateDate)){
			reactivateDate = DateUtil.getCurrentTimestamp();
		}
		// additional checking for closed/terminated accounts
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, null, null)){
			List<Date> dates = new ArrayList<Date>();
			dates.add(reactivateDate);
			if(this.businessHelper.getAccountBusiness().hasPastCloseTerminate(custNo, null, null, dates)){
				Messagebox.show("Reactivation date is after Closed/Termination date!", "Reactivate Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureReactivate(custNo, null, null)){
			promptMsg = "There is existing future reactivation. Update reactivate?";
		}else{
			promptMsg = "Confirm Reactivate?";
		}
		if(Messagebox.show(promptMsg, "Reactivate Corporate", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.reactivate(custNo, null, null);
				Messagebox.show("Corporate Reactivated", "Reactivate Corporate", Messagebox.OK, Messagebox.INFORMATION);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, null, null, "R", null);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				LoggerUtil.printStackTrace(logger, e);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, null, null, "R", e.getMessage());
				Messagebox.show("Unable to reactivate corporate! Please try again later", "Reactivate Corporate", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}