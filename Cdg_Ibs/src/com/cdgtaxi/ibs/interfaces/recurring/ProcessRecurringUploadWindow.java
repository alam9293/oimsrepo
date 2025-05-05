package com.cdgtaxi.ibs.interfaces.recurring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.model.forms.SearchRecurringRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;

public class ProcessRecurringUploadWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(ProcessRecurringUploadWindow.class);
	private static final long serialVersionUID = 1L;
	private String validIPs = null;
	
	
	public ProcessRecurringUploadWindow() throws InterruptedException{
		// adding all txn status
	
		Map tripsProperties = (Map)SpringUtil.getBean("tripsProperties");
		//retrieve properties value
		validIPs = (String)tripsProperties.get("trips.validhost");
	}

	public void refresh() throws InterruptedException {
		logger.debug("Process Recurring Upload Auto Window refresh()");
	}
	
	public void init(){
		String remoteIP = this.getHttpServletRequest().getRemoteAddr();
		String parseValue = (String)this.getHttpServletRequest().getParameter("reqNo");
		int requestNo = -1;
		
		if(parseValue != null)
			requestNo = Integer.parseInt(parseValue);

		logger.info("IP Address (Process Txn Window) " + remoteIP);

		if (validIPs.indexOf(remoteIP) == -1)
		{
			logger.error("Remote IP Address not found in the list of valid IP addresses");
			return;
		}
		
		
		SearchRecurringRequestForm form = new SearchRecurringRequestForm();
		if(requestNo == -1) {
			form.status = NonConfigurableConstants.RECURRING_REQUEST_STATUS_PENDING;
		}else{
			form.requestNo = requestNo;
		}
		//retrieve pending IttbRecurringReq
		List<IttbRecurringReq> ittbRecurringReqList = this.businessHelper.getAdminBusiness().searchIttbRecurringRequest(form);
		if(requestNo > 0 ){
			ittbRecurringReqList.get(0).setRecurringAutoManual("M");
		}
		if (ittbRecurringReqList == null)
		{
			// No record, do not do anything
			logger.info("No Recurring Requests found");
			return;
		}
		else
		{
			try
			{
				// To prevent infinite loop for unknown exception
				int noOfLoop = 0;
	
				int[] stats = new int[7];
				stats[0] = 0;
				stats[1] = 0;
				stats[2] = 0;
				stats[3] = 0;
				stats[4] = 0;
				stats[5] = 0;
				stats[6] = 0;
				
				// Update the entire objects to pending first
				Iterator<IttbRecurringReq> iter = ittbRecurringReqList.iterator();
				while (iter.hasNext())
				{
					IttbRecurringReq temp = iter.next();
					logger.info("Running Recurring Request ID "+ temp.getReqNo() + " with status " + temp.getStatus());
					temp.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_IN_PROGRESS);
					temp.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
					temp.setUpdatedDt(DateUtil.getCurrentTimestamp());
					this.businessHelper.getAdminBusiness().update(temp);
				}

				HashMap<Integer, String> map = new HashMap<Integer, String>();
				
				noOfLoop = 0;
				for( IttbRecurringReq ittbRecurringReq : ittbRecurringReqList )
				{
					String fileName = "";
					logger.info("PERFORMANCE CHECK: Before Search Recurring Dtl");
					fileName = this.businessHelper.getInvoiceBusiness().processRecurringDtl(ittbRecurringReq, stats);
					logger.info("PERFORMANCE CHECK: After Search Recurring Dtl");

					map.put(ittbRecurringReq.getReqNo(), fileName);
					
					noOfLoop++;
				}
				
				logger.info("Total No of Invoice : " + stats[0]);
				logger.info("Total No of Invoice Details Records: " + stats[5]);
				logger.info("Total No of Write to CSV: " + stats[1]);
				logger.info("Total No of 'No Token' Records: " + stats[2]);
				logger.info("Total No of Failed Records: " + stats[3]);
				logger.info("Total No of Closed Records: " + stats[6]);
				logger.info("Total No of Skipped Zero Dollar Outstanding Records: " + stats[4]);

				logger.info("PERFORMANCE CHECK: Before Updating Recurring Request List");
				// Update the entire objects
				Iterator<IttbRecurringReq> iter2 = ittbRecurringReqList.iterator();
				while (iter2.hasNext())
				{
					IttbRecurringReq temp = iter2.next();
					
					String fileName = (String)map.get(temp.getReqNo());
					
					if(fileName == null || fileName.trim().equals(""))
					{
						Map recurringConfigProperties = (Map) SpringUtil.getBean("recurringConfigProperties");
						String recurringFrom = (String) recurringConfigProperties.get("recurringEmailFrom");
						temp.setFileName(null);
						temp.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_ERROR_NOFILE);
						String msg = "No Recurring Csv file created.";
						logger.info(msg);
						EmailUtil.sendGenericNotificationError(ConfigurableConstants.EMAIL_TYPE_RECURRING_COMPLETED,recurringFrom, msg);
					}
					if(fileName.trim().equals("NoInfo"))
					{
						Map recurringConfigProperties = (Map) SpringUtil.getBean("recurringConfigProperties");
						String recurringFrom = (String) recurringConfigProperties.get("recurringEmailFrom");
						temp.setFileName(null);
						temp.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_ERROR);
						String msg = "Error: No Recurring Csv file created as there is no info due to no token tag to invoice account or no recurring flag";
						logger.info(msg);
						EmailUtil.sendGenericNotificationError(ConfigurableConstants.EMAIL_TYPE_RECURRING_COMPLETED,recurringFrom, msg);
					}
					else if(fileName.trim().equals("Error"))
					{
						Map recurringConfigProperties = (Map) SpringUtil.getBean("recurringConfigProperties");
						String recurringFrom = (String) recurringConfigProperties.get("recurringEmailFrom");
						temp.setFileName(null);
						temp.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_ERROR);
						String msg = "Error: No Recurring Csv file created as there are other errors."; 
						logger.info(msg);
						EmailUtil.sendGenericNotificationError(ConfigurableConstants.EMAIL_TYPE_RECURRING_COMPLETED,recurringFrom, msg);
					}
					else
					{
						temp.setFileName(fileName);
						temp.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_COMPLETED);
					}
					temp.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
					temp.setUpdatedDt(DateUtil.getCurrentTimestamp());
					this.businessHelper.getTxnBusiness().update(temp);
				}
				logger.info("PERFORMANCE CHECK: After Updating Recurring Request List");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.updateRequestAsErrorStatus(ittbRecurringReqList);
			}

			updateRequest(ittbRecurringReqList);
		}
	}
	
	private void updateRequestAsErrorStatus(List<IttbRecurringReq> ittbRecurringReqList){
		Iterator<IttbRecurringReq> iter = ittbRecurringReqList.iterator();
		while (iter.hasNext()){
			IttbRecurringReq temp = iter.next();
			temp.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_ERROR);
			temp.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
			temp.setUpdatedDt(DateUtil.getCurrentTimestamp());
			this.businessHelper.getTxnBusiness().update(temp);
		}
	}

	private void updateRequest(List<IttbRecurringReq> ittbRecurringReqList){
		Iterator<IttbRecurringReq> iter = ittbRecurringReqList.iterator();
		while (iter.hasNext()){
			IttbRecurringReq temp = iter.next();
			temp.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
			temp.setUpdatedDt(DateUtil.getCurrentTimestamp());
			this.businessHelper.getTxnBusiness().update(temp);
		}
	}
}
