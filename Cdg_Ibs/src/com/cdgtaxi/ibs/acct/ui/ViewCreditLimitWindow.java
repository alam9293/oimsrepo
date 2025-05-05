package com.cdgtaxi.ibs.acct.ui;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class ViewCreditLimitWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewCreditLimitWindow.class);
	private String custNo, parentCode, code, acctNo, acctName;
	@SuppressWarnings("unchecked")
	public ViewCreditLimitWindow() throws InterruptedException{
		logger.info("ViewCreditLimitWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		// account code
		code = map.get("code");
		// parent code
		parentCode = map.get("parentCode");
		// account number
		acctNo = map.get("acctNo");
		// account name
		acctName = map.get("acctName");
		this.setWidth("75%");
		this.setMinwidth(100);
		this.setSizable(false);
		this.setClosable(true);
	}
	public void init(){
		((Label)this.getFellow("acctName")).setValue(acctName);
		Rows creditLimitRows = (Rows)this.getFellow("creditLimits");
		Rows creditReviewRows = (Rows)this.getFellow("creditReviews");
		List<Map<String, String>> requests = this.businessHelper.getAccountBusiness().getCreditReviews(custNo, acctNo, parentCode, code);
		for(Map<String, String> request : requests){
			Row row = new Row();
			row.appendChild(new Label(request.get("reviewType")));
			row.appendChild(new Label(request.get("oldCreditLimit")));
			row.appendChild(new Label(request.get("newCreditLimit")));
			row.appendChild(new Label(request.get("effectiveFrom")));
			row.appendChild(new Label(request.get("effectiveTo")));
			if(request.get("status")!=null){
				row.appendChild(new Label(request.get("status")));
			}
			if(request.get("requester")!=null){
				row.appendChild(new Label(request.get("requester")));
			}
			row.appendChild(new Label(request.get("approver")));
			row.appendChild(new Label(request.get("remarks")));
			if(request.get("type").equals("creditLimit")){
				creditLimitRows.appendChild(row);
			}else if(request.get("type").equals("creditReview")){
				creditReviewRows.appendChild(row);
			}
		}
		if(creditReviewRows.getChildren().size()==1){
			((Grid)this.getFellow("creditReviewGrid")).setVisible(false);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}
