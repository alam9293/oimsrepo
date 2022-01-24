package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "ResponseQuery", schema = "loc")
public class ResponseQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "resp_id")
	private int respId;

	@Column(name = "Resp_Clarify_Details")
	private String respClarifyDtl;
	@Column(name = "Resp_Filename")
	private String respFilename;
	@Column(name = "Resp_Filedata")
	private byte[] respFiledata;
	@Column(name = "Resp_APC_ID")
	private String respApplId;
	@Column(name = "Resp_Rq_ID")
	private String respRqid;
	@Column(name = "Resp_Created_By")
	private String respCreatedBy;
	@Column(name = "Resp_Modified_By")
	private String respModifiedBy;
	@Column(name = "Resp_Status")
	private String respStatus;
	@Column(name = "Resp_Modified_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date respModifiyDate;
	@Column(name = "Resp_User_ID")
	private String respUserId;

	@Column(name = "raise_query_id")
	private String reqId;

	public int getRespId() {
		return respId;
	}

	public void setRespId(int respId) {
		this.respId = respId;
	}

	public String getRespClarifyDtl() {
		return respClarifyDtl;
	}

	public void setRespClarifyDtl(String respClarifyDtl) {
		this.respClarifyDtl = respClarifyDtl;
	}

	public String getRespFilename() {
		return respFilename;
	}

	public void setRespFilename(String respFilename) {
		this.respFilename = respFilename;
	}

	public byte[] getRespFiledata() {
		return respFiledata;
	}

	public void setRespFiledata(byte[] respFiledata) {
		this.respFiledata = respFiledata;
	}

	public String getRespApplId() {
		return respApplId;
	}

	public void setRespApplId(String respApplId) {
		this.respApplId = respApplId;
	}

	public String getRespRqid() {
		return respRqid;
	}

	public void setRespRqid(String respRqid) {
		this.respRqid = respRqid;
	}

	public String getRespCreatedBy() {
		return respCreatedBy;
	}

	public void setRespCreatedBy(String respCreatedBy) {
		this.respCreatedBy = respCreatedBy;
	}

	public String getRespModifiedBy() {
		return respModifiedBy;
	}

	public void setRespModifiedBy(String respModifiedBy) {
		this.respModifiedBy = respModifiedBy;
	}

	public String getRespStatus() {
		return respStatus;
	}

	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}

	public Date getRespModifiyDate() {
		return respModifiyDate;
	}

	public void setRespModifiyDate(Date respModifiyDate) {
		this.respModifiyDate = respModifiyDate;
	}

	public String getRespUserId() {
		return respUserId;
	}

	public void setRespUserId(String respUserId) {
		this.respUserId = respUserId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	
}
