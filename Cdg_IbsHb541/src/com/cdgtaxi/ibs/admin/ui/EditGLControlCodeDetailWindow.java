package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeDetail;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditGLControlCodeDetailWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditGLControlCodeDetailWindow.class);

	private final FmtbArContCodeMaster code;
	private final FmtbArContCodeDetail detail;

	private Datebox startDateDB;
	private Textbox costCentreTB;
	private Textbox descriptionTB;
	private Intbox glAccountIB;
	private Combobox taxTypeCB;
	
	private Label controlCodeLabel;
	
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;

	@SuppressWarnings("unchecked")
	public EditGLControlCodeDetailWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planDetailNo =  (Integer) params.get("codeDetailNo");
		logger.info("Plan Detail No = " + planDetailNo);
		detail = businessHelper.getAdminBusiness().getGLControlCodeDetail(planDetailNo);
		code = detail.getFmtbArContCodeMaster();
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		controlCodeLabel.setValue(code.getArControlCode());
		startDateDB.setRawValue(detail.getEffectiveDt());
		costCentreTB.setValue(detail.getCostCentre());
		descriptionTB.setValue(detail.getDescription());
		glAccountIB.setValue(detail.getGlAccount());

		// populate Tax Type list
		Map<String, String> masters = ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE);
		String selectedTaxType = null;
		if (detail.getMstbMasterTable() != null) {
			selectedTaxType = detail.getMstbMasterTable().getMasterCode();
		}
		taxTypeCB.appendChild(newComboitem(null, "-"));
		taxTypeCB.setSelectedIndex(0);
		for (Entry<String, String> entry : masters.entrySet()) {
			Comboitem item = newComboitem(entry.getKey(), entry.getValue());
			taxTypeCB.appendChild(item);
			if (entry.getKey().equals(selectedTaxType)) {
				taxTypeCB.setSelectedItem(item);
			}
		}
		
		if(detail.getCreatedDt()!=null)createdByLabel.setValue(detail.getCreatedBy());
		else createdByLabel.setValue("-");
		if(detail.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(detail.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(detail.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(detail.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}

	public void updateDetail() throws InterruptedException {
		taxTypeCB.getValue();

		detail.setCostCentre(costCentreTB.getValue());
		detail.setDescription(descriptionTB.getValue());
		detail.setGlAccount(glAccountIB.getValue());
		if (taxTypeCB.getSelectedItem() != null) {
			String masterCode = (String) taxTypeCB.getSelectedItem().getValue();
			detail.setMstbMasterTable(ConfigurableConstants.getMasterTable(
					ConfigurableConstants.TAX_TYPE, masterCode));
		}
		detail.setEffectiveDt(DateUtil.convertDateToTimestamp(startDateDB.getValue()));

		try {
			businessHelper.getAdminBusiness().updateGLControlCodeDetail(detail, getUserLoginIdAndDomain());
			Messagebox.show(
					"Control Code detail has been successfully saved.", "Save Control Code Detail",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DataValidationError ex) {
			Messagebox.show(ex.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
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
