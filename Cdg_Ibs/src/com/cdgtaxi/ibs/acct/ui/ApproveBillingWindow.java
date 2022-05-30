/**
 * Approving of billing information are able to proceed even effective date are of current date
 * The system will insert the current date less the time into the database.
 * However, if bill gen has already taken place, it might be incorrect.
 * E.g. User raise request on 25th May 2009 to change volume discount and take effect on 26th May.
 * Bill Gen starts on 26th May 2009 0200 (2am) without approving the billing request.
 * Approver approves the request on 26th May 2pm.
 * All volume discount calculated in the bill gen for the account, will be incorrect.
 * However, to trace for this, admin can check the approval time.
 */
package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;

public class ApproveBillingWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApproveBillingWindow.class);
	private Integer requestId;
	private boolean approvable = true;
	@SuppressWarnings("unchecked")
	public ApproveBillingWindow() throws InterruptedException{
		logger.info("ApproveBillingWindow()");
		//request number
		Map<String, Integer> map = Executions.getCurrent().getArg();
		requestId = map.get("requestId");
		if(requestId==null){
			Messagebox.show("Request ID not found!", "Approve Billing Request", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(requestId==null){
			this.back();
			return;
		}
		Map<String, List<Map<String, String>>> billing = this.businessHelper.getAccountBusiness().getBillingChangeRequest(requestId);
		List<Map<String, String>> billingDetails = billing.get("billingDetails");
		Rows rows = (Rows)this.getFellow("billingDetails");
		for(Map<String, String> billingDetail : billingDetails){
			if(billingDetail.get("event")!=null && billingDetail.get("event").equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
				continue;
			}
			Row row = new Row();
			row.appendChild(new Label((rows.getChildren().size()+1)+""));
			row.appendChild(new Label(billingDetail.get("billingCycle")));
			row.appendChild(new Label(billingDetail.get("volumeDiscount")));
			// adding [?] to row
			Image volDiscImage = new Image("/images/question.png");
			volDiscImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("approveBillingWindow.displayVolumeDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			volDiscImage.addEventHandler("onClick", pdEvent);
			row.appendChild(volDiscImage);
			row.appendChild(new Label(billingDetail.get("adminFee")));
			// adding [?] to row
			Image adminFeeImage = new Image("/images/question.png");
			adminFeeImage.setStyle("cursor: help");
			showInfo = ZScript.parseContent("approveBillingWindow.displayAdminFee()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			adminFeeImage.addEventHandler("onClick", pdEvent);
			row.appendChild(adminFeeImage);
			row.appendChild(new Label(billingDetail.get("effectiveDate")));
			row.appendChild(new Label(billingDetail.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(billingDetail.get("status"))){
				Date effectiveDate = DateUtil.convertStrToDate(billingDetail.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
				if(effectiveDate.before(DateUtil.getCurrentDate()) && !DateUtil.isToday(effectiveDate)){
//					Button approveBtn = (Button)this.getFellow("approveBtn");
//					approveBtn.setDisabled(true);
//					approveBtn.setTooltiptext("One of the effective dates is earlier than today");
					approvable = false;
				}
				for(Object child : row.getChildren()){
					if(child instanceof Label){
						((Label)child).setStyle("color:#FF0000");
					}
				}
			}
			rows.appendChild(row);
		}
		List<Map<String, String>> creditTerms = billing.get("creditTerms");
		rows = (Rows)this.getFellow("creditTerms");
		for(Map<String, String> creditTerm : creditTerms){
			if(creditTerm.get("event")!=null && creditTerm.get("event").equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
				continue;
			}
			Row row = new Row();
			row.appendChild(new Label((rows.getChildren().size()+1)+""));
			row.appendChild(new Label(creditTerm.get("creditTerm")));
			// adding [?] to row
			Image creditTermImage = new Image("/images/question.png");
			creditTermImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("approveBillingWindow.displayCreditTerm()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			creditTermImage.addEventHandler("onClick", pdEvent);
			row.appendChild(creditTermImage);
			row.appendChild(new Label(creditTerm.get("effectiveDate")));
			row.appendChild(new Label(creditTerm.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(creditTerm.get("status"))){
				Date effectiveDate = DateUtil.convertStrToDate(creditTerm.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
				if(effectiveDate.before(DateUtil.getCurrentDate()) && !DateUtil.isToday(effectiveDate)){
//					Button approveBtn = (Button)this.getFellow("approveBtn");
//					approveBtn.setDisabled(true);
//					approveBtn.setTooltiptext("One of the effective dates is earlier than today");
					approvable = false;
				}
				for(Object child : row.getChildren()){
					if(child instanceof Label){
						((Label)child).setStyle("color:#FF0000");
					}
				}
			}
			rows.appendChild(row);
		}
		List<Map<String, String>> earlyPymts = billing.get("earlyPymts");
		rows = (Rows)this.getFellow("earlyPymts");
		for(Map<String, String> earlyPymt : earlyPymts){
			if(earlyPymt.get("event")!=null && earlyPymt.get("event").equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
				continue;
			}
			Row row = new Row();
			row.appendChild(new Label((rows.getChildren().size()+1)+""));
			row.appendChild(new Label(earlyPymt.get("earlyPymt")));
			// adding [?] to row
			Image earlyPymtImage = new Image("/images/question.png");
			earlyPymtImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("approveBillingWindow.displayEarlyDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			earlyPymtImage.addEventHandler("onClick", pdEvent);
			row.appendChild(earlyPymtImage);
			row.appendChild(new Label(earlyPymt.get("effectiveDate")));
			row.appendChild(new Label(earlyPymt.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(earlyPymt.get("status"))){
				Date effectiveDate = DateUtil.convertStrToDate(earlyPymt.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
				if(effectiveDate.before(DateUtil.getCurrentDate()) && !DateUtil.isToday(effectiveDate)){
//					Button approveBtn = (Button)this.getFellow("approveBtn");
//					approveBtn.setDisabled(true);
//					approveBtn.setTooltiptext("One of the effective dates is earlier than today");
					approvable = false;
				}
				for(Object child : row.getChildren()){
					if(child instanceof Label){
						((Label)child).setStyle("color:#FF0000");
					}
				}
			}
			rows.appendChild(row);
		}
		List<Map<String, String>> latePymts = billing.get("latePymts");
		rows = (Rows)this.getFellow("latePymts");
		for(Map<String, String> latePymt : latePymts){
			if(latePymt.get("event")!=null && latePymt.get("event").equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
				continue;
			}
			Row row = new Row();
			row.appendChild(new Label((rows.getChildren().size()+1)+""));
			row.appendChild(new Label(latePymt.get("latePymt")));
			// adding [?] to row
			Image latePymtImage = new Image("/images/question.png");
			latePymtImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("approveBillingWindow.displayLateDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			latePymtImage.addEventHandler("onClick", pdEvent);
			row.appendChild(latePymtImage);
			row.appendChild(new Label(latePymt.get("effectiveDate")));
			row.appendChild(new Label(latePymt.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(latePymt.get("status"))){
				Date effectiveDate = DateUtil.convertStrToDate(latePymt.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
				if(effectiveDate.before(DateUtil.getCurrentDate()) && !DateUtil.isToday(effectiveDate)){
//					Button approveBtn = (Button)this.getFellow("approveBtn");
//					approveBtn.setDisabled(true);
//					approveBtn.setTooltiptext("One of the effective dates is earlier than today");
					approvable = false;
				}
				for(Object child : row.getChildren()){
					if(child instanceof Label){
						((Label)child).setStyle("color:#FF0000");
					}
				}
			}
			rows.appendChild(row);
		}
		List<Map<String, String>> promotions = billing.get("promotions");
		rows = (Rows)this.getFellow("promotions");
		for(Map<String, String> promotion : promotions){
			if(promotion.get("event")!=null && promotion.get("event").equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
				continue;
			}
			Row row = new Row();
			row.appendChild(new Label((rows.getChildren().size()+1)+""));
			row.appendChild(new Label(promotion.get("promotion")));
			// adding [?] to row
			Image latePymtImage = new Image("/images/question.png");
			latePymtImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("approveBillingWindow.displayPromotion()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			latePymtImage.addEventHandler("onClick", pdEvent);
			row.appendChild(latePymtImage);
			row.appendChild(new Label(promotion.get("effectiveDateFrom")));
			row.appendChild(new Label(promotion.get("effectiveDateTo")));
			row.appendChild(new Label(promotion.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(promotion.get("status"))){
				approvable = Boolean.parseBoolean(promotion.get("approvable"));
				logger.info("test = " + promotion.get("approvable"));
				logger.info("test = " + approvable);
				if(promotion.get("effectiveDateTo")!=null && promotion.get("effectiveDateTo").length()!=0){
					Date effectiveDateTo = DateUtil.convertStrToDate(promotion.get("effectiveDateTo"), DateUtil.GLOBAL_DATE_FORMAT);
					if(effectiveDateTo.before(DateUtil.getCurrentDate()) && !DateUtil.isToday(effectiveDateTo)){
						approvable = false;
					}
				}
				for(Object child : row.getChildren()){
					if(child instanceof Label){
						((Label)child).setStyle("color:#FF0000");
					}
				}
			}
			rows.appendChild(row);
		}
		Map<String, String> requestDetails = billing.get("requestDetails").get(0);
		((Label)this.getFellow("requester")).setValue(requestDetails.get("requester"));
		((Label)this.getFellow("reqDate")).setValue(requestDetails.get("reqDate"));
	}
	public void approve() throws WrongValueException, InterruptedException{
		if(approvable && Messagebox.show("Confirm Approve?", "Approve Billing Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			String remarks = ((Textbox)this.getFellow("remarks")).getValue();
			this.businessHelper.getAccountBusiness().approveBillingChangeRequest(requestId, remarks, this.getUserLoginIdAndDomain());
			Messagebox.show("Approved", "Approve Billing Request", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}else if(!approvable){
			Messagebox.show("Unable to approve. One of the effective dates is earlier than today.", "Approve Billing Request", Messagebox.OK, Messagebox.ERROR);
			
		}
	}
	public void reject() throws InterruptedException{
		if(Messagebox.show("Confirm Reject?", "Approve Billing Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			String remarks = ((Textbox)this.getFellow("remarks")).getValue();
			if(remarks!=null && remarks.length()!=0){
				this.businessHelper.getAccountBusiness().rejectBillingChangeRequest(requestId, remarks, this.getUserLoginIdAndDomain());
				Messagebox.show("Rejected", "Approve Billing Request", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}else{
				Messagebox.show("Please key in rejection remarks!", "Approve Billing Request", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}