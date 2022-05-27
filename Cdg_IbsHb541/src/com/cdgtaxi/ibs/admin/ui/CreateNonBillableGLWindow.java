package com.cdgtaxi.ibs.admin.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableDetail;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class CreateNonBillableGLWindow extends CommonWindow implements AfterCompose {

	private Listbox serviceProviderListBox, cardTypeListBox, entityListBox, 
		arControlCodeListBox, fAmountTxnCodeListBox, aFeeTxnCodeListBox,
		premiumAmountTxnCodeListBox;
	private Datebox effDateDateBox;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Listitem emptyItem = new Listitem("-", null);
		arControlCodeListBox.appendChild((Listitem)emptyItem.clone());
		
		serviceProviderListBox.appendChild((Listitem)emptyItem.clone());
		List<Entry<String, String>> splist = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.SERVICE_PROVIDER).entrySet());
		for (Map.Entry<String, String> me : splist) {
			Listitem item = new Listitem();
			item.setValue(me.getKey());
			item.setLabel(me.getValue());
			serviceProviderListBox.appendChild(item);
		}
		serviceProviderListBox.setSelectedIndex(0);
		
		cardTypeListBox.appendChild((Listitem)emptyItem.clone());
		List<Entry<String, String>> cplist = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.ACQUIRER_PYMT_TYPE).entrySet());
		for (Map.Entry<String, String> me : cplist) {
			Listitem item = new Listitem();
			item.setValue(me.getKey());
			item.setLabel(me.getValue());
			cardTypeListBox.appendChild(item);
		}
		cardTypeListBox.setSelectedIndex(0);
		
		entityListBox.appendChild((Listitem)emptyItem.clone());
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			Listitem item = new Listitem();
			item.setValue(entity.getEntityNo());
			item.setLabel(entity.getEntityName());
			entityListBox.appendChild(item);
		}
		entityListBox.setSelectedIndex(0);
		
		fAmountTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		fAmountTxnCodeListBox.setSelectedIndex(0);
		
		aFeeTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		aFeeTxnCodeListBox.setSelectedIndex(0);
		
		premiumAmountTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		premiumAmountTxnCodeListBox.setSelectedIndex(0);
		
	}

	public void populateList(){
		this.populateARControlCode();
		this.populateFareAmountTxnCode();
		this.populateAdminAmountTxnCode();
		this.populatePremiumAmountTxnCode();
	}
	
	private void populateARControlCode(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		arControlCodeListBox.getChildren().clear();
		arControlCodeListBox.appendChild((Listitem)emptyItem.clone());
		
		
		if(key!=null){
			List<FmtbArContCodeMaster> controlCodes = this.businessHelper.getAdminBusiness().getARControlCode(key);

			if(controlCodes.size()>0){
				for(FmtbArContCodeMaster controlCode : controlCodes){
					Listitem item = new Listitem();
					item.setValue(controlCode);
					item.setLabel(controlCode.getArControlCode());
					arControlCodeListBox.appendChild(item);
				}
			}
		}
		arControlCodeListBox.setSelectedIndex(0);
	}
	
	private void populateFareAmountTxnCode(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		fAmountTxnCodeListBox.getChildren().clear();
		fAmountTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		fAmountTxnCodeListBox.setSelectedIndex(0);
		
		if(key!=null){
			List<String> transactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_FARE_AMOUNT);
			for(String transactionCode : transactionCodes){
				Listitem item = new Listitem();
				item.setValue(transactionCode);
				item.setLabel(transactionCode);

				fAmountTxnCodeListBox.appendChild(item);
			}
		}
	}
	
	private void populateAdminAmountTxnCode(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		aFeeTxnCodeListBox.getChildren().clear();
		aFeeTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		aFeeTxnCodeListBox.setSelectedIndex(0);
		
		if(key!=null){
			List<String> aTransactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_ADMIN_AMOUNT);
			for(String aTransactionCode : aTransactionCodes){
				Listitem item = new Listitem();
				item.setValue(aTransactionCode);
				item.setLabel(aTransactionCode);

				aFeeTxnCodeListBox.appendChild(item);
			}
		}
	}
	
	private void populatePremiumAmountTxnCode(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		premiumAmountTxnCodeListBox.getChildren().clear();
		premiumAmountTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		premiumAmountTxnCodeListBox.setSelectedIndex(0);
		
		if(key!=null){
			List<String> transactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_PREMIUM_AMOUNT);
			for(String transactionCode : transactionCodes){
				Listitem item = new Listitem();
				item.setValue(transactionCode);
				item.setLabel(transactionCode);

				premiumAmountTxnCodeListBox.appendChild(item);
			}
		}
	}
	
	public void create() throws InterruptedException{
		try{
			this.displayProcessing();
			String serviceProvider = (String)serviceProviderListBox.getSelectedItem().getValue();
			String cardType = (String)cardTypeListBox.getSelectedItem().getValue();
			String fareTxnCode = (String)fAmountTxnCodeListBox.getSelectedItem().getValue();
			String adminTxnCode = (String)aFeeTxnCodeListBox.getSelectedItem().getValue();
			String premiumAmountTxnCode = (String)premiumAmountTxnCodeListBox.getSelectedItem().getValue();
			
			if(serviceProvider!=null && cardType!=null && fareTxnCode!=null && adminTxnCode!=null && arControlCodeListBox.getSelectedItem().getValue()!=null){
				FmtbArContCodeMaster arCont = (FmtbArContCodeMaster)arControlCodeListBox.getSelectedItem().getValue();
				FmtbNonBillableMaster master = new FmtbNonBillableMaster();
				master.setMstbMasterTableByServiceProvider(ConfigurableConstants.getMasterTable(ConfigurableConstants.SERVICE_PROVIDER, serviceProvider));
				master.setMstbMasterTableByPymtTypeMasterNo(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACQUIRER_PYMT_TYPE, cardType));
				
				FmtbNonBillableDetail detail = new FmtbNonBillableDetail();
				detail.setFareAmountTxnCode(fareTxnCode);
				detail.setAdminFeeTxnCode(adminTxnCode);
				detail.setPremiumAmountTxnCode(premiumAmountTxnCode);
				detail.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effDateDateBox.getValue()));
				detail.setFmtbArContCodeMaster(arCont);
				detail.setFmtbNonBillableMaster(master);
				
				if(this.businessHelper.getAdminBusiness().hasDuplicateRecord(master)){
					Messagebox.show("Duplicate record found.","Error", Messagebox.OK, Messagebox.ERROR);
				}else{
					businessHelper.getAdminBusiness().createNonBillableGL(master,detail, getUserLoginIdAndDomain());
					Messagebox.show("New Non Billable GL created.", "Create Non Billable GL", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}
			}else{
				Messagebox.show("All fields are mandatory.","Error", Messagebox.OK, Messagebox.ERROR);
			}
		}catch(WrongValueException wve){
			throw wve;
		}catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
