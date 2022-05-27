package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;

public abstract class CommonCreditReviewWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private List<Listitem> reviewTypes = ComponentUtil.convertToListitems(NonConfigurableConstants.REVIEW_TYPES, true);
	public List<Listitem> getReviewTypes(){
		return this.reviewTypes;
	}
	protected void creditReview(String custNo, String parentCode, String code) throws WrongValueException, Exception{
		Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
		if(fromDate==null || DateUtil.isToday(fromDate)){
			fromDate = DateUtil.getCurrentTimestamp();
		}
		Date toDate = ((Datebox)this.getFellow("toDate")).getValue();
		if(toDate!=null){
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.setTime(toDate);
			toDate = DateUtil.convertTo2359Hours(tempCalendar);
		}
		BigDecimal newCreditLimit = ((Decimalbox)this.getFellow("newCreditLimit")).getValue();
		String reviewType = (String)((Listbox)this.getFellow("reviewTypeList")).getSelectedItem().getValue();
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().creditReviewAcct(custNo, parentCode, code, newCreditLimit, reviewType, fromDate, toDate, remarks, userId);
	}
	protected void validateForm(){
		((Textbox)this.getFellow("remarks")).getValue();
	}
	protected void creditReview(String custNo, String subAcctNo) throws Exception{
		Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
		if(fromDate==null || DateUtil.isToday(fromDate)){
			fromDate = DateUtil.getCurrentTimestamp();
		}
		Date toDate = ((Datebox)this.getFellow("toDate")).getValue();
		if(toDate!=null){
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.setTime(toDate);
			toDate = DateUtil.convertTo2359Hours(tempCalendar);
		}
		BigDecimal newCreditLimit = ((Decimalbox)this.getFellow("newCreditLimit")).getValue();
		String reviewType = (String)((Listbox)this.getFellow("reviewTypeList")).getSelectedItem().getValue();
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().creditReviewAcct(custNo, subAcctNo, newCreditLimit, reviewType, fromDate, toDate, remarks, userId);
	}
	public void updateEffectiveDate(Listitem selectedItem){
		if(selectedItem.getValue().equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT)){
			Datebox toDate = ((Datebox)this.getFellow("toDate"));
			Constraint tempConstraint = toDate.getConstraint();
			Constraint nullConstraint = null;
			toDate.setConstraint(nullConstraint);
			toDate.setValue(null);
			Label toDateLabel = ((Label)this.getFellow("toDateLabel"));
			toDateLabel.setClass("fieldLabel");
			toDate.setConstraint(tempConstraint);
			toDate.setDisabled(true);
		}else{
			((Datebox)this.getFellow("toDate")).setDisabled(false);
			((Datebox)this.getFellow("toDate")).setValue(new Date());
			Label toDateLabel = ((Label)this.getFellow("toDateLabel"));
			toDateLabel.setClass("fieldLabel required");
		}
	}
}