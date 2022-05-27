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

public class TerminatePersWindow extends CommonTerminateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TerminatePersWindow.class);
	private String custNo;
	@SuppressWarnings("unchecked")
	public TerminatePersWindow() throws InterruptedException{
		logger.info("TerminatePersWindow()");
		// getting the param
		Map<String, String> params = Executions.getCurrent().getArg();
		// getting account number
		custNo = params.get("custNo");
		// checking account number. If null, return error and go back
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No Account Number!", "Terminate Personal", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.APPLICANT_LEVEL, null, null);
		((Label)this.getFellow("acctName")).setValue((String)details.get("acctName"));
		((Datebox)this.getFellow("terminateDate")).setValue(new Date());
		Rows childrenAccts = (Rows)this.getFellow("childrenAccts");
		// getting all sub applicant
		Map<Integer, Map<String, Object>> subAppls = this.businessHelper.getAccountBusiness().getAccounts(custNo, null, 1);
		if(!subAppls.isEmpty()){
			for(Integer subApplId : subAppls.keySet()){
				Map<String, Object> subAppl = subAppls.get(subApplId);
				Row subApplRow = new Row();
				subApplRow.appendChild(new Label(subAppl.get("acctName").toString()));
				subApplRow.appendChild(new Label(NonConfigurableConstants.ACCOUNT_CATEGORY.get(subAppl.get("acctCategory"))));
				subApplRow.appendChild(new Label(NonConfigurableConstants.ACCOUNT_STATUS.get(subAppl.get("acctStatus"))));
				String creditBalance = StringUtil.bigDecimalToString((BigDecimal)subAppl.get("creditBalance"), StringUtil.GLOBAL_DECIMAL_FORMAT);
				subApplRow.appendChild(new Label(creditBalance));
				String creditLimit = StringUtil.bigDecimalToString((BigDecimal)subAppl.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT);
				subApplRow.appendChild(new Label(creditLimit));
				childrenAccts.appendChild(subApplRow);
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
		if(Messagebox.show(promptMsg, "Terminate Personal", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.terminate(custNo, null);
				Messagebox.show("Personal Terminated", "Terminate Personal", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to terminate personal! Please try again later", "Terminate Personal", Messagebox.OK, Messagebox.ERROR);
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