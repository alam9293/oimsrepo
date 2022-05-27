package com.cdgtaxi.ibs.interfaces.fms;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.dao.GenericDao;
import com.cdgtaxi.ibs.common.dao.Sequence;
import com.cdgtaxi.ibs.util.DateUtil;

public class IBSFMSDriverVehicleAssocClient {

	private static final Logger logger = Logger.getLogger(IBSFMSDriverVehicleAssocClient.class);
	private static Map<String, String> prop;
	private static GenericDao genericDao;
	public GenericDao getGenericDao() { return genericDao; }
	public void setGenericDao(GenericDao genericDao) { this.genericDao = genericDao; }
	
	@SuppressWarnings("unchecked")
	public static String validateFMSDriverVehicleAssociation(String taxiNo, String nric, Timestamp tripStart, Timestamp tripEnd)
	{
		prop = (Map<String, String>)SpringUtil.getBean("webserviceProperties");
		
		//retrieve properties value
		String wsdlUrl = prop.get("ws.fms.wsdlUrl");
		
        Service service=new Service();
		Call proxy = null;
        try{
        	proxy = (Call)service.createCall();
			proxy.setTargetEndpointAddress(new URL(wsdlUrl));
			proxy.setOperationName(new QName(prop.get("ws.fms.drv.operationName")));
			proxy.addParameter(new QName(prop.get("ws.fms.drv.parameter1")),Constants.XSD_STRING,ParameterMode.IN);
			proxy.addParameter(new QName(prop.get("ws.fms.drv.parameter2")),Constants.XSD_STRING,ParameterMode.IN);
			proxy.setReturnType(Constants.XSD_STRING);
        }
        catch(Exception ex){
        	logger.error(ex.getMessage(), ex);
        }
        
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    	logger.info("@@@@@ Starting IBS FMS DRIVER VEHICLE ASSOC Transfer @");
    	logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    	IBSFMSDriverVehicleAssocClient ibsFMSDriverVehicleAssocClient = new IBSFMSDriverVehicleAssocClient();
    	String msg = ibsFMSDriverVehicleAssocClient.sendFMSDriverVehicleAssociationReq(proxy, taxiNo, nric, tripStart, tripEnd);
    	logger.info("Transfer program completed - " + msg);
    	return msg;

 	}
	
