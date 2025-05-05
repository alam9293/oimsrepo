package com.cdgtaxi.ibs.admin.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
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
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.RequiredCustomLengthBigDecimalConstraint;

@SuppressWarnings("serial")
public class CreatePromotionPlanWindow extends CommonWindow implements AfterCompose {

	private CapsTextbox nameTextBox;
	private Datebox effDateFromDateBox, effDateToDateBox, effCutoffDateDB;
	private Listbox typeListBox, productTypeListBox, 
		promotionTypeListBox, jobTypeListBox, vehicleModelListBox;
	private Decimalbox promotionValueDecimalBox;

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Listitem allItem = new Listitem("ALL", null);
		
		productTypeListBox.appendChild((Listitem)allItem.clone());
		List<PmtbProductType> productTypes = this.businessHelper.getProductTypeBusiness().getAllProductType();
		for(PmtbProductType productType : productTypes){
			Listitem item = new Listitem();
			item.setValue(productType);
			item.setLabel(productType.getName());
			productTypeListBox.appendChild(item);
		}
		productTypeListBox.setSelectedIndex(0);
		
		Set<String> promoAcctTypeKeys = NonConfigurableConstants.PROMO_ACCT_TYPE.keySet();
		for(String key : promoAcctTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.PROMO_ACCT_TYPE.get(key));
			typeListBox.appendChild(item);
		}
		typeListBox.setSelectedIndex(0);
		
		Set<String> promoTypeKeys = NonConfigurableConstants.PROMO_TYPE.keySet();
		for(String key : promoTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.PROMO_TYPE.get(key));
			promotionTypeListBox.appendChild(item);
		}
		promotionTypeListBox.setSelectedIndex(0);
		
		jobTypeListBox.appendChild((Listitem)allItem.clone());
		Map<String, String> jobTypes = ConfigurableConstants.getMasters(ConfigurableConstants.JOB_TYPE);
		Set<String> jobTypeKeys = jobTypes.keySet();
		for(String key : jobTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(jobTypes.get(key));
			jobTypeListBox.appendChild(item);
		}
		jobTypeListBox.setSelectedIndex(0);
		
		vehicleModelListBox.appendChild((Listitem)allItem.clone());
		Map<String, String> vehicleModels = ConfigurableConstants.getMasters(ConfigurableConstants.VEHICLE_MODEL);
		Set<String> vehicleModelKeys = vehicleModels.keySet();
		for(String key : vehicleModelKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(vehicleModels.get(key));
			vehicleModelListBox.appendChild(item);
		}
		vehicleModelListBox.setSelectedIndex(0);
	}
	
	public void createPlan() throws InterruptedException{
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
			
			MstbPromotion newPromotion = new MstbPromotion();
			this.businessHelper.getGenericBusiness().save(newPromotion, getUserLoginIdAndDomain());
	
			MstbPromoDetail newPromoDetail = new MstbPromoDetail();
			newPromoDetail.setName(name);
			
			if(this.businessHelper.getAdminBusiness().hasDuplicateName(null, name)){
				throw new WrongValueException(nameTextBox, "Name has been used.");
			}
			
			newPromoDetail.setMstbPromotion(newPromotion);
			newPromoDetail.setType(type);
			newPromoDetail.setPmtbProductType(productType);
			newPromoDetail.setPromoType(promoType);
			newPromoDetail.setPromoValue(promoValue);
			newPromoDetail.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(DateUtil.convertDateTo0000Hours(effDateFrom)));
			newPromoDetail.setEffectiveDtTo(DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(effDateTo)));
			newPromoDetail.setEffectiveCutoffDate(DateUtil.convertUtilDateToSqlDate(cutoffDate));
			if(jobType!=null) newPromoDetail.setMstbMasterTableByJobType(ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_TYPE, jobType));
			if(vehicleModel!=null) newPromoDetail.setMstbMasterTableByVehicleModel(ConfigurableConstants.getMasterTable(ConfigurableConstants.VEHICLE_MODEL, vehicleModel));
			
			this.businessHelper.getGenericBusiness().save(newPromoDetail, getUserLoginIdAndDomain());
			
			MstbPromoReq newPromoReq = new MstbPromoReq();
			newPromoReq.setEvent(NonConfigurableConstants.PROMOTION_EVENT_CREATE);
			newPromoReq.setMstbPromotion(newPromotion);
			newPromoReq.setFromPromoDetail(null);
			newPromoReq.setRequestBy(getUserLoginIdAndDomain());
			newPromoReq.setRequestDt(DateUtil.getCurrentTimestamp());
			
			this.businessHelper.getGenericBusiness().save(newPromoReq, getUserLoginIdAndDomain());
			
			MstbPromoReqFlow reqFlow = new MstbPromoReqFlow();
			reqFlow.setMstbPromotionReq(newPromoReq);
			reqFlow.setReqFromStatus(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_NEW);
			reqFlow.setReqToStatus(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_PENDING);
			reqFlow.setFromStatus(NonConfigurableConstants.PROMOTION_STATUS_NEW);
			reqFlow.setToStatus(NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_CREATE);
			reqFlow.setToPromoDetail(newPromoDetail);
			this.businessHelper.getGenericBusiness().save(reqFlow, getUserLoginIdAndDomain());
			
			
			newPromoReq.setLastPromoReqFlow(reqFlow);
			this.businessHelper.getGenericBusiness().update(newPromoReq, getUserLoginIdAndDomain());
			
			newPromotion.setLastPromoReq(newPromoReq);
			this.businessHelper.getGenericBusiness().update(newPromotion, getUserLoginIdAndDomain());
			
			MasterSetup.getPromotionManager().refresh();
		
			//Show result
			Messagebox.show("New promotion plan created.", "Create Promotion Plan", Messagebox.OK, Messagebox.INFORMATION);
			
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
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
