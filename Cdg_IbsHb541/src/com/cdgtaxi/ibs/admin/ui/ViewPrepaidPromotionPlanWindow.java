package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.google.common.base.Strings;

@SuppressWarnings("serial")
public class ViewPrepaidPromotionPlanWindow extends CommonWindow implements AfterCompose {

	private Label 
		promoCodeLabel, cashbackLabel, effDateFromLabel,
		effDateToLabel, remarksLabel,
		createdByLabel, createdTimeLabel, createdDateLabel, 
		lastUpdatedDateLabel, lastUpdatedByLabel, lastUpdatedTimeLabel;
	private Label message;
	private String msg;
	private MstbPromotionCashPlus promotionCashPlus;
	
	private static final Logger logger = Logger.getLogger(ViewPrepaidPromotionPlanWindow.class);
	
	@SuppressWarnings("unchecked")
	public ViewPrepaidPromotionPlanWindow(){
		Map<Object, Object> map = Executions.getCurrent().getArg();
		String promoCode = (String)map.get("promoCode");
		msg = (String) map.get("message");
		promotionCashPlus = (MstbPromotionCashPlus)this.businessHelper.getAdminBusiness().getPromotionCashPlus(promoCode);
	}
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
	
		promoCodeLabel.setValue(promotionCashPlus.getPromoCode());
		cashbackLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(promotionCashPlus.getCashplus()));
		effDateFromLabel.setValue(DateUtil.convertDateToStrWithGDFormat(promotionCashPlus.getEffectiveDtFrom()));
		effDateToLabel.setValue(DateUtil.convertDateToStrWithGDFormat(promotionCashPlus.getEffectiveDtTo()));
		remarksLabel.setValue(promotionCashPlus.getRemarks());
		createdByLabel.setValue(promotionCashPlus.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertDateToStr(promotionCashPlus.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertDateToStr(promotionCashPlus.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		if(promotionCashPlus.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(promotionCashPlus.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(promotionCashPlus.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(promotionCashPlus.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(promotionCashPlus.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(promotionCashPlus.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		if(!Strings.isNullOrEmpty(msg)){
			message.setValue(msg);
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
