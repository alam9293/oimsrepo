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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings("serial")
public class ManageGLControlCodeWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ManageGLControlCodeWindow.class);

	private Listitem itemTemplate;
	private Combobox entityCB;
	private Listbox resultList;
	private Button createBtn;

	private List<FmtbArContCodeMaster> codes;

	public void onCreate(CreateEvent ce) throws Exception {
		entityCB = (Combobox) getFellow("entityCB");
		resultList = (Listbox) getFellow("resultList");
		createBtn = (Button) getFellow("createBtn");
		itemTemplate = (Listitem) resultList.getItems().get(0);
		itemTemplate.detach();

		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getEntities();
		entityCB.appendChild(newComboitem(null, "-"));
		entityCB.setSelectedIndex(0);
		for (FmtbEntityMaster entity : entities) {
			entityCB.appendChild(newComboitem(entity.getEntityNo(), entity.getEntityName()));
		}

		refresh();
		
		if(!this.checkUriAccess(Uri.CREATE_GL_CONTROL_CODE)){
			createBtn.setDisabled(true);
		}
	}

	public void search() throws InterruptedException {
		Integer entityNo = (Integer) entityCB.getSelectedItem().getValue();
		codes = businessHelper.getAdminBusiness().getGLControlCodes(entityNo);
		buildResultList();
	}

	public void editPlan() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			FmtbArContCodeMaster code = (FmtbArContCodeMaster)selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("codeNo", code.getArControlCodeNo());
			
			if(this.checkUriAccess(Uri.EDIT_GL_CONTROL_CODE))
				forward(Uri.EDIT_GL_CONTROL_CODE, map);
			else if(this.checkUriAccess(Uri.VIEW_GL_CONTROL_CODE))
				forward(Uri.VIEW_GL_CONTROL_CODE, map);
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
			forward(Uri.CREATE_GL_CONTROL_CODE, null);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_GL_CONTROL_CODE)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this code?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			businessHelper.getAdminBusiness().deleteGLControlCode((FmtbArContCodeMaster) item.getValue());
			item.detach();
			Messagebox.show("Code has been successfully deleted", "Delete GL Control Code",
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

	@Override
	public void refresh() throws InterruptedException {
		codes = businessHelper.getAdminBusiness().getGLControlCodes(null);
		buildResultList();
	}

	@SuppressWarnings("unchecked")
	private void buildResultList() {
		resultList.getItems().clear();
		if (resultList.getListfoot() != null) {
			resultList.getListfoot().detach();
		}

		if (codes.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(3));
		} else {
			for (FmtbArContCodeMaster code : codes) {
				Listitem item = (Listitem) itemTemplate.clone();
				item.setValue(code);
				List<Listcell> cells = item.getChildren();
				cells.get(0).setLabel(code.getFmtbEntityMaster().getEntityName());
				cells.get(1).setLabel(code.getArControlCode());
				
				if(this.businessHelper.getAdminBusiness().isRecordDeletable(code)){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageGLControlCodeWindow.delete(self.getParent().getParent())");
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
