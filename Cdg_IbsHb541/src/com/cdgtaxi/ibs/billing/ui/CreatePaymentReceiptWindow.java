package com.cdgtaxi.ibs.billing.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.forms.InvoicePaymentDetail;
import com.cdgtaxi.ibs.common.model.forms.PaymentInfo;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class CreatePaymentReceiptWindow extends CommonWindow {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreatePaymentReceiptWindow.class);
	private static final String SELF = "createPaymentWindow";
	private final Map<String, ArrayList<Component>> paymentDetailMap = new HashMap<String, ArrayList<Component>>();
	private String currentSelectedPaymentMode = "";
	private BigDecimal previousAppliedAmount = new BigDecimal(0);
	private BigDecimal selectedAmount = new BigDecimal(0);
	private Integer selectedIndex = null;

	@SuppressWarnings("unchecked")
	public void createPayment() throws InterruptedException {
		logger.info("");
		this.displayProcessing();
		Button createPymtBtn = (Button) this.getFellow("createPymtBtn");
		createPymtBtn.setDisabled(true);

		try {
			// Build Payment Info first as it will do data validation as well
			PaymentInfo paymentInfo = this.buildPaymentInfo();

			Listbox paymentListBox = (Listbox) this.getFellow("paymentModeListBox");
			String paymentMode = (String) paymentListBox.getSelectedItem().getValue();

			if (paymentMode.equals(NonConfigurableConstants.PAYMENT_MODE_CONTRACT)) {
				// TODO
				Messagebox.show("Payment mode contract is currently not available yet.",
						"Create Payment Receipt", Messagebox.OK, Messagebox.INFORMATION);
				createPymtBtn.setDisabled(false);
				return;
			} 
			else {
				// pull out those applied or full payment records and pass it to BusinessImpl to do the logic
				if (paymentMode.equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)) {
					// TODO

					List<InvoicePaymentDetail> invoicePaymentDetails = new ArrayList<InvoicePaymentDetail>();
					Listbox invoiceList = (Listbox) this.getFellow("invoiceList");
					List<Listitem> invoiceListItems = invoiceList.getItems();
					for (Listitem invoiceListItem : invoiceListItems) {
						InvoicePaymentDetail paymentDetail = (InvoicePaymentDetail) invoiceListItem
								.getValue();
						if (paymentDetail.isFullPayment()) {
							invoicePaymentDetails.add(paymentDetail);
						}
					}

					// must have invoice selected and total selected is zero to do contra (clearing document)
					if (invoicePaymentDetails.size() == 0) {
						Messagebox.show("At least two invoices should be selected to do clearing document.",
								"Error", Messagebox.OK, Messagebox.ERROR);
						createPymtBtn.setDisabled(false);
						return;
					} else if (invoicePaymentDetails.size() > 0
							&& selectedAmount.compareTo(BigDecimal.ZERO) != 0) {
						Messagebox
								.show("Total selected amount must be balanced as zero in order to do clearing document.",
										"Error", Messagebox.OK, Messagebox.ERROR);
						createPymtBtn.setDisabled(false);
						return;
					}

					Long receiptNo = this.businessHelper.getPaymentBusiness().createContraReceipt(
							paymentInfo, invoicePaymentDetails, getUserLoginIdAndDomain());

					// Show result
					Messagebox.show("New payment receipt created (Receipt No: " + receiptNo + ").",
							"Create Payment Receipt", Messagebox.OK, Messagebox.INFORMATION);
				} else {
					// Check for selected amount, must not be zero
					if (selectedAmount.compareTo(new BigDecimal(0)) == 0) {
						Messagebox.show("Total applied amount cannot be equals to $0.", "Error",
								Messagebox.OK, Messagebox.ERROR);
						createPymtBtn.setDisabled(false);
						return;
					}
					// Check selected amount greater than payment amount
					else if (selectedAmount.compareTo(paymentInfo.getPaymentAmount()) > 0) {
						Messagebox.show("Total selected amount cannot be greater than payment amount.",
								"Error", Messagebox.OK, Messagebox.ERROR);
						createPymtBtn.setDisabled(false);
						return;
					} else if (selectedAmount.compareTo(BigDecimal.ZERO) < 0) {
						Messagebox.show("Total selected amount cannot be lesser than $0.", "Error",
								Messagebox.OK, Messagebox.ERROR);
						createPymtBtn.setDisabled(false);
						return;
					}
					// If there is excess amount, alert user before proceed to create payment receipt
					else if (selectedAmount.compareTo(paymentInfo.getPaymentAmount()) < 0) {
						if (Messagebox.show("There is excess amount. Continue to create payment receipt?",
								"Create Payment Receipt", Messagebox.OK | Messagebox.CANCEL,
								Messagebox.QUESTION) == Messagebox.CANCEL) {
							createPymtBtn.setDisabled(false);
							return;
						}
					}

					List<InvoicePaymentDetail> invoicePaymentDetails = new ArrayList<InvoicePaymentDetail>();
					Listbox invoiceList = (Listbox) this.getFellow("invoiceList");
					List<Listitem> invoiceListItems = invoiceList.getItems();
					for (Listitem invoiceListItem : invoiceListItems) {
						InvoicePaymentDetail paymentDetail = (InvoicePaymentDetail) invoiceListItem
								.getValue();
						if (paymentDetail.isFullPayment()) {
							invoicePaymentDetails.add(paymentDetail);
						} else if (paymentDetail.getInvoiceDetailAppliedAmount().size() > 0) {
							invoicePaymentDetails.add(paymentDetail);
						}
					}

					Long receiptNo = this.businessHelper.getPaymentBusiness().createPaymentReceipt(
							paymentInfo, invoicePaymentDetails, getUserLoginIdAndDomain());

					// reward early payment is applicable
					this.businessHelper.getPaymentBusiness().rewardEarlyPayment(paymentInfo.getAccount(),
							getUserLoginIdAndDomain(), invoicePaymentDetails);

					// Show result
					Messagebox.show("New payment receipt created (Receipt No: " + receiptNo + ").",
							"Create Payment Receipt", Messagebox.OK, Messagebox.INFORMATION);
				}
			}

			Executions.getCurrent().sendRedirect("");
		} catch (WrongValueException wve) {
			createPymtBtn.setDisabled(false);
			throw wve;
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH

			holfe.printStackTrace();
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void onSelectAccountName() throws InterruptedException {
		logger.info("");

		try {
			Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox) this.getFellow("accountNoIntBox");

			// Fix to bypass IE6 issue with double spacing
			if (accountNameComboBox.getChildren().size() == 1)
				accountNameComboBox.setSelectedIndex(0);

			if (accountNameComboBox.getSelectedItem() != null) {
				AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
				accountNoIntBox.setText(selectedAccount.getCustNo());

				selectedIndex = accountNameComboBox.getSelectedIndex();
				this.displayProcessing();

				// Display division or department according to account category
				if (selectedAccount.getAccountCategory().equals(
						NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)) {
					List<AmtbAccount> divisions = this.businessHelper.getPaymentBusiness()
							.searchBillableAccountByParentAccount(selectedAccount);
					List<AmtbAccount> departments = this.businessHelper.getPaymentBusiness()
							.searchBillableAccountByGrandParentAccount(selectedAccount);
					this.setDivisionInputVisible(divisions);
					this.setDepartmentInputVisible(departments);
				} else {
					this.setDivisionInputInvisible();
					this.setDepartmentInputInvisible();
				}

				// populate bank in banks
				this.populateBankIn(selectedAccount);

				// set default payment mode
				Listbox paymentModeListBox = (Listbox) this.getFellow("paymentModeListBox");
				if (selectedAccount.getMstbMasterTableByDefaultPaymentMode() != null) {
					String defaultPaymentModeCode = selectedAccount.getMstbMasterTableByDefaultPaymentMode()
							.getMasterCode();

					List<Listitem> listItems = paymentModeListBox.getItems();
					for (Listitem listItem : listItems) {
						if (((String) listItem.getValue()).equals(defaultPaymentModeCode)) {
							listItem.setSelected(true);
							break;
						}
					}

					this.onSelectPaymentMode(paymentModeListBox);
				}

				// clear selected amount
				Label selectedAmountLabel = (Label) this.getFellow("selectedAmountLabel");
				selectedAmountLabel.setValue("0.00");
				selectedAmount = new BigDecimal(0.00);

				// uncheck checkAll Checkbox
				Checkbox checkAll = (Checkbox) this.getFellow("checkAll");
				checkAll.setChecked(false);

				// list outstanding invoices under the corporate
				searchOutstandingInvoice(selectedAccount, (String) paymentModeListBox.getSelectedItem()
						.getValue());
			} else {
				this.clear();
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	private void clear() {
		Listbox outstandingInvoiceList = (Listbox) this.getFellow("invoiceList");
		outstandingInvoiceList.getItems().clear();
		if (outstandingInvoiceList.getListfoot() == null) {
			outstandingInvoiceList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(7));
		}

		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();

		// clear selected amount
		Label selectedAmountLabel = (Label) this.getFellow("selectedAmountLabel");
		selectedAmountLabel.setValue("0.00");
		selectedAmount = new BigDecimal(0.00);

		// uncheck checkAll Checkbox
		Checkbox checkAll = (Checkbox) this.getFellow("checkAll");
		checkAll.setChecked(false);

		// clear bank in banks
		Listbox bankInBankListBox = (Listbox) this.getFellow("bankInBankListBox");
		bankInBankListBox.getItems().clear();

		// clear selectedIndex
		selectedIndex = null;
	}

	public void onSelectDivision(Listbox divisionListBox) throws InterruptedException {
		logger.info("");

		try {
			Object selectedValue = divisionListBox.getSelectedItem().getValue();
			if ((selectedValue instanceof String) == false) {
				Listbox departmentListBox = (Listbox) this.getFellow("departmentListBox");
				departmentListBox.setSelectedIndex(0);
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectDepartment(Listbox departmentListBox) throws InterruptedException {
		logger.info("");

		try {
			Object selectedValue = departmentListBox.getSelectedItem().getValue();
			if ((selectedValue instanceof String) == false) {
				Listbox divisionListBox = (Listbox) this.getFellow("divisionListBox");
				divisionListBox.setSelectedIndex(0);
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchBillableAccountByAccountNo() throws InterruptedException {
		logger.info("");

		Longbox invoiceNoLongBox = (Longbox) this.getFellow("invoiceNoLongBox");
		Intbox accountNoIntBox = (Intbox) this.getFellow("accountNoIntBox");
		Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
		Integer accountNo = accountNoIntBox.getValue();

		if (accountNo == null)
			return;

		// accountName still the same as selected one, skip
		if (accountNameComboBox.getSelectedItem() != null) {
			AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			if (accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		// Clear combobox for a new search
		accountNameComboBox.setText("");
		// Clear list for every new search
		accountNameComboBox.getChildren().clear();
		// Clear invoice no
		invoiceNoLongBox.setText("");
		// Clear listed invoices + division + department
		this.clear();

		try {
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(
					accountNo.toString(), null);
			for (AmtbAccount account : accounts) {
				Comboitem item = new Comboitem(account.getAccountName() + " (" + account.getCustNo() + ")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if (accounts.size() == 1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchBillableAccountByAccountName(String name) throws InterruptedException {
		logger.info(name);
		Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
		Longbox invoiceNoLongBox = (Longbox) this.getFellow("invoiceNoLongBox");

		// only begin new search if input is greater than 2
		if (name.length() < 3) {
			return;
		}

		// accountName still the same as selected one, skip
		if (accountNameComboBox.getSelectedItem() != null) {
			AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			if (name.equals(selectedAccount.getAccountName() + " (" + selectedAccount.getCustNo() + ")")) {
				return;
			}
		}

		// clear textbox for a new search
		Intbox accountNoIntBox = (Intbox) this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		// Clear list for every new search
		accountNameComboBox.getChildren().clear();
		// Clear invoice no
		invoiceNoLongBox.setText("");
		// Clear listed invoices + division + department
		this.clear();

		try {
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(null,
					name);
			for (AmtbAccount account : accounts) {
				Comboitem item = new Comboitem(account.getAccountName() + " (" + account.getCustNo() + ")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if (accounts.size() == 1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			} else
				accountNameComboBox.open();
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	private void setDivisionInputVisible(List<AmtbAccount> divisions) {
		((Row) this.getFellow("divisionDepartmentRow")).setVisible(true);
		((Label) this.getFellow("divisionLabel")).setVisible(true);
		Listbox divisionListBox = (Listbox) this.getFellow("divisionListBox");
		divisionListBox.setVisible(true);
		divisionListBox.getChildren().clear();
		divisionListBox.appendChild(ComponentUtil.createNotRequiredListItem());

		for (AmtbAccount division : divisions) {
			Listitem newItem = new Listitem(division.getAccountName() + " (" + division.getCode() + ")");
			newItem.setValue(division);
			divisionListBox.appendChild(newItem);
		}
	}

	private void setDepartmentInputVisible(List<AmtbAccount> departments) {
		((Label) this.getFellow("departmentLabel")).setVisible(true);
		Listbox departmentListBox = (Listbox) this.getFellow("departmentListBox");
		departmentListBox.setVisible(true);
		departmentListBox.getChildren().clear();
		departmentListBox.appendChild(ComponentUtil.createNotRequiredListItem());

		for (AmtbAccount department : departments) {
			Listitem newItem = new Listitem(department.getAccountName() + " (" + department.getCode() + ")");
			newItem.setValue(department);
			departmentListBox.appendChild(newItem);
		}
	}

	private void setDivisionInputInvisible() {
		((Row) this.getFellow("divisionDepartmentRow")).setVisible(false);
		((Label) this.getFellow("divisionLabel")).setVisible(false);
		Listbox divisionListBox = (Listbox) this.getFellow("divisionListBox");
		divisionListBox.setVisible(false);
		divisionListBox.getChildren().clear();
	}

	private void setDepartmentInputInvisible() {
		((Label) this.getFellow("departmentLabel")).setVisible(false);
		Listbox departmentListBox = (Listbox) this.getFellow("departmentListBox");
		departmentListBox.setVisible(false);
		departmentListBox.getChildren().clear();
	}

	public void populatePaymentMode(Listbox paymentModeListBox) throws InterruptedException {
		logger.info("");

		try {
			List<Listitem> paymentModes = ComponentUtil.convertToListitems(
					ConfigurableConstants.getPaymentModes(), true);
			boolean firstItem = true;
			for (Listitem listItem : paymentModes) {
				if (listItem.getLabel().equals(NonConfigurableConstants.PAYMENT_MODE_MEMO)) {
					continue;
				}
				if (listItem.getValue().equals(NonConfigurableConstants.PAYMENT_MODE_DIRECT_RECEIPT)) {  // DR
					continue;
				}
				paymentModeListBox.appendChild(listItem);
				if (firstItem) {
					listItem.setSelected(true);
					firstItem = false;
				}
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectPaymentMode(Listbox paymentModeListBox) throws InterruptedException {
		logger.info("");

		try {
			Listitem selectedItem = paymentModeListBox.getSelectedItem();

			if (currentSelectedPaymentMode.equals("")) {
				currentSelectedPaymentMode = (String) selectedItem.getValue();
				if (NonConfigurableConstants.specificPaymentModes.get(currentSelectedPaymentMode) == null)
					currentSelectedPaymentMode = NonConfigurableConstants.PAYMENT_MODE_OTHERS;
			} else {
				// hide previous selected detail before showing the next
				ArrayList<Component> previousPaymentDetail = this.paymentDetailMap
						.get(currentSelectedPaymentMode);
				if (previousPaymentDetail != null) {
					for (Component component : previousPaymentDetail) {
						component.setVisible(false);
					}
				}

				Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
				if (accountNameComboBox.getSelectedItem() != null) {
					// if previous selected payment mode is contra and new selected is not
					if (currentSelectedPaymentMode.equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)
							&& !(((String) selectedItem.getValue())
									.equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA))) {

						AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem()
								.getValue();
						// re-retrieve the outstanding invoice
						this.searchOutstandingInvoice(selectedAccount, (String) selectedItem.getValue());
						// Reset selected amount
						Label selectedAmountLabel = (Label) this.getFellow("selectedAmountLabel");
						selectedAmountLabel.setValue("0.00");
						selectedAmount = new BigDecimal(0.00);
					}
					// if previous selected is not contra and new seiect is
					else if (!(currentSelectedPaymentMode
							.equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA))
							&& ((String) selectedItem.getValue())
									.equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)) {

						AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem()
								.getValue();
						// re-retrieve the outstanding invoice
						this.searchOutstandingInvoice(selectedAccount, (String) selectedItem.getValue());
						// Reset selected amount
						Label selectedAmountLabel = (Label) this.getFellow("selectedAmountLabel");
						selectedAmountLabel.setValue("0.00");
						selectedAmount = new BigDecimal(0.00);
					}
				}

				currentSelectedPaymentMode = (String) selectedItem.getValue();
				if (NonConfigurableConstants.specificPaymentModes.get(currentSelectedPaymentMode) == null)
					currentSelectedPaymentMode = NonConfigurableConstants.PAYMENT_MODE_OTHERS;
			}

			// Showing the next payment detail
			ArrayList<Component> nextPaymentDetail = this.paymentDetailMap.get(currentSelectedPaymentMode);
			if (nextPaymentDetail != null) {
				for (Component component : nextPaymentDetail) {
					component.setVisible(true);
				}
			}

			// default the giro bank is payment mode is interbank giro
			if (selectedItem.getValue().equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)) {
				Listbox bankListBox = (Listbox) this.getFellow("bankInBankListBox");
				for (Object item : bankListBox.getItems()) {
					FmtbBankCode bank = (FmtbBankCode) ((Listitem) item).getValue();
					if (bank.getIsDefaultGiroBank().equals(NonConfigurableConstants.BOOLEAN_YES)) {
						bankListBox.setSelectedItem((Listitem) item);
						break;
					}
				}
			}
			// else default to the default collection bank
			else {
				Listbox bankListBox = (Listbox) this.getFellow("bankInBankListBox");
				for (Object item : bankListBox.getItems()) {
					FmtbBankCode bank = (FmtbBankCode) ((Listitem) item).getValue();
					if (bank.getIsDefault().equals(NonConfigurableConstants.BOOLEAN_YES)) {
						bankListBox.setSelectedItem((Listitem) item);
						break;
					}
				}
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void populateReceiptDate(Label receiptDateLabel) {
		receiptDateLabel.setValue(DateUtil.convertDateToStr(DateUtil.getCurrentDate(),
				DateUtil.GLOBAL_DATE_FORMAT));
	}

	public void populateBankIn(AmtbAccount account) throws InterruptedException {
		logger.info("");

		try {
			Listbox listbox = (Listbox) this.getFellow("bankInBankListBox");
			listbox.getItems().clear();

			Set<FmtbBankCode> banks = this.businessHelper.getPaymentBusiness().searchBankInBanks(account);

			boolean firstItem = true;
			for (FmtbBankCode bank : banks) {
				Listitem listItem = new Listitem(bank.getBankName() + " - " + bank.getBranchName() + " ("
						+ bank.getBankAcctNo() + ")", bank);
				listbox.appendChild(listItem);
				if (firstItem) {
					listItem.setSelected(true);
					firstItem = false;
				}
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void populateBank(Listbox listbox, boolean mandatory) throws InterruptedException {
		logger.info("");

		try {
			Map<Integer, String> bankMasters = MasterSetup.getBankManager().getAllMasters();
			bankMasters = MasterSetup.getBankManager().sortMastersByValue(bankMasters);
			boolean firstItem = true;
			if (!mandatory) {
				listbox.appendChild(ComponentUtil.createNotRequiredListItem());
				listbox.setSelectedIndex(0);
			}
			for (Integer bankNo : bankMasters.keySet()) {
				Listitem listItem = new Listitem(bankMasters.get(bankNo), bankNo);
				listbox.appendChild(listItem);
				if (firstItem && mandatory) {
					listItem.setSelected(true);
					firstItem = false;
				}
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void populateBranch(String paymentMode) throws InterruptedException {
		logger.info("");

		try {
			Listbox bankListBox = null;
			Listbox branchListBox = null;
			boolean mandatory = true;

			if (paymentMode.equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)) {
				bankListBox = (Listbox) this.getFellow("cqBankListBox");
				branchListBox = (Listbox) this.getFellow("cqBranchListBox");
			} else if (paymentMode.equals(NonConfigurableConstants.PAYMENT_MODE_GIRO)) {
				bankListBox = (Listbox) this.getFellow("grBankListBox");
				branchListBox = (Listbox) this.getFellow("grBranchListBox");
				mandatory = false;
			} else if (paymentMode.equals(NonConfigurableConstants.PAYMENT_MODE_BANK_TRANSFER)) {
				bankListBox = (Listbox) this.getFellow("btBankListBox");
				branchListBox = (Listbox) this.getFellow("btBranchListBox");
				mandatory = false;
			}

			// clear list
			branchListBox.getChildren().clear();

			// retrieve branch according to bank
			Map<Integer, Map<String, String>> branches = new HashMap<Integer, Map<String, String>>();
			if (bankListBox.getSelectedItem().getValue() != null
					&& !bankListBox.getSelectedItem().getValue().toString().equals(""))
				branches = MasterSetup.getBankManager().getAllDetails(
						(Integer) bankListBox.getSelectedItem().getValue());
			Set<Listitem> sortedBranchListItems = new TreeSet(new Comparator<Listitem>() {
				public int compare(Listitem listItem1, Listitem listItem2) {
					return listItem1.getLabel().compareTo(listItem2.getLabel());
				}
			});
			// do sorting
			for (Integer branchNo : branches.keySet()) {
				MstbBranchMaster branchMaster = (MstbBranchMaster) MasterSetup.getBankManager().getDetail(
						branchNo);
				Listitem listItem = new Listitem(branchMaster.getBranchName(), branchNo);
				sortedBranchListItems.add(listItem);
			}
			// after sorting, appending into listbox
			boolean firstItem = true;
			if (!mandatory) {
				branchListBox.appendChild(ComponentUtil.createNotRequiredListItem());
				branchListBox.setSelectedIndex(0);
			}
			for (Listitem listItem : sortedBranchListItems) {
				branchListBox.appendChild(listItem);
				if (firstItem && mandatory) {
					listItem.setSelected(true);
					firstItem = false;
				}
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchOutstandingInvoice(AmtbAccount account, String paymentMode) throws InterruptedException {
		try {
			Listbox outstandingInvoiceList = (Listbox) this.getFellow("invoiceList");
			outstandingInvoiceList.getItems().clear();

			List<InvoicePaymentDetail> outstandingInvoices = new ArrayList<InvoicePaymentDetail>();
			// //Contra listed out negative amount invoices only
			// if(paymentMode.equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)){
			// outstandingInvoices = this.businessHelper.getPaymentBusiness().searchNegativeInvoice(account);
			// }
			// //Rest listed out all outstanding invoices
			// else{
			outstandingInvoices = this.businessHelper.getPaymentBusiness().searchOutstandingInvoice(account);
			// }

			if (outstandingInvoices.size() > 0) {
				for (InvoicePaymentDetail paymentDetail : outstandingInvoices) {
					Listitem item = new Listitem();
					item.setValue(paymentDetail);
					item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getInvoiceNo(),
							StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getInvoiceDate(),
							DateUtil.GLOBAL_DATE_FORMAT));
					item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getAmtbAccountByDebtTo()
							.getAccountName()));
					item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getNewTxn(),
							StringUtil.GLOBAL_DECIMAL_FORMAT));
					BigDecimal invoiceAppliedAmount = new BigDecimal(0);
					invoiceAppliedAmount = paymentDetail.getInvoiceHeader().getNewTxn()
							.subtract(paymentDetail.getInvoiceHeader().getOutstandingAmount());
					item.appendChild(newListcell(invoiceAppliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getOutstandingAmount(),
							StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(new BigDecimal(0), StringUtil.GLOBAL_DECIMAL_FORMAT));

					// last one is the checkbox
					Listcell checkboxListCell = new Listcell();
					Checkbox checkBox = new Checkbox();
					checkBox.addEventListener(Events.ON_CHECK, new EventListener() {
						public boolean isAsap() {
							return true;
						}

						public void onEvent(Event event) throws Exception {
							Checkbox self = (Checkbox) event.getTarget();

							CreatePaymentReceiptWindow window = (CreatePaymentReceiptWindow) self.getPage()
									.getFellow(CreatePaymentReceiptWindow.SELF);
							window.onCheckInvoice(self);
						}
					});
					checkboxListCell.appendChild(checkBox);
					item.appendChild(checkboxListCell);

					outstandingInvoiceList.appendChild(item);
				}

				if (outstandingInvoiceList.getListfoot() != null) {
					outstandingInvoiceList.removeChild(outstandingInvoiceList.getListfoot());
				}
			} else {
				if (outstandingInvoiceList.getListfoot() == null) {
					outstandingInvoiceList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
				}
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void checkAll() throws InterruptedException {
		logger.info("");

		try {
			Checkbox checkAll = (Checkbox) this.getFellow("checkAll");
			Listbox outstandingInvoiceList = (Listbox) this.getFellow("invoiceList");
			Label selectedAmountLabel = (Label) this.getFellow("selectedAmountLabel");

			List<Listitem> listItems = outstandingInvoiceList.getItems();
			for (Listitem listItem : listItems) {
				InvoicePaymentDetail paymentDetail = (InvoicePaymentDetail) listItem.getValue();

				// remove payment detail in the map
				paymentDetail.setInvoiceDetailAppliedAmount(new HashMap());

				// adjust applied amount
				if (checkAll.isChecked()) {
					Checkbox checkbox = (Checkbox) listItem.getLastChild().getLastChild();
					if (!checkbox.isChecked()) {
						checkbox.setChecked(true);
						// subtract away any partial applied amount
						selectedAmount = selectedAmount.subtract(paymentDetail.getTotalAppliedAmount());
						// full payment, so set the payment detail total applied amount into new full payment
						paymentDetail.setFullPayment(true);
						paymentDetail.setTotalAppliedAmount(paymentDetail.getInvoiceHeader()
								.getOutstandingAmount());
						// add into the selected amount
						selectedAmount = selectedAmount.add(paymentDetail.getTotalAppliedAmount());
						selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount,
								StringUtil.GLOBAL_DECIMAL_FORMAT));
						// apply amount
						Listcell applyAmountListCell = (Listcell) listItem.getChildren().get(6);
						applyAmountListCell.setLabel(StringUtil.bigDecimalToString(
								paymentDetail.getTotalAppliedAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
						applyAmountListCell.setValue(paymentDetail.getTotalAppliedAmount());
					}
				} else {
					Checkbox checkbox = (Checkbox) listItem.getLastChild().getLastChild();
					if (checkbox.isChecked()) {
						checkbox.setChecked(false);
						paymentDetail.setFullPayment(false);
						paymentDetail.setTotalAppliedAmount(new BigDecimal(0));
						selectedAmount = selectedAmount.subtract(paymentDetail.getInvoiceHeader()
								.getOutstandingAmount());
						selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount,
								StringUtil.GLOBAL_DECIMAL_FORMAT));
						// apply amount
						Listcell applyAmountListCell = (Listcell) listItem.getChildren().get(6);
						applyAmountListCell.setLabel("0.00");
						applyAmountListCell.setValue(BigDecimal.ZERO);
					}
				}
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onCheckInvoice(Checkbox checkbox) throws InterruptedException {
		logger.info("");

		try {
			Checkbox checkAll = (Checkbox) this.getFellow("checkAll");
			checkAll.setChecked(false);

			// retrieve selected amount label
			Label selectedAmountLabel = (Label) this.getFellow("selectedAmountLabel");

			// retrieve invoice header from listitem
			Listitem invoiceListItem = (Listitem) ((Listcell) checkbox.getParent()).getParent();
			InvoicePaymentDetail paymentDetail = (InvoicePaymentDetail) invoiceListItem.getValue();

			// adjust applied amount
			if (checkbox.isChecked()) {

				// check make sure remaining amount is able to full payment the selected invoice
				// 1. only applies to the rest other than contra and contract
				PaymentInfo paymentInfo = this.buildPaymentInfo();
				if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)
						|| paymentInfo.getPaymentMode()
								.equals(NonConfigurableConstants.PAYMENT_MODE_CONTRACT)) {
					;
				} else {
					if (!(paymentDetail.getInvoiceHeader().getOutstandingAmount().compareTo(BigDecimal.ZERO) < 0)) {
						BigDecimal remainingAmount = paymentInfo.getPaymentAmount();
						remainingAmount = remainingAmount.subtract(selectedAmount);
						if (remainingAmount
								.compareTo(paymentDetail.getInvoiceHeader().getOutstandingAmount()) < 0) {
							checkbox.setChecked(false);
							Messagebox.show("Insufficient fund to pay for the whole invoice.",
									"Create Payment Receipt", Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
				}

				// subtract away any partial applied amount
				selectedAmount = selectedAmount.subtract(paymentDetail.getTotalAppliedAmount());

				// full payment, so set the payment detail total applied amount into new full payment
				paymentDetail.setFullPayment(true);
				paymentDetail.setTotalAppliedAmount(paymentDetail.getInvoiceHeader().getOutstandingAmount());

				// add into the selected amount
				selectedAmount = selectedAmount.add(paymentDetail.getTotalAppliedAmount());
				selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount,
						StringUtil.GLOBAL_DECIMAL_FORMAT));

				// apply amount
				Listcell applyAmountListCell = (Listcell) invoiceListItem.getChildren().get(6);
				applyAmountListCell.setLabel(StringUtil.bigDecimalToString(
						paymentDetail.getTotalAppliedAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				applyAmountListCell.setValue(paymentDetail.getTotalAppliedAmount());
			} else {
				paymentDetail.setFullPayment(false);
				paymentDetail.setTotalAppliedAmount(new BigDecimal(0));
				selectedAmount = selectedAmount.subtract(paymentDetail.getInvoiceHeader()
						.getOutstandingAmount());
				selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount,
						StringUtil.GLOBAL_DECIMAL_FORMAT));

				// apply amount
				Listcell applyAmountListCell = (Listcell) invoiceListItem.getChildren().get(6);
				applyAmountListCell.setLabel("0.00");
				applyAmountListCell.setValue(BigDecimal.ZERO);
			}

			// remove payment detail in the map
			paymentDetail.setInvoiceDetailAppliedAmount(new HashMap());
		} catch (WrongValueException wve) {
			checkbox.setChecked(false);
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void registerPaymentDetail(Component component, String key) {
		ArrayList<Component> paymentDetail = this.paymentDetailMap.get(key);
		if (paymentDetail == null) {
			paymentDetail = new ArrayList<Component>();
		}
		paymentDetail.add(component);
		this.paymentDetailMap.put(key, paymentDetail);
	}

	public void init() throws InterruptedException {
		((Datebox) this.getFellow("paymentDateBox")).setValue(new Date());
		this.onSelectPaymentMode((Listbox) this.getFellow("paymentModeListBox"));
	}

	public void applyPartialPayment(Listbox invoiceListBox) throws InterruptedException {
		logger.info("");

		try {
			// retrieve current input payment info
			PaymentInfo paymentInfo = this.buildPaymentInfo();

			// Retrieve selected invoice
			Listitem selectedItem = invoiceListBox.getSelectedItem();
			InvoicePaymentDetail invoicePaymentDetail = (InvoicePaymentDetail) selectedItem.getValue();

			
			// Clearing Document is not allowed to do partial payment
			if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA))
				return;

			this.previousAppliedAmount = invoicePaymentDetail.getTotalAppliedAmount();

			// uncheck checkbox
			Checkbox fullPaymentCheckbox = (Checkbox) ((Listcell) selectedItem.getLastChild()).getLastChild();
			if (fullPaymentCheckbox.isChecked()) {
				fullPaymentCheckbox.setChecked(false);
			}

			// uncheck checkall
			Checkbox checkAll = (Checkbox) this.getFellow("checkAll");
			checkAll.setChecked(false);

			HashMap map = new HashMap();
			map.put("invoicePaymentDetail", invoicePaymentDetail);
			map.put("paymentInfo", paymentInfo);
			this.forward(Uri.APPLY_PARTIAL_PAYMENT, map);
		} catch (WrongValueException wve) {
			// if don do unselect, the user cant select again when there is a validation error.
			invoiceListBox.clearSelection();
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	private PaymentInfo buildPaymentInfo() {
		PaymentInfo paymentInfo = new PaymentInfo();

		// selected account
		Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");

		// Fix to bypass IE6 issue with double spacing
		if (accountNameComboBox.getChildren().size() == 1)
			accountNameComboBox.setSelectedIndex(0);
		else if (selectedIndex != null)
			accountNameComboBox.setSelectedIndex(selectedIndex);

		if (accountNameComboBox.getSelectedItem() == null) {
			throw new WrongValueException(accountNameComboBox, "* Mandatory field");
		}
		paymentInfo.setAccount((AmtbAccount) accountNameComboBox.getSelectedItem().getValue());

		// division
		Listbox divisionListBox = (Listbox) this.getFellow("divisionListBox");
		if (divisionListBox.getSelectedItem() != null) {
			if (!(divisionListBox.getSelectedItem().getValue() instanceof String)) {
				paymentInfo.setDivision((AmtbAccount) divisionListBox.getSelectedItem().getValue());
			}
		}
		// department
		Listbox departmentListBox = (Listbox) this.getFellow("departmentListBox");
		if (departmentListBox.getSelectedItem() != null) {
			if (!(departmentListBox.getSelectedItem().getValue() instanceof String)) {
				paymentInfo.setDepartment((AmtbAccount) departmentListBox.getSelectedItem().getValue());
			}
		}
		// payment mode
		Listbox paymentModeListBox = (Listbox) this.getFellow("paymentModeListBox");
		paymentInfo.setPaymentMode((String) paymentModeListBox.getSelectedItem().getValue());
		// payment date
		Datebox paymentDateBox = (Datebox) this.getFellow("paymentDateBox");
		paymentInfo.setPaymentDate(DateUtil.convertUtilDateToSqlDate(paymentDateBox.getValue()));
		// receipt date
		paymentInfo.setReceiptDt(DateUtil.getCurrentTimestamp());

		if (NonConfigurableConstants.specificPaymentModes.get(paymentInfo.getPaymentMode()) == null) {
			// txn ref no
			CapsTextbox txnRefNoTextBox = (CapsTextbox) this.getFellow("othersTxnRefNo");
			paymentInfo.setTxnRefNo(txnRefNoTextBox.getValue());
			// payment amount
			Decimalbox paymentAmountDecimalBox = (Decimalbox) this.getFellow("othersPaymentAmount");
			paymentInfo.setPaymentAmount(paymentAmountDecimalBox.getValue());
		} else if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_AXS)) {
			// txn ref no
			CapsTextbox txnRefNoTextBox = (CapsTextbox) this.getFellow("axsTxnRefNo");
			paymentInfo.setTxnRefNo(txnRefNoTextBox.getValue());
			// payment amount
			Decimalbox paymentAmountDecimalBox = (Decimalbox) this.getFellow("axsPaymentAmount");
			paymentInfo.setPaymentAmount(paymentAmountDecimalBox.getValue());
		} else if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_BANK_TRANSFER)) {
			// txn ref no
			CapsTextbox txnRefNoTextBox = (CapsTextbox) this.getFellow("btTxnRefNo");
			paymentInfo.setTxnRefNo(txnRefNoTextBox.getValue());
			// payment amount
			Decimalbox paymentAmountDecimalBox = (Decimalbox) this.getFellow("btPaymentAmount");
			paymentInfo.setPaymentAmount(paymentAmountDecimalBox.getValue());
			// bank
			Listbox bankListBox = (Listbox) this.getFellow("btBankListBox");
			if (!bankListBox.getSelectedItem().getValue().toString().equals(""))
				paymentInfo.setBankNo((Integer) bankListBox.getSelectedItem().getValue());
			// branch
			Listbox branchListBox = (Listbox) this.getFellow("btBranchListBox");
			if (!branchListBox.getSelectedItem().getValue().toString().equals(""))
				paymentInfo.setBranchNo((Integer) branchListBox.getSelectedItem().getValue());
		} else if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CASH)) {
			// payment amount
			Decimalbox paymentAmountDecimalBox = (Decimalbox) this.getFellow("caPaymentAmount");
			paymentInfo.setPaymentAmount(paymentAmountDecimalBox.getValue());
		} else if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)) {
			// cheque date
			Datebox chequeDateBox = (Datebox) this.getFellow("cqChequeDateBox");
			paymentInfo.setChequeDate(DateUtil.convertUtilDateToSqlDate(chequeDateBox.getValue()));
			// cheque no
			CapsTextbox chequeNoTextBox = (CapsTextbox) this.getFellow("cqChequeNoTextBox");
			paymentInfo.setChequeNo(chequeNoTextBox.getValue());
			// quick check deposit
			Checkbox checkbox = (Checkbox) this.getFellow("cqQuickChequeDepositCheckBox");
			paymentInfo.setQuickChequeDeposit(checkbox.isChecked());
			// bank
			Listbox bankListBox = (Listbox) this.getFellow("cqBankListBox");
			paymentInfo.setBankNo((Integer) bankListBox.getSelectedItem().getValue());
			// branch
			Listbox branchListBox = (Listbox) this.getFellow("cqBranchListBox");
			paymentInfo.setBranchNo((Integer) branchListBox.getSelectedItem().getValue());
			// payment amount
			Decimalbox paymentAmountDecimalBox = (Decimalbox) this.getFellow("cqPaymentAmount");
			paymentInfo.setPaymentAmount(paymentAmountDecimalBox.getValue());
		} else if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD)) {
			// txn ref no
			CapsTextbox txnRefNoTextBox = (CapsTextbox) this.getFellow("ccTxnRefNo");
			paymentInfo.setTxnRefNo(txnRefNoTextBox.getValue());
			// payment amount
			Decimalbox paymentAmountDecimalBox = (Decimalbox) this.getFellow("ccPaymentAmount");
			paymentInfo.setPaymentAmount(paymentAmountDecimalBox.getValue());
		} else if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_GIRO)) {
			// txn ref no
			CapsTextbox txnRefNoTextBox = (CapsTextbox) this.getFellow("grTxnRefNo");
			paymentInfo.setTxnRefNo(txnRefNoTextBox.getValue());
			// payment amount
			Decimalbox paymentAmountDecimalBox = (Decimalbox) this.getFellow("grPaymentAmount");
			paymentInfo.setPaymentAmount(paymentAmountDecimalBox.getValue());
			// Bank
			Listbox bankListBox = (Listbox) this.getFellow("grBankListBox");
			if (!bankListBox.getSelectedItem().getValue().toString().equals(""))
				paymentInfo.setBankNo((Integer) bankListBox.getSelectedItem().getValue());
			// branch
			Listbox branchListBox = (Listbox) this.getFellow("grBranchListBox");
			if (!branchListBox.getSelectedItem().getValue().toString().equals(""))
				paymentInfo.setBranchNo((Integer) branchListBox.getSelectedItem().getValue());
		} else if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)) {
			paymentInfo.setPaymentAmount(new BigDecimal(0));
		}

		// remarks
		CapsTextbox remarksTextBox = (CapsTextbox) this.getFellow("remarksTextBox");
		paymentInfo.setRemarks(remarksTextBox.getValue());
		// bank in
		Listbox bankListBox = (Listbox) this.getFellow("bankInBankListBox");
		if (bankListBox.getSelectedItem() == null) {
			throw new WrongValueException(bankListBox, "* Mandatory field");
		}
		FmtbBankCode selectedBankInBank = (FmtbBankCode) bankListBox.getSelectedItem().getValue();
		paymentInfo.setBankInNo(selectedBankInBank.getBankCodeNo());

		// excess amount
		if (paymentInfo.getPaymentAmount() != null) {
			paymentInfo.setExcessAmount(paymentInfo.getPaymentAmount().subtract(selectedAmount));
		} else {
			paymentInfo.setExcessAmount(new BigDecimal(0));
		}

		return paymentInfo;
	}

	public void updateAppliedAmount(InvoicePaymentDetail invoicePaymentDetail) throws InterruptedException {
		logger.info("");

		try {
			Label selectedAmountLabel = (Label) this.getFellow("selectedAmountLabel");
			Listbox invoiceList = (Listbox) this.getFellow("invoiceList");
			Listitem selectedListItem = invoiceList.getSelectedItem();

			// Update selected amount by subtracting previous selected amount then add back new amount
			selectedAmount = selectedAmount.subtract(this.previousAppliedAmount);
			// we need to take away the min applied amount as the total selected amount is only the
			// outstanding amount exclusive of discount
			selectedAmount = selectedAmount.add(invoicePaymentDetail.getTotalAppliedAmount());
			selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount,
					StringUtil.GLOBAL_DECIMAL_FORMAT));

			// surely no longer is fullpayment
			invoicePaymentDetail.setFullPayment(false);

			// Update the payment detail within the listitem
			selectedListItem.setValue(invoicePaymentDetail);

			// apply amount
			Listcell applyAmountListCell = (Listcell) selectedListItem.getChildren().get(6);
			applyAmountListCell.setLabel(StringUtil.bigDecimalToString(
					invoicePaymentDetail.getTotalAppliedAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			applyAmountListCell.setValue(invoicePaymentDetail.getTotalAppliedAmount());

			// Clear selection
			invoiceList.clearSelection();
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void clearSelection(boolean isFullPaymentChecked) {
		Listbox invoiceList = (Listbox) this.getFellow("invoiceList");
		// amazing after from forward back to this window, the checkbox will uncheck itself.
		Listcell listCell = (Listcell) invoiceList.getSelectedItem().getChildren().get(7);
		((Checkbox) listCell.getFirstChild()).setChecked(isFullPaymentChecked);
		// clear invoice list selection so that user can click again
		invoiceList.clearSelection();
	}

	public void searchBillableAccountByInvoiceNo() throws InterruptedException {
		logger.info("");

		Longbox invoiceNoLongBox = (Longbox) this.getFellow("invoiceNoLongBox");
		Intbox accountNoIntBox = (Intbox) this.getFellow("accountNoIntBox");
		Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
		Long invoiceNo = invoiceNoLongBox.getValue();

		if (invoiceNo == null)
			return;

		// Clear combobox for a new search
		accountNameComboBox.setText("");
		// Clear list for every new search
		accountNameComboBox.getChildren().clear();
		// Clear account no
		accountNoIntBox.setText("");

		this.clear();

		try {
			BmtbInvoiceHeader invoice = this.businessHelper.getPaymentBusiness().getInvoiceByInvoiceNo(
					invoiceNo);

			// if invoice not found, do not proceed the below codes
			if (invoice == null)
				return;

			AmtbAccount account = invoice.getAmtbAccountByDebtTo();

			while (account.getAmtbAccount() != null) {
				account = account.getAmtbAccount();
			}

			accountNoIntBox.setValue(new Integer(account.getCustNo()));

			this.searchBillableAccountByAccountNo();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() {

	}
} 
