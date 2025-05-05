package com.cdgtaxi.ibs.reward.ui;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;

@SuppressWarnings("serial")
public class CreatePlanWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreatePlanWindow.class);

	private Textbox planTB;

	public void onCreate() {
		planTB = (Textbox) getFellow("planTB");
		planTB.focus();
	}

	public void createPlan() throws InterruptedException {
		try {
			this.checkNameUsed();
			
			String planName = planTB.getValue();
			businessHelper.getRewardBusiness().createPlan(planName);
			Messagebox.show("Loyalty Plan has been successfully created", "Create Loyalty Plan",
					Messagebox.OK, Messagebox.INFORMATION);
			MasterSetup.getRewardsManager().refresh();
			Executions.getCurrent().sendRedirect("");
		}
		catch (WrongValueException wve){
			throw wve;
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
	
	private void checkNameUsed(){
		LrtbRewardMaster example = new LrtbRewardMaster();
		example.setRewardPlanName(planTB.getValue());
		if(this.businessHelper.getGenericBusiness().isExists(example))
			throw new WrongValueException(planTB, "Name has been used");
	}
}
