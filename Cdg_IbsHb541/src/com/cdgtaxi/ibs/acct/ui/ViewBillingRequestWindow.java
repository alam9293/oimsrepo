package com.cdgtaxi.ibs.acct.ui;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;

public class ViewBillingRequestWindow extends CommonInnerAcctWindow {
	private static Logger logger = Logger.getLogger(SearchBillingRequestWindow.class);
	private static final long serialVersionUID = 1L;
	private Integer requestId;
	@SuppressWarnings("unchecked")
	public ViewBillingRequestWindow() throws InterruptedException{
		logger.info("ViewBillingRequestWindow()");
		//request number
		Map<String, Integer> map = Executions.getCurrent().getArg();
		requestId = map.get("requestId");
		if(requestId==null){
			Messagebox.show("Request ID not found!", "View Billing Request", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(requestId==null){
			this.back();
			return;
		}
		logger.info("request id = " + requestId);
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
			ZScript showInfo = ZScript.parseContent("viewBillingRequestWindow.displayVolumeDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			volDiscImage.addEventHandler("onClick", pdEvent);
			row.appendChild(volDiscImage);
			row.appendChild(new Label(billingDetail.get("adminFee")));
			// adding [?] to row
			Image adminFeeImage = new Image("/images/question.png");
			adminFeeImage.setStyle("cursor: help");
			showInfo = ZScript.parseContent("viewBillingRequestWindow.displayAdminFee()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			adminFeeImage.addEventHandler("onClick", pdEvent);
			row.appendChild(adminFeeImage);
			row.appendChild(new Label(billingDetail.get("effectiveDate")));
			row.appendChild(new Label(billingDetail.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(billingDetail.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED).equals(billingDetail.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED).equals(billingDetail.get("status"))){
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
			ZScript showInfo = ZScript.parseContent("viewBillingRequestWindow.displayCreditTerm()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			creditTermImage.addEventHandler("onClick", pdEvent);
			row.appendChild(creditTermImage);
			row.appendChild(new Label(creditTerm.get("effectiveDate")));
			row.appendChild(new Label(creditTerm.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(creditTerm.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED).equals(creditTerm.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED).equals(creditTerm.get("status"))){
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
			ZScript showInfo = ZScript.parseContent("viewBillingRequestWindow.displayEarlyDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			earlyPymtImage.addEventHandler("onClick", pdEvent);
			row.appendChild(earlyPymtImage);
			row.appendChild(new Label(earlyPymt.get("effectiveDate")));
			row.appendChild(new Label(earlyPymt.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(earlyPymt.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED).equals(earlyPymt.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED).equals(earlyPymt.get("status"))){
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
			ZScript showInfo = ZScript.parseContent("viewBillingRequestWindow.displayLateDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			latePymtImage.addEventHandler("onClick", pdEvent);
			row.appendChild(latePymtImage);
			row.appendChild(new Label(latePymt.get("effectiveDate")));
			row.appendChild(new Label(latePymt.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(latePymt.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED).equals(latePymt.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED).equals(latePymt.get("status"))){
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
			ZScript showInfo = ZScript.parseContent("viewBillingRequestWindow.displayPromotion()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			latePymtImage.addEventHandler("onClick", pdEvent);
			row.appendChild(latePymtImage);
			row.appendChild(new Label(promotion.get("effectiveDateFrom")));
			row.appendChild(new Label(promotion.get("effectiveDateTo")));
			row.appendChild(new Label(promotion.get("status")));
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING).equals(promotion.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED).equals(promotion.get("status"))
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED).equals(promotion.get("status"))){
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
		((Label)this.getFellow("reqRemarks")).setValue(requestDetails.get("reqRemarks"));
		((Label)this.getFellow("reqStatus")).setValue(requestDetails.get("reqStatus"));
		Grid approveDetails = (Grid)this.getFellow("approveDetails");
		if(requestDetails.get("approver")!=null){
			approveDetails.setVisible(true);
			((Label)this.getFellow("approver")).setValue(requestDetails.get("approver"));
			((Label)this.getFellow("approveDate")).setValue(requestDetails.get("approveDate"));
			((Label)this.getFellow("approveRemarks")).setValue(requestDetails.get("approveRemarks"));
		}else{
			approveDetails.setVisible(false);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}