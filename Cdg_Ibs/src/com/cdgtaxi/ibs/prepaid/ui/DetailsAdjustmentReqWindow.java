package com.cdgtaxi.ibs.prepaid.ui;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbAdjustmentReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class DetailsAdjustmentReqWindow extends CommonWindow implements AfterCompose {

	protected AccountLabelMacroComponent accountMC;
	private Label cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel;
	private Label remarksLabel;
	private Label adjustValueAmountLabel, adjustCashplusAmountLabel;
	private Label adjustValueTxnCodeLabel, adjustCashplusTxnCodeLabel;
	private Label adjustValueGstLabel, adjustCashplusGstLabel;
	private PmtbProduct selectedProduct;
	protected PmtbAdjustmentReq req;
	
	private static final Logger logger = Logger.getLogger(DetailsAdjustmentReqWindow.class);

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		init();

	}
	
	@SuppressWarnings("unchecked")
	public void init(){
		Map<String, Object> map = Executions.getCurrent().getArg();

		BigDecimal requestNo = (BigDecimal) map.get("requestNo");
		req = (PmtbAdjustmentReq) this.businessHelper.getPrepaidBusiness().getPrepaidRequest(requestNo);
		
		remarksLabel.setValue(req.getRequestRemarks());
		adjustValueAmountLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getAdjustValueAmount()));
		adjustCashplusAmountLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getAdjustCashplusAmount()));

		
		FmtbTransactionCode adjustValueTxnCode = req.getAdjustValueTxnCode();
		adjustValueTxnCodeLabel.setValue((adjustValueTxnCode!=null)?adjustValueTxnCode.getDescription():"");
		
		FmtbTransactionCode adjustCashplusTxnCode = req.getAdjustCashplusTxnCode();
		adjustCashplusTxnCodeLabel.setValue((adjustCashplusTxnCode!=null)?adjustCashplusTxnCode.getDescription():"");
		
		adjustValueGstLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getAdjustValueGst()));
		adjustCashplusGstLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getAdjustCashplusGst()));
		
		selectedProduct = req.getPmtbProduct();
		AmtbAccount acct = selectedProduct.getAmtbAccount();
		acct = this.businessHelper.getAccountBusiness().getAccount(String.valueOf(acct.getAccountNo()));
		
		accountMC.populateDetails(acct);
		
		cardNoLabel.setValue(selectedProduct.getCardNo());
		nameOnCardLabel.setValue(selectedProduct.getNameOnProduct());
		
		if(this instanceof ViewAdjustmentReqWindow){
			valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getOriValueAmount()));
			cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getOriCashplusAmount()));
		} else if(this instanceof ApproveAdjustmentReqWindow ){
			valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCardValue()));
			cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCashplus()));
		}
		
		
		balanceExpiryDateLabel.setValue(DateUtil.convertDateToStr(selectedProduct.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
		expiryDateLabel.setValue(DateUtil.convertDateToStr(selectedProduct.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
		
		
		
	}

	

	@Override
	public void refresh() throws InterruptedException {
		
		
	}
	
	
	
	
	
	
	
	
}
