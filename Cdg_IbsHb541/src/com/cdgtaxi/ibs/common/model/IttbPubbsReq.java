package com.cdgtaxi.ibs.common.model;
// Generated Apr 12, 2012 5:57:27 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.cdgtaxi.ibs.acl.security.Auditable;


/**
 * IttbPubbsReq generated by hbm2java
 */

public class IttbPubbsReq  implements java.io.Serializable, Auditable, Creatable, Updatable {


    // Fields    

    private Integer reqNo;
    /**
     * P - Pending <Br>
     * G - Progress <Br>
     * E - Error <Br>
     * C - Completed <Br>
     */
    private String status;
    private String fileName;
    private String returnFileName;
    private Timestamp returnFileUploadDt;
    /**
     * Manual run input - Invoice No Range
     */
    private Long invoiceNoFrom;
    /**
     * Manual run input - Invoice No Range
     */
    private Long invoiceNoTo;
    /**
     * Manual run input - Invoice Date
     */
    private Date invoiceDate;
    private Integer version;
    private String createdBy;
    private Timestamp createdDt;
    private String updatedBy;
    private Timestamp updatedDt;
    private Set<IttbPubbsDtl> ittbPubbsDtls = new HashSet<IttbPubbsDtl>(0);
    /**
     * Manual run input - Account No and/or Account Name
     */
    private AmtbAccount amtbAccount;
    /**
     * Manual run input - Bill Gen Request No
     */
    private BmtbBillGenReq bmtbBillGenReq;


    // Constructors

    /** default constructor */
    public IttbPubbsReq() {
    }

	/** minimal constructor */
    public IttbPubbsReq(String status, String fileName) {
        this.status = status;
        this.fileName = fileName;
    }
    
    /** full constructor */
    public IttbPubbsReq(String status, String fileName, String returnFileName, Timestamp returnFileUploadDt, Long invoiceNoFrom, Long invoiceNoTo, Date invoiceDate, Integer version, String createdBy, Timestamp createdDt, String updatedBy, Timestamp updatedDt, Set<IttbPubbsDtl> ittbPubbsDtls, AmtbAccount amtbAccount, BmtbBillGenReq bmtbBillGenReq) {
        this.status = status;
        this.fileName = fileName;
        this.returnFileName = returnFileName;
        this.returnFileUploadDt = returnFileUploadDt;
        this.invoiceNoFrom = invoiceNoFrom;
        this.invoiceNoTo = invoiceNoTo;
        this.invoiceDate = invoiceDate;
        this.version = version;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.updatedBy = updatedBy;
        this.updatedDt = updatedDt;
        this.ittbPubbsDtls = ittbPubbsDtls;
        this.amtbAccount = amtbAccount;
        this.bmtbBillGenReq = bmtbBillGenReq;
    }
    

   
    // Property accessors

    public Integer getReqNo() {
        return this.reqNo;
    }
    
