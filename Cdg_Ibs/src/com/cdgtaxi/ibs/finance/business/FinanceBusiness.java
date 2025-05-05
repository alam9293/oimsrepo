
package com.cdgtaxi.ibs.finance.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

public interface FinanceBusiness extends GenericBusiness {
	List<FmtbTransactionCode> getEffectiveManualTxnCodes(int entityNo);
	public BigDecimal getLatestGST(Integer entityNo, Timestamp tripDt, String txnType);

	FmtbTaxCode getEffectiveTaxCode(int entityNo, Integer taxType);
	public FmtbTransactionCode getFmtbTransactionCode(PmtbProductType productType, FmtbEntityMaster entity);
	
	public BigDecimal getLatestGST(Integer entityNo, String productTypeId, Timestamp tripDt, String txnType);

}