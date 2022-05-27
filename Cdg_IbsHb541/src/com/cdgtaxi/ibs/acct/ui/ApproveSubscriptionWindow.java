package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ApproveSubscriptionWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApproveSubscriptionWindow.class);
	private Integer requestId;
	@SuppressWarnings("unchecked")
	public ApproveSubscriptionWindow() throws InterruptedException{
		logger.info("ApproveSubscriptionWindow()");
		//request number
		Map<String, Integer> map = Executions.getCurrent().getArg(); 
		requestId = map.get("requestId");
		if(requestId==null){
			Messagebox.show("Request ID not found!", "Approve Product Subscription Request", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(requestId==null){
			this.back();
			return;
		}
		Map<String, String> subscriptionDetails = this.businessHelper.getAccountBusiness().getPendingSubscriptionRequest(requestId);
		((Label)this.getFellow("acctNo")).setValue(subscriptionDetails.get("acctNo"));
		((Label)this.getFellow("acctName")).setValue(subscriptionDetails.get("acctName"));
		((Label)this.getFellow("divCode")).setValue(subscriptionDetails.get("divCode"));
		((Label)this.getFellow("divName")).setValue(subscriptionDetails.get("divName"));
		((Label)this.getFellow("deptCode")).setValue(subscriptionDetails.get("deptCode"));
		((Label)this.getFellow("deptName")).setValue(subscriptionDetails.get("deptName"));
		((Label)this.getFellow("prodType")).setValue(subscriptionDetails.get("prodType"));
		((Label)this.getFellow("prodDisc")).setValue(subscriptionDetails.get("prodDisc"));
		((Label)this.getFellow("loyaltyPlan")).setValue(subscriptionDetails.get("loyaltyPlan"));
		((Label)this.getFellow("subscPlan")).setValue(subscriptionDetails.get("subscPlan"));
		((Label)this.getFellow("issuancePlan")).setValue(subscriptionDetails.get("issuancePlan"));
		((Label)this.getFellow("action")).setValue(subscriptionDetails.get("action"));
		
		//Check is it SubAppln, then div/dept label and value.
		if(null != subscriptionDetails.get("subAppln"))
		{
			if(subscriptionDetails.get("subAppln").equals("subAppln"))
			{
				((Label)this.getFellow("divisionCodeLabel")).setValue("Sub Applicant Code");
				((Label)this.getFellow("divisionNameLabel")).setValue("Sub Applicant Name");
				((Label)this.getFellow("departmentCodeLabel")).setValue("");
				((Label)this.getFellow("departmentNameLabel")).setValue("");
				((Label)this.getFellow("deptCode")).setValue("");
				((Label)this.getFellow("deptName")).setValue("");
			}
		}
		
		//Set Red
		((Label)this.getFellow("action")).setStyle("color:#FF0000");
		
		if(null != subscriptionDetails.get("prodDiscRed"))
		{
			if(subscriptionDetails.get("prodDiscRed").trim().equals("red"))
				((Label)this.getFellow("prodDisc")).setStyle("color:#FF0000");
		}
		if(null != subscriptionDetails.get("loyaltyPlanRed"))
		{
			if(subscriptionDetails.get("loyaltyPlanRed").trim().equals("red"))
				((Label)this.getFellow("loyaltyPlan")).setStyle("color:#FF0000");
		}
		if(null != subscriptionDetails.get("subscPlanRed"))
		{
			if(subscriptionDetails.get("subscPlanRed").trim().equals("red"))
				((Label)this.getFellow("subscPlan")).setStyle("color:#FF0000");
		}
		if(null != subscriptionDetails.get("issuancePlanRed"))
		{
			if(subscriptionDetails.get("issuancePlanRed").trim().equals("red"))
				((Label)this.getFellow("issuancePlan")).setStyle("color:#FF0000");
		}
		
		((Label)this.getFellow("requester")).setValue(subscriptionDetails.get("requester"));
		((Label)this.getFellow("reqDate")).setValue(subscriptionDetails.get("reqDate"));
	}
	@Override
	public void refresh() throws InterruptedException {
	}
	public void approve() throws WrongValueException, InterruptedException{
		if(Messagebox.show("Confirm Approve?", "Approve Product Subscription Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			String remarks = ((Textbox)this.getFellow("remarks")).getValue();
			List<Integer> requestIds = new ArrayList<Integer>();
			requestIds.add(requestId);
			try {
				this.businessHelper.getAccountBusiness().approveSubscriptionRequest(requestIds, remarks);
				Messagebox.show("Subscription Approved", "Approve Product Subscription Request", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to approve Product Subscription request! Please try again later", "Approve Product Subscription Request", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	public void reject() throws InterruptedException{
		if(Messagebox.show("Confirm Reject?", "Approve Credit Review Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			String remarks = ((Textbox)this.getFellow("remarks")).getValue();
			if(remarks!=null && remarks.length()!=0){
				List<Integer> requestIds = new ArrayList<Integer>();
				requestIds.add(requestId);
				this.businessHelper.getAccountBusiness().rejectSubscriptionRequest(requestIds, remarks);
				Messagebox.show("Subscription Rejected", "Approve Product Subscription Request", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}else{
				Messagebox.show("Please key in rejection remarks!", "Approve Product Subscription Request", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			
		}
	}
}