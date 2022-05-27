package com.cdgtaxi.ibs.admin.ui;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;

@SuppressWarnings("serial")
public class CreateVolumeDiscountPlanWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateVolumeDiscountPlanWindow.class);

	private Textbox planTB;

	public void onCreate() {
		planTB = (Textbox) getFellow("planTB");
		planTB.focus();
	}

	public void createPlan() throws InterruptedException {
		MstbVolDiscMaster plan = new MstbVolDiscMaster();
		plan.setVolumeDiscountPlanName(planTB.getValue());

		try {
			businessHelper.getAdminBusiness().createVolumeDiscountPlan(plan, getUserLoginIdAndDomain());
			MasterSetup.getVolumeDiscountManager().refresh();
			Messagebox.show("Volume Discount Plan has been successfully created", "Create Volume Discount Plan",
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
