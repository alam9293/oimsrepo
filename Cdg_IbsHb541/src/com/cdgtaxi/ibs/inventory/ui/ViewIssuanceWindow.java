package com.cdgtaxi.ibs.inventory.ui;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbIssue;
import com.cdgtaxi.ibs.common.model.ImtbIssueReq;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbStock;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewIssuanceWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ViewIssuanceWindow.class);

	private final ImtbIssue issue;

	@SuppressWarnings("unchecked")
	public ViewIssuanceWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer stockNo =  (Integer) params.get("stockNo");
		logger.info("Request ID = " + stockNo);
		issue = businessHelper.getInventoryBusiness().getIssuanceByStockNo(stockNo);
	}

	public void onCreate() {
		Label accountNoLabel = (Label) getFellow("accountNoLabel");
		Label accountTypeLabel = (Label) getFellow("accountTypeLabel");
		Label accountNameLabel = (Label) getFellow("accountNameLabel");
		Label divisionNameLabel = (Label) getFellow("divisionNameLabel");
		Label departmentNameLabel = (Label) getFellow("departmentNameLabel");
		Label serialStartLabel = (Label) getFellow("serialStartLabel");
		Label serialEndLabel = (Label) getFellow("serialEndLabel");
		Label expiryDateLabel = (Label) getFellow("expiryDateLabel");
		Label handlingFeeLabel = (Label) getFellow("handlingFeeLabel");
		Label discountLabel = (Label) getFellow("discountLabel");
		Listbox itemList = (Listbox) getFellow("itemList");
		itemList.setPageSize(10);

		ImtbIssueReq request = issue.getImtbIssueReq();
		ImtbStock stock = issue.getImtbStock();

		// Account information
		AmtbAccount account = request.getAmtbAccount();
		String accountCategory = account.getAccountCategory();
		accountTypeLabel.setValue(NonConfigurableConstants.ACCOUNT_CATEGORY.get(accountCategory));
		if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
				|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
			accountNoLabel.setValue(account.getCustNo());
			accountNameLabel.setValue(account.getAccountName());
		} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)
				|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
			accountNoLabel.setValue(account.getAmtbAccount().getCustNo());
			accountNameLabel.setValue(account.getAmtbAccount().getAccountName());
			divisionNameLabel.setValue(account.getAccountName());

			divisionNameLabel.setVisible(true);
			divisionNameLabel.getPreviousSibling().setVisible(true);
		} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
			accountNoLabel.setValue(account.getAmtbAccount().getAmtbAccount().getCustNo());
			accountNameLabel.setValue(account.getAmtbAccount().getAmtbAccount().getAccountName());
			divisionNameLabel.setValue(account.getAmtbAccount().getAccountName());
			departmentNameLabel.setValue(account.getAccountName());

			divisionNameLabel.setVisible(true);
			departmentNameLabel.setVisible(true);
			divisionNameLabel.getPreviousSibling().setVisible(true);
			departmentNameLabel.getPreviousSibling().setVisible(true);
			departmentNameLabel.getParent().setVisible(true);
		}

		serialStartLabel.setValue(StringUtil.numberToString(stock.getSerialNoStart(), "#0"));
		serialEndLabel.setValue(StringUtil.numberToString(stock.getSerialNoEnd(), "#0"));
		String expiryDate = (issue.getExpiryDate() != null) ? DateUtil.convertUtilDateToStr(issue.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT) : "-";
		expiryDateLabel.setValue(expiryDate);
		handlingFeeLabel.setValue(StringUtil.numberToString(
				issue.getHandlingFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		discountLabel.setValue(StringUtil.numberToString(
				issue.getDiscount(), StringUtil.GLOBAL_DECIMAL_FORMAT));

		// Item listing
		Set<ImtbItem> invenItems = issue.getImtbItems();
		Set<ImtbItem> sortedInvenItems = new TreeSet<ImtbItem>(
				new Comparator<ImtbItem>() {
					public int compare(ImtbItem d1, ImtbItem d2) {
						return d1.getSerialNo().compareTo(d2.getSerialNo());
					}
				});
		sortedInvenItems.addAll(invenItems);

		for (ImtbItem invenItem : sortedInvenItems) {
			Listitem item = new Listitem();
			item.setValue(invenItem);
			item.appendChild(new Listcell(invenItem.getSerialNo().toString()));
			String status = NonConfigurableConstants.INVENTORY_ITEM_STATUS.get(
					invenItem.getStatus());
			item.appendChild(new Listcell(status));
			item.appendChild(new Listcell(DateUtil.convertDateToStr(
					invenItem.getUpdateDt(), DateUtil.LAST_UPDATED_DATE_FORMAT)));
			itemList.appendChild(item);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
