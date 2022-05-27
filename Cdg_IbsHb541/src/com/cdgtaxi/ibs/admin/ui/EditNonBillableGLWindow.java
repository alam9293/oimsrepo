package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableDetail;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class EditNonBillableGLWindow extends CommonWindow implements AfterCompose {
	
	private FmtbNonBillableMaster master;
	private Listbox detailList, serviceProviderListBox, cardTypeListBox;
	private Label createdByLabel,createdDateLabel,createdTimeLabel,lastUpdatedByLabel,lastUpdatedDateLabel,lastUpdatedTimeLabel;
	private Integer masterNo;
	private Button save, createBtn;
	private static final Logger logger = Logger.getLogger(EditNonBillableGLWindow.class);
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

		Map map = Executions.getCurrent().getArg();
		masterNo = (Integer)map.get("masterNo");
		master = this.businessHelper.getAdminBusiness().getNonBillableMaster(masterNo);

		try{
			List<Entry<String, String>> splist = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.SERVICE_PROVIDER).entrySet());
			for (Map.Entry<String, String> me : splist) {
				Listitem item = new Listitem();
				item.setValue(me.getKey());
				item.setLabel(me.getValue());

				MstbMasterTable mstbSpMaster = ConfigurableConstants.getMasterTable(ConfigurableConstants.SERVICE_PROVIDER, me.getKey());

				if(master.getMstbMasterTableByServiceProvider()!=null)
					if(master.getMstbMasterTableByServiceProvider().getMasterNo().equals(mstbSpMaster.getMasterNo()))
						item.setSelected(true);

				serviceProviderListBox.appendChild(item);
			}
			if(serviceProviderListBox.getSelectedItem()==null) serviceProviderListBox.setSelectedIndex(0);

			List<Entry<String, String>> cplist = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.ACQUIRER_PYMT_TYPE).entrySet());
			for (Map.Entry<String, String> cp : cplist) {
				Listitem item = new Listitem();
				item.setValue(cp.getKey());
				item.setLabel(cp.getValue());

				if(master.getMstbMasterTableByPymtTypeMasterNo()!=null)
					if(master.getMstbMasterTableByPymtTypeMasterNo().getMasterCode().equals(cp.getKey()))
						item.setSelected(true);
				cardTypeListBox.appendChild(item);
			}
			if(cardTypeListBox.getSelectedItem()==null) cardTypeListBox.setSelectedIndex(0);

			this.displayDetails(false);
			/*
			if(master.getCreatedBy()!=null) createdByLabel.setValue(master.getCreatedBy());
			else createdByLabel.setValue("-");
			if(master.getCreatedDt()!=null) createdDateLabel.setValue(DateUtil.convertDateToStr(master.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
			else createdDateLabel.setValue("-");
			if(master.getCreatedDt()!=null) createdTimeLabel.setValue(DateUtil.convertDateToStr(master.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
			else createdTimeLabel.setValue("-");
			
			if(master.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(master.getUpdatedBy());
			else lastUpdatedByLabel.setValue("-");
			if(master.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(master.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
			else lastUpdatedDateLabel.setValue("-");
			if(master.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(master.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
			else lastUpdatedTimeLabel.setValue("-");
			*/
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!this.checkUriAccess(Uri.CREATE_NONBILLABLE_GL_DETAIL)){
			createBtn.setDisabled(true);
		}
		if(!this.checkUriAccess(Uri.EDIT_NONBILLABLE_GL)){
			save.setDisabled(true);
			cardTypeListBox.setDisabled(true);
			serviceProviderListBox.setDisabled(true);
		}
	}
	
	private void displayDetails(boolean refresh) throws InterruptedException{
		if(refresh)
			master = this.businessHelper.getAdminBusiness().getNonBillableMaster(masterNo);
		
		if(master.getFmtbNonBillableDetails().size()>0){
			boolean flag = false;
			if(master.getFmtbNonBillableDetails().size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

			for(FmtbNonBillableDetail detail : master.getFmtbNonBillableDetails()){
				Listitem item = new Listitem();

				item.setValue(detail);
				item.appendChild(newListcell(detail.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName()));
				item.appendChild(newListcell(detail.getFmtbArContCodeMaster().getArControlCode()));
				item.appendChild(newListcell(detail.getFareAmountTxnCode()));
				item.appendChild(newListcell(detail.getAdminFeeTxnCode()));
				item.appendChild(newListcell(detail.getPremiumAmountTxnCode()));
				item.appendChild(newListcell(detail.getEffectiveDate()));
				
				if(DateUtil.getCurrentDate().compareTo(detail.getEffectiveDate()) <=0){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("editNonBillableGLWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					Listcell imageCell = new Listcell();
					imageCell.appendChild(deleteImage);
					item.appendChild(imageCell);
					flag = true;
				}else{
					item.appendChild(new Listcell());
				}
				detailList.appendChild(item);
			}
			if(!flag){
				serviceProviderListBox.setDisabled(true);
				cardTypeListBox.setDisabled(true);
				save.setVisible(false);
			}else{
				serviceProviderListBox.setDisabled(false);
				cardTypeListBox.setDisabled(false);
				save.setVisible(true);
			}

			if(master.getFmtbNonBillableDetails().size()>ConfigurableConstants.getMaxQueryResult())
				detailList.removeItemAt(ConfigurableConstants.getMaxQueryResult());

			if(detailList.getListfoot()!=null)
				detailList.removeChild(detailList.getListfoot());

		}else{
			if(detailList.getListfoot()==null){
				detailList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(6));
			}
		}
		detailList.setMold(Constants.LISTBOX_MOLD_PAGING);
		detailList.setPageSize(10);
	}
	
	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_NONBILLABLE_GL_DETAIL)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			FmtbNonBillableDetail detail = (FmtbNonBillableDetail)item.getValue();
			this.businessHelper.getGenericBusiness().delete(detail);

			item.detach();
			this.detailList.clearSelection();
			this.detailList.getItems().clear();
			this.displayDetails(true);
			Messagebox.show("The Non Billable GL Detail has been successfully deleted", "Delete Non Billable GL Detail",
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
	public void create() throws InterruptedException{
		try{
			Map map = new HashMap();
			map.put("masterNo", masterNo);
			this.forward(Uri.CREATE_NONBILLABLE_GL_DETAIL, map);
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void saveMaster() throws InterruptedException{
		try {
			String serviceProvider = (String)serviceProviderListBox.getSelectedItem().getValue();
			String cardType = (String)cardTypeListBox.getSelectedItem().getValue();
			master.setMstbMasterTableByServiceProvider(ConfigurableConstants.getMasterTable(ConfigurableConstants.SERVICE_PROVIDER, serviceProvider));
			master.setMstbMasterTableByPymtTypeMasterNo(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACQUIRER_PYMT_TYPE, cardType));
			
			if(this.businessHelper.getAdminBusiness().hasDuplicateRecord(master)){
				Messagebox.show("Duplicate record found.","Error", Messagebox.OK, Messagebox.ERROR);
			}else{
				this.businessHelper.getGenericBusiness().update(master, getUserLoginIdAndDomain());
				Messagebox.show("Non Billable GL updated.", "Edit Non Billable GL", Messagebox.OK, Messagebox.INFORMATION);
			}
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
	public void edit() throws InterruptedException{
		try{
			FmtbNonBillableDetail detail = (FmtbNonBillableDetail)detailList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("detailNo", detail.getDetailNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(detail.getEffectiveDate()));
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_NONBILLABLE_GL_DETAIL))
					forward(Uri.EDIT_NONBILLABLE_GL_DETAIL, map);
				else if(this.checkUriAccess(Uri.VIEW_NONBILLABLE_GL_DETAIL))
					forward(Uri.VIEW_NONBILLABLE_GL_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_NONBILLABLE_GL_DETAIL))
					forward(Uri.VIEW_NONBILLABLE_GL_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		this.detailList.clearSelection();
		this.detailList.getItems().clear();
		this.displayDetails(true);
	}
}
