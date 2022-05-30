package com.cdgtaxi.ibs.common.model;

import java.sql.Timestamp;

public class IttbCpPymtReqReversal  implements java.io.Serializable {
	
   	// Fields    
	private Integer reqRevId;
	private String reqStatus;
    private String requestBy;
	private Timestamp requestDt;
    private Integer sentCtr;
    private String sentEmail;
    private Timestamp updatedDt;
    private String updatedBy;
    private Integer version;

    private IttbCpPymtReqSum ittbCpPymtReqSum;

    // Constructors

    /** default constructor */
    public IttbCpPymtReqReversal() {
    }

	/**
	 * @return the reqRevId
	 */
	public Integer getReqRevId() {
		return reqRevId;
	}

	/**
	 * @param reqRevId the reqRevId to set
	 */
	public void setReqRevId(Integer reqRevId) {
		this.reqRevId = reqRevId;
	}


	/**
	 * @return the requestBy
	 */
	public String getRequestBy() {
		return requestBy;
	}

	/**
	 * @param requestBy the requestBy to set
	 */
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}
	
    public Timestamp getRequestDt() {
        return this.requestDt;
    }
    
    public void setRequestDt(Timestamp requestDt) {
        this.requestDt = requestDt;
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
  

    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

 
    /**
	 * @return the sentCtr
	 */
	public Integer getSentCtr() {
		return sentCtr;
	}

	/**
	 * @param sentCtr the sentCtr to set
	 */
	public void setSentCtr(Integer sentCtr) {
		this.sentCtr = sentCtr;
	}

	/**
	 * @return the sentEmail
	 */
	public String getSentEmail() {
		return sentEmail;
	}

	/**
	 * @param sentEmail the sentEmail to set
	 */
	public void setSentEmail(String sentEmail) {
		this.sentEmail = sentEmail;
	}

	/**
	 * @return the reqStatus
	 */
	public String getReqStatus() {
		return reqStatus;
	}

	/**
	 * @param reqStatus the reqStatus to set
	 */
	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}



	public IttbCpPymtReqSum getIttbCpPymtReqSum() {
		return ittbCpPymtReqSum;
	}

	public void setIttbCpPymtReqSum(IttbCpPymtReqSum ittbCpPymtReqSum) {
		this.ittbCpPymtReqSum = ittbCpPymtReqSum;
	}
	
	/**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("reqRevId").append("='").append(getReqRevId()).append("' ");
      buffer.append("reqStatus").append("='").append(getReqStatus()).append("' ");
      buffer.append("requestBy").append("='").append(getRequestBy()).append("' ");
      buffer.append("requestDt").append("='").append(getRequestDt()).append("' ");
      buffer.append("sentCtr").append("='").append(getSentCtr()).append("' ");	
      buffer.append("sentEmail").append("='").append(getSentEmail()).append("' ");	
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");   
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");      
      return buffer.toString();
     }

     
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbCpPymtReqReversal) ) return false;
		 IttbCpPymtReqReversal castOther = ( IttbCpPymtReqReversal ) other; 
         
		 return ( (this.getReqRevId()==castOther.getReqRevId()) || ( this.getReqRevId()!=null && castOther.getReqRevId()!=null && this.getReqRevId().equals(castOther.getReqRevId()) ) )
 && ( (this.getReqStatus()==castOther.getReqStatus()) || ( this.getReqStatus()!=null && castOther.getReqStatus()!=null && this.getReqStatus().equals(castOther.getReqStatus()) ) )
 && ( (this.getRequestBy()==castOther.getRequestBy()) || ( this.getRequestBy()!=null && castOther.getRequestBy()!=null && this.getRequestBy().equals(castOther.getRequestBy()) ) )
 && ( (this.getRequestDt()==castOther.getRequestDt()) || ( this.getRequestDt()!=null && castOther.getRequestDt()!=null && this.getRequestDt().equals(castOther.getRequestDt()) ) )
 && ( (this.getSentCtr()==castOther.getSentCtr()) || ( this.getSentCtr()!=null && castOther.getSentCtr()!=null && this.getSentCtr().equals(castOther.getSentCtr()) ) )
 && ( (this.getSentEmail()==castOther.getSentEmail()) || ( this.getSentEmail()!=null && castOther.getSentEmail()!=null && this.getSentEmail().equals(castOther.getSentEmail()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) );
   
   }
   
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReqRevId() == null ? 0 : this.getReqRevId().hashCode() );
         result = 37 * result + ( getReqStatus() == null ? 0 : this.getReqStatus().hashCode() );
         result = 37 * result + ( getRequestBy() == null ? 0 : this.getRequestBy().hashCode() );
         result = 37 * result + ( getRequestDt() == null ? 0 : this.getRequestDt().hashCode() );
         result = 37 * result + ( getSentCtr() == null ? 0 : this.getSentCtr().hashCode() );
         result = 37 * result + ( getSentEmail() == null ? 0 : this.getSentEmail().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );         
         return result;
   }

}
