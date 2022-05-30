package com.cdgtaxi.ibs.interfaces.vitalmaster;

import java.net.URL;
import java.rmi.RemoteException;
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
import com.cdgtaxi.ibs.util.DateUtil;

public class IBSVitalMasterClient {

	private static final Logger logger = Logger.getLogger(IBSVitalMasterClient.class);
	private static Map<String, String> prop;
	private static GenericDao genericDao;
	public GenericDao getGenericDao() { return genericDao; }
	public void setGenericDao(GenericDao genericDao) { this.genericDao = genericDao; }
	
	@SuppressWarnings("unchecked")
	public static String sendMasterLogin(String loginId, String seqNoVital) throws VitalMasterLoginRegInterfaceException
	{
		prop = (Map<String, String>)SpringUtil.getBean("webserviceProperties");
		
		//retrieve properties value
		String wsdlUrl = prop.get("ws.vitalmaster.wsdlUrl");
		
        Service service=new Service();
		Call proxy = null;
        try{
        	proxy = (Call)service.createCall();
			proxy.setTargetEndpointAddress(new URL(wsdlUrl));
			proxy.setOperationName(new QName(prop.get("ws.vitalmaster.drv.operationName")));
			proxy.addParameter(new QName(prop.get("ws.vitalmaster.drv.parameter1")),Constants.XSD_STRING,ParameterMode.IN);
			proxy.addParameter(new QName(prop.get("ws.vitalmaster.drv.parameter2")),Constants.XSD_STRING,ParameterMode.IN);
			proxy.setReturnType(Constants.XSD_STRING);
        }
        catch(Exception ex){
        	logger.error(ex.getMessage(), ex);
        }
        
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    	logger.info("@@@@@ Starting IBS Vital Master Login Registration @@@");
    	logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    	IBSVitalMasterClient ibsVitalMasterClient = new IBSVitalMasterClient();
    	String msg = ibsVitalMasterClient.sendVitalMasterLoginReq(proxy, loginId, seqNoVital);
    	logger.info("Transfer program completed - " + msg);
    	return msg;

 	}
	
	private String sendVitalMasterLoginReq(Call proxy, String loginId, String seqNoVital) throws VitalMasterLoginRegInterfaceException
	{
    	String msg = "";
		Document sendDocument = DocumentHelper.createDocument();
		Element sendRoot = sendDocument.addElement("dataInterface");
		Element sendHeader = sendRoot.addElement("header");
		
		// Add the header content
		Element sendId = sendHeader.addElement("id");
		sendHeader.addElement("from").addText((String)prop.get("ws.vitalmaster.drv.from"));
		sendHeader.addElement("to").addText((String)prop.get("ws.vitalmaster.drv.to"));
		sendHeader.addElement("interfaceType").addText((String)prop.get("ws.vitalmaster.drv.interfaceType"));
		Element sendTime = sendHeader.addElement("sentTime");
		// To set the num of entries at a later stage
		Element sendNumOfEntries = sendHeader.addElement("numOfEntries");
		sendHeader.addElement("deliveryMode").addText("synchronously");
		sendHeader.addElement("concurrency").addText("true");
		
		Element sendContent = sendRoot.addElement("content");
		Element sendContentItem = sendContent.addElement("contentItem");
				
		Element tempElement;

		tempElement =  sendContentItem.addElement("field");
		tempElement.addElement("fieldName").addText("LOGIN_ID");
		tempElement.addElement("fieldValue").addText(loginId);

		// Set the no of records
//		sendId.addText("IBS_CP_MSTR_REG_" + getGenericDao().getNextSequenceNo(Sequence.CP_MSTR_REG_REQ_NO_SEQUENCE).toString());
		sendId.addText("IBS_CP_MSTR_REG_" + seqNoVital);
		sendNumOfEntries.setText(Integer.toString(1));
		sendTime.setText(DateUtil.convertTimestampToStr(DateUtil.getCurrentTimestamp(), DateUtil.TRIPS_DATE_FORMAT));
		
		String sendXML = sendDocument.asXML();
		logger.info("XML Sent>>> " + sendXML + "\n");
		String errorMsg = null;
		try
		{
			errorMsg = (String) proxy.invoke( new Object[] {prop.get("ws.vitalmaster.drv.interfaceType"), sendXML} );
		}
		catch (RemoteException e1) {
			e1.printStackTrace();
			throw new VitalMasterLoginRegInterfaceException("Vital Master Login Registration Interface Error - " + e1.getMessage());
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
	            if (status != null && !"".equals(status))
	            {
	            	if (NonConfigurableConstants.SUCCESS_FLAG.equalsIgnoreCase(status))
		            	// Successful, all records to be "S"
		            		successFlag = true;
		            	else
		            		throw new VitalMasterLoginRegInterfaceException("Vital Master Login Registration Interface Error - status is not successful");
	            }
	            else if (error != null && !"".equals(error))
	            {
	            	// error contentitem - print out the error in logger
	            	// Should add in new column to put this error message for investigation
	            	logger.info("Error Text " + error + "\n");
	            	throw new VitalMasterLoginRegInterfaceException("Vital Master Login Registration Interface Error - " + error);
	            }
	            msg = "Success";
	        }
	   	}
	   	catch (DocumentException de)
	   	{
	   		// received invalid xml message.
	   		// Most probably other message like General Error message or Inaccessible Error
	   		logger.info("Vital Master Login Registration Interface error - " + de.getMessage() + "\n");
	   		throw new VitalMasterLoginRegInterfaceException("Vital Master Login Registration Interface error - " + de.getMessage());
	   	}
	   	catch (Exception e)
	   	{
	   		// Catch exception
	   		logger.info("Vital Master Login Registration Interface error - " + e.getMessage() + "\n");
	   		throw new VitalMasterLoginRegInterfaceException("Vital Master Login Registration Interface error - " + e.getMessage());
	   	}	
		
		if (successFlag)
			return NonConfigurableConstants.BOOLEAN_YES;
		else
			throw new VitalMasterLoginRegInterfaceException("Vital Master Login Registration Interface error - success flag fail ");
	}

}
