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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemReq;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.forms.SearchItemRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

public class SearchItemRequestWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;

	private Listbox itemTypeLB, statusLB, resultLB;
	private Decimalbox serialNoStartDMB, serialNoEndDMB;
	private Intbox accountNoIB;
	private CapsTextbox accountNameTB;
	private Checkbox checkAll;

	public void afterCompose() {
		Components.wireVariables(this, this);

		statusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<Entry<String, String>> statusEntries = NonConfigurableConstants.INVENTORY_ITEM_APPROVAL_STATUS
				.entrySet();
		for (Entry<String, String> entry : statusEntries)
			statusLB.appendItem(entry.getValue(), entry.getKey());
		if (statusLB.getSelectedItem() == null)
			statusLB.setSelectedIndex(0);

		itemTypeLB.appendChild(ComponentUtil.createNotRequiredListItem(null));
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

			SearchItemRequestForm form = this.buildSearchForm();
			this.displayProcessing();

			List<ImtbItemReq> results = this.businessHelper.getInventoryBusiness()
					.searchItemRequest(form);
			if (results.size() > 0) {

				if (results.size() > ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert",
							Messagebox.OK, Messagebox.INFORMATION);

				for (ImtbItemReq itemReq : results) {
					Listitem item = new Listitem();
					item.setValue(itemReq);

					ImtbItem itemVoucher = itemReq.getImtbItem();
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
					item.appendChild(newListcell(itemVoucher.getImtbIssue().getExpiryDate()));
					item.appendChild(newListcell(NonConfigurableConstants.INVENTORY_ITEM_STATUS
							.get(itemVoucher.getStatus())));
					item.appendChild(newListcell(itemReq.getRequestor()));
					item.appendChild(newListcell(itemReq.getRequestDt()));
					item.appendChild(newListcell(itemReq.getMstbMasterTableByReason()
							.getMasterValue()));

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

	private SearchItemRequestForm buildSearchForm() throws WrongValueException {

		SearchItemRequestForm form = new SearchItemRequestForm();

		form.itemTypeNo = (Integer) itemTypeLB.getSelectedItem().getValue();

		if (statusLB.getSelectedItem().getValue().toString().length() > 0)
			form.itemStatus = statusLB.getSelectedItem().getValue().toString();

		BigDecimal serialNoFrom = serialNoStartDMB.getValue();
		BigDecimal serialNoTo = serialNoEndDMB.getValue();
		if (serialNoFrom != null && serialNoTo == null) {
			serialNoTo = serialNoFrom;
			serialNoEndDMB.setValue(serialNoTo);
		} else if (serialNoTo != null && serialNoFrom == null) {
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

		form.custNo = accountNoIB.getText();
		form.accountName = accountNameTB.getValue();

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
	public void viewRequest() throws InterruptedException {
		try {
			ImtbItemReq itemReq = (ImtbItemReq) resultLB.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("reqNo", itemReq.getReqNo());
			this.forward(Uri.VIEW_INVENTORY_ITEM_REQUEST, map);
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

	public void reset() throws InterruptedException {
		itemTypeLB.setSelectedIndex(0);
		statusLB.setSelectedIndex(0);
		serialNoStartDMB.setText("");
		serialNoEndDMB.setText("");
		accountNoIB.setText("");
		accountNameTB.setText("");
		checkAll.setChecked(false);

		this.search();
	}

	@SuppressWarnings("unchecked")
	public void approve() throws InterruptedException {
		try {
			List<Integer> reqNos = new ArrayList<Integer>();
			List<Listitem> listItems = resultLB.getItems();
			for (Listitem item : listItems) {
				Listcell checkboxCell = (Listcell) item.getFirstChild();
				Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
				if (checkBox.isChecked()) {
					ImtbItemReq itemReq = (ImtbItemReq) item.getValue();
					reqNos.add(itemReq.getReqNo());
				}
			}
			this.proceedNext(reqNos, MassApprovalItemReqWindow.MODE_APPROVE);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void reject() throws InterruptedException {
		try {
			List<Integer> reqNos = new ArrayList<Integer>();
			List<Listitem> listItems = resultLB.getItems();
			for (Listitem item : listItems) {
				Listcell checkboxCell = (Listcell) item.getFirstChild();
				Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
				if (checkBox.isChecked()) {
					ImtbItemReq itemReq = (ImtbItemReq) item.getValue();
					reqNos.add(itemReq.getReqNo());
				}
			}
			this.proceedNext(reqNos, MassApprovalItemReqWindow.MODE_REJECT);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	private void proceedNext(List<Integer> reqNos, String mode) throws Exception {
		if (reqNos.isEmpty()) {
			Messagebox.show(
					"Please make at least one selection before submitting for further actions.",
					"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("reqNos", reqNos);
			args.put("mode", mode);

			final CommonWindow win = (CommonWindow) Executions.createComponents(
					Uri.MASS_APPROVAL_ITEM_REQ, this, args);
			win.setMaximizable(false);
			win.doModal();
		}
	}
}
