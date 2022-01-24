package com.webapp.ims.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.WReturn_CUSID_STATUS;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;
import com.webapp.ims.webservices.SoapConsumeEx;

@Service
public class WReturnCUSIDSTATUSBeanImpl implements WReturnCUSIDSTATUSBeanService {

	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;

	@Override
	public String createApplicantDetailObject(ApplicantDetails applicantDetails)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String xmlData = null;
		WReturn_CUSID_STATUS wReturn_CUSID_STATUS = new WReturn_CUSID_STATUS();
		try {
			wReturn_CUSID_STATUS.setControlID(applicantDetails.getControlId());
			wReturn_CUSID_STATUS.setUnitID(applicantDetails.getUnitId());
			wReturn_CUSID_STATUS.setServiceID(applicantDetails.getServiceId());
			wReturn_CUSID_STATUS.setProcessIndustryID(applicantDetails.getProcessIndustryId());
			wReturn_CUSID_STATUS.setApplicationID(applicantDetails.getApplicationId());
			wReturn_CUSID_STATUS.setStatus_Code(applicantDetails.getStatusCode());
			wReturn_CUSID_STATUS.setRemarks(applicantDetails.getRemarks());
			wReturn_CUSID_STATUS.setPendancy_level(applicantDetails.getPendancyLevel());
			wReturn_CUSID_STATUS.setFee_Amount(applicantDetails.getFeeAmount());
			wReturn_CUSID_STATUS.setFee_Status(applicantDetails.getFeeStatus());
			wReturn_CUSID_STATUS.setTransaction_ID(applicantDetails.getTransactionId());
			wReturn_CUSID_STATUS.setTransaction_Date(applicantDetails.getTransactionDate());
			wReturn_CUSID_STATUS.setTransaction_Date_Time(applicantDetails.getTransactionDateTime());
			wReturn_CUSID_STATUS.setNOC_Certificate_Number(applicantDetails.getnOCCertificateNumber());
			wReturn_CUSID_STATUS.setNOC_URL(applicantDetails.getnOCURL());
			wReturn_CUSID_STATUS.setISNOC_URL_ActiveYesNO(applicantDetails.getiSNOCURLActiveYesNO());
			wReturn_CUSID_STATUS.setRequest_ID(applicantDetails.getRequestId());
			wReturn_CUSID_STATUS.setPasssalt(applicantDetails.getPasssalt());
			wReturn_CUSID_STATUS.setObjection_Rejection_Code(applicantDetails.getObjectionRejectionCode());
			wReturn_CUSID_STATUS.setIs_Certificate_Valid_Life_Time(applicantDetails.getIsCertificateValidLifeTime());
			wReturn_CUSID_STATUS.setCertificate_EXP_Date_DDMMYYYY(applicantDetails.getCertificateEXPDateDDMMYYYY());
			wReturn_CUSID_STATUS.setD1(applicantDetails.getD1());
			wReturn_CUSID_STATUS.setD2(applicantDetails.getD2());
			wReturn_CUSID_STATUS.setD3(applicantDetails.getD3());
			wReturn_CUSID_STATUS.setD4(applicantDetails.getD4());
			wReturn_CUSID_STATUS.setD5(applicantDetails.getD5());
			wReturn_CUSID_STATUS.setD6(applicantDetails.getD6());
			wReturn_CUSID_STATUS.setD7(applicantDetails.getD7());

			// xmlData = marshalObject(wReturn_CUSID_STATUS);

			return xmlData;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		// SoapConsumeEx soapConsumeEx = new SoapConsumeEx();
		// soapConsumeEx.WReturn_CUSID_STATUS(wReturn_CUSID_STATUS );

		// Map<String, Object> map = ConvertObjectToMap(wReturn_CUSID_STATUS);
		return xmlData;
	}

	public static Map<String, Object> ConvertObjectToMap(Object obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> pomclass = obj.getClass();
		pomclass = obj.getClass();
		Method[] methods = obj.getClass().getMethods();

		Map<String, Object> map = new HashMap<String, Object>();
		for (Method m : methods) {
			if (m.getName().startsWith("get") && !m.getName().startsWith("getClass")) {
				Object value = (Object) m.invoke(obj);
				map.put(m.getName().substring(3), (Object) value);
			}
		}
		return map;
	}

