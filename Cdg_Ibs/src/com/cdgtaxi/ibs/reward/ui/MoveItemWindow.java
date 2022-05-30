package com.cdgtaxi.ibs.reward.ui;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;
import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;


@SuppressWarnings("serial")
public class MoveItemWindow extends CommonWindow implements AfterCompose {
	private static final Logger logger = Logger.getLogger(MoveItemWindow.class);
	private final LrtbGiftCategory giftCategory;
	private final List<LrtbGiftItem> selectedItems;
	private final CommonWindow owner;

	private Combobox newCategoryCB;

	@Override
	public void refresh() throws InterruptedException {
	}

	@SuppressWarnings("unchecked")
	public MoveItemWindow() {
		Map params = Executions.getCurrent().getArg();
		giftCategory = (LrtbGiftCategory) params.get("category");
		selectedItems = (List<LrtbGiftItem>) params.get("selectedItems");
		owner = (CommonWindow) params.get("owner");

		logger.info("Moving gift " + selectedItems.size() + " items");
	}

	public void afterCompose() {
		Label currentCategoryLabel = (Label) getFellow("currentCategoryLabel");
		currentCategoryLabel.setValue(giftCategory.getCategoryName());

		newCategoryCB = (Combobox) getFellow("newCategoryCB");
		List<LrtbGiftCategory> categories = businessHelper.getRewardBusiness().getCategories();
		for (LrtbGiftCategory category : categories) {
			if (category.getGiftCategoryNo().equals(giftCategory.getGiftCategoryNo())) {
				continue;
			}
			Comboitem item = new Comboitem(category.getCategoryName());
			item.setValue(category);
			newCategoryCB.appendChild(item);
		}
	}

	public void ok() throws InterruptedException{
		newCategoryCB.getValue(); // just to trigger validation
		LrtbGiftCategory newCategory = (LrtbGiftCategory) newCategoryCB.getSelectedItem().getValue();

		try {
			businessHelper.getRewardBusiness().moveItems(selectedItems, newCategory);
			detach();
			owner.refresh();
			Messagebox.show("Items have been successfully moved to " +
					newCategory.getCategoryName() + " category",
					"Move Gift Items", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}
}
