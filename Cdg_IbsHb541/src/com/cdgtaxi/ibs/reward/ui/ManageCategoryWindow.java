package com.cdgtaxi.ibs.reward.ui;

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
import com.cdgtaxi.ibs.common.exception.RewardCategoryHasItemsException;
import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings("serial")
public class ManageCategoryWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ManageCategoryWindow.class);

	private Listbox resultList;

	@SuppressWarnings("unchecked")
	public void onCreate(CreateEvent ce) throws Exception {
		resultList = (Listbox) getFellow("resultList");
		Listitem itemTemplate = (Listitem) resultList.getItems().get(0);
		itemTemplate.detach();

		List<LrtbGiftCategory> categories = businessHelper.getRewardBusiness().getCategories();

		if (categories.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
		} else {
			for (LrtbGiftCategory category : categories) {
				Listitem item = (Listitem) itemTemplate.clone();
				item.setValue(category);
				List<Listcell> cells = item.getChildren();
				cells.get(0).setLabel(category.getCategoryName());
				resultList.appendChild(item);
			}
		}
		
		if(!this.checkUriAccess(Uri.CREATE_GIFT_CATEGORY))
			((Button)this.getFellow("createCategoryBtn")).setDisabled(true);
	}

	public void viewCategory() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			LrtbGiftCategory category = (LrtbGiftCategory)selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("categoryNo", category.getGiftCategoryNo());
			
			if(this.checkUriAccess(Uri.EDIT_GIFT_CATEGORY))
				forward(Uri.EDIT_GIFT_CATEGORY, map);
			else if(this.checkUriAccess(Uri.VIEW_GIFT_CATEGORY))
				forward(Uri.VIEW_GIFT_CATEGORY, map);
			else{
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
						"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		} finally {
			resultList.clearSelection();
		}
	}

	public void createCategory() throws InterruptedException {
		try {
			forward(Uri.CREATE_GIFT_CATEGORY, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}

	public void deleteCategory(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_GIFT_CATEGORY)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this category?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			businessHelper.getRewardBusiness().deleteCategory(
					(LrtbGiftCategory) item.getValue());
			item.detach();
			Messagebox.show("Category has been successfully deleted", "Delete Gift Category",
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (RewardCategoryHasItemsException e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
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
