package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbCreditTermDetail;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewCreditTermPlanWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ViewCreditTermPlanWindow.class);

	private MstbCreditTermMaster plan;
	private Listbox detailList;
	private Label planNameLbl;

	@Override
	public void refresh() throws InterruptedException {
		plan = businessHelper.getAdminBusiness().getCreditTermPlan(plan.getCreditTermPlanNo());

		detailList.getItems().clear();

		Set<MstbCreditTermDetail> details = plan.getMstbCreditTermDetails();
		Set<MstbCreditTermDetail> sortedDetails = new TreeSet<MstbCreditTermDetail>(
				new Comparator<MstbCreditTermDetail>() {
					public int compare(MstbCreditTermDetail d1, MstbCreditTermDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbCreditTermDetail detail : sortedDetails) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getCreditTerm()));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));

			detailList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public ViewCreditTermPlanWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		logger.info("Plan No = " + planNo);
		plan = businessHelper.getAdminBusiness().getCreditTermPlan(planNo);
	}

	public void onCreate() {
		planNameLbl = (Label) getFellow("planNameLbl");
		planNameLbl.setValue(plan.getCreditTermPlanName());

		detailList = (Listbox) getFellow("detailList");
		Set<MstbCreditTermDetail> details = plan.getMstbCreditTermDetails();
		Set<MstbCreditTermDetail> sortedDetails = new TreeSet<MstbCreditTermDetail>(
				new Comparator<MstbCreditTermDetail>() {
					public int compare(MstbCreditTermDetail d1, MstbCreditTermDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbCreditTermDetail detail : sortedDetails) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getCreditTerm()));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));

			detailList.appendChild(item);
		}

	}

	public void editDetail() throws InterruptedException {
		try {
			Listitem selectedItem = detailList.getSelectedItem();
			MstbCreditTermDetail detail = (MstbCreditTermDetail) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planDetailNo", detail.getMstbCreditTermDetailNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(detail.getEffectiveDt());
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_CREDIT_TERM_PLAN_DETAIL))
					this.forward(Uri.EDIT_CREDIT_TERM_PLAN_DETAIL, map);
				else if(this.checkUriAccess(Uri.VIEW_CREDIT_TERM_PLAN_DETAIL))
					this.forward(Uri.VIEW_CREDIT_TERM_PLAN_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_CREDIT_TERM_PLAN_DETAIL))
					this.forward(Uri.VIEW_CREDIT_TERM_PLAN_DETAIL, map);
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
