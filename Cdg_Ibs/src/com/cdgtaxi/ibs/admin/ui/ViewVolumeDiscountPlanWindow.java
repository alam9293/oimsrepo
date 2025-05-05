package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbVolDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewVolumeDiscountPlanWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(ViewVolumeDiscountPlanWindow.class);

	private MstbVolDiscMaster plan;
	private Listbox detailList;
	private Label planNameLbl;

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
			
			detailList.appendChild(item);
		}
		
	}

	@SuppressWarnings("unchecked")
	public ViewVolumeDiscountPlanWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		logger.info("Plan No = " + planNo);
		plan = businessHelper.getAdminBusiness().getVolumeDiscountPlan(planNo);
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

	public void afterCompose() {
		Components.wireVariables(this, this);
		
		planNameLbl.setValue(plan.getVolumeDiscountPlanName());

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

			detailList.appendChild(item);
		}

	}
}
