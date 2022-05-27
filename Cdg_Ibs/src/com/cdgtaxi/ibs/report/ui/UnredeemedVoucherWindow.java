package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
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
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class UnredeemedVoucherWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UnredeemedVoucherWindow.class);

	private String report = "Unredeemed Voucher";
	private String reportCategory = "Inventory";
	private Long reportRsrcId;

	private Listbox itemTypeLB, statusLB, entityLB;
	private Datebox issuedDateFromDB, issuedDateToDB, expiryDateFromDB, expiryDateToDB, batchDateAsAtDB, redeemDateAsAtDB;

	public UnredeemedVoucherWindow() throws IOException {
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

		statusLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		for (Entry<String, String> entry : NonConfigurableConstants.INVENTORY_ITEM_STATUS
				.entrySet()) {
			if (entry.getKey().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED))
				continue;
			statusLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
		if (statusLB.getSelectedItem() == null)
			statusLB.setSelectedIndex(0);
		
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

		Date issuedDateFrom = DateUtil.convertUtilDateToSqlDate(issuedDateFromDB.getValue());
		Date issuedDateTo = DateUtil.convertUtilDateToSqlDate(issuedDateToDB.getValue());
		Date expiryDateFrom = DateUtil.convertUtilDateToSqlDate(expiryDateFromDB.getValue());
		Date expiryDateTo = DateUtil.convertUtilDateToSqlDate(expiryDateToDB.getValue());
		Date batchDateAsAt = DateUtil.convertUtilDateToSqlDate(batchDateAsAtDB.getValue());
		Date redeemDateAsAt = DateUtil.convertUtilDateToSqlDate(redeemDateAsAtDB.getValue());
		
		
		
		if (issuedDateFrom != null && issuedDateTo == null) {
			issuedDateTo = issuedDateFrom;
			issuedDateToDB.setValue(issuedDateFromDB.getValue());
		} else if (issuedDateFrom == null && issuedDateTo != null) {
			issuedDateFrom = issuedDateTo;
			issuedDateFromDB.setValue(issuedDateToDB.getValue());
		}
		if (expiryDateFrom != null && expiryDateTo == null) {
			expiryDateTo = expiryDateFrom;
			expiryDateToDB.setValue(expiryDateFromDB.getValue());
		} else if (expiryDateFrom == null && expiryDateTo != null) {
			expiryDateFrom = expiryDateTo;
			expiryDateFromDB.setValue(expiryDateToDB.getValue());
		}

		if (issuedDateFrom != null && issuedDateTo != null)
			if (issuedDateFrom.compareTo(issuedDateTo) > 0)
				throw new WrongValueException(issuedDateFromDB,
						"Issued Date Start cannot be later than Issued Date End");
		if (expiryDateFrom != null && expiryDateTo != null)
			if (expiryDateFrom.compareTo(expiryDateTo) > 0)
				throw new WrongValueException(expiryDateFromDB,
						"Expiry Date Start cannot be later than Expiry Date End");
		
		
		if (batchDateAsAt== null && redeemDateAsAt == null)
			throw new WrongValueException(
					"Either Unredeemed as at (Batch Date) or Unredeemed as at (Redeemed Date) is mandatory");
		
		ImtbItemType itemType = itemTypeLB.getSelectedItem().getValue() != null ? ((ImtbItemType) itemTypeLB
				.getSelectedItem().getValue()) : null;
		String status = statusLB.getSelectedItem().getValue() != null ? (String) statusLB
				.getSelectedItem().getValue() : null;
		FmtbEntityMaster entity = entityLB.getSelectedItem().getValue() != "" ? ((FmtbEntityMaster) entityLB
				.getSelectedItem().getValue()) : null;
				
		// retrieve format
		Listbox formatList = (Listbox) this.getFellow("reportFormat");
		if (formatList.getSelectedItem() == null)
			throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String) formatList.getSelectedItem().getLabel();
		String format = (String) formatList.getSelectedItem().getValue();

		StringBuffer dataInCSV = this.generateCSVData(issuedDateFrom, issuedDateTo, expiryDateFrom,
				expiryDateTo, itemType, status, getUserLoginIdAndDomain(), batchDateAsAt, redeemDateAsAt, entity);
		AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
				dataInCSV.toString());
		Filedownload.save(media);
	}

	private StringBuffer generateCSVData(Date issuedDateFrom, Date issuedDateTo,
			Date expiryDateFrom, Date expiryDateTo, ImtbItemType itemType, String status,
			String printedBy, Date batchDateAsAt, Date redeemDateAsAt, FmtbEntityMaster entity) {

		StringBuffer sb = new StringBuffer();

		// Report Header
		sb.append(Constants.TEXT_QUALIFIER + "Integrated Billing System" + Constants.TEXT_QUALIFIER
				+ ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Unredeemed Voucher"
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Selection Criteria:" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		if (issuedDateFrom != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Issued Date Start: " + DateUtil.convertDateToStr(issuedDateFrom, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Issued Date End: " + DateUtil.convertDateToStr(issuedDateTo, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (expiryDateFrom != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Expiry Date Start: " + DateUtil.convertDateToStr(expiryDateFrom, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Expiry Date End: " + DateUtil.convertDateToStr(expiryDateTo, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (itemType != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Item Type: " + itemType.getTypeName()	+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (status != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Status: " + NonConfigurableConstants.INVENTORY_ITEM_STATUS.get(status) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (entity != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Entity: " + entity.getEntityName() + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		if (batchDateAsAt != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Unredeemed as at (Batch Date):" + DateUtil.convertDateToStr(batchDateAsAt, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		if (redeemDateAsAt != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Unredeemed as at (Redeemed Date): " + DateUtil.convertDateToStr(redeemDateAsAt, DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
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
		sb.append(Constants.TEXT_QUALIFIER + "S/N." + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Stock Issued Date" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account No" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account Name" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Item Type" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Expired Date" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Status" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Serial No." + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Face Amount ($)" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Data
		List<ImtbItem> vouchers = this.businessHelper.getReportBusiness().getUnredeemedVoucher(
				issuedDateFrom, DateUtil.convertDateTo2359Hours(issuedDateTo), expiryDateFrom, expiryDateTo,
				itemType != null ? itemType.getItemTypeNo() : null, status, DateUtil.convertDateTo2359Hours(batchDateAsAt),  DateUtil.convertDateTo2359Hours(redeemDateAsAt),
				entity != null ? entity.getEntityNo() : null);
		
		Integer counter = 0;
		for (ImtbItem voucher : vouchers) {
			AmtbAccount account = voucher.getImtbIssue().getImtbIssueReq().getAmtbAccount();
			while(account.getAmtbAccount() != null)
				account = account.getAmtbAccount();
			sb.append("" + Constants.TEXT_QUALIFIER + (++counter) + Constants.TEXT_QUALIFIER + ",");
			sb.append("" + Constants.TEXT_QUALIFIER + DateUtil.convertDateToStr(voucher.getCreatedDt(), DateUtil.LAST_UPDATED_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("" + Constants.TEXT_QUALIFIER + account.getCustNo() + Constants.TEXT_QUALIFIER + ",");
			sb.append("" + Constants.TEXT_QUALIFIER + account.getAccountName() + Constants.TEXT_QUALIFIER + ",");
			sb.append("" + Constants.TEXT_QUALIFIER + voucher.getImtbItemType().getTypeName() + Constants.TEXT_QUALIFIER + ",");
			sb.append("" + Constants.TEXT_QUALIFIER + DateUtil.convertDateToStr(voucher.getImtbIssue().getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT) + Constants.TEXT_QUALIFIER + ",");
			sb.append("" + Constants.TEXT_QUALIFIER + NonConfigurableConstants.INVENTORY_ITEM_STATUS.get(voucher.getStatus()) + Constants.TEXT_QUALIFIER + ",");
			sb.append("" + Constants.TEXT_QUALIFIER + voucher.getSerialNo() + Constants.TEXT_QUALIFIER + ",");
			sb.append("" + Constants.TEXT_QUALIFIER + voucher.getImtbItemType().getPrice() + Constants.TEXT_QUALIFIER + ",");
			//sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}

		if (vouchers.size() == 0) {
			sb.append(Constants.TEXT_QUALIFIER + "No records found" + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		}

		return sb;
	}

	public void reset() {
		issuedDateFromDB.setText("");
		issuedDateToDB.setText("");
		expiryDateFromDB.setText("");
		expiryDateToDB.setText("");
		batchDateAsAtDB.setText("");
		redeemDateAsAtDB.setText("");
		itemTypeLB.setSelectedIndex(0);
		statusLB.setSelectedIndex(0);
		entityLB.setSelectedIndex(0);
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}