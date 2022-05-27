package com.cdgtaxi.ibs.common.model;
// Generated Aug 31, 2009 12:30:23 PM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.cdgtaxi.ibs.acl.model.SatbUser;


/**
 * ImtbIssue generated by hbm2java
 */

public class ImtbIssue  implements java.io.Serializable {


	// Fields

	private Integer issueNo;
	private Date expiryDate;
	private BigDecimal handlingFee;
	private BigDecimal discount;
	private BigDecimal deliveryCharges;
	private Integer version;
	private ImtbIssueReq imtbIssueReq;
	private ImtbStock imtbStock;
	private Set<ImtbItem> imtbItems = new HashSet<ImtbItem>(0);
	private SatbUser satbUser;


	// Constructors

	/** default constructor */
	public ImtbIssue() {
	}

	/** minimal constructor */
	public ImtbIssue(BigDecimal handlingFee, BigDecimal discount, BigDecimal deliveryCharges, ImtbIssueReq imtbIssueReq) {
		this.handlingFee = handlingFee;
		this.discount = discount;
		this.deliveryCharges = deliveryCharges;
		this.imtbIssueReq = imtbIssueReq;
	}

	/** full constructor */
	public ImtbIssue(Date expiryDate, BigDecimal handlingFee, BigDecimal discount, BigDecimal deliveryCharges, Integer version, ImtbIssueReq imtbIssueReq, ImtbStock imtbStock, Set<ImtbItem> imtbItems, SatbUser satbUser) {
		this.expiryDate = expiryDate;
		this.handlingFee = handlingFee;
		this.discount = discount;
		this.deliveryCharges = deliveryCharges;
		this.version = version;
		this.imtbIssueReq = imtbIssueReq;
		this.imtbStock = imtbStock;
		this.imtbItems = imtbItems;
		this.satbUser = satbUser;
	}



	// Property accessors

	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(BigDecimal deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public Integer getIssueNo() {
		return this.issueNo;
	}

	public void setIssueNo(Integer issueNo) {
		this.issueNo = issueNo;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public BigDecimal getHandlingFee() {
		return this.handlingFee;
	}

	public void setHandlingFee(BigDecimal handlingFee) {
		this.handlingFee = handlingFee;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public ImtbIssueReq getImtbIssueReq() {
		return this.imtbIssueReq;
	}

	public void setImtbIssueReq(ImtbIssueReq imtbIssueReq) {
		this.imtbIssueReq = imtbIssueReq;
	}

	public ImtbStock getImtbStock() {
		return this.imtbStock;
	}

	public void setImtbStock(ImtbStock imtbStock) {
		this.imtbStock = imtbStock;
	}

	public Set<ImtbItem> getImtbItems() {
		return this.imtbItems;
	}

	public void setImtbItems(Set<ImtbItem> imtbItems) {
		this.imtbItems = imtbItems;
	}

	public SatbUser getSatbUser() {
		return this.satbUser;
	}

	public void setSatbUser(SatbUser satbUser) {
		this.satbUser = satbUser;
	}


	/**
	 * toString
	 * @return String
	 */
	 @Override
	 public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("expiryDate").append("='").append(getExpiryDate()).append("' ");
		buffer.append("handlingFee").append("='").append(getHandlingFee()).append("' ");
		buffer.append("discount").append("='").append(getDiscount()).append("' ");
		buffer.append("deliveryCharges").append("='").append(getDeliveryCharges()).append("' ");
		buffer.append("version").append("='").append(getVersion()).append("' ");
		buffer.append("]");

		return buffer.toString();
	 }


	 @Override
	 public boolean equals(Object other) {
		 if ( (this == other ) ) {
			 return true;
		 }
		 if ( (other == null ) ) {
			 return false;
		 }
		 if ( !(other instanceof ImtbIssue) ) {
			 return false;
		 }
		 ImtbIssue castOther = ( ImtbIssue ) other;

		 return ( (this.getIssueNo()==castOther.getIssueNo()) || ( this.getIssueNo()!=null && castOther.getIssueNo()!=null && this.getIssueNo().equals(castOther.getIssueNo()) ) )
		 && ( (this.getExpiryDate()==castOther.getExpiryDate()) || ( this.getExpiryDate()!=null && castOther.getExpiryDate()!=null && this.getExpiryDate().equals(castOther.getExpiryDate()) ) )
		 && ( (this.getHandlingFee()==castOther.getHandlingFee()) || ( this.getHandlingFee()!=null && castOther.getHandlingFee()!=null && this.getHandlingFee().equals(castOther.getHandlingFee()) ) )
		 && ( (this.getDiscount()==castOther.getDiscount()) || ( this.getDiscount()!=null && castOther.getDiscount()!=null && this.getDiscount().equals(castOther.getDiscount()) ) )
		 && ( (this.getDeliveryCharges()==castOther.getDeliveryCharges()) || ( this.getDeliveryCharges()!=null && castOther.getDeliveryCharges()!=null && this.getDeliveryCharges().equals(castOther.getDeliveryCharges()) ) )
		 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
		 && ( (this.getImtbStock()==castOther.getImtbStock()) || ( this.getImtbStock()!=null && castOther.getImtbStock()!=null && this.getImtbStock().equals(castOther.getImtbStock()) ) )
		 && ( (this.getSatbUser()==castOther.getSatbUser()) || ( this.getSatbUser()!=null && castOther.getSatbUser()!=null && this.getSatbUser().equals(castOther.getSatbUser()) ) );
	 }

	 @Override
	 public int hashCode() {
		 int result = 17;

		 result = 37 * result + ( getIssueNo() == null ? 0 : this.getIssueNo().hashCode() );
		 result = 37 * result + ( getExpiryDate() == null ? 0 : this.getExpiryDate().hashCode() );
		 result = 37 * result + ( getHandlingFee() == null ? 0 : this.getHandlingFee().hashCode() );
		 result = 37 * result + ( getDiscount() == null ? 0 : this.getDiscount().hashCode() );
		 result = 37 * result + ( getDeliveryCharges() == null ? 0 : this.getDeliveryCharges().hashCode() );
		 result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );

		 result = 37 * result + ( getImtbStock() == null ? 0 : this.getImtbStock().hashCode() );

		 result = 37 * result + ( getSatbUser() == null ? 0 : this.getSatbUser().hashCode() );
		 return result;
	 }





}
