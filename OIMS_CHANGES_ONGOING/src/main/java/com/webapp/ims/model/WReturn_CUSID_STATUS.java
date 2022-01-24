package com.webapp.ims.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "WReturn_CUSID_STATUS")
public class WReturn_CUSID_STATUS {

	private String ControlID;
	private String UnitID;
	private String ServiceID;
	private String ProcessIndustryID;
	private String ApplicationID;
	private String Status_Code;
	private String Remarks;
	private String Pendancy_level;
	private String Fee_Amount;
	private String Fee_Status;
	private String Transaction_ID;
	private String Transaction_Date;
	private String Transaction_Date_Time;
	private String NOC_Certificate_Number;
	private String NOC_URL;
	private String ISNOC_URL_ActiveYesNO;
	private String passsalt;
	private String Request_ID;
	private String Objection_Rejection_Code;
	private String Is_Certificate_Valid_Life_Time;
	private String Certificate_EXP_Date_DDMMYYYY;
	private String D1;
	private String D2;
	private String D3;
	private String D4;
	private String D5;
	private String D6;
	private String D7;

//	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)

	@XmlElement(name = "ControlID")
	public String getControlID() {
		return ControlID;
	}

	public void setControlID(String controlID) {
		ControlID = controlID;
	}

	@XmlElement(name = "UnitID")
	public String getUnitID() {
		return UnitID;
	}

	public void setUnitID(String unitID) {
		UnitID = unitID;
	}

	@XmlElement(name = "ServiceID")
	public String getServiceID() {
		return ServiceID;
	}

	public void setServiceID(String serviceID) {
		ServiceID = serviceID;
	}

	@XmlElement(name = "ProcessIndustryID")
	public String getProcessIndustryID() {
		return ProcessIndustryID;
	}

	public void setProcessIndustryID(String processIndustryID) {
		ProcessIndustryID = processIndustryID;
	}

	@XmlElement(name = "ApplicationID")
	public String getApplicationID() {
		return ApplicationID;
	}

	public void setApplicationID(String applicationID) {
		ApplicationID = applicationID;
	}

	@XmlElement(name = "Status_Code")
	public String getStatus_Code() {
		return Status_Code;
	}

	public void setStatus_Code(String status_Code) {
		Status_Code = status_Code;
	}

	@XmlElement(name = "Remarks")
	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	@XmlElement(name = "Pendancy_level")
	public String getPendancy_level() {
		return Pendancy_level;
	}

	public void setPendancy_level(String pendancy_level) {
		Pendancy_level = pendancy_level;
	}

	@XmlElement(name = "Fee_Amount")
	public String getFee_Amount() {
		return Fee_Amount;
	}

	public void setFee_Amount(String fee_Amount) {
		Fee_Amount = fee_Amount;
	}

	@XmlElement(name = "Fee_Status")
	public String getFee_Status() {
		return Fee_Status;
	}

	public void setFee_Status(String fee_Status) {
		Fee_Status = fee_Status;
	}

	@XmlElement(name = "Transaction_ID")
	public String getTransaction_ID() {
		return Transaction_ID;
	}

	public void setTransaction_ID(String transaction_ID) {
		Transaction_ID = transaction_ID;
	}

	@XmlElement(name = "Transaction_Date")
	public String getTransaction_Date() {
		return Transaction_Date;
	}

	public void setTransaction_Date(String transaction_Date) {
		Transaction_Date = transaction_Date;
	}

	@XmlElement(name = "Transaction_Date_Time")
	public String getTransaction_Date_Time() {
		return Transaction_Date_Time;
	}

	public void setTransaction_Date_Time(String transaction_Date_Time) {
		Transaction_Date_Time = transaction_Date_Time;
	}

	@XmlElement(name = "NOC_Certificate_Number")
	public String getNOC_Certificate_Number() {
		return NOC_Certificate_Number;
	}

	public void setNOC_Certificate_Number(String nOC_Certificate_Number) {
		NOC_Certificate_Number = nOC_Certificate_Number;
	}

	@XmlElement(name = "NOC_URL")
	public String getNOC_URL() {
		return NOC_URL;
	}

	public void setNOC_URL(String nOC_URL) {
		NOC_URL = nOC_URL;
	}

	@XmlElement(name = "ISNOC_URL_ActiveYesNO")
	public String getISNOC_URL_ActiveYesNO() {
		return ISNOC_URL_ActiveYesNO;
	}

	public void setISNOC_URL_ActiveYesNO(String iSNOC_URL_ActiveYesNO) {
		ISNOC_URL_ActiveYesNO = iSNOC_URL_ActiveYesNO;
	}

	@XmlElement(name = "passsalt")
	public String getPasssalt() {
		return passsalt;
	}

	public void setPasssalt(String passsalt) {
		this.passsalt = passsalt;
	}

	@XmlElement(name = "Request_ID")
	public String getRequest_ID() {
		return Request_ID;
	}

	public void setRequest_ID(String request_ID) {
		Request_ID = request_ID;
	}

	@XmlElement(name = "Objection_Rejection_Code")
	public String getObjection_Rejection_Code() {
		return Objection_Rejection_Code;
	}

	public void setObjection_Rejection_Code(String objection_Rejection_Code) {
		Objection_Rejection_Code = objection_Rejection_Code;
	}

	@XmlElement(name = "Is_Certificate_Valid_Life_Time")
	public String getIs_Certificate_Valid_Life_Time() {
		return Is_Certificate_Valid_Life_Time;
	}

	public void setIs_Certificate_Valid_Life_Time(String is_Certificate_Valid_Life_Time) {
		Is_Certificate_Valid_Life_Time = is_Certificate_Valid_Life_Time;
	}

	@XmlElement(name = "Certificate_EXP_Date_DDMMYYYY")
	public String getCertificate_EXP_Date_DDMMYYYY() {
		return Certificate_EXP_Date_DDMMYYYY;
	}

	public void setCertificate_EXP_Date_DDMMYYYY(String certificate_EXP_Date_DDMMYYYY) {
		Certificate_EXP_Date_DDMMYYYY = certificate_EXP_Date_DDMMYYYY;
	}

	@XmlElement(name = "D1")
	public String getD1() {
		return D1;
	}

	public void setD1(String d1) {
		D1 = d1;
	}

	@XmlElement(name = "D2")
	public String getD2() {
		return D2;
	}

	public void setD2(String d2) {
		D2 = d2;
	}

	@XmlElement(name = "D3")
	public String getD3() {
		return D3;
	}

	public void setD3(String d3) {
		D3 = d3;
	}

	@XmlElement(name = "D4")
	public String getD4() {
		return D4;
	}

	public void setD4(String d4) {
		D4 = d4;
	}

	@XmlElement(name = "D5")
	public String getD5() {
		return D5;
	}

	public void setD5(String d5) {
		D5 = d5;
	}

	@XmlElement(name = "D6")
	public String getD6() {
		return D6;
	}

	public void setD6(String d6) {
		D6 = d6;
	}

	@XmlElement(name = "D7")
	public String getD7() {
		return D7;
	}

	public void setD7(String d7) {
		D7 = d7;
	}

}
