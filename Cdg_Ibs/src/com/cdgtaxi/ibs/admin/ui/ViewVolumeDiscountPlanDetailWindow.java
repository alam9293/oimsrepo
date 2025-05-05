package com.cdgtaxi.ibs.admin.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbVolDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscTier;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewVolumeDiscountPlanDetailWindow extends CommonWindow implements AfterCompose {

	private MstbVolDiscMaster plan;
	private MstbVolDiscDetail detail;
	private Label planNameLabel, planTypeLabel, startDateLabel;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
		    	  lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	private Rows tierRows, tripTierRows, spendTierRows;
	@SuppressWarnings("unused")
	private Grid tripTierGrid, spendTierGrid, tierGrid;
	private Row tripTierRowTemplate, spendTierRowTemplate, newTierRowTemplate, tripTierRow, spendTierRow;
	private int tierCounter = 0;
	private Label message;
	private Row messageRow;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Map map = Executions.getCurrent().getArg();
		Integer planDetailNo =  (Integer) map.get("planDetailNo");
		detail = businessHelper.getAdminBusiness().getVolumeDiscountPlanDetail(planDetailNo);
		plan = detail.getMstbVolDiscMaster();

		planNameLabel.setValue(plan.getVolumeDiscountPlanName());
		planTypeLabel.setValue(NonConfigurableConstants.VOLUME_DISCOUNT_TYPE.get(detail.getVolumeDiscountType()));
		startDateLabel.setValue(DateUtil.convertDateToStr(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
		
		Set<MstbVolDiscTier> tiers = detail.getMstbVolDiscTiers();
		if (tiers.size() > 0) {
			String numberFormat;
			tripTierRowTemplate = (Row)tripTierRow.clone();
			spendTierRowTemplate = (Row)spendTierRow.clone();
			if (detail.getVolumeDiscountType().equals(NonConfigurableConstants.VOLUME_DISCOUNT_TYPE_TRIP)) {
				tierGrid = tripTierGrid;
				tierRows = tripTierRows;
				newTierRowTemplate = tripTierRowTemplate;
				tripTierGrid.setVisible(true);
				spendTierGrid.setVisible(false);
				numberFormat = StringUtil.GLOBAL_INTEGER_FORMAT;
			} else {
				tierGrid = spendTierGrid;
				tierRows = spendTierRows;
				newTierRowTemplate = spendTierRowTemplate;
				spendTierGrid.setVisible(true);
				tripTierGrid.setVisible(false);
				numberFormat = StringUtil.GLOBAL_DECIMAL_FORMAT;
			}
			
			tierRows.removeChild(tierRows.getFirstChild());

			Set<MstbVolDiscTier> sortedTiers = new TreeSet<MstbVolDiscTier>(
					new Comparator<MstbVolDiscTier>() {
						public int compare(MstbVolDiscTier t1, MstbVolDiscTier t2) {
							return t1.getStartRange().compareTo(t2.getStartRange());
						}
					});
			sortedTiers.addAll(tiers);

			for (MstbVolDiscTier tier : sortedTiers) {
				Row row = newTier(null);
				//				List<InputElement> children = row.getChildren();
				List children = row.getChildren();

				((Label) children.get(0)).setValue(StringUtil.numberToString(
						tier.getStartRange(), numberFormat));
				((Label) children.get(1)).setValue(StringUtil.bigDecimalToString(tier.getEndRange(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				((Label) children.get(2)).setValue(StringUtil.bigDecimalToString(tier.getVolumeDiscount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
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
		
		Timestamp t1 = DateUtil.convertTimestampTo0000Hours(detail.getEffectiveDt());
		Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
		if(t1.compareTo(t2) <= 0)
			message.setValue("The record has taken effect, no changes allowed.");
		else
			messageRow.setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
	public Row newTier(Button button) {
		tierCounter++;
		Row newRow = (Row) newTierRowTemplate.clone();
		newRow.setId(newRow.getId() + "_" + detail.getVolumeDiscountType() + "_" + tierCounter);
		for (Component component : (List<Component>) newRow.getChildren()) {
			component.setId(component.getId() + "_" + detail.getVolumeDiscountType() + "_" + tierCounter);
		}

		tierRows.appendChild(newRow);
		//updateMin(newRow);


		return newRow;
	}
	
	public void updateMin(Row row) {
		if (row == null) {
			return;
		}
		BigDecimal prevMaxValue = null;
		Row prevRow = (Row) row.getPreviousSibling();
		if (prevRow != null) {
			prevMaxValue = new BigDecimal(prevRow.getChildren().get(1).toString());
		}

		Label minLabel = (Label) row.getFirstChild();
		BigDecimal minValue = BigDecimal.ZERO;
		if (detail.getVolumeDiscountType().equals(NonConfigurableConstants.VOLUME_DISCOUNT_TYPE_TRIP)) {
			if (prevMaxValue != null) {
				minValue = prevMaxValue.add(BigDecimal.ONE);
			}
			minLabel.setValue(StringUtil.numberToString(
					minValue, StringUtil.GLOBAL_INTEGER_FORMAT));
		} else {
			if (prevMaxValue != null) {
				minValue = prevMaxValue.add(new BigDecimal("0.01"));
			}
			minLabel.setValue(StringUtil.numberToString(
					minValue, StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		
	}
}
