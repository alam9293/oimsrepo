package com.cdgtaxi.ibs.common.model;
// Generated Jul 27, 2009 6:14:59 PM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.cdgtaxi.ibs.acl.security.Auditable;

/**
 * PmtbProductRenew generated by hbm2java
 */
//,Auditable 
public class PmtbProductRenew  implements java.io.Serializable,Creatable,Updatable{


    // Fields    

    private BigDecimal productRenewNo;
    private String durationType;
    private String durationLength;
    private Date renewDate;
    private Date currentExpDate;
    private Date newExpDate;
    private String remarks;
    private Integer version;
    private Timestamp createdDt;
    private String createdBy;
    private Timestamp updatedDt;
    private String updatedBy;
    private String embossFlag;
    private PmtbProduct pmtbProduct;
    private Timestamp currentExpTime;
    private Timestamp newExpTime;


    // Constructors

    /** default constructor */
    public PmtbProductRenew() {
    }

	/** minimal constructor */
    public PmtbProductRenew(String durationType, Date renewDate, Date currentExpDate, Date newExpDate, String remarks) {
        this.durationType = durationType;
        this.renewDate = renewDate;
        this.currentExpDate = currentExpDate;
        this.newExpDate = newExpDate;
        this.remarks = remarks;
    }
    
    /** full constructor */
    public PmtbProductRenew(String durationType, String durationLength, Date renewDate, Date currentExpDate, Date newExpDate, String remarks, Integer version, Timestamp createdDt, String createdBy, Timestamp updatedDt, String updatedBy, String embossFlag, PmtbProduct pmtbProduct, Timestamp currentExpTime, Timestamp newExpTime) {
        this.durationType = durationType;
        this.durationLength = durationLength;
        this.renewDate = renewDate;
        this.currentExpDate = currentExpDate;
        this.newExpDate = newExpDate;
        this.remarks = remarks;
        this.version = version;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updatedDt = updatedDt;
        this.updatedBy = updatedBy;
        this.embossFlag = embossFlag;
        this.pmtbProduct = pmtbProduct;
        this.currentExpTime = currentExpTime;
        this.newExpTime = newExpTime;
    }
    

   
    // Property accessors

    public BigDecimal getProductRenewNo() {
        return this.productRenewNo;
    }
    
    public void setProductRenewNo(BigDecimal productRenewNo) {
        this.productRenewNo = productRenewNo;
    }

    public String getDurationType() {
        return this.durationType;
    }
    
    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public String getDurationLength() {
        return this.durationLength;
    }
    
    public void setDurationLength(String durationLength) {
        this.durationLength = durationLength;
    }

    public Date getRenewDate() {
        return this.renewDate;
    }
    
    public void setRenewDate(Date renewDate) {
        this.renewDate = renewDate;
    }

    public Date getCurrentExpDate() {
        return this.currentExpDate;
    }
    
    public void setCurrentExpDate(Date currentExpDate) {
        this.currentExpDate = currentExpDate;
    }

    public Date getNewExpDate() {
        return this.newExpDate;
    }
    
    public void setNewExpDate(Date newExpDate) {
        this.newExpDate = newExpDate;
    }

    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Timestamp getCreatedDt() {
        return this.createdDt;
    }
    
    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedDt() {
        return this.updatedDt;
    }
    
