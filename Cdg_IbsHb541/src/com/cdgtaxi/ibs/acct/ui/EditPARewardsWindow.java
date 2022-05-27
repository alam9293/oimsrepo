package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

public class EditPARewardsWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditPARewardsWindow.class);
	private String custNo, acctStatus;
	private String acctType, createdDt;
	@SuppressWarnings("unchecked")
	public EditPARewardsWindow() throws InterruptedException{
		logger.info("EditPABillingWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
		}
		
		acctType = map.get("acctType");
		createdDt = map.get("createdDt");
	}
	public void init() throws InterruptedException{

		((Label)this.getFellow("custNo")).setValue(custNo);
		((Label)this.getFellow("acctStatus")).setValue(acctStatus);
		((Label)this.getFellow("acctType")).setValue(acctType);
		((Label)this.getFellow("createdDt")).setValue(createdDt);
		
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}else if(acctStatus==null || acctStatus.trim().length()==0){
			this.back();
			return;
		}
		Decimalbox initialPoints = (Decimalbox)this.getFellow("initialRewards");
		List<Map<String, Map<Date, Map<String, Integer>>>> rewards = this.businessHelper.getAccountBusiness().getRewardsDetails(custNo);
		if(rewards.isEmpty()){
			initialPoints.setValue(new BigDecimal(0));
		}else{
			for(Map<String, Map<Date, Map<String, Integer>>> reward : rewards){
				if(reward.get("initial")!=null){// initial points
					Integer initialPts = 0;
					for(Date cutoffDate : reward.get("initial").keySet()){
						initialPts += reward.get("initial").get(cutoffDate).get("initial")!=null ? reward.get("initial").get(cutoffDate).get("initial") : 0;
					}
					initialPoints.setValue(new BigDecimal(initialPts));
				}else{
					initialPoints.setValue(new BigDecimal(0));
				}
				break;
			}
		}
	}
	public void save() throws InterruptedException{
		Decimalbox initialPtBox = (Decimalbox)this.getFellow("initialRewards");
		if(this.businessHelper.getAccountBusiness().updateInitialRewardsPoints(custNo, initialPtBox.getValue(), getUserLoginIdAndDomain())){
			Messagebox.show("Rewards saved!", "Edit Rewards", Messagebox.OK, Messagebox.INFORMATION);
		}else{
			Messagebox.show("Unable to save rewards. Please try again later!", "Edit Rewards", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
}
