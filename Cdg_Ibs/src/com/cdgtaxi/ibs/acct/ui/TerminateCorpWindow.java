package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class TerminateCorpWindow extends CommonTerminateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TerminateCorpWindow.class);
	private String custNo, acctStatus;
	@SuppressWarnings("unchecked")
	public TerminateCorpWindow() throws InterruptedException{
		logger.info("TerminateCorpWindow()");
		// getting the param
		Map<String, String> params = Executions.getCurrent().getArg();
		// getting account number
		custNo = params.get("custNo");
		acctStatus = params.get("acctStatus");
		// checking account number. If null, return error and go back
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No Account Number!", "Terminate Corporate", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		if(acctStatus!=null && acctStatus.length()!=0 && acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED)){
			// disable the check box if it is terminated but not closed
			((Checkbox)this.getFellow("grace")).setDisabled(true);
		}
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.CORPORATE_LEVEL, null, null);
		((Label)this.getFellow("acctName")).setValue((String)details.get("acctName"));
		Rows childrenAccts = (Rows)this.getFellow("childrenAccts");
		// getting all division
		((Datebox)this.getFellow("terminateDate")).setValue(new Date());
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
		updateGrace();
	}
	public void terminate() throws InterruptedException{
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, null, null)){
			promptMsg = "There is existing future termination. Update terminate?";
		}else{
			promptMsg = "Confirm Terminate? This action is irreversible!";
		}
		if(Messagebox.show(promptMsg, "Terminate Corporate", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.terminate(custNo, null, null);
				Messagebox.show("Corporate Terminated", "Terminate Corporate", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to terminate corporate! Please try again later", "Terminate Corporate", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	public void updateGrace(){
		Date terminateDate = ((Datebox)this.getFellow("terminateDate")).getValue();
		if(terminateDate==null){
			terminateDate = DateUtil.getCurrentDate();
		}
		if(((Checkbox)this.getFellow("grace")).isChecked()){
			terminateDate = DateUtil.addMonthsToDate(ConfigurableConstants.getAccountTerminateGracePeriod(), terminateDate);
		}
		Label graceTerminateDate = (Label)this.getFellow("graceTerminateDate");
		graceTerminateDate.setValue(DateUtil.convertDateToStr(terminateDate, DateUtil.GLOBAL_DATE_FORMAT));
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}