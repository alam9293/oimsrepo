package com.cdgtaxi.ibs.interfaces.as.model;
// Generated Jul 23, 2009 3:32:47 PM by Hibernate Tools 3.1.0.beta4



/**
 * IttbAsUpdPayProdReq generated by hbm2java
 */

public class IttbAsUpdPayProdReq  implements java.io.Serializable {


    // Fields    

    private String reqId;
    private String cardNo;
    private String newCreditLimit;
    private String acctId;
    private String updateBy;
    private IttbAsMasterReq ittbAsMasterReq;


    // Constructors

    /** default constructor */
    public IttbAsUpdPayProdReq() {
    }

	/** minimal constructor */
    public IttbAsUpdPayProdReq(IttbAsMasterReq ittbAsMasterReq) {
        this.ittbAsMasterReq = ittbAsMasterReq;
    }
    
    /** full constructor */
    public IttbAsUpdPayProdReq(String cardNo, String newCreditLimit, String acctId, String updateBy, IttbAsMasterReq ittbAsMasterReq) {
        this.cardNo = cardNo;
        this.newCreditLimit = newCreditLimit;
        this.acctId = acctId;
        this.updateBy = updateBy;
        this.ittbAsMasterReq = ittbAsMasterReq;
    }
    

   
    // Property accessors

    public String getReqId() {
        return this.reqId;
    }
    
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getCardNo() {
        return this.cardNo;
    }
    
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getNewCreditLimit() {
        return this.newCreditLimit;
    }
    
    public void setNewCreditLimit(String newCreditLimit) {
        this.newCreditLimit = newCreditLimit;
    }

    public String getAcctId() {
        return this.acctId;
    }
    
    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }
    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public IttbAsMasterReq getIttbAsMasterReq() {
        return this.ittbAsMasterReq;
    }
    
    public void setIttbAsMasterReq(IttbAsMasterReq ittbAsMasterReq) {
        this.ittbAsMasterReq = ittbAsMasterReq;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("cardNo").append("='").append(getCardNo()).append("' ");			
      buffer.append("newCreditLimit").append("='").append(getNewCreditLimit()).append("' ");			
      buffer.append("acctId").append("='").append(getAcctId()).append("' ");			
      buffer.append("updateBy").append("='").append(getUpdateBy()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbAsUpdPayProdReq) ) return false;
		 IttbAsUpdPayProdReq castOther = ( IttbAsUpdPayProdReq ) other; 
         
		 return ( (this.getReqId()==castOther.getReqId()) || ( this.getReqId()!=null && castOther.getReqId()!=null && this.getReqId().equals(castOther.getReqId()) ) )
 && ( (this.getCardNo()==castOther.getCardNo()) || ( this.getCardNo()!=null && castOther.getCardNo()!=null && this.getCardNo().equals(castOther.getCardNo()) ) )
 && ( (this.getNewCreditLimit()==castOther.getNewCreditLimit()) || ( this.getNewCreditLimit()!=null && castOther.getNewCreditLimit()!=null && this.getNewCreditLimit().equals(castOther.getNewCreditLimit()) ) )
 && ( (this.getAcctId()==castOther.getAcctId()) || ( this.getAcctId()!=null && castOther.getAcctId()!=null && this.getAcctId().equals(castOther.getAcctId()) ) )
 && ( (this.getUpdateBy()==castOther.getUpdateBy()) || ( this.getUpdateBy()!=null && castOther.getUpdateBy()!=null && this.getUpdateBy().equals(castOther.getUpdateBy()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReqId() == null ? 0 : this.getReqId().hashCode() );
         result = 37 * result + ( getCardNo() == null ? 0 : this.getCardNo().hashCode() );
         result = 37 * result + ( getNewCreditLimit() == null ? 0 : this.getNewCreditLimit().hashCode() );
         result = 37 * result + ( getAcctId() == null ? 0 : this.getAcctId().hashCode() );
         result = 37 * result + ( getUpdateBy() == null ? 0 : this.getUpdateBy().hashCode() );
         
         return result;
   }   





}
