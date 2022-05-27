package com.cdgtaxi.ibs.billing.ui;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class CancelPaymentReceiptWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CancelPaymentReceiptWindow.class);
	private static final String SELF = "cancelPaymentReceiptWindow";
	private BmtbPaymentReceipt paymentReceipt;
	
	public CancelPaymentReceiptWindow(){
		Map params = Executions.getCurrent().getArg();
		paymentReceipt = (BmtbPaymentReceipt)params.get("paymentReceipt");
		if(paymentReceipt == null)
			throw new NullPointerException("Payment Receipt Object not found!"); //This should not happen at all
	}
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		//Cancel Remarks Section
		Listbox cancellationReasonListBox = (Listbox)this.getFellow("cancellationReasonListBox");
		//populate the reasons
		List<Listitem> cancellationReasons = ComponentUtil.convertToListitems(ConfigurableConstants.getCancellationReasons(), true);
		boolean firstItem = true;
		for(Listitem listItem : cancellationReasons){
			cancellationReasonListBox.appendChild(listItem);
			if(firstItem){
				listItem.setSelected(true);
				firstItem = false;
			}
		}
	}
	
	public void cancel() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox cancellationReasonListBox = (Listbox)this.getFellow("cancellationReasonListBox");
			String cancellationReasonCode = (String)cancellationReasonListBox.getSelectedItem().getValue();
			CapsTextbox cancelRemarksTextBox = (CapsTextbox)this.getFellow("cancelRemarksTextBox");
			String cancelRemarks = cancelRemarksTextBox.getValue();
			
			MstbMasterTable cancellationReason = ConfigurableConstants.getMasterTable(ConfigurableConstants.CANCELLATION_REASON, cancellationReasonCode);
			if(cancellationReason==null)
				throw new Exception("Selected reason does not exist in database!");
			this.paymentReceipt.setMstbMasterTableByCancellationReason(cancellationReason);
			this.paymentReceipt.setCancelRemarks(cancelRemarks);
			
			this.businessHelper.getPaymentBusiness().cancelPaymentReceipt(paymentReceipt, getUserLoginIdAndDomain());
			
			//Show result
			Messagebox.show("Cancel payment receipt successful.", "Cancel Payment Receipt", Messagebox.OK, Messagebox.INFORMATION);
			
			this.back().back(); //back to search window
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
	public void refresh() {
		
	}
}
