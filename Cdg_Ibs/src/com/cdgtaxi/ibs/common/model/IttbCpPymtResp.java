package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class IttbCpPymtResp  implements java.io.Serializable, Creatable, Updatable {
	
    // Fields    
	private Integer respId;
	private String loginId;
	private Long tmMcode;
	private String tmRefno;
	private String tmCurrency;
	private BigDecimal tmDebitamt;
	private String tmStatus;
	private String tmErrormsg;
	private String tmPaymenttype;
	private String tmApprovalcode;
	private String tmBankrespcode;
	private String tmError;
	private String tmUserfield1;
	private String tmUserfield2;
	private String tmUserfield3;
	private String tmUserfield4;
	private String tmUserfield5;
	private String tmTrntype;
	private String tmSubtrntype;
	private Integer tmCclast4digit;
	private String tmExpirydate;
	private String tmRecurrentid;
	private Long tmSubsequentmcode;
	private String tmCardnum;
    private Timestamp createdDt;
    private Timestamp updatedDt;
    private String updatedBy;
    private String createdBy;
    private Integer version;

    private Integer loginNo;
    private AmtbAccount amtbAccount;

    private IttbCpPymtReqSum ittbCpPymtReqSum;

    // Constructors

    /** default constructor */
    public IttbCpPymtResp() {
    }

    public Timestamp getCreatedDt() {
        return this.createdDt;
    }
    
    public void setCreatedDt(Timestamp createdDate) {
        this.createdDt = createdDate;
    }

    public Timestamp getUpdatedDt() {
        return this.updatedDt;
    }
    
    public void setUpdatedDt(Timestamp updatedDate) {
        this.updatedDt = updatedDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

 
    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("respId").append("='").append(getRespId()).append("' ");
      buffer.append("loginId").append("='").append(getLoginId()).append("' ");
      buffer.append("tmMcode").append("='").append(getTmMcode()).append("' ");
      buffer.append("tmRefno").append("='").append(getTmRefno()).append("' ");
      buffer.append("tmCurrency").append("='").append(getTmCurrency()).append("' ");
      buffer.append("tmDebitamt").append("='").append(getTmDebitamt()).append("' ");
      buffer.append("tmStatus").append("='").append(getTmStatus()).append("' ");			
      buffer.append("tmErrormsg").append("='").append(getTmErrormsg()).append("' ");	
      buffer.append("tmPaymenttype").append("='").append(getTmPaymenttype()).append("' ");	
      buffer.append("tmApprovalcode").append("='").append(getTmApprovalcode()).append("' ");	
      buffer.append("tmBankrespcode").append("='").append(getTmBankrespcode()).append("' ");	
      buffer.append("tmError").append("='").append(getTmError()).append("' ");	
      buffer.append("tmUserfield1").append("='").append(getTmUserfield1()).append("' ");	
      buffer.append("tmUserfield2").append("='").append(getTmUserfield2()).append("' ");
      buffer.append("tmUserfield3").append("='").append(getTmUserfield3()).append("' ");
      buffer.append("tmUserfield4").append("='").append(getTmUserfield4()).append("' ");
      buffer.append("tmUserfield5").append("='").append(getTmUserfield5()).append("' ");
      buffer.append("tmTrntype").append("='").append(getTmTrntype()).append("' ");
      buffer.append("tmSubtrntype").append("='").append(getTmSubtrntype()).append("' ");
      buffer.append("tmCclast4digit").append("='").append(getTmCclast4digit()).append("' ");
      buffer.append("tmExpirydate").append("='").append(getTmExpirydate()).append("' ");
      buffer.append("tmRecurrentid").append("='").append(getTmRecurrentid()).append("' ");
      buffer.append("tmSubsequentmcode").append("='").append(getTmSubsequentmcode()).append("' ");
      buffer.append("tmCardnum").append("='").append(getTmCardnum()).append("' ");
      buffer.append("createdDate").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("updatedDate").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


    
 
     
     
     
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbCpPymtResp) ) return false;
		 IttbCpPymtResp castOther = ( IttbCpPymtResp ) other; 
         
		 return ( (this.getRespId()==castOther.getRespId()) || ( this.getRespId()!=null && castOther.getRespId()!=null && this.getRespId().equals(castOther.getRespId()) ) )
 && ( (this.getLoginId()==castOther.getLoginId()) || ( this.getLoginId()!=null && castOther.getLoginId()!=null && this.getLoginId().equals(castOther.getLoginId()) ) )
 && ( (this.getTmMcode()==castOther.getTmMcode()) || ( this.getTmMcode()!=null && castOther.getTmMcode()!=null && this.getTmMcode().equals(castOther.getTmMcode()) ) )
 && ( (this.getTmRefno()==castOther.getTmRefno()) || ( this.getTmRefno()!=null && castOther.getTmRefno()!=null && this.getTmRefno().equals(castOther.getTmRefno()) ) )
 && ( (this.getTmCurrency()==castOther.getTmCurrency()) || ( this.getTmCurrency()!=null && castOther.getTmCurrency()!=null && this.getTmCurrency().equals(castOther.getTmCurrency()) ) )
 && ( (this.getTmDebitamt()==castOther.getTmDebitamt()) || ( this.getTmDebitamt()!=null && castOther.getTmDebitamt()!=null && this.getTmDebitamt().equals(castOther.getTmDebitamt()) ) )
 && ( (this.getTmStatus()==castOther.getTmStatus()) || ( this.getTmStatus()!=null && castOther.getTmStatus()!=null && this.getTmStatus().equals(castOther.getTmStatus()) ) )
 && ( (this.getTmErrormsg()==castOther.getTmErrormsg()) || ( this.getTmErrormsg()!=null && castOther.getTmErrormsg()!=null && this.getTmErrormsg().equals(castOther.getTmErrormsg()) ) )
 && ( (this.getTmPaymenttype()==castOther.getTmPaymenttype()) || ( this.getTmPaymenttype()!=null && castOther.getTmPaymenttype()!=null && this.getTmPaymenttype().equals(castOther.getTmPaymenttype()) ) )
 && ( (this.getTmApprovalcode()==castOther.getTmApprovalcode()) || ( this.getTmApprovalcode()!=null && castOther.getTmApprovalcode()!=null && this.getTmApprovalcode().equals(castOther.getTmApprovalcode()) ) )
 && ( (this.getTmBankrespcode()==castOther.getTmBankrespcode()) || ( this.getTmBankrespcode()!=null && castOther.getTmBankrespcode()!=null && this.getTmBankrespcode().equals(castOther.getTmBankrespcode()) ) )

 && ( (this.getTmError()==castOther.getTmError()) || ( this.getTmError()!=null && castOther.getTmError()!=null && this.getTmError().equals(castOther.getTmError()) ) )
 && ( (this.getTmUserfield1()==castOther.getTmUserfield1()) || ( this.getTmUserfield1()!=null && castOther.getTmUserfield1()!=null && this.getTmUserfield1().equals(castOther.getTmUserfield1()) ) )
 && ( (this.getTmUserfield2()==castOther.getTmUserfield2()) || ( this.getTmUserfield2()!=null && castOther.getTmUserfield2()!=null && this.getTmUserfield2().equals(castOther.getTmUserfield2()) ) )
 && ( (this.getTmUserfield3()==castOther.getTmUserfield3()) || ( this.getTmUserfield3()!=null && castOther.getTmUserfield3()!=null && this.getTmUserfield3().equals(castOther.getTmUserfield3()) ) )
 && ( (this.getTmUserfield4()==castOther.getTmUserfield4()) || ( this.getTmUserfield4()!=null && castOther.getTmUserfield4()!=null && this.getTmUserfield4().equals(castOther.getTmUserfield4()) ) )
 && ( (this.getTmUserfield5()==castOther.getTmUserfield5()) || ( this.getTmUserfield5()!=null && castOther.getTmUserfield5()!=null && this.getTmUserfield5().equals(castOther.getTmUserfield5()) ) )
 && ( (this.getTmTrntype()==castOther.getTmTrntype()) || ( this.getTmTrntype()!=null && castOther.getTmTrntype()!=null && this.getTmTrntype().equals(castOther.getTmTrntype()) ) )
 && ( (this.getTmSubtrntype()==castOther.getTmSubtrntype()) || ( this.getTmSubtrntype()!=null && castOther.getTmSubtrntype()!=null && this.getTmSubtrntype().equals(castOther.getTmSubtrntype()) ) )
 && ( (this.getTmCclast4digit()==castOther.getTmCclast4digit()) || ( this.getTmCclast4digit()!=null && castOther.getTmCclast4digit()!=null && this.getTmCclast4digit().equals(castOther.getTmCclast4digit()) ) )
 && ( (this.getTmExpirydate()==castOther.getTmExpirydate()) || ( this.getTmExpirydate()!=null && castOther.getTmExpirydate()!=null && this.getTmExpirydate().equals(castOther.getTmExpirydate()) ) )
 && ( (this.getTmRecurrentid()==castOther.getTmRecurrentid()) || ( this.getTmRecurrentid()!=null && castOther.getTmRecurrentid()!=null && this.getTmRecurrentid().equals(castOther.getTmRecurrentid()) ) )
 && ( (this.getTmSubsequentmcode()==castOther.getTmSubsequentmcode()) || ( this.getTmSubsequentmcode()!=null && castOther.getTmSubsequentmcode()!=null && this.getTmSubsequentmcode().equals(castOther.getTmSubsequentmcode()) ) )
 && ( (this.getTmCardnum()==castOther.getTmCardnum()) || ( this.getTmCardnum()!=null && castOther.getTmCardnum()!=null && this.getTmCardnum().equals(castOther.getTmCardnum()) ) )
 
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) );
   
   }
   
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRespId() == null ? 0 : this.getRespId().hashCode() );
         result = 37 * result + ( getLoginId() == null ? 0 : this.getLoginId().hashCode() );
         result = 37 * result + ( getTmMcode() == null ? 0 : this.getTmMcode().hashCode() );
         result = 37 * result + ( getTmRefno() == null ? 0 : this.getTmRefno().hashCode() );
         result = 37 * result + ( getTmCurrency() == null ? 0 : this.getTmCurrency().hashCode() );
         result = 37 * result + ( getTmDebitamt() == null ? 0 : this.getTmDebitamt().hashCode() );
         result = 37 * result + ( getTmStatus() == null ? 0 : this.getTmStatus().hashCode() );
         result = 37 * result + ( getTmErrormsg() == null ? 0 : this.getTmErrormsg().hashCode() );
         result = 37 * result + ( getTmPaymenttype() == null ? 0 : this.getTmPaymenttype().hashCode() );
         result = 37 * result + ( getTmApprovalcode() == null ? 0 : this.getTmApprovalcode().hashCode() );
         result = 37 * result + ( getTmBankrespcode() == null ? 0 : this.getTmBankrespcode().hashCode() );
         
         result = 37 * result + ( getTmError() == null ? 0 : this.getTmError().hashCode() );
         result = 37 * result + ( getTmUserfield1() == null ? 0 : this.getTmUserfield1().hashCode() );
         result = 37 * result + ( getTmUserfield2() == null ? 0 : this.getTmUserfield2().hashCode() );
         result = 37 * result + ( getTmUserfield3() == null ? 0 : this.getTmUserfield3().hashCode() );
         result = 37 * result + ( getTmUserfield4() == null ? 0 : this.getTmUserfield4().hashCode() );
         result = 37 * result + ( getTmUserfield5() == null ? 0 : this.getTmUserfield5().hashCode() );
         
         result = 37 * result + ( getTmTrntype() == null ? 0 : this.getTmTrntype().hashCode() );
         result = 37 * result + ( getTmSubtrntype() == null ? 0 : this.getTmSubtrntype().hashCode() );
         result = 37 * result + ( getTmCclast4digit() == null ? 0 : this.getTmCclast4digit().hashCode() );
         result = 37 * result + ( getTmExpirydate() == null ? 0 : this.getTmExpirydate().hashCode() );
         result = 37 * result + ( getTmRecurrentid() == null ? 0 : this.getTmRecurrentid().hashCode() );
         result = 37 * result + ( getTmSubsequentmcode() == null ? 0 : this.getTmSubsequentmcode().hashCode() );
         result = 37 * result + ( getTmCardnum() == null ? 0 : this.getTmCardnum().hashCode() );
         
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         
         return result;
   }

	public Integer getRespId() {
		return respId;
	}
	
	public String getLoginId() {
		return loginId;
	}
	
	public Long getTmMcode() {
		return tmMcode;
	}
	
	public String getTmRefno() {
		return tmRefno;
	}
	
	public String getTmCurrency() {
		return tmCurrency;
	}
	
	public String getTmRecurrentid() {
		return tmRecurrentid;
	}

	public void setTmRecurrentid(String tmRecurrentid) {
		this.tmRecurrentid = tmRecurrentid;
	}

	public BigDecimal getTmDebitamt() {
		return tmDebitamt;
	}
	
	public String getTmStatus() {
		return tmStatus;
	}
	
	public String getTmErrormsg() {
		return tmErrormsg;
	}
	
	public String getTmPaymenttype() {
		return tmPaymenttype;
	}
	
	public String getTmApprovalcode() {
		return tmApprovalcode;
	}
	
	public String getTmBankrespcode() {
		return tmBankrespcode;
	}
	
	public String getTmError() {
		return tmError;
	}
	
	public String getTmUserfield1() {
		return tmUserfield1;
	}
	
	public IttbCpPymtReqSum getIttbCpPymtReqSum() {
		return ittbCpPymtReqSum;
	}

	public void setIttbCpPymtReqSum(IttbCpPymtReqSum ittbCpPymtReqSum) {
		this.ittbCpPymtReqSum = ittbCpPymtReqSum;
	}

	public String getTmUserfield2() {
		return tmUserfield2;
	}
	
	public String getTmUserfield3() {
		return tmUserfield3;
	}
	
	public String getTmUserfield4() {
		return tmUserfield4;
	}
	
	public String getTmUserfield5() {
		return tmUserfield5;
	}
	
	public String getTmTrntype() {
		return tmTrntype;
	}
	
	public String getTmSubtrntype() {
		return tmSubtrntype;
	}
	
	public Integer getTmCclast4digit() {
		return tmCclast4digit;
	}
	
	public String getTmExpirydate() {
		return tmExpirydate;
	}
	
	public Long getTmSubsequentmcode() {
		return tmSubsequentmcode;
	}
	
	public String getTmCardnum() {
		return tmCardnum;
	}
	
	public void setRespId(Integer respId) {
		this.respId = respId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public void setTmMcode(Long tmMcode) {
		this.tmMcode = tmMcode;
	}
	
	public void setTmRefno(String tmRefno) {
		this.tmRefno = tmRefno;
	}
	
	public void setTmCurrency(String tmCurrency) {
		this.tmCurrency = tmCurrency;
	}
	
	public void setTmDebitamt(BigDecimal tmDebitamt) {
		this.tmDebitamt = tmDebitamt;
	}
	
	public void setTmStatus(String tmStatus) {
		this.tmStatus = tmStatus;
	}
	
	public void setTmErrormsg(String tmErrormsg) {
		this.tmErrormsg = tmErrormsg;
	}
	
	public void setTmPaymenttype(String tmPaymenttype) {
		this.tmPaymenttype = tmPaymenttype;
	}
	
	public void setTmApprovalcode(String tmApprovalcode) {
		this.tmApprovalcode = tmApprovalcode;
	}
	
	public void setTmBankrespcode(String tmBankrespcode) {
		this.tmBankrespcode = tmBankrespcode;
	}
	
	public void setTmError(String tmError) {
		this.tmError = tmError;
	}
	
	public void setTmUserfield1(String tmUserfield1) {
		this.tmUserfield1 = tmUserfield1;
	}
	
	public void setTmUserfield2(String tmUserfield2) {
		this.tmUserfield2 = tmUserfield2;
	}
	
	public void setTmUserfield3(String tmUserfield3) {
		this.tmUserfield3 = tmUserfield3;
	}
	
	public void setTmUserfield4(String tmUserfield4) {
		this.tmUserfield4 = tmUserfield4;
	}
	
	public void setTmUserfield5(String tmUserfield5) {
		this.tmUserfield5 = tmUserfield5;
	}
	
	public void setTmTrntype(String tmTrntype) {
		this.tmTrntype = tmTrntype;
	}
	
	public void setTmSubtrntype(String tmSubtrntype) {
		this.tmSubtrntype = tmSubtrntype;
	}
	
	public void setTmCclast4digit(Integer tmCclast4digit) {
		this.tmCclast4digit = tmCclast4digit;
	}
	
	public void setTmExpirydate(String tmExpirydate) {
		this.tmExpirydate = tmExpirydate;
	}
	
	public void setTmSubsequentmcode(Long tmSubsequentmcode) {
		this.tmSubsequentmcode = tmSubsequentmcode;
	}
	
	public void setTmCardnum(String tmCardnum) {
		this.tmCardnum = tmCardnum;
	}

	public Integer getLoginNo() {
		return loginNo;
	}

	public AmtbAccount getAmtbAccount() {
		return amtbAccount;
	}



	public void setLoginNo(Integer loginNo) {
		this.loginNo = loginNo;
	}

	public void setAmtbAccount(AmtbAccount amtbAccount) {
		this.amtbAccount = amtbAccount;
	}



}
