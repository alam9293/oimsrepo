package com.cdgtaxi.ibs.common.model;
// Generated Jul 20, 2009 4:09:44 PM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.cdgtaxi.ibs.master.model.MstbMasterTable;


/**
 * PmtbProductReplacement generated by hbm2java
 */
//,Auditable
public class PmtbProductReplacement  implements java.io.Serializable,Creatable,Updatable {


    // Fields    

    private BigDecimal productReplacementNo;
    private String currentCardNo;
    private String newCardNo;
    private Date replacementDate;
    private BigDecimal replacementFee;
    private String waiveReplacement;
    private String replacementRemarks;
    private Date currentExpDate;
    private Date newExpDate;
    private String embossFlag;
    private String smsTopupSent;
    private String billedFlag;
    private Integer version;
    private Timestamp createdDt;
    private String createdBy;
    private Timestamp updatedDt;
    private String updatedBy;
    private MstbMasterTable mstbMasterTable;
    private PmtbProduct pmtbProduct;
    private Timestamp currentExpTime;
    private Timestamp newExpTime;
    private String oldCardNo;
    private BigDecimal newProductNo;


    // Constructors

    /** default constructor */
    public PmtbProductReplacement() {
    }

	/** minimal constructor */
    public PmtbProductReplacement(String currentCardNo, String newCardNo, Date replacementDate, String waiveReplacement, String replacementRemarks, Date currentExpDate, Date newExpDate) {
        this.currentCardNo = currentCardNo;
        this.newCardNo = newCardNo;
        this.replacementDate = replacementDate;
        this.waiveReplacement = waiveReplacement;
        this.replacementRemarks = replacementRemarks;
        this.currentExpDate = currentExpDate;
        this.newExpDate = newExpDate;
    }
    
    /** full constructor */
    public PmtbProductReplacement(String currentCardNo, String newCardNo, Date replacementDate, BigDecimal replacementFee, String waiveReplacement, String replacementRemarks, Date currentExpDate, Date newExpDate, String embossFlag, String smsTopupSent, String billedFlag, Integer version, Timestamp createdDt, String createdBy, Timestamp updatedDt, String updatedBy, MstbMasterTable mstbMasterTable, PmtbProduct pmtbProduct, Timestamp currentExpTime, Timestamp newExpTime, String oldCardNo, BigDecimal newProductNo) {
        this.currentCardNo = currentCardNo;
        this.newCardNo = newCardNo;
        this.replacementDate = replacementDate;
        this.replacementFee = replacementFee;
        this.waiveReplacement = waiveReplacement;
        this.replacementRemarks = replacementRemarks;
        this.currentExpDate = currentExpDate;
        this.newExpDate = newExpDate;
        this.embossFlag = embossFlag;
        this.smsTopupSent = smsTopupSent;
        this.billedFlag = billedFlag;
        this.version = version;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updatedDt = updatedDt;
        this.updatedBy = updatedBy;
        this.mstbMasterTable = mstbMasterTable;
        this.pmtbProduct = pmtbProduct;
       
        this.currentExpTime = currentExpTime;
        this.newExpTime = newExpTime;
       
        this.oldCardNo = oldCardNo;
        this.newProductNo = newProductNo;
    }
    

   
    // Property accessors

	public BigDecimal getProductReplacementNo() {
        return this.productReplacementNo;
    }
    
    public void setProductReplacementNo(BigDecimal productReplacementNo) {
        this.productReplacementNo = productReplacementNo;
    }

    public String getCurrentCardNo() {
        return this.currentCardNo;
    }
    
    public void setCurrentCardNo(String currentCardNo) {
        this.currentCardNo = currentCardNo;
    }

    public String getNewCardNo() {
        return this.newCardNo;
    }
    
    public void setNewCardNo(String newCardNo) {
        this.newCardNo = newCardNo;
    }

    public Date getReplacementDate() {
        return this.replacementDate;
    }
    
    public void setReplacementDate(Date replacementDate) {
        this.replacementDate = replacementDate;
    }

    public BigDecimal getReplacementFee() {
        return this.replacementFee;
    }
    
    public void setReplacementFee(BigDecimal replacementFee) {
        this.replacementFee = replacementFee;
    }

    public String getWaiveReplacement() {
        return this.waiveReplacement;
    }
    
    public void setWaiveReplacement(String waiveReplacement) {
        this.waiveReplacement = waiveReplacement;
    }

    public String getReplacementRemarks() {
        return this.replacementRemarks;
    }
    
