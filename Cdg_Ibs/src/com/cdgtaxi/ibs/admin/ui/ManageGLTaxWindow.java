package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
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
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ManageGLTaxWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(ManageGLTaxWindow.class);

	private Combobox entityCB, taxTypeCB;
	private Listbox resultList;
	private Button createBtn;

	private List<FmtbTaxCode> codes;

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getEntities();
		entityCB.appendChild(newComboitem(null, "-"));
		entityCB.setSelectedIndex(0);
		for (FmtbEntityMaster entity : entities) {
			entityCB.appendChild(newComboitem(entity.getEntityNo(), entity.getEntityName()));
		}

		taxTypeCB.appendChild(newComboitem(null, "-"));
		taxTypeCB.setSelectedIndex(0);
		Set<Entry<String, String>> entries = ConfigurableConstants.getAllMasters(ConfigurableConstants.TAX_TYPE).entrySet();
		for(Entry<String, String> entry : entries){
			taxTypeCB.appendChild(newComboitem(entry.getKey(), entry.getValue()));
		}
		
		codes = businessHelper.getAdminBusiness().getGLTaxCodes(null, null);
		buildResultList();
		
		if(!this.checkUriAccess(Uri.CREATE_GST)){
			createBtn.setDisabled(true);
		}
	}

	public void search() throws InterruptedException {
		Integer entityNo = (Integer) entityCB.getSelectedItem().getValue();
		String taxType = (String) taxTypeCB.getSelectedItem().getValue();
		
		codes = businessHelper.getAdminBusiness().getGLTaxCodes(entityNo, taxType);
		buildResultList();
	}

	public void edit() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			FmtbTaxCode code = (FmtbTaxCode)selectedItem.getValue();

			//taken effect, not allowed to edit
			/*if(code.getEffectiveDate().compareTo(DateUtil.getCurrentDate()) != 1)
				return;
			*/
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("taxCodeNo", code.getTaxCodeNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(code.getEffectiveDate()));
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_GST))
					forward(Uri.EDIT_GST, map);
				else if(this.checkUriAccess(Uri.VIEW_GST))
					forward(Uri.VIEW_GST, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_GST))
					forward(Uri.VIEW_GST, map);
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
			
			forward(Uri.CREATE_GST, null);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_GST)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			businessHelper.getAdminBusiness().deleteGLTaxCode((FmtbTaxCode) item.getValue());
			MasterSetup.getGSTManager().refresh();
			item.detach();
			Messagebox.show("GST has been successfully deleted", "Delete GST",
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
		search();
	}

	private void buildResultList() {
		resultList.getItems().clear();
		if (resultList.getListfoot() != null) {
			resultList.getListfoot().detach();
		}

		if (codes.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
		} else {
			for (FmtbTaxCode code : codes) {
				Listitem item = new Listitem();
				item.setValue(code);
				
				item.appendChild(newListcell(code.getFmtbEntityMaster().getEntityName()));
				item.appendChild(newListcell(code.getGlTaxCode()));
				item.appendChild(newListcell(code.getGlCode()));
				if (code.getMstbMasterTable() != null)
					item.appendChild(newListcell(code.getMstbMasterTable().getMasterValue()));
				else
					item.appendChild(newEmptyListcell("", "-"));
				item.appendChild(newListcell(code.getCostCentre()));
				item.appendChild(newListcell(code.getTaxRate()));
				item.appendChild(newListcell(code.getEffectiveDate()));
				
				//Not taken effect then will allow delete
				if(code.getEffectiveDate().compareTo(DateUtil.getCurrentDate()) == 1){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageGLTaxWindow.delete(self.getParent().getParent())");
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
	}
}
