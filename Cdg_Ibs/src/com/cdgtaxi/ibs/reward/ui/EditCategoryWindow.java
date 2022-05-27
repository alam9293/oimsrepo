package com.cdgtaxi.ibs.reward.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.RewardItemIsUsedException;
import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;
import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class EditCategoryWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditCategoryWindow.class);

	private final Integer categoryNo;
	private LrtbGiftCategory category;
	private Listbox itemList;
	private Listitem itemTemplate;
	private CapsTextbox categoryNameTextBox;

	@Override
	public void refresh() {
		category = businessHelper.getRewardBusiness().getCategory(categoryNo);
		refreshItemList();
	}

	@SuppressWarnings("unchecked")
	private void refreshItemList() {
		itemList.getItems().clear();

		Set<LrtbGiftItem> giftItems = category.getLrtbGiftItems();
		Set<LrtbGiftItem> sortedGiftItems = new TreeSet<LrtbGiftItem>(
				new Comparator<LrtbGiftItem>() {
					public int compare(LrtbGiftItem d1, LrtbGiftItem d2) {
						return d1.getGiftItemNo().compareTo(d2.getGiftItemNo());
					}
				});
		sortedGiftItems.addAll(giftItems);

		for (LrtbGiftItem giftItem : sortedGiftItems) {
			final Listitem item = (Listitem) itemTemplate.clone();
			item.setValue(giftItem);

			List<Listcell> cells = item.getChildren();
			cells.get(1).setLabel(giftItem.getItemCode());
			cells.get(2).setLabel(giftItem.getItemName());
			cells.get(3).setLabel(StringUtil.numberToString(
					giftItem.getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			cells.get(4).setLabel(StringUtil.numberToString(
					giftItem.getPoints(), StringUtil.GLOBAL_INTEGER_FORMAT));
			cells.get(5).setLabel(StringUtil.numberToString(
					giftItem.getStock(), StringUtil.GLOBAL_INTEGER_FORMAT));

			if(this.businessHelper.getRewardBusiness().itemWasRedeemed(giftItem))
				cells.get(6).getChildren().clear();
			
			itemList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public EditCategoryWindow() {
		Map params = Executions.getCurrent().getArg();
		categoryNo =  (Integer) params.get("categoryNo");
		logger.info("Gift Category No = " + categoryNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		
		category = businessHelper.getRewardBusiness().getCategory(categoryNo);

		this.categoryNameTextBox.setValue(category.getCategoryName());

		itemList = (Listbox) getFellow("itemList");
		itemTemplate = (Listitem) itemList.getItems().get(0);
		itemTemplate.detach();

		refreshItemList();

		if(!this.checkUriAccess(Uri.ADD_GIFT_ITEM))
			((Button)this.getFellow("newItemBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.MOVE_GIFT_ITEM))
			((Button)this.getFellow("moveItemBtn")).setDisabled(true);
	}

	@Deprecated
	/*
	 * No longer need this method because if item cannot be deleted, the delete button will be hidden.
	 */
	public void deleteItem(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_GIFT_ITEM)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this item?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			businessHelper.getRewardBusiness().deleteItem((LrtbGiftItem) item.getValue());
			item.detach();
			Messagebox.show("Item has been successfully deleted", "Delete Gift Item",
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (RewardItemIsUsedException e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	public void moveItem() throws InterruptedException {
		List<LrtbGiftItem> selectedItems = new ArrayList<LrtbGiftItem>();
		for (Listitem item : (List<Listitem>) itemList.getItems()) {
			List<Listcell> cells = item.getChildren();
			Checkbox checkbox = (Checkbox) cells.get(0).getFirstChild();
			if (checkbox.isChecked()) {
				selectedItems.add((LrtbGiftItem) item.getValue());
			}
		}

		if (selectedItems.size() == 0) {
			Messagebox.show("At least one gift item must be selected", "No Items Selected", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("category", category);
		args.put("selectedItems", selectedItems);
		args.put("owner", this);
		final Window win = (Window) Executions.createComponents(Uri.MOVE_GIFT_ITEM, null, args);
		win.setMaximizable(false);
		win.doModal();
	}

	public void editItem() throws InterruptedException {
		try {
			Listitem selectedItem = itemList.getSelectedItem();
			LrtbGiftItem giftItem = (LrtbGiftItem) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("giftItemNo", giftItem.getGiftItemNo());
			if(this.checkUriAccess(Uri.EDIT_GIFT_ITEM))
				forward(Uri.EDIT_GIFT_ITEM, map);
			else if(this.checkUriAccess(Uri.VIEW_GIFT_ITEM))
				forward(Uri.VIEW_GIFT_ITEM, map);
			else{
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
						"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		} finally {
			itemList.clearSelection();
		}
	}

	public void addItem() throws InterruptedException {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("categoryNo", category.getGiftCategoryNo());
			forward(Uri.ADD_GIFT_ITEM, map);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void update() throws InterruptedException{
		try{
			String newName = categoryNameTextBox.getValue();
			LrtbGiftCategory categoryExample = new LrtbGiftCategory();
			categoryExample.setCategoryName(newName);
			List<LrtbGiftCategory> results = this.businessHelper.getGenericBusiness().getByExample(categoryExample);
			
			if(results.isEmpty() == false)
				if(results.get(0).getGiftCategoryNo().intValue() != category.getGiftCategoryNo().intValue())
					throw new WrongValueException(categoryNameTextBox, "Name has been used.");
			
			category.setCategoryName(newName);
			this.businessHelper.getGenericBusiness().update(category, getUserLoginIdAndDomain());
			
			Messagebox.show(
					"Gift Category Name successfully updated.", "Update Gift Category Name",
					Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect("");
		}
		catch(WrongValueException wve){
			throw wve;
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
