package com.cdgtaxi.ibs.enquiry.as.business;

import java.sql.Timestamp;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.interfaces.as.model.AsvwProduct;

public interface EnquiryBusiness extends GenericBusiness {

	public AsvwProduct getAsvwProduct(String cardNo);
	public AmtbAccount getAccount(String custNo, int level, String parentCode, String code);
	public PmtbProduct getProduct(String cardNo, Timestamp tripDt);
	public boolean isExternalProductRequestExist(String cardNo);
}