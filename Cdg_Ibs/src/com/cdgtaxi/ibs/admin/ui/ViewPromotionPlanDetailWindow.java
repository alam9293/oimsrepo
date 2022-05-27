package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromoReqFlow;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewPromotionPlanDetailWindow extends CommonWindow implements AfterCompose {

	public enum DetailType {APPROVE, HISTORY};
	
	private DetailType type;
	private static final Logger logger = Logger.getLogger(ViewPromotionPlanDetailWindow.class);
	private Label 
		nameLabel, typeLabel, productTypeLabel,
		promoTypeLabel, promoValueLabel, effDateFromLabel,
		effDateToLabel, jobTypeLabel, vehicleModelLabel,effCutoffDateLabel;
		
	
		
	
	private Label 
	newNameLabel, newTypeLabel, newProductTypeLabel,
	newPromoTypeLabel,newPromoValueLabel, newEffDateFromLabel,
	newEffDateToLabel, newJobTypeLabel, newVehicleModelLabel,
	newEffCutoffDateLabel;
	
	private Label 
	newName, newType, newProductType,
	newPromoType,newPromoValue, newEffDateFrom,
	newEffDateTo, newJobType, newVehicleModel,
	newEffCutoffDate;
	
	private Row actionRow;
	private Label action, requestStatus;
	private Label requestBy, requestTime, requestDate, requestRemarks, approveBy, approveTime,  approveDate, approveRemarks;
	
	
	private MstbPromoReq promoReq;

	
	@SuppressWarnings("unchecked")
	public ViewPromotionPlanDetailWindow(){
		Map map = Executions.getCurrent().getArg();
		Integer promoReqNo = (Integer)map.get("promoReqNo");
	
		type = (DetailType)map.get("type");
		
		promoReq = (MstbPromoReq)this.businessHelper.getAdminBusiness().getPromoReq(promoReqNo);
		logger.info("Promo Req No " + promoReqNo);
	}
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);		
		
		MstbPromotion promotion = promoReq.getMstbPromotion();
		MstbPromoDetail fromPromoDetail = promoReq.getFromPromoDetail();

		if(fromPromoDetail!=null){
			
			nameLabel.setValue(fromPromoDetail.getName());
			typeLabel.setValue(NonConfigurableConstants.PROMO_ACCT_TYPE.get(fromPromoDetail.getType()));
			if(fromPromoDetail.getPmtbProductType()==null) productTypeLabel.setValue("ALL");
			else productTypeLabel.setValue(fromPromoDetail.getPmtbProductType().getName());
			promoTypeLabel.setValue(NonConfigurableConstants.PROMO_TYPE.get(fromPromoDetail.getPromoType()));
			promoValueLabel.setValue(StringUtil.bigDecimalToString(fromPromoDetail.getPromoValue(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			if(fromPromoDetail.getMstbMasterTableByJobType()==null) jobTypeLabel.setValue("ALL");
			else jobTypeLabel.setValue(fromPromoDetail.getMstbMasterTableByJobType().getMasterValue());
			if(fromPromoDetail.getMstbMasterTableByVehicleModel()==null) vehicleModelLabel.setValue("ALL");
			else vehicleModelLabel.setValue(fromPromoDetail.getMstbMasterTableByVehicleModel().getMasterValue());
			effDateFromLabel.setValue(DateUtil.convertDateToStr(fromPromoDetail.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
			effDateToLabel.setValue(DateUtil.convertDateToStr(fromPromoDetail.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
			effCutoffDateLabel.setValue(DateUtil.convertDateToStr(fromPromoDetail.getEffectiveCutoffDate(), DateUtil.GLOBAL_DATE_FORMAT));
			
		
		} else {
			nameLabel.setValue("-");
			typeLabel.setValue("-");
			productTypeLabel.setValue("-");
			promoTypeLabel.setValue("-");
			promoValueLabel.setValue("-");
			jobTypeLabel.setValue("-");
			vehicleModelLabel.setValue("-");
			effDateFromLabel.setValue("-");
			effDateToLabel.setValue("-");
			effCutoffDateLabel.setValue("-");
		}
		
		Set<MstbPromoReqFlow> mstbPromoReqFlows = promoReq.getMstbPromoReqFlows();
		MstbPromoDetail toPromoDetail = null;
		for(MstbPromoReqFlow flow: mstbPromoReqFlows)
		{
			if(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_NEW.equals(flow.getReqFromStatus())){
				toPromoDetail = flow.getToPromoDetail();
				break;
			}
		}
		
		if(toPromoDetail!=null){
			newName.setValue(toPromoDetail.getName());
			newType.setValue(NonConfigurableConstants.PROMO_ACCT_TYPE.get(toPromoDetail.getType()));
			if(toPromoDetail.getPmtbProductType()==null) newProductType.setValue("ALL");
			else newProductType.setValue(toPromoDetail.getPmtbProductType().getName());
			newPromoType.setValue(NonConfigurableConstants.PROMO_TYPE.get(toPromoDetail.getPromoType()));
			newPromoValue.setValue(StringUtil.bigDecimalToString(toPromoDetail.getPromoValue(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			if(toPromoDetail.getMstbMasterTableByJobType()==null) newJobType.setValue("ALL");
			else newJobType.setValue(toPromoDetail.getMstbMasterTableByJobType().getMasterValue());
			if(toPromoDetail.getMstbMasterTableByVehicleModel()==null)newVehicleModel.setValue("ALL");
			else newVehicleModel.setValue(toPromoDetail.getMstbMasterTableByVehicleModel().getMasterValue());
			newEffDateFrom.setValue(DateUtil.convertDateToStr(toPromoDetail.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
			newEffDateTo.setValue(DateUtil.convertDateToStr(toPromoDetail.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
			newEffCutoffDate.setValue(DateUtil.convertDateToStr(toPromoDetail.getEffectiveCutoffDate(), DateUtil.GLOBAL_DATE_FORMAT));
		}
		
		Label[] newComponents = new Label[]{newName, newType, newProductType,
				newPromoType,newPromoValue, newEffDateFrom,
				newEffDateTo, newJobType, newVehicleModel,
				newEffCutoffDate};
		Label[] newComponentLabels = new Label[]{newNameLabel, newTypeLabel, newProductTypeLabel,
				newPromoTypeLabel,newPromoValueLabel, newEffDateFromLabel,
				newEffDateToLabel, newJobTypeLabel, newVehicleModelLabel,
				newEffCutoffDateLabel};
		Label[] oldComponents = new Label[]{nameLabel, typeLabel, productTypeLabel,
				promoTypeLabel, promoValueLabel, effDateFromLabel,
				effDateToLabel, jobTypeLabel, vehicleModelLabel,
				effCutoffDateLabel};
		
		
		//hide those fields does not edited
		for(int i=0; i<newComponents.length; i++){
			Label newComponent = newComponents[i];
			Label oldComponent = oldComponents[i];
			
			if(!newComponent.getValue().equals(oldComponent.getValue())){
				newComponent.getParent().setVisible(true);
				newComponent.setVisible(true);
				newComponentLabels[i].setVisible(true);
			}
		}
		
		if(type==DetailType.HISTORY){
			action.setValue(NonConfigurableConstants.PROMOTION_EVENT.get(promoReq.getEvent()));
			actionRow.setVisible(true);
		}
		
		Set<MstbPromoReqFlow> promoReqFlows = promoReq.getMstbPromoReqFlows();
		
		for(MstbPromoReqFlow flow: promoReqFlows){
			
			String reqToStatus = flow.getReqToStatus();
			if(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_PENDING.equals(reqToStatus)){
				
				if(flow.getCreatedBy()!=null) requestBy.setValue(flow.getCreatedBy());
				else requestBy.setValue("-");
				if(flow.getCreatedDt()!=null) requestDate.setValue(DateUtil.convertDateToStr(flow.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
				else requestDate.setValue("-");
				if(flow.getCreatedDt()!=null) requestTime.setValue(DateUtil.convertDateToStr(flow.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
				else requestTime.setValue("-");
				if(flow.getRemarks()!=null) requestRemarks.setValue(flow.getRemarks());
				else requestRemarks.setValue("-");
			} 
			if(NonConfigurableConstants.PROMOTION_REQUEST_STATUS_APPROVED.equals(reqToStatus) ||
					NonConfigurableConstants.PROMOTION_REQUEST_STATUS_REJECTED.equals(reqToStatus)){
				
				if(flow.getCreatedBy()!=null) approveBy.setValue(flow.getCreatedBy());
				else approveBy.setValue("-");
				if(flow.getCreatedDt()!=null) approveDate.setValue(DateUtil.convertDateToStr(flow.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
				else approveDate.setValue("-");
				if(flow.getCreatedDt()!=null) approveTime.setValue(DateUtil.convertDateToStr(flow.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
				else approveTime.setValue("-");
				if(flow.getRemarks()!=null) approveRemarks.setValue(flow.getRemarks());
				else approveRemarks.setValue("-");
				
				requestStatus.setValue(NonConfigurableConstants.PROMOTION_REQUEST_STATUS.get(flow.getReqToStatus()));
			}
		}

		
	}

	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
