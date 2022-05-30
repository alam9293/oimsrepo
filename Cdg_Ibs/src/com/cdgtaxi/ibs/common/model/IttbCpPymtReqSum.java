package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class IttbCpPymtReqSum  implements java.io.Serializable, Creatable, Updatable {
	
    // Fields    

	private Integer pymtReqId;
    private String loginId;
    private String mid;
    private String refNo;
    private String currency;
    private BigDecimal topupAmount;
    private String status;
    private Timestamp createdDt;
    private Timestamp updatedDt;
    private String updatedBy;
    private String createdBy;
    private Integer version;
  
    private Integer loginNo;
    // Constructors

    /** default constructor */
    public IttbCpPymtReqSum() {
    }


    /** full constructor */
    public IttbCpPymtReqSum(Integer pymtReqId, String loginId, String mid, String refNo, String currency, BigDecimal topupAmount, String status, Timestamp createdDate, Timestamp updatedDate, String updatedBy, String createdBy, Integer version, Integer loginNo) {
        this.pymtReqId = pymtReqId;
        this.loginId = loginId;
        this.mid = mid;
        this.refNo = refNo;
        this.currency = currency;
        this.topupAmount = topupAmount;
        this.status = status;
        this.createdDt = createdDate;
        this.updatedDt = updatedDate;
        this.updatedBy = updatedBy;
        this.createdBy = createdBy;
        this.version = version;
        this.loginNo = loginNo;
    }
    


    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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

   
    public Integer getLoginNo() {
    	return loginNo;
    }

    public void setLoginNo(Integer loginNo) {
    	this.loginNo = loginNo;
    } 

   
    
    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("pymtReqId").append("='").append(getPymtReqId()).append("' ");
      buffer.append("loginId").append("='").append(getLoginId()).append("' ");
      buffer.append("mid").append("='").append(getMid()).append("' ");
      buffer.append("refNo").append("='").append(getRefNo()).append("' ");
      buffer.append("currency").append("='").append(getCurrency()).append("' ");
      buffer.append("topupAmount").append("='").append(getTopupAmount()).append("' ");
      buffer.append("status").append("='").append(getStatus()).append("' ");			
      buffer.append("createdDate").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("updatedDate").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");		
      buffer.append("loginNo").append("='").append(getLoginNo()).append("' ");		
      buffer.append("]");
      
      return buffer.toString();
     }


    
     
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbCpPymtReqSum) ) return false;
		 IttbCpPymtReqSum castOther = ( IttbCpPymtReqSum ) other; 
         
		 return ( (this.getPymtReqId()==castOther.getPymtReqId()) || ( this.getPymtReqId()!=null && castOther.getPymtReqId()!=null && this.getPymtReqId().equals(castOther.getPymtReqId()) ) )
 && ( (this.getLoginId()==castOther.getLoginId()) || ( this.getLoginId()!=null && castOther.getLoginId()!=null && this.getLoginId().equals(castOther.getLoginId()) ) )
 && ( (this.getMid()==castOther.getMid()) || ( this.getMid()!=null && castOther.getMid()!=null && this.getMid().equals(castOther.getMid()) ) )
 && ( (this.getRefNo()==castOther.getRefNo()) || ( this.getRefNo()!=null && castOther.getRefNo()!=null && this.getRefNo().equals(castOther.getRefNo()) ) )
 && ( (this.getCurrency()==castOther.getCurrency()) || ( this.getCurrency()!=null && castOther.getCurrency()!=null && this.getCurrency().equals(castOther.getCurrency()) ) )
 && ( (this.getTopupAmount()==castOther.getTopupAmount()) || ( this.getTopupAmount()!=null && castOther.getTopupAmount()!=null && this.getTopupAmount().equals(castOther.getTopupAmount()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
&& ( (this.getLoginNo()==castOther.getLoginNo()) || ( this.getLoginNo()!=null && castOther.getLoginNo()!=null && this.getLoginNo().equals(castOther.getLoginNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPymtReqId() == null ? 0 : this.getPymtReqId().hashCode() );
         result = 37 * result + ( getLoginId() == null ? 0 : this.getLoginId().hashCode() );
         result = 37 * result + ( getLoginNo() == null ? 0 : this.getLoginNo().hashCode() );
         result = 37 * result + ( getMid() == null ? 0 : this.getMid().hashCode() );
         result = 37 * result + ( getRefNo() == null ? 0 : this.getRefNo().hashCode() );
         result = 37 * result + ( getCurrency() == null ? 0 : this.getCurrency().hashCode() );
         result = 37 * result + ( getTopupAmount() == null ? 0 : this.getTopupAmount().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getLoginNo() == null ? 0 : this.getLoginNo().hashCode() );
         return result;
   }


	public Integer getPymtReqId() {
		return pymtReqId;
	}
	
	
	public String getLoginId() {
		return loginId;
	}
	
	
	public String getMid() {
		return mid;
	}
	
	
	public String getRefNo() {
		return refNo;
	}
	
	
	public String getCurrency() {
		return currency;
	}
	
	
	public BigDecimal getTopupAmount() {
		return topupAmount;
	}
	
	
	public void setPymtReqId(Integer pymtReqId) {
		this.pymtReqId = pymtReqId;
	}
	
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
	public void setTopupAmount(BigDecimal topupAmount) {
		this.topupAmount = topupAmount;
	}

  
}
