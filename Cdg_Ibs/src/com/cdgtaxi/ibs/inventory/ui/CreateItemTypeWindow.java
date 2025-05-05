package com.cdgtaxi.ibs.inventory.ui;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

@SuppressWarnings("serial")
public class CreateItemTypeWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateItemTypeWindow.class);

	private Textbox itemTypeNameTB;
	private Decimalbox unitPriceDB;

	public void onCreate() {
		itemTypeNameTB = (Textbox) getFellow("itemTypeNameTB");
		unitPriceDB = (Decimalbox) getFellow("unitPriceDB");
		itemTypeNameTB.focus();
	}

	public void createItemType() throws InterruptedException {
		ImtbItemType itemType = new ImtbItemType();
		itemType.setTypeName(itemTypeNameTB.getValue());
		itemType.setPrice(unitPriceDB.getValue());

		try {
			businessHelper.getInventoryBusiness().createItemType(itemType);
			Messagebox.show("Item type has been successfully created", "Create Inventory Item Type",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
