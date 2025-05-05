package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.model.FmtbBankPaymentDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewBankPaymentDetailGLWindow extends CommonWindow implements AfterCompose {

	private Integer detailNo;
	private Label acquirerLabel, entityLabel, arControlCodeLabel, mdrTxnCodeLabel, adjTxnCodeLabel, effDateLabel;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	private Label message;
	private Row messageRow;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Map map = Executions.getCurrent().getArg();
		detailNo = (Integer)map.get("detailNo");
		
		FmtbBankPaymentDetail detail = this.businessHelper.getAdminBusiness().getBankPaymentDetail(detailNo);
		FmtbBankPaymentMaster master = this.businessHelper.getAdminBusiness().getBankPaymentMaster(detail.getFmtbBankPaymentMaster().getMasterNo());
		
		acquirerLabel.setValue(master.getMstbAcquirer().getName());
		entityLabel.setValue(detail.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName());
		arControlCodeLabel.setValue(detail.getFmtbArContCodeMaster().getArControlCode());
		mdrTxnCodeLabel.setValue(detail.getMdrTxnCode());
		adjTxnCodeLabel.setValue(detail.getMdrAdjTxnCode());
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
