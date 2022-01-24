package com.webapp.ims.webservices;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.NodeList;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.repository.ApplicantDetailsRepository;

/**
 * @description
 *
 * @author  durge
 * @version 1.0
 * @since   28-Aug-2020
 */

/**
 * @author nic
 *
 */
public class SoapConsumeEx implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String, String> params;

	public SoapConsumeEx(Map<String, String> params) {
		this.params = params;
	}

	public SoapConsumeEx() {
	}

	/**
	 * This method is used to ....
	 * 
	 * @param
	 * @return void
	 */

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */

//	{TxtControlID=UPSWP200001151, TxtUnitID=UPSWP20000115105, TxtServiceID=SC54001, TxtProcessIndustryID=54, TxtApplicationID=, TxtRequestID=20000115105540010001, _VIEWSTATEGENERATOR=B54E2809, _EVENTVALIDATION=a++4+JpfrrPQVWDh4N8kmcHXcNACHtNZJx5z3/vmcIQ/BfRpXfJBI3uxJmctjUasoVjp1UA0Vx48eekYmC2H6lY6x3ELx6LaIJiMuAQQXVhHCL6GiKwlvA5cFJhuu+3EHzgGLoUoANgprw6dIKHwh0weVJjwlG5No0qgDaXbPrYD7lQKcrsU5NN6u+Ho3TMoMqMztuTaukZW0QwKUcvwy7llDAU13uwQhzik2g6tuWLYs8V4ku591PsLnlTXXW71}

	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;

	public Map<String, String> soapwebservice() {
		String responseString = "";
		String outputString = "";

		String wsURL = "http://72.167.225.87/testing_nmswp/upswp_niveshmitraservices.asmx";

		URLConnection connection;

		Map<String, String> niveshResponce = new HashMap<String, String>();

		try {
			String niveshSoapreq = "";
			if (params == null || params.size() == 0) {
				niveshSoapreq = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <WGetBasicDetails xmlns=\"http://tempuri.org/\">      <ControlID>UPSWP200001967</ControlID>      <UnitID>UPSWP20000196701</UnitID>      <ServiceID>SC54001</ServiceID>      <ProcessIndustryID>54</ProcessIndustryID>      <passsalt>v8926bb8a5c915ba4b896325bc3c2k3w</passsalt>    </WGetBasicDetails>  </soap:Body></soap:Envelope>";

			} else {
				String ControlID = "<ControlID>" + params.get("TxtControlID") + "</ControlID>";
				String UnitID = "<UnitID>" + params.get("TxtUnitID") + "</UnitID>";
				String ServiceID = "<ServiceID>" + params.get("TxtServiceID") + "</ServiceID>";
				String ProcessIndustryID = "<ProcessIndustryID>" + params.get("TxtProcessIndustryID")
						+ "</ProcessIndustryID>";
				String passsalt = "<passsalt>v8926bb8a5c915ba4b896325bc3c2k3w</passsalt>";

				niveshSoapreq = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <WGetBasicDetails xmlns=\"http://tempuri.org/\">   "
						+ ControlID + UnitID + ServiceID + ProcessIndustryID + passsalt
						+ "</WGetBasicDetails>  </soap:Body></soap:Envelope>";
			}

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

			// Read the response.
			InputStreamReader isr = null;
			if (httpConn.getResponseCode() == 200) {
				isr = new InputStreamReader(httpConn.getInputStream());
			} else {
				isr = new InputStreamReader(httpConn.getErrorStream());
			}

			BufferedReader in = new BufferedReader(isr);

			// Write the SOAP message response to a String.
			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
			}

			try {
				SOAPMessage message = MessageFactory.newInstance().createMessage(null,
						new ByteArrayInputStream(outputString.getBytes()));
				SOAPBody body = message.getSOAPBody();
				NodeList returnList = body.getElementsByTagName("Table");
				NodeList innerResultList = returnList.item(0).getChildNodes();
				for (int l = 0; l < innerResultList.getLength(); l++) {
					niveshResponce.put(innerResultList.item(l).getNodeName(), innerResultList.item(l).getTextContent());
				}
			} catch (SOAPException e) {
				e.printStackTrace();
			}

			return niveshResponce;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return niveshResponce;

	}

	public Map<String, String> WReturn_CUSID_STATUS1(ApplicantDetails applicantDetails) {
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
					+ "         <NOC_URL>" + applicantDetails.getAppAddressLine1() + "</NOC_URL>\r\n"
					+ "         <ISNOC_URL_ActiveYesNO>" + applicantDetails.getiSNOCURLActiveYesNO()
					+ "</ISNOC_URL_ActiveYesNO>\r\n"
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

			//System.out.println("niveshSoapreq--======================================================" + niveshSoapreq);
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
				// applicantDetailsRepository.save(applicantDetails);
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

//	public Map<String, String> WReturn_CUSID_STATUS(String xmlData) {
//		String responseString = "";
//		String outputString = "";
//
//		String wsURL = "http://72.167.225.87/testing_nmswp/upswp_niveshmitraservices.asmx";
//		// String wsURL="http://niveshmitra.up.nic.in/upswp_niveshmitraservices.asmx";
//
//		URLConnection connection;
//		// String niveshResponce = "";
//		Map<String, String> response = new HashMap<String, String>();
//		try {
//			String niveshSoapreq = "";
//
//			niveshSoapreq = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"  xmlns=\"http://tempuri.org/\">\r\n"
//					+ "   <soap:Header/>\r\n" + "   <soap:Body>\r\n" + xmlData + " </soap:Body>\r\n"
//					+ "</soap:Envelope>";
//
//			URL url = new URL(wsURL);
//			System.out.println(niveshSoapreq);
//			connection = url.openConnection();
//
//			HttpURLConnection httpConn = (HttpURLConnection) connection;
//			httpConn.setRequestProperty("Content-type", "text/xml; charset=utf-8");
//			httpConn.setRequestMethod("POST");
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			OutputStream out = httpConn.getOutputStream();
//			out.write(niveshSoapreq.getBytes());
//			out.close();
//
//			String status = "";
//			// Read the response.
//			InputStreamReader isr = null;
//			if (httpConn.getResponseCode() == 200) {
//				isr = new InputStreamReader(httpConn.getInputStream());
//
//				// applicantDetailsRepository.save(applicantDetails);
//
//				// applicantDetailsRepository.save(applicantDetails);
//
//				status = "SUCCESS";
//			} else {
//
//				isr = new InputStreamReader(httpConn.getErrorStream());
//			}
//			BufferedReader in = new BufferedReader(isr);
//
//			// Write the SOAP message response to a String.
//			while ((responseString = in.readLine()) != null) {
//				outputString = outputString + responseString;
//				System.out.println("outputString   ---" + outputString);
//			}
//
//			return response;
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//
//	}

	public String WGetUBPaymentDetails(Map<String, String> niveshResponse) {
		String responseString = "";
		String outputString = "";

		String wsURL = "http://72.167.225.87/testing_nmswp/upswp_niveshmitraservices.asmx";

		URLConnection connection;

		String niveshResponce = "";

		try {
			String niveshSoapreq = "";
			if (params == null || params.size() == 0) {
				// <?xml version=\"1.0\" encoding=\"utf-8\"?>
				niveshSoapreq = " <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>   "
						+ "" + " <WReturn_CUSID_STATUS xmlns=\"http://tempuri.org/\">   "
						+ "   <ControlID>UPSWP200001485</ControlID>" + "      <UnitID>UPSWP20000148501</UnitID>"
						+ "      <ServiceID>SC54001</ServiceID>   " + "   <ProcessIndustryID>54</ProcessIndustryID>  "
						+ "    <passsalt>v8926bb8a5c915ba4b896325bc3c2k3w</passsalt> "
						+ "   </WReturn_CUSID_STATUS>  </soap:Body></soap:Envelope>";

			} else {

				String ControlID = "<ControlID>UPSWP200001494</ControlID>";
				String UnitID = "<UnitID>UPSWP20000149401</UnitID>";
				String ServiceID = "<ServiceID>SC54001</ServiceID>";
				String passsalt = "<passsalt>v8926bb8a5c915ba4b896325bc3c2k3w</passsalt>";
				String Request_ID = "<Request_ID>20000149401540010001</Request_ID>";

				niveshSoapreq = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
						+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
						+ "  <soap:Body>\r\n" + "<WGetUBPaymentDetails xmlns=\"http://tempuri.org/\">\r\n" + ControlID
						+ UnitID + ServiceID + passsalt + Request_ID + " </WGetUBPaymentDetails>\r\n"
						+ "  </soap:Body>\r\n" + "</soap:Envelope>";
			}

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
			 
			}

			return status;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return niveshResponce;
	}

	public String WReturn_CUSID_Entrepreneur_NOC_IN_BINARYFORMAT(Map<String, String> niveshResponse) {
		String responseString = "";
		String outputString = "";

		String wsURL = "http://72.167.225.87/testing_nmswp/upswp_niveshmitraservices.asmx";

		URLConnection connection;

		String niveshResponce = "";

		try {
			String niveshSoapreq = "";
			if (params == null || params.size() == 0) {
				// <?xml version=\"1.0\" encoding=\"utf-8\"?>
				niveshSoapreq = " <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <WReturn_CUSID_STATUS xmlns=\"http://tempuri.org/\">   "
						+ "   <ControlID>UPSWP200001485</ControlID>" + "      <UnitID>UPSWP20000148501</UnitID>"
						+ "      <ServiceID>SC54001</ServiceID>   " + "   <ProcessIndustryID>54</ProcessIndustryID>  "
						+ "    <passsalt>v8926bb8a5c915ba4b896325bc3c2k3w</passsalt> "
						+ "   </WReturn_CUSID_STATUS>  </soap:Body></soap:Envelope>";
			} else {

				String strControlID = "UPSWP200001494";
				String strUnitID = "UPSWP20000149401";
				String strServiceID = "SC54001";
				String ProcessIndustryID = "54";
				String passsalt = "v8926bb8a5c915ba4b896325bc3c2k3w";
				String NOC_Certificate_Number = "123456789";
				String Request_ID = "20000149401540010001";
				byte b64[] = null;

				niveshSoapreq = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
						+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
						+ "  <soap:Body>\r\n"
						+ "    <WReturn_CUSID_Entrepreneur_NOC_IN_BINARYFORMAT xmlns=\"http://tempuri.org/\">\r\n"
						+ "      <strControlID>" + strControlID + "</strControlID>\r\n" + "      <strUnitID>"
						+ strUnitID + "</strUnitID>\r\n" + "      <strServiceID>" + strServiceID + "</strServiceID>\r\n"
						+ "      <ProcessIndustryID>" + ProcessIndustryID + "</ProcessIndustryID>\r\n"
						+ "      <NOC_Certificate_Number>" + NOC_Certificate_Number + "</NOC_Certificate_Number>\r\n"
						+ "      <base64></base64>\r\n" + "      <MimeType>Image/jpeg</MimeType>\r\n"
						+ "      <passsalt>" + passsalt + "</passsalt>\r\n" + "      <Request_ID>" + Request_ID
						+ "</Request_ID>\r\n" + "    </WReturn_CUSID_Entrepreneur_NOC_IN_BINARYFORMAT>\r\n"
						+ "  </soap:Body>\r\n" + "</soap:Envelope>";

			}
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
			 
			}
			return status;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return niveshResponce;
	}

	public String WReturn_CUSID_ISLandPurchased(Map<String, String> niveshResponse) {
		String responseString = "";
		String outputString = "";

		String wsURL = "http://72.167.225.87/testing_nmswp/upswp_niveshmitraservices.asmx";

		URLConnection connection;

		String niveshResponce = "";

		try {
			String niveshSoapreq = "";
			if (params == null || params.size() == 0) {
				// <?xml version=\"1.0\" encoding=\"utf-8\"?>
				niveshSoapreq = " <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <WReturn_CUSID_STATUS xmlns=\"http://tempuri.org/\">   "
						+ "   <ControlID>UPSWP200001485</ControlID>" + "      <UnitID>UPSWP20000148501</UnitID>"
						+ "      <ServiceID>SC54001</ServiceID>   " + "   <ProcessIndustryID>54</ProcessIndustryID>  "
						+ "    <passsalt>v8926bb8a5c915ba4b896325bc3c2k3w</passsalt> "
						+ "   </WReturn_CUSID_STATUS>  </soap:Body></soap:Envelope>";

			} else {
				String ControlID = "UPSWP200001494";
				String UnitID = "UPSWP20000149401";
				String ServiceID = "SC54001";
				String passsalt = "v8926bb8a5c915ba4b896325bc3c2k3w";
				String ISLandPurchasedYesNO = "Yes";

				niveshSoapreq = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
						+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
						+ "  <soap:Body>\r\n" + "    <WReturn_CUSID_ISLandPurchased xmlns=\"http://tempuri.org/\">\r\n"
						+ "      <ControlID>" + ControlID + "</ControlID>\r\n" + "      <UnitID>" + UnitID
						+ "</UnitID>\r\n" + "      <ServiceID>" + ServiceID + "</ServiceID>\r\n"
						+ "      <ISLandPurchasedYesNO>" + ISLandPurchasedYesNO + "</ISLandPurchasedYesNO>\r\n"
						+ "      <passsalt>" + passsalt + "</passsalt>\r\n" + "    </WReturn_CUSID_ISLandPurchased>\r\n"
						+ "  </soap:Body>\r\n" + "</soap:Envelope>";

			}
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
//				System.out.println("WReturn_CUSID_ISLandPurchased  Response   ---->" + outputString);
//				System.out.println("WReturn_CUSID_ISLandPurchased  Response ::" + status);
			}
			return status;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return niveshResponce;
	}

}
