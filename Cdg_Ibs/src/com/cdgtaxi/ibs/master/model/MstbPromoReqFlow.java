package com.cdgtaxi.ibs.master.model;

import java.sql.Timestamp;

import com.cdgtaxi.ibs.acl.security.Auditable;
import com.cdgtaxi.ibs.common.model.Creatable;

@SuppressWarnings("serial")
public class MstbPromoReqFlow implements java.io.Serializable, Creatable, Auditable {

	// Fields

	private Integer promoReqFlowNo;
	private String reqFromStatus;
	private String reqToStatus;
	private String fromStatus;
	private String toStatus;
	private MstbPromoDetail toPromoDetail;
	private String remarks;
	private Timestamp createdDt;
	private String createdBy;
	private Integer version;
	private MstbPromoReq mstbPromotionReq;
	

	// Constructors

	/** default constructor */
	public MstbPromoReqFlow() {
	}

	/** minimal constructor */
	public MstbPromoReqFlow(String reqFromStatus, String reqToStatus, String fromStatus,  String toStatus, MstbPromoDetail toPromoDetail, Timestamp createdDt) {
		this.reqFromStatus = reqFromStatus;
		this.reqToStatus = reqToStatus;
		this.fromStatus = fromStatus;
		this.toStatus = toStatus;
		this.toPromoDetail = toPromoDetail;
		this.createdDt = createdDt;
	}

	/** full constructor */
	public MstbPromoReqFlow(String reqFromStatus, String reqToStatus, String fromStatus, String toStatus, MstbPromoDetail toPromoDetail, String remarks,  String createdBy, Timestamp createdDt,
			Integer version, MstbPromoReq mstbPromotionFlow) {
		this.reqFromStatus = reqFromStatus;
		this.reqToStatus = reqToStatus;
		this.fromStatus = fromStatus;
		this.toStatus = toStatus;
		this.toPromoDetail = toPromoDetail;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDt = createdDt;
		this.version = version;
		this.mstbPromotionReq = mstbPromotionFlow;
		
	}

	// Property accessors

	public Integer getPromoReqFlowNo() {
        return this.promoReqFlowNo;
    }
    
    public void setPromoReqFlowNo(Integer promoReqFlowNo) {
        this.promoReqFlowNo = promoReqFlowNo;
    }
	
	
	public String getReqFromStatus() {
		return this.reqFromStatus;
	}

	public void setReqFromStatus(String reqFromStatus) {
		this.reqFromStatus = reqFromStatus;
	}

	public String getReqToStatus() {
		return this.reqToStatus;
	}

	public void setReqToStatus(String reqToStatus) {
		this.reqToStatus = reqToStatus;
	}
	
	public String getFromStatus() {
		return this.fromStatus;
	}

	public void setFromStatus(String status) {
		this.fromStatus = status;
	}
	
	public String getToStatus() {
		return this.toStatus;
	}

	public void setToStatus(String status) {
		this.toStatus = status;
	}
	
	public MstbPromoDetail getToPromoDetail() {
        return this.toPromoDetail;
    }
    
    public void setToPromoDetail(MstbPromoDetail toPromoDetail) {
        this.toPromoDetail = toPromoDetail;
    }

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getCreatedDt() {
		return this.createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public MstbPromoReq getMstbPromotionReq() {
		return mstbPromotionReq;
	}

	public void setMstbPromotionReq(MstbPromoReq mstbPromotionReq) {
		this.mstbPromotionReq = mstbPromotionReq;
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
		buffer.append("reqFromStatus").append("='").append(getReqFromStatus()).append("' ");
		buffer.append("reqToStatus").append("='").append(getReqToStatus()).append("' ");
		buffer.append("fromStatus").append("='").append(getFromStatus()).append("' ");
		buffer.append("toStatus").append("='").append(getToStatus()).append("' ");
		buffer.append("remarks").append("='").append(getRemarks()).append("' ");
		buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");
		buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");
		buffer.append("toPromoDetail").append("='").append(getToPromoDetail()).append("' ");
		//buffer.append("mstbPromotionReq").append("='").append(getMstbPromotionReq()).append("' ");
		buffer.append("version").append("='").append(getVersion()).append("' ");
		buffer.append("]");

		return buffer.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MstbPromoReqFlow))
			return false;
		MstbPromoReqFlow castOther = (MstbPromoReqFlow) other;

