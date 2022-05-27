package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
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
public class CreateGLControlCodeDetailWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateGLControlCodeDetailWindow.class);

	private final FmtbArContCodeMaster master;
	private final FmtbArContCodeDetail detail;

	private Datebox startDateDB;
	private Textbox costCentreTB;
	private Textbox descriptionTB;
	private Intbox glAccountIB;
	private Combobox taxTypeCB;

	@SuppressWarnings("unchecked")
	public CreateGLControlCodeDetailWindow() {
		logger.info("New Control Code Detail");

		Map params = Executions.getCurrent().getArg();
		Integer codeNo =  (Integer) params.get("codeNo");
		master = businessHelper.getAdminBusiness().getGLControlCode(codeNo);

		detail = new FmtbArContCodeDetail();
		detail.setFmtbArContCodeMaster(master);
	}

	public void onCreate() {
		Label codeLabel = (Label) getFellow("controlCodeLabel");
		codeLabel.setValue(master.getArControlCode());

		startDateDB = (Datebox) getFellow("startDateDB");
		costCentreTB = (Textbox) getFellow("costCentreTB");
		descriptionTB = (Textbox) getFellow("descriptionTB");
		glAccountIB = (Intbox) getFellow("glAccountIB");
		taxTypeCB = (Combobox) getFellow("taxTypeCB");

		// populate Tax Type list
		Map<String, String> masters = ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE);
		taxTypeCB.appendChild(newComboitem(null, "-"));
		taxTypeCB.setSelectedIndex(0);
		for (Entry<String, String> entry : masters.entrySet()) {
			taxTypeCB.appendChild(newComboitem(entry.getKey(), entry.getValue()));
		}
	}

	public void saveDetail() throws InterruptedException {
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
			businessHelper.getAdminBusiness().saveGLControlCodeDetail(detail, getUserLoginIdAndDomain());
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
