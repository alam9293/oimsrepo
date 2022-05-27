package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
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
public class CreateGLTaxWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateGLTaxWindow.class);

	private Combobox entityCB;
	private Textbox codeTB;
	private Textbox costCentreTB;
	private Intbox glCodeTB;
	private Combobox taxTypeCB;
	private Decimalbox taxRateDB;
	private Datebox effectiveDateDB;

	public void onCreate() {
		entityCB = (Combobox) getFellow("entityCB");
		codeTB = (Textbox) getFellow("codeTB");
		costCentreTB = (Textbox) getFellow("costCentreTB");
		glCodeTB = (Intbox) getFellow("glCodeTB");
		taxTypeCB = (Combobox) getFellow("taxTypeCB");
		taxRateDB = (Decimalbox) getFellow("taxRateDB");
		effectiveDateDB = (Datebox) getFellow("effectiveDateDB");

		// populate Entity list
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			entityCB.appendChild(newComboitem(entity, entity.getEntityName()));
		}

		// populate Tax Type list
		Map<String, String> masters = ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE);
		for (Entry<String, String> entry : masters.entrySet()) {
			taxTypeCB.appendChild(newComboitem(entry.getKey(), entry.getValue()));
		}

		entityCB.focus();
	}

	public void create() throws InterruptedException {
		entityCB.getValue();
		taxTypeCB.getValue();

		FmtbTaxCode taxCode = new FmtbTaxCode();
		taxCode.setFmtbEntityMaster((FmtbEntityMaster) entityCB.getSelectedItem().getValue());
		taxCode.setGlTaxCode(codeTB.getValue()==null ? "" : codeTB.getValue());
		taxCode.setGlCode(glCodeTB.getValue()+"");
		if (taxTypeCB.getSelectedItem() != null) {
			String masterCode = (String) taxTypeCB.getSelectedItem().getValue();
			taxCode.setMstbMasterTable(ConfigurableConstants.getMasterTable(
					ConfigurableConstants.TAX_TYPE, masterCode));
		}
		taxCode.setCostCentre(costCentreTB.getValue());
		taxCode.setTaxRate(taxRateDB.getValue());
		taxCode.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(
				effectiveDateDB.getValue()));

		try {
			businessHelper.getAdminBusiness().createGLTax(taxCode, getUserLoginIdAndDomain());
			MasterSetup.getGSTManager().refresh();
			Messagebox.show("GL Tax Code has been successfully created", "Create GL Tax Code",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} 
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateEffectiveDateError ex) {
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
