package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
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
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentMaster;
import com.cdgtaxi.ibs.common.model.forms.SearchBankPaymentForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class ManageBankPaymentGLWindow extends CommonWindow implements AfterCompose {

	private Listbox resultList, acquirerListBox;
	private Button createBtn;
	List<FmtbBankPaymentMaster> bankPymts;
	SearchBankPaymentForm form;
	private static final Logger logger = Logger.getLogger(ManageBankPaymentGLWindow.class);
	
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

		Listitem emptyItem = new Listitem("-", null);
		
		acquirerListBox.appendChild((Listitem)emptyItem.clone());
		List<MstbAcquirer> acquirers = this.businessHelper.getAdminBusiness().getAcquirer();
		for(MstbAcquirer acquirer : acquirers){
			Listitem item = new Listitem();
			item.setValue(acquirer);
			item.setLabel(acquirer.getName());
			acquirerListBox.appendChild(item);
		}
		acquirerListBox.setSelectedIndex(0);
		
		if(!this.checkUriAccess(Uri.CREATE_BANK_PAYMENT_GL))
			createBtn.setDisabled(true);
	}
	public void search() throws SuspendNotAllowedException, InterruptedException{	
		try{
			form =  this.buildSearchForm();
			if(!form.isAtLeastOneCriteriaSelected){
				Messagebox.show("Search criteria must have input", "Alert", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			resultList.getItems().clear();
			this.displayProcessing();
			displayDetails(true);
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
	private void displayDetails(boolean refresh) throws InterruptedException{
		if(refresh)
			bankPymts = this.businessHelper.getAdminBusiness().searchBankPayment(form);
		
		if(bankPymts.size()>0){
			if(bankPymts.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

			for(FmtbBankPaymentMaster payment : bankPymts){
				Listitem item = new Listitem();

				item.setValue(payment);
				item.appendChild(newListcell(payment.getMstbAcquirer().getName()));
				
				if(payment.getFmtbBankPaymentDetails().size()<=0){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageBankPaymentGLWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					Listcell imageCell = new Listcell();
					imageCell.appendChild(deleteImage);
					item.appendChild(imageCell);
				}else{
					item.appendChild(new Listcell());
				}
				resultList.appendChild(item);
			}
			if(bankPymts.size()>ConfigurableConstants.getMaxQueryResult())
				resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());

			if(resultList.getListfoot()!=null)
				resultList.removeChild(resultList.getListfoot());
		}else{
			if(resultList.getListfoot()==null){
				resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
			}
		}
		resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultList.setPageSize(10);
	}
	@SuppressWarnings("unchecked")
	public void edit() throws InterruptedException{
		try{
			Integer masterNo = (Integer)((FmtbBankPaymentMaster)resultList.getSelectedItem().getValue()).getMasterNo();
			FmtbBankPaymentMaster master = (FmtbBankPaymentMaster)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("masterNo", masterNo);
			
			if(this.checkUriAccess(Uri.EDIT_BANK_PAYMENT_GL))
				forward(Uri.EDIT_BANK_PAYMENT_GL, map);
			else if(this.checkUriAccess(Uri.VIEW_BANK_PAYMENT_GL))
				forward(Uri.VIEW_BANK_PAYMENT_GL, map);
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
	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.Delete_BANK_PAYMENT_GL)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			FmtbBankPaymentMaster master = (FmtbBankPaymentMaster)item.getValue();
			this.businessHelper.getGenericBusiness().delete(master);

			item.detach();
			this.resultList.clearSelection();
			this.resultList.getItems().clear();
			this.displayDetails(true);
			Messagebox.show("The Bank Payment GL has been successfully deleted", "Delete Bank Payment GL",
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
	private SearchBankPaymentForm buildSearchForm(){
		SearchBankPaymentForm form = new SearchBankPaymentForm();
		
		if(acquirerListBox.getSelectedItem().getValue()!=null)
			form.acquirer = (MstbAcquirer)acquirerListBox.getSelectedItem().getValue();
		if(form.acquirer!=null) form.isAtLeastOneCriteriaSelected = true;
		
		return form;
	}
	public void reset(){
		acquirerListBox.setSelectedIndex(0);
		
		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
	}
	public void create() throws InterruptedException {
		try {
			
			forward(Uri.CREATE_BANK_PAYMENT_GL, null);
		} 
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		if(this.buildSearchForm().isAtLeastOneCriteriaSelected)
			this.search();
	}
}
