package com.cdgtaxi.ibs.prepaid.ui;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbTransferReq;
import com.cdgtaxi.ibs.common.model.forms.TransferableAmtInfo;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class DetailsTransferReqWindow extends CommonWindow implements AfterCompose {

	private static final Logger logger = Logger.getLogger(DetailsTransferReqWindow.class);

	private Label fromCardLabel, toCardLabel;

	private AccountLabelMacroComponent accountMC;
	private Label cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel;
	private AccountLabelMacroComponent accountMC2;
	private Label cardNoLabel2, nameOnCardLabel2, valueLabel2, cashplusLabel2, balanceExpiryDateLabel2, expiryDateLabel2;

	private Label txferableValueLabel, txferableCashplusLabel;
	private Label transferValueLabel, transferCashplusLabel;
	private Row transferValueCashPlusRow;
	private Label remarksLabel;
	private Label transferFeeLabel;
	private Checkbox waiveTransferFeeField, transferAllField;
	private Label valueAfterTxfLabel, cashplusAfterTxfLabel;

	private PmtbProduct fromProduct;
	private PmtbProduct toProduct;
	protected PmtbTransferReq req;


	
	public DetailsTransferReqWindow() throws InterruptedException {
		super();

	}

	public void afterCompose() {

		Components.wireVariables(this, this);

		try {
			init();
		} catch (Exception e) {
			
			if(e instanceof WrongValueException){
				throw (WrongValueException) e;
			}
			
			LoggerUtil.printStackTrace(logger, e);
			throw new WrongValueException("System error. Please contact System Administrator.");
		}
	}

	@SuppressWarnings("unchecked")
	public void init() throws Exception {

		Map<String, Object> map = Executions.getCurrent().getArg();

		BigDecimal requestNo = (BigDecimal) map.get("requestNo");
		req = this.businessHelper.getPrepaidBusiness().getPrepaidTransferRequest(requestNo);

		fromProduct = req.getFromPmtbProduct();
		toProduct = req.getToPmtbProduct();

		fromCardLabel.setValue(fromProduct.getCardNo());
		toCardLabel.setValue(toProduct.getCardNo());

		displayCardDetails(fromProduct, accountMC, cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel);

		displayCardDetails(toProduct, accountMC2, cardNoLabel2, nameOnCardLabel2, valueLabel2, cashplusLabel2, balanceExpiryDateLabel2, expiryDateLabel2);
	
	
		remarksLabel.setValue(req.getRequestRemarks());
		transferFeeLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getTransferFee()));
		waiveTransferFeeField.setChecked(NonConfigurableConstants.getBoolean(req.getWaiveTransferFeeFlag()));
		
		boolean isTransferAll = NonConfigurableConstants.getBoolean(req.getTransferAllFlag());
		
		transferAllField.setChecked(isTransferAll);
		transferValueCashPlusRow.setVisible(!isTransferAll);
		
		if(!isTransferAll){
			transferValueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getTransferCardValue()));
			transferCashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getTransferCashplus()));
		}
		
		if(this instanceof ViewTransferReqWindow){
			txferableValueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getTxferableCardValue()));
			txferableCashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getTxferableCashplus()));
			
		} else if(this instanceof ApproveTransferReqWindow){
			TransferableAmtInfo transferInfo = this.businessHelper.getPrepaidBusiness().calculateTransferableValueAndCashPlus(fromProduct);
			BigDecimal txferableValue = transferInfo.getCardValue();
			BigDecimal txferableCashplus = transferInfo.getCashplus();
			
			txferableValueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(txferableValue));
			txferableCashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(txferableCashplus));
		}
		
		calculateValueAndCashbackAfterTxf();
	}

	private void displayCardDetails(PmtbProduct product, AccountLabelMacroComponent accountMC, Label cardNoLabel, Label nameOnCardLabel, Label valueLabel, Label cashplusLabel, Label balanceExpiryDateLabel, Label expiryDateLabel) {

		AmtbAccount acct = product.getAmtbAccount();

		accountMC.populateDetails(acct);

		cardNoLabel.setValue(product.getCardNo());
		nameOnCardLabel.setValue(product.getNameOnProduct());

		
		if(this instanceof ViewTransferReqWindow){
			
			if (product == fromProduct) {
				valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getFromCardValue()));
				cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getFromCashplus()));
			} else {
				valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getToCardValue()));
				cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getToCashplus()));
			}
			
		} else if(this instanceof ApproveTransferReqWindow){
			
			valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(product.getCardValue()));
			cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(product.getCashplus()));
		}

		
		balanceExpiryDateLabel.setValue(DateUtil.convertDateToStr(product.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
		expiryDateLabel.setValue(DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));

	}

	@Override
	public void refresh() throws InterruptedException {

	}
	
	
	private void calculateValueAndCashbackAfterTxf() throws ParseException {

		BigDecimal toBalance =  StringUtil.stringToBigDecimal(valueLabel2.getValue());
		BigDecimal toCashplus = StringUtil.stringToBigDecimal(cashplusLabel2.getValue());

		
		BigDecimal transferValue = null;
		BigDecimal transferCashplus = null;
		
		if(NonConfigurableConstants.getBoolean(req.getTransferAllFlag())){
			BigDecimal txferableBalance = StringUtil.stringToBigDecimal(txferableValueLabel.getValue());
			BigDecimal txferableCashplus = StringUtil.stringToBigDecimal(txferableCashplusLabel.getValue());
			
			transferValue = txferableBalance;
			transferCashplus = txferableCashplus;
		} else{
			transferValue = StringUtil.stringToBigDecimal(transferValueLabel.getValue());
			transferCashplus =  StringUtil.stringToBigDecimal(transferCashplusLabel.getValue());
		}
		
		BigDecimal valueAfterTxf = toBalance.add(transferValue);
		BigDecimal cashbackAfterTxf = toCashplus.add(transferCashplus);

		if (!NonConfigurableConstants.getBoolean(req.getWaiveTransferFeeFlag())) {
			valueAfterTxf = valueAfterTxf.subtract(req.getTransferFee());
		}
		valueAfterTxfLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(valueAfterTxf));
		cashplusAfterTxfLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(cashbackAfterTxf));
	}

	
}
