package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class EditBankPaymentGLWindow extends CommonWindow implements AfterCompose {

	private Integer masterNo;
	private FmtbBankPaymentMaster master;
	private Listbox detailList, acquirerListBox;
	private Button save,createBtn;
	private static final Logger logger = Logger.getLogger(EditBankPaymentGLWindow.class);
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		try{
			//wire variables
			Components.wireVariables(this, this);

			Map map = Executions.getCurrent().getArg();
			masterNo = (Integer)map.get("masterNo");

			master = this.businessHelper.getAdminBusiness().getBankPaymentMaster(masterNo);
			
			Listitem emptyItem = new Listitem("-", null);
			acquirerListBox.appendChild((Listitem)emptyItem.clone());
			List<MstbAcquirer> acquirers = this.businessHelper.getAdminBusiness().getAcquirer();
			for(MstbAcquirer acquirer : acquirers){
				Listitem item = new Listitem();
				item.setValue(acquirer);
				item.setLabel(acquirer.getName());
				if(master.getMstbAcquirer()!=null)
					if(master.getMstbAcquirer().getAcquirerNo().equals(acquirer.getAcquirerNo()))
						item.setSelected(true);

				acquirerListBox.appendChild(item);
			}
			if(acquirerListBox.getSelectedItem()==null) acquirerListBox.setSelectedIndex(0);

			this.displayDetails(false);
			
			if(!this.checkUriAccess(Uri.CREATE_BANK_PAYMENT_DETAIL_GL))
				createBtn.setDisabled(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void displayDetails(boolean refresh) throws InterruptedException{
		if(refresh)
			master = this.businessHelper.getAdminBusiness().getBankPaymentMaster(masterNo);
		
		if(master.getFmtbBankPaymentDetails().size()>0){
			boolean flag = false;
			if(master.getFmtbBankPaymentDetails().size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

			for(FmtbBankPaymentDetail detail : master.getFmtbBankPaymentDetails()){
				Listitem item = new Listitem();

				item.setValue(detail);
				item.appendChild(newListcell(detail.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName()));
				item.appendChild(newListcell(detail.getFmtbArContCodeMaster().getArControlCode()));
				item.appendChild(newListcell(detail.getMdrTxnCode()));
				item.appendChild(newListcell(detail.getMdrAdjTxnCode()));
				item.appendChild(newListcell(detail.getEffectiveDate()));
				
				if(DateUtil.getCurrentDate().compareTo(detail.getEffectiveDate()) <=0){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("editBankPaymentGLWindow.delete(self.getParent().getParent())");
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
				acquirerListBox.setDisabled(true);
				save.setVisible(false);
			}else{
				acquirerListBox.setDisabled(false);
				save.setVisible(true);
			}

			if(master.getFmtbBankPaymentDetails().size()>ConfigurableConstants.getMaxQueryResult())
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
		if(!this.checkUriAccess(Uri.Delete_BANK_PAYMENT_DETAIL_GL)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			FmtbBankPaymentDetail detail = (FmtbBankPaymentDetail)item.getValue();
			this.businessHelper.getGenericBusiness().delete(detail);

			item.detach();
			this.detailList.clearSelection();
			this.detailList.getItems().clear();
			this.displayDetails(true);
			Messagebox.show("The Bank Payment GL Detail has been successfully deleted", "Delete Bank Payment GL Detail",
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
	public void edit() throws InterruptedException{
		try{
			FmtbBankPaymentDetail detail = (FmtbBankPaymentDetail)detailList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("detailNo", detail.getDetailNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(detail.getEffectiveDate()));
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_BANK_PAYMENT_DETAIL_GL))
					this.forward(Uri.EDIT_BANK_PAYMENT_DETAIL_GL, map);
				else if(this.checkUriAccess(Uri.VIEW_BANK_PAYMENT_DETAIL_GL))
					this.forward(Uri.VIEW_BANK_PAYMENT_DETAIL_GL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_BANK_PAYMENT_DETAIL_GL))
					this.forward(Uri.VIEW_BANK_PAYMENT_DETAIL_GL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
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
	public void create() throws InterruptedException{
		try{
			Map map = new HashMap();
			map.put("masterNo", masterNo);
			this.forward(Uri.CREATE_BANK_PAYMENT_DETAIL_GL, map);
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
	public void save() throws InterruptedException{
		try{
			MstbAcquirer acquirer = (MstbAcquirer)acquirerListBox.getSelectedItem().getValue();
			
			if(acquirer!=null){
				master.setMstbAcquirer(acquirer);
				List<FmtbBankPaymentMaster> result = this.businessHelper.getAdminBusiness().getBankPaymentByForeignExample(master);
				if(!result.isEmpty())
					throw new DuplicateNameError();
				this.businessHelper.getGenericBusiness().update(master, getUserLoginIdAndDomain());
				Messagebox.show("Bank Payment GL updated.", "Edit Bank Payment GL", Messagebox.OK, Messagebox.INFORMATION);
				this.refresh();
			}else{
				Messagebox.show("Please select Acquirer mandatory field.", "Edit Bank Payment Detail GL", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateNameError dne){
			Messagebox.show("Duplicate record found. Please specify another.", "Edit Bank Payment Detail GL",
					Messagebox.OK, Messagebox.ERROR);
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
		this.detailList.clearSelection();
		this.detailList.getItems().clear();
		this.displayDetails(true);
	}
}
