package com.webapp.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "StoreSignature", schema = "loc")
public class StoreSignatureEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private int id;

	@Column(name = "appid")
	private String appId;

	@Column(name = "signature")
	private byte[] signature;

	@Column(name = "name")
	private String name;

	@Column(name = "designation")
	private String designation;

	@Column(name = "priority")
	private int priority;

	@Column(name = "sign")
	private String sign;

	@Column(name = "jmdsignature")
	private String jmdsignature;

	@Column(name = "mdsignature")
	private String mdsignature;

	@Column(name = "pickupsignature")
	private String pickupsignature;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getJmdsignature() {
		return jmdsignature;
	}

	public void setJmdsignature(String jmdsignature) {
		this.jmdsignature = jmdsignature;
	}

	public String getMdsignature() {
		return mdsignature;
	}

	public void setMdsignature(String mdsignature) {
		this.mdsignature = mdsignature;
	}

	public String getPickupsignature() {
		return pickupsignature;
	}

	public void setPickupsignature(String pickupsignature) {
		this.pickupsignature = pickupsignature;
	}

}
