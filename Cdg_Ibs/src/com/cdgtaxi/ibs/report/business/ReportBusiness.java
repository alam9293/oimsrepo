package com.cdgtaxi.ibs.report.business;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.elixirtech.net.NetException;

public interface ReportBusiness extends GenericBusiness {
	public void setReportProperties(Map<String, String> reportProperties);
	public List<SatbResource> searchReportCategories(String userLoginId);
	public List<SatbResource> searchReports(SatbResource reportCategory, String userLoginId);
	public Map<String, String> getAllProductTypes();
	public Map<Integer, String> getAllItemTypes();
	public Map<Integer, String> getAllCreditTrems();
	public Set<String> getAllRedeemPoint();
	public boolean checkProductTypeByID(String productTypeID);
	public List<Map<String, String>> getEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd, String sortBy, boolean isReprint, String userId);
	public List<Map<String, String>> getContactlessEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd, String sortBy, boolean isReprint, String userId);
	public byte[] generate(String report, String reportCategory, String format, Properties params) throws NetException, IOException;
	public Map<String, String> searchAccount(String custNo, String custName);
	public Map<String, String> searchChildrenAccount(String custNo, boolean isBillable);
	public Map<String, String> searchChildrenAccount(String custNo, String parentCode, boolean isBillable);
	public Map<String, String> getIssuableProductTypes();
	public boolean hasEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd);
	public List<AmtbAccount> searchTopLevelAccount(String custNo, String custName);
	public List<AmtbAccount> findAccDeatil(String custNo, String custName);
	public List<FmtbArContCodeMaster> searchArContCode(Integer entityNo);
	public List<Object[]> getCustomerAgingDetail(String accountNo, String accountName, String entity,
			String arControl, String salesPerson, String outstandingAmount, String daysLate, String type, 
			String sortBy);
	public List<Object[]> getCustomerAgingSummary(String accountNo, String accountName, String entityNo,
			String arControlCodeNo, String outstandingAmount, String type, String sortBy);
	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByInvoiceNoCustomize(String accountNo, 
			String invoiceMonthYear, String invoiceNo, String sortBy);
	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByInvoiceNoCSV(String accountNo, 
			String invoiceMonthYear, String invoiceNo, String chargeTo, String sortBy);
	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByTransactionStatus(String accountNo, 
			String accountName, String cardNo, String txnStatus, String productTypeId,
			String tripStartDate, String tripEndDate, String sortBy);
	public List<MstbMasterTable> getASCTDRConfig();
	public List<MstbReportFormatMap> getReportFormatMap(Long resourceId);
	public Map<String, String> getAccountContacts(String custNo);
	public List<Object[]> getCustomerReport(String acctTypeNo, String acctNo, String acctName, String acctStatus, String productTypeId, String industryNo, String joinStart, String joinEnd, String terminateStart, String terminateEnd, String salespersonNo, String sortBy, String entityNo);
	public List<Object[]> getReceiptByPeriodDetailed(String receiptStart, String receiptEnd, String cancelStart, String cancelEnd, String acctNo, String acctName, String invoiceNo, String receiptNo, String paymentMode, String order, String entityNo, String salespersonNo);
	public List<Object[]> getReceiptByPeriodSummaryDate(String receiptStart, String receiptEnd, String paymentMode, String entityNo, String salespersonNo);
	public List<Object[]> getReceiptByPeriodSummaryPayment(String receiptStart, String receiptEnd, String paymentMode, String entityNo, String salespersonNo);
	public List<Object[]> getCreditDebitNote(String noteStart, String noteEnd, String cancelStart, String cancelEnd, String acctNo, String acctName, String noteType, String order, String entityNo);
	public Map<String, String> getIssuableNonOTUProductTypes();
	public List<Object[]> getCreditBalance(String creditBalance, String acctNo, String acctName, String divNo, String deptNo, String productType, String salespersonNo, String sort);
	public List<Object[]> getCardInProduction(String productType, String issueStart, String issueEnd, String renewStart, String renewEnd, String replaceStart, String replaceEnd, String cardStart, String cardEnd, String cardStatus, String sortBy);
	public List<Object[]> getCustomerDepositSummary(String acctNo, String acctName, String entity);
	public List<Object[]> getCustomerDepositDetailedReceipts(String depositStart, String depositEnd, String receiptStart, String receiptEnd, String acctNo, String acctName, String entity, String depositStatus, String sort);
	public List<Object[]> getCustomerDepositDetailedRefunds(String depositStart, String depositEnd, String receiptStart, String receiptEnd, String acctNo, String acctName, String entity);
	public List<Object[]> getDailyChequeDepositListing(String receivedStartDate, String receivedEndDate, String entity, String quickCheckDeposit, String order);
	public List<Object[]> getCustomerUsage(String accountNo, String accountName, String invoiceStartMonth, String invoiceEndMonth, String productType, String entityNo);
	public List<Object[]> getCustomerUsageCardLevel(String accountNo, String accountName, String invoiceStartMonth, String invoiceEndMonth, String productType, String entityNo, String expiryDT, String cardNo);
	public List<Object[]> getCustomerUsageComparsion(String invoiceMonth, String productType, String sort, String entityNo);
	public List<Object[]> getRevenueSummary(String entityNo, String invoiceStart, String invoiceEnd);
	public List<Object[]> getRevenueMisc(String entityNo, String invoiceStart, String invoiceEnd);
	public Map<String, String> getRevenueHeaderForMisc();
	public List<Object[]> getRevenueHeader();
	public List<String> getRevenueHeaderForProductTypeId(String entityNo, String invoiceStart, String invoiceEnd);
	public Map<String, Object[]> getRevenueProductTypeTripInfos(String invoiceHeaderNo, String invoiceStart, String invoiceEnd);
	public Map<String, Object[]> getRevenueProductTypePrepaidInfos(String invoiceHeaderNo, String invoiceStart, String invoiceEnd);
	public List<Object[]> getSalesReportBySalesperson(String invoiceStart, String invoiceEnd, String productType, String sort, String entityNo);
	public Map<Integer, String> getAllAcquirers();
	public List<Object[]> getBankChargebackReport(String chargebackStart, String chargebackEnd, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo, String sort);
	public List<Object[]> getCashlessAgingReportDetailed(String agingDate, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo, String sort);
	public List<Object[]> getCashlessAgingReportSummary(String agingDate, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo);
	public List<Object[]> getCashlessBankCollectionSummary(String batchNo, String creditStart, String creditEnd, String batchStart, String batchEnd, String entityNo, String acquirerNo, String paymentTypeNo, String providerNo);
	public List<Object[]> getCashlessBankCollectionDetailed(String batchNo, String creditStart, String creditEnd, String batchStart, String batchEnd, String entityNo, String acquirerNo, String paymentTypeNo, String providerNo, String taxiNo, String driverIc, String txnStatus, String sort);
	public List<Object[]> getCashlessTxnByAmtRange(String tripStart, String tripEnd, String paymentTypeNo, String entityNo);
	public List<Object[]> getCashlessCollectionStatisticsSummary(String creditStart, String creditEnd, String entityNo, String acquirerNo);
	public List<Object[]> getCashlessCollectionStatisticsDetailed(String creditStart, String creditEnd, String entityNo, String acquirerNo);
	public List<Object[]> getInvoiceReport(String entity, String invoiceStart, String invoiceEnd, String invoiceType, String eInvoice, String salesPerson, String sort);
	public List<Object[]> getRevenueByProductCardType(String revenueStart, String revenueEnd, String entity, String provider, String productType, String paymentType);
	public List<Object[]> getRevenueByProductCardType2(String revenueStart, String revenueEnd, String entity, String provider, String productType, String paymentType);
	public List<Object[]> getCreditCardPromoDetails(String batchStart, String batchEnd, String tripStart, String tripEnd, String paymentTypeNo, String binStart, String binEnd, String promo, String txnStatus, String batchStatus, String sort);
	public List<Object[]> getInventoryMovementReportDetailed(String stockStart, String stockEnd, String itemTypeNo, String entityNo);
	public List<Object[]> getPrepaidProduct(String custNo, String accountName, String productTypeId, 
			String expiryDateFrom, String expiryDateTo, String cardStatus, String cardNoFrom, String cardNoTo,
			String accountTypeNo, String accountStatus, String entityNo);
	public List<Object[]> getTripReconciliationReport(String entity,String accountNo,String accountName,String division,String department,String productType,String startDate,String endDate,String uploadStartDate,String uploadEndDate,String companyCode,String txnStatus,String sortBy);
	public List<Object[]> getContactPerson(String accountNo,String accountName,String division,String department,String accountStatus,String contactPersonName,String typeOfContact,String businessNature,String productType,String salesPerson,String sortBy, String entityNo);
	public List<Object[]> getCorporateCustomerBreakdownUsage(String invoiceMonth,String productType,String accountStatus,String businessNature,String salesPerson,String numberOfRecords,String sortBy, String entityNo);
	
	public List<Object[]> getCardStatisticReport(String productStatusDate, String issueStart, String issueEnd, String terminateStart, String terminateEnd, String suspendStart, String suspendEnd, String replaceStart, String replaceEnd, String productType, String productStatus, String acctStatus);
	public List<Object[]> getCardStatisticReportSummary(String productStatusDate, String issueStart, String issueEnd, String terminateStart, String terminateEnd, String suspendStart, String suspendEnd, String replaceStart, String replaceEnd, String productType, String productStatus, String acctStatus);
	public Map<Integer, String> getAllAccountTypes();
	public Map<String, String> getPrepaidProductTypes();
	public Map<String, String> getBinRangeProductTypes();
	public List<Object[]> getNewAccountsRevenue(String acctTypeNo, String joinedStart, String joinedEnd, String usageYear, String entityNo, String acctStatus, String industryCode, String salespersonNo);
	public List<Object[]> getRefundDepositReport(String acctNo, String acctName, String acctStatus, String terminateStart, String terminateEnd, String refund, String entityNo);
	public List<Object[]> getFinancialMemoReport(String acctNo, String acctName, String memoStart, String memoEnd, String memoNo, String invoiceNo, String receiptNo, String memoType, String sort, String entityNo);
	public List<Object[]> getItemTypeRevenueProfitReport(String startMonth, String endMonth, String itemTypeNo, String entityNo);
	public List<Object[]> getMonthlyDebtManagementDebt(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo);
	public List<Object[]> getMonthlyDebtManagementReceived(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo);
	public List<Object[]> getMonthlyDebtManagementClosing(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo);
	public List<Object[]> getTimelyPaymentStatisticsDetailed(String mthYear, String entityNo, String creditTerm, String type);
	public List<Object[]> getMovementReport(String entityNo, String productItemType, String acctNo, String acctName, String acctStatus, String productStatus, String mthYear, String type);
	public List<Object[]> getLoyaltyProgramReport(String acctTypeNo, String acctNo, String acctName, String acctStatus, String productTypeId, String pointStart, String pointEnd, String salespersonNo, String sort, String entityNo);
	public List<Object[]> getTripAdjustmentReport(String acctNo, String acctName, String paymentType, String createStart, String createEnd, String approveStart, String approveEnd, String entityNo, String providerNo, String approvalStatus, String txnStatus, String action, String fmsUpdate, String sort);
	public List<Object[]> getGiroSummary(String valueDateFrom, String valueDateTo, String generationDateFrom,
			String generationDateTo, String uploadDateFrom, String uploadDateTo, String entityNo);
	public List<Object[]> getGiroFile(String custNo, String divAcct, String deptAcct, String entityNo, String cutOffDate);
	public List<Object[]> getGiroReject(String valueDateFrom, String valueDateTo, String acctTypeNo,
			String salesPersonNo, String rejectedCode, String rejectedBy, String entityNo);
	public List<Object[]> getErrorTxnReport(String txnStart, String txnEnd, String uploadStart, String uploadEnd, String errorMsg, String offline, String jobNo, String cardNo, String nric, String taxiNo, String sort);
	public List<String> getAllTxnErrorMsg();
	/**
	 * Added by CDG.
	 * @return
	 */
	public Map<Long, String> getAllRoles(); //added vani 28.10.10 for UAM Report
	public List<Object[]> getASOfflineApproval(String txnStart, String txnEnd,String offlineTxnStart,String offlineTxnEnd,String messageType,String error,String cardNo,String sortBy); //added jtaruc 16/03/11 for Offline Approval Report
	/**
	 * Added end
	 */
	
	public List<ImtbItem> getUnredeemedVoucher(java.sql.Date issuedDateFrom,
			java.sql.Date issuedDateTo, java.sql.Date expiryDateFrom, java.sql.Date expiryDateTo,
			Integer itemTypeNo, String status, java.sql.Date batchDateAsAt,  java.sql.Date redeemDateAsAt, Integer entityNo);
	
	
	public List<ImtbItem> getRedeemedVoucher(BigDecimal serialNoStart, BigDecimal serialNoEnd, Integer itemType, 
			java.sql.Date batchDateFrom, java.sql.Date batchDateTo, java.sql.Date redeemDateFrom,java.sql.Date redeemDateTo, int accountNo, Integer entity);
	
	public List<Object[]> suspensionReactivationVoucher(String actionStartDate,
			String actionEndDate, String approvalStartDate, String approvalEndDate,
			String serialNoStart, String serialNoEnd, String itemTypeNo, String actionType,
			String approvalStatus, String entityNo) ;
	
	public List<Object[]> adjustVoucherRedemption(String batchNo, String actionStartDate,
			String actionEndDate, String approvalStartDate, String approvalEndDate,
			String serialNoStart, String serialNoEnd, String itemTypeNo, String actionType,
			String approvalStatus, String entityNo) ;
	
	/**
	 * Retrieve division or department accounts that subscribed to Govt eInvoice that belongs to a particular
	 * top level account
	 * 
	 * @param topLevelAccount
	 * @return
	 */
	public List<AmtbAccount> getGovtEInvChildrenAccounts(AmtbAccount topLevelAccount);
	
	public List<Object[]> getGovtEInv(String accountNo, String accountName, String subAccountNo,
			String entityNo, String businessUnit, String govtEInvFlag, String returnStatus, String requestNo,
			String requestDateFrom, String requestDateTo, String invoiceDateFrom, String invoiceDateTo);
	
	public List<AmtbAccount> getPubbsChildrenAccounts(AmtbAccount topLevelAccount);
	
	public List<Object[]> getPubbs(String accountNo, String accountName, String subAccountNo,
			String entityNo, String pubbsFlag, String returnStatus, String requestNo,
			String requestDateFrom, String requestDateTo, String invoiceDateFrom, String invoiceDateTo);
	
	public byte[] generatePrepaidInvoice(AmtbAccount account, String invoiceHeaderNo, boolean isDraft) throws NetException, IOException;
	
	public List<Object[]> getApprovalRequestRecords(String acctType, String acctNo, String acctName, String cardNo,
			String requestStartDate, String requestEndDate, String approvalStartDate, String approvalEndDate, String actionType, String approvalStatus, String entity, String sortBy);
	
	public List<Object[]> getTopUpRecords(String acctType, String acctNo, String acctName, String cardNo,
			String cardStatus, String mobileNo, String productTypeId, String topUpType, String promoCode, 
			String topUpStartDate, String topUpEndDate, String minTopUp, String entityNo, String sortBy);
	
	
	public List<Object[]> getPrepaidUsageRecords(String acctType, String acctNo, String acctName, String acctStatus, String subscribeProductTypeId, String joinStartDate, String joinEndDate, String tripStartDate, String tripEndDate,
			  String entityNo, String sortBy);
	
	public List<Object[]> getPrepaidUsageDetailRecords(String acctType, String acctNo, String acctName, String cardNo, String cardStatus, String productTypeId, String issueStartDate, String issueEndDate, String tripStartDate, String tripEndDate,
			  String entityNo, String sortBy);
	
	public List<Object[]> getPrepaidCardTransactionRecords(String cardNo);
	
	public Map<String, String> getAllPromotionCashPlusCodes();
	
	public List<Object[]> getEmailAudit(String dateFrom, String dateTo);
	
	public List<Object[]> getBirthdayAnnouncement(String birthdayDateFrom, String birthdayDateTo, String joinDateFrom, String joinDateTo, String accountStatus);

	public List<Object[]> getAydenPaymentMatchingSummaryReport(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2);

	public List<Object[]> getAydenPaymentMatchingSummaryReport2(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2);

	public List<Object[]> getAydenPaymentMatchingExcessAmount(String settlementStartDate, String settlementEndDate, String batchNo);

	public List<TmtbNonBillableTxnCrca> getAydenPaymentMatchingBreakdownReport(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2);
	
	public List<AmtbAcctStatus> getAccountstsAllbyAccnoandAccName(String accountNo, String custName);
	
}