package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings("serial")
public class ManageBankWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ManageBankWindow.class);

	private Listitem itemTemplate;
	private Listbox resultList;
	private Button createBtn;

	public void onCreate(CreateEvent ce) throws Exception {
		resultList = (Listbox) getFellow("resultList");
		createBtn = (Button) getFellow("createBtn");
		itemTemplate = (Listitem) resultList.getItems().get(0);
		itemTemplate.detach();
		refresh();
		if(!this.checkUriAccess(Uri.CREATE_BANK)){
			createBtn.setDisabled(true);
		}
	}

	public void editPlan() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			MstbBankMaster bank = (MstbBankMaster)selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("bankNo", bank.getBankMasterNo());
			
			if(this.checkUriAccess(Uri.EDIT_BANK))
				forward(Uri.EDIT_BANK, map);
			else if(this.checkUriAccess(Uri.VIEW_BANK))
				forward(Uri.VIEW_BANK, map);
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
			resultList.clearSelection();
		}
	}

	public void createPlan() throws InterruptedException {
		try {
			forward(Uri.CREATE_BANK, null);
		} 
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_BANK)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this bank?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		//		List<MstbVolDiscMaster> plans = new ArrayList<MstbVolDiscMaster>();
		//		plans.add((MstbVolDiscMaster) item.getValue());
		try {
			businessHelper.getAdminBusiness().deleteBank((MstbBankMaster) item.getValue());
			MasterSetup.getBankManager().refresh();
			item.detach();
			Messagebox.show("Bank has been successfully deleted", "Delete Bank",
					Messagebox.OK, Messagebox.INFORMATION);
			//		} catch (RewardPlanInEffectException e) {
			//			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} 
		catch(WrongValueException wve){
			throw wve;
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

	@SuppressWarnings("unchecked")
	@Override
	public void refresh() throws InterruptedException {
		List<MstbBankMaster> banks = businessHelper.getAdminBusiness().getBanks();

		resultList.getItems().clear();

		if (banks.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
		} else {
			for (MstbBankMaster bank : banks) {
				Listitem item = (Listitem) itemTemplate.clone();
				item.setValue(bank);
				List<Listcell> cells = item.getChildren();
				cells.get(0).setLabel(bank.getBankCode());
				cells.get(1).setLabel(bank.getBankName());

				if(this.businessHelper.getAdminBusiness().isRecordDeletable(bank)){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageBankWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					cells.get(2).appendChild(deleteImage);
				}else{
					cells.get(2).setLabel("");
				}
				resultList.appendChild(item);
			}
		}
	}
}
