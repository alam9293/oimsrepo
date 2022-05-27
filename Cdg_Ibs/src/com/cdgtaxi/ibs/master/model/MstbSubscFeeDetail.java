package com.cdgtaxi.ibs.master.model;
// Generated Apr 13, 2009 6:45:39 PM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.cdgtaxi.ibs.acl.security.Auditable;
import com.cdgtaxi.ibs.common.model.Creatable;
import com.cdgtaxi.ibs.common.model.Updatable;


/**
 * MstbSubscFeeDetail generated by hbm2java
 */

public class MstbSubscFeeDetail  implements java.io.Serializable, Creatable, Updatable, Auditable {


	// Fields    

    private Integer subscriptionFeeDetailNo;
    private Timestamp effectiveDt;
    private BigDecimal subscriptionFee;
    private int recurringPeriod;
    private Timestamp createdDt;
    private String createdBy;
    private Timestamp updatedDt;
    private String updatedBy;
    private Integer version;
    private MstbSubscFeeMaster mstbSubscFeeMaster;


    // Constructors

    /** default constructor */
    public MstbSubscFeeDetail() {
    }

	/** minimal constructor */
    public MstbSubscFeeDetail(Timestamp effectiveDt, BigDecimal subscriptionFee, int recurringPeriod) {
        this.effectiveDt = effectiveDt;
        this.subscriptionFee = subscriptionFee;
        this.recurringPeriod = recurringPeriod;
    }
    
    /** full constructor */
    public MstbSubscFeeDetail(Timestamp effectiveDt, BigDecimal subscriptionFee, int recurringPeriod, Timestamp createdDt, String createdBy, Timestamp updatedDt, String updatedBy, Integer version, MstbSubscFeeMaster mstbSubscFeeMaster) {
        this.effectiveDt = effectiveDt;
        this.subscriptionFee = subscriptionFee;
        this.recurringPeriod = recurringPeriod;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updatedDt = updatedDt;
        this.updatedBy = updatedBy;
        this.version = version;
        this.mstbSubscFeeMaster = mstbSubscFeeMaster;
    }
    

   
    // Property accessors

    public Integer getSubscriptionFeeDetailNo() {
        return this.subscriptionFeeDetailNo;
    }
    
    public void setSubscriptionFeeDetailNo(Integer subscriptionFeeDetailNo) {
        this.subscriptionFeeDetailNo = subscriptionFeeDetailNo;
    }

    public Timestamp getEffectiveDt() {
        return this.effectiveDt;
    }
    
    public void setEffectiveDt(Timestamp effectiveDt) {
        this.effectiveDt = effectiveDt;
    }

    public BigDecimal getSubscriptionFee() {
        return this.subscriptionFee;
    }
    
    public void setSubscriptionFee(BigDecimal subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }

    public int getRecurringPeriod() {
        return this.recurringPeriod;
    }
    
    public void setRecurringPeriod(int recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
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

    public MstbSubscFeeMaster getMstbSubscFeeMaster() {
        return this.mstbSubscFeeMaster;
    }
    
    public void setMstbSubscFeeMaster(MstbSubscFeeMaster mstbSubscFeeMaster) {
        this.mstbSubscFeeMaster = mstbSubscFeeMaster;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("effectiveDt").append("='").append(getEffectiveDt()).append("' ");			
      buffer.append("subscriptionFee").append("='").append(getSubscriptionFee()).append("' ");			
      buffer.append("recurringPeriod").append("='").append(getRecurringPeriod()).append("' ");		
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
		 if ( !(other instanceof MstbSubscFeeDetail) ) return false;
		 MstbSubscFeeDetail castOther = ( MstbSubscFeeDetail ) other; 
         
		 return ( (this.getSubscriptionFeeDetailNo()==castOther.getSubscriptionFeeDetailNo()) || ( this.getSubscriptionFeeDetailNo()!=null && castOther.getSubscriptionFeeDetailNo()!=null && this.getSubscriptionFeeDetailNo().equals(castOther.getSubscriptionFeeDetailNo()) ) )
 && ( (this.getEffectiveDt()==castOther.getEffectiveDt()) || ( this.getEffectiveDt()!=null && castOther.getEffectiveDt()!=null && this.getEffectiveDt().equals(castOther.getEffectiveDt()) ) )
 && ( (this.getSubscriptionFee()==castOther.getSubscriptionFee()) || ( this.getSubscriptionFee()!=null && castOther.getSubscriptionFee()!=null && this.getSubscriptionFee().equals(castOther.getSubscriptionFee()) ) )
 && (this.getRecurringPeriod()==castOther.getRecurringPeriod())
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getMstbSubscFeeMaster()==castOther.getMstbSubscFeeMaster()) || ( this.getMstbSubscFeeMaster()!=null && castOther.getMstbSubscFeeMaster()!=null && this.getMstbSubscFeeMaster().equals(castOther.getMstbSubscFeeMaster()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSubscriptionFeeDetailNo() == null ? 0 : this.getSubscriptionFeeDetailNo().hashCode() );
         result = 37 * result + ( getEffectiveDt() == null ? 0 : this.getEffectiveDt().hashCode() );
         result = 37 * result + ( getSubscriptionFee() == null ? 0 : this.getSubscriptionFee().hashCode() );
         result = 37 * result + this.getRecurringPeriod();
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getMstbSubscFeeMaster() == null ? 0 : this.getMstbSubscFeeMaster().hashCode() );
         return result;
   }   

}
