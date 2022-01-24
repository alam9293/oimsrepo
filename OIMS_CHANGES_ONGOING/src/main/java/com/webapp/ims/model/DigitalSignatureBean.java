package com.webapp.ims.model;

public class DigitalSignatureBean {

	public int id;
	public String dSCName;
	public String dSCDigitalSignature;
	public String serialNo;
	public String recoComment;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getdSCName() {
		return dSCName;
	}

	public void setdSCName(String dSCName) {
		this.dSCName = dSCName;
	}

	public String getdSCDigitalSignature() {
		return dSCDigitalSignature;
	}

	public void setdSCDigitalSignature(String dSCDigitalSignature) {
		this.dSCDigitalSignature = dSCDigitalSignature;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getRecoComment() {
		return recoComment;
	}

	public void setRecoComment(String recoComment) {
		this.recoComment = recoComment;
	}
	
	

}
