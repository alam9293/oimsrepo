package com.cdgtaxi.ibs.inventory.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewItemRequestWindow extends CommonWindow implements AfterCompose {

	private ImtbItemReq itemReq;
	
	private Row divisionDepartmentRow;
	private Label divisionLabel, divisionNameLabel, departmentLabel, departmentNameLabel;
	private Row divisionDepartmentRow2;
	private Label divisionLabel2, divisionNameLabel2, departmentLabel2, departmentNameLabel2;
	
	private Label accountNoLabel, accountNameLabel, itemTypeLabel, serialNoLabel, expiryDateLabel,
			statusLabel, redemptionPointLabel, redemptionDateLabel, cashierIdLabel, createdByLabel,
			createdDateLabel, createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel,
			lastUpdatedTimeLabel, remarksLabel, reasonLabel, batchNoLabel, requestorRemarksLabel;
	// These are the informations displaying the changing of redemption of new voucher
	private Div newVoucherDiv;
	private Label accountNoLabel2, accountNameLabel2, itemTypeLabel2, serialNoLabel2, expiryDateLabel2,
	statusLabel2, redemptionPointLabel2, redemptionDateLabel2, cashierIdLabel2;

	// These are the informations displaying the changing of expiry date
	private Row newExpiryDateRow6;
	private Label newExpiryDateLabel, newExpiryDateValueLabel;
	
	@SuppressWarnings({ "rawtypes" })
	public ViewItemRequestWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer reqNo = (Integer) params.get("reqNo");
		itemReq = businessHelper.getInventoryBusiness().getItemRequestByReqNo(reqNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		ImtbItem itemVoucher = itemReq.getImtbItem();
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
		expiryDateLabel.setValue(DateUtil.convertDateToStr(itemVoucher.getImtbIssue()
				.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
		statusLabel.setValue(NonConfigurableConstants.INVENTORY_ITEM_STATUS.get(itemVoucher
				.getStatus()));
		redemptionPointLabel.setValue(itemVoucher.getRedeemPoint());
		redemptionDateLabel.setValue(DateUtil.convertDateToStr(itemVoucher.getRedeemTime(),
				DateUtil.LAST_UPDATED_DATE_FORMAT));
		cashierIdLabel.setValue(itemVoucher.getCashierId());
		reasonLabel.setValue(itemReq.getMstbMasterTableByReason().getMasterValue());
		remarksLabel.setValue(itemReq.getRemarks());
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
		
		if(itemVoucher.getStatus().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION)){
			newVoucherDiv.setVisible(true);
			ImtbItem newVoucher = this.businessHelper.getInventoryBusiness().getItemBySerialNo(itemReq.getSerialNo());
			
			AmtbAccount account2 = newVoucher.getImtbIssue().getImtbIssueReq().getAmtbAccount();

			if(account2.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
				divisionDepartmentRow2.setVisible(true);
				departmentLabel2.setVisible(true);
				departmentNameLabel2.setVisible(true);
				departmentNameLabel2.setValue(account2.getAccountName() + " (" + account2.getCode() + ")");
				divisionLabel2.setVisible(true);
				divisionNameLabel2.setVisible(true);
				divisionNameLabel2.setValue(account2.getAmtbAccount().getAccountName() + " (" + account2.getAmtbAccount().getCode() + ")");
			}
			else if((account2.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))){
				divisionDepartmentRow2.setVisible(true);
				divisionLabel2.setVisible(true);
				divisionNameLabel2.setVisible(true);
				divisionNameLabel2.setValue(account2.getAccountName() + " (" + account2.getCode() + ")");
			}
			
			while(account2.getAmtbAccount() != null){
				account2 = account2.getAmtbAccount();
			}
			
			accountNoLabel2.setValue(account2.getCustNo());
			accountNameLabel2.setValue(account2.getAccountName());
			itemTypeLabel2.setValue(newVoucher.getImtbItemType().getTypeName());
			serialNoLabel2.setValue(newVoucher.getSerialNo().toString());
			expiryDateLabel2.setValue(DateUtil.convertDateToStr(newVoucher.getImtbIssue()
					.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
			statusLabel2.setValue(NonConfigurableConstants.INVENTORY_ITEM_STATUS.get(newVoucher
					.getStatus()));
			redemptionPointLabel2.setValue(itemReq.getRedeemPoint());
			redemptionDateLabel2.setValue(DateUtil.convertDateToStr(itemReq.getRedeemTime(),
					DateUtil.LAST_UPDATED_DATE_FORMAT));
			cashierIdLabel2.setValue(itemReq.getCashierId());
		} else if(itemVoucher.getStatus().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE)){
			newExpiryDateRow6.setVisible(true);
			newExpiryDateLabel.setVisible(true);
			newExpiryDateValueLabel.setValue(DateUtil.convertDateToStr(itemReq.getNewExpiryDate(),DateUtil.GLOBAL_DATE_FORMAT));
		}
	}

	@Override
	public void refresh() throws InterruptedException {

	}

	public void approve() throws Exception {
		List<Integer> reqNos = new ArrayList<Integer>();
		reqNos.add(itemReq.getReqNo());
		this.proceedNext(reqNos, MassApprovalItemReqWindow.MODE_APPROVE);
	}

	public void reject() throws Exception {
		List<Integer> reqNos = new ArrayList<Integer>();
		reqNos.add(itemReq.getReqNo());
		this.proceedNext(reqNos, MassApprovalItemReqWindow.MODE_REJECT);
	}

	private void proceedNext(List<Integer> reqNos, String mode) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("reqNos", reqNos);
		args.put("mode", mode);

		final CommonWindow win = (CommonWindow) Executions.createComponents(
				Uri.MASS_APPROVAL_ITEM_REQ, this, args);
		win.setMaximizable(false);
		win.doModal();
	}
}
