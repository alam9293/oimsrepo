package com.cdgtaxi.ibs.admin.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.RequiredEqualOrLaterThanCurrentDateConstraint;

@SuppressWarnings("serial")
public class EditPrepaidPromotionPlanWindow extends CommonWindow implements AfterCompose {

	private CapsTextbox remarksTextBox;
	private Button deleteButton;
	private Datebox effDateFromDateBox, effDateToDateBox;
	private Decimalbox cashbackDecimalBox;
	private Label promoCodeLabel, createdByLabel, createdTimeLabel, createdDateLabel, 
		lastUpdatedDateLabel, lastUpdatedByLabel, lastUpdatedTimeLabel;
	private static final Logger logger = Logger.getLogger(EditPrepaidPromotionPlanWindow.class);
	
	private MstbPromotionCashPlus model;
	private boolean beforeEffDateFrom = false;
	private Textbox txtpromoCodeLabel;

	public EditPrepaidPromotionPlanWindow(){

		Map map = Executions.getCurrent().getArg();
		String promoCode = (String)map.get("promoCode");
		model = (MstbPromotionCashPlus)this.businessHelper.getAdminBusiness().getPromotionCashPlus(promoCode);
	}
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		promoCodeLabel.setValue(model.getPromoCode());
	
		Date effectiveDtFrom = model.getEffectiveDtFrom();
		Date currentDt = DateUtil.getCurrentTimestamp();
		
		beforeEffDateFrom = currentDt.before(effectiveDtFrom);
		
		if(beforeEffDateFrom){
			
			effDateFromDateBox.setDisabled(false);
			effDateFromDateBox.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
			cashbackDecimalBox.setDisabled(false);
			remarksTextBox.setDisabled(false);
			deleteButton.setDisabled(false);
		}
		
		
		effDateFromDateBox.setRawValue(model.getEffectiveDtFrom());
		effDateToDateBox.setRawValue(model.getEffectiveDtTo());
		cashbackDecimalBox.setRawValue(model.getCashplus());
		remarksTextBox.setRawValue(model.getRemarks());
		createdByLabel.setValue(model.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertDateToStr(model.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertDateToStr(model.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		
		
		
	
		if(model.getUpdatedBy()!=null){
			lastUpdatedByLabel.setValue(model.getUpdatedBy());
		}
		else {
			lastUpdatedByLabel.setValue("-");
		}
		if(model.getUpdatedDt()!=null) {
			lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(model.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		}
		else {
			lastUpdatedDateLabel.setValue("-");
		}
		if(model.getUpdatedDt()!=null) {
			lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(model.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		}
		else {
			lastUpdatedTimeLabel.setValue("-");
		}

	}
	

	public void savePlan() throws InterruptedException{
		
		try{
			
			Date effDateTo = effDateToDateBox.getValue();

			if(ComponentUtil.confirmBox("Update Prepaid Promotion Plan?", "Edit Prepaid Promotion Plan")){
			
				this.displayProcessing();
			
				if(beforeEffDateFrom){

					Date effDateFrom = effDateFromDateBox.getValue();
					BigDecimal cashback = cashbackDecimalBox.getValue();
					String remarks = remarksTextBox.getValue();
					
					model.setEffectiveDtFrom(DateUtil.convertDateTo0000Hours(effDateFrom));
					model.setCashplus(cashback);			
					model.setRemarks(remarks);
				}
			
				model.setEffectiveDtTo(DateUtil.convertDateTo2359Hours(effDateTo));
				
				
				if(model.getEffectiveDtFrom().after(model.getEffectiveDtTo())){
					throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
				}
				
				this.businessHelper.getGenericBusiness().update(model, getUserLoginIdAndDomain());
	
				//Show result
				Messagebox.show("Prepaid Promotion plan edited.", "Edit Prepaid Promotion Plan", Messagebox.OK, Messagebox.INFORMATION);
				
				Executions.getCurrent().sendRedirect("");
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); 
			//TO DO A REFRESH
			LoggerUtil.printStackTrace(logger, e);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	
	public void deletePlan() throws InterruptedException {
		
		if(ComponentUtil.confirmBox("Delete Prepaid Promotion Plan?", "Edit Prepaid Promotion Plan")){
		
			this.displayProcessing();
			
			//check whether the promotion has been effective
			
			this.businessHelper.getGenericBusiness().delete(model);
			//Show result
			Messagebox.show("Prepaid Promotion plan deleted.", "Edit Promotion Plan", Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect("");
		
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
