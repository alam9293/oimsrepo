package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
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
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentDetail;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewEarlyPaymentPlanWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ViewEarlyPaymentPlanWindow.class);

	private MstbEarlyPaymentMaster plan;
	private Listbox detailList;
	private Label planNameLbl;

	@Override
	public void refresh() throws InterruptedException {
		plan = businessHelper.getAdminBusiness().getEarlyPaymentPlan(plan.getEarlyPaymentPlanNo());

		detailList.getItems().clear();

		Set<MstbEarlyPaymentDetail> details = plan.getMstbEarlyPaymentDetails();
		Set<MstbEarlyPaymentDetail> sortedDetails = new TreeSet<MstbEarlyPaymentDetail>(
				new Comparator<MstbEarlyPaymentDetail>() {
					public int compare(MstbEarlyPaymentDetail d1, MstbEarlyPaymentDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbEarlyPaymentDetail detail : sortedDetails) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getEarlyPayment()));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));

			detailList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public ViewEarlyPaymentPlanWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		logger.info("Plan No = " + planNo);
		plan = businessHelper.getAdminBusiness().getEarlyPaymentPlan(planNo);
	}

	public void onCreate() {
		planNameLbl = (Label) getFellow("planNameLbl");
		planNameLbl.setValue(plan.getEarlyPaymentPlanName());

		detailList = (Listbox) getFellow("detailList");
		Set<MstbEarlyPaymentDetail> details = plan.getMstbEarlyPaymentDetails();
		Set<MstbEarlyPaymentDetail> sortedDetails = new TreeSet<MstbEarlyPaymentDetail>(
				new Comparator<MstbEarlyPaymentDetail>() {
					public int compare(MstbEarlyPaymentDetail d1, MstbEarlyPaymentDetail d2) {
						return d2.getEffectiveDt().compareTo(d1.getEffectiveDt());
					}
				});
		sortedDetails.addAll(details);

		for (MstbEarlyPaymentDetail detail : sortedDetails) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getEarlyPayment()));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			detailList.appendChild(item);
		}
	}

	public void editDetail() throws InterruptedException {
		try {
			Listitem selectedItem = detailList.getSelectedItem();
			MstbEarlyPaymentDetail detail = (MstbEarlyPaymentDetail) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("planDetailNo", detail.getEarlyPaymentPlanDetailNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(detail.getEffectiveDt());
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_EARLY_PAYMENT_PLAN_DETAIL))
					forward(Uri.EDIT_EARLY_PAYMENT_PLAN_DETAIL, map);
				else if(this.checkUriAccess(Uri.VIEW_EARLY_PAYMENT_PLAN_DETAIL))
					forward(Uri.VIEW_EARLY_PAYMENT_PLAN_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_EARLY_PAYMENT_PLAN_DETAIL))
					forward(Uri.VIEW_EARLY_PAYMENT_PLAN_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		} finally {
			detailList.clearSelection();
		}
	}
}
