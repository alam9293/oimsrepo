package com.cdgtaxi.ibs.admin.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DuplicateEffectiveDateError;
import com.cdgtaxi.ibs.master.model.MstbVolDiscTier;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class EditVolumeDiscountPlanDetailWindow extends AbstractVolumeDiscountPlanDetailWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditVolumeDiscountPlanDetailWindow.class);
	
	@SuppressWarnings("unchecked")
	public EditVolumeDiscountPlanDetailWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer planDetailNo =  (Integer) params.get("planDetailNo");
		logger.info("Plan Detail No = " + planDetailNo);
		detail = businessHelper.getAdminBusiness().getVolumeDiscountPlanDetail(planDetailNo);
		plan = detail.getMstbVolDiscMaster();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		super.afterCompose();

		volumeDiscountType = detail.getVolumeDiscountType();
		for (Comboitem item : (List<Comboitem>) planTypeCB.getItems()) {
			if (item.getValue().equals(volumeDiscountType)) {
				planTypeCB.setSelectedItem(item);
				break;
			}
		}

		startDateDB.setRawValue(detail.getEffectiveDt());

		// Populate tiers
		updateTierGrid();
		Set<MstbVolDiscTier> tiers = detail.getMstbVolDiscTiers();
		if (tiers.size() > 0) {
			tierRows.removeChild(tierRows.getFirstChild());

			Set<MstbVolDiscTier> sortedTiers = new TreeSet<MstbVolDiscTier>(
					new Comparator<MstbVolDiscTier>() {
						public int compare(MstbVolDiscTier t1, MstbVolDiscTier t2) {
							return t1.getStartRange().compareTo(t2.getStartRange());
						}
					});
			sortedTiers.addAll(tiers);

			String numberFormat;
			if (volumeDiscountType.equals(NonConfigurableConstants.REWARDS_TYPE_PER_TRIP)) {
				numberFormat = StringUtil.GLOBAL_INTEGER_FORMAT;
			} else {
				numberFormat = StringUtil.GLOBAL_DECIMAL_FORMAT;
			}

			for (MstbVolDiscTier tier : sortedTiers) {
				Row row = newTier(null);
				//				List<InputElement> children = row.getChildren();
				List children = row.getChildren();

				((Label) children.get(0)).setValue(StringUtil.numberToString(
						tier.getStartRange(), numberFormat));
				((Decimalbox) children.get(1)).setRawValue(tier.getEndRange());
				((Decimalbox) children.get(2)).setRawValue(tier.getVolumeDiscount());
			}
		}
		if(detail.getCreatedDt()!=null)createdByLabel.setValue(detail.getCreatedBy());
		else createdByLabel.setValue("-");
		if(detail.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(detail.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(detail.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(detail.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}

	public void updateDetail() throws InterruptedException {
		Set<MstbVolDiscTier> tiers = buildTiers();
		
		Set<MstbVolDiscTier> removedTiers = detail.getMstbVolDiscTiers();
		removedTiers.removeAll(tiers);
		detail.setMstbVolDiscTiers(tiers);
		detail.setEffectiveDt(DateUtil.convertDateToTimestamp(startDateDB.getValue()));
		detail.setVolumeDiscountType(volumeDiscountType);

		try {
			businessHelper.getAdminBusiness().updateVolumeDiscountPlanDetail(detail, removedTiers, getUserLoginIdAndDomain());
			MasterSetup.getVolumeDiscountManager().refresh();
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

}
