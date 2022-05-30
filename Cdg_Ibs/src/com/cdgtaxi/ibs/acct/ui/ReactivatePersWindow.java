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

public class ReactivatePersWindow extends CommonReactivateWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ReactivatePersWindow.class);
	private String custNo;
	@SuppressWarnings("unchecked")
	public ReactivatePersWindow() throws InterruptedException{
		logger.info("ReactivatePersWindow()");
		// getting the param
		Map<String, String> params = Executions.getCurrent().getArg();
		// getting account number
		custNo = params.get("custNo");
		// checking account number. If null, return error and go back
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No Account Number!", "Reactivate Personal", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.APPLICANT_LEVEL, null, null);
		((Label)this.getFellow("acctName")).setValue((String)details.get("acctName"));
		((Datebox)this.getFellow("reactivateDate")).setValue(new Date());
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
	}
	public void reactivate() throws InterruptedException{
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		if(reactivateDate==null || DateUtil.isToday(reactivateDate)){
			reactivateDate = DateUtil.getCurrentTimestamp();
		}
		// additional checking for closed/terminated accounts
		if(this.businessHelper.getAccountBusiness().hasFutureTerminate(custNo, null)){
			List<Date> dates = new ArrayList<Date>();
			dates.add(reactivateDate);
			if(this.businessHelper.getAccountBusiness().hasPastCloseTerminate(custNo, null, dates)){
				Messagebox.show("Reactivation date is after Closed/Termination date!", "Reactivate Personal", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		String promptMsg = null;
		if(this.businessHelper.getAccountBusiness().hasFutureReactivate(custNo, null)){
			promptMsg = "There is existing future reactivation. Update reactivate?";
		}else{
			promptMsg = "Confirm Reactivate?";
		}
		if(Messagebox.show(promptMsg, "Reactivate Personal", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.reactivate(custNo, null);
				Messagebox.show("Personal Reactivated", "Reactivate Personal", Messagebox.OK, Messagebox.INFORMATION);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, null, null , "R", null);
				this.back();
			} catch (WrongValueException wve) {
				throw wve;
			} catch (Exception e) {
				LoggerUtil.printStackTrace(logger, e);
				this.businessHelper.getAccountBusiness().sendEmail(custNo, null, null, null , "R", e.getMessage());
				Messagebox.show("Unable to reactivate personal! Please try again later", "Reactivate Personal", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}