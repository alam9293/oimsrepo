package com.cdgtaxi.ibs.common.model;

// Generated Jul 20, 2009 4:09:29 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Timestamp;

import com.cdgtaxi.ibs.acl.model.SatbUser;

@SuppressWarnings("serial")
public class BmtbNoteFlow implements java.io.Serializable {

	// Fields

	private Long noteFlowNo;
	private String fromStatus;
	private String toStatus;
	private String remarks;
	private Timestamp flowDt;
	private Integer version;
	private BmtbNote bmtbNote;
	private SatbUser satbUser;

	// Constructors

	/** default constructor */
	public BmtbNoteFlow() {
	}

	/** minimal constructor */
	public BmtbNoteFlow(String fromStatus, String toStatus, Timestamp flowDt) {
		this.fromStatus = fromStatus;
		this.toStatus = toStatus;
		this.flowDt = flowDt;
	}

	/** full constructor */
	public BmtbNoteFlow(String fromStatus, String toStatus, String remarks, Timestamp flowDt,
			Integer version, BmtbNote bmtbNote, SatbUser satbUser) {
		this.fromStatus = fromStatus;
		this.toStatus = toStatus;
		this.remarks = remarks;
		this.flowDt = flowDt;
		this.version = version;
		this.bmtbNote = bmtbNote;
		this.satbUser = satbUser;
	}

	// Property accessors

	public String getFromStatus() {
		return this.fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	public String getToStatus() {
		return this.toStatus;
	}

	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getFlowDt() {
		return this.flowDt;
	}

	public void setFlowDt(Timestamp flowDt) {
		this.flowDt = flowDt;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public SatbUser getSatbUser() {
		return this.satbUser;
	}

	public void setSatbUser(SatbUser satbUser) {
		this.satbUser = satbUser;
	}

	public Long getNoteFlowNo() {
		return noteFlowNo;
	}

	public void setNoteFlowNo(Long noteFlowNo) {
		this.noteFlowNo = noteFlowNo;
	}

	public BmtbNote getBmtbNote() {
		return bmtbNote;
	}

	public void setBmtbNote(BmtbNote bmtbNote) {
		this.bmtbNote = bmtbNote;
	}

	/**
	 * toString
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode()))
				.append(" [");
		buffer.append("fromStatus").append("='").append(getFromStatus()).append("' ");
		buffer.append("toStatus").append("='").append(getToStatus()).append("' ");
		buffer.append("remarks").append("='").append(getRemarks()).append("' ");
		buffer.append("flowDt").append("='").append(getFlowDt()).append("' ");
		buffer.append("version").append("='").append(getVersion()).append("' ");
		buffer.append("]");

		return buffer.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BmtbNoteFlow))
			return false;
		BmtbNoteFlow castOther = (BmtbNoteFlow) other;

		return ((this.getNoteFlowNo() == castOther.getNoteFlowNo()) || (this.getNoteFlowNo() != null
				&& castOther.getNoteFlowNo() != null && this.getNoteFlowNo().equals(
				castOther.getNoteFlowNo())))
				&& ((this.getFromStatus() == castOther.getFromStatus()) || (this.getFromStatus() != null
						&& castOther.getFromStatus() != null && this.getFromStatus().equals(
						castOther.getFromStatus())))
				&& ((this.getToStatus() == castOther.getToStatus()) || (this.getToStatus() != null
						&& castOther.getToStatus() != null && this.getToStatus().equals(
						castOther.getToStatus())))
				&& ((this.getRemarks() == castOther.getRemarks()) || (this.getRemarks() != null
						&& castOther.getRemarks() != null && this.getRemarks().equals(
						castOther.getRemarks())))
				&& ((this.getFlowDt() == castOther.getFlowDt()) || (this.getFlowDt() != null
						&& castOther.getFlowDt() != null && this.getFlowDt().equals(
						castOther.getFlowDt())))
				&& ((this.getVersion() == castOther.getVersion()) || (this.getVersion() != null
						&& castOther.getVersion() != null && this.getVersion().equals(
						castOther.getVersion())))
				&& ((this.getBmtbNote() == castOther.getBmtbNote()) || (this.getBmtbNote() != null
						&& castOther.getBmtbNote() != null && this.getBmtbNote().equals(
						castOther.getBmtbNote())))
				&& ((this.getSatbUser() == castOther.getSatbUser()) || (this.getSatbUser() != null
						&& castOther.getSatbUser() != null && this.getSatbUser().equals(
						castOther.getSatbUser())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getNoteFlowNo() == null ? 0 : this.getNoteFlowNo().hashCode());
		result = 37 * result + (getFromStatus() == null ? 0 : this.getFromStatus().hashCode());
		result = 37 * result + (getToStatus() == null ? 0 : this.getToStatus().hashCode());
		result = 37 * result + (getRemarks() == null ? 0 : this.getRemarks().hashCode());
		result = 37 * result + (getFlowDt() == null ? 0 : this.getFlowDt().hashCode());
		result = 37 * result + (getVersion() == null ? 0 : this.getVersion().hashCode());
		result = 37 * result + (getBmtbNote() == null ? 0 : this.getBmtbNote().hashCode());
		result = 37 * result + (getSatbUser() == null ? 0 : this.getSatbUser().hashCode());
		return result;
	}

}
