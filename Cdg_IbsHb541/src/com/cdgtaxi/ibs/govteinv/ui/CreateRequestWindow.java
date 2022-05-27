package com.cdgtaxi.ibs.govteinv.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Longbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.model.IttbGovtEinvoiceReq;
import com.cdgtaxi.ibs.common.model.forms.SearchGovtEInvRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class CreateRequestWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateRequestWindow.class);

	private Intbox billGenReqNoIB, accountNoIB;
	private Datebox invoiceDateDB;
	private Longbox invoiceNoFromLB, invoiceNoToLB;
	private Combobox accountNameCB;

	// After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	/**
	 * To clear other inputs based on the given input mode.<br>
	 * 1 = Bill Gen Request No.
	 * 2 = Invoice Date
	 * 3 = Invoice No. Range
	 * 4 = Account No / Name
	 */
	public void clearOtherInputs(Integer inputMode) {
		switch (inputMode) {
		case 1:
			// accountNoIB.setText("");
			// accountNameCB.getChildren().clear();
			// accountNameCB.setSelectedItem(null);
			// accountNameCB.setText("");
			invoiceDateDB.setText("");
			invoiceNoFromLB.setText("");
			invoiceNoToLB.setText("");
			break;
		case 2:
			// accountNoIB.setText("");
			// accountNameCB.getChildren().clear();
			// accountNameCB.setSelectedItem(null);
			// accountNameCB.setText("");
			billGenReqNoIB.setText("");
			invoiceNoFromLB.setText("");
			invoiceNoToLB.setText("");
			break;
		case 3:
			// accountNoIB.setText("");
			// accountNameCB.getChildren().clear();
			// accountNameCB.setSelectedItem(null);
			// accountNameCB.setText("");
			billGenReqNoIB.setText("");
			invoiceDateDB.setText("");
			break;
		// case 4:
		// billGenReqNoIB.setText("");
		// invoiceNoFromLB.setText("");
		// invoiceNoToLB.setText("");
		// invoiceDateDB.setText("");
		// break;
		default:
			break;
		}
	}
	

	public void createRequest() throws InterruptedException {
		logger.info("");

		try {
			// Default range value if either one is empty
			if (invoiceNoFromLB.getValue() != null && invoiceNoToLB.getValue() == null)
				invoiceNoToLB.setValue(invoiceNoFromLB.getValue());
			if (invoiceNoToLB.getValue() != null && invoiceNoFromLB.getValue() == null)
				invoiceNoFromLB.setValue(invoiceNoToLB.getValue());

			// Mandatory field validation
			if (billGenReqNoIB.getValue() == null && invoiceDateDB.getValue() == null
					&& invoiceNoFromLB.getValue() == null)
				throw new WrongValueException(
						"Either Bill Gen request no. or Invoice No Range or Invoice Date is mandatory");

			// Validation check - prevent duplicate pending request
			SearchGovtEInvRequestForm form = new SearchGovtEInvRequestForm();
			
			// Create new request and default the status
			IttbGovtEinvoiceReq newRequest = new IttbGovtEinvoiceReq();
			newRequest.setStatus(NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS_PENDING);
			form.status = NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS_PENDING;

			// Capture input into request
			if (billGenReqNoIB.getValue() != null) {
				List<BmtbBillGenReq> results = this.businessHelper.getBillGenBusiness().searchRequest(
						billGenReqNoIB.getValue(), null, null, null, null, null);
				if (results.isEmpty())
					throw new WrongValueException(billGenReqNoIB, "No bill gen request found!");
				else {
					BmtbBillGenReq req = results.get(0);
					if(!NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_COMPLETED.equals(req.getStatus()) &&
							!NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_REGENERATED.equals(req.getStatus())) {
						throw new WrongValueException("Only those Completed or Regenerated status of Bill Gen is accepted!");
					}
				}
				
				newRequest.setBmtbBillGenReq(results.get(0));
				form.billGenRequestNo = results.get(0).getReqNo();
			} 
			else if (invoiceDateDB.getValue() != null){
				newRequest.setInvoiceDate(DateUtil.convertUtilDateToSqlDate(invoiceDateDB.getValue()));
				form.invoiceDate = DateUtil.convertUtilDateToSqlDate(invoiceDateDB.getValue());
			}
			else if (invoiceNoFromLB.getValue() != null) {
				newRequest.setInvoiceNoFrom(invoiceNoFromLB.getValue());
				newRequest.setInvoiceNoTo(invoiceNoToLB.getValue());
				form.invoiceNoFrom = invoiceNoFromLB.getValue();
				form.invoiceNoTo = invoiceNoToLB.getValue();
			}

			if (accountNameCB.getSelectedItem() != null){
				newRequest.setAmtbAccount((AmtbAccount) accountNameCB.getSelectedItem().getValue());
				form.accountNo = ((AmtbAccount) accountNameCB.getSelectedItem().getValue()).getAccountNo();
			}

			// Performing Validation Check
			if (this.businessHelper.getAdminBusiness().searchGovtEInvRequest(form).size() > 0) {
				throw new WrongValueException(
						"There is an existing pending/pending progress/in progress request with the same parameters. Creating the same request is not allowed until the previous request has been completed.");
			} else {
				form.status = NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS_PENDING_PROGRESS;
				if (this.businessHelper.getAdminBusiness().searchGovtEInvRequest(form).size() > 0)
					throw new WrongValueException(
							"There is an existing pending/pending progress/in progress request with the same parameters. Creating the same request is not allowed until the previous request has been completed.");
				else {
					form.status = NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS_IN_PROGRESS;
					if (this.businessHelper.getAdminBusiness().searchGovtEInvRequest(form).size() > 0)
						throw new WrongValueException(
								"There is an existing pending/pending progress/in progress request with the same parameters. Creating the same request is not allowed until the previous request has been completed.");
				}
			}

			Integer requestNo = (Integer) this.businessHelper.getGenericBusiness().save(newRequest,
					CommonWindow.getUserLoginIdAndDomain());

			Messagebox.show("New request(" + requestNo + ") successfully created",
					"Create Govt eInv Request", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} catch (WrongValueException wve) {
			throw wve;
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
			holfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void searchBillableAccountByAccountName(String name) throws InterruptedException {
		logger.info("");

		// only begin new search if input is greater than 2
		if (name.length() < 3) {
			return;
		}

		// accountName still the same as selected one, skip
		if (accountNameCB.getSelectedItem() != null) {
			AmtbAccount selectedAccount = (AmtbAccount) accountNameCB.getSelectedItem().getValue();
			if (name.equals(selectedAccount.getAccountName() + " (" + selectedAccount.getCustNo() + ")")) {
				return;
			}
		}

		// clear textbox for a new search
		accountNoIB.setText("");
		// Clear list for every new search
		accountNameCB.getChildren().clear();

		try {
			List<AmtbAccount> accounts = this.businessHelper.getBillGenBusiness()
					.getBilliableAccountOnlyTopLevelWithEffectiveEntity(null, name);
			for (AmtbAccount account : accounts) {
				Comboitem item = new Comboitem(account.getAccountName() + " (" + account.getCustNo() + ")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if (accounts.size() == 1) {
				accountNameCB.setSelectedIndex(0);
			} else
				accountNameCB.open();
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchBillableAccountByAccountNo() throws InterruptedException {
		logger.info("");

		Integer accountNo = accountNoIB.getValue();

		if (accountNo == null)
			return;

		// accountName still the same as selected one, skip
		if (accountNameCB.getSelectedItem() != null) {
			AmtbAccount selectedAccount = (AmtbAccount) accountNameCB.getSelectedItem().getValue();
			if (accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		// Clear combobox for a new search
		accountNameCB.setText("");
		// Clear list for every new search
		accountNameCB.getChildren().clear();

		try {
			List<AmtbAccount> accounts = this.businessHelper.getBillGenBusiness()
					.getBilliableAccountOnlyTopLevelWithEffectiveEntity(accountNo.toString(), null);
			for (AmtbAccount account : accounts) {
				Comboitem item = new Comboitem(account.getAccountName() + " (" + account.getCustNo() + ")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if (accounts.size() == 1) {
				accountNameCB.setSelectedIndex(0);
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectAccountName() throws InterruptedException {
		logger.info("");

		try {
			if (accountNameCB.getSelectedItem() != null) {
				AmtbAccount selectedAccount = (AmtbAccount) accountNameCB.getSelectedItem().getValue();
				accountNoIB.setText(selectedAccount.getCustNo());
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {

	}
}