		return ((this.getPromoReqFlowNo() == castOther.getPromoReqFlowNo()) || (this.getPromoReqFlowNo() != null
				&& castOther.getPromoReqFlowNo() != null && this.getPromoReqFlowNo().equals(
				castOther.getPromoReqFlowNo())))
				&& ((this.getReqFromStatus() == castOther.getReqFromStatus()) || (this.getReqFromStatus() != null
						&& castOther.getReqFromStatus() != null && this.getReqFromStatus().equals(
						castOther.getReqFromStatus())))
				&& ((this.getReqToStatus() == castOther.getReqToStatus()) || (this.getReqToStatus() != null
						&& castOther.getReqToStatus() != null && this.getReqToStatus().equals(
						castOther.getReqToStatus())))
				&& ((this.getFromStatus() == castOther.getFromStatus()) || (this.getFromStatus() != null
						&& castOther.getFromStatus() != null && this.getFromStatus().equals(
						castOther.getFromStatus())))
				&& ((this.getToStatus() == castOther.getToStatus()) || (this.getToStatus() != null
						&& castOther.getToStatus() != null && this.getToStatus().equals(
						castOther.getToStatus())))
				&& ( (this.getToPromoDetail()==castOther.getToPromoDetail()) || ( this.getToPromoDetail()!=null && castOther.getToPromoDetail()!=null && this.getToPromoDetail().equals(castOther.getToPromoDetail()) ) )
	 
				&& ((this.getRemarks() == castOther.getRemarks()) || (this.getRemarks() != null
						&& castOther.getRemarks() != null && this.getRemarks().equals(
						castOther.getRemarks())))
				&& ((this.getCreatedDt() == castOther.getCreatedDt()) || (this.getCreatedDt() != null
						&& castOther.getCreatedDt() != null && this.getCreatedDt().equals(
						castOther.getCreatedDt())))
				&& ((this.getVersion() == castOther.getVersion()) || (this.getVersion() != null
						&& castOther.getVersion() != null && this.getVersion().equals(
						castOther.getVersion())))
				/*&& ((this.getMstbPromotionReq() == castOther.getMstbPromotionReq()) || (this.getMstbPromotionReq() != null
						&& castOther.getMstbPromotionReq() != null && this.getMstbPromotionReq().equals(
						castOther.getMstbPromotionReq())))*/
				&& ((this.getCreatedBy() == castOther.getCreatedBy()) || (this.getCreatedBy() != null
						&& castOther.getCreatedBy() != null && this.getCreatedBy().equals(
						castOther.getCreatedBy())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPromoReqFlowNo() == null ? 0 : this.getPromoReqFlowNo().hashCode());
		result = 37 * result + (getReqFromStatus() == null ? 0 : this.getReqFromStatus().hashCode());
		result = 37 * result + (getReqToStatus() == null ? 0 : this.getReqToStatus().hashCode());
		result = 37 * result + (getFromStatus() == null ? 0 : this.getFromStatus().hashCode());
		result = 37 * result + (getToStatus() == null ? 0 : this.getToStatus().hashCode());
		result = 37 * result + (getToPromoDetail() == null ? 0 : this.getToPromoDetail().hashCode() );
		result = 37 * result + (getRemarks() == null ? 0 : this.getRemarks().hashCode());
		result = 37 * result + (getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode());
		result = 37 * result + (getVersion() == null ? 0 : this.getVersion().hashCode());
		//result = 37 * result + (getMstbPromotionReq() == null ? 0 : this.getMstbPromotionReq().hashCode());
		result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
		return result;
	}

}