    public void setUpdatedDt(Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getEmbossFlag() {
        return this.embossFlag;
    }
    
    public void setEmbossFlag(String embossFlag) {
        this.embossFlag = embossFlag;
    }

    public PmtbProduct getPmtbProduct() {
        return this.pmtbProduct;
    }
    
    public void setPmtbProduct(PmtbProduct pmtbProduct) {
        this.pmtbProduct = pmtbProduct;
    }
   
    public Timestamp getCurrentExpTime() {
		return currentExpTime;
	}

	public void setCurrentExpTime(Timestamp currentExpTime) {
		this.currentExpTime = currentExpTime;
	}

	public Timestamp getNewExpTime() {
		return newExpTime;
	}

	public void setNewExpTime(Timestamp newExpTime) {
		this.newExpTime = newExpTime;
	}

	/**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("durationType").append("='").append(getDurationType()).append("' ");			
      buffer.append("durationLength").append("='").append(getDurationLength()).append("' ");			
      buffer.append("renewDate").append("='").append(getRenewDate()).append("' ");			
      buffer.append("currentExpDate").append("='").append(getCurrentExpDate()).append("' ");			
      buffer.append("newExpDate").append("='").append(getNewExpDate()).append("' ");			
      buffer.append("remarks").append("='").append(getRemarks()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("embossFlag").append("='").append(getEmbossFlag()).append("' ");
      buffer.append("currentExpTime").append("='").append(getCurrentExpTime()).append("' ");
      buffer.append("newExpTime").append("='").append(getNewExpTime()).append("' ");
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PmtbProductRenew) ) return false;
		 PmtbProductRenew castOther = ( PmtbProductRenew ) other; 
         
		 return ( (this.getProductRenewNo()==castOther.getProductRenewNo()) || ( this.getProductRenewNo()!=null && castOther.getProductRenewNo()!=null && this.getProductRenewNo().equals(castOther.getProductRenewNo()) ) )
 && ( (this.getDurationType()==castOther.getDurationType()) || ( this.getDurationType()!=null && castOther.getDurationType()!=null && this.getDurationType().equals(castOther.getDurationType()) ) )
 && ( (this.getDurationLength()==castOther.getDurationLength()) || ( this.getDurationLength()!=null && castOther.getDurationLength()!=null && this.getDurationLength().equals(castOther.getDurationLength()) ) )
 && ( (this.getRenewDate()==castOther.getRenewDate()) || ( this.getRenewDate()!=null && castOther.getRenewDate()!=null && this.getRenewDate().equals(castOther.getRenewDate()) ) )
 && ( (this.getCurrentExpDate()==castOther.getCurrentExpDate()) || ( this.getCurrentExpDate()!=null && castOther.getCurrentExpDate()!=null && this.getCurrentExpDate().equals(castOther.getCurrentExpDate()) ) )
 && ( (this.getNewExpDate()==castOther.getNewExpDate()) || ( this.getNewExpDate()!=null && castOther.getNewExpDate()!=null && this.getNewExpDate().equals(castOther.getNewExpDate()) ) )
 && ( (this.getRemarks()==castOther.getRemarks()) || ( this.getRemarks()!=null && castOther.getRemarks()!=null && this.getRemarks().equals(castOther.getRemarks()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getEmbossFlag()==castOther.getEmbossFlag()) || ( this.getEmbossFlag()!=null && castOther.getEmbossFlag()!=null && this.getEmbossFlag().equals(castOther.getEmbossFlag()) ) )
 && ( (this.getPmtbProduct()==castOther.getPmtbProduct()) || ( this.getPmtbProduct()!=null && castOther.getPmtbProduct()!=null && this.getPmtbProduct().equals(castOther.getPmtbProduct()) ) )
 && ( (this.getCurrentExpTime()==castOther.getCurrentExpTime()) || ( this.getCurrentExpTime()!=null && castOther.getCurrentExpTime()!=null && this.getCurrentExpTime().equals(castOther.getCurrentExpTime()) ) )
 && ( (this.getNewExpTime()==castOther.getNewExpTime()) || ( this.getNewExpTime()!=null && castOther.getNewExpTime()!=null && this.getNewExpTime().equals(castOther.getNewExpTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProductRenewNo() == null ? 0 : this.getProductRenewNo().hashCode() );
         result = 37 * result + ( getDurationType() == null ? 0 : this.getDurationType().hashCode() );
         result = 37 * result + ( getDurationLength() == null ? 0 : this.getDurationLength().hashCode() );
         result = 37 * result + ( getRenewDate() == null ? 0 : this.getRenewDate().hashCode() );
         result = 37 * result + ( getCurrentExpDate() == null ? 0 : this.getCurrentExpDate().hashCode() );
         result = 37 * result + ( getNewExpDate() == null ? 0 : this.getNewExpDate().hashCode() );
         result = 37 * result + ( getRemarks() == null ? 0 : this.getRemarks().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getEmbossFlag() == null ? 0 : this.getEmbossFlag().hashCode() );
         result = 37 * result + ( getPmtbProduct() == null ? 0 : this.getPmtbProduct().hashCode() );
         result = 37 * result + ( getCurrentExpTime() == null ? 0 : this.getCurrentExpTime().hashCode() );
         result = 37 * result + ( getNewExpTime() == null ? 0 : this.getNewExpTime().hashCode() );
         return result;
   }   
}