package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.exception.DuplicateCodeError;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;

@SuppressWarnings({"serial", "unchecked"})
public class CreateBankWindow extends CommonWindow implements AfterCompose{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateBankWindow.class);

	private Textbox bankCodeTB;
	private Textbox bankNameTB;
	private Listbox branchList;
	private Map<String, String> branchMap = new HashMap<String, String>(); //<Code, Name>

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		bankCodeTB.focus();
	}

	public void refreshBranchList(){
		branchList.getItems().clear();
		
		Set<Entry<String, String>> entries = branchMap.entrySet();
		for(Entry<String, String> entry : entries){
			Listitem item = new Listitem();
			item.setValue(entry.getKey());
			
			item.appendChild(newListcell(entry.getKey()));
			item.appendChild(newListcell(entry.getValue()));
			
			Image deleteImage = new Image("/images/delete.png");
			deleteImage.setStyle("cursor:pointer");
			ZScript showInfo = ZScript.parseContent("createBankWindow.delete(self.getParent().getParent())");
			showInfo.setLanguage("java");
			EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			deleteImage.addEventHandler("onClick", event);
			Listcell imageCell = new Listcell();
			imageCell.appendChild(deleteImage);
			item.appendChild(imageCell);
			
			branchList.appendChild(item);
		}
	}
	
	public void create() throws InterruptedException {
		try {
			//Set the name first and use as an example to find duplicate name
			MstbBankMaster bank = new MstbBankMaster();
			bank.setBankName(bankNameTB.getValue());
			
			//Check for duplicate name
			List<MstbBankMaster> result = this.businessHelper.getGenericBusiness().getByExample(bank);
			if(result.isEmpty()==false)
					throw new DuplicateNameError();
			
			//Set remaining values that are not checking by the above
			bank.setBankCode(bankCodeTB.getValue());
			
			//Make sure at least one branch is created
			if(branchMap.isEmpty()){
				Messagebox.show("A Bank must have at least a branch", "Create Bank",
						Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			businessHelper.getAdminBusiness().createBank(bank, branchMap, getUserLoginIdAndDomain());
			MasterSetup.getBankManager().refresh();
			
			Messagebox.show("Bank has been successfully created", "Create Bank",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
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
		catch (DataValidationError ex) {
			Messagebox.show(ex.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		branchList.clearSelection();
		this.refreshBranchList();
	}
	
	public void delete(Listitem item) throws InterruptedException {
		branchMap.remove(item.getValue());
		item.detach();
	}
	
	public int saveBranch(String branchCode, String branchName){
		if(branchMap.get(branchCode) != null)
			return 1;
		
		if(branchMap.containsValue(branchName))
			return 2;
		
		branchMap.put(branchCode, branchName);
		return 0;
	}
	
	public void addBranch() throws InterruptedException{
		Map params = new HashMap();
		params.put("bankName", bankNameTB.getValue());
		forward(Uri.CREATE_NEW_BANK_BRANCH, params);
	}
	
	public void editBranch() throws InterruptedException{
		String branchCode = (String)branchList.getSelectedItem().getValue();
		
		Map params = new HashMap();
		params.put("bankName", bankNameTB.getValue());
		params.put("branchCode", branchCode);
		params.put("branchName", branchMap.get(branchCode));
		forward(Uri.EDIT_NEW_BANK_BRANCH, params);
	}
	
	public int updateBranch(String previousBranchCode, String branchCode, String branchName){
		if(!previousBranchCode.equals(branchCode)){
			if(branchMap.get(branchCode) != null)
				return 1;
			else{
				if(!branchMap.get(previousBranchCode).equals(branchName))
					if(branchMap.containsValue(branchName))
						return 2;
					
				branchMap.remove(previousBranchCode);
				branchMap.put(branchCode, branchName);
			}
		}
		else{
			if(!branchMap.get(previousBranchCode).equals(branchName))
				if(branchMap.containsValue(branchName))
					return 2;
			
			branchMap.put(branchCode, branchName);
		}
		
		return 0;
	}
}
