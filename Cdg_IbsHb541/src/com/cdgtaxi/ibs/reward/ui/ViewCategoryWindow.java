package com.cdgtaxi.ibs.reward.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
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
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class ViewCategoryWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(ViewCategoryWindow.class);

	private final Integer categoryNo;
	private LrtbGiftCategory category;
	private Listbox itemList;
	private Listitem itemTemplate;
	private Label categoryName;

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
			cells.get(0).setLabel(giftItem.getItemCode());
			cells.get(1).setLabel(giftItem.getItemName());
			cells.get(2).setLabel(StringUtil.numberToString(
					giftItem.getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			cells.get(3).setLabel(StringUtil.numberToString(
					giftItem.getPoints(), StringUtil.GLOBAL_INTEGER_FORMAT));
			cells.get(4).setLabel(StringUtil.numberToString(
					giftItem.getStock(), StringUtil.GLOBAL_INTEGER_FORMAT));
			
			itemList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public ViewCategoryWindow() {
		Map params = Executions.getCurrent().getArg();
		categoryNo =  (Integer) params.get("categoryNo");
		logger.info("Gift Category No = " + categoryNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		
		category = businessHelper.getRewardBusiness().getCategory(categoryNo);

		this.categoryName.setValue(category.getCategoryName());
		

		itemList = (Listbox) getFellow("itemList");
		itemTemplate = (Listitem) itemList.getItems().get(0);
		itemTemplate.detach();

		refreshItemList();

		if(!this.checkUriAccess(Uri.ADD_GIFT_ITEM))
			((Button)this.getFellow("newItemBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.MOVE_GIFT_ITEM))
			((Button)this.getFellow("moveItemBtn")).setDisabled(true);
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
	
}
