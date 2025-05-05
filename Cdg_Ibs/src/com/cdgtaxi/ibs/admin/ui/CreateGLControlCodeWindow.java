package com.cdgtaxi.ibs.admin.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

@SuppressWarnings("serial")
public class CreateGLControlCodeWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateGLControlCodeWindow.class);

	private Combobox entityCB;
	private Textbox codeTB;

	public void onCreate() {
		codeTB = (Textbox) getFellow("codeTB");
		entityCB = (Combobox) getFellow("entityCB");

		// populate Entity list
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			entityCB.appendChild(newComboitem(entity, entity.getEntityName()));
		}

		entityCB.focus();
	}

	public void create() throws InterruptedException {
		entityCB.getValue();

		FmtbArContCodeMaster code = new FmtbArContCodeMaster();
		code.setArControlCode(codeTB.getValue());
		code.setFmtbEntityMaster((FmtbEntityMaster) entityCB.getSelectedItem().getValue());

		try {
			businessHelper.getAdminBusiness().createGLControlCode(code, getUserLoginIdAndDomain());
			MasterSetup.getEntityManager().refresh();
			Messagebox.show("GL Control Code has been successfully created", "Create GL Control Code",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
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
