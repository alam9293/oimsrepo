package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewGLBankWindow extends CommonWindow implements AfterCompose{

	private FmtbBankCode bank;
	
	private Label entityLabel,isDefaultLabel,bankCodeLabel,bankNameLabel,
				  branchCodeLabel,branchNameLabel,glCodeLabel,taxTypeLabel,
				  costCentreLabel,effectiveDateLabel,bankAcctNoLabel,bankAcctNameLabel,
				  isDefaultGBLabel;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	  			  lastUpdatedByLabel, lastUpdatedDateLabel, 
	  			  lastUpdatedTimeLabel;
	private Label message;
	private Row messageRow;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Map params = Executions.getCurrent().getArg();
		Integer bankCodeNo =  (Integer) params.get("bankCodeNo");
		bank = businessHelper.getAdminBusiness().getGLBank(bankCodeNo);
		
		entityLabel.setValue(bank.getFmtbEntityMaster().getEntityName());
		if(bank.getIsDefault().equals(NonConfigurableConstants.BOOLEAN_YN_YES))
			isDefaultLabel.setValue("Yes");
		else
			isDefaultLabel.setValue("No");
		bankCodeLabel.setValue(bank.getBankCode());
		bankNameLabel.setValue(bank.getBankName());
		branchCodeLabel.setValue(bank.getBranchCode());
		branchNameLabel.setValue(bank.getBranchName());
		glCodeLabel.setValue(bank.getGlCode());
		taxTypeLabel.setValue(ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE).get(bank.getMstbMasterTable().getMasterCode()));
		costCentreLabel.setValue(bank.getCostCentre());
		effectiveDateLabel.setValue(DateUtil.convertDateToStr(bank.getEffectiveDate(), DateUtil.GLOBAL_DATE_FORMAT));
		bankAcctNoLabel.setValue(bank.getBankAcctNo());
		bankAcctNameLabel.setValue(bank.getBankAcctName());
		if(bank.getIsDefaultGiroBank().equals(NonConfigurableConstants.BOOLEAN_YN_YES))
			isDefaultGBLabel.setValue("Yes");
		else
			isDefaultGBLabel.setValue("No");
		
		if(bank.getCreatedDt()!=null)createdByLabel.setValue(bank.getCreatedBy());
		else createdByLabel.setValue("-");
		if(bank.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(bank.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(bank.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(bank.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(bank.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(bank.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(bank.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(bank.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(bank.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(bank.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(bank.getEffectiveDate()));
		Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
		if(t1.compareTo(t2) <= 0)
			message.setValue("The record has taken effect, no changes allowed.");
		else
			messageRow.setVisible(false);
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}
