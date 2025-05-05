package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
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

public class CreditReviewSubPersWindow extends CommonCreditReviewWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreditReviewSubPersWindow.class);
	private String custNo, acctNo;
	@SuppressWarnings("unchecked")
	public CreditReviewSubPersWindow() throws InterruptedException{
		logger.info("CreditReviewSubPersWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// account number
		acctNo = map.get("acctNo");
		if(acctNo==null || acctNo.trim().length()==0){
			Messagebox.show("No account no found!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}else if(acctNo==null || acctNo.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getSubPersAccount(acctNo);
		((Label)this.getFellow("acctName")).setValue((String)details.get("acctName"));
		((Label)this.getFellow("creditLimit")).setValue(StringUtil.bigDecimalToString((BigDecimal)details.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT));
		((Label)this.getFellow("creditBalance")).setValue((String)details.get("creditBalance"));
		((Datebox)this.getFellow("fromDate")).setValue(new Date());
	}
	public void creditReview() throws InterruptedException{
		if(this.businessHelper.getAccountBusiness().hasPendingPermanentCreditReview(custNo)){
			Messagebox.show("There is existing pending request for main applicant!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		BigDecimal subCreditLimit = ((Decimalbox)this.getFellow("newCreditLimit")).getValue();
//		if(this.businessHelper.getAccountBusiness().checkParentCreditLimit(custNo, null, subCreditLimit)){
//			Messagebox.show("Credit Limit is greater than Applicant!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.ERROR);
//			return;
//		}
		String promptMsg;
		BigDecimal creditLimit = new BigDecimal(((Label)this.getFellow("creditLimit")).getValue().replaceAll(",", ""));
		if(creditLimit.equals(subCreditLimit)){
			Messagebox.show("No change in credit limit!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		String reviewType = (String)((Listbox)this.getFellow("reviewTypeList")).getSelectedItem().getValue();
		if(reviewType.equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)){
			Date toDate = ((Datebox)this.getFellow("toDate")).getValue();
			Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
			
			if(toDate==null){
				Messagebox.show("Effective Date To cannot be empty for temporary credit review!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}else{
				if(fromDate.after(toDate)){
					Messagebox.show("Effective Date To must be equal to or later than Effective Date From!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				if(!DateUtil.isToday(fromDate) && fromDate.before(DateUtil.getCurrentDate())){
					Messagebox.show("Effective Date From must be equal to or later than today!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
			if(creditLimit.compareTo(subCreditLimit)>0){
				Messagebox.show("Temporary credit limit must be greater than permanent credit limit!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			
			//#39 check parent Credit Limit
			if(this.businessHelper.getAccountBusiness().checkParentCreditLimitRange(custNo, null, fromDate, toDate, subCreditLimit)){
				Messagebox.show("Temporary Credit Limit Not Within Main Account's Limit", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			
			if(this.businessHelper.getAccountBusiness().hasTempCreditLimit(custNo, acctNo)){
				promptMsg = "There is existing temporary credit limit. Update?";
			}else{
				promptMsg = "Confirm update credit limit?";
			}
		}else{
			Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
			if(this.businessHelper.getAccountBusiness().hasPermCreditLimit(custNo, acctNo, fromDate)){
				Messagebox.show("There is existing permanent credit limit on that date!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if(!DateUtil.isToday(fromDate) && fromDate.before(DateUtil.getCurrentDate())){
				Messagebox.show("Effective Date From must be later than today!", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			promptMsg = "Confirm update credit limit?";
		}
		if(Messagebox.show(promptMsg, "Credit Review Sub Applicant", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				super.creditReview(custNo, acctNo);
				Messagebox.show("Sub Applicant Credit Limit updated", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}catch (WrongValueException wve){
				throw wve;
			}catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to raise request! Please try again later", "Credit Review Sub Applicant", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}