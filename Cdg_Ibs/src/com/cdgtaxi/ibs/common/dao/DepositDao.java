package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;

import com.cdgtaxi.ibs.common.model.AmtbAccount;

public interface DepositDao extends GenericDao {
	public BigDecimal getTotalRequestTxnAmount(AmtbAccount account);
	public BigDecimal getTotalRefundTxnAmount(AmtbAccount account);
}