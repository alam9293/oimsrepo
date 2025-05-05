package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbProdDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewProductDiscountPlanDetailWindow extends CommonWindow implements AfterCompose {

	private MstbProdDiscMaster plan;
	private MstbProdDiscDetail detail;
	
	private Label planNameLabel,startDateDBLabel, discountDBLabel;
	
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	private Label message;
	private Row messageRow;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Map params = Executions.getCurrent().getArg();
		Integer planDetailNo =  (Integer) params.get("planDetailNo");
		detail = businessHelper.getAdminBusiness().getProductDiscountPlanDetail(planDetailNo);
		plan = detail.getMstbProdDiscMaster();
		planNameLabel.setValue(plan.getProductDiscountPlanName());
		startDateDBLabel.setValue(DateUtil.convertDateToStr(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
		discountDBLabel.setValue(StringUtil.bigDecimalToString(detail.getProductDiscount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
	
		if(detail.getCreatedDt()!=null)createdByLabel.setValue(detail.getCreatedBy());
		else createdByLabel.setValue("-");
		if(detail.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(detail.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(detail.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(detail.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		Timestamp t1 = DateUtil.convertTimestampTo0000Hours(detail.getEffectiveDt());
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
