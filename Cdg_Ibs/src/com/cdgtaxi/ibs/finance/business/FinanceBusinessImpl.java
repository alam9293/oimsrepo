
package com.cdgtaxi.ibs.finance.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

public class FinanceBusinessImpl extends GenericBusinessImpl implements FinanceBusiness {
	public List<FmtbTransactionCode> getEffectiveManualTxnCodes(int entityNo) {
		return daoHelper.getFinanceDao().getEffectiveManualTxnCodes(entityNo);
	}

	public FmtbTaxCode getEffectiveTaxCode(int entityNo, Integer taxType) {
		return daoHelper.getFinanceDao().getEffectiveTxnCode(entityNo, taxType);
	}
	
	public FmtbTransactionCode getFmtbTransactionCode(PmtbProductType productType, FmtbEntityMaster entity)
	{
		return daoHelper.getFinanceDao().getFmtbTransactionCode(productType, entity);
	}
	
	public BigDecimal getLatestGST(Integer entityNo, Timestamp tripDt, String txnCode)
	{
		return daoHelper.getFinanceDao().getLatestGST(entityNo, tripDt, txnCode);
	}
	
	public BigDecimal getLatestGST(Integer entityNo, String productTypeId, Timestamp tripDt, String txnType) {
		
		return daoHelper.getFinanceDao().getLatestGST(entityNo, productTypeId, tripDt, txnType);
	}
}
