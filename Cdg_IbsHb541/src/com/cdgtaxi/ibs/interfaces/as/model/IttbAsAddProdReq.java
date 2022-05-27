package com.cdgtaxi.ibs.interfaces.as.model;
// Generated Jul 23, 2009 3:32:47 PM by Hibernate Tools 3.1.0.beta4



/**
 * IttbAsAddProdReq generated by hbm2java
 */

public class IttbAsAddProdReq  implements java.io.Serializable {


    // Fields    

    private String reqId;
    private String cardNo;
    private String acctId;
    private String prodTypeId;
    private String fixedValue;
    private String creditLimit;
    private String expiryDate;
    private String status;
    private String reasonCode;
    private String createBy;
    private String offlineCount;
    private String offlineAmount;
    private String offlineTxnAmount;
    private String forceOnline;
    private IttbAsMasterReq ittbAsMasterReq;


    // Constructors

    /** default constructor */
    public IttbAsAddProdReq() {
    }

	/** minimal constructor */
    public IttbAsAddProdReq(IttbAsMasterReq ittbAsMasterReq) {
        this.ittbAsMasterReq = ittbAsMasterReq;
    }
    
    /** full constructor */
    public IttbAsAddProdReq(String cardNo, String acctId, String prodTypeId, String fixedValue, String creditLimit, String expiryDate, String status, String reasonCode, String createBy, String offlineCount, String offlineAmount, String offlineTxnAmount, String forceOnline, IttbAsMasterReq ittbAsMasterReq) {
        this.cardNo = cardNo;
        this.acctId = acctId;
        this.prodTypeId = prodTypeId;
        this.fixedValue = fixedValue;
        this.creditLimit = creditLimit;
        this.expiryDate = expiryDate;
        this.status = status;
        this.reasonCode = reasonCode;
        this.createBy = createBy;
        this.ittbAsMasterReq = ittbAsMasterReq;
        this.offlineCount = offlineCount;
        this.offlineAmount = offlineAmount;
        this.offlineTxnAmount = offlineTxnAmount;
        this.forceOnline = forceOnline;
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

    public String getAcctId() {
        return this.acctId;
    }
    
    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getProdTypeId() {
        return this.prodTypeId;
    }
    
    public void setProdTypeId(String prodTypeId) {
        this.prodTypeId = prodTypeId;
    }

    public String getFixedValue() {
        return this.fixedValue;
    }
    
    public void setFixedValue(String fixedValue) {
        this.fixedValue = fixedValue;
    }

    public String getCreditLimit() {
        return this.creditLimit;
    }
    
    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getReasonCode() {
        return this.reasonCode;
    }
    
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getCreateBy() {
        return this.createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    public String getOfflineCount() {
		return offlineCount;
	}

	public void setOfflineCount(String offlineCount) {
		this.offlineCount = offlineCount;
	}

	public String getOfflineAmount() {
		return offlineAmount;
	}

	public void setOfflineAmount(String offlineAmount) {
		this.offlineAmount = offlineAmount;
	}
	
	public String getOfflineTxnAmount() {
			return offlineTxnAmount;
		}

	public void setOfflineTxnAmount(String offlineTxnAmount) {
		this.offlineTxnAmount = offlineTxnAmount;
	}

	public String getForceOnline() {
		return forceOnline;
	}

	public void setForceOnline(String forceOnline) {
		this.forceOnline = forceOnline;
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
      buffer.append("acctId").append("='").append(getAcctId()).append("' ");			
      buffer.append("prodTypeId").append("='").append(getProdTypeId()).append("' ");			
      buffer.append("fixedValue").append("='").append(getFixedValue()).append("' ");			
      buffer.append("creditLimit").append("='").append(getCreditLimit()).append("' ");			
      buffer.append("expiryDate").append("='").append(getExpiryDate()).append("' ");			
      buffer.append("status").append("='").append(getStatus()).append("' ");			
      buffer.append("reasonCode").append("='").append(getReasonCode()).append("' ");			
      buffer.append("createBy").append("='").append(getCreateBy()).append("' ");	
      buffer.append("offlineCount").append("='").append(getOfflineCount()).append("' ");	
      buffer.append("offlineAmount").append("='").append(getOfflineAmount()).append("' ");
      buffer.append("offlineTxnAmount").append("='").append(getOfflineTxnAmount()).append("' ");
      buffer.append("forceOnline").append("='").append(getForceOnline()).append("' ");
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbAsAddProdReq) ) return false;
		 IttbAsAddProdReq castOther = ( IttbAsAddProdReq ) other; 
         
		 return ( (this.getReqId()==castOther.getReqId()) || ( this.getReqId()!=null && castOther.getReqId()!=null && this.getReqId().equals(castOther.getReqId()) ) )
 && ( (this.getCardNo()==castOther.getCardNo()) || ( this.getCardNo()!=null && castOther.getCardNo()!=null && this.getCardNo().equals(castOther.getCardNo()) ) )
 && ( (this.getAcctId()==castOther.getAcctId()) || ( this.getAcctId()!=null && castOther.getAcctId()!=null && this.getAcctId().equals(castOther.getAcctId()) ) )
 && ( (this.getProdTypeId()==castOther.getProdTypeId()) || ( this.getProdTypeId()!=null && castOther.getProdTypeId()!=null && this.getProdTypeId().equals(castOther.getProdTypeId()) ) )
 && ( (this.getFixedValue()==castOther.getFixedValue()) || ( this.getFixedValue()!=null && castOther.getFixedValue()!=null && this.getFixedValue().equals(castOther.getFixedValue()) ) )
 && ( (this.getCreditLimit()==castOther.getCreditLimit()) || ( this.getCreditLimit()!=null && castOther.getCreditLimit()!=null && this.getCreditLimit().equals(castOther.getCreditLimit()) ) )
 && ( (this.getExpiryDate()==castOther.getExpiryDate()) || ( this.getExpiryDate()!=null && castOther.getExpiryDate()!=null && this.getExpiryDate().equals(castOther.getExpiryDate()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getReasonCode()==castOther.getReasonCode()) || ( this.getReasonCode()!=null && castOther.getReasonCode()!=null && this.getReasonCode().equals(castOther.getReasonCode()) ) )
 && ( (this.getCreateBy()==castOther.getCreateBy()) || ( this.getCreateBy()!=null && castOther.getCreateBy()!=null && this.getCreateBy().equals(castOther.getCreateBy()) ) )
 && ( (this.getOfflineCount()==castOther.getOfflineCount()) || ( this.getOfflineCount()!=null && castOther.getOfflineCount()!=null && this.getOfflineCount().equals(castOther.getOfflineCount()) ) )
 && ( (this.getOfflineAmount()==castOther.getOfflineAmount()) || ( this.getOfflineAmount()!=null && castOther.getOfflineAmount()!=null && this.getOfflineAmount().equals(castOther.getOfflineAmount()) ) )
 && ( (this.getOfflineTxnAmount()==castOther.getOfflineTxnAmount()) || ( this.getOfflineTxnAmount()!=null && castOther.getOfflineTxnAmount()!=null && this.getOfflineTxnAmount().equals(castOther.getOfflineTxnAmount()) ) )
 && ( (this.getForceOnline()==castOther.getForceOnline()) || ( this.getForceOnline()!=null && castOther.getForceOnline()!=null && this.getForceOnline().equals(castOther.getForceOnline()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReqId() == null ? 0 : this.getReqId().hashCode() );
         result = 37 * result + ( getCardNo() == null ? 0 : this.getCardNo().hashCode() );
         result = 37 * result + ( getAcctId() == null ? 0 : this.getAcctId().hashCode() );
         result = 37 * result + ( getProdTypeId() == null ? 0 : this.getProdTypeId().hashCode() );
         result = 37 * result + ( getFixedValue() == null ? 0 : this.getFixedValue().hashCode() );
         result = 37 * result + ( getCreditLimit() == null ? 0 : this.getCreditLimit().hashCode() );
         result = 37 * result + ( getExpiryDate() == null ? 0 : this.getExpiryDate().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getReasonCode() == null ? 0 : this.getReasonCode().hashCode() );
         result = 37 * result + ( getCreateBy() == null ? 0 : this.getCreateBy().hashCode() );
         result = 37 * result + ( getOfflineCount() == null ? 0 : this.getOfflineCount().hashCode() );
         result = 37 * result + ( getOfflineAmount() == null ? 0 : this.getOfflineAmount().hashCode() );
         result = 37 * result + ( getOfflineTxnAmount() == null ? 0 : this.getOfflineTxnAmount().hashCode() );
         result = 37 * result + ( getForceOnline() == null ? 0 : this.getForceOnline().hashCode() );
         
         return result;
   }   





}
