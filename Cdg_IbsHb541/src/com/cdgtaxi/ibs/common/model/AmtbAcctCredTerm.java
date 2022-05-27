package com.cdgtaxi.ibs.common.model;
// Generated Jul 21, 2009 1:20:17 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Date;

import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;


/**
 * AmtbAcctCredTerm generated by hbm2java
 */

public class AmtbAcctCredTerm  implements java.io.Serializable {


    // Fields    

    private Integer acctCredTermNo;
    private Date effectiveDate;
    private Integer version;
    private AmtbAccount amtbAccount;
    private MstbCreditTermMaster mstbCreditTermMaster;


    // Constructors

    /** default constructor */
    public AmtbAcctCredTerm() {
    }

    
    /** full constructor */
    public AmtbAcctCredTerm(Date effectiveDate, Integer version, AmtbAccount amtbAccount, MstbCreditTermMaster mstbCreditTermMaster) {
        this.effectiveDate = effectiveDate;
        this.version = version;
        this.amtbAccount = amtbAccount;
        this.mstbCreditTermMaster = mstbCreditTermMaster;
    }
    

   
    // Property accessors

    public Integer getAcctCredTermNo() {
        return this.acctCredTermNo;
    }
    
    public void setAcctCredTermNo(Integer acctCredTermNo) {
        this.acctCredTermNo = acctCredTermNo;
    }

    public Date getEffectiveDate() {
        return this.effectiveDate;
    }
    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    public AmtbAccount getAmtbAccount() {
        return this.amtbAccount;
    }
    
    public void setAmtbAccount(AmtbAccount amtbAccount) {
        this.amtbAccount = amtbAccount;
    }

    public MstbCreditTermMaster getMstbCreditTermMaster() {
        return this.mstbCreditTermMaster;
    }
    
    public void setMstbCreditTermMaster(MstbCreditTermMaster mstbCreditTermMaster) {
        this.mstbCreditTermMaster = mstbCreditTermMaster;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("effectiveDate").append("='").append(getEffectiveDate()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AmtbAcctCredTerm) ) return false;
		 AmtbAcctCredTerm castOther = ( AmtbAcctCredTerm ) other; 
         
		 return ( (this.getAcctCredTermNo()==castOther.getAcctCredTermNo()) || ( this.getAcctCredTermNo()!=null && castOther.getAcctCredTermNo()!=null && this.getAcctCredTermNo().equals(castOther.getAcctCredTermNo()) ) )
 && ( (this.getEffectiveDate()==castOther.getEffectiveDate()) || ( this.getEffectiveDate()!=null && castOther.getEffectiveDate()!=null && this.getEffectiveDate().equals(castOther.getEffectiveDate()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getAmtbAccount()==castOther.getAmtbAccount()) || ( this.getAmtbAccount()!=null && castOther.getAmtbAccount()!=null && this.getAmtbAccount().equals(castOther.getAmtbAccount()) ) )
 && ( (this.getMstbCreditTermMaster()==castOther.getMstbCreditTermMaster()) || ( this.getMstbCreditTermMaster()!=null && castOther.getMstbCreditTermMaster()!=null && this.getMstbCreditTermMaster().equals(castOther.getMstbCreditTermMaster()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcctCredTermNo() == null ? 0 : this.getAcctCredTermNo().hashCode() );
         result = 37 * result + ( getEffectiveDate() == null ? 0 : this.getEffectiveDate().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getAmtbAccount() == null ? 0 : this.getAmtbAccount().hashCode() );
         result = 37 * result + ( getMstbCreditTermMaster() == null ? 0 : this.getMstbCreditTermMaster().hashCode() );
         return result;
   }   





}
