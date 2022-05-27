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
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewTransactionCodeWindow extends CommonWindow implements AfterCompose{
	
	private FmtbTransactionCode transactionCode;
	
	private Row discountRow;
	private Label entityLabel,productTypeLabel,txnCodeLabel,descriptionLabel,
				  txnTypeLabel,taxTypeLabel,glCodeLabel,costCentreLabel,discountableLabel,
				  discountGlCodeLabel,discountCostCentreLabel,isManualLabel,effectiveDateLabel;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	  			  lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	private Label message;
	private Row messageRow;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Map params = Executions.getCurrent().getArg();
		Integer transactionCodeNo =  (Integer) params.get("transactionCodeNo");
		transactionCode = businessHelper.getAdminBusiness().getTransactionCode(transactionCodeNo);
		
		entityLabel.setValue(transactionCode.getFmtbEntityMaster().getEntityName());
		if(transactionCode.getPmtbProductType()!=null) productTypeLabel.setValue(transactionCode.getPmtbProductType().getName());
		else productTypeLabel.setValue("-");
		txnCodeLabel.setValue(transactionCode.getTxnCode());
		descriptionLabel.setValue(transactionCode.getDescription());
		txnTypeLabel.setValue(transactionCode.getTxnType());
		taxTypeLabel.setValue(ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE).get(transactionCode.getMstbMasterTable().getMasterCode()));
		glCodeLabel.setValue(transactionCode.getGlCode());
		costCentreLabel.setValue(transactionCode.getCostCentre());
		
		if(transactionCode.getDiscountable().equals(NonConfigurableConstants.BOOLEAN_YES)){
			discountableLabel.setValue("Yes");
			discountGlCodeLabel.setValue(transactionCode.getDiscountGlCode());
			discountCostCentreLabel.setValue(transactionCode.getDiscountCostCentre());
			discountRow.setVisible(true);
		}else
			discountableLabel.setValue("No");
		
		if(transactionCode.getIsManual().equals(NonConfigurableConstants.BOOLEAN_YES))
			isManualLabel.setValue("Yes");
		else
			isManualLabel.setValue("No");
		
		effectiveDateLabel.setValue(DateUtil.convertDateToStr(transactionCode.getEffectiveDate(), DateUtil.GLOBAL_DATE_FORMAT));
		
		if(transactionCode.getCreatedDt()!=null)createdByLabel.setValue(transactionCode.getCreatedBy());
		else createdByLabel.setValue("-");
		if(transactionCode.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(transactionCode.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(transactionCode.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(transactionCode.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(transactionCode.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(transactionCode.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(transactionCode.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(transactionCode.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(transactionCode.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(transactionCode.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(transactionCode.getEffectiveDate()));
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
