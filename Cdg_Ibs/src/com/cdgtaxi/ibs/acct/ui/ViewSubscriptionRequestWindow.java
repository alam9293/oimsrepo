package com.cdgtaxi.ibs.acct.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ViewSubscriptionRequestWindow extends CommonWindow {
	private static Logger logger = Logger.getLogger(ViewSubscriptionRequestWindow.class);
	private static final long serialVersionUID = 1L;
	private Integer requestId;
	@SuppressWarnings("unchecked")
	public ViewSubscriptionRequestWindow() throws InterruptedException{
		logger.info("ViewSubscriptionRequestWindow()");
		//request number
		Map<String, Integer> map = Executions.getCurrent().getArg();
		requestId = map.get("requestId");
		if(requestId==null){
			Messagebox.show("Request ID not found!", "View Subscription Request", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(requestId==null){
			return;
		}
		Map<String, String> request = this.businessHelper.getAccountBusiness().getPendingSubscriptionRequest(requestId);
		((Label)this.getFellow("acctNo")).setValue(request.get("acctNo"));
		((Label)this.getFellow("acctName")).setValue(request.get("acctName"));
		((Label)this.getFellow("divCode")).setValue(request.get("divCode"));
		((Label)this.getFellow("divName")).setValue(request.get("divName"));
		((Label)this.getFellow("deptCode")).setValue(request.get("deptCode"));
		((Label)this.getFellow("deptName")).setValue(request.get("deptName"));
		((Label)this.getFellow("prodType")).setValue(request.get("prodType"));
		((Label)this.getFellow("prodDisc")).setValue(request.get("prodDisc"));
		((Label)this.getFellow("loyaltyPlan")).setValue(request.get("loyaltyPlan"));
		((Label)this.getFellow("subscPlan")).setValue(request.get("subscPlan"));
		((Label)this.getFellow("issuancePlan")).setValue(request.get("issuancePlan"));
		((Label)this.getFellow("requester")).setValue(request.get("requester"));
		((Label)this.getFellow("reqDate")).setValue(request.get("reqDate"));
		((Label)this.getFellow("action")).setValue(request.get("action"));
		((Label)this.getFellow("reqStatus")).setValue(request.get("reqStatus"));
		((Label)this.getFellow("appBy")).setValue(request.get("appBy"));
		((Label)this.getFellow("appDt")).setValue(request.get("appDt"));
		((Label)this.getFellow("appRemarks")).setValue(request.get("appRemarks"));
		
		//Check is it SubAppln, then change div/dept label and value.
		if(null != request.get("subAppln"))
		{
			if(request.get("subAppln").equals("subAppln"))
			{
				((Label)this.getFellow("divisionCodeLabel")).setValue("Sub Applicant Code");
				((Label)this.getFellow("divisionNameLabel")).setValue("Sub Applicant Name");
				((Label)this.getFellow("departmentCodeLabel")).setValue("");
				((Label)this.getFellow("departmentNameLabel")).setValue("");
				((Label)this.getFellow("deptCode")).setValue("");
				((Label)this.getFellow("deptName")).setValue("");
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}