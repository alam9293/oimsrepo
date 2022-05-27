package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
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
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewDivisionWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewDivisionWindow.class);
	private String custNo, acctStatus, code;
	@SuppressWarnings("unchecked")
	public ViewDivisionWindow() throws InterruptedException{
		logger.info("ViewDivisionWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Division", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Edit Division", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = map.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Edit Division", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException{
		if(custNo==null || custNo.trim().length()==0){
			this.back();
		}
		if(acctStatus==null || acctStatus.trim().length()==0){
			this.back();
		}
		if(code==null || code.trim().length()==0){
			this.back();
		}
		Map<String, Object> acctDetails = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.DIVISION_LEVEL, null, code);
		Label codeText = (Label)this.getFellow("code");
		codeText.setValue((String)acctDetails.get("acctCode"));
		Label acctNameText = (Label)this.getFellow("acctName");
		acctNameText.setValue((String)acctDetails.get("acctName"));
		Label nameOnCardText = (Label)this.getFellow("nameOnCard");
		nameOnCardText.setValue((String)acctDetails.get("nameOnCard"));
		Label creditLimit = (Label)this.getFellow("creditLimit");
		creditLimit.setValue(StringUtil.bigDecimalToString((BigDecimal)acctDetails.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT));
		Label creditBalance = (Label)this.getFellow("creditBalance");
		creditBalance.setValue((String)acctDetails.get("creditBalance"));
		Label createdDate = (Label)this.getFellow("createdDate");
		createdDate.setValue((String)acctDetails.get("createdDate"));
		Label custNoLabel = (Label)this.getFellow("custNo");
		custNoLabel.setValue(custNo);
		Label acctStatus = (Label)this.getFellow("acctStatus");
		acctStatus.setValue(NonConfigurableConstants.ACCOUNT_STATUS.get(acctDetails.get("acctStatus")));
		Label divStatus = (Label)this.getFellow("divStatus");
		divStatus.setValue(NonConfigurableConstants.ACCOUNT_STATUS.get(acctDetails.get("childStatus")));
		enableAllBtns();
		if(acctDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		}else if(acctDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED)){
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
		}else if(acctDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		}else if(acctDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED) || 
				acctDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED)){
			if(acctDetails.get("childStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
				((Button)this.getFellow("editBtn")).setDisabled(true);
			}		
			((Button)this.getFellow("creditReviewBtn")).setDisabled(true);
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
			((Button)this.getFellow("terminateBtn")).setDisabled(true);
		}
		Label acctType = (Label)this.getFellow("acctType");
		acctType.setValue((String)acctDetails.get("acctType"));
		if(acctDetails.get("lastUpdatedBy")!=null){
			((Label)this.getFellow("lastUpdatedBy")).setValue((String)acctDetails.get("lastUpdatedBy"));
		}
		if(acctDetails.get("lastUpdatedDate")!=null){
			((Label)this.getFellow("lastUpdatedDate")).setValue((String)acctDetails.get("lastUpdatedDate"));
		}
		if(acctDetails.get("lastUpdatedTime")!=null){
			((Label)this.getFellow("lastUpdatedTime")).setValue((String)acctDetails.get("lastUpdatedTime"));
		}
		if(acctDetails.get("invoiceFormat")!=null){
			Label chargeToBox = (Label)this.getFellow("chargeTo");
			chargeToBox.setValue(NonConfigurableConstants.INVOICE_CHARGE_TO.get(NonConfigurableConstants.INVOICE_CHARGE_TO_SELF));
			Label invoiceFormatBox = (Label)this.getFellow("invoiceFormat");
			invoiceFormatBox.setValue(NonConfigurableConstants.INVOICE_FORMAT.get(acctDetails.get("invoiceFormat")));
			Label invoiceSortingBox = (Label)this.getFellow("invoiceSorting");
			invoiceSortingBox.setValue(NonConfigurableConstants.INVOICE_SORTING.get(acctDetails.get("invoiceSorting")));
			//Govt eInvoice Enhancement
			String govtEInvFlag = NonConfigurableConstants.GOVT_EINV_FLAGS.get(acctDetails.get("govtEInvFlag"));
			((Label)this.getFellow("govtEInvLabel")).setValue(govtEInvFlag);
			MstbMasterTable businessUnitMaster = (MstbMasterTable) acctDetails.get("businessUnit");
			if(businessUnitMaster != null){
				((Label)this.getFellow("businessUnitLabel")).setValue(businessUnitMaster.getMasterCode() + " - " + businessUnitMaster.getMasterValue());
			}
			else
				((Label)this.getFellow("businessUnitLabel")).setValue("");
			
			if(acctDetails.get("pubbsFlag")!= null)
				((Label)this.getFellow("pubbs")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("pubbsFlag")));
			if(acctDetails.get("fiFlag")!= null)
				((Label)this.getFellow("fiFlag")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("fiFlag")));
			
			if(acctDetails.get("recurring")!=null){
				((Label)this.getFellow("recurring")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("recurring")));
			}else{
				((Label)this.getFellow("recurring")).setValue("-");
			}
			if(acctDetails.get("recurringChargeDay")!=null){
				((Label)this.getFellow("recurringChargeDay")).setValue(NonConfigurableConstants.DAYSwithSuffix.get((acctDetails.get("recurringChargeDay")).toString()));
			}else{
				((Label)this.getFellow("recurringChargeDay")).setValue("-");
			}
		}else{
			Label chargeToBox = (Label)this.getFellow("chargeTo");
			chargeToBox.setValue(NonConfigurableConstants.INVOICE_CHARGE_TO.get(NonConfigurableConstants.INVOICE_CHARGE_TO_PARENT));
			Label invoiceFormatBox = (Label)this.getFellow("invoiceFormat");
			invoiceFormatBox.setValue("-");
			Label invoiceSortingBox = (Label)this.getFellow("invoiceSorting");
			invoiceSortingBox.setValue("-");
			Label govtEInvLabel = (Label)this.getFellow("govtEInvLabel");
			govtEInvLabel.setValue("-");
			Label businessUnitLabel = (Label)this.getFellow("businessUnitLabel");
			businessUnitLabel.setValue("-");
			

			if(acctDetails.get("pubbsFlag")!= null)
				((Label)this.getFellow("pubbs")).setValue("-");
			if(acctDetails.get("fiFlag")!= null)
				((Label)this.getFellow("fiFlag")).setValue("-");
			
			if(acctDetails.get("parentRecurring")!=null){
				((Label)this.getFellow("recurring")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("parentRecurring")));
			}else{
				((Label)this.getFellow("recurring")).setValue("-");
			}
			if(acctDetails.get("parentRecurringChargeDay")!=null){
				((Label)this.getFellow("recurringChargeDay")).setValue(NonConfigurableConstants.DAYSwithSuffix.get((acctDetails.get("parentRecurringChargeDay")).toString()));
			}else{
				((Label)this.getFellow("recurringChargeDay")).setValue("-");
			}
		}
		String billing = this.businessHelper.getAccountBusiness().getContactName((Integer)acctDetails.get("mainBilling"));
		((Label)this.getFellow("billingContact")).setValue(billing);
		if(acctDetails.get("mainShipping")!=null){
			String shipping = this.businessHelper.getAccountBusiness().getContactName((Integer)acctDetails.get("mainShipping"));
			((Label)this.getFellow("shippingContact")).setValue(shipping);
		}
		// initing the product subscriptions
		Rows rows = (Rows)this.getFellow("prodSubscriptions");
		rows.getChildren().clear();
		Set<Map<String, String>> prodSubscriptions = this.businessHelper.getAccountBusiness().getProductSubscriptions(custNo, null);
		Set<String> subscribed = (Set<String>)acctDetails.get("productSubscriptions");
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
			rows.appendChild(row);
		}

		if(acctDetails.get("overdueReminder")!=null)
			((Label)this.getFellow("overdueReminder")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("overdueReminder")));
		else
			((Label)this.getFellow("overdueReminder")).setValue("-");
		
		if(acctDetails.get("reminderEmail")!= null)
			((Label)this.getFellow("reminderEmail")).setValue(checkReminderEmail((String)acctDetails.get("reminderEmail")));
		else
			((Label)this.getFellow("reminderEmail")).setValue("-");
		
		if(acctDetails.get("eInvoiceEmail")!= null)
			((Label)this.getFellow("eInvoiceEmail")).setValue(checkPDFEInvoiceEmail((String)acctDetails.get("eInvoiceEmail")));
		else
			((Label)this.getFellow("eInvoiceEmail")).setValue("-");
		
		if(acctDetails.get("eInvoiceEmailFlag")!= null)
			((Label)this.getFellow("eInvoiceEmailFlag")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("eInvoiceEmailFlag")));
		else
			((Label)this.getFellow("eInvoiceEmailFlag")).setValue("-");
		
		if(acctDetails.get("printTaxInvoiceOnlyList")!= null)
			((Label)this.getFellow("printTaxInvOnly")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("printTaxInvoiceOnlyList")));
		else
			((Label)this.getFellow("printTaxInvOnly")).setValue("-");
		
		if(!this.checkUriAccess(Uri.VIEW_STATUS_HISTORY))
			((Button)this.getFellow("viewStatusHistBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.VIEW_CREDIT_REVIEW))
			((Button)this.getFellow("viewCreditLimitHistBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EDIT_DIVISION))
			((Button)this.getFellow("editBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.CREDIT_REVIEW_DIV))
			((Button)this.getFellow("creditReviewBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.SUSPEND_DIV))
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.REACTIVATE_DIV))
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.TERMINATE_DIV))
			((Button)this.getFellow("terminateBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EINVOICE_EMAIL))
			((Button)this.getFellow("eInvoiceEmailBtn")).setDisabled(true);
		
//		if(acctDetails.get("pubbsFlag")!= null)
//			((Label)this.getFellow("pubbs")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("pubbsFlag")));
//		if(acctDetails.get("fiFlag")!= null)
//			((Label)this.getFellow("fiFlag")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(acctDetails.get("fiFlag")));
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	public void edit() throws InterruptedException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		params.put("code", code);
		this.forward(Uri.EDIT_DIVISION, params, this.getParent());
	}
	public void creditReview() throws InterruptedException{
		logger.info("creditReview()");
		this.forward(Uri.CREDIT_REVIEW_DIV);
	}
	public void suspend() throws InterruptedException{
		logger.info("suspend()");
		this.forward(Uri.SUSPEND_DIV);
	}
	public void reactivate() throws InterruptedException{
		logger.info("reactivate()");
		forward(Uri.REACTIVATE_DIV);
	}
	public void terminate() throws InterruptedException{
		logger.info("terminate()");
		forward(Uri.TERMINATE_DIV);
	}
	private void forward(String uri) throws InterruptedException{
		logger.info("forward(String uri)");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("code", code);
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
		super.viewStatusHistory(custNo, null, null, code);
	}
	public void viewCreditLimitHistory() throws InterruptedException{
		logger.info("viewCreditLimitHistory()");
		String acctName = ((Label)this.getFellow("acctName")).getValue();
		super.viewCreditLimitHistory(custNo, null, null, code, acctName);
	}
	public void overdueReminder() throws InterruptedException{
		logger.info("overdueReminder()");
		forward(Uri.OVERDUE_REMINDER_DIV);
	}
	
	public void eInvoiceEmail() throws InterruptedException{
		logger.info("eInvoiceEmail()");
		forward(Uri.EINVOICE_EMAIL_DIV);
	}
}