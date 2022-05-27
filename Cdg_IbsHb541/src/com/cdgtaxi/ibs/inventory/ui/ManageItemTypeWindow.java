package com.cdgtaxi.ibs.inventory.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.InventoryHasStockException;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.inventory.ItemTypeDto;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings({"serial","unchecked"})
public class ManageItemTypeWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ManageItemTypeWindow.class);

	private Listbox resultList;

	public void onCreate(CreateEvent ce) throws Exception {
		resultList = (Listbox) getFellow("resultList");
		Listitem itemTemplate = (Listitem) resultList.getItems().get(0);
		itemTemplate.detach();

		List<ItemTypeDto> itemTypes = businessHelper.getInventoryBusiness().getItemTypeDtos();

		if (itemTypes.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
		} else {
			for (ItemTypeDto itemType : itemTypes) {
				Listitem item = (Listitem) itemTemplate.clone();
				item.setValue(itemType);
				List<Listcell> cells = item.getChildren();
				cells.get(0).setLabel(itemType.getTypeName());
				cells.get(1).setLabel(StringUtil.numberToString(
						itemType.getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				cells.get(2).setLabel(StringUtil.numberToString(
						itemType.getTotalQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				cells.get(3).setLabel(StringUtil.numberToString(
						itemType.getStockQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				cells.get(4).setLabel(StringUtil.numberToString(
						itemType.getReservedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				cells.get(5).setLabel(StringUtil.numberToString(
						itemType.getIssuedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				cells.get(6).setLabel(StringUtil.numberToString(
						itemType.getRedeemedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				resultList.appendChild(item);
			}
		}
		
		if(!this.checkUriAccess(Uri.CREATE_INVENTORY_ITEM_TYPE))
			((Button)this.getFellow("createItemTypeBtn")).setDisabled(true);
	}

	public void viewItemType() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			ItemTypeDto itemType = (ItemTypeDto) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemTypeNo", itemType.getItemTypeNo());
			forward(Uri.VIEW_INVENTORY_ITEM_TYPE, map);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		} finally {
			resultList.clearSelection();
		}
	}

	public void createItemType() throws InterruptedException {
		try {
			forward(Uri.CREATE_INVENTORY_ITEM_TYPE, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}

	public void deleteItemType(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_INVENTORY_ITEM_TYPE)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this item type?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		ItemTypeDto itemType = (ItemTypeDto) item.getValue();
		try {
			businessHelper.getInventoryBusiness().deleteItemType(itemType.getItemTypeNo());
			item.detach();
			Messagebox.show("Item type has been successfully deleted", "Delete Item Type",
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (InventoryHasStockException e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		
		List<ItemTypeDto> itemTypes = businessHelper.getInventoryBusiness().getItemTypeDtos();

		if (itemTypes.size() > 0) {
			
			Listitem itemTemplate = (Listitem) resultList.getItems().get(0);
			resultList.getItems().clear();
			
			for (ItemTypeDto itemType : itemTypes) {
				Listitem item = (Listitem) itemTemplate.clone();
				item.setValue(itemType);
				List<Listcell> cells = item.getChildren();
				cells.get(0).setLabel(itemType.getTypeName());
				cells.get(1).setLabel(StringUtil.numberToString(
						itemType.getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				cells.get(2).setLabel(StringUtil.numberToString(
						itemType.getTotalQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				cells.get(3).setLabel(StringUtil.numberToString(
						itemType.getStockQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				cells.get(4).setLabel(StringUtil.numberToString(
						itemType.getReservedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				cells.get(5).setLabel(StringUtil.numberToString(
						itemType.getIssuedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				cells.get(6).setLabel(StringUtil.numberToString(
						itemType.getRedeemedQty(), StringUtil.GLOBAL_INTEGER_FORMAT));
				resultList.appendChild(item);
			}
		}
	}
}
