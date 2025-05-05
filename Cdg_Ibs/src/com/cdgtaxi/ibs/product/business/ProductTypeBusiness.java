package com.cdgtaxi.ibs.product.business;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

public interface ProductTypeBusiness extends GenericBusiness {
	public Map<String, Map<String, String>> getProductTypes(PmtbProductType searchProdcut);
	public PmtbProductType getProductType(String productTypeId);
	public boolean getCheckBin(String binNumber,String subBin,boolean isExternalCard);
	public boolean getCheckBin(String binNumber,String subBin,String productTypeId);
	//public void createProductTypeAPI(PmtbProductType productType, String userId) ;
	//public void saveProductTypes(List<ProductType> prodcutTypes);
	public List<PmtbProductType> getAllProductType();
	public List<PmtbProductType> getPrepaidProductType();
	public Serializable save(PmtbProductType newProductType, String userId) throws Exception;
	public List<PmtbProductType> getExternalProductType();
	public PmtbProductType getExternalProductType(String binRange, String subBinRange);
	
	
	public List<PmtbProductType> getProductTypes(List<String> productTypeId);

	public Boolean checkGotProduct(String productTypeId);
	public List<PmtbProductType> getCardlessProductType();
}
