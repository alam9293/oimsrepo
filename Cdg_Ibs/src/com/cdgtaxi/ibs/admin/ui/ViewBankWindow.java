package com.cdgtaxi.ibs.admin.ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewBankWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ViewBankWindow.class);

	private MstbBankMaster bank;
	private Listbox branchList;
	private Label bankNameLbl,bankCodeLbl;

	@Override
	public void refresh() throws InterruptedException {
		bank = businessHelper.getAdminBusiness().getBank(bank.getBankMasterNo());

		branchList.getItems().clear();

		Set<MstbBranchMaster> branches = bank.getMstbBranchMasters();
		Set<MstbBranchMaster> sortedDetails = new TreeSet<MstbBranchMaster>(
				new Comparator<MstbBranchMaster>() {
					public int compare(MstbBranchMaster d1, MstbBranchMaster d2) {
						return d1.getBranchCode().compareTo(d2.getBranchCode());
					}
				});
		sortedDetails.addAll(branches);

		for (MstbBranchMaster branch : sortedDetails) {
			Listitem item = newListitem(branch);
			item.appendChild(newListcell(branch.getBranchCode(), DateUtil.GLOBAL_DATE_FORMAT));
			item.appendChild(newListcell(branch.getBranchName()));

			branchList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public ViewBankWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer bankNo =  (Integer) params.get("bankNo");
		logger.info("Bank No = " + bankNo);
		bank = businessHelper.getAdminBusiness().getBank(bankNo);
	}

	public void onCreate() {
		bankCodeLbl = (Label) getFellow("bankCodeLbl");
		bankNameLbl = (Label) getFellow("bankNameLbl");

		bankCodeLbl.setValue(bank.getBankCode());
		bankNameLbl.setValue(bank.getBankName());

		branchList = (Listbox) getFellow("branchList");
		Set<MstbBranchMaster> branches = bank.getMstbBranchMasters();
		Set<MstbBranchMaster> sortedBranches = new TreeSet<MstbBranchMaster>(
				new Comparator<MstbBranchMaster>() {
					public int compare(MstbBranchMaster d1, MstbBranchMaster d2) {
						return d1.getBranchCode().compareTo(d2.getBranchCode());
					}
				});
		sortedBranches.addAll(branches);

		for (MstbBranchMaster branch : sortedBranches) {
			Listitem item = newListitem(branch);
			item.appendChild(newListcell(branch.getBranchCode()));
			item.appendChild(newListcell(branch.getBranchName()));

			branchList.appendChild(item);
		}
	}

	public void editBranch() throws InterruptedException {
		try {
			Listitem selectedItem = branchList.getSelectedItem();
			MstbBranchMaster branch = (MstbBranchMaster) selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("branchNo", branch.getBranchMasterNo());
			
			if(this.checkUriAccess(Uri.EDIT_BANK_BRANCH))
				forward(Uri.EDIT_BANK_BRANCH, map);
			else if(this.checkUriAccess(Uri.VIEW_BANK_BRANCH))
				forward(Uri.VIEW_BANK_BRANCH, map);
			else{
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
						"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		finally {
			branchList.clearSelection();
		}
	}
}
