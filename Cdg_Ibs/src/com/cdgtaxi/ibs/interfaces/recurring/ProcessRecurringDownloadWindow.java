package com.cdgtaxi.ibs.interfaces.recurring;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.model.forms.SearchRecurringRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class ProcessRecurringDownloadWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(ProcessRecurringDownloadWindow.class);
	private static final long serialVersionUID = 1L;
	private String validIPs = null;
	
	
	public ProcessRecurringDownloadWindow() throws InterruptedException{
		// adding all txn status
	
		Map tripsProperties = (Map)SpringUtil.getBean("tripsProperties");
		//retrieve properties value
		validIPs = (String)tripsProperties.get("trips.validhost");
	}

	public void refresh() throws InterruptedException {
		logger.debug("Process Recurring Download Auto Window refresh()");
	}
	
	public void init(){
		String remoteIP = this.getHttpServletRequest().getRemoteAddr();
		logger.info("IP Address (Process Txn Window) " + remoteIP);
		if (validIPs.indexOf(remoteIP) == -1)
		{
			logger.error("Remote IP Address not found in the list of valid IP addresses");
			return;
		}
		
		
		SearchRecurringRequestForm form = new SearchRecurringRequestForm();
		form.status = NonConfigurableConstants.RECURRING_REQUEST_STATUS_PENDING;
		List<IttbRecurringReq> ittbRecurringReqList = this.businessHelper.getAdminBusiness().searchIttbRecurringRequest(form);
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
	
				int[] stats = new int[5];
				stats[0] = 0;
				stats[1] = 0;
				stats[2] = 0;
				stats[3] = 0;
				stats[4] = 0;
				
				noOfLoop = 0;
				logger.info("PERFORMANCE CHECK: Before Download Recurring Dtl");
				this.businessHelper.getInvoiceBusiness().downloadAndProcessRecurringDtl(stats);
				logger.info("PERFORMANCE CHECK: After Download Recurring Dtl");
				
				
				logger.info("Total No of Records: " + stats[0]);
				logger.info("Total No of Process Successfully: " + stats[1]);
				logger.info("Total No of Failed Records: " + stats[2]);

				logger.info("Complete Download Recurring");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.updateRequestAsErrorStatus(ittbRecurringReqList);
			}
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
}
