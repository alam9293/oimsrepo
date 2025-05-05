package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
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
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbVolDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditVolumeDiscountPlanWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditVolumeDiscountPlanWindow.class);

	private MstbVolDiscMaster plan;
	private Textbox planNameTB;
	private Listbox detailList;
	private Button save,createBtn;

	@Override
	public void refresh() throws InterruptedException {
		plan = this.businessHelper.getAdminBusiness().getVolumeDiscountPlan(plan.getVolumeDiscountPlanNo());

		detailList.getItems().clear();

		Set<MstbVolDiscDetail> details = plan.getMstbVolDiscDetails();
		Set<MstbVolDiscDetail> sortedDetails = new TreeSet<MstbVolDiscDetail>(
				new Comparator<MstbVolDiscDetail>() {
					public int compare(MstbVolDiscDetail d1, MstbVolDiscDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbVolDiscDetail detail : sortedDetails) {
			Listitem item = new Listitem();
			item.setValue(detail);
			String rewardsType = NonConfigurableConstants.VOLUME_DISCOUNT_TYPE.get(
					detail.getVolumeDiscountType());
			item.appendChild(new Listcell(rewardsType));
			item.appendChild(new Listcell(DateUtil.convertDateToStr(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT)));
			
			if(DateUtil.getCurrentDate().compareTo(detail.getEffectiveDt()) <=0){
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("editVolumeDiscountPlanWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				item.appendChild(imageCell);
			}else{
				item.appendChild(new Listcell());
			}
			
			//			item.appendChild(new Listcell(DateUtil.convertDateToStr(
			//					detail.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT)));
			detailList.appendChild(item);
		}
		
	}
	
	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_VOLUME_DISCOUNT_PLAN_DETAIL)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			MstbVolDiscDetail detail = (MstbVolDiscDetail)item.getValue();
			
			this.businessHelper.getAdminBusiness().deleteVolumeDiscountPlanDetail(detail);
			plan = this.businessHelper.getAdminBusiness().getVolumeDiscountPlan(plan.getVolumeDiscountPlanNo());
			MasterSetup.getVolumeDiscountManager().refresh();
			item.detach();
			Messagebox.show("The Volume Discount Plan Detail has been successfully deleted", "Delete Volume Discount Plan Detail",
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

	@SuppressWarnings("unchecked")
	public EditVolumeDiscountPlanWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		logger.info("Plan No = " + planNo);
		plan = businessHelper.getAdminBusiness().getVolumeDiscountPlan(planNo);
	}

	public void updatePlan() throws InterruptedException {
		plan.setVolumeDiscountPlanName(planNameTB.getValue());

		try {
			businessHelper.getAdminBusiness().updateVolumeDiscountPlan(plan, getUserLoginIdAndDomain());
			MasterSetup.getVolumeDiscountManager().refresh();
			Messagebox.show(
					"Plan has been successfully saved.", "Save Plan",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
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

	public void editDetail() throws InterruptedException {
		try {
			Listitem selectedItem = detailList.getSelectedItem();
			MstbVolDiscDetail detail = (MstbVolDiscDetail) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planDetailNo", detail.getVolumeDiscountPlanDetailNo());
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(detail.getEffectiveDt());
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_VOLUME_DISCOUNT_PLAN_DETAIL))
					forward(Uri.EDIT_VOLUME_DISCOUNT_PLAN_DETAIL, map);
				else if(this.checkUriAccess(Uri.VIEW_VOLUME_DISCOUNT_PLAN_DETAIL))
					forward(Uri.VIEW_VOLUME_DISCOUNT_PLAN_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_VOLUME_DISCOUNT_PLAN_DETAIL))
					forward(Uri.VIEW_VOLUME_DISCOUNT_PLAN_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
		} 
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		finally {
			detailList.clearSelection();
		}
	}

	public void addDetail() throws InterruptedException {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planNo", plan.getVolumeDiscountPlanNo());
			forward(Uri.CREATE_VOLUME_DISCOUNT_PLAN_DETAIL, map);
		} 
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		boolean flag = true;
		
		planNameTB.setValue(plan.getVolumeDiscountPlanName());

		Set<MstbVolDiscDetail> details = plan.getMstbVolDiscDetails();
		Set<MstbVolDiscDetail> sortedDetails = new TreeSet<MstbVolDiscDetail>(
				new Comparator<MstbVolDiscDetail>() {
					public int compare(MstbVolDiscDetail d1, MstbVolDiscDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbVolDiscDetail detail : sortedDetails) {
			Listitem item = new Listitem();
			item.setValue(detail);
			String rewardsType = NonConfigurableConstants.VOLUME_DISCOUNT_TYPE.get(
					detail.getVolumeDiscountType());
			item.appendChild(new Listcell(rewardsType));
			item.appendChild(new Listcell(DateUtil.convertDateToStr(
					detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT)));
			
			if(DateUtil.getCurrentDate().compareTo(detail.getEffectiveDt()) <=0){
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("editVolumeDiscountPlanWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				item.appendChild(imageCell);
			}else{
				item.appendChild(new Listcell());
				flag = false;
			}
			detailList.appendChild(item);
		}
		if(!flag){
			//planNameTB.setDisabled(true);
			//save.setVisible(false);
		}
		if(!this.checkUriAccess(Uri.CREATE_VOLUME_DISCOUNT_PLAN_DETAIL)){
			createBtn.setDisabled(true);
		}
	}
}
