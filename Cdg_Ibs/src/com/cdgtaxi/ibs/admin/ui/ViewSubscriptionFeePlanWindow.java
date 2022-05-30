package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewSubscriptionFeePlanWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(EditSubscriptionFeePlanWindow.class);

	private MstbSubscFeeMaster plan;
	private Listbox detailList;
	private Label planNameLbl;

	@Override
	public void refresh() throws InterruptedException {
		plan = businessHelper.getAdminBusiness().getSubscriptionFeePlan(plan.getSubscriptionFeeNo());

		detailList.getItems().clear();

		Set<MstbSubscFeeDetail> details = plan.getMstbSubscFeeDetails();
		Set<MstbSubscFeeDetail> sortedDetails = new TreeSet<MstbSubscFeeDetail>(
				new Comparator<MstbSubscFeeDetail>() {
					public int compare(MstbSubscFeeDetail d1, MstbSubscFeeDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbSubscFeeDetail detail : sortedDetails) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getSubscriptionFee()));
			item.appendChild(newListcell(detail.getRecurringPeriod()));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));

			detailList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public ViewSubscriptionFeePlanWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		logger.info("Plan No = " + planNo);
		plan = businessHelper.getAdminBusiness().getSubscriptionFeePlan(planNo);
	}

	public void onCreate() {
		planNameLbl = (Label) getFellow("planNameLbl");
		planNameLbl.setValue(plan.getSubscriptionFeeName());

		detailList = (Listbox) getFellow("detailList");
		Set<MstbSubscFeeDetail> details = plan.getMstbSubscFeeDetails();
		Set<MstbSubscFeeDetail> sortedDetails = new TreeSet<MstbSubscFeeDetail>(
				new Comparator<MstbSubscFeeDetail>() {
					public int compare(MstbSubscFeeDetail d1, MstbSubscFeeDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbSubscFeeDetail detail : sortedDetails) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getSubscriptionFee()));
			item.appendChild(newListcell(detail.getRecurringPeriod()));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));

			detailList.appendChild(item);
		}
	}

	public void editDetail() throws InterruptedException {
		try {
			Listitem selectedItem = detailList.getSelectedItem();
			MstbSubscFeeDetail detail = (MstbSubscFeeDetail) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planDetailNo", detail.getSubscriptionFeeDetailNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(detail.getEffectiveDt());
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_SUBSCRIPTION_FEE_PLAN_DETAIL))
					forward(Uri.EDIT_SUBSCRIPTION_FEE_PLAN_DETAIL, map);
				else if(this.checkUriAccess(Uri.VIEW_SUBSCRIPTION_FEE_PLAN_DETAIL))
					forward(Uri.VIEW_SUBSCRIPTION_FEE_PLAN_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_SUBSCRIPTION_FEE_PLAN_DETAIL))
					forward(Uri.VIEW_SUBSCRIPTION_FEE_PLAN_DETAIL, map);
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

}
