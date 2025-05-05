package com.cdgtaxi.ibs.reward.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.RewardPlanInEffectException;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings({"serial", "unchecked"})
public class ManagePlanWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ManagePlanWindow.class);

	private Listbox resultList;
	private Listitem itemTemplate;

	public void onCreate(CreateEvent ce) throws Exception {
		resultList = (Listbox) getFellow("resultList");
		itemTemplate = (Listitem) resultList.getItems().get(0);
		itemTemplate.detach();
		refresh();
		
		if(!this.checkUriAccess(Uri.CREATE_LOYALTY_PLAN))
			((Button)this.getFellow("createPlanBtn")).setDisabled(true);
	}

	public void viewPlan() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			LrtbRewardMaster plan = (LrtbRewardMaster)selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planNo", plan.getRewardPlanNo());
			
			if(this.checkUriAccess(Uri.EDIT_LOYALTY_PLAN))
				forward(Uri.EDIT_LOYALTY_PLAN, map);
			else if(this.checkUriAccess(Uri.VIEW_LOYALTY_PLAN))
				forward(Uri.VIEW_LOYALTY_PLAN, map);
			else{
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
						"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		} finally {
			resultList.clearSelection();
		}
	}

	public void createPlan() throws InterruptedException {
		try {
			forward(Uri.CREATE_LOYALTY_PLAN, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}

	public void deletePlan(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_LOYALTY_PLAN)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this plan?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		List<LrtbRewardMaster> plans = new ArrayList<LrtbRewardMaster>();
		plans.add((LrtbRewardMaster) item.getValue());
		try {
			businessHelper.getRewardBusiness().deletePlans(plans);
			item.detach();
			Messagebox.show("Plan has been successfully deleted", "Delete Loyalty Plan",
					Messagebox.OK, Messagebox.INFORMATION);
			MasterSetup.getRewardsManager().refresh();
		} catch (RewardPlanInEffectException e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void deletePlans() throws InterruptedException {
		try {
			List<LrtbRewardMaster> plans = new ArrayList<LrtbRewardMaster>();
			for (Object o : resultList.getItems()) {
				Listitem item = (Listitem) o;
				Checkbox checkbox = (Checkbox) ((Listcell) item.getLastChild()).getFirstChild();
				if (checkbox.isChecked()) {
					plans.add((LrtbRewardMaster) item.getValue());
				}
			}

			businessHelper.getRewardBusiness().deletePlans(plans);
			Messagebox.show("Loyalty Plans have been successfully deleted", "Delete Loyalty Plans",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} catch (RewardPlanInEffectException ex) {
			Messagebox.show(ex.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() throws InterruptedException {	
		resultList.getItems().clear();
		List<LrtbRewardMaster> plans = businessHelper.getRewardBusiness().getPlans();

		if (plans.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
		} else {
			for (LrtbRewardMaster plan : plans) {
				Listitem item = (Listitem) itemTemplate.clone();
				item.setValue(plan);
				List<Listcell> cells = item.getChildren();
				cells.get(0).setLabel(plan.getRewardPlanName());
				if(plan.getLrtbRewardDetails().size()<=0){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageLoyaltyPlanWindow.deletePlan(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					cells.get(1).appendChild(deleteImage);
				}else{
					cells.get(1).setLabel("");
				}
				resultList.appendChild(item);
			}
		}
	}
}
