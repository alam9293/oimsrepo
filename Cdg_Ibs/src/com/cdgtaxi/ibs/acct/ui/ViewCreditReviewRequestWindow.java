package com.cdgtaxi.ibs.acct.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ViewCreditReviewRequestWindow extends CommonWindow {
	private static Logger logger = Logger.getLogger(ViewCreditReviewRequestWindow.class);
	private static final long serialVersionUID = 1L;
	private Integer requestId;
	@SuppressWarnings("unchecked")
	public ViewCreditReviewRequestWindow() throws InterruptedException{
		logger.info("ViewCreditReviewRequestWindow()");
		//request number
		Map<String, Integer> map = Executions.getCurrent().getArg();
		requestId = map.get("requestId");
		if(requestId==null){
			Messagebox.show("Request ID not found!", "View Credit Review Request", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(requestId==null){
			return;
		}
		Map<String, String> request = this.businessHelper.getAccountBusiness().getCreditReviewRequest(requestId);
		((Label)this.getFellow("acctName")).setValue(request.get("acctName"));
		((Label)this.getFellow("reviewType")).setValue(request.get("reviewType"));
		((Label)this.getFellow("creditLimit")).setValue(request.get("oldCreditLimit"));
		((Label)this.getFellow("creditBalance")).setValue(request.get("creditBalance"));
		((Label)this.getFellow("newCreditLimit")).setValue(request.get("newCreditLimit"));
		((Label)this.getFellow("fromDate")).setValue(request.get("effectiveFrom"));
		((Label)this.getFellow("toDate")).setValue(request.get("effectiveTo"));
		((Label)this.getFellow("requester")).setValue(request.get("requester"));
		((Label)this.getFellow("reqDate")).setValue(request.get("reqDate"));
		((Label)this.getFellow("reqRemarks")).setValue(request.get("reqRemarks"));
		((Label)this.getFellow("reqStatus")).setValue(request.get("status"));
		((Label)this.getFellow("approver")).setValue(request.get("approver"));
		((Label)this.getFellow("approveDate")).setValue(request.get("approveDate"));
		((Label)this.getFellow("approveRemarks")).setValue(request.get("approveRemarks"));
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}