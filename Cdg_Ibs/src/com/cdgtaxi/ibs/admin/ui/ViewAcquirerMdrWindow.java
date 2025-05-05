package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewAcquirerMdrWindow extends CommonWindow implements AfterCompose {

	private Label acquirerLabel, rateLabel, effDateLabel, createdByLabel, createdDateLabel,createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	private Label message;
	private Row messageRow;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Map map = Executions.getCurrent().getArg();
		MstbAcquirerMdr acquirerMdr = this.businessHelper.getAdminBusiness().getAcquirerMdr((Integer)map.get("mdrNo"));

		acquirerLabel.setValue(acquirerMdr.getMstbAcquirer().getName());
		rateLabel.setValue(StringUtil.bigDecimalToString(acquirerMdr.getRate(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		effDateLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getEffectiveDate(), DateUtil.GLOBAL_DATE_FORMAT));

		if(acquirerMdr.getCreatedBy()!=null) createdByLabel.setValue(acquirerMdr.getCreatedBy());
		else createdByLabel.setValue("-");
		if(acquirerMdr.getCreatedDt()!=null) createdDateLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(acquirerMdr.getCreatedDt()!=null) createdTimeLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		
		if(acquirerMdr.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(acquirerMdr.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(acquirerMdr.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(acquirerMdr.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(acquirerMdr.getEffectiveDate()));
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
