package com.cdgtaxi.ibs.common.model;
// Generated Jul 20, 2009 4:09:24 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Timestamp;


/**
 * BmtbBillGenError generated by hbm2java
 */

public class BmtbBillGenError  implements java.io.Serializable {


    // Fields    

    private Long errorNo;
    private String errorMsg;
    private Timestamp errorDt;
    private String vdFlag;
    private String rwdFlag;
    private AmtbAccount amtbAccountByTopLevelAccountNo;
    private AmtbAccount amtbAccountByBillableAccountNo;
    private BmtbBillGenReq bmtbBillGenReq;


    // Constructors

    /** default constructor */
    public BmtbBillGenError() {
    }

	/** minimal constructor */
    public BmtbBillGenError(Timestamp errorDt) {
        this.errorDt = errorDt;
    }
    
    /** full constructor */
    public BmtbBillGenError(String errorMsg, Timestamp errorDt,
    		String vdFlag, String rwdFlag,
    		AmtbAccount amtbAccountByTopLevelAccountNo, AmtbAccount amtbAccountByBillableAccountNo, 
    		BmtbBillGenReq bmtbBillGenReq) {
        this.errorMsg = errorMsg;
        this.errorDt = errorDt;
        this.vdFlag = vdFlag;
        this.rwdFlag = rwdFlag;
        this.amtbAccountByTopLevelAccountNo = amtbAccountByTopLevelAccountNo;
        this.amtbAccountByBillableAccountNo = amtbAccountByBillableAccountNo;
        this.bmtbBillGenReq = bmtbBillGenReq;
    }
    

   
    // Property accessors

    public String getVdFlag() {
        return this.vdFlag;
    }
    
    public void setVdFlag(String vdFlag) {
        this.vdFlag = vdFlag;
    }

    public String getRwdFlag() {
        return this.rwdFlag;
    }
    
    public void setRwdFlag(String rwdFlag) {
        this.rwdFlag = rwdFlag;
    }
    
    public Long getErrorNo() {
        return this.errorNo;
    }
    
    public void setErrorNo(Long errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
    
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Timestamp getErrorDt() {
        return this.errorDt;
    }
    
    public void setErrorDt(Timestamp errorDt) {
        this.errorDt = errorDt;
    }

    public AmtbAccount getAmtbAccountByTopLevelAccountNo() {
        return this.amtbAccountByTopLevelAccountNo;
    }
    
    public void setAmtbAccountByTopLevelAccountNo(AmtbAccount amtbAccountByTopLevelAccountNo) {
        this.amtbAccountByTopLevelAccountNo = amtbAccountByTopLevelAccountNo;
    }

    public AmtbAccount getAmtbAccountByBillableAccountNo() {
        return this.amtbAccountByBillableAccountNo;
    }
    
    public void setAmtbAccountByBillableAccountNo(AmtbAccount amtbAccountByBillableAccountNo) {
        this.amtbAccountByBillableAccountNo = amtbAccountByBillableAccountNo;
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
      buffer.append("errorMsg").append("='").append(getErrorMsg()).append("' ");			
      buffer.append("errorDt").append("='").append(getErrorDt()).append("' ");	
      buffer.append("vdFlag").append("='").append(getVdFlag()).append("' ");			
      buffer.append("rwdFlag").append("='").append(getRwdFlag()).append("' ");		
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BmtbBillGenError) ) return false;
		 BmtbBillGenError castOther = ( BmtbBillGenError ) other; 
         
		 return ( (this.getErrorNo()==castOther.getErrorNo()) || ( this.getErrorNo()!=null && castOther.getErrorNo()!=null && this.getErrorNo().equals(castOther.getErrorNo()) ) )
 && ( (this.getErrorMsg()==castOther.getErrorMsg()) || ( this.getErrorMsg()!=null && castOther.getErrorMsg()!=null && this.getErrorMsg().equals(castOther.getErrorMsg()) ) )
 && ( (this.getErrorDt()==castOther.getErrorDt()) || ( this.getErrorDt()!=null && castOther.getErrorDt()!=null && this.getErrorDt().equals(castOther.getErrorDt()) ) )
  && ( (this.getVdFlag()==castOther.getVdFlag()) || ( this.getVdFlag()!=null && castOther.getVdFlag()!=null && this.getVdFlag().equals(castOther.getVdFlag()) ) )
 && ( (this.getRwdFlag()==castOther.getRwdFlag()) || ( this.getRwdFlag()!=null && castOther.getRwdFlag()!=null && this.getRwdFlag().equals(castOther.getRwdFlag()) ) )
 && ( (this.getAmtbAccountByTopLevelAccountNo()==castOther.getAmtbAccountByTopLevelAccountNo()) || ( this.getAmtbAccountByTopLevelAccountNo()!=null && castOther.getAmtbAccountByTopLevelAccountNo()!=null && this.getAmtbAccountByTopLevelAccountNo().equals(castOther.getAmtbAccountByTopLevelAccountNo()) ) )
 && ( (this.getAmtbAccountByBillableAccountNo()==castOther.getAmtbAccountByBillableAccountNo()) || ( this.getAmtbAccountByBillableAccountNo()!=null && castOther.getAmtbAccountByBillableAccountNo()!=null && this.getAmtbAccountByBillableAccountNo().equals(castOther.getAmtbAccountByBillableAccountNo()) ) )
 && ( (this.getBmtbBillGenReq()==castOther.getBmtbBillGenReq()) || ( this.getBmtbBillGenReq()!=null && castOther.getBmtbBillGenReq()!=null && this.getBmtbBillGenReq().equals(castOther.getBmtbBillGenReq()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getErrorNo() == null ? 0 : this.getErrorNo().hashCode() );
         result = 37 * result + ( getErrorMsg() == null ? 0 : this.getErrorMsg().hashCode() );
         result = 37 * result + ( getErrorDt() == null ? 0 : this.getErrorDt().hashCode() );
         result = 37 * result + ( getVdFlag() == null ? 0 : this.getVdFlag().hashCode() );
         result = 37 * result + ( getRwdFlag() == null ? 0 : this.getRwdFlag().hashCode() );
         result = 37 * result + ( getAmtbAccountByTopLevelAccountNo() == null ? 0 : this.getAmtbAccountByTopLevelAccountNo().hashCode() );
         result = 37 * result + ( getAmtbAccountByBillableAccountNo() == null ? 0 : this.getAmtbAccountByBillableAccountNo().hashCode() );
         result = 37 * result + ( getBmtbBillGenReq() == null ? 0 : this.getBmtbBillGenReq().hashCode() );
         return result;
   }   





}
