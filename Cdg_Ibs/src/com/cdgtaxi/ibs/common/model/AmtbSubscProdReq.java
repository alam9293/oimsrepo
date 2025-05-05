package com.cdgtaxi.ibs.common.model;

import java.sql.Timestamp;

import com.cdgtaxi.ibs.acl.security.Auditable;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;


/**
* AmtbSubscProdReq
*/

public class AmtbSubscProdReq  implements java.io.Serializable, Auditable {

    // Fields   

    private Integer subProdReqNo;
    private PmtbProductType pmtbProductType;
    private AmtbAccount amtbAccount;
    
    private MstbProdDiscMaster mstbProdDiscMaster;
    private LrtbRewardMaster lrtbRewardMaster;
    private MstbSubscFeeMaster mstbSubscFeeMaster;
    private MstbIssuanceFeeMaster mstbIssuanceFeeMaster;

    private Timestamp effectiveDt;
    
    private Long reqBy;
    private Timestamp reqDt;
    private String subscAction;
    private String appStatus;
    private String remarks;
    private Long approveBy;
    private Timestamp approveDt;

    // Constructors
    /** default constructor */
    public AmtbSubscProdReq() {
    }

     /** minimal constructor */ // TODO: Product Subscription
    public AmtbSubscProdReq(AmtbAccount amtbAccount, PmtbProductType pmtbProductType, Timestamp effectiveDt, Long reqBy, Timestamp reqDt, String subscAction, String appStatus) {
      this.amtbAccount = amtbAccount;
      this.pmtbProductType = pmtbProductType;
      this.effectiveDt = effectiveDt;
      this.reqBy = reqBy;
      this.reqDt = reqDt;
      this.subscAction = subscAction;
      this.appStatus = appStatus;
    }
   
    /** full constructor */
    public AmtbSubscProdReq(AmtbAccount amtbAccount, PmtbProductType pmtbProductType, Timestamp effectiveDt, Long reqBy, Timestamp reqDt, String subscAction, String appStatus, MstbProdDiscMaster mstbProdDiscMaster, LrtbRewardMaster lrtbRewardMaster, MstbSubscFeeMaster mstbSubscFeeMaster, MstbIssuanceFeeMaster mstbIssuanceFeeMaster, String remarks, Long approveBy, Timestamp approveDt) {
    	 this.amtbAccount = amtbAccount;
         this.pmtbProductType = pmtbProductType;
         this.effectiveDt = effectiveDt;
         this.reqBy = reqBy;
         this.reqDt = reqDt;
         this.subscAction = subscAction;
         this.appStatus = appStatus;
         
         this.mstbProdDiscMaster = mstbProdDiscMaster;
         this.lrtbRewardMaster = lrtbRewardMaster;
         this.mstbSubscFeeMaster = mstbSubscFeeMaster;
         this.mstbIssuanceFeeMaster = mstbIssuanceFeeMaster;
         
         this.remarks = remarks;
         this.approveBy = approveBy;
         this.approveDt = approveDt;
    }
    
	public Integer getSubProdReqNo() {
		return subProdReqNo;
	}

	public void setSubProdReqNo(Integer subProdReqNo) {
		this.subProdReqNo = subProdReqNo;
	}

	public PmtbProductType getPmtbProductType() {
		return pmtbProductType;
	}

	public void setPmtbProductType(PmtbProductType pmtbProductType) {
		this.pmtbProductType = pmtbProductType;
	}

	public AmtbAccount getAmtbAccount() {
		return amtbAccount;
	}

	public void setAmtbAccount(AmtbAccount amtbAccount) {
		this.amtbAccount = amtbAccount;
	}

	public MstbProdDiscMaster getMstbProdDiscMaster() {
		return mstbProdDiscMaster;
	}

	public void setMstbProdDiscMaster(MstbProdDiscMaster mstbProdDiscMaster) {
		this.mstbProdDiscMaster = mstbProdDiscMaster;
	}

	public LrtbRewardMaster getLrtbRewardMaster() {
		return lrtbRewardMaster;
	}

	public void setLrtbRewardMaster(LrtbRewardMaster lrtbRewardMaster) {
		this.lrtbRewardMaster = lrtbRewardMaster;
	}

