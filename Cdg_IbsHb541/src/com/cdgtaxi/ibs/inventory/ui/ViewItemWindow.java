package com.cdgtaxi.ibs.inventory.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewItemWindow extends CommonWindow implements AfterCompose {

	private Button suspensionSubmissionBtn, reactivationSubmissionBtn, voidSubmissionBtn,
			changeRedemptionSubmissionBtn, removeRedemptionBtn, editExpiryDateBtn;

	private ImtbItem itemVoucher;
	private Label accountNoLabel, accountNameLabel, itemTypeLabel, serialNoLabel, expiryDateLabel,
			statusLabel, redemptionPointLabel, redemptionDateLabel, cashierIdLabel, createdByLabel,
			createdDateLabel, createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel,
			lastUpdatedTimeLabel, batchNoLabel, requestorRemarksLabel;
	private Row divisionDepartmentRow;
	private Label divisionLabel, divisionNameLabel, departmentLabel, departmentNameLabel;
	
	@SuppressWarnings({ "rawtypes" })
	public ViewItemWindow() {
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
		expiryDateLabel.setValue(DateUtil.convertDateToStr(itemVoucher.getExpiryDate(), 
				DateUtil.GLOBAL_DATE_FORMAT));
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

		if (!this.checkUriAccess(Uri.SUSPEND_ITEM))
			suspensionSubmissionBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.REACTIVATE_ITEM))
			reactivationSubmissionBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.VOID_ITEM))
			voidSubmissionBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.CHANGE_OF_ITEM_REDEMPTION))
			changeRedemptionSubmissionBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.REMOVE_ITEM_REDEMPTION))
			removeRedemptionBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.EDIT_ITEM_EXPIRY_DATE))
			editExpiryDateBtn.setDisabled(true);
		
		if (itemVoucher.getStatus().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED)) {
			suspensionSubmissionBtn.setVisible(true);
			reactivationSubmissionBtn.setVisible(false);
			voidSubmissionBtn.setVisible(false);
			changeRedemptionSubmissionBtn.setVisible(false);
			removeRedemptionBtn.setVisible(false);
			editExpiryDateBtn.setVisible(true);
		} else if (itemVoucher.getStatus().equals(
				NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED)) {
			suspensionSubmissionBtn.setVisible(false);
			reactivationSubmissionBtn.setVisible(false);
			voidSubmissionBtn.setVisible(true);
			changeRedemptionSubmissionBtn.setVisible(true);
			removeRedemptionBtn.setVisible(true);
			editExpiryDateBtn.setVisible(false);
		} else if (itemVoucher.getStatus().equals(
				NonConfigurableConstants.INVENTORY_ITEM_STATUS_SUSPENDED)) {
			suspensionSubmissionBtn.setVisible(false);
			reactivationSubmissionBtn.setVisible(true);
			voidSubmissionBtn.setVisible(false);
			changeRedemptionSubmissionBtn.setVisible(false);
			removeRedemptionBtn.setVisible(false);
			editExpiryDateBtn.setVisible(false);
		} else if (itemVoucher.getStatus().equals(
				NonConfigurableConstants.INVENTORY_ITEM_STATUS_VOID)) {
			suspensionSubmissionBtn.setVisible(false);
			reactivationSubmissionBtn.setVisible(false);
			voidSubmissionBtn.setVisible(false);
			changeRedemptionSubmissionBtn.setVisible(false);
			removeRedemptionBtn.setVisible(false);
			editExpiryDateBtn.setVisible(false);
		}
		else {
			suspensionSubmissionBtn.setVisible(false);
			reactivationSubmissionBtn.setVisible(false);
			voidSubmissionBtn.setVisible(false);
			changeRedemptionSubmissionBtn.setVisible(false);
			removeRedemptionBtn.setVisible(false);
			editExpiryDateBtn.setVisible(false);
		}
	}

	@Override
	public void refresh() throws InterruptedException {

	}

	public void suspend() throws Exception {
		if (!itemVoucher.getStatus().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED)) {
			Messagebox.show("Suspension is only applicable to vouchers with ISSUED status");
			return;
		}
		this.proceedNext(itemVoucher, MassSubmissionWindow.MODE_SUSPENSION);
	}

	public void reactivate() throws Exception {
		if (!itemVoucher.getStatus().equals(
				NonConfigurableConstants.INVENTORY_ITEM_STATUS_SUSPENDED)) {
			Messagebox.show("Reactivation is only applicable to vouchers with SUSPENDED status");
			return;
		}
		this.proceedNext(itemVoucher, MassSubmissionWindow.MODE_REACTIVATION);
	}

	public void doVoid() throws Exception {
		if (!itemVoucher.getStatus()
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED)) {
			Messagebox.show("Void is only applicable to vouchers with REDEEMED status");
			return;
		}
		this.proceedNext(itemVoucher, MassSubmissionWindow.MODE_VOID);
	}
	
	public void removeRedemption() throws Exception {
		if (!itemVoucher.getStatus()
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED)) {
			Messagebox.show("Remove redemption is only applicable to vouchers with REDEEMED status");
			return;
		}
		this.proceedNext(itemVoucher, MassSubmissionWindow.MODE_REMOVE_REDEMPTION);
	}

	public void changeOfRedemption() throws Exception {
		if (!itemVoucher.getStatus()
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED)) {
			Messagebox
					.show("Change of Redemption is only applicable to vouchers with REDEEMED status");
			return;
		}
		this.proceedNext(itemVoucher, MassSubmissionWindow.MODE_CHANGE_OF_REDEMPTION);
	}
	
	public void editExpiryDate() throws Exception {
		
		if (!itemVoucher.getStatus()
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED)) {
			Messagebox
					.show("Edit expiry date is only applicable to vouchers with ISSUED status");
			return;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemNo", itemVoucher.getItemNo());
		
		this.forward(Uri.EDIT_ITEM_EXPIRY_DATE,map);
	}

	private void proceedNext(ImtbItem item, String mode) throws Exception {

		List<Long> itemNos = new ArrayList<Long>();
		itemNos.add(item.getItemNo());

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("itemNos", itemNos);
		args.put("mode", mode);

		final CommonWindow win = (CommonWindow) Executions.createComponents(
				Uri.MASS_SUBMISSION_ITEM, this, args);
		win.setMaximizable(false);
		win.doModal();
	}
	
	
}
