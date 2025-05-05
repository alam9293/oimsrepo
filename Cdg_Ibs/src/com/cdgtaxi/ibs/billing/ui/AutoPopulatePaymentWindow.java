package com.cdgtaxi.ibs.billing.ui;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Decimalbox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class AutoPopulatePaymentWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AutoPopulatePaymentWindow.class);
	private BigDecimal totalOutstandingAmount;
	private BigDecimal paymentAmount;
	
	public AutoPopulatePaymentWindow(){
		Map map = Executions.getCurrent().getArg();
		totalOutstandingAmount = map.get("totalOutstandingAmount") == null ? BigDecimal.ZERO : (BigDecimal)map.get("totalOutstandingAmount");
	}
	
	public void cancel(){
		this.detach();
	}
	
	public void populate() throws InterruptedException{
		logger.info("");
		
		try{
			Decimalbox paymentAmountDecimalBox = (Decimalbox)this.getFellow("paymentAmountDecimalBox");
			BigDecimal tempAmount = paymentAmountDecimalBox.getValue();
			if(tempAmount.compareTo(totalOutstandingAmount)>0)
				throw new WrongValueException(paymentAmountDecimalBox, "Amount cannot be greater than total outstanding amount");
			paymentAmount = tempAmount;
			
			this.detach();
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
	public void refresh() {
		
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
}
