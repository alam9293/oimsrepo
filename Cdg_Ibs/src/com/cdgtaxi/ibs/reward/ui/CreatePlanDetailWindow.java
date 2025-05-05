package com.cdgtaxi.ibs.reward.ui;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.RewardPlanOverlappingEffectiveDateException;
import com.cdgtaxi.ibs.master.model.LrtbRewardDetail;
import com.cdgtaxi.ibs.master.model.LrtbRewardTier;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class CreatePlanDetailWindow extends AbstractPlanDetailWindow {
	private static final Logger logger = Logger.getLogger(CreatePlanDetailWindow.class);

	@SuppressWarnings("unchecked")
	public CreatePlanDetailWindow() {
		logger.info("New Plan Detail");

		Map params = Executions.getCurrent().getArg();
		Integer planNo =  (Integer) params.get("planNo");
		plan = businessHelper.getRewardBusiness().getPlan(planNo);

		detail = new LrtbRewardDetail();
		detail.setLrtbRewardMaster(plan);
	}

	public void saveDetail() throws InterruptedException {
		if(startDateDB.getValue().compareTo(endDateDB.getValue()) == 1)
			throw new WrongValueException(startDateDB, "Effective Date From cannot be later than Effective Date To.");

		Set<LrtbRewardTier> tiers = buildTiers();
		detail.setLrtbRewardTiers(tiers);
		detail.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(startDateDB.getValue()));
		detail.setEffectiveDtTo(DateUtil.convertDateToTimestamp(endDateDB.getValue()));
		detail.setRewardType(rewardType);

		try {
			businessHelper.getRewardBusiness().savePlanDetail(detail, getUserLoginIdAndDomain());
			Messagebox.show(
					"Plan detail has been successfully saved.", "Save Plan Detail",
					Messagebox.OK, Messagebox.INFORMATION);
			MasterSetup.getRewardsManager().refresh();
			back();
		} 
		catch(WrongValueException wve){
			throw wve;
		}
		catch (RewardPlanOverlappingEffectiveDateException e) {
			throw new WrongValueException(startDateDB, e.getMessage());
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
