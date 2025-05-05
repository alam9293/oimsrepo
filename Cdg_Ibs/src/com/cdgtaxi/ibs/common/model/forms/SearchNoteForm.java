package com.cdgtaxi.ibs.common.model.forms;

import java.util.Date;

public class SearchNoteForm extends SearchByAccountForm {
	private String noteType;
	private Long noteNo;
	private Long issuingInvoiceNo;
	private Date noteDateFrom;
	private Date noteDateTo;
	private String noteStatus;

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	public String getNoteType() {
		return noteType;
	}
	public Long getNoteNo() {
		return noteNo;
	}
	public void setNoteNo(Long noteNo) {
		this.noteNo = noteNo;
	}
	public Long getIssuingInvoiceNo() {
		return issuingInvoiceNo;
	}
	public void setIssuingInvoiceNo(Long issuingInvoiceNo) {
		this.issuingInvoiceNo = issuingInvoiceNo;
	}
	public Date getNoteDateFrom() {
		return noteDateFrom;
	}
	public void setNoteDateFrom(Date noteDateFrom) {
		this.noteDateFrom = noteDateFrom;
	}
	public Date getNoteDateTo() {
		return noteDateTo;
	}
	public void setNoteDateTo(Date noteDateTo) {
		this.noteDateTo = noteDateTo;
	}
	public String getNoteStatus() {
		return noteStatus;
	}
	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}
	@Override
	public boolean isAtLeastOneCriteriaSelected() {
		return super.isAtLeastOneCriteriaSelected()
		|| (noteType != null && !noteType.trim().equals(""))
		|| noteNo != null
		|| issuingInvoiceNo != null
		|| noteDateFrom != null
		|| noteDateTo != null
		|| (noteStatus != null && !noteStatus.trim().equals(""));
	}
}
