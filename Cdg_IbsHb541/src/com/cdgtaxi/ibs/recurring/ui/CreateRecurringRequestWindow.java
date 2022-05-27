package com.cdgtaxi.ibs.recurring.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Longbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.model.recurring.RecurringConfig;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class CreateRecurringRequestWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateRecurringRequestWindow.class);

	private static final String PROCESS_URL = "http://localhost:8080/ibs/recurring/process_recurring_upload.zul";
	private Intbox billGenReqNoIB;
	private Longbox invoiceNoFromLB, invoiceNoToLB;
	private CapsTextbox accountNos;
	private Datebox invoiceDateDB, chargingDateDB;

	// After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	/**
	 * To clear other inputs based on the given input mode.<br>
	 * 1 = billGenReqNo
	 * 2 = Invoice Nos from to
	 * 3 = Account Nos / Invoice Date 
	 * 4 = Charging Date
	 */
	public void clearOtherInputs(Integer inputMode) {
		switch (inputMode) {
		case 1:
			invoiceNoFromLB.setText("");
			invoiceNoToLB.setText("");
			accountNos.setText("");
			chargingDateDB.setText("");
		case 2:
			billGenReqNoIB.setText("");
			accountNos.setText("");
			invoiceDateDB.setText("");
			chargingDateDB.setText("");
			break;
		case 3:
			billGenReqNoIB.setText("");
			invoiceNoFromLB.setText("");
			invoiceNoToLB.setText("");
			chargingDateDB.setText("");
			break;
		case 4:
			billGenReqNoIB.setText("");
			invoiceNoFromLB.setText("");
			invoiceNoToLB.setText("");
			accountNos.setText("");
			invoiceDateDB.setText("");
			break;
	
		default:
			break;
		}
	}
	

	public void createRequest() throws InterruptedException {
		logger.info("");
		Integer requestNo = -1;
		try {
			
			if (invoiceNoFromLB.getValue() != null && invoiceNoToLB.getValue() == null)
				invoiceNoToLB.setValue(invoiceNoFromLB.getValue());
			if (invoiceNoToLB.getValue() != null && invoiceNoFromLB.getValue() == null)
				invoiceNoFromLB.setValue(invoiceNoToLB.getValue());
			
			// Mandatory field validation
			if (billGenReqNoIB.getValue() == null && invoiceDateDB.getValue() == null
					&& invoiceNoFromLB.getValue() == null && chargingDateDB.getValue() == null && accountNos.getValue().trim().equals(""))
				throw new WrongValueException(
						"Either Bill Gen request no. or Invoice No Range or Account No + Invoice Date or Chaging Date is mandatory");
			
			if(invoiceDateDB.getValue() != null)
			{
				if(accountNos.getValue().trim().equals(""))
					throw new WrongValueException(" Account No + Invoice Date is mandatory");
			}
			if(!accountNos.getValue().trim().equals(""))
			{
				if(invoiceDateDB.getValue() == null)
					throw new WrongValueException(" Account No + Invoice Date is mandatory");
			}
			// Create new request and default the status
			
			IttbRecurringReq newRequest = new IttbRecurringReq();
			newRequest.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_PENDING);
			newRequest.setRecurringAutoManual("M");
				
			// Capture input into request
			if (billGenReqNoIB.getValue() != null) {
				List<BmtbBillGenReq> results = this.businessHelper.getBillGenBusiness().searchRequest(
						billGenReqNoIB.getValue(), null, null, null, null, null);
				if (results.isEmpty())
					throw new WrongValueException(billGenReqNoIB, "No bill gen request found!");
							
				newRequest.setBmtbBillGenReq(results.get(0));
			} 
			else if (invoiceDateDB.getValue() != null){
				newRequest.setInvoiceDate(DateUtil.convertDateToTimestamp(invoiceDateDB.getValue()));
			}
			else if (invoiceNoFromLB.getValue() != null) {
				newRequest.setInvoiceNoFrom(invoiceNoFromLB.getValue());
				newRequest.setInvoiceNoTo(invoiceNoToLB.getValue());
			}
			if (accountNos.getValue() != null){
				newRequest.setAccountNos(accountNos.getValue());
			}
			if (chargingDateDB.getValue() != null){
				newRequest.setChargingDate(DateUtil.convertDateToTimestamp(chargingDateDB.getValue()));
			}
			
			requestNo = (Integer) this.businessHelper.getGenericBusiness().save(newRequest,
					CommonWindow.getUserLoginIdAndDomain());

			Messagebox.show("New manual recurring request(" + requestNo + ") successfully created",
					"Create Recurring Request", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} catch (WrongValueException wve) {
			throw wve;
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
			holfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
		
		try
		{
			logger.info("Calling Recurring Auto Upload URL...");
			
			//Load Properties file
			String url = null;
			URLConnection conn = null;
			
			//retrieve properties bean
			Map recurringConfigProperties = (Map)SpringUtil.getBean("recurringConfigProperties");
			
			//retrieve properties value
			String recurringUploadAutoUrl = (String)recurringConfigProperties.get("recurring.upload.auto.url");

			RecurringConfig.load();
			if (recurringUploadAutoUrl != null)
				url = recurringUploadAutoUrl;
			else
				url = PROCESS_URL;
			
			if(requestNo != -1)
				url = url + "?reqNo=" + requestNo;
			
			URL processURL = new URL(url);
			logger.info(processURL);
	        conn = processURL.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	
//	        while ((inputLine = in.readLine()) != null) 
//	        	logger.info("Return message: " + inputLine);
	        in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() {

	}
}
