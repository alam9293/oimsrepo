package com.cdgtaxi.ibs.common.model;
// Generated Jul 20, 2009 4:09:38 PM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * BmtbInvoiceDetail generated by hbm2java
 */

public class BmtbDraftInvDetail  implements java.io.Serializable, Updatable {


    // Fields    

    private Long invoiceDetailNo;
    private String invoiceDetailName;
    private String invoiceDetailType;
    private BigDecimal newTxn;
    private BigDecimal adminFee;
    private BigDecimal gst;
    private BigDecimal totalNew;
    private String cardName;
    private String cardNo;
    private BigDecimal cardCreditLimit;
    private BigDecimal cardReplacement;
    private BigDecimal cardReplacementGst;
    private BigDecimal cardReplacementGstPercent;
    private BigDecimal subscriptionFee;
    private BigDecimal subscriptionFeeGst;
    private BigDecimal subscriptionFeeGstPercent;
    private BigDecimal issuanceFee;
    private BigDecimal issuanceFeeGst;
    private BigDecimal issuanceFeeGstPercent;
    private BigDecimal oriAdminFee;
    private BigDecimal prodDis;
    private BigDecimal promoDis;
    private BigDecimal deductedPromoDis;
	private BigDecimal outstandingAmount;
    private String updatedBy;
    private Timestamp updatedDt;
    private Integer version;
    private Set<BmtbPaymentReceiptDetail> bmtbPaymentReceiptDetails = new HashSet<BmtbPaymentReceiptDetail>(0);
    private PmtbProductType pmtbProductType;
    private FmtbTransactionCode fmtbTransactionCode;
    private AmtbAccount amtbAccount;
    private PmtbProduct pmtbProduct;
    private BmtbDraftInvSummary bmtbDraftInvSummary;
    private Set<BmtbDraftInvTxn> bmtbDraftInvTxns = new HashSet<BmtbDraftInvTxn>(0);

    // Constructors

    /** default constructor */
    public BmtbDraftInvDetail() {
    	newTxn = new BigDecimal("0.00");
    	newTxn = newTxn.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    	adminFee = new BigDecimal("0.00");
    	adminFee = adminFee.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    	gst = new BigDecimal("0.00");
    	gst = gst.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    	totalNew = new BigDecimal("0.00");
    	totalNew = totalNew.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    	outstandingAmount = new BigDecimal("0.00");
    	outstandingAmount = outstandingAmount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    	oriAdminFee = new BigDecimal("0.00");
    	oriAdminFee = oriAdminFee.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    	prodDis = new BigDecimal("0.00");
    	prodDis = prodDis.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    	promoDis = new BigDecimal("0.00");
    	promoDis = promoDis.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    	deductedPromoDis = BigDecimal.ZERO;
    	deductedPromoDis = deductedPromoDis.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    }

	/** minimal constructor */
    public BmtbDraftInvDetail(String invoiceDetailType, BigDecimal newTxn, BigDecimal adminFee, BigDecimal gst, BigDecimal totalNew, BigDecimal oriAdminFee, BigDecimal prodDis, BigDecimal promoDis, BigDecimal deductedPromoDis, BigDecimal outstandingAmount) {
        this.invoiceDetailType = invoiceDetailType;
        this.newTxn = newTxn;
        this.adminFee = adminFee;
        this.gst = gst;
        this.totalNew = totalNew;
        this.oriAdminFee = oriAdminFee;
        this.prodDis = prodDis;
        this.promoDis = promoDis;
        this.deductedPromoDis = deductedPromoDis;
        this.outstandingAmount = outstandingAmount;
    }
    
