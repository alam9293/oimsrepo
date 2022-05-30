package com.cdgtaxi.ibs.interfaces.cdge;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.interfaces.cdge.exception.ExpectedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

import sg.com.cdge.webservices.ServiceLocator;
import sg.com.cdge.webservices.ServiceSoap;

public class CdgeInterfaceClient {

	private static final Logger logger = Logger.getLogger(CdgeInterfaceClient.class);
	private static final String FORMAT_dd_MM_yyyy_HH_mm_ss = "dd/MM/yyyy HH:mm:ss";
	private static final String FORMAT_dd_MM_yyyy = "dd/MM/yyyy";

	// Interface Types
	private static final String IBS_CDGE_VOUCHER_SYNC = "IBS_CDGE_VOUCHER_SYNC";
	private static final String IBS_CDGE_VOUCHER_AMEND = "IBS_CDGE_VOUCHER_AMEND";

	@SuppressWarnings("rawtypes")
	private static Map prop;

	private static final String SYSTEM_NAME_FROM = "ws.cdge.from";
	private static final String SYSTEM_NAME_TO = "ws.cdge.to";
	
	/**
	 * @return Returns a failure reason else null or empty string to indicate success
	 */
	public static String syncVoucher(Integer runningNo, String serialNoStart, String serialNoEnd,
			String voucherStatus, String voucherValue, Date redeemExpiryDate) throws Exception {

		prop = (Map)SpringUtil.getBean("webserviceProperties");
		
		// Fields pertaining to this interface type
		final String FIELD_NAME_SERIAL_NO_START = "serial_no_start";
		final String FIELD_NAME_SERIAL_NO_END = "serial_no_end";
		final String FIELD_NAME_VOUCHER_STATUS = "voucher_status";
		final String FIELD_NAME_VOUCHER_VALUE = "voucher_value";
		final String FIELD_NAME_REDEEM_EXPIRY_DATE = "redeem_expiry_date";

		Document document = DocumentHelper.createDocument();

		// Root
		Element dataInterfaceEle = document.addElement("dataInterface");

		// Header
		Element headerEle = dataInterfaceEle.addElement("header");
		String messageId = "IBS_CDGE_" + StringUtil.appendLeft(runningNo.toString(), 7, "0");
		headerEle.addElement("id").addText(messageId);
		headerEle.addElement("from").addText((String) prop.get(SYSTEM_NAME_FROM));
		headerEle.addElement("to").addText((String) prop.get(SYSTEM_NAME_TO));
		headerEle.addElement("interfaceType").addText(IBS_CDGE_VOUCHER_SYNC);
		headerEle.addElement("sentTime").addText(
				DateUtil.convertDateToStr(new Date(), FORMAT_dd_MM_yyyy_HH_mm_ss));
		headerEle.addElement("numOfEntries").addText("1");
		headerEle.addElement("deliveryMode").addText("synchronously");
		headerEle.addElement("concurrency").addText("false");

		// Content
		Element contentEle = dataInterfaceEle.addElement("content");

		// Content Item
		Element contentItemEle = contentEle.addElement("contentItem");

		Element field1Ele = contentItemEle.addElement("field");
		field1Ele.addElement("fieldName").addText(FIELD_NAME_SERIAL_NO_START);
		field1Ele.addElement("fieldValue").addText(serialNoStart);

		Element field2Ele = contentItemEle.addElement("field");
		field2Ele.addElement("fieldName").addText(FIELD_NAME_SERIAL_NO_END);
		field2Ele.addElement("fieldValue").addText(serialNoEnd);

		Element field3Ele = contentItemEle.addElement("field");
		field3Ele.addElement("fieldName").addText(FIELD_NAME_VOUCHER_STATUS);
		field3Ele.addElement("fieldValue").addText(voucherStatus);

		Element field4Ele = contentItemEle.addElement("field");
		field4Ele.addElement("fieldName").addText(FIELD_NAME_VOUCHER_VALUE);
		field4Ele.addElement("fieldValue").addText(voucherValue);

		Element field5Ele = contentItemEle.addElement("field");
		field5Ele.addElement("fieldName").addText(FIELD_NAME_REDEEM_EXPIRY_DATE);
		field5Ele.addElement("fieldValue").addText(
				DateUtil.convertDateToStr(redeemExpiryDate, FORMAT_dd_MM_yyyy));

		logger.info("[syncVoucher] SENDING: \n"+document.asXML());
		
		String wsdlAddress = (String)prop.get("ws.cdge.wsdlUrl");
	    ServiceLocator loc = new ServiceLocator();
	    loc.setServiceSoapEndpointAddress(wsdlAddress);
	    loc.setServiceSoap12EndpointAddress(wsdlAddress);
	    ServiceSoap port = loc.getServiceSoap();
	    
	    String replyMsg = port.storeInfo((String)prop.get("ws.cdge.interfaceType.voucherSync"), document.asXML());

		// Process the reply message
		return processReplyMessage(messageId, replyMsg);
	}

