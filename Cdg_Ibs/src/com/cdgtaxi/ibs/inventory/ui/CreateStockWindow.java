package com.cdgtaxi.ibs.inventory.ui;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.CDGEInventoryInterfaceException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoExistsException;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.ImtbStock;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

@SuppressWarnings("serial")
public class CreateStockWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateStockWindow.class);

	private Decimalbox serialStartDB, serialEndDB;
	private final ImtbItemType itemType;

	@SuppressWarnings("unchecked")
	public CreateStockWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer itemTypeNo =  (Integer) params.get("itemTypeNo");
		logger.info("Item Type No = " + itemTypeNo);
		itemType = businessHelper.getInventoryBusiness().getItemType(itemTypeNo);
	}

	public void onCreate() {
		Label itemTypeNameLabel = (Label) getFellow("itemTypeNameLabel");
		serialStartDB = (Decimalbox) getFellow("serialStartDB");
		serialEndDB = (Decimalbox) getFellow("serialEndDB");

		itemTypeNameLabel.setValue(itemType.getTypeName());

		BigDecimal nextSerialNo = businessHelper.getInventoryBusiness().getNextStockSerialNo(
				itemType.getItemTypeNo());
		serialStartDB.setValue(nextSerialNo);

		serialStartDB.focus();
	}

	public void saveStock() throws InterruptedException {
		BigDecimal serialNoStart = serialStartDB.getValue();
		BigDecimal serialNoEnd = serialEndDB.getValue();

		if (serialNoStart.compareTo(serialNoEnd) > 0) {
			Messagebox.show("Starting Serial No. must be less than Ending Serial No.",
					"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		ImtbStock stock = new ImtbStock();
		stock.setSerialNoStart(serialNoStart);
		stock.setSerialNoEnd(serialNoEnd);
		stock.setImtbItemType(itemType);

		try {
			businessHelper.getInventoryBusiness().createStock(stock);
			Messagebox.show("Stock has been successfully added", "Add Inventory Stock",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		} catch (InventorySerialNoExistsException e) {
			Messagebox.show(e.getMessage(), "Serial No. Exists", Messagebox.OK, Messagebox.ERROR);
		} catch (CDGEInventoryInterfaceException e) {
			Messagebox.show(e.getMessage(), "Interface Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
