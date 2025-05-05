package com.cdgtaxi.ibs.acct.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.google.common.base.Splitter;
public abstract class CommonInnerAcctWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CommonInnerAcctWindow.class);
	@SuppressWarnings("unchecked")
	protected void forward(String uri, Map params, Component parent) throws InterruptedException{
		logger.info("forward(String uri, Map params, Component parent)");
		if(!this.checkUriAccess(uri)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED, 
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		this.setVisible(false);
		CommonWindow newPage = (CommonWindow)Executions.createComponents(uri, parent, params);
		newPage.setPreviousPage(this);
	}
	
	protected String checkReminderEmail(String reminderEmail) {
		return emailValidation(reminderEmail);
	}
	
	protected String checkPDFEInvoiceEmail(String pdfEInvoiceEmail) {
		return emailValidation(pdfEInvoiceEmail);
	}
	
	private String emailValidation(String email) {
		
		String result = "";
		
		int x = 0;
		Iterable<String> result1 = Splitter.on(';').split(email);
		for(String s1: result1){
			if(x != 0)
				result = result + "; "+s1.trim();
			else
				result = s1.trim();
			x++;
		}
		
		return result;
	}
	
}