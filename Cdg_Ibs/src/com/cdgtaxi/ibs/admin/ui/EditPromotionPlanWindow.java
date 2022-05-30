package com.cdgtaxi.ibs.admin.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromoReqFlow;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.RequiredCustomLengthBigDecimalConstraint;

@SuppressWarnings("serial")
public class EditPromotionPlanWindow extends CommonWindow implements AfterCompose {

	private CapsTextbox nameTextBox,remarksTextBox;
	private Datebox effDateFromDateBox, effDateToDateBox, effCutoffDateDB;
	private Listbox typeListBox, productTypeListBox, 
		promotionTypeListBox, jobTypeListBox, vehicleModelListBox;
	private Decimalbox promotionValueDecimalBox;
	private MstbPromotion promotion;
	private Label createdByLabel, createdTimeLabel, createdDateLabel, 
		lastUpdatedDateLabel, lastUpdatedByLabel, lastUpdatedTimeLabel;
	private static final Logger logger = Logger.getLogger(EditPromotionPlanWindow.class);
	
	@SuppressWarnings("unchecked")
	public EditPromotionPlanWindow(){
		Map map = Executions.getCurrent().getArg();
		Integer promoNo = (Integer)map.get("promoNo");
		promotion = (MstbPromotion)this.businessHelper.getAdminBusiness().getPromotion(promoNo);
	}
	
