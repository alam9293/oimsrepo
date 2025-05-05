package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class RedeemedVoucherWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UnredeemedVoucherWindow.class);

	private String report = "Redeemed Voucher";
	private Long reportRsrcId;

	private Listbox itemTypeLB, entityLB;
	private Decimalbox serialNoStartDMB, serialNoEndDMB;
	private Datebox batchDateFromDB, batchDateToDB, redeemDateFromDB, redeemDateToDB;
	private Intbox accountNoIntBox;
	private Combobox accountNameComboBox;

	public RedeemedVoucherWindow() throws IOException {
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		itemTypeLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		List<ImtbItemType> itemTypes = this.businessHelper.getInventoryBusiness().getItemTypes();
		for (ImtbItemType itemType : itemTypes) {
			Listitem listItem = new Listitem(itemType.getTypeName(), itemType);
			itemTypeLB.appendChild(listItem);
		}
		if (itemTypeLB.getSelectedItem() == null)
			itemTypeLB.setSelectedIndex(0);
		
		entityLB.appendChild(ComponentUtil.createNotRequiredListItem());
		List<FmtbEntityMaster> entityList = this.businessHelper.getAdminBusiness().getEntities();
		for(FmtbEntityMaster entity : entityList){
			Listitem listItem = new Listitem(entity.getEntityName(), entity);
			entityLB.appendChild(listItem);
		}
		if (entityLB.getSelectedItem() == null)
			entityLB.setSelectedIndex(0);
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
		
		BigDecimal serialNoStart = serialNoStartDMB.getValue();
		BigDecimal serialNoEnd = serialNoEndDMB.getValue();
		Date batchDateFrom = DateUtil.convertUtilDateToSqlDate(batchDateFromDB.getValue());
		Date batchDateTo = DateUtil.convertUtilDateToSqlDate(batchDateToDB.getValue());
		Date redeemDateFrom = DateUtil.convertUtilDateToSqlDate(redeemDateFromDB.getValue());
		Date redeemDateTo = DateUtil.convertUtilDateToSqlDate(redeemDateToDB.getValue());
		ImtbItemType itemType = itemTypeLB.getSelectedItem().getValue() != null ? ((ImtbItemType) itemTypeLB
				.getSelectedItem().getValue()) : null;
		FmtbEntityMaster entity = entityLB.getSelectedItem().getValue() != "" ? ((FmtbEntityMaster) entityLB
				.getSelectedItem().getValue()) : null;
		int accountNo = accountNoIntBox.getValue() != null ? accountNoIntBox.getValue() : 0;
		String accountName = accountNameComboBox.getValue();
		
		if (batchDateFrom==null && batchDateTo==null
				&& redeemDateFrom == null && redeemDateTo==null){
			throw new WrongValueException(
					"Either Batch Date of Redeemed Date field is mandatory");
		}
		/*else if (itemType==null) {
			throw new WrongValueException("Item Type field is mandatory");
		}*/
		else if (batchDateFrom != null && batchDateTo != null) {
			if (batchDateFrom.compareTo(batchDateTo) > 0)
				throw new WrongValueException(batchDateFromDB,
						"Batch Date Start cannot be later than Batch Date End");
		}
		else if (redeemDateFrom != null && redeemDateTo != null){
			if (redeemDateFrom.compareTo(redeemDateTo) > 0)
				throw new WrongValueException(redeemDateFromDB,
						"Redeem Date Start cannot be later than Redeem Date End");
		}
		
		if(accountNo == 0 && !accountName.trim().equals(""))
			throw new WrongValueException(accountNoIntBox, "* Mandatory field if theres Account Name.");
		else if(accountNo != 0 && accountName.trim().equals(""))
			throw new WrongValueException(accountNameComboBox, "* Mandatory field if theres Account No.");
		else if(accountNo != 0 && !accountName.trim().equals(""))
		{
			Combobox accountNameComboBox2 = (Combobox)this.getFellow("accountNameComboBox");
			if(accountNameComboBox2.getSelectedItem()==null)
				throw new WrongValueException(accountNameComboBox, "* Invalid Account Name for Account No.");
			else{
				AmtbAccount topLevelAccount = (AmtbAccount)accountNameComboBox2.getSelectedItem().getValue();
				if(topLevelAccount==null)
					throw new WrongValueException(accountNameComboBox, "* Mandatory field");
			}
		}
		
		if (serialNoStart != null && serialNoEnd == null) {
			serialNoEnd = serialNoStart;
			serialNoEndDMB.setValue(serialNoStart);
		} else if (serialNoStart == null && serialNoEnd != null) {
			serialNoStart = serialNoEnd;
			serialNoStartDMB.setValue(serialNoEnd);
		}
		
		if (batchDateFrom != null && batchDateTo == null) {
			batchDateTo = batchDateFrom;
			batchDateToDB.setValue(batchDateFromDB.getValue());
		} else if (batchDateFrom == null && batchDateTo != null) {
			batchDateFrom = batchDateTo;
			batchDateFromDB.setValue(batchDateToDB.getValue());
		}
		
		if (redeemDateFrom != null && redeemDateTo == null) {
			redeemDateTo = redeemDateFrom;
			redeemDateToDB.setValue(redeemDateFromDB.getValue());
		} else if (redeemDateFrom == null && redeemDateTo != null) {
			redeemDateFrom = redeemDateTo;
			redeemDateFromDB.setValue(redeemDateToDB.getValue());
		}


		// retrieve format
		Listbox formatList = (Listbox) this.getFellow("reportFormat");
		if (formatList.getSelectedItem() == null)
			throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String) formatList.getSelectedItem().getLabel();
		String format = (String) formatList.getSelectedItem().getValue();

		StringBuffer dataInCSV = this.generateCSVData(serialNoStart, serialNoEnd, itemType, getUserLoginIdAndDomain(), batchDateFrom, batchDateTo, redeemDateFrom, redeemDateTo, accountNo, accountName, entity);
		AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
				dataInCSV.toString());
		Filedownload.save(media);
	}

	private StringBuffer generateCSVData(BigDecimal serialNoStart, BigDecimal serialNoEnd, ImtbItemType itemType,
			String printedBy, Date batchDateFrom, Date batchDateTo, Date redeemDateFrom,Date redeemDateTo, int accountNo, String accountName, FmtbEntityMaster entity) {

		StringBuffer sb = new StringBuffer();

		// Report Header
		sb.append(Constants.TEXT_QUALIFIER + "Integrated Billing System" + Constants.TEXT_QUALIFIER
				+ ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Redeemed Voucher"
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Selection Criteria:" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		if (serialNoStart != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Serial No Start: " + serialNoStart.toString() + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Serial No End: " + serialNoEnd.toString() + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		if (itemType != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Item Type: " + itemType.getTypeName()	+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		if (batchDateFrom != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Batch Start Date: " + DateUtil.convertDateToStr(batchDateFrom, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Batch End Date: " + DateUtil.convertDateToStr(batchDateTo, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		if (redeemDateFrom != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Redeem Start Date: " + DateUtil.convertDateToStr(redeemDateFrom, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Redeem End Date: " + DateUtil.convertDateToStr(redeemDateTo, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		if(accountNo != 0)
		{
			sb.append(Constants.TEXT_QUALIFIER + "Account No: " + accountNo + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Account Name: " + accountName + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (entity != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Entity: " + entity.getEntityName() + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		// Line Break
		sb.append("\n");

		sb.append(Constants.TEXT_QUALIFIER + "Printed By: " + printedBy + Constants.TEXT_QUALIFIER
				+ ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

									
		
		// Column Title
		sb.append(Constants.TEXT_QUALIFIER + "S/N" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account No." + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account Name." + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Redemption Date" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Redemption Batch No" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Redemption Point" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Cashier ID" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Status" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Batch Date" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Voucher No" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Face Amount ($)" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Data
		List<ImtbItem> vouchers = this.businessHelper.getReportBusiness().getRedeemedVoucher(
				serialNoStart, serialNoEnd, 
				itemType != null ? itemType.getItemTypeNo() : null, batchDateFrom,  DateUtil.convertDateTo2359Hours(batchDateTo), 
				redeemDateFrom,  DateUtil.convertDateTo2359Hours(redeemDateTo), accountNo, entity != null ? entity.getEntityNo() : null);

		if (vouchers.size() == 0) {
			sb.append(Constants.TEXT_QUALIFIER + "No records found" + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		} else {
			
			BigDecimal total = new BigDecimal(0);
			Integer counter = 0;
			for (ImtbItem voucher : vouchers) {
				
				sb.append("" + Constants.TEXT_QUALIFIER + (++counter) + Constants.TEXT_QUALIFIER + ",");
				AmtbAccount topAccount = AccountUtil.getTopLevelAccount(voucher.getImtbIssue().getImtbIssueReq().getAmtbAccount());
				sb.append("" + Constants.TEXT_QUALIFIER + topAccount.getCustNo()+ Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + topAccount.getAccountName()+ Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + DateUtil.convertDateToStr(voucher.getRedeemTime(), DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + voucher.getBatchNo()+ Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + voucher.getRedeemPoint() + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + voucher.getCashierId() + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + NonConfigurableConstants.INVENTORY_ITEM_STATUS.get(voucher.getStatus()) + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + DateUtil.convertDateToStr(voucher.getBatchDate(), DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + voucher.getSerialNo() + Constants.TEXT_QUALIFIER + ",");
				BigDecimal faceAmount = voucher.getImtbItemType().getPrice();
				sb.append("" + Constants.TEXT_QUALIFIER + faceAmount + Constants.TEXT_QUALIFIER + ",");
				if(faceAmount!=null){
					total = total.add(faceAmount);
				}
				sb.append("\n");
			}
			
			
			for(int i=0; i<7; i++){
				sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			}
			sb.append(Constants.TEXT_QUALIFIER + "TOTAL" + Constants.TEXT_QUALIFIER + ",");
			sb.append(Constants.TEXT_QUALIFIER +  total + Constants.TEXT_QUALIFIER + ",");
		}

		return sb;
	}

	public void reset() {
		
		serialNoStartDMB.setText("");
		serialNoEndDMB.setText("");
		batchDateFromDB.setText("");
		batchDateToDB.setText(""); 
		redeemDateFromDB.setText(""); 
		redeemDateToDB.setText("");
		itemTypeLB.setSelectedIndex(0);
		accountNoIntBox.setText("");
		accountNameComboBox.setText("");
		entityLB.setSelectedIndex(0);
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}

	public void searchBillableAccountByAccountNo() throws InterruptedException{
		logger.info("");

		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		Integer accountNo = accountNoIntBox.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		//Clear combobox for a new search
		accountNameComboBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getBillGenBusiness().getBilliableAccountOnlyTopLevelWithEffectiveEntity(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameComboBox.setSelectedIndex(0);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");

		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");

			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				accountNoIntBox.setText(selectedAccount.getCustNo());
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void searchBillableAccountByAccountName(String name) throws InterruptedException{
		logger.info("");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		
		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}

		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")")) {
				return;
			}
		}

		//clear textbox for a new search
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getBillGenBusiness().getBilliableAccountOnlyTopLevelWithEffectiveEntity(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1){
				accountNameComboBox.setSelectedIndex(0);
			}
			else accountNameComboBox.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
}