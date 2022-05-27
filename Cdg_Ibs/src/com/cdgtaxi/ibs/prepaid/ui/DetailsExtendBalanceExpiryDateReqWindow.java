package com.cdgtaxi.ibs.prepaid.ui;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbExtBalExpDateReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class DetailsExtendBalanceExpiryDateReqWindow extends CommonWindow implements AfterCompose {

	protected AccountLabelMacroComponent accountMC;
	private Label cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel;
	private Label newBalanceExpiryDateDurationTypeLabel;
	private Label newBalanceExpiryDateDurationLengthLabel;
	private Label newBalanceExpiryDateLabel;
	protected PmtbProduct selectedProduct;
	protected PmtbExtBalExpDateReq req;
	
	private static final Logger logger = Logger.getLogger(DetailsExtendBalanceExpiryDateReqWindow.class);


	public void afterCompose() {
		// wire variables
		Components.wireVariables(this, this);

		init();
	}

	@SuppressWarnings("unchecked")
	public void init() {

		Map<String, Object> map = Executions.getCurrent().getArg();

		BigDecimal requestNo = (BigDecimal) map.get("requestNo");
		req = (PmtbExtBalExpDateReq) this.businessHelper.getPrepaidBusiness().getPrepaidRequest(requestNo);
		
		String durationType = req.getNewBalExpDateDurType();
		
		newBalanceExpiryDateDurationTypeLabel.setValue(NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE.get(durationType));
		
		newBalanceExpiryDateDurationLengthLabel.setValue("-");
		if (NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION.equals(durationType)){
			newBalanceExpiryDateDurationLengthLabel.setValue(String.valueOf(req.getNewBalExpDateDurLen()));
		}
		
		newBalanceExpiryDateLabel.setValue(DateUtil.convertDateToStr(req.getNewBalExpDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
		

		selectedProduct = req.getPmtbProduct();
		AmtbAccount acct = selectedProduct.getAmtbAccount();
		acct = this.businessHelper.getAccountBusiness().getAccount(String.valueOf(acct.getAccountNo()));

		accountMC.populateDetails(acct);

		cardNoLabel.setValue(selectedProduct.getCardNo());
		nameOnCardLabel.setValue(selectedProduct.getNameOnProduct());
		valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCreditBalance()));
		cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCashplus()));
		balanceExpiryDateLabel.setValue(DateUtil.convertDateToStr(req.getOldBalExpDate(), DateUtil.GLOBAL_DATE_FORMAT));
		expiryDateLabel.setValue(DateUtil.convertDateToStr(selectedProduct.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));

	}

	
	@Override
	public void refresh() throws InterruptedException {
		
		
	}
	
	
	
}
