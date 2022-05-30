package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewBranchWindow extends CommonWindow implements AfterCompose{
	
	private MstbBankMaster bank;
	private MstbBranchMaster branch;
	private Label bankNameLabel,branchCodeLabel,branchNameLabel;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Map params = Executions.getCurrent().getArg();
		Integer branchNo =  (Integer) params.get("branchNo");
		branch = businessHelper.getAdminBusiness().getBankBranch(branchNo);
		bank = branch.getMstbBankMaster();
		
		bankNameLabel.setValue(bank.getBankName());
		branchCodeLabel.setValue(branch.getBranchCode());
		branchNameLabel.setValue(branch.getBranchName());
		
		if(branch.getCreatedDt()!=null)createdByLabel.setValue(branch.getCreatedBy());
		else createdByLabel.setValue("-");
		if(branch.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(branch.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(branch.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(branch.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(branch.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(branch.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(branch.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(branch.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(branch.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(branch.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}
