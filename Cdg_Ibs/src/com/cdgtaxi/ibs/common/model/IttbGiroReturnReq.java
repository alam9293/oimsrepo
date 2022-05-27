package com.cdgtaxi.ibs.common.model;
// Generated Mar 15, 2011 3:19:42 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Date;
import java.sql.Timestamp;

import com.cdgtaxi.ibs.acl.security.Auditable;


/**
 * IttbGiroReturnReq generated by hbm2java
 */

@SuppressWarnings("serial")
public class IttbGiroReturnReq  implements java.io.Serializable, Auditable, Creatable, Updatable {


    // Fields    

    private Long reqNo;
    private Date processingDate;
    private int processingTime;
    private String status;
    private Integer version;
    private String createdBy;
    private Timestamp createdDt;
    private String updatedBy;
    private Timestamp updatedDt;
    private IttbGiroUobHeader ittbGiroUobHeader;


    // Constructors

    /** default constructor */
    public IttbGiroReturnReq() {
    }

	/** minimal constructor */
    public IttbGiroReturnReq(Date processingDate, int processingTime, String status) {
        this.processingDate = processingDate;
        this.processingTime = processingTime;
        this.status = status;
    }
    
    /** full constructor */
    public IttbGiroReturnReq(Date processingDate, int processingTime, String status, Integer version, String createdBy, Timestamp createdDt, String updatedBy, Timestamp updatedDt, IttbGiroUobHeader ittbGiroUobHeader) {
        this.processingDate = processingDate;
        this.processingTime = processingTime;
        this.status = status;
        this.version = version;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.updatedBy = updatedBy;
        this.updatedDt = updatedDt;
        this.ittbGiroUobHeader = ittbGiroUobHeader;
    }
    

   
    // Property accessors

    public Long getReqNo() {
        return this.reqNo;
    }
    
    public void setReqNo(Long reqNo) {
        this.reqNo = reqNo;
    }

    public Date getProcessingDate() {
        return this.processingDate;
    }
    
    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    public int getProcessingTime() {
        return this.processingTime;
    }
    
    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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

    public Timestamp getCreatedDt() {
        return this.createdDt;
    }
    
    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDt() {
        return this.updatedDt;
    }
    
    public void setUpdatedDt(Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

    public IttbGiroUobHeader getIttbGiroUobHeader() {
        return this.ittbGiroUobHeader;
    }
    
    public void setIttbGiroUobHeader(IttbGiroUobHeader ittbGiroUobHeader) {
        this.ittbGiroUobHeader = ittbGiroUobHeader;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("processingDate").append("='").append(getProcessingDate()).append("' ");			
      buffer.append("processingTime").append("='").append(getProcessingTime()).append("' ");			
      buffer.append("status").append("='").append(getStatus()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbGiroReturnReq) ) return false;
		 IttbGiroReturnReq castOther = ( IttbGiroReturnReq ) other; 
         
		 return ( (this.getReqNo()==castOther.getReqNo()) || ( this.getReqNo()!=null && castOther.getReqNo()!=null && this.getReqNo().equals(castOther.getReqNo()) ) )
 && ( (this.getProcessingDate()==castOther.getProcessingDate()) || ( this.getProcessingDate()!=null && castOther.getProcessingDate()!=null && this.getProcessingDate().equals(castOther.getProcessingDate()) ) )
 && (this.getProcessingTime()==castOther.getProcessingTime())
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getIttbGiroUobHeader()==castOther.getIttbGiroUobHeader()) || ( this.getIttbGiroUobHeader()!=null && castOther.getIttbGiroUobHeader()!=null && this.getIttbGiroUobHeader().equals(castOther.getIttbGiroUobHeader()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReqNo() == null ? 0 : this.getReqNo().hashCode() );
         result = 37 * result + ( getProcessingDate() == null ? 0 : this.getProcessingDate().hashCode() );
         result = 37 * result + this.getProcessingTime();
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getIttbGiroUobHeader() == null ? 0 : this.getIttbGiroUobHeader().hashCode() );
         return result;
   }   





}
