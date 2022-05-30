package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.model.FmtbNonBillableDetail;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewNonBillableDetailGLWindow extends CommonWindow implements AfterCompose {
	
	private Label serviceProviderLabel, cardTypeLabel, entityLabel, arControlCodeLabel, fAmountTxnCodeLabel, aFeeTxnCodeLabel, effDateLabel, premiumAmountTxnCodeLabel;
	private Label createdByLabel,createdDateLabel,createdTimeLabel,lastUpdatedByLabel,lastUpdatedDateLabel,lastUpdatedTimeLabel;
	private Row messageRow;
	private Label message;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

		Map map = Executions.getCurrent().getArg();
		Integer detailNo = (Integer)map.get("detailNo");
				
		FmtbNonBillableDetail detail = this.businessHelper.getAdminBusiness().getNonBillableDetail(detailNo);
		FmtbNonBillableMaster master = this.businessHelper.getAdminBusiness().getNonBillableMaster(detail.getFmtbNonBillableMaster().getMasterNo());

		serviceProviderLabel.setValue(master.getMstbMasterTableByServiceProvider().getMasterValue());
		cardTypeLabel.setValue(master.getMstbMasterTableByPymtTypeMasterNo().getMasterValue());
		entityLabel.setValue(detail.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName());
		arControlCodeLabel.setValue(detail.getFmtbArContCodeMaster().getArControlCode());
		fAmountTxnCodeLabel.setValue(detail.getFareAmountTxnCode());
		aFeeTxnCodeLabel.setValue(detail.getAdminFeeTxnCode());
		premiumAmountTxnCodeLabel.setValue(detail.getPremiumAmountTxnCode());
		effDateLabel.setValue(DateUtil.convertDateToStr(detail.getEffectiveDate(), DateUtil.GLOBAL_DATE_FORMAT));
		
		if(detail.getCreatedBy()!=null) createdByLabel.setValue(detail.getCreatedBy());
		else createdByLabel.setValue("-");
		if(detail.getCreatedDt()!=null) createdDateLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(detail.getCreatedDt()!=null) createdTimeLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		
		if(detail.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(detail.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(detail.getEffectiveDate()));
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
