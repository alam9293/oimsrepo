package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewAcquirerWindow extends CommonWindow implements AfterCompose{
	
	private MstbAcquirer acquirer;
	private Label nameLabel;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Map map = Executions.getCurrent().getArg();
		Integer acquirerNo = (Integer)map.get("acquirerNo");		
		acquirer = (MstbAcquirer)this.businessHelper.getGenericBusiness().get(MstbAcquirer.class, acquirerNo);
	
		nameLabel.setValue(acquirer.getName());
		if(acquirer.getCreatedBy()!=null)createdByLabel.setValue(acquirer.getCreatedBy());
		else createdByLabel.setValue("-");
		if(acquirer.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(acquirer.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(acquirer.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(acquirer.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(acquirer.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(acquirer.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(acquirer.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(acquirer.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(acquirer.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(acquirer.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}
