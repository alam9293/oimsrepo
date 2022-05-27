package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DuplicateEffectiveDateError;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditGLTaxWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditGLTaxWindow.class);

	private final FmtbTaxCode tax;
	private Combobox entityCB;
	private Textbox codeTB;
	private Textbox costCentreTB;
	private Intbox glCodeTB;
	private Combobox taxTypeCB;
	private Decimalbox taxRateDB;
	private Datebox effectiveDateDB;
	
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
				  lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;

	@Override
	public void refresh() throws InterruptedException {
	}

	@SuppressWarnings("unchecked")
	public EditGLTaxWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer taxCodeNo =  (Integer) params.get("taxCodeNo");
		logger.info("Tax Code No = " + taxCodeNo);
		tax = businessHelper.getAdminBusiness().getGLTax(taxCodeNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		if(tax.getGlTaxCode()!=null && tax.getGlTaxCode().trim().length()==0)
			codeTB.setText(" ");
		else if(tax.getGlTaxCode()==null)
			codeTB.setText(" ");
		else
			codeTB.setValue(tax.getGlTaxCode());
		costCentreTB.setValue(tax.getCostCentre());
		glCodeTB.setValue(new Integer(tax.getGlCode())); //glCode should be numeric
		taxRateDB.setValue(tax.getTaxRate());
		effectiveDateDB.setRawValue(tax.getEffectiveDate());

		// populate Entity list
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			Comboitem item = newComboitem(entity, entity.getEntityName());
			entityCB.appendChild(item);
			if (tax.getFmtbEntityMaster().getEntityNo().equals(entity.getEntityNo())) {
				entityCB.setSelectedItem(item);
			}
		}

		// populate Tax Type list
		Map<String, String> masters = ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE);
		for (Entry<String, String> entry : masters.entrySet()) {
			Comboitem item = newComboitem(entry.getKey(), entry.getValue());
			taxTypeCB.appendChild(item);
			if (tax.getMstbMasterTable() != null
					&& tax.getMstbMasterTable().getMasterCode().equals(entry.getKey())) {
				taxTypeCB.setSelectedItem(item);
			}
		}
		
		if(tax.getCreatedDt()!=null)createdByLabel.setValue(tax.getCreatedBy());
		else createdByLabel.setValue("-");
		if(tax.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(tax.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(tax.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(tax.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(tax.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(tax.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(tax.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(tax.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(tax.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(tax.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}

	public void update() throws InterruptedException {
		entityCB.getValue();
		taxTypeCB.getValue();

		tax.setFmtbEntityMaster((FmtbEntityMaster) entityCB.getSelectedItem().getValue());
		tax.setGlTaxCode(codeTB.getValue()==null ? "" : codeTB.getValue());
		tax.setGlCode(glCodeTB.getValue()+"");
		if (taxTypeCB.getSelectedItem() != null) {
			String masterCode = (String) taxTypeCB.getSelectedItem().getValue();
			tax.setMstbMasterTable(ConfigurableConstants.getMasterTable(
					ConfigurableConstants.TAX_TYPE, masterCode));
		}
		tax.setCostCentre(costCentreTB.getValue());
		tax.setTaxRate(taxRateDB.getValue());
		tax.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(
				effectiveDateDB.getValue()));

		try {
			businessHelper.getAdminBusiness().updateGLTax(tax, getUserLoginIdAndDomain());
			MasterSetup.getGSTManager().refresh();
			Messagebox.show(
					"GL Tax Code has been successfully saved.", "Save GL Tax Code",
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
}
