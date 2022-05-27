package com.cdgtaxi.ibs.billing.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.forms.SearchByAccountForm;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class SearchAccountForIssueMiscInvoiceWindow extends SearchByAccountWindow {
	private static final Logger logger = Logger.getLogger(SearchAccountForIssueMiscInvoiceWindow.class);

	private Listbox resultLB;

	@Override
	public void onCreate(CreateEvent ce) throws Exception {
		super.onCreate(ce);
		resultLB = (Listbox)getFellow("resultList");
		resultLB.setPageSize(10);
	}

	public void searchAccount() throws InterruptedException{
		try {
			SearchByAccountForm form = new SearchByAccountForm();
			populateAccountForm(form);
			if (form == null) {
				return;
			}
			if(!form.isAtLeastOneCriteriaSelected()){
				Messagebox.show("Please enter one of the selection criteria",
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			form.setBillable(true);

			displayProcessing();
			List<AmtbAccount> accounts =
				businessHelper.getAccountBusiness().searchAccount(form);

			resultLB.getItems().clear();
			if (resultLB.getListfoot() != null) {
				resultLB.removeChild(resultLB.getListfoot());
			}

			if (accounts.isEmpty()) {
				resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
				return;
			}

			if(accounts.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			
			for (final AmtbAccount account : accounts) {
				Listitem item = new Listitem();
				item.setValue(account);
				AmtbAccount topLevelAccount =
					businessHelper.getAccountBusiness().getTopLevelAccount(account);
				item.appendChild(newListcell(new Integer(topLevelAccount.getCustNo()), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(account.getAccountCategory()));
				item.appendChild(newListcell(account.getAccountName()));

				resultLB.appendChild(item);
			}
			
			if(accounts.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void issueMiscInvoice() throws InterruptedException{
		try {
			Listitem selectedItem = resultLB.getSelectedItem();
			AmtbAccount account = (AmtbAccount) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("account", account);
			forward(Uri.ISSUE_MISC_INVOICE, map);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(ex.toString());
		} finally {
			resultLB.clearSelection();
		}
	}

	@Override
	public void reset() throws InterruptedException {
		super.reset();
		resultLB.getItems().clear();
	}
	
	@Override
	public void refresh() throws InterruptedException {
		reset();
	}
}
