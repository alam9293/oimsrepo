package com.cdgtaxi.ibs.inventory.ui;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.cdge.exception.ExpectedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class MassApprovalItemReqWindow extends CommonWindow implements AfterCompose {

	public static final String MODE_APPROVE = "APPROVE";
	public static final String MODE_REJECT = "REJECT";

	private List<ImtbItemReq> itemReqs;
	private Textbox remarksTextBox;
	private Listbox resultLB;
	private String mode;
	private Label remarksLabel;
	private Button submitBtn;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MassApprovalItemReqWindow() {
		Map params = Executions.getCurrent().getArg();
		mode = (String) params.get("mode");
		List<Integer> reqNos = (List<Integer>) params.get("reqNos");
		itemReqs = businessHelper.getInventoryBusiness().getItemRequestByReqNos(reqNos);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		if (mode.equals(MODE_APPROVE)){
			this.setTitle("Approve Requests");
			remarksLabel.setValue("Approval Remarks");
			submitBtn.setLabel("Approve");
		}
		else if (mode.equals(MODE_REJECT)){
			this.setTitle("Reject Requests");
			remarksLabel.setValue("Rejection Remarks");
			submitBtn.setLabel("Reject");
		}
			
		for (ImtbItemReq itemReq : itemReqs) {
			ImtbItem itemVoucher = itemReq.getImtbItem();
			AmtbAccount account = itemVoucher.getImtbIssue().getImtbIssueReq().getAmtbAccount();

			while(account.getAmtbAccount() != null){
				account = account.getAmtbAccount();
			}
			
			Listitem item = new Listitem();
			item.appendChild(newListcell(account.getCustNo()));
			item.appendChild(newListcell(account.getAccountName()));
			item.appendChild(newListcell(itemVoucher.getImtbItemType().getTypeName()));
			item.appendChild(newListcell(itemVoucher.getSerialNo(), StringUtil.GLOBAL_STRING_FORMAT));
			if (itemVoucher.getStatus().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE)){
				Vbox vbox = new Vbox();
				Label currentExpiryDateLabel = new Label();
				currentExpiryDateLabel.setValue(DateUtil.convertDateToStr(itemVoucher.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
				Label newExpiryDateLabel = new Label();
				newExpiryDateLabel.setValue("(" + 
						DateUtil.convertDateToStr(itemReq.getNewExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT)+")");
				newExpiryDateLabel.setStyle("color:red");
				vbox.appendChild(currentExpiryDateLabel);
				vbox.appendChild(newExpiryDateLabel);
				Listcell cell = new Listcell();
				cell.appendChild(vbox);
				item.appendChild(cell);
			}else{
				item.appendChild(newListcell(itemVoucher.getExpiryDate()));
			}
			item.appendChild(newListcell(NonConfigurableConstants.INVENTORY_ITEM_STATUS
					.get(itemVoucher.getStatus())));
			item.appendChild(newListcell(itemReq.getRequestor()));
			item.appendChild(newListcell(itemReq.getRequestDt()));
			item.appendChild(newListcell(itemReq.getMstbMasterTableByReason().getMasterValue()));
			item.appendChild(newListcell(itemReq.getRemarks()));
			resultLB.appendChild(item);
		}

		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}

	@Override
	public void refresh() throws InterruptedException {

	}

	public void cancel() throws InterruptedException {
		this.detach();
	}

	public void submit() throws InterruptedException {
		// loop thru each item and try to do the respective action based on mode.
		// the reason to loop item by item because if one item fails due to concurrency update,
		// it will fail for that item rather than the entire collection of items
		// removed the for loop by Yvonne Yap on 25-11-2015, 
		// due to a need to send consolidate email for all approved/rejected request
		//for (ImtbItemReq itemReq : itemReqs) {
		//	ImtbItem itemVoucher = itemReq.getImtbItem();
			try {
				if (mode.equals(MODE_APPROVE)) {
					String reason = this.businessHelper.getInventoryBusiness().approve(itemReqs,
							remarksTextBox.getValue(), getUserLoginIdAndDomain());
					if (reason != null && reason.length() > 0)
						Messagebox.show("Approve failed due to CDGE failure, reason: "
								+ reason);
				} else
					this.businessHelper.getInventoryBusiness().reject(itemReqs,
							remarksTextBox.getValue(), getUserLoginIdAndDomain());
			} 
			catch (ExpectedException ee) {
				Messagebox.show("Approve failed due to CDGE failure, reason: "
						+ ee.getMessage());
				ee.printStackTrace();
			}
			catch (HibernateOptimisticLockingFailureException holfe) {
				Messagebox
						.show("Row was updated or deleted by another transaction while trying to "
								+ mode + " request"
								+ ". Please refresh the search result and try again.");
				holfe.printStackTrace();
			} catch (Exception e) {
				Messagebox.show("Unexpected error occurred while trying to " + mode
						+ " request. ");
				e.printStackTrace();
			}
		//}

		if (mode.equals(MODE_APPROVE)) {
			Messagebox.show("Approval complete!");
		}else{
			Messagebox.show("Rejection complete!");
		}
		
		CommonWindow window = (CommonWindow) this.getParent();
		if (window instanceof SearchItemRequestWindow)
			((SearchItemRequestWindow) window).search();
		else if (window instanceof ViewItemRequestWindow)
			window.back();

		this.detach();
	}
}
