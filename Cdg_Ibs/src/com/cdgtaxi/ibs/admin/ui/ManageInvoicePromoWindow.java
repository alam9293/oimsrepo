package com.cdgtaxi.ibs.admin.ui;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbInvoicePromo;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings({"serial", "unchecked"})
public class ManageInvoicePromoWindow extends CommonWindow implements AfterCompose {

	private static final Logger logger = Logger.getLogger(ManageAcquirerWindow.class);
	private Datebox dateFromDateBox, dateToDateBox;
	private Listbox resultList;
	private CapsTextbox name;
	private Button createBtn;
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
						
		if(!this.checkUriAccess(Uri.MANAGE_INVOICE_PROMO)){
			createBtn.setDisabled(true);
		}
		
		initialSearch();
	}
	public void initialSearch(){
		resultList.getItems().clear();
		try {
			List<MstbInvoicePromo> invoicePromoList = this.businessHelper.getAdminBusiness().getListInvoicePromo("", null , null, "initial");
			
			if(invoicePromoList != null && invoicePromoList.size()>0){
				
				if(invoicePromoList.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(MstbInvoicePromo invoicePromo : invoicePromoList){
					Listitem item = new Listitem();
					item.setValue(invoicePromo.getInvoicePromoId());
					item.appendChild(newListcell(invoicePromo.getName()));
					item.appendChild(newListcell(DateUtil.convertTimestampToUtilDate(invoicePromo.getInsertDt())));
					
					item.appendChild(newListcell(invoicePromo.getPromo1()));
					item.appendChild(newListcell(invoicePromo.getPromo2()));
					item.appendChild(newListcell(invoicePromo.getPromo3()));
					
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageInvoicePromoWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					Listcell imageCell = new Listcell();
					imageCell.appendChild(deleteImage);
					item.appendChild(imageCell);
	
					resultList.appendChild(item);
				}
				
				if(invoicePromoList.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(6));
				}
			}
			
			resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultList.setPageSize(10);
		}
		catch(Exception e) {}
	}
	public void search() throws SuspendNotAllowedException, InterruptedException{
		
		logger.info("search Invoice Promo()");
		
		this.displayProcessing();
		resultList.getItems().clear();
		
		try{			
			if(dateFromDateBox.getValue()!=null && dateToDateBox.getValue()!=null)
				if(dateFromDateBox.getValue().compareTo(dateToDateBox.getValue()) == 1)
					throw new WrongValueException(dateFromDateBox, "Insert Date From cannot be later than Insert Date To.");
			
			String formName = "";
			if(name.getValue() != null)
				formName = name.getValue();
			
			Date dateFrom = null;
			Date dateTo = null;
			if(dateFromDateBox.getValue() != null)
				dateFrom = new Date (dateFromDateBox.getValue().getTime());
			if(dateToDateBox.getValue() != null)
				dateTo = new Date (dateToDateBox.getValue().getTime());
			
			List<MstbInvoicePromo> invoicePromoList = this.businessHelper.getAdminBusiness().getListInvoicePromo(formName, dateFrom , dateTo, null);
			
			if(invoicePromoList != null && invoicePromoList.size()>0){
				
				if(invoicePromoList.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(MstbInvoicePromo invoicePromo : invoicePromoList){
					Listitem item = new Listitem();
					item.setValue(invoicePromo.getInvoicePromoId());
					item.appendChild(newListcell(invoicePromo.getName()));
					item.appendChild(newListcell(DateUtil.convertTimestampToUtilDate(invoicePromo.getInsertDt())));
					
					item.appendChild(newListcell(invoicePromo.getPromo1()));
					item.appendChild(newListcell(invoicePromo.getPromo2()));
					item.appendChild(newListcell(invoicePromo.getPromo3()));
					
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageInvoicePromoWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					Listcell imageCell = new Listcell();
					imageCell.appendChild(deleteImage);
					item.appendChild(imageCell);

					resultList.appendChild(item);
				}
				
				if(invoicePromoList.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(6));
				}
			}
			
			resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultList.setPageSize(10);
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
		if(!this.checkUriAccess(Uri.DELETE_INVOICE_PROMO)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			MstbInvoicePromo invoicePromo = (MstbInvoicePromo)this.businessHelper.getGenericBusiness().get(MstbInvoicePromo.class, (Integer)item.getValue());
			this.businessHelper.getGenericBusiness().delete(invoicePromo);
			
			item.detach();
			Messagebox.show("Invoice Promo has been successfully deleted", "Delete Invoice Promo",
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
	
	public void edit() throws InterruptedException{
		try{
			Integer invoicePromoId = (Integer)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("invoicePromoId", invoicePromoId);

			if(this.checkUriAccess(Uri.EDIT_ACQUIRER_PAYMENT_TYPE))
				forward(Uri.EDIT_INVOICE_PROMO, map);
			else {
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
	
	public void create() throws InterruptedException{
		this.forward(Uri.CREATE_INVOICE_PROMO, null);
	}
	
	public void reset(){
		name.setText("");
		dateFromDateBox.setText("");
		dateToDateBox.setText("");		
		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(5));
	}
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		reset();
	}
}
