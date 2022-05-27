package com.cdgtaxi.ibs.interfaces.as.model;
// Generated Jul 23, 2009 3:32:46 PM by Hibernate Tools 3.1.0.beta4



/**
 * IttbAsAddNegProdReq generated by hbm2java
 */

public class IttbAsAddNegProdReq  implements java.io.Serializable {


    // Fields    

    private String reqId;
    private String cardNo;
    private String status;
    private String createBy;
    private IttbAsMasterReq ittbAsMasterReq;


    // Constructors

    /** default constructor */
    public IttbAsAddNegProdReq() {
    }

	/** minimal constructor */
    public IttbAsAddNegProdReq(IttbAsMasterReq ittbAsMasterReq) {
        this.ittbAsMasterReq = ittbAsMasterReq;
    }
    
    /** full constructor */
    public IttbAsAddNegProdReq(String cardNo, String status, String createBy, IttbAsMasterReq ittbAsMasterReq) {
        this.cardNo = cardNo;
        this.status = status;
        this.createBy = createBy;
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

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return this.createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
      buffer.append("status").append("='").append(getStatus()).append("' ");			
      buffer.append("createBy").append("='").append(getCreateBy()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbAsAddNegProdReq) ) return false;
		 IttbAsAddNegProdReq castOther = ( IttbAsAddNegProdReq ) other; 
         
		 return ( (this.getReqId()==castOther.getReqId()) || ( this.getReqId()!=null && castOther.getReqId()!=null && this.getReqId().equals(castOther.getReqId()) ) )
 && ( (this.getCardNo()==castOther.getCardNo()) || ( this.getCardNo()!=null && castOther.getCardNo()!=null && this.getCardNo().equals(castOther.getCardNo()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getCreateBy()==castOther.getCreateBy()) || ( this.getCreateBy()!=null && castOther.getCreateBy()!=null && this.getCreateBy().equals(castOther.getCreateBy()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReqId() == null ? 0 : this.getReqId().hashCode() );
         result = 37 * result + ( getCardNo() == null ? 0 : this.getCardNo().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getCreateBy() == null ? 0 : this.getCreateBy().hashCode() );
         
         return result;
   }   





}
