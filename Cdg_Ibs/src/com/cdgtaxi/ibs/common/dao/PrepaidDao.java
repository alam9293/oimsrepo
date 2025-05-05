package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidCardTxn;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTransferReq;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidProductForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidRequestForm;


public interface PrepaidDao extends GenericDao {
	
	
	public List<PmtbPrepaidReq> searchPrepaidRequest(SearchPrepaidRequestForm form);
	
	public List<Object[]> searchPrepaidInvoiceRequest(SearchPrepaidRequestForm form);
	
	public List<PmtbProduct> searchPrepaidProducts(SearchPrepaidProductForm form);
	
	
	public List<PmtbProduct> getTopUpableProducts(Integer accountNo, String productTypeId, String cardNo,String cardName);
	
	public List<PmtbProduct> getProductsForTransferReq(List<BigDecimal> productNoList);
	
	
	public PmtbPrepaidReq getPrepaidRequest(BigDecimal requestNo);
	
	public PmtbIssuanceReq getPrepaidIssuanceRequest(BigDecimal requestNo);
	
	public PmtbTopUpReq getPrepaidTopUpRequest(BigDecimal requestNo);
	
	public PmtbTopUpReq getPrepaidCreditTopUpRequest(BigDecimal requestNo);
	
	public PmtbTransferReq getPrepaidTransferRequest(BigDecimal requestNo);
	
	public PmtbProduct getPrepaidProduct(BigDecimal productNo);
	
	public PmtbPrepaidReq getPrepaidRequestWithInvoiceHeader(Long invoiceHeaderNo);
	
	public List<PmtbPrepaidCardTxn> getPrepaidCardTxnsByAcquireTxnNo(Integer acquireTxnNo);
	
	public Object[] getLastBalanceForfeitureAsAt(BigDecimal productNo, Date asAtDate);
	
	public BmtbInvoiceHeader getPrepaidInvoiceHeader(long invoiceHeaderNo);
}
