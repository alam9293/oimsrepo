package com.cdgtaxi.ibs.interfaces.cnii;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
import com.cdgtaxi.ibs.common.model.IttbCniiAcctReq;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;


public class IBSCNIIUpdateAcctClient {

	private static final Logger logger = Logger.getLogger(IBSCNIIUpdateAcctClient.class);
	private static Map<String,String> prop;
	private static GenericDao genericDao;
	public GenericDao getGenericDao() { return genericDao; }
	public void setGenericDao(GenericDao genericDao) { this.genericDao = genericDao; }

	
	@SuppressWarnings("unchecked")
	public static String send(ArrayList<IttbCniiAcctReq> list) throws CniiInterfaceException{
		
		prop = (Map<String,String>)SpringUtil.getBean("webserviceProperties");
		
		//retrieve properties value
        String wsdlUrl = prop.get("ws.cnii.wsdlUrl");
        
        Service service=new Service();
		Call proxy = null;
        try{
        	proxy = (Call)service.createCall();
			proxy.setTargetEndpointAddress(new URL(wsdlUrl));
			proxy.setOperationName(new QName(prop.get("ws.cnii.operationName")));
			proxy.addParameter(new QName(prop.get("ws.cnii.parameter1")),Constants.XSD_STRING,ParameterMode.IN);
			proxy.addParameter(new QName(prop.get("ws.cnii.parameter2")),Constants.XSD_STRING,ParameterMode.IN);
			proxy.setReturnType(Constants.XSD_STRING);
        }
        catch(Exception ex){
        	logger.error(ex.getMessage(), ex);
        }
		
        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    	logger.info("@@@@@ Starting IBS CNII Update Account Transfer @@@@@@");
    	logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    	IBSCNIIUpdateAcctClient ibsCniiUpdateAcctClient = new IBSCNIIUpdateAcctClient();
    	String msg = ibsCniiUpdateAcctClient.sendCniiAcctReq(proxy, list);
    	logger.info("Transfer program completed - " + msg);
    	return msg;
	}
	
	private String sendCniiAcctReq(Call proxy, ArrayList<IttbCniiAcctReq> list) throws CniiInterfaceException
	{
    	String msg = "";
		Document sendDocument = DocumentHelper.createDocument();
		Element sendRoot = sendDocument.addElement("dataInterface");
		Element sendHeader = sendRoot.addElement("header");
		
		// Add the header content
		Element sendId = sendHeader.addElement("id");
		sendHeader.addElement("from").addText((String)prop.get("ws.cnii.from"));
		sendHeader.addElement("to").addText((String)prop.get("ws.cnii.to"));
		sendHeader.addElement("interfaceType").addText((String)prop.get("ws.cnii.interfaceType"));
		Element sendTime = sendHeader.addElement("sentTime");
		// To set the num of entries at a later stage
		Element sendNumOfEntries = sendHeader.addElement("numOfEntries");
		sendHeader.addElement("deliveryMode").addText("synchronously");
		sendHeader.addElement("concurrency").addText("false");
		
		Element sendContent = sendRoot.addElement("content");
		
		// Retrieve from request table
		int count = 0;
		boolean first = true;
		String requestID = null;
				
		if(!list.isEmpty())
		{
			logger.info("Retrieving Mapped for CNII");
			Iterator<IttbCniiAcctReq> iter = list.iterator();
			while (iter.hasNext())
			{
				IttbCniiAcctReq temp = iter.next();
				count++;
				// To change to new interface value
				// Loop the request table
				String validFlg = "Y";
				Element sendContentItem = sendContent.addElement("contentItem");
				if (first)
				{
					requestID = getGenericDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE).toString();
					
					first = false;
				}
				Element tempElement;
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("requestid");
				tempElement.addElement("fieldValue").addText(getGenericDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE).toString());
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("accountid");
				if (temp.getAccountId()!= null && !"".equals(temp.getAccountId()))
					tempElement.addElement("fieldValue").addText(temp.getAccountId());
				else
					tempElement.addElement("fieldValue").addText("");
				tempElement =  sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("accountCd");
				if (temp.getAccountCd()!= null && !"".equals(temp.getAccountCd()))
					tempElement.addElement("fieldValue").addText(temp.getAccountCd());
				else
					tempElement.addElement("fieldValue").addText("");
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("accountNm");
				if (temp.getAccountNm()!= null && !"".equals(temp.getAccountNm()))
					tempElement.addElement("fieldValue").addText(temp.getAccountNm());
				else
					tempElement.addElement("fieldValue").addText("");					
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("parentId");
				if (temp.getParentId()!= null && !"".equals(temp.getParentId()))
					tempElement.addElement("fieldValue").addText(temp.getParentId());
				else
					tempElement.addElement("fieldValue").addText("");
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("terminationDt");
				if (temp.getTerminateDt() != null)
				{
					tempElement.addElement("fieldValue").addText(DateUtil.convertDateToStr(temp.getTerminateDt(), DateUtil.TRIPS_DATE_FORMAT));					
				}
				else
				{
					tempElement.addElement("fieldValue").addText("");
				}
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("suspensionStartDt");
				if (temp.getSuspensionStartDt() != null)
				{
					tempElement.addElement("fieldValue").addText(DateUtil.convertDateToStr(temp.getSuspensionStartDt(), DateUtil.TRIPS_DATE_FORMAT));
				}
				else
				{
					tempElement.addElement("fieldValue").addText("");
				}
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("suspensionEndDt");
				if (temp.getSuspensionEndDt() != null)
				{
					tempElement.addElement("fieldValue").addText(DateUtil.convertDateToStr(temp.getSuspensionEndDt(), DateUtil.TRIPS_DATE_FORMAT));
				}
				else
				{
					tempElement.addElement("fieldValue").addText("");
				}
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("validFlg");

				tempElement.addElement("fieldValue").addText(NonConfigurableConstants.BOOLEAN_YES);
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("updateBy");

				tempElement.addElement("fieldValue").addText(NonConfigurableConstants.INTERFACE_USER);
				tempElement = sendContentItem.addElement("field");
				tempElement.addElement("fieldName").addText("updateDt");
				tempElement.addElement("fieldValue").addText(DateUtil.convertTimestampToStr(DateUtil.getCurrentTimestamp(),DateUtil.TRIPS_DATE_FORMAT));
				
				// Set status to be 'S' so that we can do a mass update later
				// Insert into a hashmap for retrieval later for updates
				//cniiAcctReqHashMap.put(temp.getReqId(), temp);
				
			}
			// Set the no of records
			sendId.addText("IBS_CNII_UPD_" + requestID );// + request no);
			sendNumOfEntries.setText(Integer.toString(count));
			sendTime.setText(DateUtil.convertTimestampToStr(DateUtil.getCurrentTimestamp(), DateUtil.TRIPS_DATE_FORMAT));

