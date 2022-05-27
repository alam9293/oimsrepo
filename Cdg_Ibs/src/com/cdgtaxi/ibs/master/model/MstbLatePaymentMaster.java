package com.cdgtaxi.ibs.master.model;
// Generated Jul 20, 2009 6:37:00 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.cdgtaxi.ibs.common.model.AmtbAcctLatePymt;
import com.cdgtaxi.ibs.common.model.AmtbAcctLatePymtReq;
import com.cdgtaxi.ibs.common.model.AmtbApplication;
import com.cdgtaxi.ibs.common.model.Creatable;
import com.cdgtaxi.ibs.common.model.Updatable;


/**
 * MstbLatePaymentMaster generated by hbm2java
 */

public class MstbLatePaymentMaster  implements java.io.Serializable, Creatable, Updatable {


    // Fields    

    private Integer latePaymentPlanNo;
    private String latePaymentPlanName;
    private Timestamp createdDt;
    private String createdBy;
    private Timestamp updatedDt;
    private String updatedBy;
    private Integer version;
    private Set<AmtbAcctLatePymtReq> amtbAcctLatePymtReqs = new HashSet<AmtbAcctLatePymtReq>(0);
    private Set<AmtbApplication> amtbApplications = new HashSet<AmtbApplication>(0);
    private Set<MstbLatePaymentDetail> mstbLatePaymentDetails = new HashSet<MstbLatePaymentDetail>(0);
    private Set<AmtbAcctLatePymt> amtbAcctLatePymts = new HashSet<AmtbAcctLatePymt>(0);


    // Constructors

    /** default constructor */
    public MstbLatePaymentMaster() {
    }

	/** minimal constructor */
    public MstbLatePaymentMaster(String latePaymentPlanName) {
        this.latePaymentPlanName = latePaymentPlanName;
    }
    
    /** full constructor */
    public MstbLatePaymentMaster(String latePaymentPlanName, Timestamp createdDt, String createdBy, Timestamp updatedDt, String updatedBy, Integer version, Set<AmtbAcctLatePymtReq> amtbAcctLatePymtReqs, Set<AmtbApplication> amtbApplications, Set<MstbLatePaymentDetail> mstbLatePaymentDetails, Set<AmtbAcctLatePymt> amtbAcctLatePymts) {
        this.latePaymentPlanName = latePaymentPlanName;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updatedDt = updatedDt;
        this.updatedBy = updatedBy;
        this.version = version;
        this.amtbAcctLatePymtReqs = amtbAcctLatePymtReqs;
        this.amtbApplications = amtbApplications;
        this.mstbLatePaymentDetails = mstbLatePaymentDetails;
        this.amtbAcctLatePymts = amtbAcctLatePymts;
    }
    

   
    // Property accessors

    public Integer getLatePaymentPlanNo() {
        return this.latePaymentPlanNo;
    }
    
    public void setLatePaymentPlanNo(Integer latePaymentPlanNo) {
        this.latePaymentPlanNo = latePaymentPlanNo;
    }

    public String getLatePaymentPlanName() {
        return this.latePaymentPlanName;
    }
    
    public void setLatePaymentPlanName(String latePaymentPlanName) {
        this.latePaymentPlanName = latePaymentPlanName;
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
    
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<AmtbAcctLatePymtReq> getAmtbAcctLatePymtReqs() {
        return this.amtbAcctLatePymtReqs;
    }
    
    public void setAmtbAcctLatePymtReqs(Set<AmtbAcctLatePymtReq> amtbAcctLatePymtReqs) {
        this.amtbAcctLatePymtReqs = amtbAcctLatePymtReqs;
    }

    public Set<AmtbApplication> getAmtbApplications() {
        return this.amtbApplications;
    }
    
    public void setAmtbApplications(Set<AmtbApplication> amtbApplications) {
        this.amtbApplications = amtbApplications;
    }

    public Set<MstbLatePaymentDetail> getMstbLatePaymentDetails() {
        return this.mstbLatePaymentDetails;
    }
    
    public void setMstbLatePaymentDetails(Set<MstbLatePaymentDetail> mstbLatePaymentDetails) {
        this.mstbLatePaymentDetails = mstbLatePaymentDetails;
    }

    public Set<AmtbAcctLatePymt> getAmtbAcctLatePymts() {
        return this.amtbAcctLatePymts;
    }
    
    public void setAmtbAcctLatePymts(Set<AmtbAcctLatePymt> amtbAcctLatePymts) {
        this.amtbAcctLatePymts = amtbAcctLatePymts;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("latePaymentPlanName").append("='").append(getLatePaymentPlanName()).append("' ");
      buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MstbLatePaymentMaster) ) return false;
		 MstbLatePaymentMaster castOther = ( MstbLatePaymentMaster ) other; 
         
		 return ( (this.getLatePaymentPlanNo()==castOther.getLatePaymentPlanNo()) || ( this.getLatePaymentPlanNo()!=null && castOther.getLatePaymentPlanNo()!=null && this.getLatePaymentPlanNo().equals(castOther.getLatePaymentPlanNo()) ) )
 && ( (this.getLatePaymentPlanName()==castOther.getLatePaymentPlanName()) || ( this.getLatePaymentPlanName()!=null && castOther.getLatePaymentPlanName()!=null && this.getLatePaymentPlanName().equals(castOther.getLatePaymentPlanName()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLatePaymentPlanNo() == null ? 0 : this.getLatePaymentPlanNo().hashCode() );
         result = 37 * result + ( getLatePaymentPlanName() == null ? 0 : this.getLatePaymentPlanName().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         return result;
   }   

}
