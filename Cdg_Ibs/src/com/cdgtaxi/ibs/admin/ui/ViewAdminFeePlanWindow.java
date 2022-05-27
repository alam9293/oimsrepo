package com.cdgtaxi.ibs.admin.ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewAdminFeePlanWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ViewAdminFeePlanWindow.class);

	private MstbAdminFeeMaster plan;
	private Textbox planNameTB;
	private Listbox detailList;
	private Label planNameLbl;

	@SuppressWarnings("unchecked")
	public ViewAdminFeePlanWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		logger.info("Plan No = " + planNo);
		plan = businessHelper.getAdminBusiness().getAdminFeePlan(planNo);
	}
	@Override
	public void refresh() throws InterruptedException {
		boolean flag = true;
		plan = businessHelper.getAdminBusiness().getAdminFeePlan(plan.getAdminFeePlanNo());

		detailList.getItems().clear();

		Set<MstbAdminFeeDetail> details = plan.getMstbAdminFeeDetails();
		Set<MstbAdminFeeDetail> sortedDetails = new TreeSet<MstbAdminFeeDetail>(
				new Comparator<MstbAdminFeeDetail>() {
					public int compare(MstbAdminFeeDetail d1, MstbAdminFeeDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbAdminFeeDetail detail : sortedDetails) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getAdminFee()));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			
			detailList.appendChild(item);
		}
	}

	public void onCreate() {
		boolean flag = true;
		planNameLbl = (Label) getFellow("planNameLbl");
		planNameLbl.setValue(plan.getAdminFeePlanName());

		detailList = (Listbox) getFellow("detailList");
		Set<MstbAdminFeeDetail> details = plan.getMstbAdminFeeDetails();
		Set<MstbAdminFeeDetail> sortedDetails = new TreeSet<MstbAdminFeeDetail>(
				new Comparator<MstbAdminFeeDetail>() {
					public int compare(MstbAdminFeeDetail d1, MstbAdminFeeDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbAdminFeeDetail detail : sortedDetails) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getAdminFee()));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			
			detailList.appendChild(item);
		}
	}

	public void editDetail() throws InterruptedException {
		try {
			Listitem selectedItem = detailList.getSelectedItem();
			MstbAdminFeeDetail detail = (MstbAdminFeeDetail) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planDetailNo", detail.getAdminFeePlanDetailNo());
			if(DateUtil.getCurrentDate().compareTo(detail.getEffectiveDt()) <=0)
				forward(Uri.EDIT_ADMIN_FEE_PLAN_DETAIL, map);
			else
				forward(Uri.VIEW_ADMIN_FEE_PLAN_DETAIL, map);
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
