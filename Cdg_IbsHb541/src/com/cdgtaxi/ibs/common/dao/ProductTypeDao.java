package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

public interface ProductTypeDao extends GenericDao {
	public List<PmtbProductType> getAllProductType();
	public PmtbProductType getProductType(String productTypeId);
	public List<PmtbProductType> getProductType(PmtbProductType productType);
	public List<AmtbSubscTo> getAmtbSubscToForIssueProduct(String acctNo, boolean isPrepaid);
	public List<PmtbProductType> getProductTypes(List<String> productTypeIds);
	public List<PmtbProductType> getAllProductTypes();
	public PmtbProductType getProductType(String cardNo, java.util.Date tripDt);
	public List<PmtbProductType> getPremierServiceProductType();
	public boolean isProductNameInUse(String productTypeName);
	public boolean isProductNameInUse(String productTypeName,String productTypeId);
	public List<PmtbProductType> getPrepaidProductType();
	public List<PmtbProductType> getExternalProductType();
	public PmtbProductType getExternalProductType(String binRange, String subBinRange);
	
	/**
	 * To retrieve accounts that are subscribed to external product type
	 * 
	 * Seng Tat 27/10/2010:
	 * Changed to custom query to filter out accounts that are closed.
	 * 
	 * @param productTypeId
	 * @return
	 */
	public List<Integer> getAccountSubscribedToExternalProductType(String productTypeId);
	
	public boolean checkGotProduct(String productTypeId);
	public List<PmtbProductType> getAllCardlessProductType();

	public List<PmtbProductType> getPremierAccountProductType (String acctNo);
}