    /** full constructor */
    public BmtbDraftInvDetail(String invoiceDetailName, String invoiceDetailType, BigDecimal newTxn, BigDecimal adminFee, BigDecimal gst, BigDecimal totalNew, String cardName, String cardNo, BigDecimal cardCreditLimit, BigDecimal cardReplacement, BigDecimal cardReplacementGst, BigDecimal cardReplacementGstPercent, BigDecimal subscriptionFee, BigDecimal subscriptionFeeGst, BigDecimal subscriptionFeeGstPercent, BigDecimal issuanceFee, BigDecimal issuanceFeeGst, BigDecimal issuanceFeeGstPercent, BigDecimal oriAdminFee, BigDecimal prodDis, BigDecimal promoDis, BigDecimal deductedPromoDis, BigDecimal outstandingAmount, String updatedBy, Timestamp updatedDt, Integer version, Set<BmtbPaymentReceiptDetail> bmtbPaymentReceiptDetails, Set<BmtbDraftInvTxn> bmtbDraftInvTxns, PmtbProductType pmtbProductType, BmtbDraftInvSummary bmtbDraftInvSummary, FmtbTransactionCode fmtbTransactionCode, AmtbAccount amtbAccount, PmtbProduct pmtbProduct) {
        this.invoiceDetailName = invoiceDetailName;
        this.invoiceDetailType = invoiceDetailType;
        this.newTxn = newTxn;
        this.adminFee = adminFee;
        this.gst = gst;
        this.totalNew = totalNew;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.cardCreditLimit = cardCreditLimit;
        this.cardReplacement = cardReplacement;
        this.cardReplacementGst = cardReplacementGst;
        this.cardReplacementGstPercent = cardReplacementGstPercent;
        this.subscriptionFee = subscriptionFee;
        this.subscriptionFeeGst = subscriptionFeeGst;
        this.subscriptionFeeGstPercent = subscriptionFeeGstPercent;
        this.issuanceFee = issuanceFee;
        this.issuanceFeeGst = issuanceFeeGst;
        this.issuanceFeeGstPercent = issuanceFeeGstPercent;
        this.oriAdminFee = oriAdminFee;
        this.prodDis = prodDis;
        this.promoDis = promoDis;
        this.deductedPromoDis = deductedPromoDis;
        this.outstandingAmount = outstandingAmount;
        this.updatedBy = updatedBy;
        this.updatedDt = updatedDt;
        this.version = version;
        this.bmtbPaymentReceiptDetails = bmtbPaymentReceiptDetails;
        this.bmtbDraftInvTxns = bmtbDraftInvTxns;
        this.pmtbProductType = pmtbProductType;
        this.bmtbDraftInvSummary = bmtbDraftInvSummary;
        this.fmtbTransactionCode = fmtbTransactionCode;
        this.amtbAccount = amtbAccount;
        this.pmtbProduct = pmtbProduct;
    }
    

   
    // Property accessors

    public Long getInvoiceDetailNo() {
        return this.invoiceDetailNo;
    }
    
    public void setInvoiceDetailNo(Long invoiceDetailNo) {
        this.invoiceDetailNo = invoiceDetailNo;
    }

    public String getInvoiceDetailName() {
        return this.invoiceDetailName;
    }
    
    public void setInvoiceDetailName(String invoiceDetailName) {
        this.invoiceDetailName = invoiceDetailName;
    }

    public String getInvoiceDetailType() {
        return this.invoiceDetailType;
    }
    
    public void setInvoiceDetailType(String invoiceDetailType) {
        this.invoiceDetailType = invoiceDetailType;
    }

    public BigDecimal getNewTxn() {
        return this.newTxn;
    }
    
    public void setNewTxn(BigDecimal newTxn) {
        this.newTxn = newTxn;
    }

    public BigDecimal getAdminFee() {
        return this.adminFee;
    }
    
    public void setAdminFee(BigDecimal adminFee) {
        this.adminFee = adminFee;
    }

    public BigDecimal getGst() {
        return this.gst;
    }
    
    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public BigDecimal getTotalNew() {
        return this.totalNew;
    }
    
    public void setTotalNew(BigDecimal totalNew) {
        this.totalNew = totalNew;
    }

    public String getCardName() {
        return this.cardName;
    }
    
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNo() {
        return this.cardNo;
    }
    
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public BigDecimal getCardCreditLimit() {
        return this.cardCreditLimit;
    }
    
