package com.cdgtaxi.ibs.interfaces.trips;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.IttbTripsTxn;
import com.cdgtaxi.ibs.common.model.IttbTripsTxnReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class ProcessTxnWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(ProcessTxnWindow.class);
	private static final long serialVersionUID = 1L;
	private String validIPs = null;
	private int loops = 100;
	private int records = 1000;
	
	
	public ProcessTxnWindow() throws InterruptedException{
		// adding all txn status
	
		Map tripsProperties = (Map)SpringUtil.getBean("tripsProperties");
		//retrieve properties value
		validIPs = (String)tripsProperties.get("trips.validhost");
		//retrieve properties value
		loops = Integer.parseInt((String)tripsProperties.get("trips.loops"));
		records = Integer.parseInt((String)tripsProperties.get("trips.records"));
	}

	public void refresh() throws InterruptedException {
		logger.debug("Process Txn Window refresh()");
	}
	
	public void init(){
		String remoteIP = this.getHttpServletRequest().getRemoteAddr();
		logger.info("IP Address (Process Txn Window) " + remoteIP);
		if (validIPs.indexOf(remoteIP) == -1)
		{
			logger.error("Remote IP Address not found in the list of valid IP addresses");
			return;
		}
		
		List<IttbTripsTxnReq> ittbTripsTxnReqList = this.businessHelper.getTxnBusiness().getTripsReqs();
		if (ittbTripsTxnReqList == null)
		{
			// No record, do not do anything
			logger.info("No job found");
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
				
				// Update the entire objects to pending first
				Iterator<IttbTripsTxnReq> iter = ittbTripsTxnReqList.iterator();
				while (iter.hasNext())
				{
					IttbTripsTxnReq temp = iter.next();
					logger.info("Running Request ID "+ temp.getReqNo() + " with status " + temp.getStatus());
					temp.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_IN_PROGRESS);
					temp.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
					temp.setUpdatedDt(DateUtil.getCurrentTimestamp());
					this.businessHelper.getTxnBusiness().update(temp);
				}
	
				List<IttbTripsTxn> tripsList = null;
				noOfLoop = 0;
				
				logger.info("PERFORMANCE CHECK: Before Search Txn View");
				tripsList = this.businessHelper.getTxnBusiness().searchTxnView(records);
				logger.info("PERFORMANCE CHECK: After Search Txn View");
				
				while (tripsList!= null && noOfLoop < loops)
				{
					logger.info("PERFORMANCE CHECK: Before sendTRIPSReq");
					// This is in windows so that it will be committed after a certain number to prevent slowness in processing
					this.businessHelper.getTxnBusiness().sendTRIPSReq(tripsList, stats);
					logger.info("PERFORMANCE CHECK: After sendTRIPSReq");
					
					tripsList.clear();
					
					logger.info("PERFORMANCE CHECK: Before Search Txn View");
					tripsList = this.businessHelper.getTxnBusiness().searchTxnView(records);
					logger.info("PERFORMANCE CHECK: After Search Txn View");
					
					noOfLoop++;
				}
				
				logger.info("Total No of Records retrieved: " + stats[0]);
				logger.info("Total No of Successful Trips: " + stats[1]);
				logger.info("Total No of Failed Trips: " + stats[2]);
				logger.info("Total No of Ignored Trips: " + stats[3]);
				logger.info("Total No of Concurrent Trips: " + stats[4]);
				
				logger.info("PERFORMANCE CHECK: Before Updating Error List");
				// Update the entire objects
				Iterator<IttbTripsTxnReq> iter2 = ittbTripsTxnReqList.iterator();
				while (iter2.hasNext())
				{
					IttbTripsTxnReq temp = iter2.next();
					temp.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_COMPLETED);
					temp.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
					temp.setUpdatedDt(DateUtil.getCurrentTimestamp());
					this.businessHelper.getTxnBusiness().update(temp);
				}
				logger.info("PERFORMANCE CHECK: After Updating Error List");
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
				this.updateRequestAsErrorStatus(ittbTripsTxnReqList);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.updateRequestAsErrorStatus(ittbTripsTxnReqList);
			}
		}
	}
	
	private void updateRequestAsErrorStatus(List<IttbTripsTxnReq> ittbTripsTxnReqList){
		Iterator<IttbTripsTxnReq> iter = ittbTripsTxnReqList.iterator();
		while (iter.hasNext()){
			IttbTripsTxnReq temp = iter.next();
			temp.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_ERROR);
			temp.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
			temp.setUpdatedDt(DateUtil.getCurrentTimestamp());
			this.businessHelper.getTxnBusiness().update(temp);
		}
	}
}
