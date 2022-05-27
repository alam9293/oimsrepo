package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Button;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings("serial")
public class ManageEntityWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ManageEntityWindow.class);

	private Listitem itemTemplate;
	private Listbox resultList;
	private Button createBtn;

	public void onCreate(CreateEvent ce) throws Exception {
		resultList = (Listbox) getFellow("resultList");
		itemTemplate = (Listitem) resultList.getItems().get(0);
		itemTemplate.detach();
		refresh();
	}

	public void edit() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			FmtbEntityMaster entity = (FmtbEntityMaster)selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("entityNo", entity.getEntityNo());
			if(this.checkUriAccess(Uri.EDIT_ENTITY))
				forward(Uri.EDIT_ENTITY, map);
			else if(this.checkUriAccess(Uri.VIEW_ENTITY))
				forward(Uri.VIEW_ENTITY, map);
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

	public void create() throws InterruptedException {
		try {
			forward(Uri.CREATE_ENTITY, null);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_ENTITY)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this entity?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			FmtbEntityMaster master = (FmtbEntityMaster)item.getValue();
			businessHelper.getGenericBusiness().delete(master);
			MasterSetup.getEntityManager().refresh();
			item.detach();
			Messagebox.show("Entity has been successfully deleted", "Delete Entity",
					Messagebox.OK, Messagebox.INFORMATION);
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
		createBtn = (Button) getFellow("createBtn");
		if(!this.checkUriAccess(Uri.CREATE_ENTITY)){
			createBtn.setDisabled(true);
		}
		
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getEntities();

		resultList.getItems().clear();
		if (entities.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
		} else {
			for (FmtbEntityMaster entity : entities) {
				Listitem item = (Listitem) itemTemplate.clone();
				item.setValue(entity);
				List<Listcell> cells = item.getChildren();
				cells.get(0).setLabel(entity.getEntityName());
				cells.get(1).setLabel(entity.getEntityCode());
				cells.get(2).setLabel(entity.getEntityRcbNo());
				cells.get(3).setLabel(entity.getEntityGstNo());
				cells.get(4).setLabel(entity.getCurrencyCode());
				
				if(this.businessHelper.getAdminBusiness().isRecordDeletable(entity)){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageEntityWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					cells.get(5).appendChild(deleteImage);
				}else{
					cells.get(5).setLabel("");
				}			
				cells.get(5).setLabel("");

				resultList.appendChild(item);
			}
		}
	}
}
