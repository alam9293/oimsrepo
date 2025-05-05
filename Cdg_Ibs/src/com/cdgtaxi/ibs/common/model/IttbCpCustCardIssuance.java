/**
 * This class is Track Customer Card Issuance Model
 * 
 * @author jtaruc
 * @version 04/07/2011
 *
 * Modification History
 *
 * Modified by    Date            Description:
 * -----------    -----------     -----------
 * 
 */

package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class IttbCpCustCardIssuance implements java.io.Serializable {

	// Fields

	private Long issuanceNo;
	private BigDecimal productNo;
	private String cardNo;
	private String productTypeId;
	private String issuedTo;
	private Timestamp issuedOn;
	private Timestamp returnedOn;
	private String createdBy;
	private Timestamp createdDt;
	private String updatedBy;
	private Timestamp updatedDt;
	private PmtbProduct pmtbProduct;
	
	private String batchFlag;
	private String remarks;
	
	// Constructors

	/** default constructor */
	public IttbCpCustCardIssuance() {
	}

	/** full constructor */
	public IttbCpCustCardIssuance(Long issuanceNo, BigDecimal productNo, String cardNo, String productTypeId, String issuedTo, Timestamp issuedOn, Timestamp returnedOn, String createdBy, Timestamp createdDt, String updatedBy, Timestamp updatedDt, String batchFlag, String remarks) {
		this.issuanceNo = issuanceNo;
		this.productNo = productNo;
		this.cardNo = cardNo;
		this.productTypeId = productTypeId;
		this.issuedTo = issuedTo;
		this.issuedOn = issuedOn;
		this.returnedOn = returnedOn;
		this.createdBy = createdBy;
		this.createdDt = createdDt;
		this.updatedBy = updatedBy;
		this.updatedDt = updatedDt;
		this.batchFlag = batchFlag;
		this.remarks = remarks;
	}

	// Property accessors
	
	

    /**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public Timestamp getCreatedDt() {
		return createdDt;
	}


	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return the issueNo
	 */
	public Long getIssuanceNo() {
		return issuanceNo;
	}

	/**
	 * @param issueNo the issueNo to set
	 */
	public void setIssuanceNo(Long issuanceNo) {
		this.issuanceNo = issuanceNo;
	}
	
	/**
	 * @return the issueTo
	 */
	public String getIssuedTo() {
		return issuedTo;
	}

	/**
	 * @param issueTo the issueTo to set
	 */
	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

	/**
	 * @return the issuedOn
	 */
	public Timestamp getIssuedOn() {
		return issuedOn;
	}
	
	/**
	 * @param issuedOn the issuedOn to set
	 */
	public void setIssuedOn(Timestamp issuedOn) {
		this.issuedOn = issuedOn;
	}

	/**
	 * @return the returnedOn
	 */
	public Timestamp getReturnedOn() {
		return returnedOn;
	}

	/**
	 * @param returnedOn the returnedOn to set
	 */
	public void setReturnedOn(Timestamp returnedOn) {
		this.returnedOn = returnedOn;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the productNo
	 */
	public BigDecimal getProductNo() {
		return productNo;
	}

	/**
	 * @param productNo the productNo to set
	 */
	public void setProductNo(BigDecimal productNo) {
		this.productNo = productNo;
	}

	/**
	 * @return the productTypeId
	 */
	public String getProductTypeId() {
		return productTypeId;
	}

	/**
	 * @param productTypeId the productTypeId to set
	 */
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDt
	 */
	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt the updatedDt to set
	 */
	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getBatchFlag() {
		return batchFlag;
	}

	public void setBatchFlag(String batchFlag) {
		this.batchFlag = batchFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * toString
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("issuanceNo").append("='").append(getIssuanceNo()).append("' ");
		buffer.append("productNo").append("='").append(getProductNo()).append("' ");
		buffer.append("cardNo").append("='").append(getCardNo()).append("' ");
		buffer.append("productTypeId").append("='").append(getProductTypeId()).append("' ");
		buffer.append("issuedTo").append("='").append(getIssuedTo()).append("' ");
		buffer.append("issuedOn").append("='").append(getIssuedOn()).append("' ");
		buffer.append("returnedOn").append("='").append(getReturnedOn()).append("' ");
		buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");
		buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");
		buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");
		buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");
		buffer.append("batchFlag").append("='").append(getBatchFlag()).append("' ");
		buffer.append("remarks").append("='").append(getBatchFlag()).append("' ");
		buffer.append("]");

		return buffer.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IttbCpCustCardIssuance))
			return false;
		IttbCpCustCardIssuance castOther = (IttbCpCustCardIssuance) other;

		return ((this.getIssuanceNo() == castOther.getIssuanceNo()) || (this.getIssuanceNo() != null && castOther.getIssuanceNo() != null && this.getIssuanceNo().equals(castOther.getIssuanceNo())))
				&& ((this.getProductNo() == castOther.getProductNo()) || (this.getProductNo() != null && castOther.getProductNo() != null && this.getProductNo().equals(castOther.getProductNo())))
				&& ((this.getCardNo() == castOther.getCardNo()) || (this.getCardNo() != null && castOther.getCardNo() != null && this.getCardNo().equals(castOther.getCardNo())))
				&& ((this.getProductTypeId() == castOther.getProductTypeId()) || (this.getProductTypeId() != null && castOther.getProductTypeId() != null && this.getProductTypeId().equals(castOther.getProductTypeId())))
				&& ((this.getIssuedTo() == castOther.getIssuedTo()) || (this.getIssuedTo() != null && castOther.getIssuedTo() != null && this.getIssuedTo().equals(castOther.getIssuedTo())))
				&& ((this.getIssuedOn() == castOther.getIssuedOn()) || (this.getIssuedOn() != null && castOther.getIssuedOn() != null && this.getIssuedOn().equals(castOther.getIssuedOn())))
				&& ((this.getReturnedOn() == castOther.getReturnedOn()) || (this.getReturnedOn() != null && castOther.getReturnedOn() != null && this.getReturnedOn().equals(castOther.getReturnedOn())))
				&& ((this.getCreatedBy() == castOther.getCreatedBy()) || (this.getCreatedBy() != null && castOther.getCreatedBy() != null && this.getCreatedBy().equals(castOther.getCreatedBy())))
				&& ((this.getCreatedDt() == castOther.getCreatedDt()) || (this.getCreatedDt() != null && castOther.getCreatedDt() != null && this.getCreatedDt().equals(castOther.getCreatedDt())))
				&& ((this.getUpdatedBy() == castOther.getUpdatedBy()) || (this.getUpdatedBy() != null && castOther.getUpdatedBy() != null && this.getUpdatedBy().equals(castOther.getUpdatedBy())))
				&& ((this.getUpdatedDt() == castOther.getUpdatedDt()) || (this.getUpdatedDt() != null && castOther.getUpdatedDt() != null && this.getUpdatedDt().equals(castOther.getUpdatedDt())))
				&& ((this.getBatchFlag() == castOther.getBatchFlag()) || (this.getBatchFlag() != null && castOther.getBatchFlag() != null && this.getBatchFlag().equals(castOther.getBatchFlag())))
				&& ((this.getRemarks() == castOther.getRemarks()) || (this.getRemarks() != null && castOther.getRemarks() != null && this.getRemarks().equals(castOther.getRemarks())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIssuanceNo() == null ? 0 : this.getIssuanceNo().hashCode());
		result = 37 * result + (getProductNo() == null ? 0 : this.getProductNo().hashCode());
		result = 37 * result + (getCardNo() == null ? 0 : this.getCardNo().hashCode());
		result = 37 * result + (getProductTypeId() == null ? 0 : this.getProductTypeId().hashCode());
		result = 37 * result + (getIssuedTo() == null ? 0 : this.getIssuedTo().hashCode());
		result = 37 * result + (getIssuedOn() == null ? 0 : this.getIssuedOn().hashCode());
		result = 37 * result + (getReturnedOn() == null ? 0 : this.getReturnedOn().hashCode());
		result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
		result = 37 * result + (getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode());
		result = 37 * result + (getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode());
		result = 37 * result + (getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode());
		result = 37 * result + (getBatchFlag() == null ? 0 : this.getBatchFlag().hashCode());
		result = 37 * result + (getRemarks() == null ? 0 : this.getRemarks().hashCode());
		return result;
	}

	public PmtbProduct getPmtbProduct() {
		return pmtbProduct;
	}

	public void setPmtbProduct(PmtbProduct pmtbProduct) {
		this.pmtbProduct = pmtbProduct;
	}

}