	private String sendFMSDriverVehicleAssociationReq(Call proxy, String taxiNo, String nric, Timestamp tripStart, Timestamp tripEnd)
	{
    	String msg = "";
		Document sendDocument = DocumentHelper.createDocument();
		Element sendRoot = sendDocument.addElement("dataInterface");
		Element sendHeader = sendRoot.addElement("header");
		
		// Add the header content
		Element sendId = sendHeader.addElement("id");
		sendHeader.addElement("from").addText((String)prop.get("ws.fms.drv.from"));
		sendHeader.addElement("to").addText((String)prop.get("ws.fms.drv.to"));
		sendHeader.addElement("interfaceType").addText((String)prop.get("ws.fms.drv.interfaceType"));
		Element sendTime = sendHeader.addElement("sentTime");
		// To set the num of entries at a later stage
		Element sendNumOfEntries = sendHeader.addElement("numOfEntries");
		sendHeader.addElement("deliveryMode").addText("synchronously");
		sendHeader.addElement("concurrency").addText("false");
		
		Element sendContent = sendRoot.addElement("content");
		Element sendContentItem = sendContent.addElement("contentItem");
				
		Element tempElement;

		tempElement =  sendContentItem.addElement("field");
		tempElement.addElement("fieldName").addText("driverIc");
		tempElement.addElement("fieldValue").addText(nric);
		tempElement = sendContentItem.addElement("field");
		tempElement.addElement("fieldName").addText("vehRegnNo");
		tempElement.addElement("fieldValue").addText(taxiNo);
		tempElement = sendContentItem.addElement("field");
		tempElement.addElement("fieldName").addText("trip_start_dt");
		if (tripStart != null && !"".equals(tripStart))
		{
			tempElement.addElement("fieldValue").addText(DateUtil.convertTimestampToStr(tripStart,DateUtil.TRIPS_DATE_FORMAT));					
		}
		else
		{
			tempElement.addElement("fieldValue").addText("");
		}
		
		tempElement = sendContentItem.addElement("field");
		tempElement.addElement("fieldName").addText("trip_end_dt");
		if (tripEnd != null && !"".equals(tripEnd))
		{
			tempElement.addElement("fieldValue").addText(DateUtil.convertTimestampToStr(tripEnd,DateUtil.TRIPS_DATE_FORMAT));					
		}
		else
		{
			tempElement.addElement("fieldValue").addText("");
		}

		// Set the no of records
		sendId.addText("IBS_FMS_DRV_VEH_" + getGenericDao().getNextSequenceNo(Sequence.FMS_REQ_NO_SEQUENCE).toString());
		sendNumOfEntries.setText(Integer.toString(1));
		sendTime.setText(DateUtil.convertTimestampToStr(DateUtil.getCurrentTimestamp(), DateUtil.TRIPS_DATE_FORMAT));
		
		String sendXML = sendDocument.asXML();
		logger.info("XML Sent>>> " + sendXML + "\n");
		String errorMsg = null;
		try
		{
			errorMsg = (String) proxy.invoke( new Object[] {prop.get("ws.fms.drv.interfaceType"), sendXML} );
		}
		catch (RemoteException e1) {
			e1.printStackTrace();
			return NonConfigurableConstants.FMS_DRIVER_VALIDATION_INTERFACE_ERROR;
		}
	   	logger.info("XML Received>>> " + errorMsg + "\n");

	   	// Check if we received the messages
	   	// If successful, then mark all as done.
	   	// If not successful, mark those returned as error
        boolean successFlag = false;
 
	   	try
	   	{
		   	Document document = DocumentHelper.parseText(errorMsg);
			Element root = document.getRootElement();
			Element header = null;
			Element content = null;
	        // iterate through child elements of root
			// for header and content
	        for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
	            Element element = (Element) i.next();
	            if ("header".equals(element.getName()))
	            {
	            	header = element;
	            }
	            if ("content".equals(element.getName()))
	            {
	            	content = element;
	            }
	        }
	
	        String numOfEntries = null;
	        String accountID = null, status = null, error = null;

	        // retrieve header
	        for ( Iterator i = header.elementIterator(); i.hasNext(); ) {
	            Element headeritem = (Element) i.next();
	            if ("numOfEntries".equals(headeritem.getName()))
	            	numOfEntries = headeritem.getTextTrim();
	        }
	        
	        for ( Iterator i = content.elementIterator( "contentItem" ); i.hasNext() && !successFlag; ) 
	        {
	        	// Initialise the variable
	        	status = null;
	        	error = null;
	            Element contentItem = (Element) i.next();
	            for ( Iterator j = contentItem.elementIterator( "field" ); j.hasNext(); ){
	            	Element field = (Element) j.next();
	            	if ("status".equals(field.element("fieldName").getText()))
	            		status = field.element("fieldValue").getTextTrim();
	            	if ("Error".equals(field.element("fieldName").getText()))
	            		error = field.element("fieldValue").getTextTrim();
	            }
	            // set the record to have error
	            if (status != null && !"".equals(status) && NonConfigurableConstants.TRUE_FLAG.equalsIgnoreCase(status))
	            {
	            	// Successful, all records are received successful. Do not update
	            	// to "S" yet as it is asynchronous
	            	successFlag = true;
	            	logger.info("Successfully Received by Server\n");
	            }
	            else if (error != null && !"".equals(error))
	            {
	            	// error contentitem - print out the error in logger
	            	// Should add in new column to put this error message for investigfation
	            	logger.info("Error record " + error);
	            	return NonConfigurableConstants.FMS_DRIVER_VALIDATION_INTERFACE_ERROR;
	            }
	        }
	   	}
	   	catch (DocumentException de)
	   	{
	   		// received invalid xml message.
	   		// Most probably other message like General Error message or Inaccessible Error
	   		logger.info("FMS Interface error - " + de.getMessage() + "\n");
	   		return NonConfigurableConstants.FMS_DRIVER_VALIDATION_INTERFACE_ERROR;
	   	}
	   	catch (Exception e)
	   	{
	   		// Catch exception
	   		logger.info("FMS Interface error - " + e.getMessage() + "\n");
	   		return NonConfigurableConstants.FMS_DRIVER_VALIDATION_INTERFACE_ERROR;
	   	}	
		
		if (successFlag)
			return NonConfigurableConstants.BOOLEAN_YES;
		else
			return NonConfigurableConstants.FMS_DRIVER_VALIDATION_INTERFACE_ERROR;
	}

}