    public void setReplacementRemarks(String replacementRemarks) {
        this.replacementRemarks = replacementRemarks;
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
	
    public String getEmbossFlag() {
        return this.embossFlag;
    }
    
    public void setEmbossFlag(String embossFlag) {
        this.embossFlag = embossFlag;
    }
    
    public String getSmsTopupSent() {
		return this.smsTopupSent;
	}

	public void setSmsTopupSent(String smsTopupSent) {
		this.smsTopupSent = smsTopupSent;
	}
    
    public String getBilledFlag() {
        return this.billedFlag;
    }
    
    public void setBilledFlag(String billedFlag) {
        this.billedFlag = billedFlag;
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

    public MstbMasterTable getMstbMasterTable() {
        return this.mstbMasterTable;
    }
    
    public void setMstbMasterTable(MstbMasterTable mstbMasterTable) {
        this.mstbMasterTable = mstbMasterTable;
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

	public String getOldCardNo() {
		return oldCardNo;
	}

	public void setOldCardNo(String oldCardNo) {
		this.oldCardNo = oldCardNo;
	}
	
	public BigDecimal getNewProductNo() {
		return newProductNo;
	}

	public void setNewProductNo(BigDecimal newProductNo) {
		this.newProductNo = newProductNo;
	}

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("currentCardNo").append("='").append(getCurrentCardNo()).append("' ");			
      buffer.append("newCardNo").append("='").append(getNewCardNo()).append("' ");			
      buffer.append("replacementDate").append("='").append(getReplacementDate()).append("' ");			
      buffer.append("replacementFee").append("='").append(getReplacementFee()).append("' ");			
      buffer.append("waiveReplacement").append("='").append(getWaiveReplacement()).append("' ");			
      buffer.append("replacementRemarks").append("='").append(getReplacementRemarks()).append("' ");			
      buffer.append("currentExpDate").append("='").append(getCurrentExpDate()).append("' ");			
      buffer.append("newExpDate").append("='").append(getNewExpDate()).append("' ");			
      buffer.append("embossFlag").append("='").append(getEmbossFlag()).append("' ");	
      buffer.append("smsTopupSent").append("='").append(getSmsTopupSent()).append("' ");
      buffer.append("billedFlag").append("='").append(getBilledFlag()).append("' ");

      buffer.append("currentExpTime").append("='").append(getCurrentExpTime()).append("' ");
      buffer.append("newExpTime").append("='").append(getNewExpTime()).append("' ");
      buffer.append("oldCardNo").append("='").append(getOldCardNo()).append("' ");
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("newProductNo").append("='").append(getNewProductNo()).append("' ");
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PmtbProductReplacement) ) return false;
		 PmtbProductReplacement castOther = ( PmtbProductReplacement ) other; 
         
		 return ( (this.getProductReplacementNo()==castOther.getProductReplacementNo()) || ( this.getProductReplacementNo()!=null && castOther.getProductReplacementNo()!=null && this.getProductReplacementNo().equals(castOther.getProductReplacementNo()) ) )
 && ( (this.getCurrentCardNo()==castOther.getCurrentCardNo()) || ( this.getCurrentCardNo()!=null && castOther.getCurrentCardNo()!=null && this.getCurrentCardNo().equals(castOther.getCurrentCardNo()) ) )
 && ( (this.getNewCardNo()==castOther.getNewCardNo()) || ( this.getNewCardNo()!=null && castOther.getNewCardNo()!=null && this.getNewCardNo().equals(castOther.getNewCardNo()) ) )
 && ( (this.getReplacementDate()==castOther.getReplacementDate()) || ( this.getReplacementDate()!=null && castOther.getReplacementDate()!=null && this.getReplacementDate().equals(castOther.getReplacementDate()) ) )
 && ( (this.getReplacementFee()==castOther.getReplacementFee()) || ( this.getReplacementFee()!=null && castOther.getReplacementFee()!=null && this.getReplacementFee().equals(castOther.getReplacementFee()) ) )
 && ( (this.getWaiveReplacement()==castOther.getWaiveReplacement()) || ( this.getWaiveReplacement()!=null && castOther.getWaiveReplacement()!=null && this.getWaiveReplacement().equals(castOther.getWaiveReplacement()) ) )
 && ( (this.getReplacementRemarks()==castOther.getReplacementRemarks()) || ( this.getReplacementRemarks()!=null && castOther.getReplacementRemarks()!=null && this.getReplacementRemarks().equals(castOther.getReplacementRemarks()) ) )
 && ( (this.getCurrentExpDate()==castOther.getCurrentExpDate()) || ( this.getCurrentExpDate()!=null && castOther.getCurrentExpDate()!=null && this.getCurrentExpDate().equals(castOther.getCurrentExpDate()) ) )
 && ( (this.getNewExpDate()==castOther.getNewExpDate()) || ( this.getNewExpDate()!=null && castOther.getNewExpDate()!=null && this.getNewExpDate().equals(castOther.getNewExpDate()) ) )
 && ( (this.getEmbossFlag()==castOther.getEmbossFlag()) || ( this.getEmbossFlag()!=null && castOther.getEmbossFlag()!=null && this.getEmbossFlag().equals(castOther.getEmbossFlag()) ) )
 && ( (this.getSmsTopupSent() == castOther.getSmsTopupSent()) || (this.getSmsTopupSent() != null && castOther.getSmsTopupSent() != null && this.getSmsTopupSent().equals(castOther.getSmsTopupSent())))
 && ( (this.getBilledFlag()==castOther.getBilledFlag()) || ( this.getBilledFlag()!=null && castOther.getBilledFlag()!=null && this.getBilledFlag().equals(castOther.getBilledFlag()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getMstbMasterTable()==castOther.getMstbMasterTable()) || ( this.getMstbMasterTable()!=null && castOther.getMstbMasterTable()!=null && this.getMstbMasterTable().equals(castOther.getMstbMasterTable()) ) )
 && ( (this.getPmtbProduct()==castOther.getPmtbProduct()) || ( this.getPmtbProduct()!=null && castOther.getPmtbProduct()!=null && this.getPmtbProduct().equals(castOther.getPmtbProduct()) ) )

 && ( (this.getCurrentExpTime()==castOther.getCurrentExpTime()) || ( this.getCurrentExpTime()!=null && castOther.getCurrentExpTime()!=null && this.getCurrentExpTime().equals(castOther.getCurrentExpTime()) ) )
 && ( (this.getNewExpTime()==castOther.getNewExpTime()) || ( this.getNewExpTime()!=null && castOther.getNewExpTime()!=null && this.getNewExpTime().equals(castOther.getNewExpTime()) ) )
 && ( (this.getNewProductNo()==castOther.getNewProductNo()) || ( this.getNewProductNo()!=null && castOther.getNewProductNo()!=null && this.getNewProductNo().equals(castOther.getNewProductNo()) ) )
 && ( (this.getOldCardNo()==castOther.getOldCardNo()) || ( this.getOldCardNo()!=null && castOther.getOldCardNo()!=null && this.getOldCardNo().equals(castOther.getOldCardNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProductReplacementNo() == null ? 0 : this.getProductReplacementNo().hashCode() );
         result = 37 * result + ( getCurrentCardNo() == null ? 0 : this.getCurrentCardNo().hashCode() );
         result = 37 * result + ( getNewCardNo() == null ? 0 : this.getNewCardNo().hashCode() );
         result = 37 * result + ( getReplacementDate() == null ? 0 : this.getReplacementDate().hashCode() );
         result = 37 * result + ( getReplacementFee() == null ? 0 : this.getReplacementFee().hashCode() );
         result = 37 * result + ( getWaiveReplacement() == null ? 0 : this.getWaiveReplacement().hashCode() );
         result = 37 * result + ( getReplacementRemarks() == null ? 0 : this.getReplacementRemarks().hashCode() );
         result = 37 * result + ( getCurrentExpDate() == null ? 0 : this.getCurrentExpDate().hashCode() );
         result = 37 * result + ( getNewExpDate() == null ? 0 : this.getNewExpDate().hashCode() );
         result = 37 * result + ( getEmbossFlag() == null ? 0 : this.getEmbossFlag().hashCode() );
         result = 37 * result + ( getSmsTopupSent() == null ? 0 : this.getSmsTopupSent().hashCode());
         result = 37 * result + ( getBilledFlag() == null ? 0 : this.getBilledFlag().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getMstbMasterTable() == null ? 0 : this.getMstbMasterTable().hashCode() );
         result = 37 * result + ( getPmtbProduct() == null ? 0 : this.getPmtbProduct().hashCode() );
         result = 37 * result + ( getCurrentExpTime() == null ? 0 : this.getCurrentExpTime().hashCode() );
         result = 37 * result + ( getNewExpTime() == null ? 0 : this.getNewExpTime().hashCode() );
       
         result = 37 * result + ( getOldCardNo() == null ? 0 : this.getOldCardNo().hashCode() );
         result = 37 * result + ( getNewProductNo() == null ? 0 : this.getNewProductNo().hashCode() );
         return result;
   }   
}