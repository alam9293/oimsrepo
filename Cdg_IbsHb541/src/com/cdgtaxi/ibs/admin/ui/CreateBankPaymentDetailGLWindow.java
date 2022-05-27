package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class CreateBankPaymentDetailGLWindow extends CommonWindow implements AfterCompose {

	private Integer masterNo;
	private FmtbBankPaymentMaster master;
	private Label acquirerLabel;
	private Listbox entityListBox, arControlCodeListBox, mdrTxnCodeListBox, adjTxnCodeListBox;
	private Datebox effDateDateBox;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Map map = Executions.getCurrent().getArg();
		masterNo = (Integer)map.get("masterNo");
		master = this.businessHelper.getAdminBusiness().getBankPaymentMaster(masterNo);
		acquirerLabel.setValue(master.getMstbAcquirer().getName());
		
		Listitem emptyItem = new Listitem("-", null);
		entityListBox.appendChild((Listitem)emptyItem.clone());
		List<FmtbEntityMaster> entityList = this.businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entityList) {
			Listitem item = new Listitem();
			item.setValue(entity.getEntityNo());
			item.setLabel(entity.getEntityName());
			entityListBox.appendChild(item);
		}
		entityListBox.setSelectedIndex(0);
		
		arControlCodeListBox.appendChild((Listitem)emptyItem.clone());
		arControlCodeListBox.setSelectedIndex(0);
		
		mdrTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		mdrTxnCodeListBox.setSelectedIndex(0);
		
		adjTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		adjTxnCodeListBox.setSelectedIndex(0);
	}
	public void create() throws InterruptedException{
		try{
			FmtbArContCodeMaster controlCode = (FmtbArContCodeMaster)arControlCodeListBox.getSelectedItem().getValue();
			String mdr = (String)mdrTxnCodeListBox.getSelectedItem().getValue();
			String adj = (String)adjTxnCodeListBox.getSelectedItem().getValue();
			
			if(controlCode!=null && mdr!=null && adj!=null && effDateDateBox.getValue()!=null){
				FmtbBankPaymentDetail detail = new FmtbBankPaymentDetail();
				detail.setFmtbArContCodeMaster(controlCode);
				detail.setMdrTxnCode(mdr);
				detail.setMdrAdjTxnCode(adj);
				detail.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effDateDateBox.getValue()));
				detail.setFmtbBankPaymentMaster(master);
				
				List<FmtbBankPaymentDetail> result = this.businessHelper.getAdminBusiness().getBankPaymentDetailByForeignExample(detail);
				if(!result.isEmpty())
						throw new DuplicateNameError();
				
				this.checkTxnCodeEffectedBeforeNewDetail(detail);
				this.businessHelper.getGenericBusiness().save(detail, getUserLoginIdAndDomain());
				Messagebox.show("Bank Payment GL Created.", "Create Bank Payment GL", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}else{
				Messagebox.show("All fields are mandatory.", "Create Bank Payment GL", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(DuplicateNameError dne){
			Messagebox.show("Another record with same effective date found. Please specify another.", "Create Bank Payment GL",
					Messagebox.OK, Messagebox.ERROR);
		}
		catch(DataValidationError dve){
			Messagebox.show(dve.getMessage(), "Create Bank Payment GL", Messagebox.OK, Messagebox.INFORMATION);
			dve.printStackTrace();
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
	public void populateList(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		this.populateARControlCode(key);
		this.populateMDRTxnCode(key);
		this.populateADJTxnCode(key);
	}
	
	private void populateADJTxnCode(Integer key){
		Listitem emptyItem = new Listitem("-", null);
		adjTxnCodeListBox.getChildren().clear();
		adjTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		if(key!=null){
			List<String> adjCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key,NonConfigurableConstants.TRANSACTION_TYPE_BANK_ADVICE_MDR_ADJUSTMENT);
			for(String adjCode : adjCodes){
				Listitem item = new Listitem();
				item.setValue(adjCode);
				item.setLabel(adjCode);
				adjTxnCodeListBox.appendChild(item);
			}
		}
		adjTxnCodeListBox.setSelectedIndex(0);
	}
	
	private void populateMDRTxnCode(Integer key){
		Listitem emptyItem = new Listitem("-", null);
		mdrTxnCodeListBox.getChildren().clear();
		mdrTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		if(key!=null){
			List<String> mdrCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key,NonConfigurableConstants.TRANSACTION_TYPE_BANK_ADVICE_MDR);
			for(String code : mdrCodes){
				Listitem item = new Listitem();
				item.setValue(code);
				item.setLabel(code);
				mdrTxnCodeListBox.appendChild(item);
			}
		}
		mdrTxnCodeListBox.setSelectedIndex(0);
	}
	
	private void populateARControlCode(Integer key){
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
	
	private void checkTxnCodeEffectedBeforeNewDetail(FmtbBankPaymentDetail detail) throws DataValidationError{
		FmtbTransactionCode mdrTxnCode = this.businessHelper.getAdminBusiness().getEarliestEffectedTxnCode(
				NonConfigurableConstants.TRANSACTION_TYPE_BANK_ADVICE_MDR, detail.getMdrTxnCode());
		
		if(mdrTxnCode.getEffectiveDate().after(detail.getEffectiveDate()))
			throw new DataValidationError("Selected MDR Txn Code has not yet taken effect based on detail's effective date!");
		
		FmtbTransactionCode mdrAdjustTxnCode = this.businessHelper.getAdminBusiness().getEarliestEffectedTxnCode(
				NonConfigurableConstants.TRANSACTION_TYPE_BANK_ADVICE_MDR_ADJUSTMENT, detail.getMdrAdjTxnCode());
		
		if(mdrAdjustTxnCode.getEffectiveDate().after(detail.getEffectiveDate()))
			throw new DataValidationError("Selected MDR adjustment Txn Code has not yet taken effect based on detail's effective date!");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
