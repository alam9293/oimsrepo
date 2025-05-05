package com.cdgtaxi.ibs.admin.ui;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromoReqFlow;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class ViewPromotionPlanWindow extends CommonWindow implements AfterCompose {

	private Label 
		typeLabel, productTypeLabel,
		promoTypeLabel, promoValueLabel, effDateFromLabel,
		effDateToLabel, jobTypeLabel, vehicleModelLabel,
		createdByLabel, createdTimeLabel, createdDateLabel, 
		lastUpdatedDateLabel, lastUpdatedByLabel, lastUpdatedTimeLabel,
		effCutoffDateLabel;
	private MstbPromotion promotion;
	private Label message;
	private Row messageRow;
	private String msg, type, promoType;
	private boolean isPending;
	private CapsTextbox nameLabel;
	private static final Logger logger = Logger.getLogger(ViewPromotionPlanWindow.class);
	PmtbProductType productType = null;
	private BigDecimal promoValue;
	Timestamp effDateFrom, effDateTo;
	Date effCutoffDate;
	MstbMasterTable jobType = null, vehicleModel= null;
	MstbPromoDetail promoDetail = null;
	
	@SuppressWarnings("unchecked")
	public ViewPromotionPlanWindow(){
		Map map = Executions.getCurrent().getArg();
		Integer promoNo = (Integer)map.get("promoNo");
		msg = (String) map.get("message");
		isPending = (Boolean) map.get("pending");
		promotion = (MstbPromotion)this.businessHelper.getAdminBusiness().getPromotion(promoNo);
	}
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		MstbPromoReq lastPromoReq = promotion.getLastPromoReq();
		MstbPromoReqFlow lastPromoReqFlow = lastPromoReq.getLastPromoReqFlow();
		promoDetail = lastPromoReqFlow.getToPromoDetail();

		
		productType =promoDetail.getPmtbProductType();
		nameLabel.setValue(promoDetail.getName());
		effDateFromLabel.setValue(DateUtil.convertDateToStr(promoDetail.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
		effDateToLabel.setValue(DateUtil.convertDateToStr(promoDetail.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
		promoValueLabel.setValue(StringUtil.bigDecimalToString(promoDetail.getPromoValue(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		createdByLabel.setValue(promotion.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertDateToStr(promotion.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertDateToStr(promotion.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		if(promotion.getLastUpdatedBy()!=null) lastUpdatedByLabel.setValue(promotion.getLastUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(promotion.getLastUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(promotion.getLastUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(promotion.getLastUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(promotion.getLastUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		if(promoDetail.getPmtbProductType()==null) productTypeLabel.setValue("ALL");
		else productTypeLabel.setValue(promoDetail.getPmtbProductType().getName());
		
		typeLabel.setValue(NonConfigurableConstants.PROMO_ACCT_TYPE.get(promoDetail.getType()));
		promoTypeLabel.setValue(NonConfigurableConstants.PROMO_TYPE.get(promoDetail.getPromoType()));
		
		if(promoDetail.getMstbMasterTableByJobType()==null) jobTypeLabel.setValue("ALL");
		else jobTypeLabel.setValue(promoDetail.getMstbMasterTableByJobType().getMasterValue());
		
		if(promoDetail.getMstbMasterTableByVehicleModel()==null) vehicleModelLabel.setValue("ALL");
		else vehicleModelLabel.setValue(promoDetail.getMstbMasterTableByVehicleModel().getMasterValue());
		
		effCutoffDateLabel.setValue(DateUtil.convertDateToStr(promoDetail.getEffectiveCutoffDate(), DateUtil.GLOBAL_DATE_FORMAT));
		
		
		if(msg!=null && !"".equals(msg)){
			message.setValue(msg);
		}
		
		//set value
		type =promoDetail.getType();
		promoValue=(promoDetail.getPromoValue());
		promoType =promoDetail.getPromoType();
		effDateFrom=promoDetail.getEffectiveDtFrom();
		effDateTo=promoDetail.getEffectiveDtTo();
		effCutoffDate=promoDetail.getEffectiveCutoffDate();
		if(promoDetail.getMstbMasterTableByJobType()!=null)
			jobType = promoDetail.getMstbMasterTableByJobType();
		if(promoDetail.getMstbMasterTableByVehicleModel()!=null)
			vehicleModel=promoDetail.getMstbMasterTableByVehicleModel();
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
	@SuppressWarnings("unchecked")
	public void savePlan() throws InterruptedException{
		
		try{
			this.displayProcessing();
			
			String name = nameLabel.getValue();
			
			if(this.businessHelper.getAdminBusiness().hasDuplicateName(promotion.getPromoNo(), name)){
				throw new WrongValueException(nameLabel, "Name has been used.");
			}
			
			promoDetail.setName(name);
			this.businessHelper.getGenericBusiness().update(promoDetail, getUserLoginIdAndDomain());
			MasterSetup.getPromotionManager().refresh();
			
			//Show result
			Messagebox.show("Promotion plan edited.", "Edit Promotion Plan", Messagebox.OK, Messagebox.INFORMATION);
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
}
