package com.cdgtaxi.ibs.interfaces.trips;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.IttbSetlTxnReq;
import com.cdgtaxi.ibs.common.model.IttbTripsTxnReq;
import com.cdgtaxi.ibs.common.model.VwIntfSetlForIb;
import com.cdgtaxi.ibs.common.model.VwIntfTripsForIb;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;

public class RetrieveTxnWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(RetrieveTxnWindow.class);
	private static final long serialVersionUID = 1L;
	private String validIPs = null;
	private int loops = 100;
	private int records = 1000;
	
	@SuppressWarnings("unchecked")
	public RetrieveTxnWindow() throws InterruptedException{
		// adding all txn status
	
		Map tripsProperties = (Map)SpringUtil.getBean("tripsProperties");
		//retrieve properties value
		validIPs = (String)tripsProperties.get("trips.validhost");
		loops = Integer.parseInt((String)tripsProperties.get("trips.loops"));
		records = Integer.parseInt((String)tripsProperties.get("trips.records"));
	}

	public void refresh() throws InterruptedException {
		logger.debug("App Txn Window refresh()");
	}
	
	@SuppressWarnings("unchecked")
	public void init(){
		
		String remoteIP = this.getHttpServletRequest().getRemoteAddr();
		String retrievalPK = this.getHttpServletRequest().getParameter("retrieve_pk");
		logger.info("IP Address (Retrieve Txn Window) " + remoteIP);

		//toDo Norman to remove ayden
		if(retrievalPK == null){
			retrievalPK = "2";
		}


		if (validIPs.indexOf(remoteIP) == -1)
		{
			logger.error("Remote IP Address not found in the list of valid IP addresses");
			return;
		}
		if (retrievalPK == null || "".equals(retrievalPK))
		{
			// Check if retrieval PK must be an integer
			try
			{
				Integer.parseInt(retrievalPK);
			}
			catch (Exception e)
			{
				logger.error("Retrieval PK must be an integer");
				return;
			}
			logger.error("Retrieval PK is empty");
			return;
		}

		// To prevent infinite loop for unknown exception
		int noOfLoop = 0;
		int count = 0;
		
		int noOfSetlLoop = 0;
		int countSetl = 0;
		
		int[] tripStats = new int[13];
		tripStats[0] = 0; //Successful Records retrieved from Trips
		tripStats[1] = 0; //Ignored Records retrieved from Trips
		
		tripStats[2] = 0; //Successful CRCA Records retrieved from SETL
		tripStats[3] = 0; //Successful NETS Records retrieved from SETL
		tripStats[4] = 0; //Successful NFP 	Records retrieved from SETL
		tripStats[5] = 0; //Successful CUP 	Records retrieved from SETL
		tripStats[6] = 0; //Successful EPIN Records retrieved from SETL
		tripStats[7] = 0; //Successful EZL 	Records retrieved from SETL
		tripStats[8] = 0; //Successful DASH 	Records retrieved from SETL
		tripStats[9] = 0; //Ignored Records retrieved from SETL
		tripStats[10] = 0; //Successful KARHOO 	Records retrieved from SETL
		tripStats[11] = 0; //Successful Lazada 	Records retrieved from SETL
		tripStats[12] = 0; //Successful "Other new Non Billable Txn" Records retrieved from SETL.
		
		boolean error = false;
		
		try{
			List<VwIntfTripsForIb> requestList = null;
			//toDo: comment below to test in SIT
			this.businessHelper.getTxnBusiness().started(Integer.parseInt(retrievalPK));
			
			// To retrieve all trips and populate to the error tables
			boolean gotRecords = false;
			requestList = this.businessHelper.getTxnBusiness().searchTRIPSview(records);
			while (requestList!= null && noOfLoop < loops)
			{
				gotRecords = true;
				count += this.businessHelper.getTxnBusiness().retrieveFromTRIPS(requestList, tripStats);
				requestList.clear();
				requestList = this.businessHelper.getTxnBusiness().searchTRIPSview(records);
				noOfLoop++;
			}
			
			if(gotRecords){
				IttbTripsTxnReq requestExample = new IttbTripsTxnReq();
				requestExample.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_PENDING);
				List results = this.businessHelper.getGenericBusiness().getByExample(requestExample);
				
				IttbTripsTxnReq requestExample2 = new IttbTripsTxnReq();
				requestExample2.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_IN_PROGRESS);
				List results2 = this.businessHelper.getGenericBusiness().getByExample(requestExample2);
				
				if(results.isEmpty() && results2.isEmpty()){
					// Insert into the DB
					IttbTripsTxnReq ittbTripsTxnReq = new IttbTripsTxnReq();
					ittbTripsTxnReq.setCreatedBy(NonConfigurableConstants.INTERFACE_USER);
					ittbTripsTxnReq.setCreatedDt(DateUtil.getCurrentTimestamp());
					ittbTripsTxnReq.setRequestDate(DateUtil.getCurrentDate());
					ittbTripsTxnReq.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_PENDING);
					this.businessHelper.getTxnBusiness().save(ittbTripsTxnReq);
				}
			}
		}
		catch (IOException ioe)
		{
			LoggerUtil.printStackTrace(logger, ioe);
			error = true;
		}
		catch (Exception e)
		{
			LoggerUtil.printStackTrace(logger, e);
			error = true;
		}
		
		try{
			// To retrieve all setl txns
			boolean gotRecords = false;
			List<VwIntfSetlForIb> setlList = null;
			setlList = this.businessHelper.getTxnBusiness().searchSETLview(records);
			while (setlList!= null && noOfSetlLoop < loops)
			{
				gotRecords = true;
				countSetl += this.businessHelper.getTxnBusiness().retrieveFromSETL(setlList, tripStats);
				setlList.clear();
				setlList = this.businessHelper.getTxnBusiness().searchSETLview(records);
				noOfSetlLoop++;
			}
			
			if(gotRecords){
				IttbSetlTxnReq requestExample = new IttbSetlTxnReq();
				requestExample.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_PENDING);
				List results = this.businessHelper.getGenericBusiness().getByExample(requestExample);
				
				IttbSetlTxnReq requestExample2 = new IttbSetlTxnReq();
				requestExample2.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_IN_PROGRESS);
				List results2 = this.businessHelper.getGenericBusiness().getByExample(requestExample2);
				
				if(results.isEmpty() && results2.isEmpty()){
					// Insert into the DB
					IttbSetlTxnReq ittbSetlTxnReq = new IttbSetlTxnReq();
					ittbSetlTxnReq.setCreatedBy(NonConfigurableConstants.INTERFACE_USER);
					ittbSetlTxnReq.setCreatedDt(DateUtil.getCurrentTimestamp());
					ittbSetlTxnReq.setRequestDate(DateUtil.getCurrentDate());
					ittbSetlTxnReq.setStatus(NonConfigurableConstants.TRIPS_REQUEST_STATUS_PENDING);
					this.businessHelper.getTxnBusiness().save(ittbSetlTxnReq);
				}
			}
		}
		catch (IOException ioe)
		{
			LoggerUtil.printStackTrace(logger, ioe);
			error = true;
		}
		catch (Exception e)
		{
			LoggerUtil.printStackTrace(logger, e);
			error = true;
		}
		
		//toDo: comment below for SIT
		try{
			if(error == false)				
				this.businessHelper.getTxnBusiness().ended(Integer.parseInt(retrievalPK));
		}
		catch(Exception e){
			LoggerUtil.printStackTrace(logger, e);
		}
		
		logger.info("Total No of Successful Records retrieved from Trips: " + tripStats[0]);
		logger.info("Total No of Ignored Records retrieved from Trips: " + tripStats[1]);
		logger.info("Total No of Records retrieved from Trips: " + count);
		
		logger.info("Total No of Successful CRCA Records retrieved from SETL: " + tripStats[2]);
		logger.info("Total No of Successful NETS Records retrieved from SETL: " + tripStats[3]);
		logger.info("Total No of Successful NFP Records retrieved from SETL: " + tripStats[4]);
		logger.info("Total No of Successful CUP Records retrieved from SETL: " + tripStats[5]);
		logger.info("Total No of Successful EPIN Records retrieved from SETL: " + tripStats[6]);
		logger.info("Total No of Successful EZL Records retrieved from SETL: " + tripStats[7]);
		logger.info("Total No of Successful DASH Records retrieved from SETL: " + tripStats[8]);
		logger.info("Total No of Successful KARHOO Records retrieved from SETL: " + tripStats[10]);
		logger.info("Total No of Successful Lazada Records retrieved from SETL: " + tripStats[11]);
		logger.info("Total No of Successful 'Other New Non Billable' Records retrieved from SETL: "+tripStats[12]);
		
		logger.info("Total No of Ignored Records retrieved from SETL: " + tripStats[9]);
		logger.info("Total No of Records retrieved from SETL: " + countSetl);
		
		logger.info("Retrieving from TRIPS Interface Completed");
	}
	
}
