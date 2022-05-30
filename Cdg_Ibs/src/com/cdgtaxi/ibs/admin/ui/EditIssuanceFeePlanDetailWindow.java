package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DuplicateEffectiveDateError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditIssuanceFeePlanDetailWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditIssuanceFeePlanDetailWindow.class);

	private final MstbIssuanceFeeMaster plan;
	private final MstbIssuanceFeeDetail detail;

	private Datebox startDateDB;
	private Decimalbox subscriptionFeeDB;
	
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel,planNameLabel;

	@SuppressWarnings("unchecked")
	public EditIssuanceFeePlanDetailWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planDetailNo =  (Integer) params.get("planDetailNo");
		logger.info("Plan Detail No = " + planDetailNo);
		detail = businessHelper.getAdminBusiness().getIssuanceFeePlanDetail(planDetailNo);
		plan = detail.getMstbIssuanceFeeMaster();
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		planNameLabel.setValue(plan.getIssuanceFeeName());
		startDateDB.setRawValue(detail.getEffectiveDt());
		subscriptionFeeDB.setValue(detail.getIssuanceFee());
		
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
	}

	public void updateDetail() throws InterruptedException {
		detail.setEffectiveDt(DateUtil.convertDateToTimestamp(startDateDB.getValue()));
		detail.setIssuanceFee(subscriptionFeeDB.getValue());

		try {
			businessHelper.getAdminBusiness().updateIssuanceFeePlanDetail(detail, getUserLoginIdAndDomain());
			MasterSetup.getSubscriptionManager().refresh();
			Messagebox.show(
					"Plan detail has been successfully saved.", "Save Plan Detail",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		} 
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateEffectiveDateError e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} 
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