	public String marshalObject(WReturn_CUSID_STATUS wReturn_CUSID_STATUS) {
		try {
			SoapConsumeEx soapConsumeEx = new SoapConsumeEx();
			// creating the JAXB context
			JAXBContext jContext = JAXBContext.newInstance(WReturn_CUSID_STATUS.class);
			// creating the marshaller object
			Marshaller marshallObj = jContext.createMarshaller();
			// setting the property to show xml format output
			marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// setting the values in POJO class
			// wReturnCUSIDSTATUSBean = new Student(“abhishek”, 1163, “hadoop”);
			// calling the marshall method
			StringWriter sw = new StringWriter();
			marshallObj.marshal(wReturn_CUSID_STATUS, new FileOutputStream("f:\\student.xml"));
			marshallObj.marshal(wReturn_CUSID_STATUS, sw);
			String xmlData = sw.toString();

			Source source = new StreamSource(new StringReader(sw.toString()));
			Writer resultWriter = new StringWriter();
			Result res = new StreamResult(resultWriter);

			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, res);

			WReturn_CUSID_STATUS(xmlData);

			return xmlData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void unMarshalObject(WReturn_CUSID_STATUS Return_CUSID_STATUS) {
		try {
			// getting the xml file to read
			File file = new File("/home/knoldus/Desktop/student.xml");
			// creating the JAXB context
			JAXBContext jContext = JAXBContext.newInstance(WReturn_CUSID_STATUS.class);
			// creating the unmarshall object
			Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
			// calling the unmarshall method
			Return_CUSID_STATUS = (WReturn_CUSID_STATUS) unmarshallerObj.unmarshal(file);

			// System.out.println(student.getName()+” “+student.getId()+”
			// “+student.getSubject());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> WReturn_CUSID_STATUS(String xmlData) {
		String responseString = "";
		String outputString = "";

		String wsURL = "http://72.167.225.87/testing_nmswp/upswp_niveshmitraservices.asmx";
		// String wsURL="http://niveshmitra.up.nic.in/upswp_niveshmitraservices.asmx";

		System.out.println("xmlData-->>>>>>>>>>>>>>>>>>>" + xmlData);
		URLConnection connection;
		// String niveshResponce = "";
		Map<String, String> response = new HashMap<String, String>();
		try {
			String niveshSoapreq = "";

			niveshSoapreq = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"  xmlns=\"http://tempuri.org/\">\r\n"
					+ "   <soap:Header/>\r\n" + "   <soap:Body>\r\n" + xmlData + " </soap:Body>\r\n"
					+ "</soap:Envelope>";

			URL url = new URL(wsURL);

			connection = url.openConnection();

			HttpURLConnection httpConn = (HttpURLConnection) connection;
			httpConn.setRequestProperty("Content-type", "text/xml; charset=utf-8");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			out.write(niveshSoapreq.getBytes());
			out.close();

			String status = "";
			// Read the response.
			InputStreamReader isr = null;
			if (httpConn.getResponseCode() == 200) {
				isr = new InputStreamReader(httpConn.getInputStream());

				status = "SUCCESS";
			} else {

				isr = new InputStreamReader(httpConn.getErrorStream());
			}
			BufferedReader in = new BufferedReader(isr);

			// Write the SOAP message response to a String.
			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
				System.out.println("outputString   ---" + outputString);
			}

			return response;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public Map<String, String> WReturn_CUSID_STATUS(ApplicantDetails applicantDetails) {
		String responseString = "";
		String outputString = "";

		String wsURL = "http://72.167.225.87/testing_nmswp/upswp_niveshmitraservices.asmx";
		// String wsURL="http://niveshmitra.up.nic.in/upswp_niveshmitraservices.asmx";

		URLConnection connection;
		// String niveshResponce = "";
		Map<String, String> response = new HashMap<String, String>();
		try {
			String niveshSoapreq = "";

			niveshSoapreq = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"  xmlns=\"http://tempuri.org/\">\r\n"
					+ "   <soap:Header/>\r\n" + "   <soap:Body>\r\n" + "     " + " <WReturn_CUSID_STATUS>\r\n"
					+ "         <ControlID>" + applicantDetails.getControlId() + "</ControlID>\r\n"
					+ "         <UnitID>" + applicantDetails.getUnitId() + "</UnitID>\r\n" + "         <ServiceID>"
					+ applicantDetails.getServiceId() + "</ServiceID>\r\n" + "         <ProcessIndustryID>"
					+ applicantDetails.getProcessIndustryId() + "</ProcessIndustryID>\r\n" + "         <ApplicationID>"
					+ applicantDetails.getApplicationId() + "</ApplicationID>\r\n" + "         <Status_Code>"
					+ applicantDetails.getStatusCode() + "</Status_Code>\r\n" + "         <Remarks>"
					+ applicantDetails.getRemarks() + "</Remarks>\r\n" + "         <Pendancy_level>"
					+ applicantDetails.getPendancyLevel() + "</Pendancy_level>\r\n" + "         <Fee_Amount>"
					+ applicantDetails.getFeeAmount() + "</Fee_Amount>\r\n" + "         <Fee_Status>"
					+ applicantDetails.getFeeStatus() + "</Fee_Status>\r\n" + "         <Transaction_ID>"
					+ applicantDetails.getTransactionId() + "</Transaction_ID>\r\n" + "         <Transaction_Date>"
					+ applicantDetails.getTransactionDate() + "</Transaction_Date>\r\n"
					+ "         <Transaction_Date_Time>" + applicantDetails.getTransactionDateTime()
					+ "</Transaction_Date_Time>\r\n" + "         <NOC_Certificate_Number>"
					+ applicantDetails.getnOCCertificateNumber() + "</NOC_Certificate_Number>\r\n"
					+ "         <NOC_URL></NOC_URL>\r\n" + "         <ISNOC_URL_ActiveYesNO>"
					+ applicantDetails.getiSNOCURLActiveYesNO() + "</ISNOC_URL_ActiveYesNO>\r\n"
					+ "         <passsalt>v8926bb8a5c915ba4b896325bc3c2k3w</passsalt>\r\n" + "         <Request_ID>"
					+ applicantDetails.getRequestId() + "</Request_ID>\r\n" + "         <Objection_Rejection_Code>"
					+ applicantDetails.getObjectionRejectionCode() + "</Objection_Rejection_Code>\r\n"
					+ "         <Is_Certificate_Valid_Life_Time>" + applicantDetails.getIsCertificateValidLifeTime()
					+ "</Is_Certificate_Valid_Life_Time>\r\n" + "         <Certificate_EXP_Date_DDMMYYYY>"
					+ applicantDetails.getCertificateEXPDateDDMMYYYY() + "</Certificate_EXP_Date_DDMMYYYY>\r\n"
					+ "         <D1>" + applicantDetails.getD1() + "</D1>\r\n" + "         <D2>"
					+ applicantDetails.getD2() + "</D2>\r\n" + "         <D3>" + applicantDetails.getD3() + "</D3>\r\n"
					+ "         <D4>" + applicantDetails.getD4() + "</D4>\r\n" + "         <D5>"
					+ applicantDetails.getD5() + "</D5>\r\n" + "         <D6>" + applicantDetails.getD5() + "</D6>\r\n"
					+ "         <D7>" + applicantDetails.getD5() + "</D7>\r\n" + "     "
					+ " </WReturn_CUSID_STATUS>\r\n" + "   </soap:Body>\r\n" + "</soap:Envelope>";

			URL url = new URL(wsURL);
			connection = url.openConnection();
			niveshSoapreq = niveshSoapreq.replaceAll("null", "");
		System.out.println(niveshSoapreq);

			HttpURLConnection httpConn = (HttpURLConnection) connection;
			httpConn.setRequestProperty("Content-type", "text/xml; charset=utf-8");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			out.write(niveshSoapreq.getBytes());
			out.close();

			String status = "";
			// Read the response.
			InputStreamReader isr = null;
			if (httpConn.getResponseCode() == 200) {
				isr = new InputStreamReader(httpConn.getInputStream());
				applicantDetailsRepository.save(applicantDetails);
				status = "SUCCESS";

			} else {

				isr = new InputStreamReader(httpConn.getErrorStream());
			}
			BufferedReader in = new BufferedReader(isr);

			// Write the SOAP message response to a String.
			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
				System.out.println("outputString   ---" + outputString);
			}

			return response;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
