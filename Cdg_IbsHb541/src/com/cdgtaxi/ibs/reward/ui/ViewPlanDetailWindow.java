package com.cdgtaxi.ibs.reward.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.RewardPlanOverlappingEffectiveDateException;
import com.cdgtaxi.ibs.master.model.LrtbRewardTier;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewPlanDetailWindow extends AbstractPlanDetailWindow implements AfterCompose {
	private static final Logger logger = Logger.getLogger(ViewPlanDetailWindow.class);
	
	private Label createdByLabel, createdTimeLabel, createdDateLabel, 
	lastUpdatedDateLabel, lastUpdatedByLabel, lastUpdatedTimeLabel, planTyleLbl, planNameLabel,startDateLbl,endDateLbl;
	
	@SuppressWarnings("unchecked")
	public ViewPlanDetailWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planDetailNo =  (Integer) params.get("planDetailNo");
		logger.info("Plan Detail No = " + planDetailNo);
		detail = businessHelper.getRewardBusiness().getPlanDetail(planDetailNo);
		plan = detail.getLrtbRewardMaster();
	}

	public void afterCompose(){
		Components.wireVariables(this, this);
		
		if(detail.getCreatedBy()!=null && detail.getCreatedBy().length()>0)
			createdByLabel.setValue(detail.getCreatedBy());
		else createdByLabel.setValue("-");
		
		if(detail.getCreatedDt()!=null)
			createdDateLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		
		if(detail.getCreatedDt()!=null)
			createdTimeLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		
		if(detail.getUpdatedBy()!=null && detail.getUpdatedBy().length()>0)
			lastUpdatedByLabel.setValue(detail.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		
		if(detail.getUpdatedDt()!=null)
			lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		
		if(detail.getUpdatedDt()!=null)
			lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void onCreate() {
		//super.onCreate();
		tripTierRowTemplate = (Row) getFellow("tripTierRow").clone();
		spendTierRowTemplate = (Row) getFellow("spendTierRow").clone();

		rewardType = detail.getRewardType();
		planNameLabel.setValue(plan.getRewardPlanName());
		planTyleLbl.setValue(NonConfigurableConstants.REWARDS_TYPE.get(detail.getRewardType()));
		startDateLbl.setValue(DateUtil.convertDateToStr(detail.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
		endDateLbl.setValue(DateUtil.convertDateToStr(detail.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));

		// Populate tiers
		updateViewTierGrid();
		Set<LrtbRewardTier> tiers = detail.getLrtbRewardTiers();
		if (tiers.size() > 0) {
			tierRows.removeChild(tierRows.getFirstChild());

			Set<LrtbRewardTier> sortedTiers = new TreeSet<LrtbRewardTier>(
					new Comparator<LrtbRewardTier>() {
						public int compare(LrtbRewardTier t1, LrtbRewardTier t2) {
							return t1.getStartRange().compareTo(t2.getStartRange());
						}
					});
			sortedTiers.addAll(tiers);

			String numberFormat;
			if (rewardType.equals(NonConfigurableConstants.REWARDS_TYPE_PER_TRIP)) {
				numberFormat = StringUtil.GLOBAL_INTEGER_FORMAT;
			} else {
				numberFormat = StringUtil.GLOBAL_DECIMAL_FORMAT;
			}

			for (LrtbRewardTier tier : sortedTiers) {
				Row row = newViewTier(null);
				//				List<InputElement> children = row.getChildren();
				List children = row.getChildren();

				((Label) children.get(0)).setValue(StringUtil.numberToString(
						tier.getStartRange(), numberFormat));
				((Label) children.get(1)).setValue(tier.getEndRange().toString());
				((Label) children.get(2)).setValue(String.valueOf(tier.getPtsPerValue()));
			}
		}
	}

	public void updateDetail() throws InterruptedException {
		if(startDateDB.getValue().compareTo(endDateDB.getValue()) == 1)
			throw new WrongValueException(startDateDB, "Effective Date From cannot be later than Effective Date To.");
		
		Set<LrtbRewardTier> tiers = buildTiers();
		Set<LrtbRewardTier> removedTiers = detail.getLrtbRewardTiers();
		removedTiers.removeAll(tiers);
		detail.setLrtbRewardTiers(tiers);
		detail.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(startDateDB.getValue()));
		detail.setEffectiveDtTo(DateUtil.convertDateToTimestamp(endDateDB.getValue()));
		detail.setRewardType(rewardType);

		try {
			businessHelper.getRewardBusiness().updatePlanDetail(detail, removedTiers, getUserLoginIdAndDomain());
			Messagebox.show(
					"Plan detail has been successfully saved.", "Save Plan Detail",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		} catch (RewardPlanOverlappingEffectiveDateException e) {
			throw new WrongValueException(startDateDB, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(e.toString());
		}
	}
}
