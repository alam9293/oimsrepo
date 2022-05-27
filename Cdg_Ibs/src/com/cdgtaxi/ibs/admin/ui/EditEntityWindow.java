package com.cdgtaxi.ibs.admin.ui;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.lob.BlobImpl;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditEntityWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditEntityWindow.class);

	private final FmtbEntityMaster entity;
	private Textbox entityCodeTB;
	private Textbox entityNameTB;
	private Textbox rcbNoTB;
	private Textbox gstNoTB;
	private Textbox currencyTB;
	private Textbox blockTB;
	private Textbox unitTB;
	private Textbox streetTB;
	private Textbox buildingTB;
	private Textbox areaTB;
	private Combobox countryCB;
	private Textbox cityTB;
	private Textbox stateTB;
	private Textbox postalTB;
	private Textbox telephoneTB;
	private Textbox emailTB;
	private Label logoNoImageLabel;
	private Image logoImage;
	private Datebox effEndDateDB;
	
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;

	@Override
	public void refresh() throws InterruptedException {
	}

	@SuppressWarnings("unchecked")
	public EditEntityWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer entityNo =  (Integer) params.get("entityNo");
		logger.info("Entity No = " + entityNo);
		entity = businessHelper.getAdminBusiness().getEntity(entityNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		
		entityCodeTB.setRawValue(entity.getEntityCode());
		entityNameTB.setRawValue(entity.getEntityName());
		rcbNoTB.setRawValue(entity.getEntityRcbNo());
		gstNoTB.setRawValue(entity.getEntityGstNo());
		currencyTB.setRawValue(entity.getCurrencyCode());
		blockTB.setRawValue(entity.getEntityBlock());
		unitTB.setRawValue(entity.getEntityUnit());
		streetTB.setRawValue(entity.getEntityStreet());
		buildingTB.setRawValue(entity.getEntityBuilding());
		areaTB.setRawValue(entity.getEntityArea());
		cityTB.setRawValue(entity.getEntityCity());
		stateTB.setRawValue(entity.getEntityState());
		postalTB.setRawValue(entity.getEntityPostal());
		telephoneTB.setRawValue(entity.getEntityTel());
		emailTB.setRawValue(entity.getEntityEmail());
		if(MasterSetup.getEntityManager().isEntityActive(entity)==false){
			effEndDateDB.setDisabled(true);
			effEndDateDB.setRawValue(DateUtil.convertSqlDateToUtilDate(entity.getEffectiveEndDate()));
		}
		else
			effEndDateDB.setRawValue(DateUtil.convertSqlDateToUtilDate(entity.getEffectiveEndDate()));
		
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

		try {
			if (entity.getLogo() != null) {
				InputStream is = entity.getLogo().getBinaryStream();
				logoImage.setContent(new AImage(null, is));
				logoNoImageLabel.setVisible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// populate Country list
		Map<String, String> masters = ConfigurableConstants.getMasters(ConfigurableConstants.COUNTRY_MASTER_CODE);
		for (Entry<String, String> entry : masters.entrySet()) {
			Comboitem item = newComboitem(entry.getKey(), entry.getValue());
			countryCB.appendChild(item);
			if (entity.getMstbMasterTable() != null
					&& entity.getMstbMasterTable().getMasterCode().equals(entry.getKey())) {
				countryCB.setSelectedItem(item);
			}
		}
		this.checkCountry();
		
		entityCodeTB.focus();
	}

	public void checkCountry(){
		Comboitem selectedItem = countryCB.getSelectedItem();
		
		boolean isSGSelected = false;
		if(selectedItem.getValue()==null ||
				selectedItem.getValue().equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
			isSGSelected = true;
		}
		cityTB.setDisabled(isSGSelected);
		stateTB.setDisabled(isSGSelected);
		if(isSGSelected){
			cityTB.setText(null);
			stateTB.setText(null);
		}
		else {
			cityTB.setText(entity.getEntityCity());
			stateTB.setText(entity.getEntityState());
		}
	}
	
	public void update() throws InterruptedException {
		if(countryCB.getSelectedItem().getValue()==null ||
				countryCB.getSelectedItem().getValue().toString().length()==0)
			throw new WrongValueException(countryCB, "* Mandatory field");

		if(cityTB.isDisabled()==false)
			if(cityTB.getValue()==null || cityTB.getValue().length()==0)
				throw new WrongValueException(cityTB, "* Mandatory field");
		
		if(stateTB.isDisabled()==false)
			if(stateTB.getValue()==null || stateTB.getValue().length()==0)
				throw new WrongValueException(stateTB, "* Mandatory field");
		
		try {
			entity.setEntityName(entityNameTB.getValue());
			entity.setEntityCode(entityCodeTB.getValue());
			entity.setEntityRcbNo(rcbNoTB.getValue());
			entity.setEntityGstNo(gstNoTB.getValue());
			entity.setCurrencyCode(currencyTB.getValue());
			entity.setEntityBlock(blockTB.getValue());
			entity.setEntityUnit(unitTB.getValue());
			entity.setEntityStreet(streetTB.getValue());
			entity.setEntityBuilding(buildingTB.getValue());
			entity.setEntityArea(areaTB.getValue());
			entity.setEntityCity(cityTB.getValue());
			entity.setEntityState(stateTB.getValue());
			entity.setEntityPostal(postalTB.getValue());
			entity.setMstbMasterTable(ConfigurableConstants.getMasterTable(
					ConfigurableConstants.COUNTRY_MASTER_CODE,
					(String) countryCB.getSelectedItem().getValue()));
			entity.setEntityTel(telephoneTB.getValue());
			entity.setEntityEmail(emailTB.getValue());
			if (logoImage.getContent() != null) {
				Blob blob = new BlobImpl(logoImage.getContent().getByteData());
				entity.setLogo(blob);
			}
			entity.setEffectiveEndDate(DateUtil.convertUtilDateToSqlDate(effEndDateDB.getValue()));
			
			businessHelper.getAdminBusiness().updateEntity(entity, getUserLoginIdAndDomain());
			MasterSetup.getEntityManager().refresh();
			Messagebox.show(
					"Entity has been successfully saved.", "Save Entity",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
			
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DataValidationError e) {
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

	public void uploadImage() throws InterruptedException {
		try {
			Media media = Fileupload.get(true);
			if (media == null) {
				return;
			}
			logoNoImageLabel.setVisible(false);
			logoImage.setContent(new AImage(null, media.getByteData()));
		} 
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

}