	public void afterCompose() {
		
		//wire variables
		Components.wireVariables(this, this);
		
		MstbPromoDetail promoDetail = null;
		MstbPromoReq lastPromoReq = promotion.getLastPromoReq();
		MstbPromoReqFlow lastPromoReqFlow = lastPromoReq.getLastPromoReqFlow();
		
		promoDetail = promotion.getCurrentPromoDetail();
		
		nameTextBox.setValue(promoDetail.getName());
		effDateFromDateBox.setValue(DateUtil.convertTimestampToUtilDate(promoDetail.getEffectiveDtFrom()));
		effDateToDateBox.setValue(DateUtil.convertTimestampToUtilDate(promoDetail.getEffectiveDtTo()));
		promotionValueDecimalBox.setValue(promoDetail.getPromoValue());
		createdByLabel.setValue(promotion.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertDateToStr(promotion.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertDateToStr(promotion.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		if(promotion.getLastUpdatedBy()!=null) lastUpdatedByLabel.setValue(promotion.getLastUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(promotion.getLastUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(promotion.getLastUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(promotion.getLastUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(promotion.getLastUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		Listitem allItem = new Listitem("ALL", null);
		
		productTypeListBox.appendChild((Listitem)allItem.clone());
		List<PmtbProductType> productTypes = this.businessHelper.getProductTypeBusiness().getAllProductType();
		for(PmtbProductType productType : productTypes){
			Listitem item = new Listitem();
			item.setValue(productType);
			item.setLabel(productType.getName());
			
			if(promoDetail.getPmtbProductType()!=null)
				if(productType.getProductTypeId().equals(promoDetail.getPmtbProductType().getProductTypeId()))
					item.setSelected(true);
			
			productTypeListBox.appendChild(item);
		}
		//default selection if none is found
		if(productTypeListBox.getSelectedItem()==null) productTypeListBox.setSelectedIndex(0);
		
		Set<String> promoAcctTypeKeys = NonConfigurableConstants.PROMO_ACCT_TYPE.keySet();
		for(String key : promoAcctTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.PROMO_ACCT_TYPE.get(key));
			
			if(promoDetail.getType().equals(key))
				item.setSelected(true);
			
			typeListBox.appendChild(item);
		}
		//default selection if none is found
		if(typeListBox.getSelectedItem()==null) typeListBox.setSelectedIndex(0);
		
		effCutoffDateDB.setValue(promoDetail.getEffectiveCutoffDate());
		
		Set<String> promoTypeKeys = NonConfigurableConstants.PROMO_TYPE.keySet();
		for(String key : promoTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.PROMO_TYPE.get(key));
			
			if(promoDetail.getPromoType().equals(key))
				item.setSelected(true);
			
			promotionTypeListBox.appendChild(item);
		}
		//default selection if none is found
		if(promotionTypeListBox.getSelectedItem()==null) promotionTypeListBox.setSelectedIndex(0);
		
		jobTypeListBox.appendChild((Listitem)allItem.clone());
		Map<String, String> jobTypes = ConfigurableConstants.getMasters(ConfigurableConstants.JOB_TYPE);
		Set<String> jobTypeKeys = jobTypes.keySet();
		for(String key : jobTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(jobTypes.get(key));
			
			if(promoDetail.getMstbMasterTableByJobType()!=null)
				if(promoDetail.getMstbMasterTableByJobType().getMasterCode().equals(key))
					item.setSelected(true);
			
			jobTypeListBox.appendChild(item);
		}
		//default selection if none is found
		if(jobTypeListBox.getSelectedItem()==null) jobTypeListBox.setSelectedIndex(0);
		
		vehicleModelListBox.appendChild((Listitem)allItem.clone());
		Map<String, String> vehicleModels = ConfigurableConstants.getMasters(ConfigurableConstants.VEHICLE_MODEL);
		Set<String> vehicleModelKeys = vehicleModels.keySet();
		for(String key : vehicleModelKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(vehicleModels.get(key));
			
			if(promoDetail.getMstbMasterTableByVehicleModel()!=null)
				if(promoDetail.getMstbMasterTableByVehicleModel().getMasterCode().equals(key))
					item.setSelected(true);
			
			vehicleModelListBox.appendChild(item);
		}
		//default selection if none is found
		if(vehicleModelListBox.getSelectedItem()==null) vehicleModelListBox.setSelectedIndex(0);
		
	}
	
	@SuppressWarnings("unchecked")
	public void savePlan() throws InterruptedException{
		
		try{
			this.displayProcessing();
			
			String name = nameTextBox.getValue();
			String type = (String)typeListBox.getSelectedItem().getValue();
			PmtbProductType productType = (PmtbProductType)productTypeListBox.getSelectedItem().getValue();
			String promoType = (String)promotionTypeListBox.getSelectedItem().getValue();
			BigDecimal promoValue = promotionValueDecimalBox.getValue();
			Date effDateFrom = effDateFromDateBox.getValue();
			Date effDateTo = effDateToDateBox.getValue();
			String jobType = (String)jobTypeListBox.getSelectedItem().getValue();
			String vehicleModel = (String)vehicleModelListBox.getSelectedItem().getValue();
			
			if(effDateFrom.compareTo(effDateTo) == 1)
				throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
			
			if(promotionTypeListBox.getSelectedItem().getValue().equals(NonConfigurableConstants.PROMO_TYPE_DOLLAR)){
				Constraint dollarValueConstraint = new RequiredCustomLengthBigDecimalConstraint(10);
				dollarValueConstraint.validate(promotionValueDecimalBox, promoValue);
			}
			else{
				Constraint percentageValueConstraint = new RequiredCustomLengthBigDecimalConstraint("100.00");
				percentageValueConstraint.validate(promotionValueDecimalBox, promoValue);
			}
			
			Date cutoffDate = effCutoffDateDB.getValue();
				if(cutoffDate.before(effDateTo))
					throw new WrongValueException(effCutoffDateDB, "Effective Cutoff Date cannot be earlier than Effective Trip Date To.");
			
			
			if(this.businessHelper.getAdminBusiness().hasDuplicateName(promotion.getPromoNo(), name)){
				throw new WrongValueException(nameTextBox, "Name has been used.");
			}
			
			MstbPromoDetail editPromoDetail = new MstbPromoDetail();
			editPromoDetail.setName(name);
			editPromoDetail.setMstbPromotion(promotion);
			editPromoDetail.setType(type);
			editPromoDetail.setPmtbProductType(productType);
			editPromoDetail.setPromoType(promoType);
			editPromoDetail.setPromoValue(promoValue);
			editPromoDetail.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(DateUtil.convertDateTo0000Hours(effDateFrom)));
			editPromoDetail.setEffectiveDtTo(DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(effDateTo)));
			editPromoDetail.setEffectiveCutoffDate(DateUtil.convertUtilDateToSqlDate(cutoffDate));
			if(jobType!=null) editPromoDetail.setMstbMasterTableByJobType(ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_TYPE, jobType));
			else editPromoDetail.setMstbMasterTableByJobType(null);
			if(vehicleModel!=null) editPromoDetail.setMstbMasterTableByVehicleModel(ConfigurableConstants.getMasterTable(ConfigurableConstants.VEHICLE_MODEL, vehicleModel));
			else editPromoDetail.setMstbMasterTableByVehicleModel(null);
			this.businessHelper.getGenericBusiness().save(editPromoDetail, getUserLoginIdAndDomain());
			
			
			MstbPromoReq req = new MstbPromoReq();
			req.setEvent(NonConfigurableConstants.PROMOTION_EVENT_EDIT);
			req.setMstbPromotion(promotion);
			req.setFromPromoDetail(promotion.getCurrentPromoDetail());
			req.setRequestBy(getUserLoginIdAndDomain());
			req.setRequestDt(DateUtil.getCurrentTimestamp());
			this.businessHelper.getGenericBusiness().save(req, getUserLoginIdAndDomain());
			
			MstbPromoReqFlow reqFlow = new MstbPromoReqFlow();
			reqFlow.setMstbPromotionReq(req);
			reqFlow.setReqFromStatus(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_NEW);
			reqFlow.setReqToStatus(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_PENDING);
			reqFlow.setFromStatus(promotion.getCurrentStatus());
			reqFlow.setToStatus(NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_EDIT);
			reqFlow.setToPromoDetail(editPromoDetail);
			reqFlow.setRemarks(remarksTextBox.getValue());
			this.businessHelper.getGenericBusiness().save(reqFlow, getUserLoginIdAndDomain());

			
			req.setLastPromoReqFlow(reqFlow);
			this.businessHelper.getGenericBusiness().update(req, getUserLoginIdAndDomain());
			
			promotion.setLastPromoReq(req);
			this.businessHelper.getGenericBusiness().update(promotion, getUserLoginIdAndDomain());

			MasterSetup.getPromotionManager().refresh();
			
			//Show result
			Messagebox.show("Promotion plan edited.", "Edit Promotion Plan", Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect("");
		}
		catch(WrongValueException wve){
			throw wve;
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
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	
	public void deletePlan() throws InterruptedException {
		
		this.displayProcessing();
		
		MstbPromoReq req = new MstbPromoReq();
		req.setEvent(NonConfigurableConstants.PROMOTION_EVENT_DELETE);
		req.setMstbPromotion(promotion);
		req.setFromPromoDetail(promotion.getCurrentPromoDetail());
		req.setRequestBy(getUserLoginIdAndDomain());
		req.setRequestDt(DateUtil.getCurrentTimestamp());
		this.businessHelper.getGenericBusiness().save(req, getUserLoginIdAndDomain());
		
		MstbPromoReqFlow reqFlow = new MstbPromoReqFlow();
		reqFlow.setMstbPromotionReq(req);
		reqFlow.setReqFromStatus(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_NEW);
		reqFlow.setReqToStatus(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_PENDING);
		reqFlow.setFromStatus(promotion.getCurrentStatus());
		reqFlow.setToStatus(NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_DELETE);
		reqFlow.setToPromoDetail(promotion.getCurrentPromoDetail());
		reqFlow.setRemarks(remarksTextBox.getValue());
		this.businessHelper.getGenericBusiness().save(reqFlow, getUserLoginIdAndDomain());

		
		req.setLastPromoReqFlow(reqFlow);
		this.businessHelper.getGenericBusiness().update(req, getUserLoginIdAndDomain());
		
		promotion.setLastPromoReq(req);
		this.businessHelper.getGenericBusiness().update(promotion, getUserLoginIdAndDomain());

		MasterSetup.getPromotionManager().refresh();
		
		//Show result
		Messagebox.show("Promotion plan deleted.", "Edit Promotion Plan", Messagebox.OK, Messagebox.INFORMATION);
		
		Executions.getCurrent().sendRedirect("");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
