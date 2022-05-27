/**
 * This class is Track Customer Card Issuance Model
 * 
 * @author jtaruc
 * @version 04/07/2011
 *
 * Modification History
 *
 * Modified by    Date            Description:
 * -----------    -----------     -----------
 * 
 */

package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class IttbCpRewardReq  implements java.io.Serializable {


    // Fields    

	private Long reqNo;
    private Integer accountNo;
    private BigDecimal pendingPoints;
    private Timestamp reqDt;
    private String status;
    private String remarks;
    private String createdBy;
    private Timestamp createdDt;
    private String updatedBy;
    private Timestamp updatedDt;
    private AmtbAccount amtbAccount;


    // Constructors
    
    /** default constructor */
    public IttbCpRewardReq() {
    }
    
    /** full constructor */
    public IttbCpRewardReq(Long reqNo, Integer accountNo, BigDecimal pendingPoints,  Timestamp reqDt, String status, String remarks, String createdBy, Timestamp createdDt, String updatedBy, Timestamp updatedDt, AmtbAccount amtbAccount) {
        this.reqNo = reqNo;
        this.accountNo = accountNo;
        this.pendingPoints = pendingPoints;
        this.reqDt = reqDt;
        this.status = status;
        this.remarks = remarks;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.updatedBy = updatedBy;
        this.updatedDt = updatedDt;
        this.amtbAccount = amtbAccount;
    }
    
    // Property accessors

    

	/**
	 * @return the reqDt
	 */
	public Timestamp getReqDt() {
		return reqDt;
	}

	/**
	 * @param reqDt the reqDt to set
	 */
	public void setReqDt(Timestamp reqDt) {
		this.reqDt = reqDt;
	}

	/**
	 * @return the reqNo
	 */
	public Long getReqNo() {
		return reqNo;
	}

	/**
	 * @param reqNo the reqNo to set
	 */
	public void setReqNo(Long reqNo) {
		this.reqNo = reqNo;
	}

	/**
	 * @return the accountNo
	 */
	public Integer getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public Timestamp getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return the pendingPoints
	 */
	public BigDecimal getPendingPoints() {
		return pendingPoints;
	}

	/**
	 * @param pendingPoints the pendingPoints to set
	 */
	public void setPendingPoints(BigDecimal pendingPoints) {
		this.pendingPoints = pendingPoints;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDt
	 */
	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt the updatedDt to set
	 */
	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

    public AmtbAccount getAmtbAccount() {
        return this.amtbAccount;
    }
    
    public void setAmtbAccount(AmtbAccount amtbAccount) {
        this.amtbAccount = amtbAccount;
    }

	/**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("reqNo").append("='").append(getReqNo()).append("' ");		
      buffer.append("accountNo").append("='").append(getAccountNo()).append("' ");
      buffer.append("pendingPoints").append("='").append(getPendingPoints()).append("' ");
      buffer.append("reqDt").append("='").append(getReqDt()).append("' ");     
      buffer.append("status").append("='").append(getStatus()).append("' ");
      buffer.append("remarks").append("='").append(getRemarks()).append("' ");
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
		 if ( !(other instanceof IttbCpRewardReq) ) return false;
		 IttbCpRewardReq castOther = (IttbCpRewardReq) other; 
         
		 return ( (this.getReqNo()==castOther.getReqNo()) || ( this.getReqNo()!=null && castOther.getReqNo()!=null && this.getReqNo().equals(castOther.getReqNo()) ) )
 && ( (this.getAccountNo()==castOther.getAccountNo()) || ( this.getAccountNo()!=null && castOther.getAccountNo()!=null && this.getAccountNo().equals(castOther.getAccountNo()) ) )
 && ( (this.getPendingPoints()==castOther.getPendingPoints()) || ( this.getPendingPoints()!=null && castOther.getPendingPoints()!=null && this.getPendingPoints().equals(castOther.getPendingPoints()) ) )
 && ( (this.getReqDt()==castOther.getReqDt()) || ( this.getReqDt()!=null && castOther.getReqDt()!=null && this.getReqDt().equals(castOther.getReqDt()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getRemarks()==castOther.getRemarks()) || ( this.getRemarks()!=null && castOther.getRemarks()!=null && this.getRemarks().equals(castOther.getRemarks()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getAmtbAccount()==castOther.getAmtbAccount()) || ( this.getAmtbAccount()!=null && castOther.getAmtbAccount()!=null && this.getAmtbAccount().equals(castOther.getAmtbAccount()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReqNo() == null ? 0 : this.getReqNo().hashCode() );
         result = 37 * result + ( getAccountNo() == null ? 0 : this.getAccountNo().hashCode() );
         result = 37 * result + ( getPendingPoints() == null ? 0 : this.getPendingPoints().hashCode() );
         result = 37 * result + ( getReqDt() == null ? 0 : this.getReqDt().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getRemarks() == null ? 0 : this.getRemarks().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         return result;
   }



}