	public MstbSubscFeeMaster getMstbSubscFeeMaster() {
		return mstbSubscFeeMaster;
	}

	public void setMstbSubscFeeMaster(MstbSubscFeeMaster mstbSubscFeeMaster) {
		this.mstbSubscFeeMaster = mstbSubscFeeMaster;
	}

	public MstbIssuanceFeeMaster getMstbIssuanceFeeMaster() {
		return mstbIssuanceFeeMaster;
	}

	public void setMstbIssuanceFeeMaster(MstbIssuanceFeeMaster mstbIssuanceFeeMaster) {
		this.mstbIssuanceFeeMaster = mstbIssuanceFeeMaster;
	}

	public Timestamp getEffectiveDt() {
		return effectiveDt;
	}

	public void setEffectiveDt(Timestamp effectiveDt) {
		this.effectiveDt = effectiveDt;
	}

	public Long getReqBy() {
		return reqBy;
	}

	public void setReqBy(Long reqBy) {
		this.reqBy = reqBy;
	}

	public Timestamp getReqDt() {
		return reqDt;
	}

	public void setReqDt(Timestamp reqDt) {
		this.reqDt = reqDt;
	}

	public String getSubscAction() {
		return subscAction;
	}

	public void setSubscAction(String subscAction) {
		this.subscAction = subscAction;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(Long approveBy) {
		this.approveBy = approveBy;
	}

	public Timestamp getApproveDt() {
		return approveDt;
	}

	public void setApproveDt(Timestamp approveDt) {
		this.approveDt = approveDt;
	}
	/**
     * toString
     * @return String
     */
     public String toString() {
       StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("effectiveDt").append("='").append(getEffectiveDt()).append("' ");
      buffer.append("reqBy").append("='").append(getReqBy()).append("' ");              
      buffer.append("reqDt").append("='").append(getReqDt()).append("' ");              
      buffer.append("subscAction").append("='").append(getSubscAction()).append("' ");              
      buffer.append("appStatus").append("='").append(getAppStatus()).append("' ");  
      buffer.append("remarks").append("='").append(getRemarks()).append("' "); 
      buffer.append("approveBy").append("='").append(getApproveBy()).append("' ");              
      buffer.append("approveDt").append("='").append(getApproveDt()).append("' ");            
      buffer.append("]");
     
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
          if ( (other == null ) ) return false;
          if ( !(other instanceof AmtbSubscProdReq) ) return false;
          AmtbSubscProdReq castOther = ( AmtbSubscProdReq ) other;

          // TODO : Add New Fields
          return ( (this.getSubProdReqNo()==castOther.getSubProdReqNo()) || ( this.getSubProdReqNo()!=null && castOther.getSubProdReqNo()!=null && this.getSubProdReqNo().equals(castOther.getSubProdReqNo()) ) )
&& ( (this.getEffectiveDt()==castOther.getEffectiveDt()) || ( this.getEffectiveDt()!=null && castOther.getEffectiveDt()!=null && this.getEffectiveDt().equals(castOther.getEffectiveDt()) ) )
&& ( (this.getReqBy()==castOther.getReqBy()) || ( this.getReqBy()!=null && castOther.getReqBy()!=null && this.getReqBy().equals(castOther.getReqBy()) ) )
&& ( (this.getReqDt()==castOther.getReqDt()) || ( this.getReqDt()!=null && castOther.getReqDt()!=null && this.getReqDt().equals(castOther.getReqDt()) ) )
&& ( (this.getSubscAction()==castOther.getSubscAction()) || ( this.getSubscAction()!=null && castOther.getSubscAction()!=null && this.getSubscAction().equals(castOther.getSubscAction()) ) )
&& ( (this.getAppStatus()==castOther.getAppStatus()) || ( this.getAppStatus()!=null && castOther.getAppStatus()!=null && this.getAppStatus().equals(castOther.getAppStatus()) ) )
&& ( (this.getRemarks()==castOther.getRemarks()) || ( this.getRemarks()!=null && castOther.getRemarks()!=null && this.getRemarks().equals(castOther.getRemarks()) ) )
&& ( (this.getMstbProdDiscMaster()==castOther.getMstbProdDiscMaster()) || ( this.getMstbProdDiscMaster()!=null && castOther.getMstbProdDiscMaster()!=null && this.getMstbProdDiscMaster().equals(castOther.getMstbProdDiscMaster()) ) )
&& ( (this.getLrtbRewardMaster()==castOther.getLrtbRewardMaster()) || ( this.getLrtbRewardMaster()!=null && castOther.getLrtbRewardMaster()!=null && this.getLrtbRewardMaster().equals(castOther.getLrtbRewardMaster()) ) )
&& ( (this.getMstbSubscFeeMaster()==castOther.getMstbSubscFeeMaster()) || ( this.getMstbSubscFeeMaster()!=null && castOther.getMstbSubscFeeMaster()!=null && this.getMstbSubscFeeMaster().equals(castOther.getMstbSubscFeeMaster()) ) )
&& ( (this.getMstbIssuanceFeeMaster()==castOther.getMstbIssuanceFeeMaster()) || ( this.getMstbIssuanceFeeMaster()!=null && castOther.getMstbIssuanceFeeMaster()!=null && this.getMstbIssuanceFeeMaster().equals(castOther.getMstbIssuanceFeeMaster()) ) )
&& ( (this.getAmtbAccount()==castOther.getAmtbAccount()) || ( this.getAmtbAccount()!=null && castOther.getAmtbAccount()!=null && this.getAmtbAccount().equals(castOther.getAmtbAccount()) ) )
&& ( (this.getPmtbProductType()==castOther.getPmtbProductType()) || ( this.getPmtbProductType()!=null && castOther.getPmtbProductType()!=null && this.getPmtbProductType().equals(castOther.getPmtbProductType()) ) )
&& ( (this.getApproveBy()==castOther.getApproveBy()) || ( this.getApproveBy()!=null && castOther.getApproveBy()!=null && this.getApproveBy().equals(castOther.getApproveBy()) ) )
&& ( (this.getApproveDt()==castOther.getApproveDt()) || ( this.getApproveDt()!=null && castOther.getApproveDt()!=null && this.getApproveDt().equals(castOther.getApproveDt()) ) );


   }
  
   public int hashCode() {
         int result = 17;
         // TODO : Add New Fields
         result = 37 * result + ( getSubProdReqNo() == null ? 0 : this.getSubProdReqNo().hashCode() );
         result = 37 * result + ( getEffectiveDt() == null ? 0 : this.getEffectiveDt().hashCode() );
         result = 37 * result + ( getReqBy() == null ? 0 : this.getReqBy().hashCode() );
         result = 37 * result + ( getReqDt() == null ? 0 : this.getReqDt().hashCode() );
         result = 37 * result + ( getSubscAction() == null ? 0 : this.getSubscAction().hashCode() );
         result = 37 * result + ( getAppStatus() == null ? 0 : this.getAppStatus().hashCode() );
        
         result = 37 * result + ( getMstbProdDiscMaster() == null ? 0 : this.getMstbProdDiscMaster().hashCode() );
         result = 37 * result + ( getLrtbRewardMaster() == null ? 0 : this.getLrtbRewardMaster().hashCode() );
         result = 37 * result + ( getMstbSubscFeeMaster() == null ? 0 : this.getMstbSubscFeeMaster().hashCode() );
         result = 37 * result + ( getMstbIssuanceFeeMaster() == null ? 0 : this.getMstbIssuanceFeeMaster().hashCode() );
         
         result = 37 * result + ( getAmtbAccount() == null ? 0 : this.getAmtbAccount().hashCode() );       
         result = 37 * result + ( getPmtbProductType() == null ? 0 : this.getPmtbProductType().hashCode() );
         
         result = 37 * result + ( getRemarks() == null ? 0 : this.getRemarks().hashCode() );
         
         result = 37 * result + ( getApproveBy() == null ? 0 : this.getApproveBy().hashCode() );
         result = 37 * result + ( getApproveDt() == null ? 0 : this.getApproveDt().hashCode() );
         return result;
   }  
}
