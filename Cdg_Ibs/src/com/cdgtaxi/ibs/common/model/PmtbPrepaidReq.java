package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.acl.security.Auditable;


@SuppressWarnings("serial")
public class PmtbPrepaidReq  implements java.io.Serializable, Auditable, Creatable, Updatable {

    private BigDecimal reqNo;
    //always the top account
    private AmtbAccount amtbAccount;
	private SatbUser requestBy;
    private Date requestDate;
    private String requestRemarks;
    private String status;
    private String requestType;
    private String approvalRequired;
    private BmtbInvoiceHeader bmtbInvoiceHeader;
    private String createdBy;
    private Timestamp createdDt;
    private String updatedBy;
    private Timestamp updatedDt;
    private Integer version;
    private SatbUser approvalBy;
    private Date approvalDate;
    private String approvalStatus;
    private String approvalRemarks;
    private Date processDate;
    private transient boolean isSelected;
    private String uri;
    private String failureUri;
    private String redDotInvoiceNo;
   
    
	public String getRedDotInvoiceNo() {
		return redDotInvoiceNo;
	}
	public void setRedDotInvoiceNo(String redDotInvoiceNo) {
		this.redDotInvoiceNo = redDotInvoiceNo;
	}
	
	public String getFailureUri() {
		return failureUri;
	}
	public void setFailureUri(String failureUri) {
		this.failureUri = failureUri;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public BigDecimal getReqNo() {
		return reqNo;
	}
	public AmtbAccount getAmtbAccount() {
		return amtbAccount;
	}
	public SatbUser getRequestBy() {
		return requestBy;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public String getStatus() {
		return status;
	}
	public String getRequestType() {
		return requestType;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Timestamp getCreatedDt() {
		return createdDt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public Timestamp getUpdatedDt() {
		return updatedDt;
	}
	public Integer getVersion() {
		return version;
	}
	public void setReqNo(BigDecimal reqNo) {
		this.reqNo = reqNo;
	}
	public void setAmtbAccount(AmtbAccount amtbAccount) {
		this.amtbAccount = amtbAccount;
	}
	public void setRequestBy(SatbUser requestBy) {
		this.requestBy = requestBy;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public BmtbInvoiceHeader getBmtbInvoiceHeader() {
		return bmtbInvoiceHeader;
	}
	public void setBmtbInvoiceHeader(BmtbInvoiceHeader bmtbInvoiceHeader) {
		this.bmtbInvoiceHeader = bmtbInvoiceHeader;
	}
	
	public String getApprovalRequired() {
		return approvalRequired;
	}
	public void setApprovalRequired(String approvalRequired) {
		this.approvalRequired = approvalRequired;
	}
	public SatbUser getApprovalBy() {
		return approvalBy;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalBy(SatbUser approvalBy) {
		this.approvalBy = approvalBy;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getRequestRemarks() {
		return requestRemarks;
	}
	public String getApprovalRemarks() {
		return approvalRemarks;
	}
	public void setRequestRemarks(String requestRemarks) {
		this.requestRemarks = requestRemarks;
	}
	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reqNo == null) ? 0 : reqNo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PmtbPrepaidReq other = (PmtbPrepaidReq) obj;
		if (reqNo == null) {
			if (other.reqNo != null)
				return false;
		} else if (!reqNo.equals(other.reqNo))
			return false;
		return true;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
     

}