    public void setReqNo(Integer reqNo) {
        this.reqNo = reqNo;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReturnFileName() {
        return this.returnFileName;
    }
    
    public void setReturnFileName(String returnFileName) {
        this.returnFileName = returnFileName;
    }

    public Timestamp getReturnFileUploadDt() {
        return this.returnFileUploadDt;
    }
    
    public void setReturnFileUploadDt(Timestamp returnFileUploadDt) {
        this.returnFileUploadDt = returnFileUploadDt;
    }

    public Long getInvoiceNoFrom() {
        return this.invoiceNoFrom;
    }
    
    public void setInvoiceNoFrom(Long invoiceNoFrom) {
        this.invoiceNoFrom = invoiceNoFrom;
    }

    public Long getInvoiceNoTo() {
        return this.invoiceNoTo;
    }
    
    public void setInvoiceNoTo(Long invoiceNoTo) {
        this.invoiceNoTo = invoiceNoTo;
    }

    public Date getInvoiceDate() {
        return this.invoiceDate;
    }
    
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public Set<IttbPubbsDtl> getIttbPubbsDtls() {
        return this.ittbPubbsDtls;
    }
    
    public void setIttbPubbsDtls(Set<IttbPubbsDtl> ittbPubbsDtls) {
        this.ittbPubbsDtls = ittbPubbsDtls;
    }

    public AmtbAccount getAmtbAccount() {
        return this.amtbAccount;
    }
    
    public void setAmtbAccount(AmtbAccount amtbAccount) {
        this.amtbAccount = amtbAccount;
    }

    public BmtbBillGenReq getBmtbBillGenReq() {
        return this.bmtbBillGenReq;
    }
    
    public void setBmtbBillGenReq(BmtbBillGenReq bmtbBillGenReq) {
        this.bmtbBillGenReq = bmtbBillGenReq;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("status").append("='").append(getStatus()).append("' ");			
      buffer.append("fileName").append("='").append(getFileName()).append("' ");			
      buffer.append("returnFileName").append("='").append(getReturnFileName()).append("' ");			
      buffer.append("returnFileUploadDt").append("='").append(getReturnFileUploadDt()).append("' ");			
      buffer.append("invoiceNoFrom").append("='").append(getInvoiceNoFrom()).append("' ");			
      buffer.append("invoiceNoTo").append("='").append(getInvoiceNoTo()).append("' ");			
      buffer.append("invoiceDate").append("='").append(getInvoiceDate()).append("' ");			
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
		 if ( !(other instanceof IttbPubbsReq) ) return false;
		 IttbPubbsReq castOther = ( IttbPubbsReq ) other; 
         
		 return ( (this.getReqNo()==castOther.getReqNo()) || ( this.getReqNo()!=null && castOther.getReqNo()!=null && this.getReqNo().equals(castOther.getReqNo()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getFileName()==castOther.getFileName()) || ( this.getFileName()!=null && castOther.getFileName()!=null && this.getFileName().equals(castOther.getFileName()) ) )
 && ( (this.getReturnFileName()==castOther.getReturnFileName()) || ( this.getReturnFileName()!=null && castOther.getReturnFileName()!=null && this.getReturnFileName().equals(castOther.getReturnFileName()) ) )
 && ( (this.getReturnFileUploadDt()==castOther.getReturnFileUploadDt()) || ( this.getReturnFileUploadDt()!=null && castOther.getReturnFileUploadDt()!=null && this.getReturnFileUploadDt().equals(castOther.getReturnFileUploadDt()) ) )
 && ( (this.getInvoiceNoFrom()==castOther.getInvoiceNoFrom()) || ( this.getInvoiceNoFrom()!=null && castOther.getInvoiceNoFrom()!=null && this.getInvoiceNoFrom().equals(castOther.getInvoiceNoFrom()) ) )
 && ( (this.getInvoiceNoTo()==castOther.getInvoiceNoTo()) || ( this.getInvoiceNoTo()!=null && castOther.getInvoiceNoTo()!=null && this.getInvoiceNoTo().equals(castOther.getInvoiceNoTo()) ) )
 && ( (this.getInvoiceDate()==castOther.getInvoiceDate()) || ( this.getInvoiceDate()!=null && castOther.getInvoiceDate()!=null && this.getInvoiceDate().equals(castOther.getInvoiceDate()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getAmtbAccount()==castOther.getAmtbAccount()) || ( this.getAmtbAccount()!=null && castOther.getAmtbAccount()!=null && this.getAmtbAccount().equals(castOther.getAmtbAccount()) ) )
 && ( (this.getBmtbBillGenReq()==castOther.getBmtbBillGenReq()) || ( this.getBmtbBillGenReq()!=null && castOther.getBmtbBillGenReq()!=null && this.getBmtbBillGenReq().equals(castOther.getBmtbBillGenReq()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReqNo() == null ? 0 : this.getReqNo().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getFileName() == null ? 0 : this.getFileName().hashCode() );
         result = 37 * result + ( getReturnFileName() == null ? 0 : this.getReturnFileName().hashCode() );
         result = 37 * result + ( getReturnFileUploadDt() == null ? 0 : this.getReturnFileUploadDt().hashCode() );
         result = 37 * result + ( getInvoiceNoFrom() == null ? 0 : this.getInvoiceNoFrom().hashCode() );
         result = 37 * result + ( getInvoiceNoTo() == null ? 0 : this.getInvoiceNoTo().hashCode() );
         result = 37 * result + ( getInvoiceDate() == null ? 0 : this.getInvoiceDate().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         
         result = 37 * result + ( getAmtbAccount() == null ? 0 : this.getAmtbAccount().hashCode() );
         result = 37 * result + ( getBmtbBillGenReq() == null ? 0 : this.getBmtbBillGenReq().hashCode() );
         return result;
   }   





}
