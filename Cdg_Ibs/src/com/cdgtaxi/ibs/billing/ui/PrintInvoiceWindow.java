package com.cdgtaxi.ibs.billing.ui;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
public class PrintInvoiceWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PrintInvoiceWindow.class);

	private Longbox invoiceNoFromLongBox, invoiceNoToLongBox;
	private Listbox entityListBox, printerListBox;
	private Intbox billGenRequestNoIntBox, accountNoFromIntBox, accountNoToIntBox;
	private Row billGenInfoRow1, billGenInfoRow2;
	private Label billGenSetupLabel, requestDateLabel, statusLabel;
	
	private BmtbBillGenReq billGenRequest;
	
	@Override
	public void refresh() {

	}

	public void afterCompose() {
		invoiceNoFromLongBox = (Longbox)this.getFellow("invoiceNoFromLongBox");
		invoiceNoToLongBox = (Longbox)this.getFellow("invoiceNoToLongBox");
		entityListBox = (Listbox)this.getFellow("entityListBox");
		printerListBox = (Listbox)this.getFellow("printerListBox");
		billGenRequestNoIntBox = (Intbox)this.getFellow("billGenRequestNoIntBox");
		billGenInfoRow1 = (Row)this.getFellow("billGenInfoRow1");
		billGenInfoRow2 = (Row)this.getFellow("billGenInfoRow2");
		billGenSetupLabel = (Label)this.getFellow("billGenSetupLabel");
		requestDateLabel = (Label)this.getFellow("requestDateLabel");
		statusLabel = (Label)this.getFellow("statusLabel");
		accountNoFromIntBox = (Intbox)this.getFellow("accountNoFromIntBox");
		accountNoToIntBox = (Intbox)this.getFellow("accountNoToIntBox");
		
		//init the printers
		Map<String,String> printerMap = ConfigurableConstants.getPrinters();
		Set<String> keys = printerMap.keySet();
		for(String key : keys){
			printerListBox.appendChild(new Listitem(printerMap.get(key), key));
		}
		
		//populate entity list
		Listbox entityListBox = (Listbox)this.getFellow("entityListBox");
		Listitem listItem =  new Listitem("-", null);
		listItem.setSelected(true);
		entityListBox.appendChild(listItem);
		Map<Integer, String> ems = MasterSetup.getEntityManager().getAllMasters();
		for(Integer em : ems.keySet()){
			entityListBox.appendChild(new Listitem(ems.get(em), em));
		}
	}
	
	public void searchBillGenRequest() throws InterruptedException{
		this.displayProcessing();
		
		try{
			this.clearBillGenInfo();
			this.clearInvoiceNoInputs();
			this.disableAccountNoInputs();
			
			Integer entityNo = (Integer)entityListBox.getSelectedItem().getValue();
			Integer billGenRequestNo = billGenRequestNoIntBox.getValue();
			if(billGenRequestNo!=null){
				List<BmtbBillGenReq> billGenRequests = this.businessHelper.getBillGenBusiness().searchRequest(billGenRequestNo, null, null, null, null, entityNo);
				if(!billGenRequests.isEmpty()){
					if(billGenRequests.get(0).getBmtbBillGenSetupBySetupNo().getSetupNo().intValue() != NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue() &&
							(billGenRequests.get(0).getStatus().equals(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_REGENERATED) ||
							billGenRequests.get(0).getStatus().equals(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_COMPLETED))){
						billGenRequest = billGenRequests.get(0);
						this.showBillGenInfo(billGenRequests.get(0));
						this.enableAccountNoInputs();
					}
				}
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
	
	private void clearBillGenInfo(){
		logger.info("");
		billGenRequest = null;
		billGenInfoRow1.setVisible(false);
		billGenInfoRow2.setVisible(false);
		billGenSetupLabel.setValue("");
		requestDateLabel.setValue("");
		statusLabel.setValue("");
	}
	
	private void showBillGenInfo(BmtbBillGenReq billGenRequest){
		logger.info("");
		billGenInfoRow1.setVisible(true);
		billGenInfoRow2.setVisible(true);
		billGenSetupLabel.setValue(NonConfigurableConstants.BILL_GEN_SETUP.get(billGenRequest.getBmtbBillGenSetupBySetupNo().getSetupNo()));
		requestDateLabel.setValue(DateUtil.convertDateToStr(billGenRequest.getRequestDate(), DateUtil.GLOBAL_DATE_FORMAT));
		statusLabel.setValue(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS.get(billGenRequest.getStatus()));
	}
	
	private void clearInvoiceNoInputs(){
		logger.info("");
		invoiceNoFromLongBox.setText("");
		invoiceNoToLongBox.setText("");
	}
	
	private void enableAccountNoInputs(){
		logger.info("");
		accountNoFromIntBox.setText("");
		accountNoToIntBox.setText("");
		accountNoFromIntBox.setDisabled(false);
		accountNoToIntBox.setDisabled(false);
		
		//Geok Hua 20 Jan 2010
		//should focus to Account No From after losing focus from Bill Gen Request No instead of 
		//focusing on invoice no
		accountNoFromIntBox.focus();
	}
	
	private void disableAccountNoInputs(){
		logger.info("");
		accountNoFromIntBox.setText("");
		accountNoToIntBox.setText("");
		accountNoFromIntBox.setDisabled(true);
		accountNoToIntBox.setDisabled(true);
	}
	
	@SuppressWarnings("unchecked")
	public void print() throws InterruptedException{
		try{
			Long invoiceNoFrom = invoiceNoFromLongBox.getValue();
			Long invoiceNoTo = invoiceNoToLongBox.getValue();
			
			if(invoiceNoFrom!=null && invoiceNoTo==null){
				invoiceNoToLongBox.setValue(invoiceNoFrom);
				invoiceNoTo = invoiceNoFrom;
			}
			else if(invoiceNoTo!=null && invoiceNoFrom==null){
				invoiceNoFromLongBox.setValue(invoiceNoTo);
				invoiceNoFrom = invoiceNoTo;
			}
			if(invoiceNoFrom!=null && invoiceNoTo!=null){
				if(invoiceNoFrom.intValue() > invoiceNoTo.intValue()){
					Messagebox.show("Invoice No From input cannot be greater than Invoice No To input",
							"Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			
			Integer billGenRequestNo = billGenRequestNoIntBox.getValue(); 
			if(invoiceNoFrom==null && invoiceNoTo==null){
				if(billGenRequestNo==null)
					throw new WrongValueException(billGenRequestNoIntBox, "* Mandatory field");
				if(billGenRequest==null)
					throw new WrongValueException(billGenRequestNoIntBox, "Bill gen request no provided is invalid");
			}
			
			Integer custNoFrom = accountNoFromIntBox.getValue();
			Integer custNoTo = accountNoToIntBox.getValue();
			if(custNoFrom!=null && custNoTo==null){
				accountNoToIntBox.setValue(custNoFrom);
				custNoTo = custNoFrom;
			}
			else if(custNoTo!=null && custNoFrom==null){
				accountNoFromIntBox.setValue(custNoTo);
				custNoFrom = custNoTo;
			}
			if(custNoFrom!=null && custNoTo!=null){
				if(custNoFrom.intValue() > custNoTo.intValue()){
					Messagebox.show("Account No From input cannot be greater than Account No To input",
							"Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			
			//Geok Hua - 25 Jan 2010
			//Printing using invoice no: Regardless there is activities or not, the invoice is to be printed
			boolean printNoActivity = false;
			if(invoiceNoFrom!=null) printNoActivity = true;
			
			Set<Listitem> printers = printerListBox.getSelectedItems();
			if(printers.isEmpty()){
				Messagebox.show("You must select at least one printer",
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			boolean printOK = this.businessHelper.getInvoiceBusiness().print(billGenRequestNo, custNoFrom, custNoTo, invoiceNoFrom, invoiceNoTo, printNoActivity, printers, getUserLoginIdAndDomain());
			
			if(printOK)
				Messagebox.show("Your printing request has been sent for processing.",
					"Printing Invoices", Messagebox.OK, Messagebox.INFORMATION);
			else
				Messagebox.show("No invoices found for printing, your request is not send.",
						"Printing Invoices", Messagebox.OK, Messagebox.EXCLAMATION);
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
	
	public void reset(){
		this.entityListBox.setSelectedIndex(0);
		this.billGenRequestNoIntBox.setText("");
		this.clearBillGenInfo();
		this.clearInvoiceNoInputs();
		this.disableAccountNoInputs();
	}
}
