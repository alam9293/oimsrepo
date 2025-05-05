package com.cdgtaxi.ibs.admin.ui;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.InputElement;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbVolDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscTier;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public abstract class AbstractVolumeDiscountPlanDetailWindow extends CommonWindow implements AfterCompose{
	//	private static final Logger logger = Logger.getLogger(AbstractPlanDetailWindow.class);

	protected MstbVolDiscMaster plan;
	protected MstbVolDiscDetail detail;

	protected Combobox planTypeCB;
	protected Datebox startDateDB;

	protected String volumeDiscountType;
	protected Grid tierGrid;
	protected Rows tierRows;
	protected Row tripTierRowTemplate, spendTierRowTemplate, newTierRowTemplate;
	protected int tierCounter = 0;
	protected Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Label planNameLabel = (Label) getFellow("planNameLabel");
		planNameLabel.setValue(plan.getVolumeDiscountPlanName());

		planTypeCB = (Combobox) getFellow("planTypeCB");
		List<Comboitem> items = ComponentUtil.convertToComboitems(
				NonConfigurableConstants.VOLUME_DISCOUNT_TYPE, true);
		for (Comboitem item : items) {
			planTypeCB.appendChild(item);
		}

		startDateDB = (Datebox) getFellow("startDateDB");

		// remember row templates
		tripTierRowTemplate = (Row) getFellow("tripTierRow").clone();
		spendTierRowTemplate = (Row) getFellow("spendTierRow").clone();

		planTypeCB.focus();
	}

	@SuppressWarnings("unchecked")
	public Row newTier(Button button) {
		tierCounter++;
		Row newRow = (Row) newTierRowTemplate.clone();
		newRow.setId(newRow.getId() + "_" + volumeDiscountType + "_" + tierCounter);
		for (Component component : (List<Component>) newRow.getChildren()) {
			component.setId(component.getId() + "_" + volumeDiscountType + "_" + tierCounter);
		}

		tierRows.appendChild(newRow);
		updateMin(newRow);

		if (button != null) {
			((InputElement) newRow.getChildren().get(1)).setFocus(true);
		}

		return newRow;
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
		volumeDiscountType = (String) planTypeCB.getSelectedItem().getValue();
		if (volumeDiscountType.equals(NonConfigurableConstants.VOLUME_DISCOUNT_TYPE_TRIP)) {
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

	public void updateTierGrid() {
		Grid tripTierGrid = (Grid) getFellow("tripTierGrid");
		Grid spendTierGrid = (Grid) getFellow("spendTierGrid");

		volumeDiscountType = (String) planTypeCB.getSelectedItem().getValue();
		if (volumeDiscountType.equals(NonConfigurableConstants.VOLUME_DISCOUNT_TYPE_TRIP)) {
			tierGrid = tripTierGrid;
			tierRows = (Rows) getFellow("tripTierRows");
			newTierRowTemplate = tripTierRowTemplate;
			spendTierGrid.setVisible(false);
		} else {
			tierGrid = spendTierGrid;
			tierRows = (Rows) getFellow("spendTierRows");
			newTierRowTemplate = spendTierRowTemplate;
			tripTierGrid.setVisible(false);
		}
		updateMin((Row) tierRows.getFirstChild());

		tierGrid.setVisible(true);
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
	protected Set<MstbVolDiscTier> buildTiers() throws InterruptedException {
		Set<MstbVolDiscTier> tiers = new HashSet<MstbVolDiscTier>();
		BigDecimal prevMaxValue = BigDecimal.ZERO;
		for (Row row : (List<Row>) tierRows.getChildren()) {
			List children = row.getChildren();
			MstbVolDiscTier tier = new MstbVolDiscTier();
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
			if (maxDB.getValue().compareTo(prevMaxValue) <= 0) {
				throw new WrongValueException(maxDB,
				"This value must be greater than that of the previous tier");
			}
			tier.setEndRange(maxDB.getValue());
			tier.setVolumeDiscount(((Decimalbox) children.get(2)).getValue());
			tier.setMstbVolDiscDetail(detail);
			tiers.add(tier);

			prevMaxValue = maxDB.getValue();
		}
		return tiers;
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
