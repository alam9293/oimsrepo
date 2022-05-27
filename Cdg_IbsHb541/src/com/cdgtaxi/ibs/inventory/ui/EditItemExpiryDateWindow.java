package com.cdgtaxi.ibs.inventory.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditItemExpiryDateWindow extends CommonWindow implements AfterCompose {
	private static final Logger logger = Logger.getLogger(EditItemExpiryDateWindow.class);

	
	private ImtbItem itemVoucher;
	private Label accountNoLabel, accountNameLabel, itemTypeLabel, serialNoLabel,
			statusLabel, redemptionPointLabel, redemptionDateLabel, cashierIdLabel, createdByLabel,
			createdDateLabel, createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel,
			lastUpdatedTimeLabel, batchNoLabel,requestorRemarksLabel;
	private Row divisionDepartmentRow;
	private Label divisionLabel, divisionNameLabel, departmentLabel, departmentNameLabel;
	private Datebox expiryDateDB;
	private Listbox reasonLB, resultLB;
	private Textbox remarksTextBox;
	
	@SuppressWarnings("unchecked")
	public EditItemExpiryDateWindow() {
		Map params = Executions.getCurrent().getArg();
		Long itemNo = (Long) params.get("itemNo");
		itemVoucher = businessHelper.getInventoryBusiness().getItemByItemNo(itemNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		AmtbAccount account = itemVoucher.getImtbIssue().getImtbIssueReq().getAmtbAccount();

		if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
			divisionDepartmentRow.setVisible(true);
			departmentLabel.setVisible(true);
			departmentNameLabel.setVisible(true);
			departmentNameLabel.setValue(account.getAccountName() + " (" + account.getCode() + ")");
			divisionLabel.setVisible(true);
			divisionNameLabel.setVisible(true);
			divisionNameLabel.setValue(account.getAmtbAccount().getAccountName() + " (" + account.getAmtbAccount().getCode() + ")");
		}
		else if((account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))){
			divisionDepartmentRow.setVisible(true);
			divisionLabel.setVisible(true);
			divisionNameLabel.setVisible(true);
			divisionNameLabel.setValue(account.getAccountName() + " (" + account.getCode() + ")");
		}
		
		while(account.getAmtbAccount() != null){
			account = account.getAmtbAccount();
		}
		
		accountNoLabel.setValue(account.getCustNo());
		accountNameLabel.setValue(account.getAccountName());
		itemTypeLabel.setValue(itemVoucher.getImtbItemType().getTypeName());
		serialNoLabel.setValue(itemVoucher.getSerialNo().toString());
		expiryDateDB.setValue(itemVoucher.getExpiryDate());
		statusLabel.setValue(NonConfigurableConstants.INVENTORY_ITEM_STATUS.get(itemVoucher
				.getStatus()));
		redemptionPointLabel.setValue(itemVoucher.getRedeemPoint());
		redemptionDateLabel.setValue(DateUtil.convertDateToStr(itemVoucher.getRedeemTime(),
				DateUtil.LAST_UPDATED_DATE_FORMAT));
		cashierIdLabel.setValue(itemVoucher.getCashierId());
		batchNoLabel.setValue(itemVoucher.getBatchNo());
		requestorRemarksLabel.setValue(itemVoucher.getImtbIssue().getImtbIssueReq().getRequestorRemarks());
		
		// Displaying the 4 common fields
		createdByLabel.setValue(itemVoucher.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertTimestampToStr(itemVoucher.getCreatedDt(),
				DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertTimestampToStr(itemVoucher.getCreatedDt(),
				DateUtil.GLOBAL_TIME_FORMAT));

		if (itemVoucher.getUpdateDt() == null) {
			lastUpdatedByLabel.setValue("-");
			lastUpdatedDateLabel.setValue("-");
			lastUpdatedTimeLabel.setValue("-");
		} else {
			lastUpdatedByLabel.setValue(itemVoucher.getUpdatedBy());
			lastUpdatedDateLabel.setValue(DateUtil.convertTimestampToStr(itemVoucher.getUpdateDt(),
					DateUtil.GLOBAL_DATE_FORMAT));
			lastUpdatedTimeLabel.setValue(DateUtil.convertTimestampToStr(itemVoucher.getUpdateDt(),
					DateUtil.GLOBAL_TIME_FORMAT));
		}
		
		Map<String, String> reasons = ConfigurableConstants
				.getMasters(ConfigurableConstants.INVENTORY_EDIT_EXPIRY_DATE_REASONS);
		this.populateReasons(reasons);
	}
	
	private void populateReasons(Map<String, String> reasons) {
		for (Entry<String, String> entry : reasons.entrySet()) {
			reasonLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
		if (!reasonLB.getItems().isEmpty())
			reasonLB.setSelectedIndex(0);
	}
	
	private MstbMasterTable getReason(String reasonCode) {
		MstbMasterTable reasonMasterRecord = ConfigurableConstants.getMasterTable(
				ConfigurableConstants.INVENTORY_EDIT_EXPIRY_DATE_REASONS,
				(String) reasonLB.getSelectedItem().getValue());
		return reasonMasterRecord;
	}
	
	public void submit() throws InterruptedException {
		// Validation
		// Reason is mandatory
		if (reasonLB.getItems().isEmpty() || reasonLB.getSelectedItem() == null)
			throw new WrongValueException("Reason is mandatory");
		
		if (expiryDateDB.getValue().compareTo(itemVoucher.getExpiryDate()) == 0)
			throw new WrongValueException(expiryDateDB,
					"No change of expiry date. Please select a new expiry date.");
		List<ImtbItem> itemVouchers = new ArrayList<ImtbItem>();
		itemVouchers.add(itemVoucher);
		try {
			this.businessHelper.getInventoryBusiness().createApprovalRequest(itemVouchers,
					getReason((String) reasonLB.getSelectedItem().getValue()),
					remarksTextBox.getValue(), getUserLoginIdAndDomain(), MassSubmissionWindow.MODE_EDIT_EXPIRY_DATE, 
					null, DateUtil.convertUtilDateToSqlDate(expiryDateDB.getValue()));
			
			Messagebox.show("Submission complete!");

			CommonWindow window =  (CommonWindow) this.getPreviousPage();
			if(window instanceof SearchItemWindow)
				((SearchItemWindow) window) .search();
			else if(window instanceof ViewItemWindow)
				window.back();
				
			this.detach();
			
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox
					.show("Row was updated or deleted by another transaction while trying to create approval request for this voucher: "
							+ itemVoucher.getSerialNo()
							+ ". Please refresh the search result and try again.");
			holfe.printStackTrace();
		} catch (Exception e) {
			Messagebox
					.show("Unexpected error occurred while trying to create approval request for this voucher: "
							+ itemVoucher.getSerialNo());
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