			String sendXML = sendDocument.asXML();
			logger.info("XML Sent>>> " + sendXML);
			
		   	String errorMsg = null;
			try {
				errorMsg = (String) proxy.invoke( new Object[] {prop.get("ws.cnii.interfaceType"), sendXML} );
				logger.info("XML Received>>> " + errorMsg + "\n");
			} 
			catch (RemoteException e1) {
				e1.printStackTrace();
				throw new CniiInterfaceException("CNII Interface Error - " + e1.getMessage());
			}
		   	
		   	// Check the error Message
		   	// If successful, then mark all as done.
		   	// If not successful, mark those returned as error
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
		        boolean successFlag = false;
		        // retrieve header
		        for ( Iterator i = header.elementIterator(); i.hasNext(); ) {
		            Element headeritem = (Element) i.next();
		            if ("numOfEntries".equals(headeritem.getName()))
		            	numOfEntries = headeritem.getTextTrim();
		        }       
		        
		        for ( Iterator i = content.elementIterator( "contentItem" ); i.hasNext(); ) 
		        {
		        	// Initialise the variable
		        	accountID = null;
		        	status = null;
		        	error = null;
		            Element contentItem = (Element) i.next();
		            for ( Iterator j = contentItem.elementIterator( "field" ); j.hasNext(); ){
		            	Element field = (Element) j.next();
		            	if ("status".equals(field.element("fieldName").getText()))
		            		status = field.element("fieldValue").getTextTrim();
		            	if ("accountId".equals(field.element("fieldName").getText()))
		            		accountID = field.element("fieldValue").getTextTrim();
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
		            		throw new CniiInterfaceException("CNII Interface Error - status is not success");
		            }
		            else if (accountID != null && !"".equals(accountID))
		            {
		            	// Retrieve object from Hash map
		            	logger.info("Error record " + accountID + "\n");
		            }
		            else if (error != null && !"".equals(error))
		            {
		            	// error contentitem - print out the error in logger
		            	// Should add in new column to put this error message for investigation
		            	logger.info("Error Text " + error + "\n");
		            	throw new CniiInterfaceException("CNII Interface Error - " + error);
		            }
		        }
		        msg = "Success";
		   	}
		   	catch (DocumentException de)
		   	{
		   		// received invalid xml message.
		   		// Most probably other message like General Error message or Inaccessible Error
		   		logger.info("CNII Interface error - " + de.getMessage() + "\n");
		   		LoggerUtil.printStackTrace(logger, de);
		   		throw new CniiInterfaceException("CNII Interface Error - " + de.getMessage());
		   	}
		   	catch (Exception e)
		   	{
		   		// Catch exception
		   		logger.info("CNII Interface error - " + e.getMessage() + "\n");
		   		LoggerUtil.printStackTrace(logger, e);
		   		throw new CniiInterfaceException("CNII Interface Error - " + e.getMessage());
		   	}
		}
		else
		{
			// No need to send if there is no record
			//sendNumOfEntries.setText(Integer.toString(count));
			msg = "No records to transfer";
		}
		
		return msg;
	}

}
