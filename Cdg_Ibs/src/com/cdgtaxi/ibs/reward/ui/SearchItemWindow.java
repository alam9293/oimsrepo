package com.cdgtaxi.ibs.reward.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.billing.ui.SearchByAccountWindow;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;
import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.model.forms.SearchGiftItemForm;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class SearchItemWindow extends SearchByAccountWindow {
	private static final Logger logger = Logger.getLogger(SearchItemWindow.class);

	private AmtbAccount account;

	private Label previousPointsLabel, currentPointsLabel;
	private Combobox contactPersonCB;
	private Row pointsRow, contactRow;

	private Combobox categoryCB;
	private Textbox itemCodeTB, itemNameTB;
	private Intbox itemPointsFromIB, itemPointsToIB;
	private Decimalbox itemPriceFromDB, itemPriceToDB;
	private Listbox resultLB;

	@Override
	public void onCreate(CreateEvent ce) throws Exception{
		super.onCreate(ce);

		previousPointsLabel = (Label) getFellow("previousPointsLabel");
		currentPointsLabel = (Label) getFellow("currentPointsLabel");
		contactPersonCB = (Combobox) getFellow("contactPersonCB");
		pointsRow = (Row) getFellow("pointsRow");
		contactRow = (Row) getFellow("contactRow");

		categoryCB = (Combobox) getFellow("categoryCB");
		itemCodeTB = (Textbox) getFellow("itemCodeTB");
		itemNameTB = (Textbox) getFellow("itemNameTB");
		itemPointsFromIB = (Intbox) getFellow("itemPointsFromIB");
		itemPointsToIB = (Intbox) getFellow("itemPointsToIB");
		itemPriceFromDB = (Decimalbox) getFellow("itemPriceFromDB");
		itemPriceToDB = (Decimalbox) getFellow("itemPriceToDB");

		List<LrtbGiftCategory> categories = businessHelper.getRewardBusiness().getCategories();
		for (LrtbGiftCategory category : categories) {
			Comboitem item = new Comboitem(category.getCategoryName());
			item.setValue(category);
			categoryCB.appendChild(item);
		}

		resultLB = (Listbox) getFellow("resultList");
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}

	@Override
	public void onSelectAccountName() throws InterruptedException {
		
		//Fix to bypass IE6 issue with double spacing
		if(accountNameComboBox.getChildren().size()==1)
			accountNameComboBox.setSelectedIndex(0);
		
		if(accountNameComboBox.getSelectedItem()!=null){
			account = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			accountNoIntBox.setText(account.getCustNo());

			account = businessHelper.getRewardBusiness().getAccount(this.account.getAccountNo());
			if(account == null){
				Messagebox.show("The corporate/applicant account has either no rewards account or all rewards accounts are expired therefore redemption of gifts is not allowed.", "Redeem Gift", Messagebox.OK, Messagebox.ERROR);
				this.resetSeletedAccount();
				return;
			}
			
			Integer currPoints = businessHelper.getRewardBusiness().calcCurrPoints(this.account);
			currentPointsLabel.setValue(StringUtil.numberToString(
					currPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

			Integer prevPoints = businessHelper.getRewardBusiness().calcPrevPoints(this.account);
			previousPointsLabel.setValue(StringUtil.numberToString(
					prevPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

			contactPersonCB.setRawValue(null);
			contactPersonCB.getItems().clear();
			for (AmtbContactPerson person : this.account.getAmtbContactPersons()) {
				String name = person.getMainContactName();
				if(person.getSubContactName()!= null && person.getSubContactName().length() > 0)
					name += " / " + person.getSubContactName();
				Comboitem item = new Comboitem(name);
				item.setValue(person);
				contactPersonCB.appendChild(item);
			}
			if (contactPersonCB.getItems().size() == 1) {
				contactPersonCB.setSelectedIndex(0);
			}

			pointsRow.setVisible(true);
			contactRow.setVisible(true);
			
			AmtbAcctStatus acctStatus = this.businessHelper.getRewardBusiness().getAccountLatestStatus(account.getCustNo());
			if (acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
				Messagebox.show("The corporate/applicant account is closed, redemption of gifts is not allowed.", "Redeem Gift", Messagebox.OK, Messagebox.ERROR);
				this.resetSeletedAccount();
				return;
			}
			else if (acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				Messagebox.show("The corporate/applicant account is pending activation, redemption of gifts is not allowed.", "Redeem Gift", Messagebox.OK, Messagebox.ERROR);
				this.resetSeletedAccount();
				return;
			}
		} else {
			previousPointsLabel.setValue("-");
			currentPointsLabel.setValue("-");
			contactPersonCB.setRawValue(null);
			contactPersonCB.getItems().clear();

			pointsRow.setVisible(false);
			contactRow.setVisible(false);
		}
	}
	public void onSelectAccountNameReset() throws InterruptedException {
		
		if(accountNameComboBox.getSelectedItem()!=null){
			account = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			accountNoIntBox.setText(account.getCustNo());

			account = businessHelper.getRewardBusiness().getAccount(this.account.getAccountNo());
			if(account == null){
				Messagebox.show("The corporate/applicant account has either no rewards account or all rewards accounts are expired therefore redemption of gifts is not allowed.", "Redeem Gift", Messagebox.OK, Messagebox.ERROR);
				this.resetSeletedAccount();
				return;
			}
			
			Integer currPoints = businessHelper.getRewardBusiness().calcCurrPoints(this.account);
			currentPointsLabel.setValue(StringUtil.numberToString(
					currPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

			Integer prevPoints = businessHelper.getRewardBusiness().calcPrevPoints(this.account);
			previousPointsLabel.setValue(StringUtil.numberToString(
					prevPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

			contactPersonCB.setRawValue(null);
			contactPersonCB.getItems().clear();
			for (AmtbContactPerson person : this.account.getAmtbContactPersons()) {
				String name = person.getMainContactName();
				if(person.getSubContactName()!= null && person.getSubContactName().length() > 0)
					name += " / " + person.getSubContactName();
				Comboitem item = new Comboitem(name);
				item.setValue(person);
				contactPersonCB.appendChild(item);
			}
			if (contactPersonCB.getItems().size() == 1) {
				contactPersonCB.setSelectedIndex(0);
			}

			pointsRow.setVisible(true);
			contactRow.setVisible(true);
			
			AmtbAcctStatus acctStatus = this.businessHelper.getRewardBusiness().getAccountLatestStatus(account.getCustNo());
			if (acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
				Messagebox.show("The corporate/applicant account is closed, redemption of gifts is not allowed.", "Redeem Gift", Messagebox.OK, Messagebox.ERROR);
				this.resetSeletedAccount();
				return;
			}
			else if (acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				Messagebox.show("The corporate/applicant account is pending activation, redemption of gifts is not allowed.", "Redeem Gift", Messagebox.OK, Messagebox.ERROR);
				this.resetSeletedAccount();
				return;
			}
		} else {
			previousPointsLabel.setValue("-");
			currentPointsLabel.setValue("-");
			contactPersonCB.setRawValue(null);
			contactPersonCB.getItems().clear();

			pointsRow.setVisible(false);
			contactRow.setVisible(false);
		}
	}

	public void searchItem() throws InterruptedException{
		categoryCB.getValue(); //just to trigger validation

		try {
			SearchGiftItemForm form = buildSearchForm();
			if (form == null) {
				return;
			}

			displayProcessing();
			List<LrtbGiftItem> giftItems =
				businessHelper.getRewardBusiness().searchItem(form);

			//			List<Listheader> children =
			//				resultLB.getListhead().getChildren();
			//			Listheader header = new Listheader("Test");
			//			ListitemComparator comparator;
			//			header.setSortAscending(new ListitemComparator(header, true, true, true));
			//			header.setSortDescending(new ListitemComparator(header, false, true, true));
			//			resultLB.getListhead().appendChild(header);

			resultLB.getItems().clear();
			if (resultLB.getListfoot() != null) {
				resultLB.removeChild(resultLB.getListfoot());
			}

			if (giftItems.isEmpty()) {
				resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(
						resultLB.getListhead().getChildren().size()));
				return;
			}

			if(giftItems.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			
			for (LrtbGiftItem giftItem : giftItems) {
				Listitem item = newListitem(giftItem);

				//				item.appendChild(new Listcell(giftItem.getItemCode()));
				//				item.appendChild(new Listcell(giftItem.getItemName()));

				//				cell = new Listcell(StringUtil.numberToString(
				//						giftItem.getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				//				cell.setValue(giftItem.getPrice());
				//				item.appendChild(cell);

				//				cell = new Listcell(StringUtil.numberToString(
				//						giftItem.getPoints(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				//				cell.setValue(giftItem.getPoints());
				//				item.appendChild(cell);

				//				cell = new Listcell(StringUtil.numberToString(
				//						giftItem.getStock(), StringUtil.GLOBAL_INTEGER_FORMAT));
				//				cell.setValue(giftItem.getStock());
				item.appendChild(newListcell(giftItem.getItemCode()));
				item.appendChild(newListcell(giftItem.getItemName()));
				item.appendChild(newListcell(giftItem.getPrice()));
				item.appendChild(newListcell(giftItem.getPoints()));
				item.appendChild(newListcell(giftItem.getStock()));

				resultLB.appendChild(item);
			}
			
			if(giftItems.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	protected SearchGiftItemForm buildSearchForm() throws InterruptedException {
		SearchGiftItemForm form = new SearchGiftItemForm();

		if (itemPointsFromIB.getValue() != null
				&& itemPointsToIB.getValue() != null
				&& itemPointsFromIB.getValue() > itemPointsToIB.getValue()) {
			Messagebox.show("Item Points To shouldn't be less than Item Points From",
					"Error", Messagebox.OK, Messagebox.INFORMATION);
			return null;
		}

		if (itemPriceFromDB.getValue() != null
				&& itemPriceToDB.getValue() != null
				&& itemPriceFromDB.getValue().compareTo(itemPriceToDB.getValue()) > 0) {
			Messagebox.show("Item Price To shouldn't be less than Item Price From",
					"Error", Messagebox.OK, Messagebox.INFORMATION);
			return null;
		}

		LrtbGiftCategory category = (LrtbGiftCategory) categoryCB.getSelectedItem().getValue();
		form.setCategoryNo(category.getGiftCategoryNo());
		form.setItemCode(itemCodeTB.getValue());
		form.setItemName(itemNameTB.getValue());
		form.setPointsFrom(itemPointsFromIB.getValue());
		form.setPointsTo(itemPointsToIB.getValue());
		form.setPriceFrom(itemPriceFromDB.getValue());
		form.setPriceTo(itemPriceToDB.getValue());

		return form;
	}

	//	public void updateInvoiceNoTo() {
	//		if (invoiceNoToLB.getValue() == null) {
	//			invoiceNoToLB.setValue(invoiceNoFromLB.getValue());
	//		}
	//	}

	//	public void updateInvoiceDateTo() {
	//		if (invoiceDateToDB.getValue() == null) {
	//			invoiceDateToDB.setValue(invoiceDateFromDB.getValue());
	//		}
	//	}

	public void redeemItem() throws InterruptedException{
		if (accountNameComboBox.getSelectedItem() == null) {
			Messagebox.show("Please specify an Account", "No Account selected", Messagebox.OK, Messagebox.ERROR);
			resultLB.clearSelection();
			return;
		}

		AmtbAcctStatus acctStatus = this.businessHelper.getRewardBusiness().getAccountLatestStatus(account.getCustNo());
		if (acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
			Messagebox.show("The corporate/applicant account is closed, redemption of gifts is not allowed.", "Redeem Gift", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		else if (acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
			Messagebox.show("The corporate/applicant account is pending activation, redemption of gifts is not allowed.", "Redeem Gift", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		if(contactPersonCB.getSelectedItem()==null)
			throw new WrongValueException(contactPersonCB, "* Mandatory field");
		
		try {
			Listitem selectedItem = resultLB.getSelectedItem();
			LrtbGiftItem giftItem = (LrtbGiftItem) selectedItem.getValue();
			AmtbAccount account = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			AmtbContactPerson contactPerson = (AmtbContactPerson) contactPersonCB.getSelectedItem().getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("accountNo", account.getAccountNo());
			map.put("contactPersonNo", contactPerson.getContactPersonNo());
			map.put("giftItemNo", giftItem.getGiftItemNo());
			forward(Uri.REDEEM_GIFT_ITEM, map);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		finally {
			resultLB.clearSelection();
		}
	}

	@Override
	public void reset() throws InterruptedException {
		this.resetSeletedAccount();

		categoryCB.setRawValue(null);
		itemCodeTB.setRawValue(null);
		itemNameTB.setRawValue(null);
		itemPointsFromIB.setRawValue(null);
		itemPointsToIB.setRawValue(null);
		itemPriceFromDB.setRawValue(null);
		itemPriceToDB.setRawValue(null);
		resultLB.getItems().clear();
	}
	
	public void resetSeletedAccount() throws InterruptedException{
		accountNoIntBox.setRawValue(null);
		accountNameComboBox.setSelectedItem(null);
		accountNameComboBox.setRawValue(null);
		accountNameComboBox.setValue(null);
		contactPersonCB.setRawValue(null);
		contactPersonCB.setSelectedItem(null);
		
		onSelectAccountNameReset();
	}

	@Override
	public void refresh() throws InterruptedException {
		account = businessHelper.getRewardBusiness().getAccount(
				account.getAccountNo());

		Integer currPoints = businessHelper.getRewardBusiness().calcCurrPoints(account);
		currentPointsLabel.setValue(StringUtil.numberToString(
				currPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

		Integer prevPoints = businessHelper.getRewardBusiness().calcPrevPoints(account);
		previousPointsLabel.setValue(StringUtil.numberToString(
				prevPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

		searchItem();
	}
}
