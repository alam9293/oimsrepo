package com.webapp.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DSC_Authentication", schema = "loc")
public class DigitalSignatureEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "dSCName")
	private String dSCName;

	@Column(name = "dSCDigitalSignature")
	private String dSCDigitalSignature;

	@Column(name = "serialNo")
	private String serialNo;

	private String recoComment;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