    public void setCardCreditLimit(BigDecimal cardCreditLimit) {
        this.cardCreditLimit = cardCreditLimit;
    }

    public BigDecimal getCardReplacement() {
        return this.cardReplacement;
    }
    
    public void setCardReplacement(BigDecimal cardReplacement) {
        this.cardReplacement = cardReplacement;
    }

    public BigDecimal getCardReplacementGst() {
        return this.cardReplacementGst;
    }
    
    public void setCardReplacementGst(BigDecimal cardReplacementGst) {
        this.cardReplacementGst = cardReplacementGst;
    }

    public BigDecimal getCardReplacementGstPercent() {
        return this.cardReplacementGstPercent;
    }
    
    public void setCardReplacementGstPercent(BigDecimal cardReplacementGstPercent) {
        this.cardReplacementGstPercent = cardReplacementGstPercent;
    }

    public BigDecimal getSubscriptionFee() {
        return this.subscriptionFee;
    }
    
    public void setSubscriptionFee(BigDecimal subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }

    public BigDecimal getSubscriptionFeeGst() {
        return this.subscriptionFeeGst;
    }
    
    public void setSubscriptionFeeGst(BigDecimal subscriptionFeeGst) {
        this.subscriptionFeeGst = subscriptionFeeGst;
    }

    public BigDecimal getSubscriptionFeeGstPercent() {
        return this.subscriptionFeeGstPercent;
    }
    
    public void setSubscriptionFeeGstPercent(BigDecimal subscriptionFeeGstPercent) {
        this.subscriptionFeeGstPercent = subscriptionFeeGstPercent;
    }
    
    public BigDecimal getIssuanceFee() {
        return this.issuanceFee;
    }
    
    public void setIssuanceFee(BigDecimal issuanceFee) {
        this.issuanceFee = issuanceFee;
    }

    public BigDecimal getIssuanceFeeGst() {
        return this.issuanceFeeGst;
    }
    
    public void setIssuanceFeeGst(BigDecimal issuanceFeeGst) {
        this.issuanceFeeGst = issuanceFeeGst;
    }

    public BigDecimal getIssuanceFeeGstPercent() {
        return this.issuanceFeeGstPercent;
    }
    
    public void setIssuanceFeeGstPercent(BigDecimal issuanceFeeGstPercent) {
        this.issuanceFeeGstPercent = issuanceFeeGstPercent;
    }

    public BigDecimal getOriAdminFee() {
        return this.oriAdminFee;
    }
    
    public void setOriAdminFee(BigDecimal oriAdminFee) {
        this.oriAdminFee = oriAdminFee;
    }

    public BigDecimal getProdDis() {
        return this.prodDis;
    }
    
    public void setProdDis(BigDecimal prodDis) {
        this.prodDis = prodDis;
    }

    public BigDecimal getPromoDis() {
        return this.promoDis;
    }
    
    public void setPromoDis(BigDecimal promoDis) {
        this.promoDis = promoDis;
    }
    
    public BigDecimal getDeductedPromoDis() {
		return deductedPromoDis;
	}

	public void setDeductedPromoDis(BigDecimal deductedPromoDis) {
		this.deductedPromoDis = deductedPromoDis;
	}

    public BigDecimal getOutstandingAmount() {
        return this.outstandingAmount;
    }
    
    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDt() {
        return this.updatedDt;
    }
    
