package com.cdgtaxi.ibs.prepaid.business;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.PmtbAdjustmentReq;
import com.cdgtaxi.ibs.common.model.PmtbExtBalExpDateReq;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidCardTxn;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTransferReq;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidProductForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidRequestForm;
import com.cdgtaxi.ibs.common.model.forms.TransferableAmtInfo;
import com.cdgtaxi.ibs.interfaces.cdge.exception.ExpectedException;
import com.elixirtech.net.NetException;

public interface PrepaidBusiness extends GenericBusiness {
	
	public void createIssuanceRequest(PmtbIssuanceReq request) throws NetException, IOException;
	
	public void createTopUpRequest(PmtbTopUpReq request) throws NetException, IOException, ExpectedException;
	
	public void createTopUpCreditRequest(PmtbTopUpReq request, String link) throws NetException, IOException, ExpectedException;
	
	public void recreateTopUpCreditRequest(PmtbTopUpReq request, String link) throws NetException, IOException, ExpectedException;
	
	public void createTransferBalanceRequest(PmtbTransferReq request);
	
	public void createExtendBalanceExpiryDateRequest(PmtbExtBalExpDateReq request);
	
	public void createAdjustmentRequest(PmtbAdjustmentReq request);
	
	public List<PmtbPrepaidReq> searchPrepaidRequest(SearchPrepaidRequestForm form);
	
	public List<Object[]> searchPrepaidCreditInvoiceRequest(SearchPrepaidRequestForm form);
	
	public List<PmtbProduct> searchPrepaidProducts(SearchPrepaidProductForm form);
	
	public List<PmtbProduct> getTopUpableProducts(Integer accountNo,  String productTypeId, String cardNo,String cardName);
	
	public List<PmtbProduct> getProductsForTransferReq(List<BigDecimal> productNoList);
	
	public PmtbPrepaidReq getPrepaidRequest(BigDecimal requestNo);

	public PmtbIssuanceReq getPrepaidIssuanceRequest(BigDecimal requestNo);

	public PmtbTopUpReq getPrepaidTopUpRequest(BigDecimal requestNo);
	
	public PmtbTopUpReq getPrepaidCreditTopUpRequest(BigDecimal requestNo);
	
	public PmtbTransferReq getPrepaidTransferRequest(BigDecimal requestNo);
	
	public PmtbProduct getPrepaidProduct(BigDecimal productNo);
	
	public void approveIssuanceRequest(PmtbIssuanceReq req, String user) throws NetException, IOException;
	
	public void approveTopUpRequest(PmtbTopUpReq req, String user) throws NetException, IOException, ExpectedException;
	
	public void approveTopUpCreditRequest(PmtbTopUpReq req, String user) throws NetException, IOException, ExpectedException;
	
	public void approveTransferRequest(PmtbTransferReq req, String user);
	
	public void approveExtendBalanceExpiryDateRequest(PmtbExtBalExpDateReq req, String user);
	
	public void approveAdjustmentRequest(PmtbAdjustmentReq req, String user);
	
	public void issueProduct(PmtbIssuanceReq req, boolean withPayment, String user);
	
	public void topUp(PmtbTopUpReq req, boolean withPayment, String user);
	
	public TransferableAmtInfo calculateTransferableValueAndCashPlus(PmtbProduct product);
	
	public Object createIssuanceInvoice(PmtbIssuanceReq req, String user, boolean isDraft);
	
	public Object createTopUpInvoice(PmtbTopUpReq req, String user, boolean isDraft);
	
	
	public void createPrepaidDirectReceipt(PmtbPrepaidCardTxn txn, String user);
	
	public void cancelPrepaidInvoice(BmtbInvoiceHeader header, String user);
	
	public void commonApprove(PmtbPrepaidReq req, String user) throws NetException, IOException, ExpectedException;
	
	public void commonReject(PmtbPrepaidReq req, String user) ;
	
	public void generatePrepaidInvoiceFileForEligibleRequest(PmtbPrepaidReq req) throws NetException, IOException;
	
	public BmtbInvoiceHeader getPrepaidInvoiceHeader(long invoiceHeaderNo);
	
	public PmtbPrepaidReq getPrepaidRequestWithInvoiceHeader(Long invoiceHeaderNo);
	
	public BigDecimal getLatestGST(Integer entityNo, String productTypeId, Timestamp tripDt, String txnType);

	public String reddotSendEmail(String reddotInvoiceNo) throws IOException;
}
