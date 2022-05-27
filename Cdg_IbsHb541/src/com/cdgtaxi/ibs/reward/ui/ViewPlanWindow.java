package com.cdgtaxi.ibs.reward.ui;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
import com.cdgtaxi.ibs.master.model.LrtbRewardDetail;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewPlanWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(ViewPlanWindow.class);

	private LrtbRewardMaster plan;
	private Listbox detailList;

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
			detailList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public ViewPlanWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		logger.info("Plan No = " + planNo);
		plan = businessHelper.getRewardBusiness().getPlan(planNo);
	}

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Label planNameLbl = (Label) getFellow("planNameLbl");
		planNameLbl.setValue(plan.getRewardPlanName());

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
			detailList.appendChild(item);
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

}
