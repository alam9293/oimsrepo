package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Dis_Raise_Query", schema = "loc")
public class DisRaiseQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "RQ_ID")
	private String rqId;
	@Column(name = "RQ_Clarify_Sought")
	private String rqClarifySought;
	@Column(name = "RQ_Missingdoc_Detail")
	private String rqMissdocdtl;
	@Column(name = "RQ_Filename")
	private String rqFilename;
	@Column(name = "RQ_Filedata")
	private byte[] rqFiledata;
	@Column(name = "RQ_APC_ID")
	private String rqApplId;
	@Column(name = "RQ_User_ID")
	private String rqUserId;
	@Column(name = "RQ_Created_By")
	private String rqCreatedBy;
	@Column(name = "RQ_Modified_By")
	private String rqModifiedBy;
	@Column(name = "RQ_Status")
	private String rqStatus;
	@Column(name = "RQ_Department")
	private String rqDept;
	@Column(name = "RQ_Modified_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date rqModifiyDate;

	public DisRaiseQuery() {
		super();
	}

	public String getRqDept() {
		return rqDept;
	}

	public void setRqDept(String rqDept) {
		this.rqDept = rqDept;
	}

	public String getRqId() {
		return rqId;
	}

	public void setRqId(String rqId) {
		this.rqId = rqId;
	}

	public String getRqClarifySought() {
		return rqClarifySought;
	}

	public void setRqClarifySought(String rqClarifySought) {
		this.rqClarifySought = rqClarifySought;
	}

	public String getRqMissdocdtl() {
		return rqMissdocdtl;
	}

	public void setRqMissdocdtl(String rqMissdocdtl) {
		this.rqMissdocdtl = rqMissdocdtl;
	}

	public String getRqFilename() {
		return rqFilename;
	}

	public void setRqFilename(String rqFilename) {
		this.rqFilename = rqFilename;
	}

	public byte[] getRqFiledata() {
		return rqFiledata;
	}

	public void setRqFiledata(byte[] rqFiledata) {
		this.rqFiledata = rqFiledata;
	}

	public String getRqApplId() {
		return rqApplId;
	}

	public void setRqApplId(String rqApplId) {
		this.rqApplId = rqApplId;
	}

	public String getRqUserId() {
		return rqUserId;
	}

	public void setRqUserId(String rqUserId) {
		this.rqUserId = rqUserId;
	}

	public String getRqCreatedBy() {
		return rqCreatedBy;
	}

	public void setRqCreatedBy(String rqCreatedBy) {
		this.rqCreatedBy = rqCreatedBy;
	}

	public String getRqModifiedBy() {
		return rqModifiedBy;
	}

	public void setRqModifiedBy(String rqModifiedBy) {
		this.rqModifiedBy = rqModifiedBy;
	}

	public String getRqStatus() {
		return rqStatus;
	}

	public void setRqStatus(String rqStatus) {
		this.rqStatus = rqStatus;
	}

	public Date getRqModifiyDate() {
		return rqModifiyDate;
	}

	public void setRqModifiyDate(Date rqModifiyDate) {
		this.rqModifiyDate = rqModifiyDate;
	}

}
