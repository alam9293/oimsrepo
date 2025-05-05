package com.cdgtaxi.ibs.admin.ui;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeMaster;

@SuppressWarnings("serial")
public class CreateAdminFeePlanWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateAdminFeePlanWindow.class);

	private Textbox planTB;

	public void onCreate() {
		planTB = (Textbox) getFellow("planTB");
		planTB.focus();
	}

	public void createPlan() throws InterruptedException {
		MstbAdminFeeMaster adminFee = new MstbAdminFeeMaster();
		adminFee.setAdminFeePlanName(planTB.getValue());

		try {
			businessHelper.getAdminBusiness().createAdminFeePlan(adminFee, getUserLoginIdAndDomain());
			MasterSetup.getAdminFeeManager().refresh();
			Messagebox.show("Admin Fee Plan has been successfully created", "Create Admin Fee Plan",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} 
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateNameError e) {
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

	@Override
	public void refresh() throws InterruptedException {
	}
}
