package com.cdgtaxi.ibs.product.ui;

import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings({ "unchecked", "serial" })
public class ViewProductTypeWindow  extends CommonWindow implements AfterCompose{
	
	private PmtbProductType productType;
	private Label productTypeIdLbl, nameLbl, digitLbl, binRangeLbl, subBinRangeLbl,
		issubleLbl, nameOnCardLbl, batchIssueLbl, issueTypeLbl, fixedValueLbl,
		creditLimitLbl, luhnCheckLbl, defaultCardStatusLbl, externalCardLbl,
		usageLbl, validityPeriodLbl, defaultExpiryLbl, replacementFeeLbl,
		interfaceMappingLbl, loginLbl, hotelLbl, prepaidLbl, contactlessLbl, cardlessLbl, virtualLbl, smsFormatLbl, smsExpiryFormatLbl,  smsTopUpFormatLbl,
		defaultBalanceExpMonthsLbl, topupFeeLbl, transferFeeLbl;
	
	
	public ViewProductTypeWindow (){
		Map params = Executions.getCurrent().getArg();
		String productTypeId = (String)params.get("productTypeId");
		productType = (PmtbProductType)this.businessHelper.getProductTypeBusiness().getProductType(productTypeId);
		
		if(productType==null)
			throw new NullPointerException("Product Type["+productType+"] not found!"); //This should not happen at all
	}
			
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		productTypeIdLbl.setValue(productType.getProductTypeId());
		nameLbl.setValue(productType.getName());
		digitLbl.setValue(productType.getNumberOfDigit()+"");
		binRangeLbl.setValue(productType.getBinRange());
		subBinRangeLbl.setValue(productType.getSubBinRange());
		issubleLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getIssuable()));
		nameOnCardLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getNameOnProduct()));
		batchIssueLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getBatchIssue()));
		String issueType = NonConfigurableConstants.ISSUE_TYPE.get(productType.getIssueType());
		if(issueType==null) issueType=productType.getIssueType();
		issueTypeLbl.setValue(issueType);
		fixedValueLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getFixedValue()));
		creditLimitLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getCreditLimit()));
		luhnCheckLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getLuhnCheck()));
		defaultCardStatusLbl.setValue(NonConfigurableConstants.PRODUCT_STATUS.get(productType.getDefaultCardStatus()));
		externalCardLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getExternalCard()));
		usageLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getOneTimeUsage()));
		validityPeriodLbl.setValue(NonConfigurableConstants.VALIDITY_PERIOD_FLAGS.get(productType.getValidityPeriod()));
		defaultExpiryLbl.setValue(productType.getDefaultValidPeriod()+"");
		replacementFeeLbl.setValue(StringUtil.bigDecimalToString(productType.getReplacementFees(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		interfaceMappingLbl.setValue(productType.getInterfaceMappingValue());
		loginLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getLoginRegistration()));
		hotelLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getHotel()));
		prepaidLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getPrepaid()));
		contactlessLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getContactless()));
		cardlessLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getCardless()));
		virtualLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(productType.getVirtualProduct()));
		MstbMasterTable mstbMasterTableBySMSFormat = productType.getMstbMasterTableBySMSFormat();
		String smsFormatValue = (mstbMasterTableBySMSFormat!=null) ? mstbMasterTableBySMSFormat.getMasterCode() : "-";
		smsFormatLbl.setValue(smsFormatValue);
		
		MstbMasterTable mstbMasterTableBySMSExpiryFormat = productType.getMstbMasterTableBySMSExpiryFormat();
		String smsExpiryFormatValue = (mstbMasterTableBySMSExpiryFormat!=null) ? mstbMasterTableBySMSExpiryFormat.getMasterCode() : "-";
		smsExpiryFormatLbl.setValue(smsExpiryFormatValue);
		
		MstbMasterTable mstbMasterTableBySMSTopUpFormat = productType.getMstbMasterTableBySMSTopupFormat();
		String smsTopUpFormatValue = (mstbMasterTableBySMSTopUpFormat!=null) ? mstbMasterTableBySMSTopUpFormat.getMasterCode() : "-";
		smsTopUpFormatLbl.setValue(smsTopUpFormatValue);
		
		defaultBalanceExpMonthsLbl.setValue(String.valueOf(productType.getDefaultBalanceExpMonths()));
		topupFeeLbl.setValue(StringUtil.bigDecimalToStringWithGDFormat(productType.getTopUpFee()));
		transferFeeLbl.setValue(StringUtil.bigDecimalToStringWithGDFormat(productType.getTransferFee()));
		
	}

	@Override
	public void refresh() throws InterruptedException {
		
	}
}