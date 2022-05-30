package com.cdgtaxi.ibs.admin.ui;

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
public class CreateEntityWindow extends CommonWindow implements AfterCompose{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateEntityWindow.class);

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

	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	
	public void onCreate() {
		// populate Country list
		Map<String, String> masters = ConfigurableConstants.getMasters(ConfigurableConstants.COUNTRY_MASTER_CODE);
		Comboitem defaultEmptyItem = newComboitem(null, "-");
		countryCB.appendChild(defaultEmptyItem);
		for (Entry<String, String> entry : masters.entrySet()) {
			Comboitem item = newComboitem(entry.getKey(), entry.getValue());
			countryCB.appendChild(item);
			
			if(entry.getKey().equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG))
				countryCB.setSelectedItem(item);
		}
		if(countryCB.getSelectedItem() == null)
			countryCB.setSelectedItem(defaultEmptyItem);

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
		cityTB.setText(null);
		stateTB.setDisabled(isSGSelected);
		stateTB.setText(null);
	}
	
	public void create() throws InterruptedException {
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
			FmtbEntityMaster entity = new FmtbEntityMaster();
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
			
			businessHelper.getAdminBusiness().createEntity(entity, getUserLoginIdAndDomain());
			MasterSetup.getEntityManager().refresh();
			Messagebox.show("Entity has been successfully created", "Create Entity",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
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
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(e.toString());
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
