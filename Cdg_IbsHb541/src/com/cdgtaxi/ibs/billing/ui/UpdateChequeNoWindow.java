package com.cdgtaxi.ibs.billing.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;

import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class UpdateChequeNoWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UpdateChequeNoWindow.class);
	private BmtbPaymentReceipt paymentReceipt;
	private String chequeNo;
	
	public UpdateChequeNoWindow(){
		Map params = Executions.getCurrent().getArg();
		paymentReceipt = (BmtbPaymentReceipt)params.get("paymentReceipt");
	}
	
	public void cancel(){
		this.detach();
	}
	
	public void save() throws InterruptedException{
		logger.info("");
		
		try{
			chequeNo = ((CapsTextbox)this.getFellow("chequeNoTextBox")).getValue();
			paymentReceipt.setChequeNo(chequeNo);
			this.businessHelper.getGenericBusiness().update(paymentReceipt, getUserLoginIdAndDomain());
			
			this.detach();
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

	public String getChequeNo() {
		return chequeNo;
	}
}
