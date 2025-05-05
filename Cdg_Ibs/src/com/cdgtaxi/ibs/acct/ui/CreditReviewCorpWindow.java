package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class CreditReviewCorpWindow extends CommonCreditReviewWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreditReviewCorpWindow.class);
	private String custNo;
	@SuppressWarnings("unchecked")
	public CreditReviewCorpWindow() throws InterruptedException{
		logger.info("CreditReviewCorpWindow()");
		// getting the param
		Map<String, String> params = Executions.getCurrent().getArg();
		// getting account number
		custNo = params.get("custNo");
		// checking account number. If null, return error and go back
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No Account Number!", "Credit Review Corporate", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.CORPORATE_LEVEL, null, null);
		((Label)this.getFellow("acctName")).setValue((String)details.get("acctName"));
		((Label)this.getFellow("creditLimit")).setValue(StringUtil.bigDecimalToString((BigDecimal)details.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT));
		((Label)this.getFellow("creditBalance")).setValue((String)details.get("creditBalance"));
		((Datebox)this.getFellow("fromDate")).setValue(new Date());
	}
	public void creditReview() throws InterruptedException, WrongValueException{
		if(this.businessHelper.getAccountBusiness().hasPendingCreditReview(custNo)){
			Messagebox.show("There is existing pending request!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		String promptMsg;
		BigDecimal newCreditLimit = ((Decimalbox)this.getFellow("newCreditLimit")).getValue();
		BigDecimal creditLimit = new BigDecimal(((Label)this.getFellow("creditLimit")).getValue().replaceAll(",", ""));
		if(creditLimit.equals(newCreditLimit)){
			Messagebox.show("No change in credit limit!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}	
		String reviewType = (String)((Listbox)this.getFellow("reviewTypeList")).getSelectedItem().getValue();
		ArrayList<String> tempCreditLimitDivDeptCodes = new ArrayList<String>();
		if(reviewType.equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)){
			Date toDate = ((Datebox)this.getFellow("toDate")).getValue();
			if(toDate==null){// checking effective to must not be null
				Messagebox.show("Effective Date To cannot be empty for temporary credit review!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}else{
				Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
				if(fromDate.after(toDate)){// checking that from date is after to date
					Messagebox.show("Effective Date To must be equal to or later than Effective Date From!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				if(!DateUtil.isToday(fromDate) && fromDate.before(DateUtil.getCurrentDate())){// checking from date must not before today
					Messagebox.show("Effective Date From must be equal to or later than today!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
			// now checking the credit limit must be higher than perm credit limit
			if(creditLimit.compareTo(newCreditLimit)>0){
				Messagebox.show("Temporary credit limit must be greater than permanent credit limit!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			
			if(this.businessHelper.getAccountBusiness().hasTempChildFutureCreditLimit(custNo, null, null)){
				tempCreditLimitDivDeptCodes.add("yes");
			}
			
			if(this.businessHelper.getAccountBusiness().hasTempCreditLimit(custNo, null, null)){
				promptMsg = "There is existing temporary credit limit. Update?";
			}else{
				promptMsg = "Confirm review?";
			}
		}else{
			// checking for higher than parent or lower than children.
			if(this.businessHelper.getAccountBusiness().checkChildrenCreditLimit(custNo, null, newCreditLimit)){
				Messagebox.show("Credit limit must be greater than children's credit limit!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
			if(this.businessHelper.getAccountBusiness().hasPermCreditLimit(custNo, null, null, fromDate)){
				Messagebox.show("There is existing permanent credit limit on that date!", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if(!DateUtil.isToday(fromDate) && fromDate.before(DateUtil.getCurrentDate())){
				Messagebox.show("Effective Date From must be later than today!", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			promptMsg = "Confirm review?";
		}
		if(Messagebox.show(promptMsg, "Credit Review Corporate", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				//check child temp limit
				if(tempCreditLimitDivDeptCodes.size() > 0)
				{
					String promptMsg2 = "Warning: There are future Temporary Credit limits in Div/Dept. Do you want to clear Div/Dept's future Temporary Limits?";
//					String userId = this.getUserLoginIdAndDomain();
					
					if(Messagebox.show(promptMsg2, "Credit Review Corporate", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
//						this.businessHelper.getAccountBusiness().clearFutureChildCreditLimit(custNo, null, null, userId);
						
						super.creditReview(custNo, null, null);
						Messagebox.show("Raised request for Credit Review.", "Credit Review Corporate", Messagebox.OK, Messagebox.INFORMATION);
						this.back();
					}
				}
				else 
				{
					super.creditReview(custNo, null, null);
					Messagebox.show("Raised request for Credit Review.", "Credit Review Corporate", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}
			}catch (WrongValueException wve){
				throw wve;
			}catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to raise request! Please try again later", "Credit Review Corporate", Messagebox.OK, Messagebox.ERROR);
			}
			
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}