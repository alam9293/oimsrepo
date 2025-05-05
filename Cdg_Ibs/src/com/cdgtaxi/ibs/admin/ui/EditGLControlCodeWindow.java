package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeDetail;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class EditGLControlCodeWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(EditGLControlCodeWindow.class);

	private FmtbArContCodeMaster code;
	private Combobox entityCB;
	private Textbox codeTB;
	private Listbox detailList;
	
	private Button save, newDetailBtn;

	@Override
	public void refresh() throws InterruptedException {
		boolean flag = true;
		code = businessHelper.getAdminBusiness().getGLControlCode(code.getArControlCodeNo());

		detailList.getItems().clear();

		Set<FmtbArContCodeDetail> details = code.getFmtbArContCodeDetails();
		for (FmtbArContCodeDetail detail : details) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getCostCentre()));
			item.appendChild(newListcell(detail.getDescription()));
			item.appendChild(newListcell(detail.getGlAccount(), StringUtil.GLOBAL_STRING_FORMAT));
			String taxType = "";
			if (detail.getMstbMasterTable() != null) {
				taxType = detail.getMstbMasterTable().getMasterValue();
			}
			item.appendChild(newListcell(taxType));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			
			//Not taken effect then will allow delete
			if(detail.getEffectiveDt().compareTo(DateUtil.getCurrentTimestamp()) == 1){
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("editGLControlCodeWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				item.appendChild(imageCell);
			}
			else{
				item.appendChild(new Listcell());
				flag = false;
			}
			
			detailList.appendChild(item);
		}
		if(!flag){
			codeTB.setDisabled(true);
			entityCB.setDisabled(true);
			save.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	public EditGLControlCodeWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer codeNo =  (Integer) params.get("codeNo");
		logger.info("Plan No = " + codeNo);
		code = businessHelper.getAdminBusiness().getGLControlCode(codeNo);
	}

	public void onCreate() {
		boolean flag = true;
		codeTB = (Textbox) getFellow("codeTB");
		entityCB = (Combobox) getFellow("entityCB");
		save = (Button) getFellow("save");
		newDetailBtn = (Button) getFellow("newDetailBtn");
		
		codeTB.setValue(code.getArControlCode());

		if(this.businessHelper.getAdminBusiness().isArContCodeBeenUsed(code.getArControlCodeNo()))
			entityCB.setDisabled(true);
		
		// populate Entity list
		List<FmtbEntityMaster> entities = Collections.emptyList();
		if(entityCB.isDisabled()) entities = businessHelper.getAdminBusiness().getEntities();
		else entities = businessHelper.getAdminBusiness().getActiveEntities();
		
		for (FmtbEntityMaster entity : entities) {
			Comboitem item = newComboitem(entity, entity.getEntityName());
			entityCB.appendChild(item);
			if (code.getFmtbEntityMaster().getEntityNo().equals(entity.getEntityNo())) {
				entityCB.setSelectedItem(item);
			}
		}

		detailList = (Listbox) getFellow("detailList");
		Set<FmtbArContCodeDetail> details = code.getFmtbArContCodeDetails();
		for (FmtbArContCodeDetail detail : details) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getCostCentre()));
			item.appendChild(newListcell(detail.getDescription()));
			item.appendChild(newListcell(detail.getGlAccount(), StringUtil.GLOBAL_STRING_FORMAT));
			String taxType = "";
			if (detail.getMstbMasterTable() != null) {
				taxType = detail.getMstbMasterTable().getMasterValue();
			}
			item.appendChild(newListcell(taxType));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			
			//Not taken effect then will allow delete
			if(detail.getEffectiveDt().compareTo(DateUtil.getCurrentTimestamp()) == 1){
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("editGLControlCodeWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				item.appendChild(imageCell);
			}
			else{
				item.appendChild(new Listcell());
				flag = false;
			}
			
			detailList.appendChild(item);
		}
		if(!flag){
			codeTB.setDisabled(true);
			entityCB.setDisabled(true);
			save.setVisible(false);
		}
		
		if(entityCB.getSelectedItem()!=null){
			FmtbEntityMaster entity = (FmtbEntityMaster)entityCB.getSelectedItem().getValue();
			if(MasterSetup.getEntityManager().isEntityActive(entity)==false)
				newDetailBtn.setVisible(false);
		}

		if(!this.checkUriAccess(Uri.CREATE_GL_CONTROL_CODE_DETAIL)){
			newDetailBtn.setDisabled(true);
		}
	}

	public void update() throws InterruptedException {
		entityCB.getValue();

		code.setArControlCode(codeTB.getValue());
		if(!entityCB.isDisabled())
			code.setFmtbEntityMaster((FmtbEntityMaster) entityCB.getSelectedItem().getValue());

		try {
			businessHelper.getAdminBusiness().updateGLControlCode(code, getUserLoginIdAndDomain());
			Messagebox.show(
					"Control Code has been successfully saved.", "Save Control Code",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		} 
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DataValidationError ex) {
			Messagebox.show(ex.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
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

	public void editDetail() throws InterruptedException {
		try {
			Listitem selectedItem = detailList.getSelectedItem();
			FmtbArContCodeDetail detail = (FmtbArContCodeDetail) selectedItem.getValue();
			
			//Not taken effect then will allow delete
			/*if(detail.getEffectiveDt().compareTo(DateUtil.getCurrentTimestamp()) != 1)
				return;
			*/
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("codeDetailNo", detail.getArContCodeDetailNo());
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(detail.getEffectiveDt());
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_GL_CONTROL_CODE_DETAIL))
					forward(Uri.EDIT_GL_CONTROL_CODE_DETAIL, map);
				else if(this.checkUriAccess(Uri.VIEW_GL_CONTROL_CODE_DETAIL))
					forward(Uri.VIEW_GL_CONTROL_CODE_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_GL_CONTROL_CODE_DETAIL))
					forward(Uri.VIEW_GL_CONTROL_CODE_DETAIL, map);
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
			detailList.clearSelection();
		}
	}

	public void addDetail() throws InterruptedException {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("codeNo", code.getArControlCodeNo());
			forward(Uri.CREATE_GL_CONTROL_CODE_DETAIL, map);
		} 
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void delete(Listitem item) throws InterruptedException{
		try{
			if(!this.checkUriAccess(Uri.DELETE_GL_CONTROL_CODE_DETAIL)){
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
						"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
					Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
				return;
			}
			
			FmtbArContCodeDetail detail = (FmtbArContCodeDetail) item.getValue();
			this.businessHelper.getGenericBusiness().delete(detail);
			code = businessHelper.getAdminBusiness().getGLControlCode(code.getArControlCodeNo());
			
			item.detach();
			
			Messagebox.show("Delete has been successfully deleted", "Delete Detail",
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
}
