package com.cdgtaxi.ibs.reward.ui;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.InputElement;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.LrtbRewardDetail;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.LrtbRewardTier;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public abstract class AbstractPlanDetailWindow extends CommonWindow {
	//	private static final Logger logger = Logger.getLogger(AbstractPlanDetailWindow.class);

	protected LrtbRewardMaster plan;
	protected LrtbRewardDetail detail;

	protected Combobox planTypeCB;
	protected Datebox startDateDB, endDateDB;

	protected String rewardType;
	protected Grid tierGrid;
	protected Grid tierGrid2;
	protected Rows tierRows;
	protected Row tripTierRowTemplate, spendTierRowTemplate, newTierRowTemplate;
	protected int tierCounter = 0;

	public void onCreate() {
		Label planNameLabel = (Label) getFellow("planNameLabel");
		planNameLabel.setValue(plan.getRewardPlanName());

		planTypeCB = (Combobox) getFellow("planTypeCB");
		List<Comboitem> items = ComponentUtil.convertToComboitems(
				NonConfigurableConstants.REWARDS_TYPE, true);
		for (Comboitem item : items) {
			planTypeCB.appendChild(item);
		}

		startDateDB = (Datebox) getFellow("startDateDB");
		endDateDB = (Datebox) getFellow("endDateDB");

		// remember row templates
		tripTierRowTemplate = (Row) getFellow("tripTierRow").clone();
		spendTierRowTemplate = (Row) getFellow("spendTierRow").clone();

		planTypeCB.focus();
	}

	@SuppressWarnings("unchecked")
	public Row newTier(Button button) {
		tierCounter++;
		Row newRow = (Row) newTierRowTemplate.clone();
		newRow.setId(newRow.getId() + "_" + rewardType + "_" + tierCounter);
		for (Component component : (List<Component>) newRow.getChildren()) {
			component.setId(component.getId() + "_" + rewardType + "_" + tierCounter);
		}

		tierRows.appendChild(newRow);
		updateMin(newRow);

		if (button != null) {
			((InputElement) newRow.getChildren().get(1)).setFocus(true);
		}

		return newRow;
	}
	@SuppressWarnings("unchecked")
	public Row newViewTier(Button button) {
		tierCounter++;
		Row newRow = (Row) newTierRowTemplate.clone();
		newRow.setId(newRow.getId() + "_" + rewardType + "_" + tierCounter);
		for (Component component : (List<Component>) newRow.getChildren()) {
			component.setId(component.getId() + "_" + rewardType + "_" + tierCounter);
		}

		tierRows.appendChild(newRow);
		updateViewMin(newRow);

		if (button != null) {
			((InputElement) newRow.getChildren().get(1)).setFocus(true);
		}

		return newRow;
	}
	public void updateViewMin(Row row) {
		if (row == null) {
			return;
		}

		BigDecimal prevMaxValue = null;
		Row prevRow = (Row) row.getPreviousSibling();
		if (prevRow != null) {
			Label maxDB = (Label) prevRow.getChildren().get(1);
			prevMaxValue = new BigDecimal(maxDB.getValue());
		}

		Label minLabel = (Label) row.getFirstChild();
		BigDecimal minValue = BigDecimal.ZERO;
		rewardType = (String) detail.getRewardType();
		if (rewardType.equals(NonConfigurableConstants.REWARDS_TYPE_PER_TRIP)) {
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
	public void updateMin(Row row) {
		if (row == null) {
			return;
		}

		BigDecimal prevMaxValue = null;
		Row prevRow = (Row) row.getPreviousSibling();
		if (prevRow != null) {
			Decimalbox maxDB = (Decimalbox) prevRow.getChildren().get(1);
			prevMaxValue = maxDB.getValue();
		}

		Label minLabel = (Label) row.getFirstChild();
		BigDecimal minValue = BigDecimal.ZERO;
		rewardType = (String) planTypeCB.getSelectedItem().getValue();
		if (rewardType.equals(NonConfigurableConstants.REWARDS_TYPE_PER_TRIP)) {
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
	public void updateViewTierGrid() {
		Grid tripTierGrid = (Grid) getFellow("tripTierGrid");
		Grid tripTierGrid2 = (Grid) getFellow("tripTierGrid2");
		Grid spendTierGrid = (Grid) getFellow("spendTierGrid");
		Grid spendTierGrid2 = (Grid) getFellow("spendTierGrid2");

		rewardType = (String) detail.getRewardType();
		if (rewardType.equals(NonConfigurableConstants.REWARDS_TYPE_PER_TRIP)) {
			tierGrid = tripTierGrid;
			tierGrid2 = tripTierGrid2;
			tierRows = (Rows) getFellow("tripTierRows");
			newTierRowTemplate = tripTierRowTemplate;
			spendTierGrid.setVisible(false);
			spendTierGrid2.setVisible(false);
		} else {
			tierGrid = spendTierGrid;
			tierGrid2 = spendTierGrid2;
			tierRows = (Rows) getFellow("spendTierRows");
			newTierRowTemplate = spendTierRowTemplate;
			tripTierGrid.setVisible(false);
			tripTierGrid2.setVisible(false);
		}
		updateViewMin((Row) tierRows.getFirstChild());

		tierGrid.setVisible(true);
		tierGrid2.setVisible(true);
	}
	public void updateTierGrid() {
		Grid tripTierGrid = (Grid) getFellow("tripTierGrid");
		Grid tripTierGrid2 = (Grid) getFellow("tripTierGrid2");
		Grid spendTierGrid = (Grid) getFellow("spendTierGrid");
		Grid spendTierGrid2 = (Grid) getFellow("spendTierGrid2");

		rewardType = (String) planTypeCB.getSelectedItem().getValue();
		if (rewardType.equals(NonConfigurableConstants.REWARDS_TYPE_PER_TRIP)) {
			tierGrid = tripTierGrid;
			tierGrid2 = tripTierGrid2;
			tierRows = (Rows) getFellow("tripTierRows");
			newTierRowTemplate = tripTierRowTemplate;
			spendTierGrid.setVisible(false);
			spendTierGrid2.setVisible(false);
		} else {
			tierGrid = spendTierGrid;
			tierGrid2 = spendTierGrid2;
			tierRows = (Rows) getFellow("spendTierRows");
			newTierRowTemplate = spendTierRowTemplate;
			tripTierGrid.setVisible(false);
			tripTierGrid2.setVisible(false);
		}
		updateMin((Row) tierRows.getFirstChild());

		tierGrid.setVisible(true);
		tierGrid2.setVisible(true);
	}

	public void dropTier(Row row) throws InterruptedException {
		
		int response = Messagebox.show("Are you sure you wish to drop this tier?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION);

		if (response != Messagebox.OK) {
			return;
		}

		Row nextRow = (Row) row.getNextSibling();
		row.detach();
		updateMin(nextRow);
	}

	@SuppressWarnings("unchecked")
	protected Set<LrtbRewardTier> buildTiers() throws InterruptedException {
		Set<LrtbRewardTier> tiers = new HashSet<LrtbRewardTier>();
		BigDecimal prevMaxValue = BigDecimal.ZERO;
		
		boolean firstRow = true;
		for (Row row : (List<Row>) tierRows.getChildren()) {
			List children = row.getChildren();
			LrtbRewardTier tier = new LrtbRewardTier();
			BigDecimal startRange = BigDecimal.ZERO;
			try {
				String sStartRange = ((Label) children.get(0)).getValue();
				startRange = new BigDecimal(StringUtil.stringToNumber(sStartRange).doubleValue());
			} catch (ParseException e) {
				e.printStackTrace();
				Messagebox.show(e.toString());
				throw new InterruptedException();
			}
			
			tier.setStartRange(startRange);
			Decimalbox maxDB = (Decimalbox) children.get(1);
			BigDecimal endRange = maxDB.getValue();
			
			if(rewardType.equals(NonConfigurableConstants.REWARDS_TYPE_PER_TRIP)){
				endRange = endRange.setScale(0, BigDecimal.ROUND_HALF_UP);
				maxDB.setValue(endRange);
				maxDB.getValue();
			}
			
			if(!firstRow)
				if (endRange.compareTo(prevMaxValue) <= 0) {
					throw new WrongValueException(maxDB,
							"This value must be greater than that of the previous tier");
				}
//			else if(startRange.compareTo(endRange) == 0){
//				throw new WrongValueException(maxDB, 
//						"This value must be greater than the min value of the same tier");
//			}
			tier.setEndRange(endRange);
			tier.setPtsPerValue(((Intbox) children.get(2)).getValue());
			tier.setLrtbRewardDetail(detail);
			tiers.add(tier);

			prevMaxValue = endRange;
			firstRow = false;
		}
		return tiers;
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
