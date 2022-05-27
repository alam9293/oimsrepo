package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DuplicateEffectiveDateError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class CreateSubscriptionFeePlanDetailWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateSubscriptionFeePlanDetailWindow.class);

	private final MstbSubscFeeMaster plan;
	private final MstbSubscFeeDetail detail;

	private Datebox startDateDB;
	private Decimalbox subscriptionFeeDB;
	private Intbox recurringPeriodIB;

	@SuppressWarnings("unchecked")
	public CreateSubscriptionFeePlanDetailWindow() {
		logger.info("New Subscription Fee Plan Detail");

		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		System.out.println("############# " + planNo);
		plan = businessHelper.getAdminBusiness().getSubscriptionFeePlan(planNo);

		detail = new MstbSubscFeeDetail();
		detail.setMstbSubscFeeMaster(plan);
	}

	public void onCreate() {
		Label planNameLabel = (Label) getFellow("planNameLabel");
		planNameLabel.setValue(plan.getSubscriptionFeeName());

		startDateDB = (Datebox) getFellow("startDateDB");
		subscriptionFeeDB = (Decimalbox) getFellow("subscriptionFeeDB");
		recurringPeriodIB = (Intbox) getFellow("recurringPeriodIB");
	}

	public void saveDetail() throws InterruptedException {
		detail.setEffectiveDt(DateUtil.convertDateToTimestamp(startDateDB.getValue()));
		detail.setSubscriptionFee(subscriptionFeeDB.getValue());
		detail.setRecurringPeriod(recurringPeriodIB.getValue());

		try {
			businessHelper.getAdminBusiness().saveSubscriptionFeePlanDetail(detail, getUserLoginIdAndDomain());
			MasterSetup.getSubscriptionManager().refresh();
			Messagebox.show(
					"Plan detail has been successfully saved.", "Save Plan Detail",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		} 
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateEffectiveDateError e) {
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

	@Override
	public void refresh() throws InterruptedException {
	}
}
