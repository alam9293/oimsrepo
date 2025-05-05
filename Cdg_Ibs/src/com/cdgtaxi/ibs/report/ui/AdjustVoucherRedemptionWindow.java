package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class AdjustVoucherRedemptionWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AdjustVoucherRedemptionWindow.class);

	private String report = "Adjust Voucher Redemption";
	private String reportCategory = "Inventory";
	private Long reportRsrcId;
	private Map<String, String> actionTypeMap = new HashMap<String, String>();
	
	private Listbox itemTypeLB, approvalStatusLB, actionTypeLB, entityLB;
	private Datebox actionStartDateDB, actionEndDateDB, approvalStartDateDB, approvalEndDateDB;
	private Decimalbox serialNoStartDMB, serialNoEndDMB;
	private Textbox batchNoTB;

	public AdjustVoucherRedemptionWindow() throws IOException {
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
		
		actionTypeMap.put(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION, "EDIT");
		actionTypeMap.put(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION, "REMOVE");
		actionTypeMap.put(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID, "VOID");
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

		actionTypeLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		actionTypeLB.appendChild(new Listitem(actionTypeMap.get(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID), NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID));
		actionTypeLB.appendChild(new Listitem(actionTypeMap.get(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION), NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION));
		actionTypeLB.appendChild(new Listitem(actionTypeMap.get(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION), NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION));
		if (actionTypeLB.getSelectedItem() == null)
			actionTypeLB.setSelectedIndex(0);
		
		approvalStatusLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		approvalStatusLB.appendChild(new Listitem(NonConfigurableConstants.INVENTORY_REQUEST_STATUS.get(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING), NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING));
		approvalStatusLB.appendChild(new Listitem(NonConfigurableConstants.INVENTORY_REQUEST_STATUS.get(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED), NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED));
		approvalStatusLB.appendChild(new Listitem(NonConfigurableConstants.INVENTORY_REQUEST_STATUS.get(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_REJECTED), NonConfigurableConstants.INVENTORY_REQUEST_STATUS_REJECTED));
		if (approvalStatusLB.getSelectedItem() == null)
			approvalStatusLB.setSelectedIndex(0);
		
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
		
		String batchNo = batchNoTB.getValue();
		Date actionStartDate = DateUtil.convertUtilDateToSqlDate(actionStartDateDB.getValue());
		Date actionEndDate = DateUtil.convertUtilDateToSqlDate(actionEndDateDB.getValue());
		Date approvalStartDate = DateUtil.convertUtilDateToSqlDate(approvalStartDateDB.getValue());
		Date approvalEndDate = DateUtil.convertUtilDateToSqlDate(approvalEndDateDB.getValue());
		BigDecimal serialNoStart = serialNoStartDMB.getValue();
		BigDecimal serialNoEnd = serialNoEndDMB.getValue();
		
		if (actionStartDate != null && actionEndDate == null) {
			actionEndDate = actionStartDate;
			actionEndDateDB.setValue(actionStartDateDB.getValue());
		} else if (actionStartDate == null && actionEndDate != null) {
			actionStartDate = actionEndDate;
			actionEndDateDB.setValue(actionStartDateDB.getValue());
		}
		if (approvalStartDate != null && approvalEndDate == null) {
			approvalEndDate = approvalStartDate;
			approvalEndDateDB.setValue(approvalStartDateDB.getValue());
		} else if (approvalStartDate == null && approvalEndDate != null) {
			approvalStartDate = approvalEndDate;
			approvalStartDateDB.setValue(approvalEndDateDB.getValue());
		}
		if (serialNoStart != null && serialNoEnd == null) {
			serialNoEnd = serialNoStart;
			serialNoEndDMB.setValue(serialNoStartDMB.getValue());
		} else if (serialNoStart == null && serialNoEnd != null) {
			serialNoStart = serialNoEnd;
			serialNoStartDMB.setValue(serialNoEndDMB.getValue());
		}

		if (actionStartDate != null && actionEndDate != null)
			if (actionStartDate.compareTo(actionEndDate) > 0)
				throw new WrongValueException(actionStartDateDB,
						"Action Date Start cannot be later than Action Date End");
		if (approvalStartDate != null && approvalEndDate != null)
			if (approvalStartDate.compareTo(approvalEndDate) > 0)
				throw new WrongValueException(approvalStartDateDB,
						"Approval Date Start cannot be later than Approval Date End");
		if (serialNoStart != null && serialNoEnd != null)
			if (serialNoStart.compareTo(serialNoEnd) > 0)
				throw new WrongValueException(serialNoStartDMB,
						"Serial No Start cannot be greater than Serial No End");

		if (actionStartDate == null && actionEndDate == null && approvalStartDate == null
				&& approvalEndDate == null && (batchNo == null || batchNo.length() == 0))
			throw new WrongValueException(
					"Either Action Date Range or Approval Date Range or Batch No is mandatory");

		ImtbItemType itemType = itemTypeLB.getSelectedItem().getValue() != null ? ((ImtbItemType) itemTypeLB
				.getSelectedItem().getValue()) : null;
		String approvalStatus = approvalStatusLB.getSelectedItem().getValue() != null ? (String) approvalStatusLB
				.getSelectedItem().getValue() : null;
		String actionType = actionTypeLB.getSelectedItem().getValue() != null ? (String) actionTypeLB
				.getSelectedItem().getValue() : null;
		FmtbEntityMaster entity = entityLB.getSelectedItem().getValue() != "" ? ((FmtbEntityMaster) entityLB
				.getSelectedItem().getValue()) : null;
		
		// retrieve format
		Listbox formatList = (Listbox) this.getFellow("reportFormat");
		if (formatList.getSelectedItem() == null)
			throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String) formatList.getSelectedItem().getLabel();
		String format = (String) formatList.getSelectedItem().getValue();

		StringBuffer dataInCSV = this.generateCSVData(batchNo != null ? batchNo : "",
				actionStartDate != null ? actionStartDateDB.getText() : null,
				actionEndDate != null ? actionEndDateDB.getText() : null,
				approvalStartDate != null ? approvalStartDateDB.getText() : null,
				approvalEndDate != null ? approvalEndDateDB.getText() : null,
				serialNoStart != null ? serialNoStart.toString() : null,
				serialNoEnd != null ? serialNoEnd.toString() : null, itemType, actionType,
				approvalStatus, getUserLoginIdAndDomain(),
				entity);
		AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
				dataInCSV.toString());
		Filedownload.save(media);
	}

	private StringBuffer generateCSVData(String batchNo, String actionStartDate,
			String actionEndDate, String approvalStartDate, String approvalEndDate,
			String serialNoStart, String serialNoEnd, ImtbItemType itemType, String actionType,
			String approvalStatus, String printedBy, FmtbEntityMaster entity) {

		StringBuffer sb = new StringBuffer();

		// Report Header
		sb.append(Constants.TEXT_QUALIFIER + "Integrated Billing System" + Constants.TEXT_QUALIFIER
				+ ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Adjust Voucher Redemption"
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Selection Criteria:" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");
		
		if (batchNo != null && batchNo.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Batch No: " + batchNo + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (actionStartDate != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Action Start Date: " + actionStartDate + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Action End Date: " + actionEndDate + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			actionEndDate += " 23:59:59";
		}
		else{
			actionStartDate = "";
			actionEndDate = "";
		}
		if (approvalStartDate != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Approval Start Date: " + approvalStartDate + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Approval End Date: " + approvalEndDate + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			approvalEndDate += " 23:59:59";
		}
		else{
			approvalStartDate = "";
			approvalEndDate = "";
		}
		if (serialNoStart != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Serial No Start: " + serialNoStart + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Serial No End: " + serialNoEnd + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		else{
			serialNoStart = "";
			serialNoEnd = "";
		}
		if (itemType != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Item Type: " + itemType.getTypeName()	+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		if (actionType != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Action Type: " + actionTypeMap.get(actionType) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		else actionType = "";
		if (approvalStatus != null) {
			sb.append(Constants.TEXT_QUALIFIER + "Approval Status: " + NonConfigurableConstants.INVENTORY_REQUEST_STATUS.get(approvalStatus) + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		else approvalStatus = "";
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
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Original" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Adjustment" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "" + Constants.TEXT_QUALIFIER + ",");
		
		sb.append("\n");
		
		sb.append(Constants.TEXT_QUALIFIER + "S/N." + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Redemption Date" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Redemption Batch No" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Redemption Point" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Cashier ID" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account No" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account Name" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Item Type" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Serial No." + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Face Amount ($)" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account No" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account Name" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Item Type" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Serial No." + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Face Amount ($)" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Action" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Adjustment Amount ($)" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Request Date" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Request By" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Requestor Reason" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Requestor Remarks" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Approval Date" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Approval By" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Approval Status" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Approval Remarks" + Constants.TEXT_QUALIFIER + ",");
		
		sb.append("\n");

		//Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness()
				.adjustVoucherRedemption(batchNo, actionStartDate, actionEndDate,
						approvalStartDate, approvalEndDate, serialNoStart, serialNoEnd,
						itemType != null ? itemType.getItemTypeNo().toString() : "", actionType,
						approvalStatus, entity != null ? entity.getEntityNo().toString() : null);
		
		int serialNoCounter = 0;
		for(Object[] columnDataObject : rowsOfData){
			serialNoCounter++;
			sb.append(""+Constants.TEXT_QUALIFIER+serialNoCounter+Constants.TEXT_QUALIFIER+",");
			for(int i=0; i<columnDataObject.length; i++){
				Object data = columnDataObject[i];
				
				if(i==14){
					if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+ actionTypeMap.get(data.toString()) +Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
				else if(i==22){
					if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+ NonConfigurableConstants.INVENTORY_REQUEST_STATUS.get(data.toString()) +Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
				else{
					if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
			}
			
			sb.append("\n");
		}
		
		if(rowsOfData.size()==0){
			sb.append(Constants.TEXT_QUALIFIER+"No records found"+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}

		return sb;
	}

	public void reset() {
		batchNoTB.setText("");
		actionStartDateDB.setText("");
		actionEndDateDB.setText("");
		approvalStartDateDB.setText("");
		approvalEndDateDB.setText("");
		serialNoStartDMB.setText("");
		serialNoEndDMB.setText("");
		itemTypeLB.setSelectedIndex(0);
		approvalStatusLB.setSelectedIndex(0);
		actionTypeLB.setSelectedIndex(0);
		entityLB.setSelectedIndex(0);
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}