    public void setUpdatedDt(Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<BmtbPaymentReceiptDetail> getBmtbPaymentReceiptDetails() {
        return this.bmtbPaymentReceiptDetails;
    }
    
    public void setBmtbPaymentReceiptDetails(Set<BmtbPaymentReceiptDetail> bmtbPaymentReceiptDetails) {
        this.bmtbPaymentReceiptDetails = bmtbPaymentReceiptDetails;
    }

    public Set<BmtbDraftInvTxn> getBmtbDraftInvTxns() {
        return this.bmtbDraftInvTxns;
    }
    
    public void setBmtbDraftInvTxns(Set<BmtbDraftInvTxn> bmtbDraftInvTxns) {
        this.bmtbDraftInvTxns = bmtbDraftInvTxns;
    }

    public PmtbProductType getPmtbProductType() {
        return this.pmtbProductType;
    }
    
    public void setPmtbProductType(PmtbProductType pmtbProductType) {
        this.pmtbProductType = pmtbProductType;
    }

    public BmtbDraftInvSummary getBmtbDraftInvSummary() {
        return this.bmtbDraftInvSummary;
    }
    
    public void setBmtbDraftInvSummary(BmtbDraftInvSummary bmtbDraftInvSummary) {
        this.bmtbDraftInvSummary = bmtbDraftInvSummary;
    }

    public FmtbTransactionCode getFmtbTransactionCode() {
        return this.fmtbTransactionCode;
    }
    
    public void setFmtbTransactionCode(FmtbTransactionCode fmtbTransactionCode) {
        this.fmtbTransactionCode = fmtbTransactionCode;
    }

    public AmtbAccount getAmtbAccount() {
        return this.amtbAccount;
    }
    
    public void setAmtbAccount(AmtbAccount amtbAccount) {
        this.amtbAccount = amtbAccount;
    }

    public PmtbProduct getPmtbProduct() {
        return this.pmtbProduct;
    }
    
    public void setPmtbProduct(PmtbProduct pmtbProduct) {
        this.pmtbProduct = pmtbProduct;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("invoiceDetailName").append("='").append(getInvoiceDetailName()).append("' ");			
      buffer.append("invoiceDetailType").append("='").append(getInvoiceDetailType()).append("' ");			
      buffer.append("newTxn").append("='").append(getNewTxn()).append("' ");			
      buffer.append("adminFee").append("='").append(getAdminFee()).append("' ");			
      buffer.append("gst").append("='").append(getGst()).append("' ");			
      buffer.append("totalNew").append("='").append(getTotalNew()).append("' ");			
      buffer.append("cardName").append("='").append(getCardName()).append("' ");			
      buffer.append("cardNo").append("='").append(getCardNo()).append("' ");			
      buffer.append("cardCreditLimit").append("='").append(getCardCreditLimit()).append("' ");			
      buffer.append("cardReplacement").append("='").append(getCardReplacement()).append("' ");			
      buffer.append("cardReplacementGst").append("='").append(getCardReplacementGst()).append("' ");			
      buffer.append("cardReplacementGstPercent").append("='").append(getCardReplacementGstPercent()).append("' ");			
      buffer.append("subscriptionFee").append("='").append(getSubscriptionFee()).append("' ");			
      buffer.append("subscriptionFeeGst").append("='").append(getSubscriptionFeeGst()).append("' ");			
      buffer.append("subscriptionFeeGstPercent").append("='").append(getSubscriptionFeeGstPercent()).append("' ");			
      buffer.append("issuanceFee").append("='").append(getIssuanceFee()).append("' ");			
      buffer.append("issuanceFeeGst").append("='").append(getIssuanceFeeGst()).append("' ");			
      buffer.append("issuanceFeeGstPercent").append("='").append(getIssuanceFeeGstPercent()).append("' ");
      buffer.append("oriAdminFee").append("='").append(getOriAdminFee()).append("' ");			
      buffer.append("prodDis").append("='").append(getProdDis()).append("' ");			
      buffer.append("promoDis").append("='").append(getPromoDis()).append("' ");
      buffer.append("deductedPromoDis").append("='").append(getDeductedPromoDis()).append("' ");
      buffer.append("outstandingAmount").append("='").append(getOutstandingAmount()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BmtbDraftInvDetail) ) return false;
		 BmtbDraftInvDetail castOther = ( BmtbDraftInvDetail ) other; 
         
		 return ( (this.getInvoiceDetailNo()==castOther.getInvoiceDetailNo()) || ( this.getInvoiceDetailNo()!=null && castOther.getInvoiceDetailNo()!=null && this.getInvoiceDetailNo().equals(castOther.getInvoiceDetailNo()) ) )
 && ( (this.getInvoiceDetailName()==castOther.getInvoiceDetailName()) || ( this.getInvoiceDetailName()!=null && castOther.getInvoiceDetailName()!=null && this.getInvoiceDetailName().equals(castOther.getInvoiceDetailName()) ) )
 && ( (this.getInvoiceDetailType()==castOther.getInvoiceDetailType()) || ( this.getInvoiceDetailType()!=null && castOther.getInvoiceDetailType()!=null && this.getInvoiceDetailType().equals(castOther.getInvoiceDetailType()) ) )
 && ( (this.getNewTxn()==castOther.getNewTxn()) || ( this.getNewTxn()!=null && castOther.getNewTxn()!=null && this.getNewTxn().equals(castOther.getNewTxn()) ) )
 && ( (this.getAdminFee()==castOther.getAdminFee()) || ( this.getAdminFee()!=null && castOther.getAdminFee()!=null && this.getAdminFee().equals(castOther.getAdminFee()) ) )
 && ( (this.getGst()==castOther.getGst()) || ( this.getGst()!=null && castOther.getGst()!=null && this.getGst().equals(castOther.getGst()) ) )
 && ( (this.getTotalNew()==castOther.getTotalNew()) || ( this.getTotalNew()!=null && castOther.getTotalNew()!=null && this.getTotalNew().equals(castOther.getTotalNew()) ) )
 && ( (this.getCardName()==castOther.getCardName()) || ( this.getCardName()!=null && castOther.getCardName()!=null && this.getCardName().equals(castOther.getCardName()) ) )
 && ( (this.getCardNo()==castOther.getCardNo()) || ( this.getCardNo()!=null && castOther.getCardNo()!=null && this.getCardNo().equals(castOther.getCardNo()) ) )
 && ( (this.getCardCreditLimit()==castOther.getCardCreditLimit()) || ( this.getCardCreditLimit()!=null && castOther.getCardCreditLimit()!=null && this.getCardCreditLimit().equals(castOther.getCardCreditLimit()) ) )
 && ( (this.getCardReplacement()==castOther.getCardReplacement()) || ( this.getCardReplacement()!=null && castOther.getCardReplacement()!=null && this.getCardReplacement().equals(castOther.getCardReplacement()) ) )
 && ( (this.getCardReplacementGst()==castOther.getCardReplacementGst()) || ( this.getCardReplacementGst()!=null && castOther.getCardReplacementGst()!=null && this.getCardReplacementGst().equals(castOther.getCardReplacementGst()) ) )
 && ( (this.getCardReplacementGstPercent()==castOther.getCardReplacementGstPercent()) || ( this.getCardReplacementGstPercent()!=null && castOther.getCardReplacementGstPercent()!=null && this.getCardReplacementGstPercent().equals(castOther.getCardReplacementGstPercent()) ) )
 && ( (this.getSubscriptionFee()==castOther.getSubscriptionFee()) || ( this.getSubscriptionFee()!=null && castOther.getSubscriptionFee()!=null && this.getSubscriptionFee().equals(castOther.getSubscriptionFee()) ) )
 && ( (this.getSubscriptionFeeGst()==castOther.getSubscriptionFeeGst()) || ( this.getSubscriptionFeeGst()!=null && castOther.getSubscriptionFeeGst()!=null && this.getSubscriptionFeeGst().equals(castOther.getSubscriptionFeeGst()) ) )
 && ( (this.getSubscriptionFeeGstPercent()==castOther.getSubscriptionFeeGstPercent()) || ( this.getSubscriptionFeeGstPercent()!=null && castOther.getSubscriptionFeeGstPercent()!=null && this.getSubscriptionFeeGstPercent().equals(castOther.getSubscriptionFeeGstPercent()) ) )
 && ( (this.getIssuanceFee()==castOther.getIssuanceFee()) || ( this.getIssuanceFee()!=null && castOther.getIssuanceFee()!=null && this.getIssuanceFee().equals(castOther.getIssuanceFee()) ) )
 && ( (this.getIssuanceFeeGst()==castOther.getIssuanceFeeGst()) || ( this.getIssuanceFeeGst()!=null && castOther.getIssuanceFeeGst()!=null && this.getIssuanceFeeGst().equals(castOther.getIssuanceFeeGst()) ) )
 && ( (this.getIssuanceFeeGstPercent()==castOther.getIssuanceFeeGstPercent()) || ( this.getIssuanceFeeGstPercent()!=null && castOther.getIssuanceFeeGstPercent()!=null && this.getIssuanceFeeGstPercent().equals(castOther.getIssuanceFeeGstPercent()) ) )
 && ( (this.getOriAdminFee()==castOther.getOriAdminFee()) || ( this.getOriAdminFee()!=null && castOther.getOriAdminFee()!=null && this.getOriAdminFee().equals(castOther.getOriAdminFee()) ) )
 && ( (this.getProdDis()==castOther.getProdDis()) || ( this.getProdDis()!=null && castOther.getProdDis()!=null && this.getProdDis().equals(castOther.getProdDis()) ) )
 && ( (this.getPromoDis()==castOther.getPromoDis()) || ( this.getPromoDis()!=null && castOther.getPromoDis()!=null && this.getPromoDis().equals(castOther.getPromoDis()) ) )
 && ( (this.getDeductedPromoDis()==castOther.getDeductedPromoDis()) || ( this.getDeductedPromoDis()!=null && castOther.getDeductedPromoDis()!=null && this.getDeductedPromoDis().equals(castOther.getDeductedPromoDis()) ) )
 && ( (this.getOutstandingAmount()==castOther.getOutstandingAmount()) || ( this.getOutstandingAmount()!=null && castOther.getOutstandingAmount()!=null && this.getOutstandingAmount().equals(castOther.getOutstandingAmount()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getPmtbProductType()==castOther.getPmtbProductType()) || ( this.getPmtbProductType()!=null && castOther.getPmtbProductType()!=null && this.getPmtbProductType().equals(castOther.getPmtbProductType()) ) )
 && ( (this.getFmtbTransactionCode()==castOther.getFmtbTransactionCode()) || ( this.getFmtbTransactionCode()!=null && castOther.getFmtbTransactionCode()!=null && this.getFmtbTransactionCode().equals(castOther.getFmtbTransactionCode()) ) )
 && ( (this.getAmtbAccount()==castOther.getAmtbAccount()) || ( this.getAmtbAccount()!=null && castOther.getAmtbAccount()!=null && this.getAmtbAccount().equals(castOther.getAmtbAccount()) ) )
 && ( (this.getPmtbProduct()==castOther.getPmtbProduct()) || ( this.getPmtbProduct()!=null && castOther.getPmtbProduct()!=null && this.getPmtbProduct().equals(castOther.getPmtbProduct()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getInvoiceDetailNo() == null ? 0 : this.getInvoiceDetailNo().hashCode() );
         result = 37 * result + ( getInvoiceDetailName() == null ? 0 : this.getInvoiceDetailName().hashCode() );
         result = 37 * result + ( getInvoiceDetailType() == null ? 0 : this.getInvoiceDetailType().hashCode() );
         result = 37 * result + ( getNewTxn() == null ? 0 : this.getNewTxn().hashCode() );
         result = 37 * result + ( getAdminFee() == null ? 0 : this.getAdminFee().hashCode() );
         result = 37 * result + ( getGst() == null ? 0 : this.getGst().hashCode() );
         result = 37 * result + ( getTotalNew() == null ? 0 : this.getTotalNew().hashCode() );
         result = 37 * result + ( getCardName() == null ? 0 : this.getCardName().hashCode() );
         result = 37 * result + ( getCardNo() == null ? 0 : this.getCardNo().hashCode() );
         result = 37 * result + ( getCardCreditLimit() == null ? 0 : this.getCardCreditLimit().hashCode() );
         result = 37 * result + ( getCardReplacement() == null ? 0 : this.getCardReplacement().hashCode() );
         result = 37 * result + ( getCardReplacementGst() == null ? 0 : this.getCardReplacementGst().hashCode() );
         result = 37 * result + ( getCardReplacementGstPercent() == null ? 0 : this.getCardReplacementGstPercent().hashCode() );
         result = 37 * result + ( getSubscriptionFee() == null ? 0 : this.getSubscriptionFee().hashCode() );
         result = 37 * result + ( getSubscriptionFeeGst() == null ? 0 : this.getSubscriptionFeeGst().hashCode() );
         result = 37 * result + ( getSubscriptionFeeGstPercent() == null ? 0 : this.getSubscriptionFeeGstPercent().hashCode() );
         result = 37 * result + ( getIssuanceFee() == null ? 0 : this.getIssuanceFee().hashCode() );
         result = 37 * result + ( getIssuanceFeeGst() == null ? 0 : this.getIssuanceFeeGst().hashCode() );
         result = 37 * result + ( getIssuanceFeeGstPercent() == null ? 0 : this.getIssuanceFeeGstPercent().hashCode() );
         result = 37 * result + ( getOriAdminFee() == null ? 0 : this.getOriAdminFee().hashCode() );
         result = 37 * result + ( getProdDis() == null ? 0 : this.getProdDis().hashCode() );
         result = 37 * result + ( getPromoDis() == null ? 0 : this.getPromoDis().hashCode() );
         result = 37 * result + ( getDeductedPromoDis() == null ? 0 : this.getDeductedPromoDis().hashCode() );
         result = 37 * result + ( getOutstandingAmount() == null ? 0 : this.getOutstandingAmount().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getPmtbProductType() == null ? 0 : this.getPmtbProductType().hashCode() );
         result = 37 * result + ( getFmtbTransactionCode() == null ? 0 : this.getFmtbTransactionCode().hashCode() );
         result = 37 * result + ( getAmtbAccount() == null ? 0 : this.getAmtbAccount().hashCode() );
         result = 37 * result + ( getPmtbProduct() == null ? 0 : this.getPmtbProduct().hashCode() );
         return result;
   }   
   
 
   public static BmtbDraftInvDetail copy(BmtbInvoiceDetail t, BmtbDraftInvSummary summary) {
		
	   	BmtbDraftInvDetail o = new BmtbDraftInvDetail();
		o.setInvoiceDetailName(t.getInvoiceDetailName());
		o.setInvoiceDetailType(t.getInvoiceDetailType());
		o.setNewTxn(t.getNewTxn());
		o.setAdminFee(t.getAdminFee());
		o.setGst(t.getGst());
		o.setTotalNew(t.getTotalNew());
		o.setCardName(t.getCardName());
		o.setCardNo(t.getCardNo());
		o.setCardCreditLimit(t.getCardCreditLimit());
		o.setCardReplacement(t.getCardReplacement());
		o.setCardReplacementGst(t.getCardReplacementGst());
		o.setCardReplacementGstPercent(t.getCardReplacementGstPercent());
		o.setSubscriptionFee(t.getSubscriptionFee());
		o.setSubscriptionFeeGst(t.getSubscriptionFeeGst());
		o.setSubscriptionFeeGstPercent(t.getSubscriptionFeeGstPercent());
		o.setIssuanceFee(t.getIssuanceFee());
		o.setIssuanceFeeGst(t.getIssuanceFeeGst());
		o.setIssuanceFeeGstPercent(t.getIssuanceFeeGstPercent());
		o.setOriAdminFee(t.getOriAdminFee());
		o.setProdDis(t.getProdDis());
		o.setPromoDis(t.getPromoDis());
		o.setDeductedPromoDis(t.getDeductedPromoDis());
		o.setOutstandingAmount(t.getOutstandingAmount());
		o.setUpdatedBy(t.getUpdatedBy());
		o.setUpdatedDt(t.getUpdatedDt());
		o.setPmtbProductType(t.getPmtbProductType());
		o.setFmtbTransactionCode(t.getFmtbTransactionCode());
		o.setAmtbAccount(t.getAmtbAccount());
		o.setPmtbProduct(t.getPmtbProduct());
		o.setBmtbDraftInvSummary(summary);
		   
		return o;
		
	}
}
