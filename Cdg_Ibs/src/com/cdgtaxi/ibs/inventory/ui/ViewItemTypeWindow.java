package com.cdgtaxi.ibs.inventory.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.ImtbStock;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.inventory.ItemTypeDto;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewItemTypeWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ViewItemTypeWindow.class);

	private ItemTypeDto itemTypeDto;
	private ImtbItemType itemType;
	private Label totalQtyLabel, issuedQtyLabel, stockQtyLabel, reservedQtyLabel;
	private Listbox stockList;

	@Override
	public void refresh() throws InterruptedException {
		Integer itemTypeNo = itemTypeDto.getItemTypeNo();
		itemTypeDto = businessHelper.getInventoryBusiness().getItemTypeDto(itemTypeNo);
		itemType = businessHelper.getInventoryBusiness().getItemType(itemTypeNo);

		refreshValues();
	}

	@SuppressWarnings("rawtypes")
	public ViewItemTypeWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer itemTypeNo =  (Integer) params.get("itemTypeNo");
		logger.info("Item Type No = " + itemTypeNo);
		itemTypeDto = businessHelper.getInventoryBusiness().getItemTypeDto(itemTypeNo);
		itemType = businessHelper.getInventoryBusiness().getItemType(itemTypeNo);
	}

	public void onCreate() {
		Label itemTypeNameLabel = (Label) getFellow("itemTypeNameLabel");
		Label priceLabel = (Label) getFellow("priceLabel");
		Label redeemedQtyLabel = (Label) getFellow("redeemedQtyLabel");
		totalQtyLabel = (Label) getFellow("totalQtyLabel");
		issuedQtyLabel = (Label) getFellow("issuedQtyLabel");
		stockQtyLabel = (Label) getFellow("stockQtyLabel");
		reservedQtyLabel = (Label) getFellow("reservedQtyLabel");
		stockList = (Listbox) getFellow("stockList");
		stockList.setPageSize(10);

		itemTypeNameLabel.setValue(itemTypeDto.getTypeName());
		priceLabel.setValue(StringUtil.numberToString(
				itemTypeDto.getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		redeemedQtyLabel.setValue(StringUtil.numberToString(
				itemTypeDto.getRedeemedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));

		refreshValues();
		
		if(!this.checkUriAccess(Uri.ADD_INVENTORY_STOCK))
			((Button)this.getFellow("newStockBtn")).setDisabled(true);
	}

	private void refreshValues() {
		totalQtyLabel.setValue(StringUtil.numberToString(
				itemTypeDto.getTotalQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
		issuedQtyLabel.setValue(StringUtil.numberToString(
				itemTypeDto.getIssuedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
		stockQtyLabel.setValue(StringUtil.numberToString(
				itemTypeDto.getStockQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
		reservedQtyLabel.setValue(StringUtil.numberToString(
				itemTypeDto.getReservedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));

		// Stock listing
		List<ImtbStock> stockEntries = this.businessHelper.getInventoryBusiness().getStocksByItemType(itemType.getItemTypeNo());

		stockList.getItems().clear();
		if (stockList.getListfoot() != null) {
			stockList.getListfoot().detach();
		}

		if (stockEntries.isEmpty()) {
			stockList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(
					stockList.getListhead().getChildren().size()));
		} else {
			for (ImtbStock stockEntry : stockEntries) {
				Listitem item = new Listitem();
				item.setValue(stockEntry);
				String rewardsType = NonConfigurableConstants.STOCK_TXN_TYPE.get(
						stockEntry.getTxnType());
				item.appendChild(new Listcell(rewardsType));
				BigDecimal quantity = stockEntry.getSerialNoEnd().subtract(
						stockEntry.getSerialNoStart()).add(BigDecimal.ONE);
				item.appendChild(new Listcell(StringUtil.numberToString(
						quantity, StringUtil.GLOBAL_INTEGER_FORMAT)));
				item.appendChild(new Listcell(stockEntry.getSerialNoStart().toString()));
				item.appendChild(new Listcell(stockEntry.getSerialNoEnd().toString()));
				item.appendChild(new Listcell(DateUtil.convertDateToStr(
						stockEntry.getTxnDt(), DateUtil.GLOBAL_DATE_FORMAT) + " " + DateUtil.convertDateToStr(
								stockEntry.getTxnDt(), DateUtil.GLOBAL_TIME_FORMAT)));
				stockList.appendChild(item);
			}
		}
	}

	public void addStock() throws InterruptedException {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemTypeNo", itemTypeDto.getItemTypeNo());
			forward(Uri.ADD_INVENTORY_STOCK, map);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}

	public void viewIssuanceDetails() throws InterruptedException {
		ImtbStock stock = (ImtbStock) stockList.getSelectedItem().getValue();
		if (!stock.getTxnType().equals(NonConfigurableConstants.STOCK_TXN_TYPE_ISSUED)) {
			return;
		}

		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("stockNo", stock.getStockNo());
			forward(Uri.VIEW_INVENTORY_ISSUANCE, map);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}
}
