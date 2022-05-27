package com.cdgtaxi.ibs.reward.ui;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.LrtbRewardDetail;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class EditPlanWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditPlanWindow.class);

	private LrtbRewardMaster plan;
	private Listbox detailList;
	private CapsTextbox planNameTextBox;

	@Override
	public void refresh() throws InterruptedException {
		plan = businessHelper.getRewardBusiness().getPlan(plan.getRewardPlanNo());

		detailList.getItems().clear();

		Set<LrtbRewardDetail> details = plan.getLrtbRewardDetails();
//		Set<LrtbRewardDetail> sortedDetails = new TreeSet<LrtbRewardDetail>(
//				new Comparator<LrtbRewardDetail>() {
//					public int compare(LrtbRewardDetail d1, LrtbRewardDetail d2) {
//						return d1.getEffectiveDtFrom().compareTo(d2.getEffectiveDtFrom());
//					}
//				});
//		sortedDetails.addAll(details);

		for (LrtbRewardDetail detail : details) {
			Listitem item = new Listitem();
			item.setValue(detail);
			String rewardsType = NonConfigurableConstants.REWARDS_TYPE.get(
					detail.getRewardType());
			item.appendChild(new Listcell(rewardsType));
			item.appendChild(newListcell(detail.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
			item.appendChild(newListcell(detail.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
			
			if(DateUtil.getCurrentDate().compareTo(detail.getEffectiveDtFrom()) <=0){
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("viewLoyaltyPlanWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				item.appendChild(imageCell);
			}else{
				item.appendChild(new Listcell());
			}
			
			detailList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public EditPlanWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		logger.info("Plan No = " + planNo);
		plan = businessHelper.getRewardBusiness().getPlan(planNo);
	}

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		planNameTextBox.setValue(plan.getRewardPlanName());

		Set<LrtbRewardDetail> details = plan.getLrtbRewardDetails();
//		Set<LrtbRewardDetail> sortedDetails = new TreeSet<LrtbRewardDetail>(
//				new Comparator<LrtbRewardDetail>() {
//					public int compare(LrtbRewardDetail d1, LrtbRewardDetail d2) {
//						return d1.getEffectiveDtFrom().compareTo(d2.getEffectiveDtFrom());
//					}
//				});
//		sortedDetails.addAll(details);

		for (LrtbRewardDetail detail : details) {
			Listitem item = new Listitem();
			item.setValue(detail);
			String rewardsType = NonConfigurableConstants.REWARDS_TYPE.get(
					detail.getRewardType());
			item.appendChild(new Listcell(rewardsType));
			item.appendChild(newListcell(detail.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
			item.appendChild(newListcell(detail.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
			
			if(DateUtil.getCurrentDate().compareTo(detail.getEffectiveDtFrom()) <=0){
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("viewLoyaltyPlanWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				item.appendChild(imageCell);
			}else{
				item.appendChild(new Listcell());
			}
			detailList.appendChild(item);
		}
		
		if(!this.checkUriAccess(Uri.ADD_LOYALTY_PLAN_DETAIL))
			((Button)this.getFellow("createDetailBtn")).setDisabled(true);
	}
	
	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_LOYALTY_PLAN_DETAIL)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			LrtbRewardDetail detail = (LrtbRewardDetail)item.getValue();
			this.businessHelper.getGenericBusiness().delete(detail);
			
			item.detach();
			Messagebox.show("Loyalty plan detail has been successfully deleted", "Delete Loyalty Plan Detail",
					Messagebox.OK, Messagebox.INFORMATION);
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void editDetail() throws InterruptedException {
		try {
			Listitem selectedItem = detailList.getSelectedItem();
			LrtbRewardDetail detail = (LrtbRewardDetail) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planDetailNo", detail.getRewardPlanDetailNo());
			
			Calendar currentCalendar = Calendar.getInstance();
			Timestamp currentTime = new Timestamp(currentCalendar.getTimeInMillis());
			
			if(detail.getEffectiveDtFrom().compareTo(currentTime) < 0){
				if(this.checkUriAccess(Uri.VIEW_LOYALTY_PLAN_DETAIL))
					forward(Uri.VIEW_LOYALTY_PLAN_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
			else{
				if(this.checkUriAccess(Uri.EDIT_LOYALTY_PLAN_DETAIL))
					forward(Uri.EDIT_LOYALTY_PLAN_DETAIL, map);
				else if(this.checkUriAccess(Uri.VIEW_LOYALTY_PLAN_DETAIL))
					forward(Uri.VIEW_LOYALTY_PLAN_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		} finally {
			detailList.clearSelection();
		}
	}

	public void addDetail() throws InterruptedException {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planNo", plan.getRewardPlanNo());
			forward(Uri.ADD_LOYALTY_PLAN_DETAIL, map);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updatePlan() throws InterruptedException{
		try{
			String newName = planNameTextBox.getValue();
			LrtbRewardMaster rewardExample = new LrtbRewardMaster();
			rewardExample.setRewardPlanName(newName);
			List<LrtbRewardMaster> results = this.businessHelper.getGenericBusiness().getByExample(rewardExample);
			
			if(results.isEmpty() == false)
				if(results.get(0).getRewardPlanNo().intValue() != plan.getRewardPlanNo().intValue())
					throw new WrongValueException(planNameTextBox, "Name has been used.");
			
			plan.setRewardPlanName(newName);
			this.businessHelper.getGenericBusiness().update(plan, getUserLoginIdAndDomain());
			
			MasterSetup.getRewardsManager().refresh();
			
			Messagebox.show(
					"Plan name successfully updated.", "Update Plan Name",
					Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect("");
		}
		catch(WrongValueException wve){
			throw wve;
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
