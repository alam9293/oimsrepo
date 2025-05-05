package com.cdgtaxi.ibs.enquiry.as.business;

import java.sql.Timestamp;

import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.interfaces.as.model.AsvwProduct;

public class EnquiryBusinessImpl extends GenericBusinessImpl implements EnquiryBusiness {
	public AsvwProduct getAsvwProduct(String cardNo)
	{
		return this.daoHelper.getEnquiryDao().getAsvwProduct(cardNo);
	}
	
	public AmtbAccount getAccount(String custNo, int level, String parentCode, String code)
	{
		return this.daoHelper.getAccountDao().getAccount(custNo, level, parentCode, code);
	}
	
	public PmtbProduct getProduct(String cardNo, Timestamp tripDt)
	{
		return this.daoHelper.getProductDao().getProduct(cardNo, tripDt);
	}
	
	public boolean isExternalProductRequestExist(String cardNo){
		return this.daoHelper.getEnquiryDao().isExternalProductRequestExist(cardNo);
	}
}
