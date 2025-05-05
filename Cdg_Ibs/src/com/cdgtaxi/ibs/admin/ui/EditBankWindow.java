package com.cdgtaxi.ibs.admin.ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.exception.DuplicateCodeError;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditBankWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(EditBankWindow.class);

	private MstbBankMaster bank;
	private Textbox bankNameTB;
	private Textbox bankCodeTB;
	private Listbox branchList;
	private Button save, createBtn;

	@Override
	public void refresh() throws InterruptedException {
		boolean flag = true;
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

			if(this.businessHelper.getAdminBusiness().isRecordDeletable(branch)){
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("editBankWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				item.appendChild(imageCell);
			}else{
				item.appendChild(new Listcell());
				flag = false;
			}

			branchList.appendChild(item);
		}
		if(!flag){
			bankCodeTB.setDisabled(true);
			bankNameTB.setDisabled(true);
			save.setVisible(false);
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_BANK_BRANCH)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			MstbBranchMaster branch = (MstbBranchMaster)item.getValue();
			this.businessHelper.getAdminBusiness().delete(branch);
			bank = businessHelper.getAdminBusiness().getBank(bank.getBankMasterNo());
			MasterSetup.getBankManager().refresh();
			item.detach();
			Messagebox.show("The Branch has been successfully deleted", "Delete Branch",
					Messagebox.OK, Messagebox.INFORMATION);
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	public EditBankWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer bankNo =  (Integer) params.get("bankNo");
		logger.info("Bank No = " + bankNo);
		bank = businessHelper.getAdminBusiness().getBank(bankNo);
	}

	public void onCreate() {
		boolean flag = true;
		bankCodeTB = (Textbox) getFellow("bankCodeTB");
		bankNameTB = (Textbox) getFellow("bankNameTB");
		createBtn = (Button) getFellow("createBtn");
		save = (Button) getFellow("save");

		bankCodeTB.setValue(bank.getBankCode());
		bankNameTB.setValue(bank.getBankName());

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

			if(this.businessHelper.getAdminBusiness().isRecordDeletable(branch)){
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("editBankWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				item.appendChild(imageCell);
			}else{
				item.appendChild(new Listcell());
				flag = false;
			}

			branchList.appendChild(item);
		}
		if(!flag){
			bankCodeTB.setDisabled(true);
			bankNameTB.setDisabled(true);
			save.setVisible(false);
		}
		if(!this.checkUriAccess(Uri.CREATE_BANK_BRANCH)){
			createBtn.setDisabled(true);
		}
	}

	@SuppressWarnings("unchecked")
	public void update() throws InterruptedException {
		try {
			MstbBankMaster bankExample = new MstbBankMaster();
			bankExample.setBankName(bankNameTB.getValue());
			List<MstbBankMaster> result = this.businessHelper.getGenericBusiness().getByExample(bankExample);
			if(result.isEmpty()==false)
				if(result.get(0).getBankMasterNo().intValue() != bank.getBankMasterNo().intValue())
					throw new DuplicateNameError();

			bank.setBankCode(bankCodeTB.getValue());
			bank.setBankName(bankNameTB.getValue());
			businessHelper.getAdminBusiness().updateBank(bank, getUserLoginIdAndDomain());
			MasterSetup.getBankManager().refresh();
			Messagebox.show(
					"Bank has been successfully saved.", "Save Bank",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateNameError dne){
			throw new WrongValueException(bankNameTB, dne.getMessage());
		}
		catch (DuplicateCodeError dce){
			throw new WrongValueException(bankCodeTB, dce.getMessage());
		}
		catch (DataValidationError e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
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

	public void addBranch() throws InterruptedException {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("bankNo", bank.getBankMasterNo());
			forward(Uri.CREATE_BANK_BRANCH, map);
		} 
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
}
