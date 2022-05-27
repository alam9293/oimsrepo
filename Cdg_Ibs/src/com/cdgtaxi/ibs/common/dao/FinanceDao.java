package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

public interface FinanceDao extends GenericDao {
	List<FmtbTransactionCode> getEffectiveManualTxnCodes(int entityNo);

	FmtbTaxCode getEffectiveTxnCode(int entityNo, Integer taxType);
	public FmtbTransactionCode getFmtbTransactionCode(PmtbProductType productType, FmtbEntityMaster entity);
	// To get latest GST value
	public BigDecimal getLatestGST(Integer entityNo, String productTypeId, Timestamp tripDt );
	public BigDecimal getLatestGST(Integer entityNo, Timestamp tripDt, String txnType);
	
	public BigDecimal getLatestGST(Integer entityNo, String productTypeId, Timestamp tripDt, String txnType);
}
