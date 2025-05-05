package com.cdgtaxi.ibs.common.dao;

import com.cdgtaxi.ibs.interfaces.as.model.AsvwProduct;

public interface EnquiryDao extends GenericDao {
	public AsvwProduct getAsvwProduct(String cardNo);
	public boolean isExternalProductRequestExist(String cardNo);
}
