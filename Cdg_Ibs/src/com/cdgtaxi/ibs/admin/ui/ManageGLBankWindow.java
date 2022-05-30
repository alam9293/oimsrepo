package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.forms.SearchGLBankForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class ManageGLBankWindow extends CommonWindow implements AfterCompose {
	private static final Logger logger = Logger.getLogger(ManageGLBankWindow.class);

	private Combobox entityCB;
	private Listbox resultList;
	private CapsTextbox bankCodeTB, branchCodeTB;
	private Datebox effDateFromDatebox, effDateToDatebox;
	private Button createBtn;

	private List<Object[]> codes;

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getEntities();
		entityCB.appendChild(newComboitem(null, "-"));
		entityCB.setSelectedIndex(0);
		for (FmtbEntityMaster entity : entities) {
			entityCB.appendChild(newComboitem(entity.getEntityNo(), entity.getEntityName()));
		}
		
		if(!this.checkUriAccess(Uri.CREATE_GL_BANK)){
			createBtn.setDisabled(true);
		}
	}
	
	public void search() throws InterruptedException {
		this.displayProcessing();
		if(effDateFromDatebox.getValue()!=null && effDateToDatebox.getValue()!=null)
			if(effDateFromDatebox.getValue().compareTo(effDateToDatebox.getValue()) == 1)
				throw new WrongValueException(effDateFromDatebox, "Effective Date From cannot be later than Effective Date To.");
		SearchGLBankForm form = this.buildSearchForm();
		if(!form.isAtLeastOneCriteriaSelected){
			Messagebox.show("Please provide at least one search criteria", 
					"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		codes = businessHelper.getAdminBusiness().getGLBankCodes(form);
		buildResultList();
	}
	
	private SearchGLBankForm buildSearchForm(){
		SearchGLBankForm form = new SearchGLBankForm();
		if(entityCB.getSelectedItem().getValue()!=null)
			form.entityNo = (Integer)entityCB.getSelectedItem().getValue();
		if(form.entityNo!=null) form.isAtLeastOneCriteriaSelected = true;
		if(bankCodeTB.getValue()!=null)
			form.bankCode = (String)bankCodeTB.getValue();
		if(form.bankCode!=null) form.isAtLeastOneCriteriaSelected = true;
		if(branchCodeTB.getValue()!=null)
			form.branchCode = (String)branchCodeTB.getValue();
		if(form.branchCode!=null) form.isAtLeastOneCriteriaSelected = true;
		
		form.effDateFromDate = DateUtil.convertUtilDateToSqlDate(effDateFromDatebox.getValue());
		if(form.effDateFromDate!=null) form.isAtLeastOneCriteriaSelected = true;
		form.effDateToDate = DateUtil.convertUtilDateToSqlDate(effDateToDatebox.getValue());
		if(form.effDateToDate!=null) form.isAtLeastOneCriteriaSelected = true;
		if(effDateFromDatebox.getValue()!=null && effDateToDatebox.getValue()==null){
			effDateToDatebox.setValue(effDateFromDatebox.getValue());
			form.effDateToDate = DateUtil.convertUtilDateToSqlDate(effDateFromDatebox.getValue());
		}else if(effDateFromDatebox.getValue()==null && effDateToDatebox.getValue()!=null){
			effDateFromDatebox.setValue(effDateToDatebox.getValue());
			form.effDateFromDate = DateUtil.convertUtilDateToSqlDate(effDateToDatebox.getValue());
		}	
		return form;
	}

	public void editPlan() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			FmtbBankCode code = (FmtbBankCode)selectedItem.getValue();

			//Not taken effect then will allow delete
			/*if(code.getEffectiveDate().compareTo(DateUtil.getCurrentDate()) <= 0 )
				return;
			*/
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("bankCodeNo", code.getBankCodeNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(code.getEffectiveDate()));
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_GL_BANK))
					forward(Uri.EDIT_GL_BANK, map);
				else if(this.checkUriAccess(Uri.VIEW_GL_BANK))
					forward(Uri.VIEW_GL_BANK, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_GL_BANK))
					forward(Uri.VIEW_GL_BANK, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
		} 
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
		finally {
			resultList.clearSelection();
		}
	}

	public void createPlan() throws InterruptedException {
		try {
			forward(Uri.CREATE_GL_BANK, null);
		} 
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_GL_BANK)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			businessHelper.getAdminBusiness().deleteGLBankCode((FmtbBankCode) item.getValue());
			item.detach();
			Messagebox.show("Code has been successfully deleted", "Delete GL Bank Code",
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

	@Override
	public void refresh() throws InterruptedException {
		this.search();
	}

	private void buildResultList()throws SuspendNotAllowedException, InterruptedException{
		resultList.getItems().clear();
		if (resultList.getListfoot() != null) {
			resultList.getListfoot().detach();
		}
		try{
		if (codes.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(12));
		} else {
			if(codes.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

			for(Object[] objectArray : codes){
				Listitem item = new Listitem();
				FmtbBankCode code = (FmtbBankCode)objectArray[0];
				FmtbEntityMaster entity = (FmtbEntityMaster)objectArray[1];
				MstbMasterTable master = (MstbMasterTable)objectArray[2];
				item.setValue(code);
				
				item.appendChild(newListcell(entity.getEntityName()));
				item.appendChild(newListcell(code.getBankCode()));
				item.appendChild(newListcell(code.getBankName()));
				item.appendChild(newListcell(code.getBranchCode()));
				item.appendChild(newListcell(code.getBranchName()));
				item.appendChild(newListcell(code.getGlCode()));
				item.appendChild(newListcell(master.getMasterValue()));
				item.appendChild(newListcell(code.getCostCentre()));
				item.appendChild(newListcell(code.getIsDefault()));
				item.appendChild(newListcell(code.getIsDefaultGiroBank()));
				item.appendChild(newListcell(code.getEffectiveDate()));
				item.appendChild(newListcell(code.getBankAcctNo()));
				
				//Not taken effect then will allow delete
				if(code.getEffectiveDate().compareTo(DateUtil.getCurrentDate()) == 1){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageGLBankWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					Listcell imageCell = new Listcell();
					imageCell.appendChild(deleteImage);
					item.appendChild(imageCell);
				}
				else
					item.appendChild(new Listcell());
				
				resultList.appendChild(item);
			}

		}
		}catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void reset(){
		entityCB.setSelectedIndex(0);
		effDateFromDatebox.setText("");
		effDateToDatebox.setText("");
		bankCodeTB.setText("");
		branchCodeTB.setText("");
		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(12));
	}
}
