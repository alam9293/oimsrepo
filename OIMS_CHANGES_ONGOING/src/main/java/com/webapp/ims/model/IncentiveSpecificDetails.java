package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class IncentiveSpecificDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ISF_ID")
	private String id;

	@Column(name = "ISF_APC_ID")
	private String ISF_APC_Id;

	@Column(name = "ISF_Amt_SGST_Claimed")
	private Long ISF_Claim_Reim;

	@Column(name = "ISF_Reim_SCST")
	private Long ISF_Reim_SCST;

	@Column(name = "ISF_Reim_FW")
	private Long ISF_Reim_FW;

	@Column(name = "ISF_Reim_BPLW")
	private Long ISF_Reim_BPLW;

	@Column(name = "ISF_Ttl_SGST_Reim")
	private Long ISF_Ttl_SGST_Reim;

	@Column(name = "ISF_Amt_Stamp_Duty_EX")
	private Long ISF_Stamp_Duty_EX;

	@Column(name = "ISF_Additonal_Stamp_Duty_EX")
	private Long ISF_Additonal_Stamp_Duty_EX;

	@Column(name = "ISF_Amt_Stamp_Duty_Reim")
	private Long ISF_Amt_Stamp_Duty_Reim;

	@Column(name = "ISF_Indus_Payroll_Asst")
	private Long ISF_Indus_Payroll_Asst;

	@Column(name = "ISF_Ttl_Stamp_Duty_EX")
	private Long ISF_Ttl_Stamp_Duty_EX;

	@Column(name = "ISF_EPF_Reim_USW")
	private Long ISF_Epf_Reim_UW;

	@Column(name = "ISF_Add_EPF_SKUNSK")
	private Long ISF_Add_Epf_Reim_SkUkW;

	@Column(name = "ISF_Add_EPF_Reim_DIVSCSTF")
	private Long ISF_Add_Epf_Reim_DIVSCSTF;

	@Column(name = "ISF_Ttl_EPF_Reim")
	private Long ISF_Ttl_EPF_Reim;

	@Column(name = "ISF_Cap_Int_Subsidy")
	private Long ISF_Cis;

	@Column(name = "ISF_ACI_Subsidy_Indus_DIVSCSTF")
	private Long ISF_ACI_Subsidy_Indus;

	@Column(name = "ISF_Infra_Int_Subsidy")
	private Long ISF_Infra_Int_Subsidy;

	@Column(name = "ISF_AII_Subsidy_DIVSCSTF")
	private Long ISF_AII_Subsidy_DIVSCSTF;

	@Column(name = "ISF_Loan_Subsidy")
	private Long ISF_Loan_Subsidy;

	@Column(name = "ISF_Total_Int_Subsidy")
	private Long ISF_Total_Int_Subsidy;

	@Column(name = "ISF_Tax_Credit_Reim")
	private Long ISF_Tax_Credit_Reim;

	@Column(name = "ISF_EX_E_Duty")
	private Long ISF_EX_E_Duty;

	@Column(name = "ISF_EX_E_Duty_PC")
	private Long ISF_EX_E_Duty_PC;

	@Column(name = "ISF_EX_Mandi_Fee")
	private Long ISF_EX_Mandee_Fee;

	@Column(name = "ISF_Ttl_Oth_Inc")
	private Long Total_Other_Incentive;

	@Column(name = "ISF_Created_By")
	private String created_By;

	@Column(name = "ISF_Modified_By")
	private String modified_By;

	@Column(name = "ISF_Modify_Date")
	private Date modify_Date;

	@Column(name = "ISF_Status")
	private String status;

	@Column(name = "ISF_Cstm_Inc_Status")
	private String ISF_Cstm_Inc_Status;

	@OneToMany(mappedBy = "incentiveDetails", cascade = CascadeType.ALL)
	private List<AvailCustomisedDetails> availCustomisedDetails;

	@Column(name = "ISF_Cstm_Inv_Doc_Name")
	private String isfCustIncDocName;

	@Column(name = "ISF_Cstm_Inv_Doc")
	private byte[] isfCustIncDoc;
	
	@Column(name = "Oth_Add_Request1")
	private String othAddRequest1;
	
	
	@Column(name = "ISF_SGST_Comment")
	private String isfSgstComment;

	@Column(name = "ISF_SCST_Comment")
	private String isfScstComment;

	@Column(name = "ISF_FW_Comment")
	private String isffwComment;

	@Column(name = "ISF_BPL_Comment")
	private String isfBplComment;

	@Column(name = "ISF_Elec_Duty_Comment")
	private String isfElecdutyComment;

	@Column(name = "ISF_Mandi_Comment")
	private String isfMandiComment;
	
	@Column(name = "ISF_STAMP_Comment")
	private String isfStampComment;
	
	@Column(name = "ISF_STAMPREM_Comment")
	private String isfStampremComment;
	
	@Column(name = "ISF_STAMPSCST_Comment")
	private String isfStampscstComment;
	
	@Column(name = "ISF_EPF_Comment")
	private String isfepfComment;
	
	@Column(name = "ISF_EPFADD_Comment")
	private String isfepfaddComment;
	
	@Column(name = "ISF_EPFSC_Comment")
	private String isfepfscComment;
	
	@Column(name = "ISF_CAPIS_Comment")
	private String isfcapisComment;
	
	@Column(name = "ISF_CAPISA_Comment")
	private String isfcapisaComment;
	
	@Column(name = "ISF_INF_Comment")
	private String isfinfComment;
	
	@Column(name = "ISF_INFA_Comment")
	private String isfinfaComment;
	
	@Column(name = "ISF_ISLOAN_Comment")
	private String isfloanComment;
	
	@Column(name = "ISF_DIS_Comment") // disallowed
	private String isfdisComment;
	
	@Column(name = "ISF_ELEPODOWN_Comment") //elec power down
	private String isfelepodownComment;
	
	@Column(name = "ISF_DIFFERABIL_Comment") // differable EPF
	private String isfdifferabilComment;
	
	@Column(name = "ISF_SGST_Remark") 
	private String sgstRemark;
	
	@Column(name = "ISF_SCST_Remark") 
	private String scstRemark;
		
	@Column(name = "ISF_Stamp_Duty_Exempt_Remark") 
	private String stampDutyExemptRemark;
	
	@Column(name = "ISF_Stamp_Duty_Reim_Remark") 
	private String stampDutyReimRemark;
	
	@Column(name = "ISF_Cap_Int_Sub_Remark") 
	private String capIntSubRemark;
	
	@Column(name = "ISF_Infra_Int_Sub_Remark") 
	private String infraIntSubRemark;
	
	@Column(name = "ISF_Loan_Int_Sub_Remark") 
	private String loanIntSubRemark;
	
	@Column(name = "ISF_Input_Tax _Remark") 
	private String inputTaxRemark;
	
	@Column(name = "ISF_Elec_Duty_Captive_Remark") 
	private String elecDutyCapRemark;
	
	@Column(name = "ISF_Elec_Duty_Drawn_Remark") 
	private String elecDutyDrawnRemark;
	
	@Column(name = "ISF_Mandi_Fee_Remark") 
	private String mandiFeeRemark;
	
	@Column(name = "ISF_Diff_Abled_Workers_Remark") 
	private String diffAbleWorkRemark;
	
	

	@Column(name = "ISF_Reim_FW_Remark") 
	private String fwRemark;	
	@Column(name = "ISF_Reim_BPLW_Remark") 
	private String bplRemark;
	@Column(name = "ISF_DvngSCST_Remark") 
	private String divyangSCSTRemark;	
	@Column(name = "ISF_Epf_Unskl_Remark") 
	private String epfUnsklRemark;
	@Column(name = "ISF_Epf_SklUnskl_Remark") 
	private String epfSklUnsklRemark;	
	@Column(name = "ISF_Epf_Divscstf_Remark") 
	private String epfDvngSCSTRemark;
	@Column(name = "ISF_ACI_Subsidy_Remark") 
	private String aciSubsidyRemark;	
	@Column(name = "ISF_AII_Subsidy_Remark") 
	private String aiiSubsidyRemark;

	@Column(name = "ISF_SGST_Comment_Dis")
	private String isfSgstCommentdis;

	@Column(name = "ISF_SCST_Comment_Dis")
	private String isfScstCommentdis;

	@Column(name = "ISF_FW_Comment_Dis")
	private String isffwCommentdis;

	@Column(name = "ISF_BPL_Comment_Dis")
	private String isfBplCommentdis;

	@Column(name = "ISF_Elec_Duty_Comment_Dis") // Electricity Duty on power drawn
	// from power companies for 20 years
	private String isfElecdutyCommentdis;

	@Column(name = "ISF_Mandi_Comment_Dis")
	private String isfMandiCommentdis;

	@Column(name = "ISF_STAMP_Comment_Dis")
	private String isfStampCommentdis;

	@Column(name = "ISF_STAMPREM_Comment_Dis")
	private String isfStampremCommentdis;

	@Column(name = "ISF_STAMPSCST_Comment_Dis")
	private String isfStampscstCommentdis;

	@Column(name = "ISF_EPF_Comment_Dis")
	private String isfepfCommentdis;

	@Column(name = "ISF_EPFADD_Comment_Dis")
	private String isfepfaddCommentdis;

	@Column(name = "ISF_EPFSC_Comment_Dis")
	private String isfepfscCommentdis;

	@Column(name = "ISF_CAPIS_Comment_Dis")
	private String isfcapisCommentdis;

	@Column(name = "ISF_CAPISA_Comment_Dis")
	private String isfcapisaCommentdis;

	@Column(name = "ISF_INF_Comment_Dis")
	private String isfinfCommentdis;

	@Column(name = "ISF_INFA_Comment_Dis")
	private String isfinfaCommentdis;

	@Column(name = "ISF_ISLOAN_Comment_Dis")
	private String isfloanCommentdis;

	@Column(name = "ISF_DIS_Comment_Dis")
	private String isfdisCommentdis;

	@Column(name = "ISF_ELEPODOWN_Comment_Dis")
	private String isfelepodownCommentdis;

	@Column(name = "ISF_DIFFERABIL_Comment_Dis")
	private String isfdifferabilCommentdis;
	
	
	private transient int index;
	
	
	

	public String getIsfSgstCommentdis() {
		return isfSgstCommentdis;
	}

	public void setIsfSgstCommentdis(String isfSgstCommentdis) {
		this.isfSgstCommentdis = isfSgstCommentdis;
	}

	public String getIsfScstCommentdis() {
		return isfScstCommentdis;
	}

	public void setIsfScstCommentdis(String isfScstCommentdis) {
		this.isfScstCommentdis = isfScstCommentdis;
	}

	public String getIsffwCommentdis() {
		return isffwCommentdis;
	}

	public void setIsffwCommentdis(String isffwCommentdis) {
		this.isffwCommentdis = isffwCommentdis;
	}

	public String getIsfBplCommentdis() {
		return isfBplCommentdis;
	}

	public void setIsfBplCommentdis(String isfBplCommentdis) {
		this.isfBplCommentdis = isfBplCommentdis;
	}

	public String getIsfElecdutyCommentdis() {
		return isfElecdutyCommentdis;
	}

	public void setIsfElecdutyCommentdis(String isfElecdutyCommentdis) {
		this.isfElecdutyCommentdis = isfElecdutyCommentdis;
	}

	public String getIsfMandiCommentdis() {
		return isfMandiCommentdis;
	}

	public void setIsfMandiCommentdis(String isfMandiCommentdis) {
		this.isfMandiCommentdis = isfMandiCommentdis;
	}

	public String getIsfStampCommentdis() {
		return isfStampCommentdis;
	}

	public void setIsfStampCommentdis(String isfStampCommentdis) {
		this.isfStampCommentdis = isfStampCommentdis;
	}

	public String getIsfStampremCommentdis() {
		return isfStampremCommentdis;
	}

	public void setIsfStampremCommentdis(String isfStampremCommentdis) {
		this.isfStampremCommentdis = isfStampremCommentdis;
	}

	public String getIsfStampscstCommentdis() {
		return isfStampscstCommentdis;
	}

	public void setIsfStampscstCommentdis(String isfStampscstCommentdis) {
		this.isfStampscstCommentdis = isfStampscstCommentdis;
	}

	public String getIsfepfCommentdis() {
		return isfepfCommentdis;
	}

	public void setIsfepfCommentdis(String isfepfCommentdis) {
		this.isfepfCommentdis = isfepfCommentdis;
	}

	public String getIsfepfaddCommentdis() {
		return isfepfaddCommentdis;
	}

	public void setIsfepfaddCommentdis(String isfepfaddCommentdis) {
		this.isfepfaddCommentdis = isfepfaddCommentdis;
	}

	public String getIsfepfscCommentdis() {
		return isfepfscCommentdis;
	}

	public void setIsfepfscCommentdis(String isfepfscCommentdis) {
		this.isfepfscCommentdis = isfepfscCommentdis;
	}

	public String getIsfcapisCommentdis() {
		return isfcapisCommentdis;
	}

	public void setIsfcapisCommentdis(String isfcapisCommentdis) {
		this.isfcapisCommentdis = isfcapisCommentdis;
	}

	public String getIsfcapisaCommentdis() {
		return isfcapisaCommentdis;
	}

	public void setIsfcapisaCommentdis(String isfcapisaCommentdis) {
		this.isfcapisaCommentdis = isfcapisaCommentdis;
	}

	public String getIsfinfCommentdis() {
		return isfinfCommentdis;
	}

	public void setIsfinfCommentdis(String isfinfCommentdis) {
		this.isfinfCommentdis = isfinfCommentdis;
	}

	public String getIsfinfaCommentdis() {
		return isfinfaCommentdis;
	}

	public void setIsfinfaCommentdis(String isfinfaCommentdis) {
		this.isfinfaCommentdis = isfinfaCommentdis;
	}

	public String getIsfloanCommentdis() {
		return isfloanCommentdis;
	}

	public void setIsfloanCommentdis(String isfloanCommentdis) {
		this.isfloanCommentdis = isfloanCommentdis;
	}

	public String getIsfdisCommentdis() {
		return isfdisCommentdis;
	}

	public void setIsfdisCommentdis(String isfdisCommentdis) {
		this.isfdisCommentdis = isfdisCommentdis;
	}

	public String getIsfelepodownCommentdis() {
		return isfelepodownCommentdis;
	}

	public void setIsfelepodownCommentdis(String isfelepodownCommentdis) {
		this.isfelepodownCommentdis = isfelepodownCommentdis;
	}

	public String getIsfdifferabilCommentdis() {
		return isfdifferabilCommentdis;
	}

	public void setIsfdifferabilCommentdis(String isfdifferabilCommentdis) {
		this.isfdifferabilCommentdis = isfdifferabilCommentdis;
	}

	public String getFwRemark() {
		return fwRemark;
	}

	public void setFwRemark(String fwRemark) {
		this.fwRemark = fwRemark;
	}

	public String getBplRemark() {
		return bplRemark;
	}

	public void setBplRemark(String bplRemark) {
		this.bplRemark = bplRemark;
	}

	public String getDivyangSCSTRemark() {
		return divyangSCSTRemark;
	}

	public void setDivyangSCSTRemark(String divyangSCSTRemark) {
		this.divyangSCSTRemark = divyangSCSTRemark;
	}

	public String getEpfUnsklRemark() {
		return epfUnsklRemark;
	}

	public void setEpfUnsklRemark(String epfUnsklRemark) {
		this.epfUnsklRemark = epfUnsklRemark;
	}

	public String getEpfSklUnsklRemark() {
		return epfSklUnsklRemark;
	}

	public void setEpfSklUnsklRemark(String epfSklUnsklRemark) {
		this.epfSklUnsklRemark = epfSklUnsklRemark;
	}

	public String getEpfDvngSCSTRemark() {
		return epfDvngSCSTRemark;
	}

	public void setEpfDvngSCSTRemark(String epfDvngSCSTRemark) {
		this.epfDvngSCSTRemark = epfDvngSCSTRemark;
	}

	public String getAciSubsidyRemark() {
		return aciSubsidyRemark;
	}

	public void setAciSubsidyRemark(String aciSubsidyRemark) {
		this.aciSubsidyRemark = aciSubsidyRemark;
	}

	public String getAiiSubsidyRemark() {
		return aiiSubsidyRemark;
	}

	public void setAiiSubsidyRemark(String aiiSubsidyRemark) {
		this.aiiSubsidyRemark = aiiSubsidyRemark;
	}

	public String getSgstRemark() {
		return sgstRemark;
	}

	public void setSgstRemark(String sgstRemark) {
		this.sgstRemark = sgstRemark;
	}

	public String getScstRemark() {
		return scstRemark;
	}

	public void setScstRemark(String scstRemark) {
		this.scstRemark = scstRemark;
	}

	public String getStampDutyExemptRemark() {
		return stampDutyExemptRemark;
	}

	public void setStampDutyExemptRemark(String stampDutyExemptRemark) {
		this.stampDutyExemptRemark = stampDutyExemptRemark;
	}

	public String getStampDutyReimRemark() {
		return stampDutyReimRemark;
	}

	public void setStampDutyReimRemark(String stampDutyReimRemark) {
		this.stampDutyReimRemark = stampDutyReimRemark;
	}

	public String getCapIntSubRemark() {
		return capIntSubRemark;
	}

	public void setCapIntSubRemark(String capIntSubRemark) {
		this.capIntSubRemark = capIntSubRemark;
	}

	public String getInfraIntSubRemark() {
		return infraIntSubRemark;
	}

	public void setInfraIntSubRemark(String infraIntSubRemark) {
		this.infraIntSubRemark = infraIntSubRemark;
	}

	public String getLoanIntSubRemark() {
		return loanIntSubRemark;
	}

	public void setLoanIntSubRemark(String loanIntSubRemark) {
		this.loanIntSubRemark = loanIntSubRemark;
	}

	public String getInputTaxRemark() {
		return inputTaxRemark;
	}

	public void setInputTaxRemark(String inputTaxRemark) {
		this.inputTaxRemark = inputTaxRemark;
	}

	public String getElecDutyCapRemark() {
		return elecDutyCapRemark;
	}

	public void setElecDutyCapRemark(String elecDutyCapRemark) {
		this.elecDutyCapRemark = elecDutyCapRemark;
	}

	public String getElecDutyDrawnRemark() {
		return elecDutyDrawnRemark;
	}

	public void setElecDutyDrawnRemark(String elecDutyDrawnRemark) {
		this.elecDutyDrawnRemark = elecDutyDrawnRemark;
	}

	public String getMandiFeeRemark() {
		return mandiFeeRemark;
	}

	public void setMandiFeeRemark(String mandiFeeRemark) {
		this.mandiFeeRemark = mandiFeeRemark;
	}

	public String getDiffAbleWorkRemark() {
		return diffAbleWorkRemark;
	}

	public void setDiffAbleWorkRemark(String diffAbleWorkRemark) {
		this.diffAbleWorkRemark = diffAbleWorkRemark;
	}

	public String getIsfStampComment() {
		return isfStampComment;
	}

	public void setIsfStampComment(String isfStampComment) {
		this.isfStampComment = isfStampComment;
	}

	public String getIsfStampremComment() {
		return isfStampremComment;
	}

	public void setIsfStampremComment(String isfStampremComment) {
		this.isfStampremComment = isfStampremComment;
	}

	public String getIsfStampscstComment() {
		return isfStampscstComment;
	}

	public void setIsfStampscstComment(String isfStampscstComment) {
		this.isfStampscstComment = isfStampscstComment;
	}

	public String getIsfepfComment() {
		return isfepfComment;
	}

	public void setIsfepfComment(String isfepfComment) {
		this.isfepfComment = isfepfComment;
	}

	public String getIsfepfaddComment() {
		return isfepfaddComment;
	}

	public void setIsfepfaddComment(String isfepfaddComment) {
		this.isfepfaddComment = isfepfaddComment;
	}

	public String getIsfepfscComment() {
		return isfepfscComment;
	}

	public void setIsfepfscComment(String isfepfscComment) {
		this.isfepfscComment = isfepfscComment;
	}

	public String getIsfcapisComment() {
		return isfcapisComment;
	}

	public void setIsfcapisComment(String isfcapisComment) {
		this.isfcapisComment = isfcapisComment;
	}

	public String getIsfcapisaComment() {
		return isfcapisaComment;
	}

	public void setIsfcapisaComment(String isfcapisaComment) {
		this.isfcapisaComment = isfcapisaComment;
	}

	public String getIsfinfComment() {
		return isfinfComment;
	}

	public void setIsfinfComment(String isfinfComment) {
		this.isfinfComment = isfinfComment;
	}

	public String getIsfinfaComment() {
		return isfinfaComment;
	}

	public void setIsfinfaComment(String isfinfaComment) {
		this.isfinfaComment = isfinfaComment;
	}

	public String getIsfloanComment() {
		return isfloanComment;
	}

	public void setIsfloanComment(String isfloanComment) {
		this.isfloanComment = isfloanComment;
	}

	public String getIsfdisComment() {
		return isfdisComment;
	}

	public void setIsfdisComment(String isfdisComment) {
		this.isfdisComment = isfdisComment;
	}

	public String getIsfelepodownComment() {
		return isfelepodownComment;
	}

	public void setIsfelepodownComment(String isfelepodownComment) {
		this.isfelepodownComment = isfelepodownComment;
	}

	public String getIsfdifferabilComment() {
		return isfdifferabilComment;
	}

	public void setIsfdifferabilComment(String isfdifferabilComment) {
		this.isfdifferabilComment = isfdifferabilComment;
	}

	public String getIsfSgstComment() {
		return isfSgstComment;
	}

	public void setIsfSgstComment(String isfSgstComment) {
		this.isfSgstComment = isfSgstComment;
	}

	public String getIsfScstComment() {
		return isfScstComment;
	}

	public void setIsfScstComment(String isfScstComment) {
		this.isfScstComment = isfScstComment;
	}

	public String getIsffwComment() {
		return isffwComment;
	}

	public void setIsffwComment(String isffwComment) {
		this.isffwComment = isffwComment;
	}

	public String getIsfBplComment() {
		return isfBplComment;
	}

	public void setIsfBplComment(String isfBplComment) {
		this.isfBplComment = isfBplComment;
	}

	public String getIsfElecdutyComment() {
		return isfElecdutyComment;
	}

	public void setIsfElecdutyComment(String isfElecdutyComment) {
		this.isfElecdutyComment = isfElecdutyComment;
	}

	public String getIsfMandiComment() {
		return isfMandiComment;
	}

	public void setIsfMandiComment(String isfMandiComment) {
		this.isfMandiComment = isfMandiComment;
	}

	public String getOthAddRequest1() {
		return othAddRequest1;
	}

	public void setOthAddRequest1(String othAddRequest1) {
		this.othAddRequest1 = othAddRequest1;
	}


	private transient String acdid;

	public String getAcdid() {
		return acdid;
	}

	public void setAcdid(String acdid) {
		this.acdid = acdid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getISF_APC_Id() {
		return ISF_APC_Id;
	}

	public void setISF_APC_Id(String iSF_APC_Id) {
		ISF_APC_Id = iSF_APC_Id;
	}

	public Long getISF_Claim_Reim() {
		return ISF_Claim_Reim;
	}

	public void setISF_Claim_Reim(Long iSF_Claim_Reim) {
		ISF_Claim_Reim = iSF_Claim_Reim;
	}

	public Long getISF_Reim_SCST() {
		return ISF_Reim_SCST;
	}

	public void setISF_Reim_SCST(Long iSF_Reim_SCST) {
		ISF_Reim_SCST = iSF_Reim_SCST;
	}

	public Long getISF_Reim_FW() {
		return ISF_Reim_FW;
	}

	public void setISF_Reim_FW(Long iSF_Reim_FW) {
		ISF_Reim_FW = iSF_Reim_FW;
	}

	public Long getISF_Reim_BPLW() {
		return ISF_Reim_BPLW;
	}

	public void setISF_Reim_BPLW(Long iSF_Reim_BPLW) {
		ISF_Reim_BPLW = iSF_Reim_BPLW;
	}

	public Long getISF_Ttl_SGST_Reim() {
		return ISF_Ttl_SGST_Reim;
	}

	public void setISF_Ttl_SGST_Reim(Long iSF_Ttl_SGST_Reim) {
		ISF_Ttl_SGST_Reim = iSF_Ttl_SGST_Reim;
	}

	public Long getISF_Stamp_Duty_EX() {
		return ISF_Stamp_Duty_EX;
	}

	public void setISF_Stamp_Duty_EX(Long iSF_Stamp_Duty_EX) {
		ISF_Stamp_Duty_EX = iSF_Stamp_Duty_EX;
	}

	public Long getISF_Additonal_Stamp_Duty_EX() {
		return ISF_Additonal_Stamp_Duty_EX;
	}

	public void setISF_Additonal_Stamp_Duty_EX(Long iSF_Additonal_Stamp_Duty_EX) {
		ISF_Additonal_Stamp_Duty_EX = iSF_Additonal_Stamp_Duty_EX;
	}

	public Long getISF_Ttl_Stamp_Duty_EX() {
		return ISF_Ttl_Stamp_Duty_EX;
	}

	public void setISF_Ttl_Stamp_Duty_EX(Long iSF_Ttl_Stamp_Duty_EX) {
		ISF_Ttl_Stamp_Duty_EX = iSF_Ttl_Stamp_Duty_EX;
	}

	public Long getISF_Epf_Reim_UW() {
		return ISF_Epf_Reim_UW;
	}

	public void setISF_Epf_Reim_UW(Long iSF_Epf_Reim_UW) {
		ISF_Epf_Reim_UW = iSF_Epf_Reim_UW;
	}

	public Long getISF_Add_Epf_Reim_SkUkW() {
		return ISF_Add_Epf_Reim_SkUkW;
	}

	public void setISF_Add_Epf_Reim_SkUkW(Long iSF_Add_Epf_Reim_SkUkW) {
		ISF_Add_Epf_Reim_SkUkW = iSF_Add_Epf_Reim_SkUkW;
	}

	public Long getISF_Add_Epf_Reim_DIVSCSTF() {
		return ISF_Add_Epf_Reim_DIVSCSTF;
	}

	public void setISF_Add_Epf_Reim_DIVSCSTF(Long iSF_Add_Epf_Reim_DIVSCSTF) {
		ISF_Add_Epf_Reim_DIVSCSTF = iSF_Add_Epf_Reim_DIVSCSTF;
	}

	public Long getISF_Ttl_EPF_Reim() {
		return ISF_Ttl_EPF_Reim;
	}

	public void setISF_Ttl_EPF_Reim(Long iSF_Ttl_EPF_Reim) {
		ISF_Ttl_EPF_Reim = iSF_Ttl_EPF_Reim;
	}

	public Long getISF_Cis() {
		return ISF_Cis;
	}

	public void setISF_Cis(Long iSF_Cis) {
		ISF_Cis = iSF_Cis;
	}

	public Long getISF_ACI_Subsidy_Indus() {
		return ISF_ACI_Subsidy_Indus;
	}

	public void setISF_ACI_Subsidy_Indus(Long iSF_ACI_Subsidy_Indus) {
		ISF_ACI_Subsidy_Indus = iSF_ACI_Subsidy_Indus;
	}

	public Long getISF_Infra_Int_Subsidy() {
		return ISF_Infra_Int_Subsidy;
	}

	public void setISF_Infra_Int_Subsidy(Long iSF_Infra_Int_Subsidy) {
		ISF_Infra_Int_Subsidy = iSF_Infra_Int_Subsidy;
	}

	public Long getISF_AII_Subsidy_DIVSCSTF() {
		return ISF_AII_Subsidy_DIVSCSTF;
	}

	public void setISF_AII_Subsidy_DIVSCSTF(Long iSF_AII_Subsidy_DIVSCSTF) {
		ISF_AII_Subsidy_DIVSCSTF = iSF_AII_Subsidy_DIVSCSTF;
	}

	public Long getISF_Loan_Subsidy() {
		return ISF_Loan_Subsidy;
	}

	public void setISF_Loan_Subsidy(Long iSF_Loan_Subsidy) {
		ISF_Loan_Subsidy = iSF_Loan_Subsidy;
	}

	public Long getISF_Total_Int_Subsidy() {
		return ISF_Total_Int_Subsidy;
	}

	public void setISF_Total_Int_Subsidy(Long iSF_Total_Int_Subsidy) {
		ISF_Total_Int_Subsidy = iSF_Total_Int_Subsidy;
	}

	public Long getISF_Tax_Credit_Reim() {
		return ISF_Tax_Credit_Reim;
	}

	public void setISF_Tax_Credit_Reim(Long iSF_Tax_Credit_Reim) {
		ISF_Tax_Credit_Reim = iSF_Tax_Credit_Reim;
	}

	public Long getISF_EX_E_Duty() {
		return ISF_EX_E_Duty;
	}

	public void setISF_EX_E_Duty(Long iSF_EX_E_Duty) {
		ISF_EX_E_Duty = iSF_EX_E_Duty;
	}

	public Long getISF_EX_E_Duty_PC() {
		return ISF_EX_E_Duty_PC;
	}

	public void setISF_EX_E_Duty_PC(Long iSF_EX_E_Duty_PC) {
		ISF_EX_E_Duty_PC = iSF_EX_E_Duty_PC;
	}

	public Long getISF_EX_Mandee_Fee() {
		return ISF_EX_Mandee_Fee;
	}

	public void setISF_EX_Mandee_Fee(Long iSF_EX_Mandee_Fee) {
		ISF_EX_Mandee_Fee = iSF_EX_Mandee_Fee;
	}

	public Long getTotal_Other_Incentive() {
		return Total_Other_Incentive;
	}

	public void setTotal_Other_Incentive(Long total_Other_Incentive) {
		Total_Other_Incentive = total_Other_Incentive;
	}

	public String getCreated_By() {
		return created_By;
	}

	public void setCreated_By(String created_By) {
		this.created_By = created_By;
	}

	public String getModified_By() {
		return modified_By;
	}

	public void setModified_By(String modified_By) {
		this.modified_By = modified_By;
	}

	public Date getModify_Date() {
		return modify_Date;
	}

	public void setModify_Date(Date modify_Date) {
		this.modify_Date = modify_Date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getISF_Cstm_Inc_Status() {
		return ISF_Cstm_Inc_Status;
	}

	public void setISF_Cstm_Inc_Status(String iSF_Cstm_Inc_Status) {
		ISF_Cstm_Inc_Status = iSF_Cstm_Inc_Status;
	}

	public Long getISF_Amt_Stamp_Duty_Reim() {
		return ISF_Amt_Stamp_Duty_Reim;
	}

	public void setISF_Amt_Stamp_Duty_Reim(Long iSF_Amt_Stamp_Duty_Reim) {
		ISF_Amt_Stamp_Duty_Reim = iSF_Amt_Stamp_Duty_Reim;
	}

	public Long getISF_Indus_Payroll_Asst() {
		return ISF_Indus_Payroll_Asst;
	}

	public void setISF_Indus_Payroll_Asst(Long iSF_Indus_Payroll_Asst) {
		ISF_Indus_Payroll_Asst = iSF_Indus_Payroll_Asst;
	}

	public List<AvailCustomisedDetails> getAvailCustomisedDetails() {
		return availCustomisedDetails;
	}

	public void setAvailCustomisedDetails(List<AvailCustomisedDetails> availCustomisedDetails) {
		this.availCustomisedDetails = availCustomisedDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "Add_Inc_Type")
	private String addIncentiveType;

	@Column(name = "Add_Amt")
	private Long addAmt;

	@Column(name = "Type_Dtl_Cus_Inc")
	private String typeDtlCusIncentives;

	@Column(name = "Oth_Add_Request")
	private String othAddRequest;

	public String getAddIncentiveType() {
		return addIncentiveType;
	}

	public void setAddIncentiveType(String addIncentiveType) {
		this.addIncentiveType = addIncentiveType;
	}

	public Long getAddAmt() {
		return addAmt;
	}

	public void setAddAmt(Long addAmt) {
		this.addAmt = addAmt;
	}

	public String getTypeDtlCusIncentives() {
		return typeDtlCusIncentives;
	}

	public void setTypeDtlCusIncentives(String typeDtlCusIncentives) {
		this.typeDtlCusIncentives = typeDtlCusIncentives;
	}

	public String getOthAddRequest() {
		return othAddRequest;
	}

	public void setOthAddRequest(String othAddRequest) {
		this.othAddRequest = othAddRequest;
	}

	public String getIsfCustIncDocName() {
		return isfCustIncDocName;
	}

	public void setIsfCustIncDocName(String isfCustIncDocName) {
		this.isfCustIncDocName = isfCustIncDocName;
	}

	public byte[] getIsfCustIncDoc() {
		return isfCustIncDoc;
	}

	public void setIsfCustIncDoc(byte[] isfCustIncDoc) {
		this.isfCustIncDoc = isfCustIncDoc;
	}

}