	/**
	 * @param expectedMessageId
	 * @param replyMsg
	 * @return a value means reply message implies a failure with reason and the reason is the value
	 *         returned
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static String processReplyMessage(String expectedMessageId, String replyMsg) throws Exception {
		logger.info("REPLY: \n" + replyMsg);

		// Fields pertaining to this reply document
		final String FIELD_NAME_REASON = "reason";

		Document document = DocumentHelper.parseText(replyMsg);

		Element dataInterfaceEle = document.getRootElement();
		Element headerEle = dataInterfaceEle.element("header");
		Element idEle = headerEle.element("id");

		if (!expectedMessageId.equals(idEle.getText()))
			throw new ExpectedException("Expected reply message id to be " + expectedMessageId
					+ " but gotten " + idEle.getText());

		Element contentEle = dataInterfaceEle.element("content");
		Element contentItemEle = contentEle.element("contentItem");

		List<Element> fieldEles = contentItemEle.elements("field");
		for (Element field : fieldEles) {
			Element fieldNameEle = field.element("fieldName");
			Element fieldValueEle = field.element("fieldValue");

			if (fieldNameEle.getText().equals(FIELD_NAME_REASON))
				return fieldValueEle.getText();
		}

		throw new ExpectedException("Unable to determine the success or failure from CDGE due to invalid message reply.");
	}

	/**
	 * @return Returns a failure reason else null or empty string to indicate success
	 */
	public static String amendVoucher(Integer runningNo, String oldSerialNo, String newSerialNo)
			throws Exception {

		// Fields pertaining to this interface type
		final String FIELD_NAME_OLD_SERIAL_NO = "old_serial_no";
		final String FIELD_NAME_NEW_SERIAL_NO = "new_serial_no";
		
		Document document = DocumentHelper.createDocument();

		// Root
		Element dataInterfaceEle = document.addElement("dataInterface");

		// Header
		Element headerEle = dataInterfaceEle.addElement("header");
		String messageId = "IBS_CDGE_" + StringUtil.appendLeft(runningNo.toString(), 7, "0");
		headerEle.addElement("id").addText(messageId);
		headerEle.addElement("from").addText((String) prop.get(SYSTEM_NAME_FROM));
		headerEle.addElement("to").addText((String) prop.get(SYSTEM_NAME_TO));
		headerEle.addElement("interfaceType").addText(IBS_CDGE_VOUCHER_AMEND);
		headerEle.addElement("sentTime").addText(
				DateUtil.convertDateToStr(new Date(), FORMAT_dd_MM_yyyy_HH_mm_ss));
		headerEle.addElement("numOfEntries").addText("1");
		headerEle.addElement("deliveryMode").addText("synchronously");
		headerEle.addElement("concurrency").addText("false");

		// Content
		Element contentEle = dataInterfaceEle.addElement("content");

		// Content Item
		Element contentItemEle = contentEle.addElement("contentItem");

		Element field1Ele = contentItemEle.addElement("field");
		field1Ele.addElement("fieldName").addText(FIELD_NAME_OLD_SERIAL_NO);
		field1Ele.addElement("fieldValue").addText(oldSerialNo);

		Element field2Ele = contentItemEle.addElement("field");
		field2Ele.addElement("fieldName").addText(FIELD_NAME_NEW_SERIAL_NO);
		field2Ele.addElement("fieldValue").addText(newSerialNo);

		logger.info("[amendVoucher] SENDING: \n"+document.asXML());
		
		String wsdlAddress = (String)prop.get("ws.cdge.wsdlUrl");
		ServiceLocator loc = new ServiceLocator();
		loc.setServiceSoapEndpointAddress(wsdlAddress);
		loc.setServiceSoap12EndpointAddress(wsdlAddress);
		ServiceSoap port = loc.getServiceSoap();
		    
		String replyMsg = port.storeInfo((String)prop.get("ws.cdge.interfaceType.voucherAmend"), document.asXML());
		    
		// Process the reply message
		return processReplyMessage(messageId, replyMsg);
	}
}
