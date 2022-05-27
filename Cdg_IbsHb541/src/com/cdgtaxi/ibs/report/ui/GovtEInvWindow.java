package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class GovtEInvWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GovtEInvWindow.class);

	private Long reportRsrcId;
	private String report = "Government eInvoice";

	private Intbox accountNoIB, requestNoIB;
	private Combobox accountNameCB;
	private Listbox divDeptLB, entityLB, businessUnitLB, govtEInvLB, returnStatusLB;
	private Datebox requestDateFromDB, requestDateToDB, invoiceDateFromDB, invoiceDateToDB;

	public GovtEInvWindow() throws IOException {
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		divDeptLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		divDeptLB.setSelectedIndex(0);

		// populate entity list
		entityLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		entityLB.setSelectedIndex(0);
		Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
		for (Integer entityNo : entities.keySet()) {
			entityLB.appendChild(new Listitem(entities.get(entityNo), entityNo.toString()));
		}

		// populate businessUnitLB
		businessUnitLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		businessUnitLB.setSelectedIndex(0);
		Map<String, String> businessUnitMasters = ConfigurableConstants
				.getMasters(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE);
		for (Entry<String, String> entry : businessUnitMasters.entrySet()) {
			businessUnitLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}

		// populate govtEInvLB
		govtEInvLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		govtEInvLB.setSelectedIndex(0);
		for (Entry<String, String> entry : NonConfigurableConstants.GOVT_EINV_FLAGS.entrySet()) {
			if (entry.getKey().equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO))
				continue;
			govtEInvLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}

		// populate returnStatusLB
		returnStatusLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		returnStatusLB.setSelectedIndex(0);
		returnStatusLB.appendChild(new Listitem("PENDING", "P"));
		returnStatusLB.appendChild(new Listitem("SUCCESS",
				NonConfigurableConstants.GOVT_EINV_RETURN_STATUS_SUCCESS));
		returnStatusLB.appendChild(new Listitem("REJECT",
				NonConfigurableConstants.GOVT_EINV_RETURN_STATUS_REJECT));
	}

	public void searchTopLevelAccountByAccountName(String name) throws InterruptedException {
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
		// Clear list for new search
		this.clearDivDeptListBox();

		try {
			List<AmtbAccount> accounts = this.businessHelper.getReportBusiness().searchTopLevelAccount(null,
					name);
			for (AmtbAccount account : accounts) {
				if (account.getGovtEInvoiceFlag().equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO)
						&& this.businessHelper.getReportBusiness().getGovtEInvChildrenAccounts(account)
								.size() == 0)
					continue;

				Comboitem item = new Comboitem(account.getAccountName() + " (" + account.getCustNo() + ")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if (accounts.size() == 1) {
				if (accountNameCB.getChildren().size() > 0)
					accountNameCB.setSelectedIndex(0);
				this.onSelectAccountName();
			} else
				accountNameCB.open();
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchTopLevelAccountByAccountNo() throws InterruptedException {
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
		// Clear list for new search
		this.clearDivDeptListBox();

		try {
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(
					accountNo.toString(), null);
			for (AmtbAccount account : accounts) {
				if (account.getGovtEInvoiceFlag().equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO)
						&& this.businessHelper.getReportBusiness().getGovtEInvChildrenAccounts(account)
								.size() == 0)
					continue;

				Comboitem item = new Comboitem(account.getAccountName() + " (" + account.getCustNo() + ")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if (accounts.size() == 1) {
				if (accountNameCB.getChildren().size() > 0)
					accountNameCB.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectAccountName() throws InterruptedException {
		logger.info("");
		this.clearDivDeptListBox();

		try {
			if (accountNameCB.getSelectedItem() != null) {
				AmtbAccount selectedAccount = (AmtbAccount) accountNameCB.getSelectedItem().getValue();
				accountNoIB.setText(selectedAccount.getCustNo());

				// Populates Division and Department that subscribed to Govt eInv
				for (AmtbAccount account : this.businessHelper.getReportBusiness()
						.getGovtEInvChildrenAccounts(selectedAccount)) {
					divDeptLB.appendChild(new Listitem(account.getAccountName() + " (" + account.getCode()
							+ ")", account));
				}
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void populateReportFormatList(Listbox listbox) throws NetException {
		List<MstbReportFormatMap> reportFormatMapList = this.businessHelper.getReportBusiness()
				.getReportFormatMap(this.reportRsrcId);
		boolean firstItem = true;
		for (MstbReportFormatMap formatMap : reportFormatMapList) {
			Listitem listItem = new Listitem(formatMap.getReportFormat(),
					Constants.EXTENSION_MAP.get(formatMap.getReportFormat()));
			if (firstItem) {
				listItem.setSelected(true);
				firstItem = false;
			}
			listbox.appendChild(listItem);
		}
	}

	public void generate() throws HttpException, IOException, InterruptedException, NetException,
			WrongValueException {
		logger.info("");
		this.displayProcessing();

		String accountNo = "";
		String accountName = "";
		AmtbAccount subAccount;
		String entityNo = "";
		String businessUnit = "";
		String govtEInvFlag = "";
		String returnStatus = "";
		String requestNo = "";
		String requestDateFrom = "";
		String requestDateTo = "";
		String invoiceDateFrom = "";
		String invoiceDateTo = "";

		if (accountNoIB.getValue() != null) {
			accountNo = accountNoIB.getValue().toString();
		} else {
			if (accountNameCB.getSelectedItem() != null) {
				AmtbAccount selectedAccount = (AmtbAccount) accountNameCB.getSelectedItem().getValue();
				accountNo = accountNoIB.getValue().toString();
				accountName = selectedAccount.getAccountName();
			} else {
				if (accountNameCB.getText() != null && accountNameCB.getText().length() > 0) {
					accountName = accountNameCB.getText().toUpperCase();
				}
			}
		}

		if (invoiceDateFromDB.getValue() == null)
			throw new WrongValueException(invoiceDateFromDB, "* Mandatory Field");
		if (invoiceDateToDB.getValue() == null)
			throw new WrongValueException(invoiceDateToDB, "* Mandatory Field");

		if (requestDateFromDB.getValue() != null && requestDateToDB.getValue() == null)
			requestDateToDB.setValue(requestDateFromDB.getValue());
		else if (requestDateToDB.getValue() != null && requestDateFromDB.getValue() == null)
			requestDateFromDB.setValue(requestDateToDB.getValue());

		if (invoiceDateFromDB.getValue() != null && invoiceDateToDB.getValue() == null)
			invoiceDateToDB.setValue(invoiceDateFromDB.getValue());
		else if (invoiceDateToDB.getValue() != null && invoiceDateFromDB.getValue() == null)
			invoiceDateFromDB.setValue(invoiceDateToDB.getValue());

		subAccount = (AmtbAccount) divDeptLB.getSelectedItem().getValue();
		entityNo = entityLB.getSelectedItem().getValue() == null ? "" : (String) entityLB.getSelectedItem()
				.getValue();
		businessUnit = businessUnitLB.getSelectedItem().getValue() == null ? "" : (String) businessUnitLB
				.getSelectedItem().getValue();
		govtEInvFlag = govtEInvLB.getSelectedItem().getValue() == null ? "" : (String) govtEInvLB
				.getSelectedItem().getValue();
		returnStatus = returnStatusLB.getSelectedItem().getValue() == null ? "" : (String) returnStatusLB
				.getSelectedItem().getValue();
		requestNo = requestNoIB.getValue() == null ? "" : requestNoIB.getValue().toString();
		requestDateFrom = DateUtil
				.convertDateToStr(requestDateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		requestDateTo = DateUtil.convertDateToStr(requestDateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		invoiceDateFrom = DateUtil
				.convertDateToStr(invoiceDateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		invoiceDateTo = DateUtil.convertDateToStr(invoiceDateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);

		// retrieve format
		Listbox formatList = (Listbox) this.getFellow("reportFormat");
		if (formatList.getSelectedItem() == null)
			throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String) formatList.getSelectedItem().getLabel();
		String format = (String) formatList.getSelectedItem().getValue();

		if (format.equals(Constants.FORMAT_CSV)) {
			StringBuffer dataInCSV = this.generateCSVData(accountNo, accountName, subAccount, entityNo,
					businessUnit, govtEInvFlag, returnStatus, requestNo, requestDateFrom, requestDateTo,
					invoiceDateFrom, invoiceDateTo, getUserLoginIdAndDomain());
			AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
					dataInCSV.toString());
			Filedownload.save(media);
		}
	}

	private StringBuffer generateCSVData(String accountNo, String accountName, AmtbAccount subAccount,
			String entityNo, String businessUnit, String govtEInvFlag, String returnStatus, String requestNo,
			String requestDateFrom, String requestDateTo, String invoiceDateFrom, String invoiceDateTo,
			String printedBy) {

		StringBuffer sb = new StringBuffer();

		// Report Header
		sb.append(Constants.TEXT_QUALIFIER + "Integrated Billing System" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Government eInvoice Report (Summary)"
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Selection Criteria:" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		if (accountNo != null && accountNo.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Account No: " + accountNo + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (accountName != null && accountName.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Account Name: " + accountName + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		}
		if (subAccount != null) {
			String title = "";
			if (subAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))
				title = "Division";
			else if (subAccount.getAccountCategory().equals(
					NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
				title = "Department";
			sb.append(Constants.TEXT_QUALIFIER + title + ": " + subAccount.getAccountName()
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (entityNo != null && entityNo.length() > 0) {
			FmtbEntityMaster entityMaster = (FmtbEntityMaster) MasterSetup.getEntityManager().getMaster(
					new Integer(entityNo));
			sb.append(Constants.TEXT_QUALIFIER + "Entity: " + entityMaster.getEntityName()
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (businessUnit != null && businessUnit.length() > 0) {
			MstbMasterTable buMaster = ConfigurableConstants.getMasterTable(
					ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, businessUnit);
			sb.append(Constants.TEXT_QUALIFIER + "Business Unit: " + buMaster.getMasterValue()
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (govtEInvFlag != null && govtEInvFlag.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Govt eInvoice: "
					+ NonConfigurableConstants.GOVT_EINV_FLAGS.get(govtEInvFlag) + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		}
		if (returnStatus != null && returnStatus.length() > 0) {
			String label = "PENDING";
			if (returnStatus.equals(NonConfigurableConstants.GOVT_EINV_RETURN_STATUS_SUCCESS))
				label = "SUCCESS";
			else if (returnStatus.equals(NonConfigurableConstants.GOVT_EINV_RETURN_STATUS_REJECT))
				label = "SUCCESS";
			sb.append(Constants.TEXT_QUALIFIER + "Return Status: " + label + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (requestNo != null && requestNo.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Request No: " + requestNo + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (requestNo != null && requestNo.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Request No: " + requestNo + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (requestDateFrom != null && requestDateFrom.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Request Date From: " + requestDateFrom
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Request Date To: " + requestDateTo
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");

			requestDateTo += " 23:59:59";
		}
		if (invoiceDateFrom != null && invoiceDateFrom.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Invoice Date From: " + invoiceDateFrom
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Invoice Date To: " + invoiceDateTo
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}

		// Line Break
		sb.append("\n");

		sb.append(Constants.TEXT_QUALIFIER + "Printed By: " + printedBy + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		// Column Title
		sb.append(Constants.TEXT_QUALIFIER + "Entity" + Constants.TEXT_QUALIFIER + ","); // 0
		sb.append(Constants.TEXT_QUALIFIER + "Vendor Id" + Constants.TEXT_QUALIFIER + ","); // 1
		sb.append(Constants.TEXT_QUALIFIER + "Account No" + Constants.TEXT_QUALIFIER + ","); // 2
		sb.append(Constants.TEXT_QUALIFIER + "Account Name" + Constants.TEXT_QUALIFIER + ","); // 3
		sb.append(Constants.TEXT_QUALIFIER + "Div Code" + Constants.TEXT_QUALIFIER + ","); // 4
		sb.append(Constants.TEXT_QUALIFIER + "Div Name" + Constants.TEXT_QUALIFIER + ","); // 5
		sb.append(Constants.TEXT_QUALIFIER + "Dept Code" + Constants.TEXT_QUALIFIER + ","); // 6
		sb.append(Constants.TEXT_QUALIFIER + "Dept Name" + Constants.TEXT_QUALIFIER + ","); // 7
		sb.append(Constants.TEXT_QUALIFIER + "Business Unit" + Constants.TEXT_QUALIFIER + ","); // 8
		sb.append(Constants.TEXT_QUALIFIER + "Government eInvoice" + Constants.TEXT_QUALIFIER + ","); // 9
		sb.append(Constants.TEXT_QUALIFIER + "Attention To" + Constants.TEXT_QUALIFIER + ","); // 10
		sb.append(Constants.TEXT_QUALIFIER + "Email Address" + Constants.TEXT_QUALIFIER + ","); // 11
		sb.append(Constants.TEXT_QUALIFIER + "Invoice Date" + Constants.TEXT_QUALIFIER + ","); // 12
		sb.append(Constants.TEXT_QUALIFIER + "Invoice No" + Constants.TEXT_QUALIFIER + ","); // 13
		sb.append(Constants.TEXT_QUALIFIER + "Invoice Amount" + Constants.TEXT_QUALIFIER + ","); // 14
		sb.append(Constants.TEXT_QUALIFIER + "Request Date" + Constants.TEXT_QUALIFIER + ","); // 15
		sb.append(Constants.TEXT_QUALIFIER + "Request No" + Constants.TEXT_QUALIFIER + ","); // 16
		sb.append(Constants.TEXT_QUALIFIER + "Outgoing Filename" + Constants.TEXT_QUALIFIER + ","); // 17
		sb.append(Constants.TEXT_QUALIFIER + "Return File Upload Date" + Constants.TEXT_QUALIFIER + ","); // 18
		sb.append(Constants.TEXT_QUALIFIER + "Return Status" + Constants.TEXT_QUALIFIER + ","); // 19
		sb.append(Constants.TEXT_QUALIFIER + "Return Remarks" + Constants.TEXT_QUALIFIER + ","); // 20
		sb.append("\n");

		// Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getGovtEInv(accountNo,
				accountName, subAccount != null ? subAccount.getAccountNo().toString() : "", entityNo,
				businessUnit, govtEInvFlag, returnStatus, requestNo, requestDateFrom, requestDateTo,
				invoiceDateFrom, invoiceDateTo);
		for (Object[] columnDataObject : rowsOfData) {
			for (int i = 0; i < columnDataObject.length; i++) {
				Object data = columnDataObject[i];

				if (i == 4 || i == 6) {
					if (data != null)
						sb.append("" + Constants.TEXT_QUALIFIER + "'"
								+ data.toString().replaceAll("\"", "\"\"") + Constants.TEXT_QUALIFIER + ",");
					else
						sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
				} else if (i == 9) {
					if (data != null)
						sb.append("" + Constants.TEXT_QUALIFIER
								+ NonConfigurableConstants.GOVT_EINV_FLAGS.get(data.toString())
								+ Constants.TEXT_QUALIFIER + ",");
					else
						sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
				}
				// Return Status
				else if (i == 19) {
					System.out.println("data19: " + data);
					if (data != null && !data.equals("PENDING"))
						sb.append("" + Constants.TEXT_QUALIFIER
								+ NonConfigurableConstants.GOVT_EINV_RETURN_STATUS.get(data.toString())
								+ Constants.TEXT_QUALIFIER + ",");
					else if (data != null && data.equals("PENDING"))
						sb.append(Constants.TEXT_QUALIFIER + "PENDING" + Constants.TEXT_QUALIFIER + ",");
					else
						sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
				} else {
					if (data != null)
						sb.append("" + Constants.TEXT_QUALIFIER + data.toString().replaceAll("\"", "\"\"")
								+ Constants.TEXT_QUALIFIER + ",");
					else
						sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
				}
			}
			sb.append("\n");
		}

		if (rowsOfData.size() == 0) {
			sb.append(Constants.TEXT_QUALIFIER + "No records found" + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}

		return sb;
	}

	public void clearAccountNameComboBox() {
		accountNameCB.getChildren().clear();
		accountNameCB.setText("");
	}

	public void clearDivDeptListBox() {
		divDeptLB.getChildren().clear();
		divDeptLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		divDeptLB.setSelectedIndex(0);
	}

	public void reset() {
		accountNoIB.setText("");
		this.clearAccountNameComboBox();
		this.clearDivDeptListBox();
		entityLB.setSelectedIndex(0);
		businessUnitLB.setSelectedIndex(0);
		govtEInvLB.setSelectedIndex(0);
		returnStatusLB.setSelectedIndex(0);
		requestNoIB.setText("");
		requestDateFromDB.setText("");
		requestDateToDB.setText("");
		invoiceDateFromDB.setText("");
		invoiceDateToDB.setText("");
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}