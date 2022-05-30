package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class IttbCpPymtReqDet  implements java.io.Serializable, Creatable, Updatable {
	
	private Integer pymtReqDetId;
	private BigDecimal topupAmount;
    private String nameOnProduct;
    private String cardNo;
    private BigDecimal creditBalance;
    private Date expiryDate;
    private Timestamp createdDt;
    private Timestamp updatedDt;
    private String updatedBy;
    private String createdBy;
    private Integer version;

    private IttbCpPymtReqSum ittbCpPymtReqSum;
    private PmtbProduct pmtbProduct;
    private PmtbProductType pmtbProductType;
    
    // Constructors

    /** default constructor */
    public IttbCpPymtReqDet() {
    }


    /** full constructor */
    public IttbCpPymtReqDet(Integer pymtReqDetId, BigDecimal topupAmount, String nameOnProduct,  String cardNo, BigDecimal creditBalance, Date expiryDate, Timestamp createdDate, Timestamp updatedDate, String updatedBy, String createdBy, Integer version) {
        this.pymtReqDetId = pymtReqDetId;
        this.topupAmount = topupAmount;
        this.nameOnProduct = nameOnProduct;
        this.cardNo = cardNo;
        this.creditBalance = creditBalance;
        this.expiryDate = expiryDate;
        this.createdDt = createdDate;
        this.updatedDt = updatedDate;
        this.updatedBy = updatedBy;
        this.createdBy = createdBy;
        this.version = version;
    }
    


    public Timestamp getCreatedDt() {
        return this.createdDt;
    }
    
    public void setCreatedDt(Timestamp createdDate) {
        this.createdDt = createdDate;
    }

    public Timestamp getUpdatedDt() {
        return this.updatedDt;
    }
    
    public void setUpdatedDt(Timestamp updatedDate) {
        this.updatedDt = updatedDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

   
  

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("pymtReqDetId").append("='").append(getPymtReqDetId()).append("' ");
      buffer.append("topupAmount").append("='").append(getTopupAmount()).append("' ");
      buffer.append("nameOnProduct").append("='").append(getNameOnProduct()).append("' ");
      buffer.append("cardNo").append("='").append(getCardNo()).append("' ");
      buffer.append("creditBalance").append("='").append(getCreditBalance()).append("' ");
      buffer.append("expiryDate").append("='").append(getExpiryDate()).append("' ");
      buffer.append("createdDate").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("updatedDate").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


    
     
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbCpPymtReqDet) ) return false;
		 IttbCpPymtReqDet castOther = ( IttbCpPymtReqDet ) other; 
         
		 return ( (this.getPymtReqDetId()==castOther.getPymtReqDetId()) || ( this.getPymtReqDetId()!=null && castOther.getPymtReqDetId()!=null && this.getPymtReqDetId().equals(castOther.getPymtReqDetId()) ) )
 && ( (this.getTopupAmount()==castOther.getTopupAmount()) || ( this.getTopupAmount()!=null && castOther.getTopupAmount()!=null && this.getTopupAmount().equals(castOther.getTopupAmount()) ) )
 && ( (this.getNameOnProduct()==castOther.getNameOnProduct()) || ( this.getNameOnProduct()!=null && castOther.getNameOnProduct()!=null && this.getNameOnProduct().equals(castOther.getNameOnProduct()) ) )
 && ( (this.getCardNo()==castOther.getCardNo()) || ( this.getCardNo()!=null && castOther.getCardNo()!=null && this.getCardNo().equals(castOther.getCardNo()) ) )
 && ( (this.getCreditBalance()==castOther.getCreditBalance()) || ( this.getCreditBalance()!=null && castOther.getCreditBalance()!=null && this.getCreditBalance().equals(castOther.getCreditBalance()) ) )
 && ( (this.getExpiryDate()==castOther.getExpiryDate()) || ( this.getExpiryDate()!=null && castOther.getExpiryDate()!=null && this.getExpiryDate().equals(castOther.getExpiryDate()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) );
   
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPymtReqDetId() == null ? 0 : this.getPymtReqDetId().hashCode() );
         result = 37 * result + ( getTopupAmount() == null ? 0 : this.getTopupAmount().hashCode() );
         result = 37 * result + ( getNameOnProduct() == null ? 0 : this.getNameOnProduct().hashCode() );
         result = 37 * result + ( getCardNo() == null ? 0 : this.getCardNo().hashCode() );
         result = 37 * result + ( getCreditBalance() == null ? 0 : this.getCreditBalance().hashCode() );
         result = 37 * result + ( getExpiryDate() == null ? 0 : this.getExpiryDate().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         return result;
   }


	public Integer getPymtReqDetId() {
		return pymtReqDetId;
	}
	
	
	public BigDecimal getTopupAmount() {
		return topupAmount;
	}
	
	
	public String getNameOnProduct() {
		return nameOnProduct;
	}
	
	
	public String getCardNo() {
		return cardNo;
	}
	
	
	public BigDecimal getCreditBalance() {
		return creditBalance;
	}
	
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	
	public void setPymtReqDetId(Integer pymtReqDetId) {
		this.pymtReqDetId = pymtReqDetId;
	}
	
	
	public void setTopupAmount(BigDecimal topupAmount) {
		this.topupAmount = topupAmount;
	}
	
	
	public void setNameOnProduct(String nameOnProduct) {
		this.nameOnProduct = nameOnProduct;
	}
	
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}
	
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public IttbCpPymtReqSum getIttbCpPymtReqSum() {
		return ittbCpPymtReqSum;
	}


	public void setIttbCpPymtReqSum(IttbCpPymtReqSum ittbCpPymtReqSum) {
		this.ittbCpPymtReqSum = ittbCpPymtReqSum;
	}


	public PmtbProduct getPmtbProduct() {
		return pmtbProduct;
	}


	public void setPmtbProduct(PmtbProduct pmtbProduct) {
		this.pmtbProduct = pmtbProduct;
	}


	public PmtbProductType getPmtbProductType() {
		return pmtbProductType;
	}


	public void setPmtbProductType(PmtbProductType pmtbProductType) {
		this.pmtbProductType = pmtbProductType;
	}


	

  
}
