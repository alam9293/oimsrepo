package com.cdgtaxi.ibs.inventory.ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.forms.SearchItemForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class MassSubmissionWindow extends CommonWindow implements AfterCompose {

	public static final String MODE_SUSPENSION = "SUSPENSION";
	public static final String MODE_REACTIVATION = "REACTIVATION";
	public static final String MODE_VOID = "VOID";
	public static final String MODE_REMOVE_REDEMPTION = "REMOVE_REDEMPTION";
	public static final String MODE_CHANGE_OF_REDEMPTION = "CHANGE_OF_REDEMPTION";
	public static final String MODE_EDIT_EXPIRY_DATE = "EDIT_EXPIRY_DATE";

	private String mode;
	private List<ImtbItem> itemVouchers;

	private Listbox reasonLB, resultLB;
	private Textbox remarksTextBox;
	private Decimalbox serialNoDMB;
	private Datebox expiryDateDB;
	private Label itemTypeLabel, accountNoLabel, accountNameLabel;
	private Row row4, row5, row6, row7, row8;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MassSubmissionWindow() {
		Map params = Executions.getCurrent().getArg();
		mode = (String) params.get("mode");
		List<Long> itemNos = (List<Long>) params.get("itemNos");
		itemVouchers = businessHelper.getInventoryBusiness().getItemByItemNos(itemNos);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		if (mode.equals(MODE_SUSPENSION)) {
			this.setTitle("Suspend Taxi Vouchers");
			Map<String, String> reasons = ConfigurableConstants
					.getMasters(ConfigurableConstants.INVENTORY_SUSPENSION_REASONS);
			this.populateReasons(reasons);
		} else if (mode.equals(MODE_REACTIVATION)) {
			this.setTitle("Reactivate Taxi Vouchers");
			Map<String, String> reasons = ConfigurableConstants
					.getMasters(ConfigurableConstants.INVENTORY_REACTIVATION_REASONS);
			this.populateReasons(reasons);
		} else if (mode.equals(MODE_VOID)) {
			this.setTitle("Void Taxi Vouchers");
			Map<String, String> reasons = ConfigurableConstants
					.getMasters(ConfigurableConstants.INVENTORY_VOID_REASONS);
			this.populateReasons(reasons);
		} else if (mode.equals(MODE_REMOVE_REDEMPTION)) {
			this.setTitle("Remove Taxi Vouchers Redemption");
			Map<String, String> reasons = ConfigurableConstants
					.getMasters(ConfigurableConstants.INVENTORY_REMOVE_REDEMPTION_REASONS);
			this.populateReasons(reasons);
		} else if (mode.equals(MODE_CHANGE_OF_REDEMPTION)) {
			this.setTitle("Change of Taxi Vouchers Redemption");
			Map<String, String> reasons = ConfigurableConstants
					.getMasters(ConfigurableConstants.INVENTORY_CHANGE_OF_REDEMPTION_REASONS);
			this.populateReasons(reasons);

			row4.setVisible(true);
			row5.setVisible(true);
			row6.setVisible(true);
			row7.setVisible(true);
		} else if (mode.equals(MODE_EDIT_EXPIRY_DATE)) {
			this.setTitle("Edit Expiry Date");
			Map<String, String> reasons = ConfigurableConstants
					.getMasters(ConfigurableConstants.INVENTORY_EDIT_EXPIRY_DATE_REASONS);
			this.populateReasons(reasons);

			row8.setVisible(true);
		}
		
		for (ImtbItem itemVoucher : itemVouchers) {
			Listitem item = new Listitem();
			item.setValue(itemVoucher);

			AmtbAccount account = itemVoucher.getImtbIssue().getImtbIssueReq().getAmtbAccount();

			while(account.getAmtbAccount() != null){
				account = account.getAmtbAccount();
			}
			
			item.appendChild(newListcell(account.getCustNo()));
			item.appendChild(newListcell(account.getAccountName()));
			item.appendChild(newListcell(itemVoucher.getImtbItemType().getTypeName()));
			item.appendChild(newListcell(itemVoucher.getSerialNo(), StringUtil.GLOBAL_STRING_FORMAT));
			item.appendChild(newListcell(itemVoucher.getExpiryDate()));
			item.appendChild(newListcell(itemVoucher.getRedeemPoint()));
			item.appendChild(newListcell(itemVoucher.getRedeemTime()));
			item.appendChild(newListcell(itemVoucher.getCashierId()));
			item.appendChild(newListcell(NonConfigurableConstants.INVENTORY_ITEM_STATUS
					.get(itemVoucher.getStatus())));
			resultLB.appendChild(item);

			if (mode.equals(MODE_CHANGE_OF_REDEMPTION)) {
				accountNoLabel.setValue(account.getCustNo());
				accountNameLabel.setValue(account.getAccountName());
			}
		}

		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}

	private void populateReasons(Map<String, String> reasons) {
		for (Entry<String, String> entry : reasons.entrySet()) {
			reasonLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
		if (!reasonLB.getItems().isEmpty())
			reasonLB.setSelectedIndex(0);
	}

	@Override
	public void refresh() throws InterruptedException {

	}

	public void cancel() throws InterruptedException {
		this.detach();
	}

	private MstbMasterTable getReason(String reasonCode) {
		String masterType = null;
		if (mode.equals(MODE_CHANGE_OF_REDEMPTION))
			masterType = ConfigurableConstants.INVENTORY_CHANGE_OF_REDEMPTION_REASONS;
		else if (mode.equals(MODE_REACTIVATION))
			masterType = ConfigurableConstants.INVENTORY_REACTIVATION_REASONS;
		else if (mode.equals(MODE_SUSPENSION))
			masterType = ConfigurableConstants.INVENTORY_SUSPENSION_REASONS;
		else if (mode.equals(MODE_VOID))
			masterType = ConfigurableConstants.INVENTORY_VOID_REASONS;
		else if (mode.equals(MODE_REMOVE_REDEMPTION))
			masterType = ConfigurableConstants.INVENTORY_REMOVE_REDEMPTION_REASONS;
		else if (mode.equals(MODE_EDIT_EXPIRY_DATE))
			masterType = ConfigurableConstants.INVENTORY_EDIT_EXPIRY_DATE_REASONS;

		MstbMasterTable reasonMasterRecord = ConfigurableConstants.getMasterTable(masterType,
				(String) reasonLB.getSelectedItem().getValue());
		return reasonMasterRecord;
	}

	public void submit() throws InterruptedException {
		// Validation
		// Reasion is mandatory
		if (reasonLB.getItems().isEmpty() || reasonLB.getSelectedItem() == null)
			throw new WrongValueException("Reason is mandatory");

		if (mode.equals(MODE_CHANGE_OF_REDEMPTION)) {
			Constraint requiredConstraint = new SimpleConstraint(SimpleConstraint.NO_EMPTY);
			requiredConstraint.validate(serialNoDMB, serialNoDMB.getValue());

			SearchItemForm form = new SearchItemForm();
			form.serialNoFrom = serialNoDMB.getValue();
			form.serialNoTo = serialNoDMB.getValue();
			List<ImtbItem> items = this.businessHelper.getInventoryBusiness().searchItem(form);
			if (items.isEmpty())
				throw new WrongValueException(serialNoDMB,
						"No vouchers found with the entered serial no.");

			ImtbItem item = items.get(0);
			ImtbItem itemVoucher = itemVouchers.get(0);
			
			if(item.getSerialNo().compareTo(itemVoucher.getSerialNo()) == 0)
				throw new WrongValueException(serialNoDMB,
				"Cannot change redemption to the same voucher!");
			
			if (!item.getStatus().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED))
				throw new WrongValueException(serialNoDMB,
						"Voucher found but only vouchers with ISSUED status is allowed");
			
		}else if (mode.equals(MODE_EDIT_EXPIRY_DATE)){
			// check if new expiry date is different from voucher expiry date
			for (ImtbItem itemVoucher : itemVouchers) {
				if (mode.equals(MODE_EDIT_EXPIRY_DATE)){
					if (expiryDateDB.getValue().compareTo(itemVoucher.getExpiryDate()) == 0)
						throw new WrongValueException("No change of expiry date for this voucher: "
								+ itemVoucher.getSerialNo()
								+ ". Please select a new expiry date or unselect this voucher for submission.");
				}
			}
		}
		// loop thru each item and try to do the respective action based on mode.
		// the reason to loop item by item because if one item fails due to concurrency update,
		// it will fail for that item rather than the entire collection of items
		// removed the looping by Yvonne Yap on 25-11-2015, 
		// due to a need to consolidate email for all submitted request
		//for (ImtbItem itemVoucher : itemVouchers) {
			try {
				/*if (mode.equals(MODE_EDIT_EXPIRY_DATE)){
					if (expiryDateDB.getValue().compareTo(itemVoucher.getExpiryDate()) != 0){
						// create approval request is new expiry date is different from voucher expiry date
						this.businessHelper.getInventoryBusiness().createApprovalRequest(itemVoucher,
								getReason((String) reasonLB.getSelectedItem().getValue()),
								remarksTextBox.getValue(), getUserLoginIdAndDomain(), mode, 
								itemVoucher.getRedeemPoint(),
								itemVoucher.getBatchNo(),
								itemVoucher.getBatchDate(),
								itemVoucher.getRedeemTime(),
								null, null, DateUtil.convertUtilDateToSqlDate(expiryDateDB.getValue()));
					}else
						Messagebox
						.show("No change of expiry date for this voucher: "
								+ itemVoucher.getSerialNo()
								+ ". System will skip creating request for this voucher.");
				}else if (!mode.equals(MODE_CHANGE_OF_REDEMPTION))
					this.businessHelper.getInventoryBusiness().createApprovalRequest(itemVoucher,
							getReason((String) reasonLB.getSelectedItem().getValue()),
							remarksTextBox.getValue(), getUserLoginIdAndDomain(), mode, 
							itemVoucher.getRedeemPoint(),
							itemVoucher.getBatchNo(),
							itemVoucher.getBatchDate(),
							itemVoucher.getRedeemTime(),
							null, null, null);
				else
					this.businessHelper.getInventoryBusiness().createApprovalRequest(itemVoucher,
							getReason((String) reasonLB.getSelectedItem().getValue()),
							remarksTextBox.getValue(), getUserLoginIdAndDomain(), mode,
							itemVoucher.getRedeemPoint(),
							itemVoucher.getBatchNo(),
							itemVoucher.getBatchDate(),
							itemVoucher.getRedeemTime(),
							itemVoucher.getCashierId(), serialNoDMB.getValue(), null);
				*/
				if (mode.equals(MODE_EDIT_EXPIRY_DATE)){
					this.businessHelper.getInventoryBusiness().createApprovalRequest(itemVouchers,
							getReason((String) reasonLB.getSelectedItem().getValue()),
							remarksTextBox.getValue(), getUserLoginIdAndDomain(), mode, 
							null, DateUtil.convertUtilDateToSqlDate(expiryDateDB.getValue()));
				}else if (!mode.equals(MODE_CHANGE_OF_REDEMPTION)){
					this.businessHelper.getInventoryBusiness().createApprovalRequest(itemVouchers,
							getReason((String) reasonLB.getSelectedItem().getValue()),
							remarksTextBox.getValue(), getUserLoginIdAndDomain(), mode, 
							null, null);
				}else{
					this.businessHelper.getInventoryBusiness().createApprovalRequest(itemVouchers,
							getReason((String) reasonLB.getSelectedItem().getValue()),
							remarksTextBox.getValue(), getUserLoginIdAndDomain(), mode, 
							serialNoDMB.getValue(), null);
				}
			} catch (HibernateOptimisticLockingFailureException holfe) {
				Messagebox
						.show("Row was updated or deleted by another transaction while trying to create approval request "
								+ ". Please refresh the search result and try again.");
				holfe.printStackTrace();
			} catch (Exception e) {
				Messagebox
						.show("Unexpected error occurred while trying to create approval request ");
				e.printStackTrace();
			}
		//}
		
		Messagebox.show("Submission complete!");

		CommonWindow window = (CommonWindow) this.getParent();
		if(window instanceof SearchItemWindow)
			((SearchItemWindow) window) .search();
		else if(window instanceof ViewItemWindow)
			window.back();
			
		this.detach();
	}
	
	public void updateItemTypeLabel(String serialNo){
		if(serialNo == null)
			itemTypeLabel.setValue("");
		else{
			ImtbItem item = this.businessHelper.getInventoryBusiness().getItemBySerialNo(new BigDecimal(serialNo));
			if(item==null)
				itemTypeLabel.setValue("");
			else
				itemTypeLabel.setValue(item.getImtbItemType().getTypeName());
		}
	}
}
