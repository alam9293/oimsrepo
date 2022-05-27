package com.cdgtaxi.ibs.inventory.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.forms.SearchItemForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class SearchItemWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;

	private Listbox itemTypeLB, statusLB, resultLB;
	private Decimalbox serialNoStartDMB, serialNoEndDMB;
	private Button suspensionSubmissionBtn, reactivationSubmissionBtn, voidSubmissionBtn, editExpiryDateBtn;
	private Checkbox checkAll;

	public void afterCompose() {
		Components.wireVariables(this, this);

		if (!this.checkUriAccess(Uri.SUSPEND_ITEM))
			suspensionSubmissionBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.REACTIVATE_ITEM))
			reactivationSubmissionBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.VOID_ITEM))
			voidSubmissionBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.EDIT_ITEM_EXPIRY_DATE))
			editExpiryDateBtn.setDisabled(true);

		statusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<Entry<String, String>> redemptionStatusEntries = NonConfigurableConstants.INVENTORY_ITEM_STATUS
				.entrySet();
		for (Entry<String, String> entry : redemptionStatusEntries)
			statusLB.appendItem(entry.getValue(), entry.getKey());
		if (statusLB.getSelectedItem() == null)
			statusLB.setSelectedIndex(0);

		List<ImtbItemType> itemTypes = this.businessHelper.getInventoryBusiness().getItemTypes();
		for (ImtbItemType itemType : itemTypes) {
			Listitem listItem = new Listitem(itemType.getTypeName(), itemType.getItemTypeNo());
			itemTypeLB.appendChild(listItem);
		}
		if (itemTypeLB.getSelectedItem() == null)
			itemTypeLB.setSelectedIndex(0);
	}

	public void search() throws InterruptedException {
		try {
			resultLB.getItems().clear();
			checkAll.setChecked(false);

			SearchItemForm form = this.buildSearchForm();
			this.displayProcessing();

			List<ImtbItem> results = this.businessHelper.getInventoryBusiness().searchItem(form);
			if (results.size() > 0) {

				if (results.size() > ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert",
							Messagebox.OK, Messagebox.INFORMATION);

				for (ImtbItem itemVoucher : results) {
					Listitem item = new Listitem();
					item.setValue(itemVoucher);

					AmtbAccount account = itemVoucher.getImtbIssue().getImtbIssueReq()
							.getAmtbAccount();
					
					while(account.getAmtbAccount() != null){
						account = account.getAmtbAccount();
					}

					Listcell checkboxCell = new Listcell();
					checkboxCell.appendChild(new Checkbox());
					item.appendChild(checkboxCell);

					item.appendChild(newListcell(account.getCustNo()));
					item.appendChild(newListcell(account.getAccountName()));
					item.appendChild(newListcell(itemVoucher.getImtbItemType().getTypeName()));
					item.appendChild(newListcell(itemVoucher.getSerialNo(),
							StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(itemVoucher.getExpiryDate()));
					item.appendChild(newListcell(itemVoucher.getRedeemPoint()));
					if(itemVoucher.getRedeemTime() != null)
						item.appendChild(newListcell(itemVoucher.getRedeemTime()));
					else
						item.appendChild(newEmptyListcell(DateUtil.getUtilDateForNullComparison(), ""));
					item.appendChild(newListcell(itemVoucher.getCashierId()));
					item.appendChild(newListcell(NonConfigurableConstants.INVENTORY_ITEM_STATUS
							.get(itemVoucher.getStatus())));
					resultLB.appendChild(item);
				}

				if (results.size() > ConfigurableConstants.getMaxQueryResult())
					resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());

				if (resultLB.getListfoot() != null)
					resultLB.removeChild(resultLB.getListfoot());
			} else {
				if (resultLB.getListfoot() == null) {
					resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(9));
				}
			}

			resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultLB.setPageSize(10);
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	private SearchItemForm buildSearchForm() throws WrongValueException {

		SearchItemForm form = new SearchItemForm();

		form.itemTypeNo = (Integer) itemTypeLB.getSelectedItem().getValue();

		if (statusLB.getSelectedItem().getValue().toString().length() > 0)
			form.itemStatus = statusLB.getSelectedItem().getValue().toString();

		BigDecimal serialNoFrom = serialNoStartDMB.getValue();
		BigDecimal serialNoTo = serialNoEndDMB.getValue();
		if (serialNoFrom != null && serialNoTo == null){
			serialNoTo = serialNoFrom;
			serialNoEndDMB.setValue(serialNoTo);
		}
		else if (serialNoTo != null && serialNoFrom == null){
			serialNoFrom = serialNoTo;
			serialNoStartDMB.setValue(serialNoFrom);
		}

		if (serialNoFrom != null && serialNoTo != null)
			if (serialNoFrom.compareTo(serialNoTo) > 0)
				throw new WrongValueException(serialNoStartDMB,
						"Serial No. Start cannot be greater than Serial No. End");

		if (serialNoFrom != null)
			form.serialNoFrom = serialNoFrom;
		if (serialNoTo != null)
			form.serialNoTo = serialNoTo;

		return form;
	}

	@SuppressWarnings("unchecked")
	public void checkAll() {
		List<Listitem> listItems = resultLB.getItems();
		for (Listitem item : listItems) {
			Listcell checkboxCell = (Listcell) item.getFirstChild();
			Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
			if (!checkAll.isChecked())
				checkBox.setChecked(false);
			else
				checkBox.setChecked(true);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void viewItem() throws InterruptedException {
		try {
			ImtbItem item = (ImtbItem) resultLB.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("itemNo", item.getItemNo());
			this.forward(Uri.VIEW_INVENTORY_ITEM, map);
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		this.search();
	}

	public void reset() {
		itemTypeLB.setSelectedIndex(0);
		statusLB.setSelectedIndex(0);
		serialNoStartDMB.setText("");
		serialNoEndDMB.setText("");

		resultLB.getItems().clear();
		if (resultLB.getListfoot() == null) {
			resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
		}
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
		checkAll.setChecked(false);
	}

	@SuppressWarnings("unchecked")
	public void suspend() throws InterruptedException {
		try {
			List<Long> itemNos = new ArrayList<Long>();
			List<ImtbItem> filteredItems = new ArrayList<ImtbItem>();

			List<Listitem> listItems = resultLB.getItems();
			boolean filtered = false;
			for (Listitem item : listItems) {
				Listcell checkboxCell = (Listcell) item.getFirstChild();
				Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
				if (checkBox.isChecked()) {
					ImtbItem itemVoucher = (ImtbItem) item.getValue();
					if (!itemVoucher.getStatus().equals(
							NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED)) {
						filtered = true;
						filteredItems.add(itemVoucher);
					} else
						itemNos.add(itemVoucher.getItemNo());
				}
			}

			this.proceedNext(itemNos, filteredItems, filtered, MassSubmissionWindow.MODE_SUSPENSION);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void reactivate() throws InterruptedException {
		try {
			List<Long> itemNos = new ArrayList<Long>();
			List<ImtbItem> filteredItems = new ArrayList<ImtbItem>();

			List<Listitem> listItems = resultLB.getItems();
			boolean filtered = false;
			for (Listitem item : listItems) {
				Listcell checkboxCell = (Listcell) item.getFirstChild();
				Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
				if (checkBox.isChecked()) {
					ImtbItem itemVoucher = (ImtbItem) item.getValue();
					if (!itemVoucher.getStatus().equals(
							NonConfigurableConstants.INVENTORY_ITEM_STATUS_SUSPENDED)) {
						filtered = true;
						filteredItems.add(itemVoucher);
					} else
						itemNos.add(itemVoucher.getItemNo());
				}
			}

			this.proceedNext(itemNos, filteredItems, filtered,
					MassSubmissionWindow.MODE_REACTIVATION);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void doVoid() throws InterruptedException {
		try {
			List<Long> itemNos = new ArrayList<Long>();
			List<ImtbItem> filteredItems = new ArrayList<ImtbItem>();

			List<Listitem> listItems = resultLB.getItems();
			boolean filtered = false;
			for (Listitem item : listItems) {
				Listcell checkboxCell = (Listcell) item.getFirstChild();
				Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
				if (checkBox.isChecked()) {
					ImtbItem itemVoucher = (ImtbItem) item.getValue();
					if (!itemVoucher.getStatus().equals(
							NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED)) {
						filtered = true;
						filteredItems.add(itemVoucher);
					} else
						itemNos.add(itemVoucher.getItemNo());
				}
			}

			this.proceedNext(itemNos, filteredItems, filtered, MassSubmissionWindow.MODE_VOID);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeRedemption() throws InterruptedException {
		try {
			List<Long> itemNos = new ArrayList<Long>();
			List<ImtbItem> filteredItems = new ArrayList<ImtbItem>();

			List<Listitem> listItems = resultLB.getItems();
			boolean filtered = false;
			for (Listitem item : listItems) {
				Listcell checkboxCell = (Listcell) item.getFirstChild();
				Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
				if (checkBox.isChecked()) {
					ImtbItem itemVoucher = (ImtbItem) item.getValue();
					if (!itemVoucher.getStatus().equals(
							NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED)) {
						filtered = true;
						filteredItems.add(itemVoucher);
					} else
						itemNos.add(itemVoucher.getItemNo());
				}
			}

			this.proceedNext(itemNos, filteredItems, filtered, MassSubmissionWindow.MODE_REMOVE_REDEMPTION);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void editExpiryDate() throws InterruptedException {
		
		try {
			List<Long> itemNos = new ArrayList<Long>();
			List<ImtbItem> filteredItems = new ArrayList<ImtbItem>();

			List<Listitem> listItems = resultLB.getItems();
			boolean filtered = false;
			for (Listitem item : listItems) {
				Listcell checkboxCell = (Listcell) item.getFirstChild();
				Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
				if (checkBox.isChecked()) {
					ImtbItem itemVoucher = (ImtbItem) item.getValue();
					if (!itemVoucher.getStatus().equals(
							NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED)) {
						filtered = true;
						filteredItems.add(itemVoucher);
					} else
						itemNos.add(itemVoucher.getItemNo());
				}
			}
			
			this.proceedNext(itemNos, filteredItems, filtered, MassSubmissionWindow.MODE_EDIT_EXPIRY_DATE);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void proceedNext(List<Long> itemNos, List<ImtbItem> filteredItems, boolean filtered,
			String mode) throws Exception {
		if (itemNos.isEmpty()) {
			if (!filtered)
				Messagebox.show(
						"Please make at least one selection before submitting for further actions",
						"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
			else
				Messagebox
						.show("After filtering out those that are unable to go "
								+ mode
								+ ", you left with no selections. Please make at least one valid selection before submitting for further actions.",
								"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			if (filtered) {
				boolean firstItem = true;
				String serialNos = "";
				for (ImtbItem item : filteredItems) {
					if (firstItem) {
						firstItem = false;
						serialNos += item.getSerialNo().toString();
					} else
						serialNos += ", " + item.getSerialNo().toString();
				}

				int response = Messagebox
						.show("One or more selections are not valid for further actions and have been filtered out, do you still want to proceed with the rest? The following serial nos that are filtered out: "
								+ serialNos, "Suspend Taxi Vouchers", Messagebox.OK
								| Messagebox.CANCEL, null);
				if (response == Messagebox.OK) {
					Map<String, Object> args = new HashMap<String, Object>();
					args.put("itemNos", itemNos);
					args.put("mode", mode);

					final CommonWindow win = (CommonWindow) Executions.createComponents(
							Uri.MASS_SUBMISSION_ITEM, this, args);
					win.setMaximizable(false);
					win.doModal();
				}
			} else {
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("itemNos", itemNos);
				args.put("mode", mode);

				final CommonWindow win = (CommonWindow) Executions.createComponents(
						Uri.MASS_SUBMISSION_ITEM, this, args);
				win.setMaximizable(false);
				win.doModal();
			}
		}
	}
}
