package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class AccountReportDetailedWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AccountReportDetailedWindow.class);

	private Long reportRsrcId;
	private String report = "AccReportCR";

	private Intbox accountNoIB;
	private Combobox accountNameCB,sortbyCB,accountstsCB;
	private Combobox entityCB;

	public AccountReportDetailedWindow() throws IOException {
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		// populate entity list
		entityCB.appendChild(new Comboitem("All"));
		Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
		for (Integer entityNo : entities.keySet()) {
			
			Comboitem item = new Comboitem(entities.get(entityNo));
			System.out.println(" :account.getAcctStatus()"+entities.get(entityNo));
			entityCB.appendChild(item);
		}
		entityCB.setSelectedIndex(0);
		
		Comboitem item = new Comboitem("ACCOUNT NAME");
		sortbyCB.appendChild(item);
	}

	public void loadAccountName() throws InterruptedException {
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

		try {
			List<AmtbAccount> accounts = this.businessHelper.getReportBusiness().searchTopLevelAccount(
					accountNo.toString(), null);
			System.out.println(" :account.getAccountName()"+accounts.size());
			for (AmtbAccount account : accounts) {
				
				Comboitem item = new Comboitem(account.getAccountName());
				item.setValue(account);
				System.out.println(" :account.getAccountName()"+account.getAccountName());
				accountNameCB.appendChild(item);
			}
			if(accounts.size()>0)
			accountNameCB.setSelectedIndex(0);
			this.onSelectAccountName();
			
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectAccountName() throws InterruptedException 
	{
		try 
		{
			loadAccountStatus();
		} 
		catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void loadAccountStatus()  throws InterruptedException {
	
		try 
		{
			List<AmtbAcctStatus> stsofaccounts = this.businessHelper.getReportBusiness().getAccountstsAllbyAccnoandAccName(
					accountNoIB.getValue().toString(), accountNameCB.getValue());
			System.out.println(" :stsofaccounts.size()"+stsofaccounts.size());
			accountstsCB.getChildren().clear();
			
			for (AmtbAcctStatus accstatus : stsofaccounts) {
				
				Comboitem item = new Comboitem(accstatus.getAcctStatus());
				//System.out.println(" :account.getAcctStatus()"+accstatus.getAcctStatus());
				accountstsCB.appendChild(item);
			}
			accountstsCB.setSelectedIndex(0);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void populateReportFormatList(Listbox listbox) throws NetException {
		System.out.println("reportRsrcId :"+reportRsrcId);
		List<MstbReportFormatMap> reportFormatMapList = this.businessHelper.getReportBusiness()
				.getReportFormatMap(this.reportRsrcId);
		boolean firstItem = true;
		System.out.println("reportFormatMapList :"+reportFormatMapList.size());
		for (MstbReportFormatMap formatMap : reportFormatMapList) {
			Listitem listItem = new Listitem(formatMap.getReportFormat(),
					Constants.EXTENSION_MAP.get(formatMap.getReportFormat()));
			if (firstItem) {
				listItem.setSelected(true);
				firstItem = false;
			}
			System.out.println("ListItem :"+listItem);
			listbox.appendChild(listItem);
		}
	}

	public void generate() throws HttpException, IOException, InterruptedException, NetException,
			WrongValueException {
		logger.info("");
		this.displayProcessing();

		String accountNo = "";
		String accountName = "";
		String accStatus = "";
		String sortBy = "";
		String Entity = "";
		

		if (accountNoIB.getValue() != null) 
		{
			accountNo = accountNoIB.getValue().toString();
		}
		if (accountNameCB.getSelectedItem() != null) 
		{
			accountName = accountNameCB.getValue();
		} 
		if (accountstsCB.getSelectedItem() != null) 
		{
			accStatus = accountstsCB.getValue();
		} 
		if (sortbyCB.getValue()!= null) 
		{
			sortBy = sortbyCB.getValue();
		}
		if (entityCB.getValue()!= null) 
		{
			Entity = entityCB.getValue();
		}
		//System.out.println("sortBy :"+sortBy);
		//System.out.println("Entity :"+Entity);
		// retrieve format
		Listbox formatList = (Listbox) this.getFellow("reportFormat");
		if (formatList.getSelectedItem() == null)
			throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String) formatList.getSelectedItem().getLabel();
		String format = (String) formatList.getSelectedItem().getValue();

		if (format.equals(Constants.FORMAT_CSV)) {
			StringBuffer dataInCSV = this.generateCSVData(accountNo, accountName, accStatus, sortBy, Entity,getUserLoginIdAndDomain());
			AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
					dataInCSV.toString());
			Filedownload.save(media);
		}
	}

	private StringBuffer generateCSVData(String accountNo, String accountName,
			String accStatus, String sortBy, String Entity,
			String printedBy) {

		System.out.println("accountName :"+accountName);
		StringBuffer sb = new StringBuffer();

		// Report Header
		if (accountNo != null && accountNo.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Account No: " + accountNo + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (accountName != null && accountName.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Account Name: " + accountName + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		}
		if (accStatus != null && accStatus.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Account Status: " + accStatus + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		}
		if (sortBy != null && sortBy.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Sort By: " + sortBy + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		}
		if (Entity != null && Entity.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Entity: " + Entity + Constants.TEXT_QUALIFIER
					+ ",");
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
		sb.append(Constants.TEXT_QUALIFIER + "S/N" + Constants.TEXT_QUALIFIER + ","); // 0
		sb.append(Constants.TEXT_QUALIFIER + "Account No" + Constants.TEXT_QUALIFIER + ","); // 1
		sb.append(Constants.TEXT_QUALIFIER + "Account Name" + Constants.TEXT_QUALIFIER + ","); // 2
		sb.append(Constants.TEXT_QUALIFIER + "Account Category" + Constants.TEXT_QUALIFIER + ","); // 3
		sb.append(Constants.TEXT_QUALIFIER + "Entity" + Constants.TEXT_QUALIFIER + ","); // 4
		sb.append(Constants.TEXT_QUALIFIER + "Credit Balance" + Constants.TEXT_QUALIFIER + ","); // 5
		sb.append(Constants.TEXT_QUALIFIER + "Date Created" + Constants.TEXT_QUALIFIER + ","); // 6
		sb.append("\n");

		// Data
		List<AmtbAccount> rowsOfData = this.businessHelper.getReportBusiness().findAccDeatil(accountNo,
				accountName);
		int counter=0;
		for (AmtbAccount data : rowsOfData) {

			counter++;

			sb.append("" + Constants.TEXT_QUALIFIER
					+ counter + Constants.TEXT_QUALIFIER + ",");
			if (data.getCustNo()!= null)
				sb.append("" + Constants.TEXT_QUALIFIER + ""
						+ data.getCustNo() + Constants.TEXT_QUALIFIER + ",");
			else
				sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
			
			if (data.getAccountName() != null)
				sb.append("" + Constants.TEXT_QUALIFIER + ""
						+ data.getAccountName() + Constants.TEXT_QUALIFIER + ",");
			else
				sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
			
			if (data.getAccountCategory()!= null)
				sb.append("" + Constants.TEXT_QUALIFIER + ""
						+ data.getAccountCategory() + Constants.TEXT_QUALIFIER + ",");
			else
				sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
			
			if (Entity!= null)
				sb.append("" + Constants.TEXT_QUALIFIER + ""
						+ Entity + Constants.TEXT_QUALIFIER + ",");
			else
				sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
			
			if (data.getCreditBalance()!= null)
				sb.append("" + Constants.TEXT_QUALIFIER + ""
						+ data.getCreditBalance() + Constants.TEXT_QUALIFIER + ",");
			else
				sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
			
			if (data.getCreatedDt()!= null)
				sb.append("" + Constants.TEXT_QUALIFIER + ""
						+ data.getCreatedDt() + Constants.TEXT_QUALIFIER + ",");
			else
				sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
		
			
			
		
			sb.append("\n");
		}

		if (rowsOfData.size() == 0) {
			sb.append(Constants.TEXT_QUALIFIER + "No records found" + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}

		return sb;
	}

	public void reset() {
		accountNoIB.setText("");
		accountNameCB.getChildren().clear();
		accountNameCB.setText("");
		sortbyCB.getChildren().clear();
		sortbyCB.setText("");
		accountstsCB.getChildren().clear();
		accountstsCB.setText("");
		entityCB.getChildren().clear();
		entityCB.setText("");
		
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
}