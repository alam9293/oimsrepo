package com.cdgtaxi.ibs.admin.ui;

import java.io.InputStream;
import java.util.Map;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewEntityWindow extends CommonWindow implements AfterCompose{

	private FmtbEntityMaster entity;
	
	private Label entityCodeLabel,entityNameLabel,rcbNoLabel,gstNoLabel,
				  currencyLabel,blockLabel,unitLabel,streetLabel,buildingLabel,
				  areaLabel,countryLabel,cityLabel,stateLabel,postalLabel,
				  telephoneLabel,emailLabel,logoNoImageLabel;
	
	private Image logoImage;

	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
				  lastUpdatedByLabel, lastUpdatedDateLabel, 
				  lastUpdatedTimeLabel;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Map params = Executions.getCurrent().getArg();
		Integer entityNo =  (Integer) params.get("entityNo");
		entity = businessHelper.getAdminBusiness().getEntity(entityNo);
		
		entityCodeLabel.setValue(entity.getEntityCode());
		entityNameLabel.setValue(entity.getEntityName());
		rcbNoLabel.setValue(entity.getEntityRcbNo());
		gstNoLabel.setValue(entity.getEntityGstNo());
		currencyLabel.setValue(entity.getCurrencyCode());
		streetLabel.setValue(entity.getEntityStreet());
		countryLabel.setValue(ConfigurableConstants.getMasters(ConfigurableConstants.COUNTRY_MASTER_CODE).get(entity.getMstbMasterTable().getMasterCode()));
		cityLabel.setValue(entity.getEntityCity());
		stateLabel.setValue(entity.getEntityState());
		postalLabel.setValue(entity.getEntityPostal());
		
		if(entity.getEntityBlock()!=null)
			blockLabel.setValue(entity.getEntityBlock());
		else
			blockLabel.setValue("-");
		if(entity.getEntityUnit()!=null)
			unitLabel.setValue(entity.getEntityUnit());
		else
			unitLabel.setValue("-");
		if(entity.getEntityBuilding()!=null)
			buildingLabel.setValue(entity.getEntityBuilding());
		else
			buildingLabel.setValue("-");
		if(entity.getEntityArea()!=null)
			areaLabel.setValue(entity.getEntityArea());
		else
			areaLabel.setValue("-");
		if(entity.getEntityTel()!=null)
			telephoneLabel.setValue(entity.getEntityTel());
		else
			telephoneLabel.setValue("-");
		if(entity.getEntityEmail()!=null)
			emailLabel.setValue(entity.getEntityEmail());
		else
			emailLabel.setValue("-");
		
		try {
			if (entity.getLogo() != null) {
				InputStream is = entity.getLogo().getBinaryStream();
				logoImage.setContent(new AImage(null, is));
				logoNoImageLabel.setVisible(false);
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(entity.getCreatedDt()!=null)createdByLabel.setValue(entity.getCreatedBy());
		else createdByLabel.setValue("-");
		if(entity.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(entity.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(entity.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(entity.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(entity.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(entity.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(entity.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(entity.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(entity.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(entity.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}
