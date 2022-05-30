package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewGLTaxWindow extends CommonWindow implements AfterCompose{

	private FmtbTaxCode tax;
	
	private Label entityLabel,codeLabel,glCodeLabel,taxTypeLabel,costCentreLabel,
				  taxRateLabel,effectiveDateLabel;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	 			  lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	private Label message;
	private Row messageRow;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Map params = Executions.getCurrent().getArg();
		Integer taxCodeNo =  (Integer) params.get("taxCodeNo");
		tax = businessHelper.getAdminBusiness().getGLTax(taxCodeNo);
		
		entityLabel.setValue(tax.getFmtbEntityMaster().getEntityName());
		codeLabel.setValue(tax.getGlTaxCode());
		glCodeLabel.setValue(tax.getGlCode());
		taxTypeLabel.setValue(ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE).get(tax.getMstbMasterTable().getMasterCode()));
		costCentreLabel.setValue(tax.getCostCentre());
		taxRateLabel.setValue(StringUtil.bigDecimalToString(tax.getTaxRate(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		effectiveDateLabel.setValue(DateUtil.convertDateToStr(tax.getEffectiveDate(), DateUtil.GLOBAL_DATE_FORMAT));

		if(tax.getCreatedDt()!=null)createdByLabel.setValue(tax.getCreatedBy());
		else createdByLabel.setValue("-");
		if(tax.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(tax.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(tax.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(tax.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(tax.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(tax.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(tax.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(tax.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(tax.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(tax.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(tax.getEffectiveDate()));
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
