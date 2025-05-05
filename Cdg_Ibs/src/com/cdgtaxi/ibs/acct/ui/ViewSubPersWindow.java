package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewSubPersWindow extends AddSubPersWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewSubPersWindow.class);
	private String custNo, acctStatus, acctNo;
	
	@SuppressWarnings("unchecked")
	public ViewSubPersWindow() throws InterruptedException{
		logger.info("ViewSubPersWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// account number
		acctNo = map.get("acctNo");
		if(acctNo==null || acctNo.trim().length()==0){
			Messagebox.show("No account no found!", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException{
		logger.info("init()");
		// checking
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}else if(acctStatus==null || acctStatus.trim().length()==0){
			this.back();
			return;
		}else if(acctNo==null || acctNo.trim().length()==0){
			this.back();
			return;
		}
		// initing fields
		Map<String, Object> subDetails = this.businessHelper.getAccountBusiness().getSubPersAccount(acctNo);
		((Label)this.getFellow("sal")).setValue(ConfigurableConstants.getSalutations().get(subDetails.get("salCode")));
		((Label)this.getFellow("race")).setValue(ConfigurableConstants.getRace().get(subDetails.get("race")));
		((Label)this.getFellow("acctName")).setValue((String)subDetails.get("acctName"));
		((Label)this.getFellow("nameOnCard")).setValue((String)subDetails.get("nameOnCard"));
		((Label)this.getFellow("nric")).setValue(StringUtil.maskNric((String)subDetails.get("nric")));
		((Label)this.getFellow("birthdate")).setValue(DateUtil.convertDateToStr((Date)subDetails.get("birthdate"), DateUtil.GLOBAL_DATE_FORMAT));
		((Label)this.getFellow("relation")).setValue(ConfigurableConstants.getRelationships().get(subDetails.get("relationCode")));
		((Label)this.getFellow("creditLimit")).setValue(StringUtil.bigDecimalToString((BigDecimal)subDetails.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT));
		((Label)this.getFellow("creditBalance")).setValue((String)subDetails.get("creditBalance"));
		((Label)this.getFellow("tel")).setValue((String)subDetails.get("tel"));
		((Label)this.getFellow("email")).setValue((String)subDetails.get("email"));
		((Label)this.getFellow("mobile")).setValue((String)subDetails.get("mobile"));
		((Label)this.getFellow("office")).setValue((String)subDetails.get("office"));
		if(subDetails.get("blkNo")!=null){
			((Label)this.getFellow("blkNo")).setValue((String)subDetails.get("blkNo"));
		}else{
			((Label)this.getFellow("blkNo")).setValue("-");
		}
		if(subDetails.get("unitNo")!=null){
			((Label)this.getFellow("unitNo")).setValue((String)subDetails.get("unitNo"));
		}else{
			((Label)this.getFellow("unitNo")).setValue("-");
		}
		((Label)this.getFellow("street")).setValue((String)subDetails.get("street"));
		if(subDetails.get("building")!=null){
			((Label)this.getFellow("building")).setValue((String)subDetails.get("building"));
		}else{
			((Label)this.getFellow("building")).setValue("-");
		}
		if(subDetails.get("area")!=null){
			((Label)this.getFellow("area")).setValue((String)subDetails.get("area"));
		}else{
			((Label)this.getFellow("area")).setValue("-");
		}
		((Label)this.getFellow("country")).setValue(ConfigurableConstants.getCountries().get(subDetails.get("countryCode")));
		if(subDetails.get("city")!=null){
			((Label)this.getFellow("city")).setValue((String)subDetails.get("city"));
		}else{
			((Label)this.getFellow("city")).setValue("-");
		}
		if(subDetails.get("state")!=null){
			((Label)this.getFellow("state")).setValue((String)subDetails.get("state"));
		}else{
			((Label)this.getFellow("state")).setValue("-");
		}
		((Label)this.getFellow("postal")).setValue((String)subDetails.get("postal"));
		Label subStatus = (Label)this.getFellow("subStatus");
		subStatus.setValue(NonConfigurableConstants.ACCOUNT_STATUS.get(subDetails.get("childStatus")));
		enableAllBtns();
		if(subDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		}else if(subDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED)){
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
		}else if(subDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		}else if(subDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED) || 
				subDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED)){
			((Button)this.getFellow("editBtn")).setDisabled(true);
			((Button)this.getFellow("creditReviewBtn")).setDisabled(true);
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
			((Button)this.getFellow("terminateBtn")).setDisabled(true);
		}
		// initing the product subscriptions
		Rows rows = (Rows)this.getFellow("prodSubscriptions");
		while(rows.getChildren().size()!=1){
			rows.removeChild(rows.getFirstChild());
		}
		Set<Map<String, String>> prodSubscriptions = this.businessHelper.getAccountBusiness().getProductSubscriptions(custNo, null);
		Set<String> subscribed = (Set<String>)subDetails.get("productSubscriptions");
		for(Map<String, String> prodSubscription : prodSubscriptions){
			Row row = new Row();
			row.appendChild(new Label(prodSubscription.get("prodType")==null ? "-" : prodSubscription.get("prodType")));
			row.appendChild(new Label(prodSubscription.get("prodDiscount")==null ? "-" : prodSubscription.get("prodDiscount")));
			row.appendChild(new Label(prodSubscription.get("rewards")==null ? "-" : prodSubscription.get("rewards")));
			row.appendChild(new Label(prodSubscription.get("subscribeFee")==null ? "-" : prodSubscription.get("subscribeFee")));
			row.appendChild(new Label(prodSubscription.get("issuanceFee")==null ? "-" : prodSubscription.get("issuanceFee")));
			if(subscribed.contains(prodSubscription.get("prodTypeId"))){
				row.appendChild(new Label(NonConfigurableConstants.BOOLEAN_YN.get(NonConfigurableConstants.BOOLEAN_YN_YES)));
			}else{
				row.appendChild(new Label(NonConfigurableConstants.BOOLEAN_YN.get(NonConfigurableConstants.BOOLEAN_YN_NO)));
			}
			rows.insertBefore(row, rows.getLastChild());
		}

		if(subDetails.get("overdueReminder")!=null)
			((Label)this.getFellow("overdueReminder")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(subDetails.get("overdueReminder")));
		else
			((Label)this.getFellow("overdueReminder")).setValue("-");
		
		if(subDetails.get("reminderEmail")!= null)
			((Label)this.getFellow("reminderEmail")).setValue(checkReminderEmail((String)subDetails.get("reminderEmail")));
		else
			((Label)this.getFellow("reminderEmail")).setValue("-");
		
		if(subDetails.get("eInvoiceEmail")!= null)
			((Label)this.getFellow("eInvoiceEmail")).setValue(checkPDFEInvoiceEmail((String)subDetails.get("eInvoiceEmail")));
		else
			((Label)this.getFellow("eInvoiceEmail")).setValue("-");
		
		if(subDetails.get("eInvoiceEmailFlag")!= null)
			((Label)this.getFellow("eInvoiceEmailFlag")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(subDetails.get("eInvoiceEmailFlag")));
		else
			((Label)this.getFellow("eInvoiceEmailFlag")).setValue("-");
		
		if(subDetails.get("recurring")!=null){
			((Label)this.getFellow("recurring")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(subDetails.get("recurring")));
		}else{
			((Label)this.getFellow("recurring")).setValue("-");
		}
		if(subDetails.get("recurringChargeDay")!=null){
			((Label)this.getFellow("recurringChargeDay")).setValue(NonConfigurableConstants.DAYSwithSuffix.get((subDetails.get("recurringChargeDay")).toString()));
		}else{
			((Label)this.getFellow("recurringChargeDay")).setValue("-");
		}
		if(!this.checkUriAccess(Uri.VIEW_STATUS_HISTORY))
			((Button)this.getFellow("viewStatusHistBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.VIEW_CREDIT_REVIEW))
			((Button)this.getFellow("viewCreditLimitHistBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.CREDIT_REVIEW_SUB_PERS))
			((Button)this.getFellow("creditReviewBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EDIT_SUB_PERS))
			((Button)this.getFellow("editBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.SUSPEND_SUB_PERS))
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.REACTIVATE_SUB_PERS))
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.TERMINATE_SUB_PERS))
			((Button)this.getFellow("terminateBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EINVOICE_EMAIL))
			((Button)this.getFellow("eInvoiceEmailBtn")).setDisabled(true);
	}
	public void edit() throws InterruptedException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		params.put("acctNo", acctNo);
		this.forward(Uri.EDIT_SUB_PERS, params, this.getParent());
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	public void creditReview() throws InterruptedException{
		logger.info("creditReview()");
		this.forward(Uri.CREDIT_REVIEW_SUB_PERS);
	}
	public void suspend() throws InterruptedException{
		logger.info("suspend()");
		this.forward(Uri.SUSPEND_SUB_PERS);
	}
	public void reactivate() throws InterruptedException{
		logger.info("reactivate()");
		this.forward(Uri.REACTIVATE_SUB_PERS);
	}
	public void terminate() throws InterruptedException{
		logger.info("terminate()");
		this.forward(Uri.TERMINATE_SUB_PERS);
	}
	private void forward(String uri) throws InterruptedException{
		logger.info("forward(String uri)");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		params.put("acctNo", acctNo);
		this.forward(uri, params, this.getParent());
	}
	private void enableAllBtns(){
		((Button)this.getFellow("editBtn")).setDisabled(false);
		((Button)this.getFellow("creditReviewBtn")).setDisabled(false);
		((Button)this.getFellow("suspendBtn")).setDisabled(false);
		((Button)this.getFellow("reactivateBtn")).setDisabled(false);
		((Button)this.getFellow("terminateBtn")).setDisabled(false);
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		super.viewStatusHistory(custNo, acctNo, null, null);
	}
	public void viewCreditLimitHistory() throws InterruptedException{
		logger.info("viewCreditLimitHistory()");
		String acctName = ((Label)this.getFellow("acctName")).getValue();
		super.viewCreditLimitHistory(custNo, acctNo, null, null, acctName);
	}
	public void overdueReminder() throws InterruptedException{
		logger.info("overdueReminder()");
		forward(Uri.OVERDUE_REMINDER_SUB_PERS);
	}
	
	public void eInvoiceEmail() throws InterruptedException{
		logger.info("eInvoiceEmail()");
		forward(Uri.EINVOICE_EMAIL_SUB_PERS);
	}
}